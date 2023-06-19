#include <stdio.h>
#include <WinSock2.h>

//for Visual C++ compiler
//#pragma comment(lib,"Ws2_32.lib")

//for gcc use -l ws2_32

int main() {
    setvbuf(stdout, NULL, _IONBF, 0);
    // initialize the Windows Sockets Library
    WSADATA wsaData;
    if(WSAStartup(MAKEWORD(2,2), &wsaData) < 0) {
        perror("Error initializing the Windows Sockets Library\n");
        exit(-1);
    }

    unsigned long long sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock < 0) {
        perror("Socket creation error!\n");
        exit(1);
    }

    //create socket
    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));
    server.sin_port = htons(4444); // server port
    server.sin_family = AF_INET;            // server address family IPv4
    server.sin_addr.s_addr = INADDR_ANY;

    //bind socket
    if(bind(sock, (struct sockaddr *) &server, sizeof(server)) < 0) {
        perror("Bind error!\n");
        exit(2);
    }
    listen(sock, 5);

    struct sockaddr_in client;
    memset(&client, 0, sizeof(client));
    int l = sizeof(client);

    //waiting loop
    int connection, err;
    while(1) {
        int a, b;
        printf("Listening for incoming connections\n");

        //accepting connection
        connection = accept(sock, (struct sockaddr *) &client, &l);
        err = errno;
        err = WSAGetLastError();

        if(connection < 0) {
            printf("Accept error: %d", err);
            continue;
        }

        printf("Client connected! %s:%d\n", inet_ntoa(client.sin_addr), ntohs(client.sin_port));  //inet_ntop -> %d
        // a
        int res;
        res = recv(connection, (char*)&a, sizeof(a), 0);
        if(res != sizeof(a)){
            printf("Error on receiving data!\n");
            continue;
        }
        // b
        res = recv(connection, (char*)&b, sizeof(b), 0);
        if(res != sizeof(b)){
            printf("Error on receiving data!\n");
            continue;
        }

        a = ntohs(a);
        b = ntohs(b);
        int sum = a + b;
        printf("Numbers %d and %d recevied -> sending Sum: %d\n", a,b,sum);
        sum = htons(sum);

        // sending sum
        res = send(connection, (char*)&sum, sizeof(sum),0);
        if(res != sizeof(sum)) {
            printf("Error on sending data!\n");
        }
        closesocket(connection);
    }

    return 0;
}
