main: main.o FileReader.o DeviceSimulatorConfiguer.o DeviceSimulator.o StringUtils.o
	gcc -Wall -g main.o FileReader.o DeviceSimulatorConfiguer.o StringUtils.o DeviceSimulator.o -o main -lpthread
main.o: main.c
	gcc -Wall -c -g main.c -lpthread
FileReader.o: FileReader.c
	gcc -Wall -c -g FileReader.c
DeviceSimulatorConfiguer.o: DeviceSimulatorConfiguer.c
	gcc -Wall -c -g DeviceSimulatorConfiguer.c
StringUtils.o: StringUtils.c
	gcc -Wall -c -g StringUtils.c
DeviceSimulator: DeviceSimulator.c
	gcc -Wall -c -g DeviceSimulator.c
run: main
	./main
clean:
	rm *.o main