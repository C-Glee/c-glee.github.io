#include "CsvParser.h"
#include <fstream>
#include <sstream>
#include <algorithm>
#include <cctype>

using namespace std;

// Trim whitespace (spaces, tabs, CR/LF) from both ends.
string CsvParser::trim(const string& str) {
    size_t first = str.find_first_not_of(" \t\r\n");
    if (first == string::npos) return "";
    size_t last = str.find_last_not_of(" \t\r\n");
    return str.substr(first, last - first + 1);
}

// Parse a single line, splitting on commas but respecting quotes.
vector<string> CsvParser::parseLine(const string& line) {
    vector<string> result;
    istringstream ss(line);
    string segment;
    bool inQuotes = false;
    string field;

    // Iterate over comma-delimited segments
    while (getline(ss, segment, ',')) {
        // Count quotes in this segment
        int quoteCount = count(segment.begin(), segment.end(), '\"');
        // Toggle inQuotes if an odd number of quotes appear
        if (quoteCount % 2 != 0) {
            inQuotes = !inQuotes;
        }
        // Append segment to field (with comma if still inside quotes)
        field += segment;
        if (inQuotes) {
            field.push_back(',');  // keep the comma as part of the field
        }
        else {
            // Field is complete; process trimming and remove outer quotes
            string trimmed = trim(field);
            // If quoted, strip outer quotes and unescape inner quotes ("")
            if (trimmed.size() >= 2 && trimmed.front() == '\"' && trimmed.back() == '\"') {
                trimmed = trimmed.substr(1, trimmed.size() - 2);
                // Replace double quotes with single quote character
                size_t pos;
                while ((pos = trimmed.find("\"\"")) != string::npos) {
                    trimmed.replace(pos, 2, "\"");
                }
            }
            result.push_back(trimmed);
            field.clear();
        }
    }
    // In case the last field had no closing quote (malformed CSV), still push it
    if (!field.empty()) {
        result.push_back(trim(field));
    }
    return result;
}

// Read CSV file line by line and parse.
bool CsvParser::parseFile(const string& filename, vector<vector<string>>& rows) {
    ifstream file(filename);
    if (!file.is_open()) return false;
    string line;
    while (getline(file, line)) {
        if (line.empty()) continue;
        auto tokens = parseLine(line);
        if (!tokens.empty()) {
            rows.push_back(tokens);
        }
    }
    return true;
}