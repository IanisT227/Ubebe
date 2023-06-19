#include <stdio.h>
#include <stdint.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>


#include <pthread.h>

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <errno.h>


#define SERVER_PORT 9999

typedef int SOCKET;
#define closesocket close


void convert_string(char *args[], char *string, int *arg_nr) {
    int nr = 0;

    char *p = strtok(string, " ");
	while(p != NULL) {
        args[nr++] = p;
        p = strtok(NULL, " ");
    }

    args[nr] = NULL;

    *arg_nr = nr;
}

int main() {
    SOCKET sock = socket(AF_INET, SOCK_STREAM, 0);
    if(sock < 0) {
        perror("Socket creation error!");
        printf("Error code %d\n", errno);
        exit(1);
    }

    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));
    server.sin_addr.s_addr = INADDR_ANY;
    server.sin_family = AF_INET;
    server.sin_port = htons(SERVER_PORT);

    if(bind(sock, (struct sockaddr*) &server, sizeof(server)) < 0) {
        perror("Bind error!");
        printf("Error code %d\n", errno);
        exit(2);
    }
    listen(sock, 2);

    struct sockaddr_in client;
    ssize_t client_size = sizeof(client);
    memset(&client, 0, client_size);

    while(1)
    {
        SOCKET client_sock = accept(sock, (struct sockaddr*) &client, (void*) &client_size);
        if(client_sock < 0) {
            perror("Accept error!");
            printf("Error code %d\n", errno);
            continue;
        }


        int fork_code = fork();
        if(fork_code < 0) {
            perror("Error on creating child process!");
            printf("Error code %d\n", errno);
            continue;
        }
        else if(fork_code == 0) {
            SOCKET child_sock = client_sock;
            struct sockaddr_in child = client;

            printf("Client connected -> %s:%d\n", inet_ntoa(child.sin_addr), ntohs(child.sin_port));

            char command[51];
            recv(child_sock, (void*) command, sizeof(command), 0);

            printf("%s\n\n", command);

            int arg_nr;
            char *args[50];
            convert_string(args, command, &arg_nr);

            dup2(child_sock, STDOUT_FILENO);

            int child_id = fork();
            if(child_id == 0) {
                if(execv(args[0], args) < 0) {
                    char err[] = "ERROR";
                    send(child_sock, (void*) err, sizeof(err), 0);
                    exit(-99);
                }
            }

            int exec_code;
            waitpid(child_id, &exec_code, 0);
            send(child_sock, (void*) &exec_code, sizeof(exec_code), 0);

            close(child_sock);
            exit(0);
        }

    }

    return 0;
}