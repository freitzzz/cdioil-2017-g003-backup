/*Struct that defines the properties of a Device Simulation*/
typedef struct{
    int reviewSendTime;
    int reviewQuantity;
    int deviceNumber;
}DeviceSimulator;

/*Constant that represents the name of the Help Parameter*/
#define HELP_PARAMETER "-h"

/*Constant that represents the content that shows if the help parameter is passed on execution of the Device Simulation*/
#define HELP_PARAMETER_CONTENT "Welcome to the Device Simulation\nYou can execute the simulation by passing as argument the name of the file with the device simulation configuration file"
/*Constant that represents the content that shows if trying to run with invalid parameters*/
#define INVALID_PARAMETER_CONTENT "Execute the simulation with -h parameter in order to know how to run the simulation"

/*Constant that represents the message that shows if trying to run the simulation with an invalid configuration file*/
#define INVALID_CONFIGURATION_FILE_MESSAGE "Invalid Configuration file!\n Please check your local configuration example file"
/*Constant that represents the number of possible parameters that can be passed on the execution of a Device Simulation*/
#define NUMBER_OF_POSSIBLE_PARAMETERS 1

