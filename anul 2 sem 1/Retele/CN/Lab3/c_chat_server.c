#include <stdio.h>
#include <stdint.h>
#include <WinSock2.h>

#define SERVER_PORT 9999

fd_set master, read_fds;
SOCKET listen_sock;
int max_fd, client_count;

struct sockaddr_in server, client;

void win_init() {
    WSADATA wsaData;
    if(WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {
        perror("Error initializing windows socket library!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(-99);
    }
}

struct sockaddr_in getSocketName(SOCKET sock, int local) {
    struct sockaddr_in addr;
    int len = sizeof(addr);
    memset(&addr, 0, len);

    int ret = (local==1?getsockname(sock, (struct sockaddr*) &addr, (void*) &len):   // getsockname -> data of the local socket
               getpeername(sock, (struct sockaddr*) &addr, (void*) &len));        // getpeername -> data of the remote socket

    if(ret < 0)
        perror("getSocketName Error: ");

    return addr;
}

char * getIPAddress(SOCKET sock, int local) {
    struct sockaddr_in addr = getSocketName(sock, local);
    return inet_ntoa(addr.sin_addr);
}

int getPort(SOCKET sock, int local) {
    struct sockaddr_in addr = getSocketName(sock, local);
    return addr.sin_port;
}

void sendToAll(char *buf, int nbytes, int except) {
    for(int i=0; i<=max_fd; i++)
        if(i != except && i != listen_sock && FD_ISSET(i, &master))
            if(send(i, buf, nbytes, 0) < 0)
                perror("Send error:");
}

int main() {
    win_init();

    FD_ZERO(&master);
    FD_ZERO(&read_fds);

    if((listen_sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Socket creation error!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(1);
    }
    int reuse_addr = 1;
    setsockopt(listen_sock, SOL_SOCKET, SO_REUSEADDR, (void*) &reuse_addr, sizeof(int));

    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(SERVER_PORT);
    server.sin_addr.s_addr = INADDR_ANY;

    if(bind(listen_sock, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Bind error!");
        printf("Error code %d\n", WSAGetLastError());
        exit(2);
    }
    listen(listen_sock, 2);
    
    FD_SET(listen_sock, &master);
    max_fd = listen_sock;

    printf("Chat room started!\n");
    while (1)
    {
        read_fds = master;
        if(select(max_fd+1, &read_fds, NULL, NULL, NULL) == -1) {
            perror("Select error!");
            printf("Error code %d\n", WSAGetLastError());
            exit(4);
        }

        for(int i=0; i<=max_fd; i++) {
            if(FD_ISSET(i, &read_fds)) {
                if(i == listen_sock) {      // handle a new conection
                    SSIZE_T client_size = sizeof(client);
                    SOCKET client_sock = accept(listen_sock, (struct sockaddr*) &client, (void*) &client_size);
                    if(client_sock < 0) {
                        perror("Accept error!");
                        printf("Error code %d\n", WSAGetLastError());
                        continue;
                    }
                    client_count++;

                    FD_SET(client_sock, &master);
                    if(client_sock > max_fd)
                        max_fd = client_sock;


                    char *client_IP = getIPAddress(client_sock, 0);
                    printf("\t>>> New connection from %s on socket %du\n", client_IP, (int) client_sock);
                    
                    char buf[128];
                    int nbytes = sprintf(buf, "Hi! You are client :[%d] (%s:%d) connected to server %s\nThere are %d clients connected\n",
                            (int) client_sock, client_IP, getPort(client_sock, 0), getIPAddress(listen_sock, 1), client_count);
                    send(client_sock, (void*) buf, nbytes, 0);
                }
                else {      // handle client data
                    char buf[4000];
                    int nbytes = recv(i, (void*) buf, sizeof(buf), 0);
                    if(nbytes <= 0) {  // connection closed
                        printf("Connection to client %s closed!\n", getIPAddress(i, 0));
                        client_count--;
                        FD_CLR(i, &master);
                        closesocket(i);

                        continue;
                    }

                    char tmpbuf[4089];
                    buf[nbytes] = '\0';
                    nbytes = sprintf(tmpbuf, "<%s - [%d]> %s", getIPAddress(i, 0), i, buf);
                    sendToAll(tmpbuf, nbytes, i);
                }
            }
        }

    }
    


    closesocket(listen_sock);
    WSACleanup();
    return 0;
}
