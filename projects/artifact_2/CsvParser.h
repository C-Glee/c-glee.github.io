#pragma once
#ifndef CSVPARSER_H
#define CSVPARSER_H

#include <string>
#include <vector>

using namespace std;

class CsvParser {
public:
    // Parse the given CSV file. Returns true on success and fills 'rows' with tokenized fields.
    static bool parseFile(const string& filename, vector<vector<string>>& rows);

    // Parse a single CSV line into fields (handles quotes and embedded commas).
    static vector<string> parseLine(const string& line);

private:
    // Trim whitespace from both ends of a string.
    static string trim(const string& str);
};

#endif
