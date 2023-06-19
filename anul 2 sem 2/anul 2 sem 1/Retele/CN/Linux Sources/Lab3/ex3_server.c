#include <stdio.h>
#include <unistd.h>
#include <stdint.h>
#include <string.h>
#include <stdlib.h>

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <errno.h>

#define SERVER_PORT 9999
#define MAX_CLIENTS 20

typedef int SOCKET;

SOCKET sock;
fd_set master, read_fds;
struct sockaddr_in server, client;
int max_fd, client_count, client_size;

struct client_data {
    char ip[20];
    int port;
    int socket;
}clients[MAX_CLIENTS];


void remove_client(SOCKET sock) {
    int i;

    for(i=0; i<client_count; i++)
        if(clients[i].socket == sock)
            break;

    for(i=i;i<client_count-1; i++) {
        strcpy(clients[i].ip, clients[i+1].ip);
        clients[i].port = clients[i+1].port;
    }

    client_count --;
}

void serialize_clients(char *buf) {
    int client_size = sizeof(struct client_data);
    int sock = 0;

    for(int i=0; i<MAX_CLIENTS; i++) {
        memcpy(buf + i*client_size, clients[i].ip, 20);  // stuct char max length
        memcpy(buf + i*client_size + sizeof(char) * 20, &clients[i].port, sizeof(int));
        memcpy(buf + i*client_size + sizeof(char) * 20 + sizeof(int), &sock, sizeof(int));
    }
}

void send_client_list(int except_sock) {
    int data_length = sizeof(struct client_data) * MAX_CLIENTS + 1;
    char data[data_length];
    serialize_clients(data);
    data[data_length] = '\0';

    int network_client_count = htons(client_count);

    for(int i=0; i<=max_fd; i++)
        if(FD_ISSET(i, &master) && i != except_sock && i != sock) {
            send(i, (void*) &network_client_count, sizeof(network_client_count), 0);

            send(i, (void*) data, data_length, 0);
        }
}


struct sockaddr_in getSocketName(SOCKET sock) {
    struct sockaddr_in addr;
    int len = sizeof(addr);
    memset(&addr, 0, len);

    int ret = getpeername(sock, (struct sockaddr*) &addr, (void*) &len);

    if(ret < 0)
        perror("getSocketName Error: ");

    return addr;
}

char * getIPAddress(SOCKET sock) {
    struct sockaddr_in addr = getSocketName(sock);
    return inet_ntoa(addr.sin_addr);
}

int getPort(SOCKET sock) {
    struct sockaddr_in addr = getSocketName(sock);
    return addr.sin_port;
}

int main() {
    FD_ZERO(&master);
    FD_ZERO( &read_fds);

    sock = socket(AF_INET, SOCK_STREAM, 0);
    if(sock < 0) {
        perror("Socket creation error!");
        printf("Error code: %d\n", errno);
        exit(1);
    }
    int reuse_addr = 1;
    setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, (void*) &reuse_addr, sizeof(int));

    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_port = htons(SERVER_PORT);
    server.sin_addr.s_addr = INADDR_ANY;

    if(bind(sock, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Bind error!");
        printf("Error code %d\n", errno);
        exit(2);
    }
    listen(sock, 2);

    FD_SET(sock, &master);
    max_fd = sock;

    client_size = sizeof(client);
    printf("Server started!\n\n");
    while(1) {
        read_fds = master;
        if(select(max_fd+1, &read_fds, NULL, NULL, NULL) == -1) {
            perror("Select error!");
            printf("Error code %d\n", errno);
            exit(4);
        }

        for(int i=0; i<=max_fd; i++) {
            if(FD_ISSET(i, &read_fds)) {
                if(i == sock) {  // handle a new connection
                    SOCKET client_sock = accept(sock, (struct sockaddr*) &client, (void*) &client_size);
                    if(client_sock < 0) {
                        perror("Accept error!");
                        printf("Error code %d\n", errno);
                        continue;
                    }

                    char *client_ip = getIPAddress(client_sock);
                    int client_port = getPort(client_sock);

                    strcpy(clients[client_count].ip, client_ip);
                    clients[client_count].port = client_port;
                    clients[client_count].socket = client_sock;
                    client_count ++;

                    FD_SET(client_sock, &master);
                    if(client_sock > max_fd)
                        max_fd = client_sock;

                    send_client_list(-1);

                    printf("Client %s:%d connected...\n", client_ip, client_port);

                }
                else {  // handle a client leaving
                    remove_client(i);

                    send_client_list(i);

                    FD_CLR(i, &master);
                    close(i);

                    printf("Client on socket: %d disconnected...\n", i);
                }
            }
        }

    }

    return 0;
}