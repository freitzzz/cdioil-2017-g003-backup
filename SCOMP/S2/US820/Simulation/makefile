DeviceSimulator: StringUtils.o FileReader.o DeviceSimulatorConfiguer.o Simulation.o DeviceSimulator.o   
	gcc -Wall -g StringUtils.o FileReader.o DeviceSimulatorConfiguer.o Simulation.o DeviceSimulator.o  -o DeviceSimulator -lrt -lpthread
FileReader.o: Utils/FileReader.c
	gcc -Wall -c -g Utils/FileReader.c
DeviceSimulatorConfiguer.o: DeviceSimulatorConfiguer.c
	gcc -Wall -c -g DeviceSimulatorConfiguer.c
DeviceSimulator.o: DeviceSimulator.c
	gcc -Wall -c -g DeviceSimulator.c
StringUtils.o: Utils/StringUtils.c
	gcc -Wall -c -g Utils/StringUtils.c
Simulation.o: Simulation.c
	gcc -Wall -c -g Simulation.c -lrt -lpthread
run: DeviceSimulator
	./DeviceSimulator
clean:
	rm *.o DeviceSimulator