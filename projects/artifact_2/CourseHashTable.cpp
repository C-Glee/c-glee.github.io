#include "CourseHashTable.h"
#include <functional>
#include <iostream>

using namespace std;

// Insert course pointer into hash table using hash on courseNumber
void CourseHashTable::insert(Course* course) {
    size_t hashVal = hash<string>{}(course->courseNumber);
    size_t idx = hashVal % bucketCount;
    buckets[idx].push_back(course);
}

// Search for courseNumber by scanning the appropriate bucket
Course* CourseHashTable::search(const string& courseId) const {
    size_t hashVal = hash<string>{}(courseId);
    size_t idx = hashVal % bucketCount;
    for (Course* c : buckets[idx]) {
        if (c->courseNumber == courseId) return c;
    }
    return nullptr;
}

// Iterate through all courses in the table (to measure iteration time)
void CourseHashTable::traverseAll() const {
    for (const auto& bucket : buckets) {
        for (Course* c : bucket) {
            (void)c; // no action, just iterating
        }
    }
}