#include <stdio.h>
#include <stdint.h>
#include <WinSock2.h>

#define SERVER_IP "192.168.1.6"
#define SERVER_PORT 9999
#define MAX_CLIENTS 20
#define MAX_LEN_BUF 4098

SOCKET server_sock, udp_sock;
fd_set master, read_fds;
int max_fd;
struct sockaddr_in recv_addr;

void win_init() {
    WSADATA wsaData;
    if(WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {
        perror("Error initializing windows socket library!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(-99);
    }
}

struct client_data {
    char ip[20];
    int port;
    int socket;
}clients[MAX_CLIENTS];

int client_count;

void deserialize_clients(char *buf) {
    int client_size = sizeof(struct client_data);

    for(int i=0; i<MAX_CLIENTS; i++) {
        memcpy(clients[i].ip, buf + i*client_size, 20);        // 20 -> strucct char max length
        memcpy(&clients[i].port, buf + i*client_size + sizeof(char) * 20, sizeof(int));
        memcpy(&clients[i].socket, buf + i*client_size + sizeof(char) * 20 + sizeof(int), sizeof(int));
    }
}

void update_client_list(SOCKET server_sock) {
    int new_client_count;
    recv(server_sock, (void*) &new_client_count, sizeof(new_client_count), 0);
    client_count = ntohs(new_client_count);

    int data_length = sizeof(struct client_data) * MAX_CLIENTS + 1;
    char data[data_length];
    recv(server_sock, (void*) data, data_length, 0);

    deserialize_clients(data);
}

int main(int argc, char **argv) {
    if(argc < 2) {
        printf("Usage: <command> <port>");
        exit(-1);
    }

    win_init();

    server_sock = socket(AF_INET, SOCK_STREAM, 0);
    if(server_sock < 0) {
        perror("Socket creation error!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(1);
    }

    udp_sock = socket(AF_INET, SOCK_DGRAM, 0);
    if(udp_sock < 0) {
        perror("Socket creation error!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(1);
    }

    char broadcast = '1';
    if(setsockopt(udp_sock,SOL_SOCKET,SO_BROADCAST,&broadcast,sizeof(broadcast)) < 0)
    {
        perror("Could not set broadcast option!");
        printf("Error code: %d\n", WSAGetLastError());
        closesocket(udp_sock);
        exit(4);
    }

    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));
    server.sin_addr.s_addr = inet_addr(SERVER_IP);
    server.sin_family = AF_INET;
    server.sin_port = htons(SERVER_PORT);

    if(connect(server_sock, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Could not connect to server!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(2);
    }

    // receive struct
    recv_addr.sin_family = AF_INET;
    recv_addr.sin_port = htons(atoi(argv[1]));
    recv_addr.sin_addr.s_addr = INADDR_BROADCAST;


    FD_ZERO(&master);
    FD_ZERO(&read_fds);
    FD_SET(fileno(0), &master);
    FD_SET(server_sock, &master);
    FD_SET(udp_sock, &master);
    max_fd = server_sock;

    char buf[MAX_LEN_BUF];
    while(1) {
        read_fds = master;
        if(select(max_fd+1, &read_fds, NULL, NULL, NULL) < 0 ) {
            perror("Select error!");
            printf("Error code %d\n", WSAGetLastError());
            closesocket(server_sock);
            exit(4);
        }

        for(int i=0; i<=max_fd; i++) {
            if(FD_ISSET(i, &read_fds)) {
                if(i == server_sock) {  // handle client list update
                    update_client_list(i);
                }
                else if (i == STDIN_FILENO) {  // (stdin) broadcast message
                    scanf("%s", buf);
                    sendto(udp_sock, buf, sizeof(buf)+1, 0, (struct sockaddr*) &recv_addr, sizeof(struct sockaddr_in));
                }
                else { // receive communication

                }
            }
        }
    }


    WSACleanup();
    return 0;
}