#include "CourseLinkedList.h"
#include <iostream>

using namespace std;

// Destructor: delete all nodes
CourseLinkedList::~CourseLinkedList() {
    Node* curr = head;
    while (curr) {
        Node* nxt = curr->next;
        delete curr;
        curr = nxt;
    }
}

// Append course pointer to list
void CourseLinkedList::append(Course* course) {
    if (!head) {
        head = new Node(course);
    }
    else {
        Node* curr = head;
        while (curr->next) curr = curr->next;
        curr->next = new Node(course);
    }
}

// Linear search
Course* CourseLinkedList::search(const string& courseId) const {
    Node* curr = head;
    while (curr) {
        if (curr->data->courseNumber == courseId) {
            return curr->data;
        }
        curr = curr->next;
    }
    return nullptr;
}

// Traverse list
void CourseLinkedList::traverseAll() const {
    Node* curr = head;
    while (curr) {
        // no output, just traversal
        (void)curr->data;
        curr = curr->next;
    }
}