#include "Sockets.h"
// read a string from stdin protecting buffer overflow
#define GETS(B, S){fgets(B, S - 2, stdin); B[strlen(B) - 1] = 0;}


int main(int argc, char **argv)
{
    int err, sock;
    unsigned long f, i, n, num;
    unsigned char bt;
    char linha[BUF_SIZE];
    struct addrinfo req, *list;

    if (argc != 2)
    {
        puts("Server's IPv4/IPv6 address or DNS name is required as argument");
        exit(1);
    }
    bzero((char *)&req, sizeof(req));
    // let getaddrinfo set the family depending on the supplied server address
    req.ai_family = AF_UNSPEC;
    req.ai_socktype = SOCK_STREAM;
    err = getaddrinfo(argv[1], SERVER_PORT, &req, &list);
    if (err)
    {
        printf("Failed to get server address, error: %s\n", gai_strerror(err));
        exit(1);
    }
    sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);
    if (sock == -1)
    {
        perror("Failed to open socket");
        freeaddrinfo(list);
        exit(1);
    }
    if (connect(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen) == -1)
    {
        perror("Failed connect");
        freeaddrinfo(list);
        close(sock);
        exit(1);
    }

    do
    {
        do
        {
            printf("Enter a positive integer to SUM (zero to terminate): ");
            GETS(linha, BUF_SIZE);
            while (sscanf(linha, "%li", &num) != 1 || num < 0)
            {
                puts("Invalid number");
                GETS(linha, BUF_SIZE);
            }
            n = num;
            for (i = 0; i < 4; i++)
            {
                bt = n % 256;
                write(sock, &bt, 1);
                n = n / 256;
            }
        } while (num);
        num = 0;
        f = 1;
        for (i = 0; i < 4; i++)
        {
            read(sock, &bt, 1);
            num = num + bt * f;
            f = f * 256;
        }
        printf("SUM RESULT=%lu\n", num);
    } while (num);
    close(sock);
    exit(0);
}

void connect_tcp(char* tcpConnectionIP){
    struct addrinfo req, *list;
    bzero((char *)&req, sizeof(req)); //Limpa o lixo da estrutura
    req.ai_family = AF_UNSPEC; //Define a familia da ligação
    req.ai_socktype = SOCK_STREAM; //Define o tipo de sockets a ser usado
    int success=getaddrinfo(tcpConnectionIP, SERVER_PORT, &req, &list); //Preence a estrutura com a informação necessar
    int sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);// Cria um socket respectivo a ser usado na ligacao
    int connectionStatus=connect(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen); //Cria uma ligação TCP a ser lido num certo IP previamento definido
    close(sock); //Fecha a ligação com o socket    
}

/*Sends content to a TCP socket*/
void send_tcp_socket(int socketFileDescriptor,void* buffer,int bufferSize){
    write(socketFileDescriptor,buffer,bufferSize);
}
/*Recieves content from a TCP socket*/
void receive_tcp_sockect (int socketFileDescriptor,void* buffer, int bufferSize){
    read(socketFileDescriptor,buffer,bufferSize);
}