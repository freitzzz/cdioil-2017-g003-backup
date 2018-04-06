#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <string.h>
#include <sys/stat.h> 
#include <unistd.h>
#include <sys/types.h>
#include <sys/mman.h>
#include <fcntl.h>

/*
 * Estrutura usada para definir uma avaliação.
 */
typedef struct {
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
		"Leite"
};

/*
 * Gera um nome de produto aleatório.
 */
char* produtoAleatorio() {
	int idx = rand() % 5;

	return produtos[idx];
}

/*
 * Cria n (size) processos filho, retornando o índice do filho.
 */
int criarFilhos(int size){
	int i = 0;
	printf("========================\n");
	printf("Máquinas criadas:\n");
	for (; i < size; i++) {
		pid_t pid = fork();
		if (pid == 0) {
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
int main(){
	
	int nMaquinas, tempoEntreAvaliacoes;
	char sucesso[] = "Recebido com sucesso!";
	char erro[] = "Erro na receção de dados!";

	printf("========================\n");
	printf("Feedback Monkey\n");
	printf("========================\n");

	printf("Quantas máquinas existem?\n");
	scanf("%d", &nMaquinas);

	if (nMaquinas < 1) {
		printf("Numero inválido.\n");
		return -1;
	}
	
	
	int fd = -1;
	int shm_size = sizeof(Avaliacao) * nMaquinas;	
	
	// Criação da zona de memória partilhada
	fd = shm_open("/concorrenciaAvaliacoes", O_CREAT | O_EXCL | O_RDWR, S_IRUSR | S_IWUSR);
	ftruncate(fd, shm_size);
	Avaliacao *a1;
	Avaliacao *aX;
	a1 = (Avaliacao *) mmap(NULL, shm_size, PROT_READ | PROT_WRITE, MAP_SHARED, fd, 0);
	
	int x = criarFilhos(nMaquinas); // Criação dos processos filhos
	
	
	if(x == -1){ //Processo Pai
		int i = 0;
		int catched = 0;
		while (1) {
			for(i = 0; i < nMaquinas; i++){
				aX = a1 + i;
				if(aX->flagX != 1){
					catched = 1;
					aX->flagX = 1;
				}					
			}
			
			if(catched){
				for(i = 0; i < nMaquinas; i++){
					aX = a1 + i;
					if(aX->shm_flag == 1){
						// Imprime avaliação no ecrã
						printf("\n==================== Processo Pai ====================\n");
						printf("Nova avaliação registada.\n");
						printf("Máquina nº %d\n", aX->id);
						printf("Nome Produto: %s\n", aX->nomeProduto);
						printf("Avaliação: %d\n", aX->valor);
						aX->flagX = 0;
						aX->shm_flag= 0;
						catched = 0;
					}
				}
			}
		}	
	} else { // Processos filho
		time_t t;
		srand((unsigned) time(&t) + getpid());
		a1 += x;
		while (1) {
			// Gera um valor de intervalo entre avaliações automático
			tempoEntreAvaliacoes = (rand() % 5) + 1;
			sleep(tempoEntreAvaliacoes); // Adormece um tempo (valor gerado)
			while(!a1->flagX);
				// Cria uma avaliação aleatória de um produto
				strcpy(a1->nomeProduto, produtoAleatorio());
				a1->valor = rand() % 11; // Avaliação entre 0 e 10 (inclusive)
				a1->id = x; // ID da avaliação correspondente ao ID do processo filho
				a1->shm_flag = 1;
				//leitura da mensagem de sucesso aqui
				while(a1->shm_flag);
				printf("\n=================== Processo Filho %d (%d) ===================\n", x, getpid());
				//Verifica se a mensagem a1 corresponde ao esperado.
				printf("%s\n", sucesso);
				a1->flagX = 0;	
		}
	}
	
	return 0;
}