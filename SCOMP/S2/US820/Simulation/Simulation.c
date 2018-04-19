/*Include Header file for the DeviceSimulatorStruct struct*/
#include "Simulation.h"

#include "SharedZone.h"
/*Include Header file for the Semaphore Zone configuration*/
#include "Stoplight.h"

/*
 * All products available to be reviewwd
 */
char produtos[][20] = {
	"Iogurte Continente",
	"Vinho Verde",
	"Massa Esparguete",
	"Agua 50cl",
	"Lapis",
	"Livro",
	"Pao",
	"Cereais",
	"Leite"
};

/*
 * Generates a random product
 */
char *produtoAleatorio()
{
	int idx = rand() % NUMBER_OF_PRODUCTS_AVAILABLE;
	return produtos[idx];
}

/*
 * Creates a certain number of child processes
 */
int criarFilhos(int size)
{
	int i = 0;
	printf("========================\n");
	printf("Máquinas criadas:\n");
	for (; i < size; i++)
	{
		pid_t pid = fork();
		if (pid == 0)
		{
			printf("Máquina %d\n", getpid());
			return i;
		}
	}
	return -1;
}
/*Checks if the device number is valid
  Returns 1 if the device number is valid, 0 if not*/
int checkDeviceNumber(int deviceNumber){return deviceNumber>0;}

/*Checks if the review quantity is valid
  Returns 1 if the review quantity is valid, 0 if not*/
int checkReviewQuantity(int reviewQuantity){return reviewQuantity>0;}

/*Checks if the review send time is valid
  Returns 1 if the review send time is valid, 0 if not*/
int checkReviewSendTime(int reviewSendTime){return reviewSendTime>0;}

/*Prints the the simulation header*/
void printSimulationHeader(){printf("%s\n",SIMULATION_HEADER);}

/*Executes the semaphore simulation*/
void executeSemaphoreSimulation(DeviceSimulatorStruct simulator){
	int deviceNumber,reviewQuantity,reviewSendTime;
    deviceNumber=simulator.deviceNumber;
    reviewQuantity=simulator.reviewQuantity;
    reviewSendTime=simulator.reviewSendTime;
	int sharedFileDescriptor = -1;
	int shm_size = sizeof(Avaliacao);
	sharedFileDescriptor = shm_open(SHARED_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);								   //Opens the Shared Memory Zone
	sem_t *semaphoreParentFileDescriptor;
	sem_t *semaphoreChildFileDescriptor;
	sem_t *semaphorePrintFileDescriptor;
	semaphoreParentFileDescriptor = sem_open(PARENT_SEMAPHORE_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, SEMAPHORE_PARENT_INTIAL_VALUE);
	semaphoreChildFileDescriptor = sem_open(CHILD_SEMAPHORE_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, SEMAPHORE_CHILD_INTIAL_VALUE); //Opens the Semaphore Zone
	semaphorePrintFileDescriptor = sem_open(PRINT_SEMAPHORE_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, SEMAPHORE_PRINT_INTIAL_VALUE); //Opens the Semaphore Zone
	ftruncate(sharedFileDescriptor, shm_size);
	Avaliacao *review;
	review = (Avaliacao *)mmap(NULL, shm_size, PROT_READ | PROT_WRITE, MAP_SHARED, sharedFileDescriptor, 0);
	int x = criarFilhos(deviceNumber); // Criação dos processos filhos
	printf("%d\n",x);
	if (x == -1)
	{ //Processo Pai
		while (1)
		{
			sem_post(semaphoreParentFileDescriptor); //UP DIZ AO FILHO QUE PODE ENVIAR
			sem_wait(semaphoreChildFileDescriptor); //DOWN ESPERA QUE O FILHO DIGA AO PAI QUE PODE IMPRIMIR
			// Imprime avaliação no ecrã
			printf("\n==================== Processo Pai ====================\n");
			printf("Nova avaliação registada.\n");
			printf("Máquina nº %d\n", review->id);
			printf("Nome Produto: %s\n", review->nomeProduto);
			printf("Avaliação: %d\n", review->valor);
			sem_post(semaphorePrintFileDescriptor); //Ativa a impressao do filho
		}
	}
	else
	{
		int catched=0;
		while (!catched)
		{
			sleep(reviewSendTime);
			sem_wait(semaphoreParentFileDescriptor);
			strcpy(review->nomeProduto, produtoAleatorio());
			review->valor = rand() % 11; 
			review->id = x;
			review->shm_flag = 1;
			sem_post(semaphoreChildFileDescriptor);
			sem_wait(semaphorePrintFileDescriptor);
			printf("\n=================== Processo Filho %d (%d) ===================\n", x, getpid());
			//Verifica se a mensagem review corresponde ao esperado.
			printf("%s\n",SUCCESS_MESSAGE);
			reviewQuantity--;
			if(reviewQuantity==0)catched=1;
		}
	}
}

/*Executes a simulation based on a certain Simulation Configuration described on a DeviceSimulatorStruct struct*/
void simulate(DeviceSimulatorStruct simulator){
    int deviceNumber,reviewQuantity,reviewSendTime;
    deviceNumber=simulator.deviceNumber;
    reviewQuantity=simulator.reviewQuantity;
    reviewSendTime=simulator.reviewSendTime;
	if(!checkDeviceNumber(deviceNumber) || !checkReviewSendTime(reviewSendTime) || !checkReviewQuantity(reviewQuantity))return;
	printSimulationHeader();
	executeSemaphoreSimulation(simulator);
}