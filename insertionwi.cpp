/*
 Name: Keaton Rohlfs
 Email: kwrohlfs@crimson.ua.edu
 Course Section: Spring 2022 CS 201
 Homework #: 1
*/

#include <iostream>
#include <array>
#include <string>
#include <vector>
#include <algorithm>
#include <iterator>

/*
InsertionSort(numbers, numbersSize) {
   i = 0
   j = 0
   temp = 0  // Temporary variable for swap
   
   for (i = 1; i < numbersSize; ++i) {
      j = i
      // Insert numbers[i] into sorted part
      // stopping once numbers[i] in correct position
      while (j > 0 && numbers[j] < numbers[j - 1]) {
         
         // Swap numbers[j] and numbers[j - 1]
         temp = numbers[j]
         numbers[j] = numbers[j - 1]
         numbers[j - 1] = temp
         --j
      }
   }
}
*/

template< class RandomIt >
void insertionsort(RandomIt first, RandomIt last) {
	RandomIt i = 0;
	RandomIt j = 0;
    for (RandomIt i = first + 1; i < last; ++i) {
		auto temp = *i;
		RandomIt j = i - 1;
			while(j >= first && *j > temp) {
				*(j+1) = *j;
				--j;
			}
		*(j+1) = temp;
    }
}