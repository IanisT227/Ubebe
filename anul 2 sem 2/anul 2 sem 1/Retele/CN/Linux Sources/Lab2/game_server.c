#include <stdio.h>
#include <unistd.h>
#include <stdint.h>
#include <string.h>
#include <signal.h>
#include <stdlib.h>

#include <pthread.h>

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <errno.h>

#define closesocket close

#define SERVER_PORT 9999

typedef int SOCKET;


pthread_mutex_t m = PTHREAD_MUTEX_INITIALIZER;
pthread_cond_t cnd = PTHREAD_COND_INITIALIZER;
pthread_t *t, reset_thread;
int rand_min = 0, rand_max = INT32_MAX/10, game_finished = 0, number, client_idx, winner_thread, winner_thread_tries;
char S = 'S', H = 'H', W = 'W', L = 'L';

struct client_info
{
    struct sockaddr_in client;
    SOCKET sock;
    int client_id;

}*clients;

void* reset_game(void* arg) {
    while(1) {
        pthread_mutex_lock(&m);
        while(!game_finished) {
            pthread_cond_wait(&cnd, &m);
        }

        printf("We have a winner! Thread %d gessed after %d tries!\n", winner_thread, winner_thread_tries);
        for(int i=0; i<client_idx; i++) {
            if(i != winner_thread) {
                send(clients[i].sock, (void*) &L, 1, 0);
            }
            pthread_join(t[i], NULL);
        }

        printf("Resetting game!\n");
        sleep(2);
        game_finished = 0;
        number = rand() % (rand_max - rand_min + 1) + rand_min;
        client_idx = 0;

        free(t);
        t = (pthread_t*) malloc(50*sizeof(pthread_t));
        free(clients);
        clients = (struct client_info*) malloc(50*sizeof(struct client_info));

        printf("\n\n\nNew game strated! The number is %d!\n", number);
        pthread_mutex_unlock(&m);
    }

    return NULL;
}

void* worker(void* arg) {
    struct client_info *c_info = (struct client_info*) arg;
    int32_t response, tries = 1;
    
    printf("Client connected -> %s:%d\n", inet_ntoa(c_info->client.sin_addr), ntohs(c_info->client.sin_port));
    char message[] = "Hello client! You are entering the number guess competion now !";
    send(c_info->sock, (void *) message, sizeof(message), 0);

    while(!game_finished) {
        recv(c_info->sock, (void*) &response, sizeof(response), 0);
        response = ntohl(response);

        if(response < number)
            send(c_info->sock, (void*) &S, 1, 0);
        else if(response > number)
            send(c_info->sock, (void*) &H, 1, 0);
        else {
            send(c_info->sock, (void*) &W, 1, 0);
            
            pthread_mutex_lock(&m);
            game_finished = 1;
            winner_thread = c_info->client_id;
            winner_thread_tries = tries;
            pthread_cond_broadcast(&cnd);
            pthread_mutex_unlock(&m);
        }
        tries++;
    }

    return NULL;
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
    server.sin_port = htons(SERVER_PORT);
    server.sin_family = AF_INET;
    server.sin_addr.s_addr = INADDR_ANY;

    if(bind(sock, (struct sockaddr *) &server, sizeof(server)) < 0) {
        perror("Bind error!");
        printf("Error code %d\n", errno);
        exit(2);
    }
    listen(sock, 2);

    struct sockaddr_in client;
    ssize_t client_size = sizeof(client);
    memset(&client, 0, client_size);

    reset_thread = pthread_create(&reset_thread, NULL, reset_game, NULL);

    number = rand() % (rand_max - rand_min + 1) + rand_min;
    client_idx = 0;
    t = (pthread_t*) malloc(50*sizeof(pthread_t));
    clients = (struct client_info*) malloc(50*sizeof(struct client_info));
    printf("Game strated! The number is %d!\n", number);
    while(1) {
        int conn_sock = accept(sock, (struct sockaddr*) &client, (void*) &client_size);
        if(conn_sock < 0) {
            perror("Accept error!");
            printf("Error code %d\n", errno);
            continue;
        }

        clients[client_idx].client = client;
        clients[client_idx].sock = conn_sock;
        clients[client_idx].client_id = client_idx;
        t[client_idx] = pthread_create(&t[client_idx], NULL, worker, (void*) &clients[client_idx]);
        client_idx++;
    }

    return 0;
}