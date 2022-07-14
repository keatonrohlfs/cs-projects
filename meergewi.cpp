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

template< class RandomIt >
void merge(RandomIt first1, RandomIt mid, RandomIt last1, RandomIt tempstart) {
    int leftd = std::distance(first1, mid);
    int rightd = std::distance(mid, last1);
    RandomIt first = first1;
    RandomIt middle = mid;
    int i = 0;
    int j = 0;
    int k = 0;

    while (i < leftd && j < rightd) {
        if(first[i] < middle[j]) {
        tempstart[k] = first[i];
            ++i;
        }
        else if(middle[j] < first[i]) {
        tempstart[k] = middle[j];
            ++j;
        }
        ++k;
    }
    while(i < leftd) {
    tempstart[k] = first[i];
        ++i;
        ++k;
    }
    
    while(j < rightd) {
    tempstart[k] = middle[j];
        ++j;
        ++k;
    }
    int mergesize = std::distance(first1, last1);
    for(int l = 0; l < mergesize; l++) {
        first1[l] = tempstart[l];
    }
}

template< class RandomIt >
void mergesort(RandomIt first1, RandomIt last1, RandomIt first2) {
    int mergesize = std::distance(first1, last1);
    RandomIt mid = std::next(first1, mergesize / 2);
    if (mergesize <= 1) { 
        return;
    }

    mergesort(first1, mid, first2);
    mergesort(mid, last1, first2);
    merge(first1, mid, last1, first2);
}