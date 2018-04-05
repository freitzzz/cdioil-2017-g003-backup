us805: us805.o 
	gcc -Wall -g us805.o -o us805 -lrt

us805.o: us805.c
	gcc -Wall -c -g us805.c -lrt
	
run: us805
	./us805
	
clean:
	rm *.o
