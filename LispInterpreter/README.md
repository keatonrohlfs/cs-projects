# LISP_Interpreter
Keaton Rohlfs' LISP Interpreter for CS 403

To compile, run "make" to build the program

The make file runs the following command
    swiftc -o swiftlisp main.swift LexToken.swift

Execute using ./swiftlisp filename
    - if filename is left blank, program will run in interactive mode
    - program will read files put in local directory

Examples:
    ./swiftlisp data.lisp (File Mode)
    ./swiftlisp (Interactive Mode)

My test cases are written in data.lisp. The resulting output can be seen in dataOutput.txt
