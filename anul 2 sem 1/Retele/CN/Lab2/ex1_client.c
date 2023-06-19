#include <stdio.h>
#include <stdint.h>
#include <WinSock2.h>

#define SERVER_IP "192.168.1.5"
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

    SOCKET sock = socket(AF_INET, SOCK_STREAM, 0);
    if(sock < 0) {
        perror("Socket creation error!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(1);
    }

    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));
    server.sin_addr.s_addr = inet_addr(SERVER_IP);
    server.sin_family = AF_INET;
    server.sin_port = htons(SERVER_PORT);

    if(connect(sock, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Could not connect to server!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(2);
    }

    char command[51];
    printf("Enter command:\n");
    fgets(command, 51, stdin);
    command[strlen(command)-1] = '\0';

    send(sock, (void*) command, sizeof(command), 0);

    char response[4096];
    recv(sock, (void*) response, sizeof(response), 0);
    printf("%s\n", response);

    int exec_code;
    recv(sock, (void*) &exec_code, sizeof(exec_code), 0);
    printf("Exec code: %d\n", exec_code);

    closesocket(sock);

    WSACleanup();
    return 0;
}