#include "DeviceSimulatorConfiguer.h"

/*Configures a certain DeviceSimulator according to a configuration file*/
int configureDeviceSimulator(DeviceSimulator simulator,char* configFile){
    int configFileLines=0;
    char** configFileContent=readAllLines(configFile,&configFileLines);
    if(configFileContent==NULL||!configFileLines)return INVALID;
    int i;
    int* reviewQuantity=NULL;
    int* reviewSendTime=NULL;
    for(i=0;i<configFileLines;i++){
        char* nextLine=configFileContent[i];
        if(!startsWith(nextLine,IGNORE_LINE)){
            if(reviewQuantity==NULL && startsWith(nextLine,DEVICE_CONFIGURATION_REVIEW_QUANTITY_IDENTIFIER)){
                reviewQuantity=getIdentifierValue(nextLine,DEVICE_CONFIGURATION_REVIEW_QUANTITY_IDENTIFIER);
                if(reviewQuantity==NULL)return INVALID;
                simulator.reviewQuantity= *reviewQuantity;
            }else if(reviewSendTime==NULL && startsWith(nextLine,DEVICE_CONFIGURATION_REVIEW_SEND_TIME_IDENTIFIER)){
                reviewSendTime=getIdentifierValue(nextLine,DEVICE_CONFIGURATION_REVIEW_SEND_TIME_IDENTIFIER);
                if(reviewSendTime==NULL)return INVALID;
                simulator.reviewSendTime= *reviewSendTime;
            }
        }
    }
    return VALID;  
}