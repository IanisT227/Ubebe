#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <WinSock2.h>

#define SERVER_PORT 9999
#define SERVER_IP "192.168.1.6"

#define bzero(b,len) (memset((b), '\0', (len)), (void) 0)  
#define bcopy(b1,b2,len) (memmove((b2), (b1), (len)), (void) 0)

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

    SOCKET sock = socket(AF_INET, SOCK_DGRAM, 0);
    if(sock < 0) {
        perror("Socket creation error!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(1);
    }

    struct sockaddr_in server, from;
    struct hostent *hp;


    server.sin_family = AF_INET;
    if((hp = gethostbyname(SERVER_IP)) == 0) {
        perror("Unknown host!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(2);
    }

    bcopy((char *) hp->h_addr, (char *) &server.sin_addr, hp->h_length);
    server.sin_port = htons(SERVER_PORT);
    int server_len = sizeof(struct sockaddr_in);

    char buf[4098];
    printf("Please enter a message: ");
    bzero(buf, 4098);
    fgets(buf, 4098, stdin);

    int nbytes = sendto(sock, buf, 4098, 0, (struct sockaddr *) &server, server_len);
    if(nbytes < 0) {
        perror("Send error!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(2);
    }

    nbytes = recvfrom(sock, buf, 4098, 0, (struct sockaddr*) &from, &server_len);
    if(nbytes < 0) {
        perror("Receive error!");
        printf("Error code: %d\n", WSAGetLastError());
        exit(2); 
    }


    // linux
    //write(stdout, "Got an ack: ", 12);
    //write(stdout, buf, nbytes);


    printf("Got an ack: %s\n", buf);

    WSACleanup();
    return 0;
}