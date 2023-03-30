
a.out: final.o
	g++ -std=c++17 final.o -o a.out 

final.o: final.cpp
	g++ -std=c++17 -c final.cpp

target: dependancies
	action

clean: 
	rm *.o a.out
