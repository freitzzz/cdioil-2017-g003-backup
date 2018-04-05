#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <time.h>
#include <string.h>
#include <sys/wait.h>
#include <sys/types.h>

/*
 * Matriz de produtos
 */
char produtos[][20] = {
		"Iogurte Continente",
		"Vinho Verde",
		"Massa Esparguete",
		"Agua 50cl",
		"Lápis"
};

/*
 * Devolve um nome de produto aleatorio
 */
char* produtoAleatorio() {
	int idx = rand() % 5;

	return produtos[idx];
}
void inicializarPipes(int size, int **fd){
	int a;
	for(a=0; a<size; a++){
		pipe(fd[a]);
	}
}

int criarFilhos(int size){
	printf("======================\n");
	printf("Máquinas criadas:\n");
	int i = 0;
	for (; i < size; i++) {
		pid_t pid = fork();
		if (pid == 0) {
			printf("Máquina %d\n", getpid());
			return i;
		}
	}
	return -1;	
}

void fecharExtremidadePipe(int maquina, int extremidade, int ** fd){
	close(fd[maquina][extremidade]);
}

/*
 * Estrutura usada para definir uma avaliação
 */
typedef struct Avaliacao {
	char nomeProduto[20];
	int valor;
	int idMaquina;
	int id;
} Avaliacao;

/*
 * US-802
 *
 * Receção concorrente da avaliação de produtos
 */
int main() {

	int nMaquinas, tempoEntreAvaliacoes;
	int valorMAX = 256;
	char sucesso[] = "Recebido com sucesso!";
	char recebidoFilho[valorMAX];

	Avaliacao a1;

	pid_t pid;

	//printf("%d\n", sizeof(Avaliacao));

	printf("======================\n");
	printf("Feedback Monkey\n");
	printf("======================\n");

	printf("Quantas máquinas existem?\n");
	scanf("%d", &nMaquinas);

	if (nMaquinas < 1) {
		printf("Numero inválido.\n");
		return -1;
	}

	int fd[nMaquinas+1][2], i=0;
	inicializarPipes(nMaquinas+1,(int**)fd);
	int nrFilho = criarFilhos(nMaquinas);
	switch (nrFilho) {
	case -1:// Processo Pai
		//Fecha a extremidade de leitura do segundo pipe
		for(; i < nMaquinas; i++){
			fecharExtremidadePipe(i,0,(int **)fd);
		}
		//Fecha a extremidade de escrita do primeiro pipe
		close(fd[0][1]);
		
		while (1) {
			// Lê avaliaçao
			read(fd[0][0], &a1, sizeof(Avaliacao));
			
			//Escreve o sucesso das leituras
			write(fd[a1.id][1], sucesso, sizeof(sucesso));

			// Imprime avaliação para o ecra
			printf("\n====================Processo Pai====================\n");
			printf("Nova avaliação registada.\n");
			printf("Máquina nº %d\n", a1.id);
			printf("Nome Produto: %s\n", a1.nomeProduto);
			printf("Avaliação: %d\n", a1.valor);
		}
		break;
	default: 
	 // child
		pid = getpid();
		
		//Fecha a extremidade de leitura do primeiro pipe
		close(fd[0][0]);
		for(i=0; i<nMaquinas; i++){
			if(i!=nrFilho){
				fecharExtremidadePipe(i,1,(int **)fd);
			}
		}

		srand(pid);
		while (1) {
			tempoEntreAvaliacoes = (rand() % 5) + 10;
			sleep(tempoEntreAvaliacoes); // Adormece um temp


			// Cria uma avaliação aleatória de um produto
			strcpy(a1.nomeProduto, produtoAleatorio());
			a1.valor = rand() % 11; // avaliação entre 0 e 10 (inclusive)
			a1.id = nrFilho;
			write(fd[0][1], &a1, sizeof(Avaliacao)); // Escreve para o pipe
			
			//Lê a mensagem de sucesso das leituras.
			size_t n = read(fd[a1.id][0], recebidoFilho, valorMAX);
			
			printf("\n===================Processo Filho %d===================\n", getpid());
			//Verifica se a mensagem recebida corresponde ao esperado.
			if( n == sizeof(sucesso)){
				printf("%s\n", recebidoFilho);
			}else{
				printf("Erro na receção de dados!\n");
			}
		}
		break;
	}
}
