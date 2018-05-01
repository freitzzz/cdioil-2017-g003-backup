/*Generator Include Header*/
#include "AuthKeyGenerator.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <openssl/sha.h>

#define BUFFER_SIZE 100

char* generate(){
    char macAndFakeMSN[BUFFER_SIZE];
    sprintf(macAndFakeMSN,"%s\n%lu",get_mac_address(),getFakeMSN());
    printf("%s\n",macAndFakeMSN);
    SHA512_CTX sha512;
    unsigned char hash[SHA512_DIGEST_LENGTH];
    unsigned char* realHash=malloc(SHA512_DIGEST_LENGTH);
    SHA512_Init(&sha512);
    SHA512_Update(&sha512,macAndFakeMSN,strlen(macAndFakeMSN));
    SHA512_Final(hash,&sha512);
    int i;
    for(i=0;i<SHA512_DIGEST_LENGTH;i++)sprintf(realHash+(i*2),"%02x",hash[i]);
    return realHash;
}


int main(){
    printf("%s\n",generate());
    return 0;
}