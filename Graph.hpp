/*
 Name: Keaton Rohlfs
 Email: kwrohlfs@crimson.ua.edu
 Course Section: Spring 2022 CS 201
 Homework #: Homework 5
*/

#ifndef _GRAPH_HPP_
#define _GRAPH_HPP_

#include <iostream>
#include <string>
#include <sstream>
#include <list>
#include <limits>

using namespace std;

class Vertex
{
public:
    bool visited;
    int distance;
    int previous;
    int finish;
    list<int> adj;
};

class Graph
{
public:
    Graph(int V, int E, pair<int, int> *edges)
    {
        _V = V;
        _E = E;
        vertices = new Vertex[_V];
        for (int i = 0; i < _V; i++)
        {
            vertices[i].visited = false;
            vertices[i].distance = INFINITY;
            vertices[i].previous = -1;
        }
        for (int i = 0; i < _E; i++)
        {
            addEdge(edges[i].first, edges[i].second);
        }
    }

    virtual ~Graph()
    {
        for (int i = 0; i < _V; ++i)
        {
            auto adj = vertices[i].adj;
            adj.clear();
        }

        delete[] vertices;
    }

    int V()
    {
        return _V;
    }

    int E()
    {
        return _E;
    }

    void addEdge(int u, int v)
    {
        vertices[u].adj.push_back(v);
    }

    list<int> getEdges(int u)
    {
        return vertices[u].adj;
    }

    int degree(int u)
    {
        return vertices[u].adj.size();
    }

    void bfs(int s)
    {
        for (int i = 0; i != 0; i++)
        {
            vertices[i].visited = false;
            vertices[i].distance = INFINITY;
            vertices[i].previous = 0;
        }
        vertices[s].visited = true;
        vertices[s].distance = 0;
        vertices[s].previous = 0;
        list<int> mylist;
        mylist.push_back(s);
        int j;
        list<int>::iterator i;
        while (!mylist.empty())
        {
            j = mylist.front();
            mylist.pop_front();
            i = vertices[j].adj.begin();
            while (i != vertices[j].adj.end())
            {
                if (vertices[*i].visited == false)
                {
                    vertices[*i].visited = true;
                    vertices[*i].distance = vertices[j].distance + 1;
                    vertices[*i].previous = j;
                    mylist.push_back(*i);
                }
                i++;
            }
            vertices[j].visited = true;
        }
    }

    void dfs()
    {
        for (int i = 0; i < V(); i++)
        {
            vertices[i].visited = false;
            vertices[i].previous = 0;
        }

        for (int r = 0; r < V(); r++)
        {
            if (vertices[r].visited == false)
            {
                cout << r << "-> ";
                dfs_visit(r);
            }
        }
    }

    void dfs_visit(int u)
    {
        vertices[u].distance = vertices[u].distance + 1;
        vertices[u].visited = true;
        list<int>::iterator i;
        i = vertices[u].adj.begin();
        while (i != vertices[u].adj.end())
        {
            if (vertices[*i].visited == false)
            {
                vertices[*i].previous = u;
                std::cout << *i << "-> ";
                dfs_visit(*i);
            }
            i++;
        }
        vertices[u].visited = true;
        vertices[u].distance = vertices[u].distance + 1;
        vertices[u].finish = vertices[u].distance;
    }

    void print_path(int s, int v)
    {
        if (v == s)
            cout << s;
        else if (vertices[v].previous == -1)
            cout << "not connected";
        else
        {
            print_path(s, vertices[v].previous);
            cout << "->" << v;
        }
    }

    string toString()
    {
        stringbuf buffer;
        ostream os(&buffer);
        os << "Vertices = " << _V << ", Edges = " << _E << endl;
        for (int i = 0; i < _V; ++i)
        {
            os << i << "(" << degree(i) << "): ";
            for (const auto &l : vertices[i].adj)
                os << l << " ";
            os << endl;
        }

        return buffer.str();
    }

private:
    int _V;
    int _E;
    Vertex *vertices;
    const int INFINITY = numeric_limits<int>::max();
};

#endif