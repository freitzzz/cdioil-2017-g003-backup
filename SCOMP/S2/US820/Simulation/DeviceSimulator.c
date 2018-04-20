/*Includes the configuer needed to configure the simulation */
#include "DeviceSimulatorConfiguer.h"
#include "Simulation.h"
#include "DeviceSimulator.h"
#include <stdio.h>
#include <sys/wait.h>
#include <sys/stat.h>

/*Prints the help parameter content*/
void printHelpParameter(){printf("%s\n",HELP_PARAMETER_CONTENT);}

/*Prints the invalid parameter content*/
void printInvalidParameter(){printf("%s\n",INVALID_PARAMETER_CONTENT);}

/*Prints the Invalid Configuration message*/
void printInvalidConfigurationFile(){printf("%s\n",INVALID_CONFIGURATION_FILE_MESSAGE);}

/*Checks if a certain parameter is the help parameter*/
int isHelpParameter(char *parameter){return strcmp(HELP_PARAMETER,parameter)==0;}

/*Clears the terminal screen*/
void clearScreen(){
    if(fork()==0){
        execlp("clear","clear",NULL);
    }else{
        wait(NULL);
    }
}
/*Starts the simulation with the simulation configuration detailed on a certain configuration file*/
void startSimulation(char *fileName){
    DeviceSimulatorStruct simulator;
    DeviceSimulatorStruct* simulatorPointed=&simulator;
    int validConfiguration=configureDeviceSimulator(simulatorPointed,fileName);
    if(validConfiguration){
        simulate(simulator);
    }else{
        printInvalidConfigurationFile();
    }
}

/*Executes a certain Device Simulation*/
/*Execute with -h parameters for help*/
int main(int argc,char* argv[]){
    clearScreen();
    if(argc==1 || argc>NUMBER_OF_POSSIBLE_PARAMETERS+1){
        printInvalidParameter();
        return 0;
    }else if(argc==2){
        if(isHelpParameter(argv[0])){
            printHelpParameter();
            return 0;
        }
        startSimulation(argv[1]);
    }else{
        startSimulation(argv[1]);
    }
    return 0;
}
