serverAuth: AuthGenerator/AuthKeyGenerator.o FileReader.o StringUtils.o serverAuth.o aes.o
	gcc -Wall -g AuthGenerator/msn.o AuthGenerator/mac.o AuthGenerator/AuthKeyGenerator.o FileReader.o StringUtils.o serverAuth.o aes.o -o serverAuth -lrt -lpthread -L/usr/local/ssl/lib -lcrypto -lssl
clientAuth: AuthGenerator/AuthKeyGenerator.o clientAuth.o aes.o
	gcc -Wall -g AuthGenerator/msn.o AuthGenerator/mac.o AuthGenerator/AuthKeyGenerator.o clientAuth.o -L/usr/local/ssl/lib -lcrypto -lssl -o clientAuth aes.o -lrt -lpthread -L/usr/local/ssl/lib -lcrypto -lssl

serverAuth.o: serverAuth.c Sockets.h sharedmemory.h review.h aes.h
	gcc -Wall -c -g serverAuth.c -lrt -lpthread -L/usr/local/ssl/lib -lcrypto -lssl
clientAuth.o: clientAuth.c Sockets.h sharedmemory.h review.h aes.h
	gcc -Wall -c -g clientAuth.c -lrt -lpthread -L/usr/local/ssl/lib -lcrypto -lssl

StringUtils.o: Utils/StringUtils.c
	gcc -Wall -c -g Utils/StringUtils.c
FileReader.o: Utils/FileReader.c
	gcc -Wall -c -g Utils/FileReader.c
aes.o: aes.c
	gcc -Wall -c -g aes.c

run: serverAuth clientAuth
	./serverAuth
clean:
	rm *.o serverAuth clientAuth
