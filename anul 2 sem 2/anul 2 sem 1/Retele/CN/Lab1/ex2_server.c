#include <stdio.h>
#include <WinSock2.h>

#define SERVER_PORT 9999

void win_init() {
    WSADATA wsaData;
    if(WSAStartup(MAKEWORD(2, 2), &wsaData) < 0) {
        perror("Error initializing windows socket library!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(-99);
    }
}

int get_space_count(char *string) {
    int space_count = 0;
    for(int i=0; i<strlen(string); i++)
        if(string[i] == ' ')
            space_count ++;

    return space_count;
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
    server.sin_addr.s_addr = INADDR_ANY;

    if(bind(s, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Bind error!");
        printf("Error code %d\n", WSAGetLastError());
        exit(2);
    }
    listen(s, 5);

    struct sockaddr_in client;
    SSIZE_T client_size = sizeof(client);
    memset(&client, 0,  client_size);

    SSIZE_T string_length = 101;
    char string[101];
    while(1) {
        printf("\nListening for incomming connections...\n");

        SOCKET conn_sock = accept(s, (struct sockaddr*) &client, (void*) &client_size);
        if(conn_sock < 0) {
            perror("Accept error!");
            printf("Error code %d\n", WSAGetLastError());
            continue;
        }
        printf("Client connected -> %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));

        memset(string, 0, string_length);
        int res = recv(conn_sock, string, sizeof(string), 0);
        if(res <= 0) {
            printf("Error on receiving string!\n");
            continue;
        }
        string[res+1] = '\0';
        printf("\t >>> The string is: %s\n", string);

        int space_count = htons(get_space_count(string));
        send(conn_sock, (char*) &space_count, sizeof(space_count), 0);

        closesocket(conn_sock);
    }

    closesocket(s);
    return 0;
}