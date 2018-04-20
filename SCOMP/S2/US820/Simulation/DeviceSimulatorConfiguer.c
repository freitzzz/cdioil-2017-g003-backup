#include "DeviceSimulatorConfiguer.h"
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>

/*Configures a certain DeviceSimulatorStruct according to a configuration file*/
int configureDeviceSimulator(DeviceSimulatorStruct* simulator,char* configFile){
    int configFileLines=0;
    char** configFileContent=readAllLines(configFile,&configFileLines);
    if(configFileContent==NULL||!configFileLines)return INVALID;
    int i;
    int* reviewQuantity=NULL;
    int* reviewSendTime=NULL;
    int* deviceNumber=NULL;
    int* randomQuantity=NULL;
    int* randomSendTime=NULL;
    for(i=0;i<configFileLines;i++){
        char* nextLine=configFileContent[i];
        if(!startsWith(nextLine,IGNORE_LINE)){
            if(reviewQuantity==NULL && startsWith(nextLine,DEVICE_CONFIGURATION_REVIEW_QUANTITY_IDENTIFIER)){
                reviewQuantity=getIdentifierValue(nextLine,DEVICE_CONFIGURATION_REVIEW_QUANTITY_IDENTIFIER);
                if(reviewQuantity==NULL)return INVALID;
                simulator->reviewQuantity= *reviewQuantity;

            }else if(reviewSendTime==NULL && startsWith(nextLine,DEVICE_CONFIGURATION_REVIEW_SEND_TIME_IDENTIFIER)){
                reviewSendTime=getIdentifierValue(nextLine,DEVICE_CONFIGURATION_REVIEW_SEND_TIME_IDENTIFIER);
                if(reviewSendTime==NULL)return INVALID;
                simulator->reviewSendTime= *reviewSendTime;

            }else if(deviceNumber==NULL && startsWith(nextLine,DEVICE_CONFIGURATION_NUMBER_OF_DEVICES_IDENTIFIER)){
                deviceNumber=getIdentifierValue(nextLine,DEVICE_CONFIGURATION_NUMBER_OF_DEVICES_IDENTIFIER);
                if(deviceNumber==NULL)return INVALID;
                simulator->deviceNumber= *deviceNumber;

            }else if(randomQuantity==NULL && startsWith(nextLine,DEVICE_CONFIGURATION_RANDOM_REVIEW_QUANTITY_IDENTIFIER)){
                randomQuantity=getIdentifierValue(nextLine,DEVICE_CONFIGURATION_RANDOM_REVIEW_QUANTITY_IDENTIFIER);
                short randomQuantityX= (short)*randomQuantity;
                if(randomQuantity==NULL|| randomQuantityX>TRUE)return INVALID;
                simulator->randomQuantity= randomQuantityX;
                
            }else if(randomSendTime==NULL && startsWith(nextLine,DEVICE_CONFIGURATION_RANDOM_REVIEW_SEND_TIME_IDENTIFIER)){
                randomSendTime=getIdentifierValue(nextLine,DEVICE_CONFIGURATION_RANDOM_REVIEW_SEND_TIME_IDENTIFIER);
                short randomSendTimeX= (short)*randomSendTime;
                if(randomSendTime==NULL || randomSendTimeX>TRUE)return INVALID;
                simulator->randomSendTime= randomSendTimeX;
            }
        }
    }
    return (reviewQuantity!=NULL && reviewSendTime!=NULL && deviceNumber!=NULL 
            && randomQuantity!=NULL && randomSendTime!=NULL) ? VALID : INVALID;
}