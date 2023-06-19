#include <stdio.h>
#include <WinSock2.h>

#define SERVER_IP "192.168.1.6"
#define SERVER_PORT 9999

void win_init() {
    WSADATA wsaData;
    if(WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {
        perror("Error initializing windows socket library!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(-99);
    }
}

int main() {
    win_init();

    SOCKET s = socket(AF_INET, SOCK_STREAM, 0);
    if(s < 0) {
        perror("Socket creation error!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(1);
    }

    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(SERVER_PORT);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr(SERVER_IP);

    if(connect(s, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Could not connect to server!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(2);
    }

    int numbers[] = {1,5,47,8,-10,37}, length = 6;
    for(int i=0; i<length; i++)
        numbers[i] = htons(numbers[i]);
    length = htons(length);

    send(s, (void*) &length, sizeof(length), 0);
    send(s, (void*) numbers, sizeof(numbers), 0);

    int sum;
    recv(s, (void*) &sum, sizeof(sum), 0);
    sum = ntohs(sum);

    printf("Sum is: %d!\n", sum);

    closesocket(s);
    WSACleanup();
    return 0;
}