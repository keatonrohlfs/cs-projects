// Keaton Rohlfs
// CWID: 11893990
// CS 300-001
// Project 3
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include "mytime.h"

typedef struct {
    int tid;
    int ticket;
} lunch;

pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
sem_t ready_to_serve[50];
sem_t waiting_customers;
int current_ticket = 1;
int now_serving = 0;

void lunch_init(lunch *l) {
    l->tid = -1;
    l->ticket = -1;
}

void Show_serving(int number) {
    printf("Serving %d\n", number);
}

// int num_servers = 0;

int lunch_get_ticket(lunch *l) {
    pthread_mutex_lock(&mutex);
    int ticket = current_ticket++;
    l->ticket = ticket;
    pthread_mutex_unlock(&mutex);

    printf("<%lu Customer> enter lunch_get_ticket\n", pthread_self());
    printf("<%lu Customer> get ticket %d\n", pthread_self(), ticket);
    printf("<%lu Customer> leave lunch_get_ticket\n", pthread_self());

    return ticket;
}

void lunch_wait_turn(lunch *l) {
    printf("<%lu Customer> enter lunch_wait_turn with %d\n", pthread_self(), l->ticket);

    sem_post(&waiting_customers);

    sem_wait(&ready_to_serve[l->ticket]);

    printf("<%lu Customer> leave lunch_wait_turn after ticket %d served\n", pthread_self(), l->ticket);
}

// void lunch_wait_customer(lunch *l) {
//     printf("<%lu Server> enter lunch_wait_customer\n", pthread_self());

//     pthread_mutex_lock(&mutex);
//     now_serving++;
//     Show_serving(now_serving);
//     pthread_mutex_unlock(&mutex);

//     sem_post(&semaphore);

//     printf("<%lu Server> after served ticket %d\n", pthread_self(), now_serving);
//     printf("<%lu Server> leave lunch_wait_customer\n", pthread_self());
// }

void lunch_wait_customer(lunch *l) {
    printf("<%lu Server> enter lunch_wait_customer\n", pthread_self());

    sem_wait(&waiting_customers);
    pthread_mutex_lock(&mutex);
    now_serving++;
    Show_serving(now_serving);
    pthread_mutex_unlock(&mutex);

    sem_post(&ready_to_serve[now_serving]);

    printf("<%lu Server> after served ticket %d\n", pthread_self(), now_serving);
    printf("<%lu Server> leave lunch_wait_customer\n", pthread_self());
}

void *customer(void *arg) {
    lunch *l = (lunch *)arg;
    int ticket = lunch_get_ticket(l);
    int sleep_time = mytime(0, 3);
    printf("Sleeping Time: %d sec; Thread Id = %lu\n", sleep_time, pthread_self());
    sleep(sleep_time);
    lunch_wait_turn(l);
    return NULL;
}

void *server(void *arg) {
    lunch *l = (lunch *)arg;
    int sleep_time = mytime(0, 3);
    printf("Sleeping Time: %d sec; Thread Id = %lu\n", sleep_time, pthread_self());
    sleep(sleep_time);
    lunch_wait_customer(l);
    return NULL;
}

int main(int argc, char *argv[]) {
    if (argc != 3) {
        printf("Usage: %s <number_of_servers> <number_of_customers>\n", argv[0]);
        exit(1);
    }

    int num_servers = atoi(argv[1]);
    int num_customers = atoi(argv[2]);

    pthread_t customer_threads[num_customers];
    pthread_t server_threads[num_servers];
    sem_init(&waiting_customers, 0, 0);
    lunch customer_data[num_customers];
    lunch server_data[num_servers];

    int p;
    for (p = 0; p < num_customers; p++) {
        sem_init(&ready_to_serve[p], 0, 0);
    }

    int i;
    for (i = 0; i < num_customers; i++) {
        lunch_init(&customer_data[i]);
        customer_data[i].tid = i + 1;
        pthread_create(&customer_threads[i], NULL, customer, &customer_data[i]);
    }

    int j;
    for (j = 0; j < num_servers; j++) {
        lunch_init(&server_data[j]);
        server_data[j].tid = num_customers + j + 1;
        pthread_create(&server_threads[j], NULL, server, &server_data[j]);
    }

    int k;
    for (k = 0; k < num_customers; k++) {
        pthread_join(customer_threads[k], NULL);
    }

    int l;
    for (l = 0; l < num_servers; l++) {
        pthread_join(server_threads[l], NULL);
    }

    return 0;
}
