/*Includes the structure needed to execute the simulation*/
#include "DeviceSimulator.h"
/*Includes the configuer needed to configure the simulation */
#include "DeviceSimulatorConfiguer.h"


/*Prints the help parameter content*/
void printHelpParameter(){printf("%s\n",HELP_PARAMETER_CONTENT);}

/*Prints the invalid parameter content*/
void printInvalidParameter(){printf("%s\n",INVALID_PARAMETER_CONTENT);}

/*Prints the Invalid Configuration message*/
void printInvalidConfigurationFile(){printf("%s\n",INVALID_CONFIGURATION_FILE_MESSAGE);}

/*Checks if a certain parameter is the help parameter*/
int isHelpParameter(char *parameter){return strcmp(HELP_PARAMETER,parameter);}

/*Starts the simulation with the simulation configuration detailed on a certain configuration file*/
void startSimulation(char *fileName){
    DeviceSimulator simulator;
    int validConfiguration=configureDeviceSimulator(simulator,fileName);
    if(validConfiguration){
        //Execute
    }else{
        printInvalidConfigurationFile();
    }
}

/*Executes a certain Device Simulation*/
/*Execute with -h parameters for help*/
int main(int argc,char* argv[]){
    if(argc==0 || argc>NUMBER_OF_POSSIBLE_PARAMETERS){
        printInvalidParameter();
        return 5;
    }else if(argc==1){
        if(isHelpParameter(argv[0]))printHelpParameter();
        printInvalidParameter();
        return 5;
    }else{

    }
}
