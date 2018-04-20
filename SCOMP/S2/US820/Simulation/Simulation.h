#ifndef SIMULATION
#define SIMULATION
/*Include Header file for the DeviceSimulatorStruct struct*/
#include "DeviceSimulator.h"

/*Constant that represents the numbe of products available to be reviewed*/
#define NUMBER_OF_PRODUCTS_AVAILABLE 9

/*Constant that represents the message shown if the childs receive*/
#define SUCCESS_MESSAGE "Recebido com sucesso!"

#define SIMULATION_HEADER "========================\nFeedback Monkey\n========================"

/*
 * Struct used to define an avaliation
 */
typedef struct
{
	char nomeProduto[20];
	int valor;
	int id;
	int deadDevices;
} Avaliacao;



/*Executes a simulation based on a certain Simulation Configuration described on a DeviceSimulatorStruct struct*/
void simulate(DeviceSimulatorStruct simulator);

#endif