/*
 Name: Keaton Rohlfs
 Email: kwrohlfs@crimson.ua.edu
 Course Section: Spring 2022 CS 201
 Homework #: 2
*/

#include <iostream>
#include <vector>
#include <regex>
#include <map>
#include <string>
#include <algorithm>

using namespace std;

bool PairCompare(pair<string, int> i, pair<string, int> j)
{
    return i.second > j.second;
}

int main()
{
    string text;
    vector<pair<string, int> > val;
    map<string, int> occurance;
    const regex delim("\\s+");

    // auto starttime = std::chrono::steady_clock::now();

    while (cin >> text)
    {
        auto begin = sregex_token_iterator(text.begin(), text.end(), delim, -1);
        auto end = sregex_token_iterator();
        for (sregex_token_iterator word = begin; word != end; word++)
        {
            occurance[*word]++;
        }
    }

    for (auto it1 = occurance.begin(); it1 != occurance.end(); it1++)
    {
        val.push_back(*it1);
    }

    sort(val.begin(), val.end(), PairCompare);

    // auto endtime = std::chrono::steady_clock::now();
    // std::chrono::duration<double> timetaken = endtime - starttime;
    // std::cout << timetaken.count() << std::endl;

    for (auto it = val.begin(); it != val.end(); it++)
    {
        cout << it->first << ": " << it->second << endl;
    }
    return 0;
}