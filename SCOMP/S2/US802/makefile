us802: us802.o 
	gcc -Wall -g us802.o -o us802

us802.o: SCOMP_US802_S2.c
	gcc -Wall -c -g us802.c
	
run: us802
	./us802
	
clean:
	rm *.o
