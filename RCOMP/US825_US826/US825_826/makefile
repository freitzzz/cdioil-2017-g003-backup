serverAuth: AuthGenerator/AuthKeyGenerator.o FileReader.o StringUtils.o serverAuth.o
	gcc -Wall -g AuthGenerator/msn.o AuthGenerator/mac.o AuthGenerator/AuthKeyGenerator.o FileReader.o StringUtils.o serverAuth.o -lcrypto -o serverAuth -lrt -lpthread
clientAuth: AuthGenerator/AuthKeyGenerator.o clientAuth.o
	gcc -Wall -g AuthGenerator/msn.o AuthGenerator/mac.o AuthGenerator/AuthKeyGenerator.o clientAuth.o -lcrypto -o clientAuth -lrt -lpthread

serverAuth.o: serverAuth.c Sockets.h sharedmemory.h review.h
	gcc -Wall -c -g serverAuth.c -lrt -lpthread
clientAuth.o: clientAuth.c Sockets.h sharedmemory.h review.h
	gcc -Wall -c -g clientAuth.c -lrt -lpthread

StringUtils.o: Utils/StringUtils.c
	gcc -Wall -c -g Utils/StringUtils.c
FileReader.o: Utils/FileReader.c
	gcc -Wall -c -g Utils/FileReader.c

run: serverAuth clientAuth
	./serverAuth
clean:
	rm *.o serverAuth clientAuth