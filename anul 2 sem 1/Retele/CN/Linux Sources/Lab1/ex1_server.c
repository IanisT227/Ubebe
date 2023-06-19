#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <errno.h>

#define closesocket close

#define SERVER_PORT 9999

typedef int SOCKET;

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
    server.sin_addr.s_addr = INADDR_ANY;
    
    if(bind(s, (struct sockaddr *) &server, sizeof(server)) < 0) {
        perror("Bind error!");
        printf("Error code %d\n", errno);
        exit(2);
    }
    listen(s, 5);

    struct sockaddr_in client;
    ssize_t client_size = sizeof(client);
    memset(&client, 0, client_size);

    int array_length, sum;
    while(1) {
        printf("\nListening for incomming connections...\n");

        int conn_sock = accept(s, (struct sockaddr*) &client, (void*) &client_size);
        if(conn_sock < 0) {
            perror("Accept error!");
            printf("Error code %d\n", errno);
            continue;
        }
        printf("Client connected -> %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));

        int res = recv(conn_sock, (void*) &array_length, sizeof(array_length), 0);
        if(res != sizeof(array_length)) {
            printf("Error on receiving length!\nExpected %ld bytes, received %d bytes\n\n", sizeof(int), res);
            continue;
        }
        array_length = ntohs(array_length);

        ssize_t expected_bytes = array_length * sizeof(int);
        int *array = (int*) malloc(expected_bytes);
        res = recv(conn_sock, (void*) array, expected_bytes, 0);
        if(res != array_length * sizeof(int)) {
            printf("Error on receiving array!\nExpected %ld bytes, received %d bytes\n\n", expected_bytes, res);
            continue;
        }

        sum = 0;
        for(int i=0; i<array_length; i++)
            sum += ntohs(array[i]);

        sum = htons(sum);
        send(conn_sock, (void*) &sum, sizeof(sum), 0);

        closesocket(conn_sock);
        free(array);
    }

    return 0;
}