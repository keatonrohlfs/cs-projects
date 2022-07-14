/*
 Name: Keaton Rohlfs
 Email:kwrohlfs@crimson.ua.edu
 Course Section: Spring 2022 CS 201
 Homework #: 3
*/
#ifndef _ST_HPP_
#define _ST_HPP_

#include <utility>

#include "RBT.hpp"
#include "RBTPrint.hpp"

template <class Key, class Type>
class ST : public RedBlackTree<Key, Type>
{
public:
    typedef RBTNode<Key, Type> *iterator;

    // Constructors
    // constant
    ST()
    {
        nodeCount = 0;
    };

    // Destructor
    // linear in the size of the ST
    ~ST(){

    };

    // access or insert specifies element
    // inserts if the key is not present and returns a reference to
    // the value corresponding to that key
    // O(LogN), N size of ST
    Type &operator[](const Key &key)
    {
        RBTNode<Key, Type> *node = this->Search(key);
        if (node)
        {
            return node->value;
        }
        else
        {
            RBTNode<Key, Type> *newnode = new RBTNode<Key, Type>(key, Type(), nullptr, true, nullptr, nullptr);
            this->InsertNode(newnode);
            nodeCount++;
            return newnode->value;
        }
    };

    // insert a (key, value) pair, if the key already exists
    // set the new value to the existing key
    // O(LogN), N size of ST
    void insert(const Key &key, const Type &value)
    {
        RBTNode<Key, Type> *node = this->Search(key);
        if (node == nullptr)
        { //key does not exist
            this->Insert(key, value);
            nodeCount++; //node count increases by one
        }
        else
        { //key exists,
            node->value = value;
            //node count stay the same
        }
    };

    // remove element at the given position
    // amortized constant
    void remove(iterator position){

    };

    // remove element with keyvalue key and
    // return number of elements removed (either 0 or 1)
    // O(logN), N size of ST
    std::size_t remove(const Key &key)
    {
        if (this->Remove(key))
        {
            nodeCount--; //node counts decreses by one
            return 1;
        }
        else
        { //key not removed, didn't exist
            //node count stays same
            return 0;
        }
    };

    // removes all elements from the ST, after this size() returns 0
    // linear in the size of the ST
    void clear()
    {
        if (nodeCount > 0)
        {
            this->DeleteTree(this->root);
            this->root = nullptr;
            nodeCount = 0;
        }
    };

    // checks if ST has no elements; true is empty, false otherwise
    // constant
    bool empty() const
    {
        if (nodeCount <= 0)
        {
            return true; //No root is present, thus no elements in ST
        }
        else
        {
            return false; //root is present, thus elemets in ST
        }
    };

    // returns number of elements in ST
    // constant
    std::size_t size() const
    {
        return nodeCount;
    };

    // returns number of elements that match keyvalue key
    // value returned is 0 or 1 since keys are unique
    // O(LogN), N size of ST
    std::size_t count(const Key &key)
    {
        if (this->Search(key) == nullptr)
        {
            return 0; //key was not found thus appears 0 times
        }
        else
        {
            return 1; //key was found thus appears 1 time
        }
    };

    // find an element with keyvalue key and return
    // the iterator to the element found, nullptr if not found
    // O(LogN), N size of ST
    iterator find(const Key &key)
    {
        return this->Search(key);
    };

    // check if key exists in ST
    // O(LogN), N size of ST
    bool contains(const Key &key)
    {
        if (this->Search(key) == nullptr)
        {
            return false; //key DNE
        }
        else
        {
            return true; //key exists
        }
    };

    // return contents of ST as a vector of (key,value) pairs
    // O(N), N size of ST
    std::vector<std::pair<Key, Type> > toVector()
    {
        v1.clear();
        VectorRecursive(this->GetRoot());
        return v1;
    };

    // print the symbol table as Red-Black Tree
    // O(N), N size of ST
    void displayTree()
    {
        std::cout << RBTPrint<Key, Type>::TreeToString(RedBlackTree<Key, Type>::root) << std::endl;
    };

    // print the symbol table in sorted order
    // O(N), N size of ST
    void display()
    {
        DisplayRecursive(this->GetRoot());
    };

private:
    std::size_t nodeCount;
    std::vector<std::pair<Key, Type> > v1;

    void DisplayRecursive(iterator root)
    {
        if (root)
        {
            DisplayRecursive(root->left);
            std::cout << root->key << ": " << root->value << std::endl;
            DisplayRecursive(root->right);
        }
    }

    void VectorRecursive(iterator root)
    {
        if (root)
        {
            VectorRecursive(root->left);
            v1.push_back(std::pair<Key, Type>(root->key, root->value));
            VectorRecursive(root->right);
        }
    }
};

#endif
