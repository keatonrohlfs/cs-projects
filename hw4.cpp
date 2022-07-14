// Sample program that reads a "/" delimited file and a query file and prints the
// parsed concents to stdout
// To Compile: g++ -std=c++20 HW4Sample.cpp
// To Run: ./a.out dbfile1.txt query.txt
/*
 Name: Keaton Rohlfs
 Email: kwrohlfs@crimson.ua.edu
 Course Section: Spring 2022 CS 201
 Homework #: 4
*/
#include <iostream>
#include <fstream>
#include <string>
#include <regex>
#include <unordered_map>
#include <vector>
#include <chrono>

using namespace std;

int main(int argc, char *argv[])
{
    ofstream myFile;
    myFile.open("output.txt");
    auto start2 = chrono::steady_clock::now();
    int i = 0;
    // check for correct command-line arguments
    if (argc != 3)
    {
        cout << "Usage: " << argv[0] << " <db file> <query file>" << endl;
        exit(-1);
    }

    string line, name;
    regex delim("/");
    ifstream dbfile(argv[1]);
    if (!dbfile.is_open())
    {
        cout << "Unable to open file: " << argv[1] << endl;
        exit(-1);
    }

    cout << "***Reading db file " << argv[1] << "***" << endl;
    unordered_map<string, vector<string> > movieMap;
    unordered_map<string, vector<string> > actorMap;
    auto start = chrono::steady_clock::now();
    while (getline(dbfile, line))
    {
        // parse each line for tokens delimited by "/"
        auto begin = sregex_token_iterator(line.begin(), line.end(), delim, -1);
        auto end = sregex_token_iterator();
        cout << "***Line " << ++i << " ***" << endl;
        string movieName = *begin;
        ++begin;
        cout << "Actors: " << endl;
        for (sregex_token_iterator word = begin; word != end; ++word)
        {
            movieMap[movieName].push_back(*word);
            actorMap[*word].push_back(movieName);
        }
    }
    auto end = chrono::steady_clock::now();
    chrono::duration<double> diff = end - start;
    myFile << diff.count() << " s (making)" << endl;
    dbfile.close();
    cout << "***Done reading db file " << argv[1] << "***" << endl;

    ifstream queryfile(argv[2]);
    if (!queryfile.is_open())
    {
        cout << "Unable to open file: " << argv[2] << endl;
        exit(-1);
    }

    cout << "***Reading query file " << argv[2] << "***" << endl;
    auto start1 = chrono::steady_clock::now();
    while (getline(queryfile, name))
    {
        bool isMovieQuery = (name.find("(") != -1);
        if (isMovieQuery)
        {
            auto result = movieMap.find(name);
            if (result == movieMap.end())
            {
                cout << "Not Found" << endl;
            }
            else
            {
                for (auto it = result->second.begin(); it != result->second.end(); it++)
                {
                    cout << *it << endl;
                }
            }
        }
        else
        {
            auto result = actorMap.find(name);
            if (result == actorMap.end())
            {
                cout << "Not Found" << endl;
            }
            else
            {
                for (auto it = result->second.begin(); it != result->second.end(); it++)
                {
                    cout << *it << endl;
                }
            }
        }
    }
    auto end1 = chrono::steady_clock::now();
    chrono::duration<double> diff1 = end1 - start1;
    myFile << diff1.count() << " s (search)" << endl;
    queryfile.close();
    cout << "***Done reading query file " << argv[2] << "***" << endl;
    auto end2 = chrono::steady_clock::now();
    chrono::duration<double> diff2 = end2 - start2;
    myFile << diff2.count() << " s (total)" << endl;

    return 0;
}
