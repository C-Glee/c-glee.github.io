#include "CourseTree.h"
#include <iostream>

using namespace std;

// Helper to insert a Course pointer into the BST by courseNumber
void CourseTree::insertNode(Node*& node, Course* course) {
    if (!node) {
        node = new Node(course);
    }
    else if (course->courseNumber < node->data->courseNumber) {
        insertNode(node->left, course);
    }
    else {
        insertNode(node->right, course);
    }
}

// PRIVATE: In-order traversal
void CourseTree::inOrderTraverse(Node* node) const {
    if (!node) return;
    inOrderTraverse(node->left);

    // no printing
    (void)node->data;

    inOrderTraverse(node->right);
}

// PUBLIC: Traverse entire tree
void CourseTree::traverseAll() const {
    inOrderTraverse(root);
}


// Recursive search in BST
Course* CourseTree::searchNode(Node* node, const string& courseId) const {
    if (!node) return nullptr;
    if (node->data->courseNumber == courseId) {
        return node->data;
    }
    else if (courseId < node->data->courseNumber) {
        return searchNode(node->left, courseId);
    }
    else {
        return searchNode(node->right, courseId);
    }
}

// Delete all nodes
void CourseTree::destroy(Node* node) {
    if (!node) return;
    destroy(node->left);
    destroy(node->right);
    delete node;
}