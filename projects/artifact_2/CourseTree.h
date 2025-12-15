#pragma once
#ifndef COURSETREE_H
#define COURSETREE_H

#include "Course.h"
#include <string>

using namespace std;

class CourseTree {
private:
    // Tree node holds a pointer to a Course in central storage
    struct Node {
        Course* data;
        Node* left;
        Node* right;
        Node(Course* c) : data(c), left(nullptr), right(nullptr) {}
    };
    Node* root;

    void insertNode(Node*& node, Course* course);
    void inOrderTraverse(Node* node) const;
    Course* searchNode(Node* node, const string& courseId) const;
    void destroy(Node* node);

public:
    CourseTree() : root(nullptr) {}
    ~CourseTree() { destroy(root); }

    void insert(Course* course) { insertNode(root, course); }
    void traverseAll() const; 
    Course* search(const string& courseId) const { return searchNode(root, courseId); }

};

#endif