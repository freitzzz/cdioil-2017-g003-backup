#include "Sockets.h"
#include "Utils/FileReader.h"
#include "Utils/StringUtils.h"
#include "AuthGenerator/AuthKeyGenerator.h"

/*Constant that represents the valid key code sent to machines that are not allowed to send reviews*/
#define VALID_KEY_CODE 200
/*Constant that represents the invalid key code sent to machines that are not allowed to send reviews*/
#define INVALID_KEY_CODE 400

/*Function that checks if a certain authentication key is valid*/
/*Returns 1 if authentication key is valid, false if not*/
int isAuthKeyAllowed(char** authenticationKeys,char* authenticationKey,int authenticationKeysTotal){
    int i;
    for(i=0;i<authenticationKeysTotal;i++)
        if(startsWith(authenticationKey,authenticationKeys[i]))return 1;
    return 0;
}


/*Runs the Server*/
/*Recieves through parameter the name of the file with the machines authentication keys*/
int main(int argc,char *argv[]){
    if(argc!=2){
        printf("The server needs an configuration file with the machines authentication keys\nPlease run the server with the file name passed through parameters\n");
        return 0;
    }
    int authenticationKeysTotal=0;
    char** authenticationKeys=readAllLines(argv[1],&authenticationKeysTotal);
    struct addrinfo req, *list;
    struct sockaddr_storage from;
    char clientIP[BUF_SIZE];
    char clientPort[BUF_SIZE];
    char receivedCode[BUF_SIZE];
    bzero((char *)&req, sizeof(req)); //Limpa o lixo da estrutura
    req.ai_family = AF_UNSPEC; //Define a familia da ligação
    req.ai_socktype = SOCK_STREAM; //Define o tipo de sockets a ser usado
    req.ai_flags = AI_PASSIVE; // local address

    int failure=getaddrinfo(NULL, SERVER_PORT, &req, &list); //Preence a estrutura com a informação necessar
    if(failure)return 0;
    
    int sock = socket(list->ai_family, list->ai_socktype, list->ai_protocol);// Cria um socket respectivo a ser usado na ligacao
    if(sock==-1)return 0;
    
    int binded=bind(sock, (struct sockaddr *)list->ai_addr, list->ai_addrlen);
    if(binded==-1)return 0;
    
    freeaddrinfo(list);
    listen(sock, SOMAXCONN);
    
    unsigned int adl=sizeof(from);
    short catched=0;
    
    while(!catched){
        int newSock = accept(sock, (struct sockaddr *)&from, &adl);
        if(!fork()){
            close(sock);
            getnameinfo((struct sockaddr *)&from, adl, clientIP, BUF_SIZE,
                            clientPort, BUF_SIZE, NI_NUMERICHOST | NI_NUMERICSERV);
            read(newSock,receivedCode,BUF_SIZE);
            int code= isAuthKeyAllowed(authenticationKeys,receivedCode,authenticationKeysTotal) ? VALID_KEY_CODE : INVALID_KEY_CODE;
            write(newSock,&code,sizeof(code));
            close(newSock);
            exit(5);
        }
        close(newSock);
    }
    return 0;
}