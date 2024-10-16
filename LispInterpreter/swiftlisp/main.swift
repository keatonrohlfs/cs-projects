import Foundation


var debugMode: Bool = true



print("Keaton's LISP")

let arguments = CommandLine.arguments
let argumentCount = arguments.count

if(argumentCount < 2) {
    LispInterpreter.run(sourceFile: "", isInteractive: true)
} else {
    LispInterpreter.run(sourceFile: arguments[1], isInteractive: false)
}

public class LispInterpreter {
    private init(producesTokens: Lex) {
        lexicalAnalyzer = producesTokens
    }
    
    /*************************************************************************
     * VAR
     *************************************************************************/
    
    private var lexicalAnalyzer: Lex
    private let NIL: SEXP = SEXP(type: "()")
    private let TRUE: SEXP = SEXP(type: "t")
    
    private func ERROR(msg: String, token: Token) -> Void {
        var note: String
        let flowerbox = "\n*********************************************************\n"
        if (token.line == -1) {
            note = "ERROR ::" + msg + token.text
        }
        else {
            note = "ERROR on Line, Col(" + String(token.line) + "," + String(token.col) + ")::" + msg + token.text
        }
        print(flowerbox + note + flowerbox)
    }
    
    private func ERROR(msg: String) -> Void {
        var note: String
        let flowerbox = "\n*********************************************************\n"
        note = "ERROR ::" + msg
        print(flowerbox + note + flowerbox)
        if (pos > 10) {
            showFrom(start: pos - 10, end: pos)
        }
        else {
            showFrom(start: 0, end: pos)
        }
        print(flowerbox)
    }
    
    
    /*************************************************************************
     * TOKEN MGT
     * INPUT
     *************************************************************************/
    func userinput(pos: Int, state: String) -> Token {
        while (inputleng <= pos) {
            userinput.append(lexicalAnalyzer.getToken(state: state))
            inputleng += 1
        }
        return userinput[pos]
    }
    
    func expect(t: Token, state: String) -> Void {
        if (userinput(pos: pos, state: state) === t) {
            pos += 1
        }
        else {
            ERROR(msg: "EXPECTED" + t.text + "found" + userinput(pos: pos, state: state).text)
        }
    }
    
    func isNumber(pos: Int, state: String) -> Bool {
        return isDigits(pos: pos, state: state) || userinput(pos: pos, state: state) === Token.MINUS && isDigits(pos: pos + 1, state: state)
    }
    
    func isDigits(pos: Int, state: String) -> Bool {
        return userinput(pos: pos, state: state).type == Token.NUMBER
    }
    
    func isValue(pos: Int, state: String) -> Bool {
        return userinput(pos: pos, state: state) === Token.TIC || isNumber(pos: pos, state: state) || userinput(pos: pos, state: state) === Token.TRUE || userinput(pos: pos, state: state) === Token.OPEN_PAR && userinput(pos: pos, state: state) === Token.CLOSE_PAR
    }
    
    /*************************************************************************
     * PARSE SEXP
     *************************************************************************/
    
    func parseList() -> SEXP {
        let car: SEXP
        let cdr: SEXP
        if (userinput(pos: pos, state: "parseList->)") === Token.CLOSE_PAR) {
            pos += 1
            return NIL
        }
        else {
            car = parseSExp()
            cdr = parseList()
            return LISTSXP(carval: car, cdrval: cdr)
        }
    }
    
    func intValueOf(num: Token) -> Int? {
        return Int(num.text)
    }
    
    func parseInt() -> SEXP {
        let value: Int
        if (userinput(pos: pos, state: "parseInt") === Token.MINUS && userinput(pos: pos + 1, state: "parseInt+1").type == Token.NUMBER) {
            value = -1 * intValueOf(num: userinput(pos: pos + 1, state: "parseInt+1"))!
            pos += 2
        }
        else {
            value = intValueOf(num: userinput(pos: pos, state: "parseInt"))!
            pos += 1
        }
        return NUMSXP(intval: value)
    }
    
    func parseSym() -> SEXP {
        let symbolname: String = parseName()
        return SYMSXP(symval: symbolname)
    }
    
    func parseSExp() -> SEXP {
        if (userinput(pos: pos, state: "parseSExp") === Token.TRUE) {
            pos += 1
            return TRUE
        }
        if (userinput(pos: pos, state: "parseSExp") === Token.OPEN_PAR) {
            pos += 1
            return parseList()
        }
        if (isNumber(pos: pos, state: "parseSExp")) {
            return parseInt()
        }
        return parseSym()
    }
    
    func parseVal() -> SEXP {
        if (userinput(pos: pos, state: "parseSExp") === Token.TIC) {
            pos += 1
        }
        return parseSExp()
    }
    
    /*************************************************************************
     * S-EXPRESSIONS
     *************************************************************************/
    
    func isTrueVal(s: SEXP) -> Bool {
        return s !== NIL
    }
    
    /*************************************************************************
     * EVALUATION
     *************************************************************************/
    
    func evalList(el: EXPLIST?, rho: ENV) -> VALUELIST? {
        if (el == nil) {
            return nil
        }
        let h: SEXP = eval(e: el!.head, rho: rho)
        let t: VALUELIST? = evalList(el: el?.tail, rho: rho)
        return VALUELIST(head: h, tail: t)
    }
    
    func applyCtrlOp(controlOP: Token, args: EXPLIST?, rho: ENV) -> SEXP {
        var s: SEXP = NIL
        switch controlOP.text {
        case "IF":
            if (isTrueVal(s: eval(e: args?.head, rho: rho))) {
                return eval(e: args?.tail!.head, rho: rho)
            }
            else {
                return eval(e: args?.tail!.tail!.head, rho: rho)
            }
        case "WHILE":
            s = eval(e: args?.head, rho: rho)
            while(s !== NIL) {
                s = eval(e: args?.tail!.head, rho: rho)
                s = eval(e: args?.head, rho: rho)
            }
            return s
        case "SET":
            s = eval(e: args?.tail!.head, rho: rho)
            let varble: String = (args?.head as! VAREXP).varble
            if (isBound(nm: varble, rho: rho)) {
                assign(nm: varble, s: s, rho: rho)
            }
            else if (isBound(nm: varble, rho: globalEnv)) {
                assign(nm: varble, s: s, rho: globalEnv)
            }
            else {
                bindVar(nm: varble, s: s, rho: globalEnv)
            }
            return s
        case "BEGIN":
            var argsVar: EXPLIST = args!
            while (argsVar.tail != nil) {
                s = eval(e: argsVar.head, rho: rho)
                argsVar = argsVar.tail!
            }
            s = eval(e: argsVar.head, rho: rho)
            return s
        default:
            return s
        }
    }
    
    func showFrom(start: Int, end: Int) -> Void {
      
        for i in start...end {
            if (i > start) {
                if ((userinput(pos: i - 1, state: "showFrom") === Token.CLOSE_PAR && userinput(pos: i, state: "showFrom") === Token.OPEN_PAR) || userinput(pos: i, state: "showFrom") === Token.EOF || userinput(pos: i, state: "showFrom") === Token.QUIT) {
                    break   // breaks to avoid printing next '(' from next statement
                }
                else {
                    print(" ", terminator: "")
                }
                
            }
            print(userinput(pos: i, state: "showFrom").text, terminator: "")

        }
        print()
    }
    
    /*************************************************************************
     * PARSE DEFINE
     *************************************************************************/
    
    func parseDef() -> String {
        expect(t: Token.OPEN_PAR, state: "parseDef")
        expect(t: Token.DEFINE, state: "parseDef")
        let fname: String = parseName()
        expect(t: Token.OPEN_PAR, state: "parseDef")
        let nl: NAMELIST = parseNL()!
        let e: EXP = parseEXP()
        expect(t: Token.CLOSE_PAR, state: "parseDef")
        newFunDef(fname: fname, nl: nl, e: e)
        return fname
    }
    
    func parseName() -> String {
        if (userinput(pos: pos, state: "parseName").type == Token.NUMBER) {
            ERROR(msg: "Expected name, instead read :", token: userinput(pos: pos, state: "parseName"))
            return "error"
        }
        let name: String = userinput(pos: pos, state: "parseName").text
        pos += 1
        return name
    }
    
    func parseNL() -> NAMELIST? {
        let nm: String
        let nl: NAMELIST?
        if (userinput(pos: pos, state: "parseNL") === Token.CLOSE_PAR) {
            pos += 1
            return nil
        }
        else {
            nm = parseName()
            nl = parseNL()
            return NAMELIST(head: nm, tail: nl)
        }
    }
    
    /*************************************************************************
     * PARSE EXP
     * PARSE APEXP
     *************************************************************************/
    
    func parseEXP() -> EXP {
        let nm: String
        let el: EXPLIST?
        if (userinput(pos: pos, state: "parseExp") === Token.OPEN_PAR) {
            pos += 1
            if (userinput(pos: pos, state: "parseExp") === Token.CLOSE_PAR) {
                pos += 1
                return VALEXP(sxp: NIL)
            }
            nm = parseName()
            el = parseEL()
            return APEXP(optr: nm, args: el)
        }
        else if (isValue(pos: pos, state: "parseExp")) {
            return VALEXP(sxp: parseVal())
        }
        else {
            return VAREXP(varble: parseName())
        }
    }
    
    func parseEL() -> EXPLIST? {
        if (userinput(pos: pos, state: "parseEL") === Token.EOF) {
            ERROR(msg: "Expected ) found EOF")
            return nil
        }
        if (userinput(pos: pos, state: "parseEL") === Token.CLOSE_PAR) {
            pos += 1
            return nil
        }
        else {
            let e: EXP = parseEXP()
            let el: EXPLIST? = parseEL()
            return EXPLIST(head: e, tail: el)
        }
    }
    
    func applyUserFun(nm: String, actuals: VALUELIST) -> SEXP {
        let fun: FUNDEF? = fetchFun(fname: nm)
        if (fun == nil) {
            ERROR(msg: "Undefined function:" + nm)
            return NIL
        }
        if (lengthNL(nl: fun!.formals) != lengthVL(vl: actuals)) {
            ERROR(msg: "Wrong number of arguments to: " + nm)
            return NIL
        }
        let rho: ENV = ENV(vars: fun?.formals, values: actuals)
        return eval(e: fun!.body, rho: rho)
    }
    
    func fetchFun(fname: String) -> FUNDEF? {
        var f: FUNDEF? = fundefs
        while (f != nil) {
            if (f?.funname == fname) {
                return f
            }
            else {
                f = f?.nextfundef
            }
        }
        return nil
    }
    
    private var fundefs: FUNDEF? = nil
    
    func newFunDef(fname: String, nl: NAMELIST, e: EXP) -> Void {
        if (fetchFun(fname: fname) == nil) {
            fundefs = FUNDEF(funname: fname, formals: nl, body: e, nextfundef: fundefs)
        }
        else {
            ERROR(msg: fname + " already installed")
        }
    }
    
    func eval(e: EXP?, rho: ENV) -> SEXP {
        let op: Token?
        
        if (e is VALEXP) {
            let s: VALEXP = e as! LispInterpreter.VALEXP
            return s.sxp
        }
        else if (e is VAREXP) {
            let v: VAREXP = e as! LispInterpreter.VAREXP
            if (isBound(nm: v.varble, rho: rho)) {
                return fetch(nm: v.varble, rho: rho)
            }
            if (isBound(nm: v.varble, rho: globalEnv)) {
                return fetch(nm: v.varble, rho: globalEnv)
            }
            ERROR(msg: "Undefined variable " + v.varble)
        }
        else if (e is APEXP) {
            let a: APEXP = e as! LispInterpreter.APEXP
            op = Token.builtin(optr: a.optr)
            if (op == nil) {
                return applyUserFun(nm: a.optr, actuals: evalList(el: a.args, rho: rho)!)
            }
            else {
                if (op?.optype == Token.CONTROL) {
                    return applyCtrlOp(controlOP: op!, args: a.args, rho: rho)
                }
                else {
                    return applyValueOp(op: op!, vl: evalList(el: a.args, rho: rho)!)
                }
            }
        }
        return NIL
    }
    
    
    /*************************************************************************
     * ENVIRONMENTS
     *************************************************************************/
    
    func emptyEnv() -> ENV {
        return ENV(vars: nil, values: nil)
    }
    
    func bindVar(nm: String, s: SEXP, rho: ENV) {
        rho.vars = NAMELIST(head: nm, tail: rho.vars)
        rho.values = VALUELIST(head: s, tail: rho.values)
    }
    
    func findVar(nm: String, rho: ENV) -> VALUELIST? { 
        var nl: NAMELIST? = rho.vars
        var vl: VALUELIST? = rho.values
        var found: Bool = false
        while (nl != nil && !found) {
            if (nl!.head == nm) {
                found = true
            }
            else {
                nl = nl?.tail
                vl = vl?.tail
            }
        }
        return vl
    }
    
    func assign(nm: String, s: SEXP, rho: ENV) -> Void {
        let varloc: VALUELIST = findVar(nm: nm, rho: rho)!
        varloc.head = s
    }
    
    func fetch(nm: String, rho: ENV) -> SEXP {
        let vl: VALUELIST = findVar(nm: nm, rho: rho)!
        return vl.head
    }
    
    func isBound(nm: String, rho: ENV) -> Bool {
        return findVar(nm: nm, rho: rho) != nil
    }
    
    
    /*************************************************************************
     * APPLY
     *************************************************************************/
    
    func applyArithOp(op: Token, n1: Int, n2: Int) -> NUMSXP {
        var result = 0
        switch op.text {
        case "+":
            result = n1 + n2
        case "-":
            result = n1 - n2
        case "*":
            result = n1 * n2
        case "/":
            result = n1 / n2
        case "%":
            result = n1 % n2
        default:
            result = 0
        }
        return NUMSXP(intval: result)
    }
    
    func applyRelOp(op: Token, n1: Int, n2: Int) -> SEXP {
        let result: Bool
        switch op.text {
        case "<":
            result = n1 < n2
        case ">":
            result = n1 > n2
        case "<=":
            result = n1 <= n2
        case ">=":
            result = n1 >= n2
        case "=":
            result = n1 == n2
        default:
            result = false
        }
        if (result) {
            return TRUE
        }
        else {
            return NIL
        }
    }
    
    //builtin arity op
    func applyValueOp(op: Token, vl: VALUELIST) -> SEXP {
        var result: SEXP = NIL
        var s1: SEXP = NIL
        var s2: SEXP = NIL
        if (op.arity != 0 && op.arity != lengthVL(vl: vl)) {
            ERROR(msg: "Wrong number of arguments to " + op.text + " expected" + String(op.arity) + " but found" + String(lengthVL(vl: vl)))
            return NIL
        }
        s1 = vl.head
        if (op.arity == 2) {
            s2 = vl.tail!.head
        }
        if (op.optype == Token.ARITHMATIC || op.optype == Token.RELATIONAL) {
            if (s1.type == "Number" && s2.type == "Number") {
                let n1: NUMSXP = s1 as! NUMSXP
                let n2: NUMSXP = s2 as! NUMSXP
                if (op.optype == Token.ARITHMATIC) {
                    result = applyArithOp(op: op, n1: n1.intval, n2: n2.intval)
                }
                else {
                    result = applyRelOp(op: op, n1: n1.intval, n2: n2.intval)
                }
            }
            else {
                ERROR(msg: "Non-arithmatic arguments to " + op.text)
            }
        }
        else if (op.arity == 2) {
            result = apply(op: op, s1: s1, s2: s2)
        }
        else {
            result = apply(op: op, s1: s1)
        }
        return result
    }
    
    func apply (op: Token, s1: SEXP, s2: SEXP) -> SEXP {
        var result: SEXP = NIL
        switch op.text {
        case "CONS":
            result = LISTSXP(carval: s1, cdrval: s2)
        case "EQ?", "=":
            if (s1 === NIL && s2 === NIL) {
                result = TRUE
            }
            else if (s1.type == "Number" && s2.type == "Number") {
                let n1: NUMSXP = s1 as! LispInterpreter.NUMSXP
                let n2: NUMSXP = s2 as! LispInterpreter.NUMSXP
                if (n1.intval == n2.intval) {
                    result = TRUE
                }
            }
            else if (s1.type == "Symbol" && s2.type == "Symbol") {
                let n1: SYMSXP = s1 as! LispInterpreter.SYMSXP
                let n2: SYMSXP = s2 as! LispInterpreter.SYMSXP
                if (n1.symval == n2.symval) {
                    result = TRUE
                }
            }
        default:
            ERROR(msg: "Apply was not CONS or EQ? or =")
        }
        return result
    }
    
    func apply (op: Token, s1: SEXP) -> SEXP {
        var result: SEXP = NIL
        switch op.text {
        case "NOT":
            if (s1 === NIL) {
                result = TRUE
            }
        case "CAR":
            if (s1.type == "List") {
                let concell: LISTSXP = s1 as! LispInterpreter.LISTSXP
                result = concell.carval
            }
            else {
                ERROR(msg: "car applied to non-list")
            }
        case "CDR":
            if (s1.type == "List") {
                let concell: LISTSXP = s1 as! LispInterpreter.LISTSXP
                result = concell.cdrval
            }
            else {
                ERROR(msg: "cdr applied to non-list")
            }
        case "NIL?", "NULL?":
            if (s1 === NIL) {
                result = TRUE
            }
        case "NUMBER?":
            if (s1.type == "Number") {
                result = TRUE
            }
        case "SYMBOL?":
            if (s1.type == "Symbol") {
                result = TRUE
            }
        case "LIST?":
            if (s1.type == "List") {
                result = TRUE
            }
        case "PRINT":
            print(s1.toString())
            result = s1
        default:
            ERROR(msg: "None of the symbols found")
        }
        return result
    }
    
    /*************************************************************************
     * RUN
     *************************************************************************/
    
    static func run(sourceFile: String, isInteractive: Bool) -> Void {
        let lisp: LispInterpreter = LispInterpreter(producesTokens: Lex.setup(sourceFile: sourceFile, isInteractiveMode: isInteractive))

            
        if(isInteractive) {
            // print("Interactive Mode disabled")
            lisp.rep()
        } else {
            lisp.repl()
        }
        
        
    }

    
    /*************************************************************************
     * READ-EVAL-PRINT-LOOP
     *************************************************************************/
    private var quittingTime = false
    
    private var globalEnv: ENV = ENV(vars: nil, values: nil)
    private var currentExp: EXP = EXP()
    
    private var userinput: [Token] = []
    private var inputleng = 0
    private var pos = 0
    private var prevpos = 0
    
    private func repl() -> Void {
        var result = ""
        while (!quittingTime) {
            result = ""
            prevpos = pos
            print("Input: ", terminator: "")

            if (userinput(pos: pos, state: "repl") === Token.QUIT || userinput(pos: pos, state: "repl") === Token.EOF) {
                quittingTime = true
                // pos += 1
                result = "quittingTime"
            }
            else if (userinput(pos: pos, state: "repl") === Token.OPEN_PAR && userinput(pos: pos + 1, state: "repl") === Token.DEFINE) {
                result = parseDef()
            }
            else {
                currentExp = parseEXP()
                result = eval(e: currentExp, rho: emptyEnv()).toString()
            }
            if (!(lexicalAnalyzer.isInteractiveMode)) {
                showFrom(start: prevpos, end: pos)

            }
            print("Output: " + result + "\n")

            
        }
    }
    
    
    private func rep() {
        var input: String = ""
        print("Interactive Mode:")
        
        while(!quittingTime) {
            print("> ", terminator: "")
            input = readLine()!

            input = input.lowercased()
            lexicalAnalyzer.setSource(sourceString: input)
            
    
            var result = ""
            prevpos = pos
            if (userinput(pos: pos, state: "repl") === Token.QUIT || userinput(pos: pos, state: "repl") === Token.EOF) {
                quittingTime = true
                pos += 1
                result = "quittingTime"
            }
            else if (userinput(pos: pos, state: "repl") === Token.OPEN_PAR && userinput(pos: pos + 1, state: "repl") === Token.DEFINE) {
                result = parseDef()
            }
            else {
                currentExp = parseEXP()
                result = eval(e: currentExp, rho: emptyEnv()).toString()
            }
            print("Output: " + result + "\n")
        }
    }
    

    /*************************************************************************
     * TYPES
     *************************************************************************/
    
    class SEXP {
        var type: String
        
        init(type: String) {
            self.type = type
        }
        
        public func toString() -> String {
            return type
        }
    }
    
    class NUMSXP: SEXP {
        var intval: Int
        
        init(intval: Int) {
            self.intval = intval
            super.init(type: "Number")
        }
        
        public override func toString() -> String {
            return String(intval)
        }
    }
    
    class SYMSXP: SEXP {
        var symval: String
        
        init(symval: String) {
            self.symval = symval
            super.init(type: "Symbol")
        }
        
        public override func toString() -> String {
            return symval
        }
    }
    
    class LISTSXP: SEXP {
        var carval: SEXP
        var cdrval: SEXP
        
        init(carval: SEXP, cdrval: SEXP) {
            self.carval = carval
            self.cdrval = cdrval
            super.init(type: "List")
        }
        
        public override func toString() -> String {
            var write = "(" + carval.type 
            var s1: SEXP = cdrval
            while(s1.type == "List") {
                let s2: LISTSXP = s1 as! LISTSXP
                write += " " + s2.carval.type
                s1 = s2.cdrval
            }
            write += ")"
            return write
        }
    }
    
    class EXP {
    }
    
    class VALEXP: EXP {
        var sxp: SEXP
        
        init(sxp: SEXP) {
            self.sxp = sxp
        }
    }
    
    class VAREXP: EXP {
        var varble: String
        
        init(varble: String) {
            self.varble = varble
        }
    }
    
    class APEXP: EXP {
        var optr: String
        var args: EXPLIST?
        
        init(optr: String, args: EXPLIST?) {
            self.optr = optr
            self.args = args
        }
    }
    
    class EXPLIST {
        var head: EXP
        var tail: EXPLIST? 
        
        init(head: EXP, tail: EXPLIST?) {
            self.head = head
            self.tail = tail
        }
    }
    
    class VALUELIST {
        var head: SEXP
        var tail: VALUELIST?
        
        init(head: SEXP, tail: VALUELIST?) {
            self.head = head
            self.tail = tail
        }
    }
    
    class NAMELIST {
        var head: String
        var tail: NAMELIST?
        
        init(head: String, tail: NAMELIST?) {
            self.head = head
            self.tail = tail
        }
    }
    
    class ENV {
        var vars: NAMELIST?
        var values: VALUELIST?
        
        init(vars: NAMELIST?, values: VALUELIST?) {
            self.vars = vars
            self.values = values
        }
    }
    
    class FUNDEF {
        var funname: String
        var formals: NAMELIST
        var body: EXP
        var nextfundef: FUNDEF?
        
        init(funname: String, formals: NAMELIST, body: EXP, nextfundef: FUNDEF?) {
            self.funname = funname
            self.formals = formals
            self.body = body
            self.nextfundef = nextfundef
        }
    }
    
    /************************************************************************* * DATA STRUCTURE OPS *************************************************************************/
    
    func lengthVL(vl: VALUELIST) -> Int {
        var len = 0
        var localvl: VALUELIST? = vl
        while(localvl != nil) { 
            len += 1
            localvl = localvl?.tail 
        }
        return len
    }
    
    
    func lengthNL(nl: NAMELIST) -> Int {
        var len = 0
        var localnl:  NAMELIST? = nl
        while (localnl != nil) {
            len += 1
            localnl = localnl?.tail
        }
        return len
    }
    
}
