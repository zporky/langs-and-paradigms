CC = g++

main: main.o state-machine.o
	$(CC) main.o state-machine.o -o main -g
main.o: main.cc state-machine.h
	$(CC) main.cc -c -g
state-machine.o: state-machine.cc state-machine.h
	$(CC) state-machine.cc -c -g
