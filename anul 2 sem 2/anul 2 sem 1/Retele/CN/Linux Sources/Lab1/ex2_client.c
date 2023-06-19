#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <stdlib.h>

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <errno.h>

#define closesocket close

#define SERVER_IP "192.168.1.8"
#define SERVER_PORT 9999

typedef int SOCKET;

#define DATA_STRING "in this string there are 6 spaces"

int main() {
    SOCKET s = socket(AF_INET, SOCK_STREAM, 0);
    if(s < 0) {
        perror("Socket creation error!");
        exit(1);
    }

    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(SERVER_PORT);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr(SERVER_IP);

    if(connect(s, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Could not connect to server!");
        printf("Error code: %d\n", errno);
        exit(2);
    }

    char data[] = DATA_STRING;
    send(s, (void*) data, strlen(data)+1, 0);

    int space_count;
    recv(s, (void*) &space_count, sizeof(space_count), 0);
    space_count = ntohs(space_count);

    printf("Number of spaces: %d\n", space_count);

    closesocket(s);
    return 0;
}