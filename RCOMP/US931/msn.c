#include <stdio.h>
#include <cpuid.h>

/*Value for retrieving the machine serial number*/
#define MSN_EAX_VALUE 3

/*Gets a "fake" machine serial number since allowing his retrieval is considered a security flaw since the Pentinum III release*/
/*Inspired from https://stackoverflow.com/questions/6491566/getting-the-machine-serial-number-and-cpu-id-using-c-c-in-linux
*/
unsigned long getFakeMSN(){
    unsigned int level=0;
    unsigned int eax=MSN_EAX_VALUE;
    unsigned int ebx;
    unsigned int ecx;
    unsigned int edx;
    __get_cpuid(level,&eax,&ebx,&ecx,&edx);
    return eax+ebx+ecx+edx;
}

int main()
{
    printf("%lu\n",getFakeMSN());
    return 0;
}