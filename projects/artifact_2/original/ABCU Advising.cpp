//============================================================================
// Name        : ABCU Advising.cpp
// Author      : Carson Lee
// Description : This program supports loading a CSV course list into a BinarySearchTree, listing all courses,
//               or searching for a specific course.
//============================================================================
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

// Course structure
struct Course {
    string courseNumber;
    string courseName;
    vector<string> preReqNumbers;
};

// Tree node for BST
struct TreeNode {
    Course data;
    TreeNode* left;
    TreeNode* right;

    TreeNode(Course c) : data(c), left(nullptr), right(nullptr) {}
};

// Binary Search Tree
class CourseTree {
private:
    TreeNode* root;

    void insertNode(TreeNode*& node, Course c) {
        if (!node) {
            node = new TreeNode(c);
        } else if (c.courseNumber < node->data.courseNumber) {
            insertNode(node->left, c);
        } else {
            insertNode(node->right, c);
        }
    }

    void inOrder(TreeNode* node) {
        if (!node) return;
        inOrder(node->left);
        cout << node->data.courseNumber << ": " << node->data.courseName << endl;
        inOrder(node->right);
    }

    Course* search(TreeNode* node, const string& courseId) {
        if (!node) return nullptr;
        if (node->data.courseNumber == courseId) {
            return &node->data;
        } else if (courseId < node->data.courseNumber) {
            return search(node->left, courseId);
        } else {
            return search(node->right, courseId);
        }
    }

    void destroyTree(TreeNode* node) {
        if (!node) return;
        destroyTree(node->left);
        destroyTree(node->right);
        delete node;
    }

public:
    CourseTree() : root(nullptr) {}
    ~CourseTree() { destroyTree(root); }

    void insert(Course c) {
        insertNode(root, c);
    }

    void printInOrder() {
        inOrder(root);
    }

    Course* search(const string& courseId) {
        return search(root, courseId);
    }
};

// Helper function to trim whitespace
string trim(const string& str) {
    size_t first = str.find_first_not_of(" \t\r\n");
    size_t last = str.find_last_not_of(" \t\r\n");
    return str.substr(first, (last - first + 1));
}

// Function to load courses into the BST
bool loadCourses(string filePath, CourseTree& courseTree, vector<Course>& courseList) {
    ifstream file(filePath);
    if (!file.is_open()) {
        cout << "Error: file was unable to be read." << endl;
        return false;
    }

    string line;
    while (getline(file, line)) {
        line = trim(line);
        if (line.empty()) continue;

        stringstream ss(line);
        string token;
        vector<string> tokens;

        while (getline(ss, token, ',')) {
            tokens.push_back(trim(token));
        }

        if (tokens.size() < 2) {
            cout << "Format error: incorrect number of fields: " << line << endl;
            return false;
        }

        Course course;
        course.courseNumber = tokens[0];
        course.courseName = tokens[1];
        for (size_t i = 2; i < tokens.size(); ++i) {
            course.preReqNumbers.push_back(tokens[i]);
        }

        courseTree.insert(course);
        courseList.push_back(course);
    }

    file.close();
    return true;
}

// Function to display detailed course information
void displayCourse(CourseTree& tree, vector<Course>& courseList, const string& courseId) {
    Course* course = tree.search(courseId);
    if (!course) {
        cout << "Course " << courseId << " not found." << endl;
        return;
    }

    cout << "COURSE: " << course->courseNumber << " – " << course->courseName << endl;
    if (course->preReqNumbers.empty()) {
        cout << "Prerequisites: None" << endl;
    } else {
        cout << "Prerequisites:" << endl;
        for (const string& pre : course->preReqNumbers) {
            bool found = false;
            for (const Course& c : courseList) {
                if (c.courseNumber == pre) {
                    cout << "  • " << c.courseNumber << " – " << c.courseName << endl;
                    found = true;
                    break;
                }
            }
            if (!found) {
                cout << "  • " << pre << " – [Course not found]" << endl;
            }
        }
    }
}

// Main menu loop
int main() {
    CourseTree courseTree;
    vector<Course> courseList;
    string csvPath = "course.csv";
    int choice = 0;

    while (choice != 9) {
        cout << "\nMenu:" << endl;
        cout << "1. Load courses from file" << endl;
        cout << "2. List all CS courses" << endl;
        cout << "3. Show course details and prerequisites" << endl;
        cout << "9. Exit" << endl;
        cout << "Enter choice: ";
        cin >> choice;

        switch (choice) {
            case 1:
                if (loadCourses(csvPath, courseTree, courseList)) {
                    cout << "Courses loaded successfully." << endl;
                } else {
                    cout << "Failed to load courses." << endl;
                }
                break;
            case 2:
                cout << "All CS Courses (Sorted):" << endl;
                courseTree.printInOrder();
                break;
            case 3: {
                cout << "Enter a course number to search: ";
                string courseId;
                cin >> courseId;
                displayCourse(courseTree, courseList, courseId);
                break;
            }
            case 9:
                cout << "Exiting program." << endl;
                break;
            default:
                cout << "Invalid choice. Please select an option." << endl;
        }
    }

    return 0;
}