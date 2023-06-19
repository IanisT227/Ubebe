#include <stdio.h>
#include <stdint.h>
#include <WinSock2.h>
#include <windows.h>

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

    SOCKET sock = socket(AF_INET, SOCK_STREAM, 0);
    if(sock < 0) {
        perror("Socket creation error!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(1);
    }

    struct sockaddr_in server;
    server.sin_port = htons(SERVER_PORT);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr(SERVER_IP);

    if(connect(sock, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Could not connect to server!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(2);
    }

    char welcome[1025];
    recv(sock, (void*) welcome, sizeof(welcome), 0);
    printf("%s\n", welcome);

    char game_state = 'X';
    int min = 0, max = INT_MAX;
    int32_t nr, network_nr;

    while(game_state != 'L'&& game_state != 'W') {
        nr = rand() % (max - min + 1) + min;
        printf("You tried nr %d\n", nr);

        network_nr = htonl(nr);
        send(sock,(void*) &network_nr, sizeof(nr), 0);
        recv(sock, (void*) &game_state, 1, 0);

        switch (game_state)
        {
        case 'S': {
            min = nr+1;
            break;
        }
        case 'H': {
            max = nr-1;
            break;
        }
        default:
            break;
        }

        Sleep(0.5);
    }

    if(game_state == 'W')
        printf("You win!\n");
    else
        printf("You lose!\n");

    closesocket(sock);
    WSACleanup();
    return 0;
}