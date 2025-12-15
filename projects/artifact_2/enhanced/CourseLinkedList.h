#pragma once
#ifndef COURSELINKEDLIST_H
#define COURSELINKEDLIST_H

#include "Course.h"
#include <string>

using namespace std;

class CourseLinkedList {
private:
    struct Node {
        Course* data;
        Node* next;
        Node(Course* c) : data(c), next(nullptr) {}
    };
    Node* head;
public:
    CourseLinkedList() : head(nullptr) {}
    ~CourseLinkedList();

    // Append to end of list
    void append(Course* course);
    // Search by courseNumber (linear)
    Course* search(const string& courseId) const;
    // Print all courses (not necessarily sorted)
    void traverseAll() const;
};

#endif