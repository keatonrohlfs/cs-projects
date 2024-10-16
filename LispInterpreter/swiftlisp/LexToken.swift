import Foundation

extension StringProtocol {
    subscript(offset: Int) -> Character {
        self[index(startIndex, offsetBy: offset)]
    }
}

enum Object {
    case int(Int)
    case dbl(Double)
}

public class Token {
    var type = ""
    static let SYMBOLNAME = "SYMBOLNAME"
    static let NUMBER = "NUMBER"
    static let BUILTIN = "BUILTIN"
    final var text = ""
    var value: Object = Object.int(-1)
    var col = -1
    var line =  -1
    var arity = -1
    var optype = ""
    static let RELATIONAL = "RELATIONAL"
    static let ARITHMATIC = "ARITHMATIC"
    static let PRIMITIVE = "PRIMITIVE"
    static let PREDICATE = "PREDICATE"
    static let SYMBOL = "SYMBOL"
    static let CONTROL = "CONTROL"
    
    func toString() ->  String {
        return type + ":" + text + " "
    }
    
    //  optype SYMBOL
    static let OPEN_PAR: Token = makeBuiltIn(definition: "(")
    static let CLOSE_PAR: Token = makeBuiltIn(definition: ")")
    static let EOF: Token = makeBuiltIn(definition: "[EOF]")
    static let SEMI: Token = makeBuiltIn(definition: ";")
    static let NIL: Token = makeBuiltIn(definition: "()")
    static let TRUE: Token = makeBuiltIn(definition: "t")
    static let TIC: Token = makeBuiltIn(definition: "\\")   //  TEST
    
    //  optype ARITHMATIC,2
    static let PLUS: Token = makeBuiltIn(definition: "+", optype: ARITHMATIC, arity: 2)
    static let MINUS: Token = makeBuiltIn(definition: "-", optype: ARITHMATIC, arity: 2)
    static let MULT: Token = makeBuiltIn(definition: "*", optype: ARITHMATIC, arity: 2)
    static let DIV: Token = makeBuiltIn(definition: "/", optype: ARITHMATIC, arity: 2)
    static let MOD: Token = makeBuiltIn(definition: "%", optype: ARITHMATIC, arity: 2)
    
    //  optype RELATIONAL,2
    static let LT: Token = makeBuiltIn(definition: "<", optype: RELATIONAL, arity: 2)
    static let GT: Token = makeBuiltIn(definition: ">", optype: RELATIONAL, arity: 2)
    static let LTEQ: Token = makeBuiltIn(definition: "<=", optype: RELATIONAL, arity: 2)
    static let GTEQ: Token = makeBuiltIn(definition: ">=", optype: RELATIONAL, arity: 2)
    static let NOT: Token = makeBuiltIn(definition: "NOT", optype: RELATIONAL, arity: 1)
    
    //  not relational because doesn't require aritmatic types
    static let EQ: Token = makeBuiltIn(definition: "=", optype: PRIMITIVE, arity: 2)
    static let EQUAL: Token = makeBuiltIn(definition: "EQ?", optype: PRIMITIVE, arity: 2)
    
    //  optype PREDICATE,1
    static let NIL_PRED: Token = makeBuiltIn(definition: "NIL?", optype: PREDICATE, arity: 1)
    static let NULL_PRED: Token = makeBuiltIn(definition: "NULL?", optype: PREDICATE, arity: 1)
    static let ATOM_PRED: Token = makeBuiltIn(definition: "ATOM?", optype: PREDICATE, arity: 1)
    static let LIST_PRED: Token = makeBuiltIn(definition: "LIST?", optype: PREDICATE, arity: 1)
    static let SYMBOL_PRED: Token = makeBuiltIn(definition: "SYMBOL?", optype: PREDICATE, arity: 1)
    static let NUMBER_PRED: Token = makeBuiltIn(definition: "NUMBER?", optype: PREDICATE, arity: 1)
    
    //  optype PRIMITIVE,1|2|3
    static let DEFINE: Token = makeBuiltIn(definition: "DEFINE", optype: PRIMITIVE, arity: 3)
    static let CONS: Token = makeBuiltIn(definition: "CONS", optype: PRIMITIVE, arity: 2)
    static let CAR: Token = makeBuiltIn(definition: "CAR", optype: PRIMITIVE, arity: 1)
    static let CDR: Token = makeBuiltIn(definition: "CDR", optype: PRIMITIVE, arity: 1)
    static let PRINT: Token = makeBuiltIn(definition: "PRINT", optype: PRIMITIVE, arity: 1)
    static let QUIT: Token = makeBuiltIn(definition: "QUIT", optype: PRIMITIVE, arity: -1)
    
    //  optype CONTROL
    static let IF: Token = makeBuiltIn(definition: "IF", optype: CONTROL, arity: 3)
    static let SET: Token = makeBuiltIn(definition: "SET", optype: CONTROL, arity: 2)
    static let WHILE: Token = makeBuiltIn(definition: "WHILE", optype: CONTROL, arity: 2)
    static let BEGIN: Token = makeBuiltIn(definition: "BEGIN", optype: CONTROL, arity: 0)
    
    private init(source: String) {
        text = source
        type  = Token.BUILTIN
        optype = Token.SYMBOL
    }
    
    private init(source: String, optype: String, arity: Int) {
        text = source
        type  = Token.BUILTIN
        self.optype = optype
        self.arity = arity
    }
    
    public init(source: String, line: Int, col: Int) {
        text = source
        type  = Token.SYMBOLNAME
        self.line = line
        self.col = col
    }
    
    private static func makeBuiltIn(definition: String) -> Token {
        return Token(source: definition)
    }
    
    private static func makeBuiltIn(definition: String, optype: String, arity: Int) -> Token {
        return Token(source: definition, optype: optype, arity: arity)
    }
    
    public static func makeNumber(number: String, line: Int, col: Int) -> Token {
        do {
            return try Token(value: Int(number)!, line: line, col: col)
        } catch {
            return Token(source: number, line: line, col: col)
        }
    }
    
    private init(value: Int, line: Int, col: Int) throws {
        type = Token.NUMBER
        self.value = Object.int(value)
        self.text = "" + String(value)
        self.line = line
        self.col = col
    }
    
    public static func makeFloat(number: String, line: Int, col: Int) -> Token {
        do {
            return try Token(value: Double(number)!, line: line, col: col)
        } catch {
            return Token(source: number, line: line, col: col)
        }
    }
    
    private init(value: Double, line: Int, col: Int) throws {
        type = Token.NUMBER
        self.value = Object.dbl(value)
        self.text = "" + String(value)
        self.line = line
        self.col = col
    }
    
    public static func builtin(optr: String) -> Token? {
        switch optr.lowercased() {
        case ";":
            return SEMI
        case "\\":
            return TIC
        case "$":
            return EOF
        case "(":
            return OPEN_PAR
        case ")":
            return CLOSE_PAR
        case "+":
            return PLUS
        case "-":
            return MINUS
        case "*":
            return MULT
        case "/":
            return DIV
        case "%":
            return MOD
        case "=":
            return EQ
        case "<=":
            return LTEQ
        case "<":
            return LT
        case ">=":
            return GTEQ
        case ">":
            return GT
        case "print":
            return PRINT
            
        case "set":
            return SET
        case "not":
            return NOT
            
        case "null?":
            return NIL_PRED
        case "atom?":
            return ATOM_PRED
        case "list?":
            return LIST_PRED
        case "symbol?":
            return SYMBOL_PRED
        case "number?":
            return NUMBER_PRED
        case "eq?":
            return EQ
            
        case "quit":
            return QUIT
        case "define":
            return DEFINE
            
        case "if":
            return IF
        case "while":
            return WHILE
        case "begin":
            return BEGIN
        case "cons":
            return CONS
        case "car":
            return CAR
        case "cdr":
            return CDR
        default:
            return nil
        }
    }
}

public class Lex {
    public var currentToken: Token = Token.SEMI
    
    public func getToken(state: String) -> Token {
        if (currentToken === Token.EOF) { 
            print("Attempt to read past EOF")
            print("state: " + state)
            return Token.EOF
        }
        currentToken = nextToken()
        return currentToken
    }
    
    var isInteractiveMode = false
    private var source: [String] = []
    private var srcIndex = 0
    private var lineText: String? = ""
    private var lineCount = 0
    
    public func getLine() -> Int {
        return lineCount
    }
    
    public func setSource(sourceString: String) -> Void {
        source = [sourceString]
        srcIndex = 0
        lineText = ""
        lineCount = 0
    }
    
    private var col = 0
    
    public init(inputSource: String, isInteractiveMode: Bool) {
        source = inputSource.components(separatedBy: "\n")
        self.isInteractiveMode = isInteractiveMode
    }
    
    public static func setup(sourceFile: String, isInteractiveMode: Bool) -> Lex {
        if(!isInteractiveMode) {
            var io: String
            io = readFile(sourceFile: sourceFile)
            return Lex(inputSource: io, isInteractiveMode: false)
        } else {
            return Lex(inputSource: "", isInteractiveMode: true)

        }
    }
    


  
    private func fetchLine() -> Void {
        col = -1
        if (source.indices.contains(srcIndex) && source[srcIndex] != "") {
            lineCount += 1
            col = 0
            lineText = source[srcIndex]
            srcIndex += 1
        }
        else {
            col = -1
            lineText = nil
        }
        return
    }
    

    
    private func skipBlanks() -> Void {
        while(lineText != nil) {
            if (col < lineText!.count && lineText![col] != ";") {
                if (lineText?[col].isWhitespace == true) {
                    col += 1
                }
                else {
                    return
                }
            }
            else {
                fetchLine()
            }
        }
        return
    }
    
    private func foundToken(token: Token) -> Token {
        if (token === Token.EOF) {
            col = -1
            lineText = nil
        }
        else {
            col += token.text.count
        }
        return token
    }
    
    private func lookAhead(ch: Character) -> Bool {
        return (col + 1 < lineText!.count && lineText![col + 1] == ch) 
    }
    
    private func nextToken() -> Token {
        skipBlanks()
        if (col == -1 || lineText == nil) {
            return Token.EOF
        }
        switch lineText?[col] {
        case ";":
            return foundToken(token: Token.SEMI)
        case "\\":
            return foundToken(token: Token.TIC)
        case "$":
            return foundToken(token: Token.EOF)
        case "(":
            return foundToken(token: Token.OPEN_PAR)
        case ")":
            return foundToken(token: Token.CLOSE_PAR)
        case "+":
            return foundToken(token: Token.PLUS)
        case "-":
            return foundToken(token: Token.MINUS)
        case "*":
            return foundToken(token: Token.MULT)
        case "/":
            return foundToken(token: Token.DIV)
        case "%":
            return foundToken(token: Token.MOD)
        case "=":
            return foundToken(token: Token.EQ)
        case "<":
            if (lookAhead(ch: "=")) {
                return foundToken(token: Token.LTEQ)
            }
            return foundToken(token: Token.LT)
        case ">":
            if (lookAhead(ch: "=")) {
                return foundToken(token: Token.GTEQ)
            }
            return foundToken(token: Token.GT)
        default:
            if (lineText![col] >= "0" && lineText![col] <= "9") {
                var end = col + 1
                while (end < lineText!.count && lineText![end] >= "0" && lineText![end] <= "9") {
                    end += 1
                }
                if (end < lineText!.count && lineText![end] == ".") {
                    end += 1
                    while (end < lineText!.count && lineText![end] >= "0" && lineText![end] <= "9") {
                        end += 1
                    }
                    let index1 = lineText?.index(lineText!.startIndex, offsetBy: col)
                    let index2 = lineText?.index(lineText!.startIndex, offsetBy: end) 
                    let range = index1!..<index2!
                    return foundToken(token: Token.makeFloat(number: String(lineText![range]), line: lineCount, col: col))
                }
                else {
                    let index1 = lineText?.index(lineText!.startIndex, offsetBy: col)
                    let index2 = lineText?.index(lineText!.startIndex, offsetBy: end) 
                    let range = index1!..<index2!
                    return foundToken(token: Token.makeNumber(number: String(lineText![range]), line: lineCount, col: col))
                }
            }
            else if ((lineText?.lowercased()[col])! >= "a" && (lineText?.lowercased()[col])! <= "z") {
                var end = col + 1
                while (end < lineText!.count && (lineText?.lowercased()[end])! >= "a" && (lineText?.lowercased()[end])! <= "z") {
                    end += 1
                }
                if (end < lineText!.count && lineText![end] == "?") {
                    end += 1
                }
                let index1 = lineText?.index(lineText!.startIndex, offsetBy: col)
                let index2 = lineText?.index(lineText!.startIndex, offsetBy: end)
                let range = index1!..<index2!
                let matchText = lineText![range]
                
                switch matchText.lowercased() {
                case "t":
                    return foundToken(token: Token.TRUE)
                case "print":
                    return foundToken(token: Token.PRINT)
                case "set":
                    return foundToken(token: Token.SET)
                case "not":
                    return foundToken(token: Token.NOT)
                case "nil?":
                    return foundToken(token: Token.NIL_PRED)
                case "atom?":
                    return foundToken(token: Token.ATOM_PRED)
                case "list?":
                    return foundToken(token: Token.LIST_PRED)
                case "symbol?":
                    return foundToken(token: Token.SYMBOL_PRED)
                case "number?":
                    return foundToken(token: Token.NUMBER_PRED)
                case "eq?":
                    return foundToken(token: Token.EQ)
                case "quit":
                    return foundToken(token: Token.QUIT)
                case "define":
                    return foundToken(token: Token.DEFINE)
                case "if":
                    return foundToken(token: Token.IF)
                case "while":
                    return foundToken(token: Token.WHILE)
                case "begin":
                    return foundToken(token: Token.BEGIN)
                case "cons":
                    return foundToken(token: Token.CONS)
                case "car":
                    return foundToken(token: Token.CAR)
                case "cdr":
                    return foundToken(token: Token.CDR)
                default:
                    return foundToken(token: Token(source: String(matchText), line: lineCount, col: col))//Token(lineCount, lineCount, col))
                }
            }
            else {
                print("Unexpected characters on line(" + String(lineCount) + "):" + lineText!)
                print("This: \\" + String(lineText![col]) + "\\")
                return Token.EOF
            }
        }
    }

}


func readFile(sourceFile: String) -> String {
    let path = FileManager.default.currentDirectoryPath
    var fileURL = URL(fileURLWithPath: path)
    fileURL = fileURL.appendingPathComponent(sourceFile)
    var sourceText = "?"
    do {
        sourceText = try String(contentsOf: fileURL, encoding: .utf8)
        return sourceText
    }
    catch {
        print(error.localizedDescription)
        return error.localizedDescription
    }


}



extension String {
    
    func fileName() -> String {
        return URL(fileURLWithPath: self).deletingPathExtension().lastPathComponent
    }
    
    func fileExtension() -> String {
        return URL(fileURLWithPath: self).pathExtension
    }
}




