us806: us806.o 
	gcc -Wall -g us806.o -o us806 -lrt -lpthread

us806.o: us806.c
	gcc -Wall -c -g us806.c -lrt -lpthread
	
run: us806
	./us806
	
clean:
	rm *.o
