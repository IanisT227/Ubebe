#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <errno.h>

#define closesocket close

#define SERVER_IP "192.168.1.3"
#define SERVER_PORT 9999

typedef int SOCKET;

fd_set master, read_fds;

int main() {
    /*int ipaddr = inet_addr(SERVER_IP);
    // check if address is a hostname
    
    printf("%s => %d ip address\n",SERVER_IP,ipaddr);
    if (ipaddr == -1 ) {
      struct in_addr inaddr;
      struct hostent * host = gethostbyname( SERVER_IP );
      if (host == NULL ) {
               printf("Error getting the host address\n");
               exit(1);
      }
      memcpy(&inaddr.s_addr, host->h_addr_list[0],sizeof(inaddr));
      printf("Connecting to %s ...\n",inet_ntoa( inaddr) );
      memcpy(&ipaddr, host->h_addr_list[0],sizeof(unsigned long int)) ;
    }*/


    SOCKET sock;
    if((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0) {
        perror("Socket creation error!");
        printf("Error code: %d\n", errno);
        exit(1);
    }

    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = inet_addr(SERVER_IP);
    server.sin_port = htons(SERVER_PORT);

    if(connect(sock, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Could not connect to server!");
        printf("Error code: %d\n", errno);
        close(sock);
        exit(2);
    }

    FD_ZERO(&master);
    FD_ZERO(&read_fds);
    FD_SET(STDIN_FILENO, &master);
    FD_SET(sock, &master);

    int nbytes;
    char buf[4089];
    while(1) {
        read_fds = master;
        if(select(sock+1, &read_fds, NULL, NULL, NULL) == -1 ) {
            perror("Select error!");
            printf("Error code %d\n", errno);
            close(sock);
            exit(4);
        }

        if(FD_ISSET(STDIN_FILENO, &read_fds)) { // send new message
            nbytes = read(STDIN_FILENO, buf, sizeof(buf)-1);
            if(send(sock, buf, nbytes, 0) < 0) {
                perror("Could not send message!");
                printf("Error code: %d\n", errno);
                close(sock);
                exit(3);
            }
        }

        if(FD_ISSET(sock, &read_fds)) { // handle incoming messages
            nbytes = read(sock, buf, sizeof(buf)-1);
            if(nbytes <= 0) {
                printf("Server has closed connection... closing...\n");
                close(sock);
                exit(5);
            }
            write(STDOUT_FILENO, buf, nbytes);
        }
    }

    close(sock);
    return 0;
}





