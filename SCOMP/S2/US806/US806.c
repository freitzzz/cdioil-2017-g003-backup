#include "SharedZone.h"
#include "Stoplight.h"

/*
 * Estrutura usada para definir uma avaliação.
 */
typedef struct
{
	char nomeProduto[20];
	int shm_flag;
	int valor;
	int id;
	int flagX;
} Avaliacao;

/*
 * Matriz de produtos.
 */
char produtos[][20] = {
	"Iogurte Continente",
	"Vinho Verde",
	"Massa Esparguete",
	"Agua 50cl",
	"Lápis",
	"Livro",
	"Pao",
	"Cereais",
	"Leite"};

/*
 * Gera um nome de produto aleatório.
 */
char *produtoAleatorio()
{
	int idx = rand() % 5;

	return produtos[idx];
}

/*
 * Cria n (size) processos filho, retornando o índice do filho.
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

/*
 * Sprint II US-805
 *
 * Receção concorrente da avaliação de produtos através de memória partilhada.
 */
int main()
{

	int nMaquinas, tempoEntreAvaliacoes;
	char sucesso[] = "Recebido com sucesso!";
	char erro[] = "Erro na receção de dados!";

	printf("========================\n");
	printf("Feedback Monkey\n");
	printf("========================\n");

	printf("Quantas máquinas existem?\n");
	scanf("%d", &nMaquinas);

	if (nMaquinas < 1)
	{
		printf("Numero inválido.\n");
		return -1;
	}

	int sharedFileDescriptor = -1; //File descriptor used for the Shared Memory
	sem_t *semaphoreParentFileDescriptor;
	sem_t *semaphoreChildFileDescriptor;
	sem_t *semaphorePrintFileDescriptor;
	//*semaphoreParentFileDescriptor = 0;	 //File descriptor used for the Semaphore
	int shm_size = sizeof(Avaliacao); // Retirar o *nMaquinas fica so 1
	// Criação da zona de memória partilhada
	sharedFileDescriptor = shm_open(SHARED_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);								   //Opens the Shared Memory Zone
	semaphoreParentFileDescriptor = sem_open(PARENT_SEMAPHORE_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, SEMAPHORE_PARENT_INTIAL_VALUE);
	semaphoreChildFileDescriptor = sem_open(CHILD_SEMAPHORE_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, SEMAPHORE_CHILD_INTIAL_VALUE); //Opens the Semaphore Zone
	semaphorePrintFileDescriptor = sem_open(PRINT_SEMAPHORE_NAME, O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR, SEMAPHORE_PRINT_INTIAL_VALUE); //Opens the Semaphore Zone
	ftruncate(sharedFileDescriptor, shm_size);
	Avaliacao *a1;
	a1 = (Avaliacao *)mmap(NULL, shm_size, PROT_READ | PROT_WRITE, MAP_SHARED, sharedFileDescriptor, 0);

	int x = criarFilhos(nMaquinas); // Criação dos processos filhos

	if (x == -1)
	{ //Processo Pai
		while (1)
		{
			sem_post(semaphoreParentFileDescriptor); //UP DIZ AO FILHO QUE PODE ENVIAR
			sem_wait(semaphoreChildFileDescriptor); //DOWN ESPERA QUE O FILHO DIGA AO PAI QUE PODE IMPRIMIR
			// Imprime avaliação no ecrã
			printf("\n==================== Processo Pai ====================\n");
			printf("Nova avaliação registada.\n");
			printf("Máquina nº %d\n", a1->id);
			printf("Nome Produto: %s\n", a1->nomeProduto);
			printf("Avaliação: %d\n", a1->valor);
			sem_post(semaphorePrintFileDescriptor); //Ativa a impressao do filho
		}
	}
	else
	{ // Processos filho
		time_t t;
		srand((unsigned)time(&t) + getpid());
		while (1)
		{
			// Gera um valor de intervalo entre avaliações automático
			tempoEntreAvaliacoes = (rand() % 5) + 1;
			sleep(tempoEntreAvaliacoes);	   // Adormece um tempo (valor gerado)
			sem_wait(semaphoreParentFileDescriptor); //Down ESPERA PELO PAI ATE PODER ARRANCAR
			// Cria uma avaliação aleatória de um produto
			strcpy(a1->nomeProduto, produtoAleatorio());
			a1->valor = rand() % 11; // Avaliação entre 0 e 10 (inclusive)
			a1->id = x;				 // ID da avaliação correspondente ao ID do processo filho
			a1->shm_flag = 1;
			//leitura da mensagem de sucesso aqui
			sem_post(semaphoreChildFileDescriptor); //Diz ao pai que pode IMPRIMIR
			sem_wait(semaphorePrintFileDescriptor); //Ativado quando pode ser imprimido
			printf("\n=================== Processo Filho %d (%d) ===================\n", x, getpid());
			//Verifica se a mensagem a1 corresponde ao esperado.
			printf("%s\n", sucesso);
		}
	}

	return 0;
}