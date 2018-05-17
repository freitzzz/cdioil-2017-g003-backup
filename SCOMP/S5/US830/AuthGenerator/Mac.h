#include <sys/socket.h>
#include <sys/ioctl.h>
#include <linux/if.h>
#include <netdb.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

/*Mac Address Name*/
#define MAC_ADDRESS_NAME "eth0"
/*Mac Address Size*/
#define MAC_ADDRESS_SIZE 17
/*Mac Address Separators (-|:)*/
#define MAC_ADDRESS_SEPARATORS 5
/*Mac Address format used in sprintf*/
#define MAC_ADDRESS_FORMAT "%02x-%02x-%02x-%02x-%02x-%02x"

/*Gets current computer Mac Address, NULL if an error ocured*/
/*Inspired from https://stackoverflow.com/a/24387019*/
char* get_mac_address();