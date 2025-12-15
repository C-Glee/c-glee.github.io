//============================================================================
// Name        : ABCU.cpp
// Author      : Carson Lee
// Description : This program supports loading a CSV course list into several data structures (BinarySearchTree, LinkedList, and HashTable)
//               and searching each of these data structures for a specific course. The program benchmarks the performance of each data structure by
//               outputting the time taken for each search.
//============================================================================


#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
#include <chrono>
#include <functional>
#include <limits>

#include "CsvParser.h"
#include "CourseTree.h"
#include "CourseLinkedList.h"
#include "CourseHashTable.h"

using namespace std;

// -----------------------------------------------------------------------------
// Helper: Convert parsed CSV fields into Course objects
// -----------------------------------------------------------------------------
static bool loadCoursesModular(
        const string& filePath,
        vector<Course>& centralStorage)
{
    vector<vector<string>> rows;

    // Parse using modular parser
    if (!CsvParser::parseFile(filePath, rows)) {
        cout << "Error: Could not read file: " << filePath << endl;
        return false;
    }

    centralStorage.clear();

    for (const auto& fields : rows) {
        if (fields.size() < 2) {
            cout << "Warning: Skipping malformed row.\n";
            continue;
        }

        Course c;
        c.courseNumber = fields[0];
        c.courseName   = fields[1];

        for (size_t i = 2; i < fields.size(); ++i) {
            if (!fields[i].empty()) {
               c.preReqNumbers.push_back(fields[i]);
            }
        }

        centralStorage.push_back(move(c));
    }

    return true;
}

// -----------------------------------------------------------------------------
// Helper to find course in central storage by course number
// -----------------------------------------------------------------------------
static Course* findCourse(vector<Course>& central, const string& id) {
    for (auto& c : central) {
        if (c.courseNumber == id) return &c;
    }
    return nullptr;
}

// -----------------------------------------------------------------------------
// Main
// -----------------------------------------------------------------------------
int main() {
    vector<Course> centralStorage;

    CourseTree     courseTree;
    CourseLinkedList courseList;
    CourseHashTable courseHash(211); // prime bucket count

    string csvPath = "ABCU_Advising_Program_Input.csv";
    bool loaded = false;

    int choice = 0;

    while (choice != 9) {
        cout << "\nMenu:\n";
        cout << "1. Load courses from file\n";
        cout << "2. List all CS courses (with iteration performance)\n";
        cout << "3. Show course details and prerequisites (with search performance)\n";
        cout << "9. Exit\n";
        cout << "\nEnter choice: ";

        if (!(cin >> choice)) {
            cin.clear();
            cin.ignore(numeric_limits<streamsize>::max(), '\n');
            cout << "========================================================================\n";
            cout << "Invalid input. Please enter a number. \n";
            cout << "========================================================================\n";
            continue;
        }

        switch (choice) {

        // ---------------------------------------------------------------------
        // Load CSV and populate data structures
        // ---------------------------------------------------------------------
        case 1: {
            if (!loadCoursesModular(csvPath, centralStorage)) {
                cout << "Failed to load.\n";
                break;
            }

            // Reset structures
            courseTree = CourseTree();
            courseList = CourseLinkedList();
            courseHash = CourseHashTable(211);

            for (auto& c : centralStorage) {
                Course* ptr = &c;
                courseTree.insert(ptr);
                courseList.append(ptr);
                courseHash.insert(ptr);
            }

            loaded = true;
            cout << "========================================================================\n";
            cout << "Loaded " << centralStorage.size() << " courses successfully.\n";
            cout << "========================================================================\n";
            break;
        }

        // ---------------------------------------------------------------------
        // List courses and measure average iteration performance using loops
        // ---------------------------------------------------------------------
        case 2: {
            if (!loaded) {
                cout << "Please load courses first.\n";
                break;
            }

            // Print sorted course list once
            vector<Course*> sorted;
            for (auto& c : centralStorage) sorted.push_back(&c);

            sort(sorted.begin(), sorted.end(),
                [](Course* a, Course* b) { return a->courseNumber < b->courseNumber; });
            cout << "========================================================================\n";
            cout << "All CS Courses (sorted):\n";
            cout << "========================================================================\n";
            for (Course* c : sorted) {
                cout << c->courseNumber << ": " << c->courseName << endl;
            }

            using Clock = chrono::high_resolution_clock;

            const int ITERATIONS = 1000;
            long long treeTotal = 0, listTotal = 0, hashTotal = 0;

            // BST traversal time
            {
                auto t1 = Clock::now();
                for (int i = 0; i < ITERATIONS; ++i) {
                    courseTree.traverseAll();
                }
                auto t2 = Clock::now();
                treeTotal = chrono::duration_cast<chrono::nanoseconds>(t2 - t1).count();
            }

            // Linked List traversal time
            {
                auto t1 = Clock::now();
                for (int i = 0; i < ITERATIONS; ++i) {
                    courseList.traverseAll();
                }
                auto t2 = Clock::now();
                listTotal = chrono::duration_cast<chrono::nanoseconds>(t2 - t1).count();
            }

            // Hash Table traversal time
            {
                auto t1 = Clock::now();
                for (int i = 0; i < ITERATIONS; ++i) {
                    courseHash.traverseAll();
                }
                auto t2 = Clock::now();
                hashTotal = chrono::duration_cast<chrono::nanoseconds>(t2 - t1).count();
            }

            // Calculate average traversal times
            long long treeAvg = treeTotal / ITERATIONS;
            long long listAvg = listTotal / ITERATIONS;
            long long hashAvg = hashTotal / ITERATIONS;

            cout << "========================================================================\n";
            cout << "Average traversal time (nanoseconds):\n";
            cout << " - Binary Search Tree: " << treeAvg << " ns\n";
            cout << " - Linked List:        " << listAvg << " ns\n";
            cout << " - Hash Table:         " << hashAvg << " ns\n";

            long long minAvg = min({ treeAvg, listAvg, hashAvg });
            cout << "========================================================================\n";
            cout << "Fastest traversal: ";
            if (minAvg == treeAvg) cout << "Binary Search Tree\n";
            else if (minAvg == listAvg) cout << "Linked List\n";
            else cout << "Hash Table\n";
            cout << "========================================================================\n";

            break;
        }


        // ---------------------------------------------------------------------
        // Search course in all structures and measure the average performance times
        // ---------------------------------------------------------------------
        case 3: {
            if (!loaded) {
                cout << "Please load courses first.\n";
                break;
            }

            cout << "Enter course number to search: ";
            string courseId;
            cin >> courseId;

            using Clock = chrono::high_resolution_clock;
            const int ITERATIONS = 1000;

            long long treeTotal = 0, listTotal = 0, hashTotal = 0;
            Course* rTree = nullptr;
            Course* rList = nullptr;
            Course* rHash = nullptr;

            // BST search time
            {
                Course* temp = nullptr;
                auto t1 = Clock::now();
                for (int i = 0; i < ITERATIONS; ++i) {
                    temp = courseTree.search(courseId);
                }
                auto t2 = Clock::now();
                treeTotal = chrono::duration_cast<chrono::nanoseconds>(t2 - t1).count();
                rTree = temp; // last search result
            }

            // Linked List search time
            {
                Course* temp = nullptr;
                auto t1 = Clock::now();
                for (int i = 0; i < ITERATIONS; ++i) {
                    temp = courseList.search(courseId);
                }
                auto t2 = Clock::now();
                listTotal = chrono::duration_cast<chrono::nanoseconds>(t2 - t1).count();
                rList = temp;
            }

            // Hash Table search time
            {
                Course* temp = nullptr;
                auto t1 = Clock::now();
                for (int i = 0; i < ITERATIONS; ++i) {
                    temp = courseHash.search(courseId);
                }
                auto t2 = Clock::now();
                hashTotal = chrono::duration_cast<chrono::nanoseconds>(t2 - t1).count();
                rHash = temp;
            }
            // Calculate average search times
            long long treeAvg = treeTotal / ITERATIONS;
            long long listAvg = listTotal / ITERATIONS;
            long long hashAvg = hashTotal / ITERATIONS;

            cout << "========================================================================\n";
            cout << "Average search times (nanoseconds):\n";
            cout << " - Binary Search Tree: " << treeAvg << " ns\n";
            cout << " - Linked List:        " << listAvg << " ns\n";
            cout << " - Hash Table:         " << hashAvg << " ns\n";

            long long minAvg = min({ treeAvg, listAvg, hashAvg });

            cout << "Fastest search: ";
            if (minAvg == treeAvg) cout << "Binary Search Tree\n";
            else if (minAvg == listAvg) cout << "Linked List\n";
            else cout << "Hash Table\n";
            cout << "========================================================================\n";
            // Show the course only once
            Course* found =
                rTree ? rTree :
                rHash ? rHash :
                rList ? rList : nullptr;

            if (!found) {
                cout << "Course " << courseId << " not found.\n";
            }
            else {
                cout << "COURSE: " << found->courseNumber
                    << " - " << found->courseName << endl;
                cout << "========================================================================\n";
                if (found->preReqNumbers.empty()) {
                    cout << "Prerequisites: None\n";
                }
                else {
                    cout << "Prerequisites:\n";
                    for (const string& pre : found->preReqNumbers) {
                        Course* p = findCourse(centralStorage, pre);
                        if (p) {
                            cout << " * " << p->courseNumber
                                << " - " << p->courseName << endl;
                        }
                        else {
                            cout << " * " << pre << " - [Missing]\n";
                        }
                    }
                }
            }
            cout << "========================================================================\n";
            break;
        }

        case 9:
            cout << "Exiting program.\n";
            break;

        default:
            cout << "========================================================================\n";
            cout << "Invalid option. Please enter one of the listed options.\n";
        }
    }

    return 0;
}