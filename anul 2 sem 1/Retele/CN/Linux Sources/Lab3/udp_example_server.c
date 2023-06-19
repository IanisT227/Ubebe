#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

#define SERVER_PORT 9999

typedef int SOCKET;


int main() {
    SOCKET sock = socket(AF_INET, SOCK_DGRAM, 0);
    if(sock < 0) {
        perror("Socket creation error!");
        printf("Error code: %d\n", errno);
        exit(1);
    }

    struct sockaddr_in server, from;
    bzero(&server, sizeof(server));
    bzero(&from, sizeof(from));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_port = htons(SERVER_PORT);

    if(bind(sock, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Bind error!");
        printf("Error code %d\n", errno);
        exit(2);
    }

    char msg[] = "Message received\n";
    int msg_len = sizeof(msg);
    int from_len = sizeof(struct sockaddr_in);
    char buf[4098];
    while(1) {
        int nybtes = recvfrom(sock, buf, 4098, 0, (struct sockaddr*) &from, &from_len);
        if(nybtes < 0) {
            perror("Receive error!");
            printf("Error code: %d\n", errno);
            continue;
        }
        printf("Received datagram: %s\n", buf);

        nybtes = sendto(sock, msg, msg_len, 0, (struct sockaddr*) &from, from_len);
        if(nybtes < 0) {
            perror("Send error!");
            printf("Error code: %d\n", errno);
            continue;
        }
    }


    return 0;
}