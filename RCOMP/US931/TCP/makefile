serverAuth: AuthGenerator/AuthKeyGenerator.o FileReader.o StringUtils.o serverAuth.o
	gcc -Wall -g AuthGenerator/msn.o AuthGenerator/mac.o AuthGenerator/AuthKeyGenerator.o FileReader.o StringUtils.o serverAuth.o -lcrypto -o serverAuth
clientAuth: AuthGenerator/AuthKeyGenerator.o clientAuth.o
	gcc -Wall -g AuthGenerator/msn.o AuthGenerator/mac.o AuthGenerator/AuthKeyGenerator.o clientAuth.o -lcrypto -o clientAuth

serverAuth.o: serverAuth.c
	gcc -Wall -c -g serverAuth.c 
clientAuth.o: clientAuth.c
	gcc -Wall -c -g clientAuth.c

StringUtils.o: Utils/StringUtils.c
	gcc -Wall -c -g Utils/StringUtils.c
FileReader.o: Utils/FileReader.c
	gcc -Wall -c -g Utils/FileReader.c

run: serverAuth clientAuth
	./serverAuth
clean:
	rm *.o serverAuth clientAuth