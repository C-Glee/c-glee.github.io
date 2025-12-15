#pragma once
#ifndef COURSEHASHTABLE_H
#define COURSEHASHTABLE_H

#include "Course.h"
#include <string>
#include <vector>
#include <cstddef>

using namespace std;

class CourseHashTable {
private:
    vector<vector<Course*>> buckets; // vector of buckets
    size_t bucketCount;

public:
    CourseHashTable(size_t numBuckets = 101)
        : buckets(numBuckets), bucketCount(numBuckets) {
    }

    void insert(Course* course);
    Course* search(const string& courseId) const;
    // For iteration: go through all buckets.
    void traverseAll() const;
};

#endif