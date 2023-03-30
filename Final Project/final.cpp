#include <iostream>
#include <string>
#include <unordered_map>
#include <map>
#include <algorithm>
#include <sstream>
#include <fstream>
#include <vector>
#include <tuple>
#include <string.h>
#include <cassert>

using namespace std;

// class TCparse {
//     private:
//     TCparse* table_columns_parser;
//     string column_type;
//     string column_name;
//     string tab_name;
//     int column_id;
//     public:
//     TCparse() {
//         this->column_type = "";
//         this->table_columns_parser = nullptr;
//         this->column_id = 0;
//         this -> tab_name = "";
//         this->column_type = "";
//     }
//     string getctype(){
//         return this->column_type;
//     }
//     void setctype(string ctype){
//         this->column_type = ctype;
//     }
//     int getcid(){
//         return this->column_id;
//     }
//     void setcid(int cid){
//         this->column_id = cid;
//     }
//     string gettname(){
//         return this->tab_name;
//     }
//     void settname(string tname){
//         this->column_name = tname;
//     }
//     string getcname(){
//         return this->column_name;
//     }
//     void setcname(string cname){
//         this->column_name = cname;
//     }
// };
// class Select {
//     private:
//     Select* select;
//     vector<string> select_cond;
//     public:
//     Select() {
//         this -> select = nullptr;
//     }
//     Select(vector<string> columns) {
//         this -> select_cond = columns;
//     }
//     vector<string> getSelectconds(){
//         return this -> select_cond;
//     }
//     void Sparse (string select_command) {
//         stringstream sstream(select_command);
//         while(sstream.good()) {
//             string parsestring;
//             getline(sstream, parsestring, ',');
//             this -> select_cond.push_back(parsestring);
//         }
//     }
// };

// class From {
//     private:
//     From* from;
//     string tname;
//     public:
//     From() {
//         this -> from = nullptr;
//     }
//     string getTname () {
//         return this -> tname;
//     }
//     void Fparse(string from_command) {
//         this -> tname = from_command;
//     }
// };

// class Where {
//     private:
//     Where* where;
//     public:
//     Where() {
//         this -> where = nullptr;
//     }
// };
// class Orderby {
//     private:
//     vector<string> sort_cond;
//     vector<int> sort_ord;
//     public:
//     Orderby() {

//     }
//     Orderby(vector<string> sort_req) {
//         sort_cond = sort_req;
//     }
//     vector<string> getSortCond() {
//         return sort_cond;
//     }
// };
// class Query {
//     private:
//     Query* query;
//     public:
//     Select* select;
//     From* from;
//     Where* where;
//     Orderby* orderby;

string trimString(string str);

vector<string> parseString(string q_str);

string ccat(vector<string> num, char chr);

template <typename trimstr> bool typeCheck(string sign, trimstr num1, trimstr num2);

bool typeCheck(string ctype, string sign, string left, string right);

auto parseStr(string str, char chr) {
    vector<string> vect;
    istringstream sstr(str);
    string tok;
    while (getline(sstr, tok, chr))
        vect.push_back(trimString(tok));
    return vect;
}

class Search {
private:
public:
    vector<string> rows;
    enum row_index {tname, cname, ctype, column_index};

    Search(string tname) {
        ifstream inputFile("TAB_COLUMNS.csv");
        string inputStr;
        while (getline(inputFile, inputStr))
            if (inputStr.find(tname) == 0)
                rows.push_back(inputStr);
    }

    tuple<int, string> columnSearch(string cname) {
        for (string rowstr : rows) {
            auto row_index = parseStr(rowstr, ',');
            if (row_index[row_index::cname] == cname)
                return {
                    stoi(row_index[row_index::column_index]),
                    row_index[row_index::ctype]};
        }
    }
    string getTstr(string cname) {
        auto [ind, tstr] = columnSearch(cname);
        return tstr;
    }
    int findIndex(string cname) {
        auto [ind, tstr] = columnSearch(cname);
        return ind;
    }
    string getColumnName(int ind) {
        for (string rowstr : rows) {
            auto row_index = parseStr(rowstr, ',');
            if (stoi(row_index[row_index::column_index]) == ind)
                return row_index[row_index::cname];
        }
        assert(false);
    }
};

vector<string> getTrimStr(string tname, Search &search, int sClear) {
    vector<string> trimstr;
        ifstream file(tname + ".csv");
        string inputStr;
        while (getline(file, inputStr)) {
            auto row_index = parseStr(inputStr, ',');
            int clearance = stoi(row_index[search.findIndex("TC") - 1]);
            if (clearance <= sClear) {
                trimstr.push_back(inputStr);
            }
        }
    return trimstr;
}

vector<string> pRows(vector<string> trimstr, Search &search, string where) {
    for (auto yString : parseStr(where, ',')) {
        auto rowCheck = [](string rowstr, string s_clear, Search &search) -> bool {string left, sign, right;
            for (auto signvar : {"=", ">=", "<=", "<>", ">", "<"}) {
                int i = s_clear.find(signvar);
                if (i != -1) {
                    sign = signvar;
                    left = trimString(s_clear.substr(0, i)),
                    right = trimString(s_clear.substr(i + strlen(signvar)));
                    continue;
                }
            }
            auto [column_index, ctype] = search.columnSearch(left);
            string column_string = parseStr(rowstr, ',')[column_index - 1];
            return 
            typeCheck(ctype, sign, column_string, right);
        };

        vector<string> parsed;
        for (auto rowstr : trimstr) {
            if (rowCheck(rowstr, yString, search)) {
                parsed.push_back(rowstr);
            }
            trimstr = parsed;
        }
    }
    return trimstr;
}

vector<string> sRows(vector<string> trimstr, Search &search, string orderby) {
    vector<string> xvar = parseStr(orderby, ',');
    struct str {
        int ind;
        string tstr;
        bool boo;
    };
    vector<str> x;
    for (auto y : xvar) {
        auto splitstr = parseStr(y, ':');
        auto [ind, tstr] = search.columnSearch(splitstr[0]);
        bool boo = (stoi(splitstr[1]) == 1);
        x.push_back({ind, tstr, boo});
    }
    auto helperSort = [&x](string rowA, string rowB) {
        vector<string> Num1 = parseStr(rowA, ',');
        vector<string> Num2 = parseStr(rowB, ',');
        for (auto y : x) {
            string num1 = Num1[y.ind - 1], num2 = Num2[y.ind - 1];
            if (num1 == num2) {
                continue;
            }
            return typeCheck(y.tstr, y.boo ? "<=" : ">", num1, num2);
        }
        return false;
    };
    sort(trimstr.begin(), trimstr.end(), helperSort);
    return trimstr;
}

vector<string> boolCheck(vector<string> trimstr, Search &search, string xString) {
    vector<string> tName;
    vector<string> column_names;
    for (int ind = 0; ind < search.rows.size(); ind++) {
        column_names.push_back(search.getColumnName(ind + 1));        
    }
    tName.push_back(ccat(column_names, ','));
    for (string rowstr : trimstr) {
        tName.push_back(rowstr);        
    }
    if (xString == "*") {
        return tName;        
    }
    vector<string> x = parseStr(xString, ',');
    bool boolean;
    vector<int> output;
    vector<string> input_columns;

    for (auto y : x) {
        auto vect = parseStr(y, ':');
        input_columns.push_back(vect[0]);
        boolean = vect[1] == "1";
    }

    if (!boolean) {
        for (int ind = 1; ind <= search.rows.size(); ind++) {
            bool aval = find(input_columns.begin(), input_columns.end(), search.getColumnName(ind)) != input_columns.end();
            if (!aval) {
                output.push_back(ind);                
            }
        }
    }
    if (boolean) {
        for (auto cname : input_columns) {
            output.push_back(search.findIndex(cname));
        }
    }
    vector<string> outputstr;

    for (auto rowstr : tName) {
        auto row_index = parseStr(rowstr, ',');
        vector<string> num;
        for (int ind : output) {
            num.push_back(row_index[ind - 1]);            
        }
        outputstr.push_back(ccat(num, ','));
    }

    return outputstr;
}
vector<string> clearanceCheck(int sClear, vector<string> query) {
    string columns = query[0];
    string tname = query[1];
    string where = query[2];
    string orderby = query[3];
    Search search(tname);
    auto trimstr = getTrimStr(tname, search, sClear);
    auto matchingRows = pRows(trimstr, search, where);
    auto ordered = sRows(matchingRows, search, orderby);
    auto outputstr = boolCheck(ordered, search, columns);
    return outputstr;
}

void execQuery(int sClear, string q_str)
{
    vector<string> query = parseString(q_str);
    vector<string> trimstr = clearanceCheck(sClear, query);
    for (string rowstr : trimstr) {
        cout << rowstr << endl;        
    }
}

int main(int argc, char **argv)
{
    int sClear = stoi(argv[1]);
    if (argc < 2) {
        cout << "Invalid Input" << endl;
    }

    if (argc == 3)
    {
        string q_str = argv[2];
        execQuery(sClear, q_str);
    }
    else
    {
        while (true)
        {
                cout << endl << "MLS> ";

            string cla;
            getline(cin, cla);

            if (cla == "EXIT")
            {
                return 0;
            }

            if (!cla.empty())
                execQuery(sClear, cla);
        }
    }
}


string trimString(string str)
{
    int i = 0;
    int j = str.size() - 1;
    while (str[i] == ' ')
        i++;
    while (str[j] == ' ')
        j--;
    return str.substr(i, j - i + 1);
}

vector<string> parseString(string q_str)
{
    auto sstr = [&](int i, int j)
    {
        return q_str.substr(i, j - i);
    };

    assert(q_str[q_str.size() - 1] == ';');
    q_str = q_str.substr(0, q_str.size() - 1);

    int selectQuery = q_str.find("SELECT");
    selectQuery += strlen("SELECT");
    int fromQuery = q_str.find("FROM");
    int whereQuery = q_str.find("WHERE");
    int orderByQuery = q_str.find("ORDERBY");
    string columns = sstr(selectQuery, fromQuery);
    string trimstr;
    string where = "";
    string orderBy = "";
    if (whereQuery == -1 && orderByQuery == -1) {
        trimstr = q_str.substr(fromQuery + strlen("FROM"));
    }
    else {
        if (whereQuery != -1) {
            trimstr = sstr(fromQuery + strlen("FROM"), whereQuery);
            if (orderByQuery == -1) {
                where = q_str.substr(whereQuery + strlen("WHERE"));
            }
            else {
                where = sstr(whereQuery + strlen("WHERE"), orderByQuery);
                orderBy = q_str.substr(orderByQuery + strlen("ORDERBY"));
            }
        }
        else {
            assert(orderByQuery != -1);
            trimstr = sstr(fromQuery + strlen("FROM"), orderByQuery);
            orderBy = q_str.substr(orderByQuery + strlen("ORDERBY"));
        }
    }

    columns = trimString(columns);
    trimstr = trimString(trimstr);
    where = trimString(where);
    orderBy = trimString(orderBy);

    return {columns, trimstr, where, orderBy};
}

string ccat(vector<string> num, char chr)
{
    string str = "";
    for (int i = 0; i < num.size(); i++) {
        str += num[i];
        if (i != num.size() - 1) {
            str += chr;            
        }

    }
    return str;
};

template <typename trimstr> bool typeCheck(string sign, trimstr num1, trimstr num2) {
    map<string, bool> dictionary = {
        {"<>", num1 != num2},
        {"==", num1 == num2},
        {"<=", num1 <= num2},
        {">=", num1 >= num2},
        {"<", num1 < num2},
        {">", num1 > num2},

    };
    return dictionary[sign];
}

bool typeCheck(string ctype, string sign, string left, string right)
{
    auto intConversion = [](string str) -> int
    { return str.empty() ? numeric_limits<int>::max() : stoi(str); };
    auto floatConversion = [](string str) -> float
    { return str.empty() ? numeric_limits<int>::max() : stoi(str); };

    if (ctype == "INT" || ctype == "FLOAT"){
        return typeCheck(sign, intConversion(left), intConversion(right));        
    }

    if (ctype == "CHAR" || ctype == "STRING") {
        return typeCheck(sign, left, right);        
    }
    assert(false);
}