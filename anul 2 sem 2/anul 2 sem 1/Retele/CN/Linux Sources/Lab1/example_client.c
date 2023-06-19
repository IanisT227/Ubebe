#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>

#define SERVER_ADDRESS "192.168.1.8"
#define A 55
#define B 130

int main() {
    int sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock < 0) {
        printf("Socket creation error!\n");
        exit(1);
    }

    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(4444); // server port
    server.sin_family = AF_INET;            // server address family IPv4
    server.sin_addr.s_addr = inet_addr(SERVER_ADDRESS); // server address

    printf("Connecting to %s...\n", SERVER_ADDRESS);
    if(connect(sock, (struct sockaddr *) &server, sizeof(server)) < 0) {
        printf("Could not connect to server!\n");
        exit(2);
    }

    int a = htons(A), b = htons(B);
    printf("Sending data...\n");
    send(sock, &a, sizeof(a), 0);
    send(sock, &b, sizeof(b), 0);

    int sum;
    recv(sock, &sum, sizeof(sum), 0);
    sum = ntohs(sum);

    printf("Suma este %d\n\n", sum);

    close(sock);
    return 0;
}
