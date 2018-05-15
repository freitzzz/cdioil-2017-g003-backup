#include "Sockets.h"
int main(void)
{
    struct sockaddr_storage from;
    int err, newSock, sock;
    unsigned int adl;
    unsigned long i, f, n, num, sum;
    unsigned char bt;
    char cliIPtext[BUF_SIZE], cliPortText[BUF_SIZE];
    struct addrinfo req, *list;
    bzero((char *)&req, sizeof(req));
    // requesting a IPv6 local address will allow both IPv4 and IPv6 clients to use it
    req.ai_family = AF_INET6;		//Socket using IPv6
    req.ai_socktype = SOCK_STREAM;	//TCP socket
    req.ai_flags = AI_PASSIVE;		//local address
    err = getaddrinfo(NULL, SERVER_PORT, &req, &list);
    if (err)
    {
        printf("Failed to get local address, error: %s\n", gai_strerror(err));
        exit(1);
    }
    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1)
    {
        perror("Failed to open socket");
        freeaddrinfo(list);
        exit(1);
    }
    if (bind(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen) == -1)
    {
        perror("Bind failed");
        close(sock);
        freeaddrinfo(list);
        exit(1);
    }
    freeaddrinfo(list);
    listen(sock, SOMAXCONN);
    puts("Accepting TCP connections (IPv6/IPv4). Use CTRL+C to terminate the server");
    adl = sizeof(from);
    while (1)
    {
        newSock = accept(sock, (struct sockaddr *)&from, &adl); //Aceita conexoes TCP
        if (!fork()) //Cria um processo filho para tratar do conhecimento do conteudo do socket
        {
            close(sock); //Fecha o socket recebido
            getnameinfo((struct sockaddr *)&from, adl, cliIPtext, BUF_SIZE,
                        cliPortText, BUF_SIZE, NI_NUMERICHOST | NI_NUMERICSERV);
            printf("New connection from %s, port number %s\n", cliIPtext, cliPortText);
            do
            {
                sum = 0;
                do
                {
                    num = 0;
                    f = 1;
                    for (i = 0; i < 4; i++)
                    {
                        read(newSock, &bt, 1);
                        num = num + bt * f;
                        f = 256 * f;
                    }
                    sum = sum + num;
                } while (num);
                n = sum;
                for (i = 0; i < 4; i++)
                {
                    bt = n % 256;
                    write(newSock, &bt, 1);
                    n = n / 256;
                }
            } while (sum);
            close(newSock);
            printf("Connection %s, port number %s closed\n", cliIPtext, cliPortText);
            exit(0);
        }
        close(newSock);
    }
    close(sock);
}