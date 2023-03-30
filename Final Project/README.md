This project involves implementing a simplified SELECT statement to retrieve information from a multi-level security (MLS) table with an additional "TC" attribute indicating classification levels. The SELECT statement has SELECT, FROM, WHERE, and ORDERBY clauses. The SELECT and FROM clauses are required, while the WHERE and ORDERBY clauses are optional. The project provides examples of SELECT statements and the structure of the tables.

The program should be written in C or C++ and take an integer (1-4) as a command-line argument, indicating the user's security clearance. Users cannot read up; for example, a user with a Secret clearance (3) cannot access rows with TC > 3. The program will process the SELECT statement and output results in a CSV format with column names as the first row.

Useful C functions for this project include fgets, strsep, strstr, strchr, strncpy, sprintf, and sscanf. The program should handle various line terminations and ensure the removal of any leading and trailing spaces or white spaces for string values.