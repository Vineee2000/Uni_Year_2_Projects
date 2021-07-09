// Parser class for CE204 assignment 2, 2020
// eleven methods need to be modified to use your contructor(s) - eight for part 3 and three for part 6
// (these are the static ExpTree methods at the end of the Parser class)
// these methods currently return null to allow this file to compile
// (in order to compile the current version you must have an ExpTree class in a file ExpTree.java
// but it doesn't have to have any attributes or methods)

// a getLine method has been supplied to allow you to read a line of text from the keyboard
// when asking the user if another expression is wanted;
// it strips leading and trailing white space and loops until a non-empty line is input

import java.io.*;

public class Parser {
    private Lexer lex;

    public Parser() {
        lex = new Lexer();
    }

    public String getLine() {
        return lex.getLine();
    }

    public ExpTree parseLine() {
        ExpTree defs = null;
        lex.init();
        lex.getToken();
        if (lex.token == Lexer.let) {
            lex.getToken();
            defs = parseDefList();
            if (lex.token != Lexer.in)
                throw new ParseException("'in' expected");
            lex.getToken();
        }
        ExpTree result = parseExp(true);
        if (lex.token != Lexer.semico)
            throw new ParseException("semicolon expected");
        if (defs != null)
            result = makeLetTree(defs, result);
        return result;
    }

    private ExpTree parseExp(boolean idsAllowed) {
        ExpTree result = parseTerm(idsAllowed);
        {
            while (lex.token == Lexer.plus || lex.token == Lexer.minus) {
                int op = lex.token;
                lex.getToken();
                if (op == Lexer.plus)
                    result = makePlusTree(result, parseTerm(idsAllowed));
                else
                    result = makeMinusTree(result, parseTerm(idsAllowed));
            }
        }
        return result;
    }

    private ExpTree parseTerm(boolean idsAllowed) {
        ExpTree result = parsePow(idsAllowed);
        {
            while (lex.token == Lexer.times || lex.token == Lexer.div || lex.token == Lexer.mod) {
                int op = lex.token;
                lex.getToken();
                if (op == Lexer.times)
                    result = makeTimesTree(result, parsePow(idsAllowed));
                else if (op == Lexer.div)
                    result = makeDivideTree(result, parsePow(idsAllowed));
                else
                    result = makeModTree(result, parsePow(idsAllowed));
            }
        }
        return result;
    }

    private ExpTree parsePow(boolean idsAllowed) {
        ExpTree result = parseOpd(idsAllowed);
        if (lex.token == Lexer.pow) {
            lex.getToken();
            result = makePowerTree(result, parsePow(idsAllowed));
        }
        return result;
    }

    private ExpTree parseOpd(boolean idsAllowed) {
        ExpTree result;
        switch (lex.token) {
            case Lexer.num:
                result = makeNumberLeaf(lex.numval);
                lex.getToken();
                return result;
            case Lexer.id:
                if (!idsAllowed)
                    throw new ParseException("identifier not allowed in identifier definition");
                result = makeIdLeaf(lex.idval);
                lex.getToken();
                return result;
            case Lexer.lp:
                lex.getToken();
                result = parseExp(idsAllowed);
                if (lex.token != Lexer.rp)
                    throw new ParseException("right parenthesis expected");
                lex.getToken();
                return result;
            default:
                throw new ParseException("invalid operand");
        }
    }

    private ExpTree parseDefList() {
        ExpTree result = parseDef();
        while (lex.token == Lexer.and) {
            lex.getToken();
            result = makeAndTree(result, parseDef());
        }
        return result;
    }

    private ExpTree parseDef() {
        if (lex.token != Lexer.id)
            throw new ParseException("definition must start with identifier");
        String id = lex.idval;
        lex.getToken();
        if (lex.token != Lexer.eq)
            throw new ParseException("'=' expected");
        lex.getToken();
        return makeDefTree(id, parseExp(false));
    }

    // the next eight methods need to be modified for part 3 of the assignment

    static ExpTree makeNumberLeaf(int n) {
        System.out.println("number " + n);
        return new ExpTree(n);
        // this method should return a new number leaf with value n created using your constructor
        // if you've used the abstract class approach you will probably need something like
        // return new NumLeaf(n);
        // if you've used an ExpTree class that stores the node kind you will probably need something like
        // return new ExpTree(ExpTree.numNode, n, null, null);
    }

    static ExpTree makeIdLeaf(String s) {
        return new ExpTree(s);
        // this method should return a new id leaf with value s
    }

    static ExpTree makePlusTree(ExpTree l, ExpTree r) {
        return new OperatorNode("+", l, r);
        // this method should return a new plus node with children l and r created using your constructor
        // if you've used the abstract class approach you will probably need something like
        // return new OpNode('+', l, r);
        // or
        // return new PlusNode(l, r);
        // if you've used an ExpTree class that stores the node kind you will probably need something like
        // return new ExpTree(ExpTree.opNode, '+', l, r);
    }

    static ExpTree makeMinusTree(ExpTree l, ExpTree r) {
        return new OperatorNode("-", l, r);
        // this method should return a new minus node with children l and r
    }

    static ExpTree makeTimesTree(ExpTree l, ExpTree r) {
        return new OperatorNode("*", l ,r);
        // this method should return a new times node with children l and r
    }

    static ExpTree makeDivideTree(ExpTree l, ExpTree r) {
        return new OperatorNode("/", l, r);
        // this method should return a new divide node with children l and r
    }

    static ExpTree makeModTree(ExpTree l, ExpTree r) {
        return new OperatorNode("%", l, r);
        // this method should return a new mod (%) node with children l and r
    }

    static ExpTree makePowerTree(ExpTree l, ExpTree r) {
        return new OperatorNode("^", l ,r);
        // this method should return a new power (^) node with children l and r
    }

    // the next three methods need to be modified for part 6 of the assignment - do not modify them if you have not attempted part 6

    static ExpTree makeLetTree(ExpTree l, ExpTree r) { // remove the following line if you modify this method; leave it here if you do not attempt part 6
        return new LetNode(l, r);
        // this method should return a new let node with children l and r
    }

    static ExpTree makeAndTree(ExpTree l, ExpTree r) {
        return new DefinitionsNode(ExpTree.AND, l, r);
        // this method should return a new and node with children l and r
    }

    static ExpTree makeDefTree(String s, ExpTree t) {
        return new DefinitionsNode(ExpTree.EQUALS, new ExpTree(s), t);
        // this method should return a new definition node with identifier s and child t
        // if your definition nodes have 2 children you should put a new id leaf containing c in the left child and use t as the right child
    }

    public static void main(String[] args) {
        Parser p = new Parser();
        ExpTree t = p.parseLine();
    }

}



