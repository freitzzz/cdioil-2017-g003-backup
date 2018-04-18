#include "DeviceSimulatorConfiguer.h"
#include <stdio.h>

int main(int argc,char* argv[]){
    DeviceSimulator simulator;
    int validConfiguration=configureDeviceSimulator(simulator,"devices.config");
    printf("%d\n",validConfiguration);
    return 0;
}