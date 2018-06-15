mac.o: mac.c
	gcc -Wall -c -g mac.c
msn.o: msn.c
	gcc -Wall -c -g msn.c
AuthKeyGenerator.o: mac.o msn.o AuthKeyGenerator.c
	gcc -Wall -c -g mac.o msn.o AuthKeyGenerator.c -lcrypto

build: AuthKeyGenerator.o