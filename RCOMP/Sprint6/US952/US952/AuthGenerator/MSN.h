#include <stdio.h>
#include <cpuid.h>

/*Value for retrieving the machine serial number*/
#define MSN_EAX_VALUE 3

/*Gets a "fake" machine serial number since allowing his retrieval is considered a security flaw since the Pentinum III release*/
/*Inspired from https://stackoverflow.com/questions/6491566/getting-the-machine-serial-number-and-cpu-id-using-c-c-in-linux*/
unsigned long getFakeMSN();