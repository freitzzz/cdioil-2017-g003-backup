#include "Mac.h"

/*Gets current computer Mac Address, NULL if an error ocured*/
/*Inspired from https://stackoverflow.com/a/24387019*/
char* get_mac_address(){
    int fileDescriptor=socket(PF_INET, SOCK_DGRAM, IPPROTO_IP);
    struct ifreq freqStruct;
    strcpy(freqStruct.ifr_name,MAC_ADDRESS_NAME);
    if(ioctl(fileDescriptor,SIOCGIFHWADDR,&freqStruct)==0){
        char* macAddress=(char*)malloc(MAC_ADDRESS_SIZE);
        char * macAddressData=freqStruct.ifr_addr.sa_data;
        snprintf(macAddress,MAC_ADDRESS_SIZE,MAC_ADDRESS_FORMAT,macAddressData[0],macAddressData[1]
            ,macAddressData[2],macAddressData[3],macAddressData[4],macAddressData[5]);
        return macAddress;
    }else{
        return NULL;
    }
}
