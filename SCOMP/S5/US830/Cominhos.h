/* 
* Estrutura para autenticação que guarda a chave gerada e o time stamp.
*/
typedef struct {
char key[256];
time_t timestamp;
} Cominhos;
