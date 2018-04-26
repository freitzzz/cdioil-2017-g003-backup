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
/*Destroys a certain semaphore*/
void destroySemaphore(char *semaphoreName){
	sem_unlink(semaphoreName);
}

/*Executes the semaphore simulation*/
/* Currently uses four semaphores in order to send reviews atomically through a shared memory zone
 * - One for the father to communicate with the childs telling them that they can send a review
 * - One for the childs to comunicate with the parent telling him that a review was sent
 * - One for the parent to communicate with the childs telling them that the review was received with success
 * - One for the childs to communicate with the parent telling him that the success message was printed 
 *   (This one is also used for the father to know that the childs simulation ended, in order to end the simulations)
 * <br><b>Nota:</b> Note: Since we are using only one receipt (father) the simulation only sends one review per 
 * time to the father
 */
void executeSemaphoreSimulation(DeviceSimulatorStruct simulator){
	int deviceNumber,reviewQuantity,reviewSendTime;
	short randomQuantity,randomSendTime;
    deviceNumber=simulator.deviceNumber;
    reviewQuantity=simulator.reviewQuantity;
    reviewSendTime=simulator.reviewSendTime;
	randomQuantity=simulator.randomQuantity;
	randomSendTime=simulator.randomSendTime;
	int sharedFileDescriptor = -1;
	int shm_size = sizeof(Avaliacao);
	sharedFileDescriptor = shm_open(SHARED_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);								   //Opens the Shared Memory Zone
	sem_t *semaphoreParentFileDescriptor;
	sem_t *semaphoreChildFileDescriptor;
	sem_t *semaphorePrintFileDescriptor;
	sem_t *semaphorePrintedFileDescriptor;
	semaphoreParentFileDescriptor = sem_open(PARENT_SEMAPHORE_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, SEMAPHORE_PARENT_INTIAL_VALUE);
	semaphoreChildFileDescriptor = sem_open(CHILD_SEMAPHORE_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, SEMAPHORE_CHILD_INTIAL_VALUE); //Opens the Semaphore Zone
	semaphorePrintFileDescriptor = sem_open(PRINT_SEMAPHORE_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, SEMAPHORE_PRINT_INTIAL_VALUE); //Opens the Semaphore Zone
	semaphorePrintedFileDescriptor=sem_open(CHILDS_PRINTED_SEMAPHORE_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, SEMAPHORE_PRINT_INTIAL_VALUE);
	ftruncate(sharedFileDescriptor, shm_size);
	Avaliacao *review;
	review = (Avaliacao *)mmap(NULL, shm_size, PROT_READ | PROT_WRITE, MAP_SHARED, sharedFileDescriptor, 0);
	int x = criarFilhos(deviceNumber); // Criação dos processos filhos
	int catched=0;
	if (x == -1)
	{ //Processo Pai
		review->deadDevices=0;
		while (!catched)
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
			sem_wait(semaphorePrintedFileDescriptor);
			if(review->deadDevices==deviceNumber)catched=1;
		}
		munmap(review,shm_size);
		close(sharedFileDescriptor);
		shm_unlink(SHARED_NAME);
		destroySemaphore(PARENT_SEMAPHORE_NAME);
		destroySemaphore(CHILD_SEMAPHORE_NAME);
		destroySemaphore(PRINT_SEMAPHORE_NAME);
		destroySemaphore(CHILDS_PRINTED_SEMAPHORE_NAME);
	}
	else
	{
		time_t t;
		srand((unsigned)&t+getpid());
		if(randomQuantity)reviewQuantity=(rand()%reviewQuantity)+1;
		while (!catched)
		{	if(randomSendTime)reviewSendTime=(rand()%simulator.reviewSendTime)+1;
			sleep(reviewSendTime);
			sem_wait(semaphoreParentFileDescriptor);
			strcpy(review->nomeProduto, produtoAleatorio());
			review->valor = rand() % 11; 
			review->id = x;
			reviewQuantity--;
			sem_post(semaphoreChildFileDescriptor);
			sem_wait(semaphorePrintFileDescriptor);
			printf("\n=================== Processo Filho %d (%d) ===================\n", x, getpid());
			//Verifica se a mensagem review corresponde ao esperado.
			printf("%s\n",SUCCESS_MESSAGE);
			if(reviewQuantity==0){
				review->deadDevices++;
				catched=1;
			}
			sem_post(semaphorePrintedFileDescriptor);
		}
		exit(5);
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