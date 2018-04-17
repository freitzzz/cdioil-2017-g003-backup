main: main.o FileReader.o
	gcc -Wall -g main.o FileReader.o -o main -lpthread
main.o: main.c
	gcc -Wall -c -g main.c -lpthread
FileReader.o: FileReader.c
	gcc -Wall -c -g FileReader.c
run: main
	./main
clean:
	rm *.o main