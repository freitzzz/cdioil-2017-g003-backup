#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#define BUF_SIZE 300
#define BCAST_ADDRESS "255.255.255.255"
#define UDP_PORT "9998"
#define TCP_PORT "9999"