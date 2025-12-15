#pragma once
#ifndef COURSE_H
#define COURSE_H

#include <string>
#include <vector>

using namespace std;

// Course struct holds the course ID, name, and prerequisite IDs
struct Course {
    string courseNumber;
    string courseName;
    vector<string> preReqNumbers;
};

#endif
