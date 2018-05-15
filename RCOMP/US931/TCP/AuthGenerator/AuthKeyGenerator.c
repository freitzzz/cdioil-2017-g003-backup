/*Generator Include Header*/
#include "AuthKeyGenerator.h"
#include "Mac.h"
#include "MSN.h"
#include "StringUtils.h"
#include <time.h>

/*Hash Buffer Size*/
#define BUFFER_SIZE 100
/*Mac Address and MSN concatenation format for sprintf*/
#define MAC_MSN_FORMAT "%s\n%lu"
/*Hash Concatenation format for sprintf*/
#define HASH_FORMAT "%02x"

/* Generates a Authentication Key based on the computer Mac Address & MSN
 * <br>Uses SHA-512 for hash generation
 * <br>Inspired from https://stackoverflow.com/a/2458382
 */
unsigned char* generate(){
    char macAndFakeMSN[BUFFER_SIZE];
    sprintf(macAndFakeMSN,MAC_MSN_FORMAT,get_mac_address(),getFakeMSN());
    SHA512_CTX sha512;
    unsigned char hash[SHA512_DIGEST_LENGTH];
    unsigned char* realHash=malloc(SHA512_DIGEST_LENGTH);
    SHA512_Init(&sha512);
    SHA512_Update(&sha512,macAndFakeMSN,strlen(macAndFakeMSN));
    SHA512_Final(hash,&sha512);
    int i;
    char *timestamp =  utoa((unsigned)time(NULL)); 
    for(i=0;i<SHA512_DIGEST_LENGTH;i++)sprintf((char*)realHash+(i<<1),HASH_FORMAT,hash[i]);
    return realHash;
}
