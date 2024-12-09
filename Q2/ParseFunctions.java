import java.util.HashSet;
import java.util.Set;

public class ParseFunctions {

    // Variables
    static int charClass;
    static char nextChar;
    static int lexCounter = 0;
    static String lexme = "";
    static int lexLen;
    static int token;
    static int nextToken;
    static String readInput;
    static String output = "";
    static boolean endOfFile = false; // New flag to track EOF

    static int counter = 0;

    // Reserved Keywords
    static Set<String> reservedKeywords = new HashSet<>();

    // Character Classes
    final static int LETTER = 0;
    final static int DIGIT = 1;
    final static int UNKNOWN = 99;

    // Token Codes
    final static int INT_LIT = 10;
    final static int IDENT = 11;
    final static int ASSIGN_OP = 20;
    final static int ADD_OP = 21;
    final static int SUB_OP = 22;
    final static int MULT_OP = 23;
    final static int DIV_OP = 24;
    final static int LEFT_PAREN = 25;
    final static int RIGHT_PAREN = 26;
    final static int EOF = -1;
    final static int COMMA = 30;
    final static int SEMICOLON = 31;
    final static int LEFT_CURLY = 32;
    final static int RIGHT_CURLY = 33;
    final static int PERIOD = 34;
    final static int KEYWORD = 40; // New token code for keywords
    final static int RETURN = 41;
    

    public static int lookup(char ch) {
        switch (ch) {
            case '(':
                
                nextToken = LEFT_PAREN;
                output += "(";
                break;
            case ')':
                
                nextToken = RIGHT_PAREN;
                output += ")";
                break;
            case '+':
                
                nextToken = ADD_OP;
                output += "+";
                break;
            case '-':
                
                nextToken = SUB_OP;
                output += "-";
                break;
            case '*':
                
                nextToken = MULT_OP;
                output += "*";
                break;
            case '=':
                
                nextToken = ASSIGN_OP;
                output += "=";
                break;
            case '/':
                
                nextToken = DIV_OP;
                output += "/";
                break;
            case ',':
                
                nextToken = COMMA;
                output += ",";
                break;
            case ';':
                
                nextToken = SEMICOLON;
                output += ";";
                break;
            case '{':
                
                nextToken = LEFT_CURLY;
                output += "{";
                break;
            case '}':
                
                nextToken = RIGHT_CURLY;
                output += "}";
                break;
            case '.':
                
                nextToken = PERIOD;
                output += ".";
                break;
            default:
                
                nextToken = EOF;
                break;
        }
        return nextToken;
    }
    
    public static void getChar() {
        if (counter < lexLen) {
            nextChar = lexme.charAt(counter);
            if (Character.isLetter(nextChar)) {
                charClass = LETTER;
            } else if (Character.isDigit(nextChar)) {
                charClass = DIGIT;
            } else {
                charClass = UNKNOWN;
            }

            counter++;
        } else {
            charClass = EOF;
        }
    }

    public static void getNonBlank() {
        while (Character.isWhitespace(nextChar)) {
            getChar();
        }
    }

    public static int lex() {
        if (endOfFile && charClass == EOF) {
            nextToken = EOF;
            System.out.printf("Next token is: %d, Next Lexeme is EOF%n", nextToken);
            return nextToken;
        }

        getNonBlank();
        switch (charClass) {
            case LETTER:
                output += nextChar;
                
                
                getChar();
                while (charClass == LETTER || charClass == DIGIT) {
                    output += nextChar;
                    
                    getChar();
                }
                if (reservedKeywords.contains(output)) {
                    nextToken = KEYWORD; // Recognize reserved keyword
                }
                else if(output.equals("return"))
                {
                    nextToken = RETURN;
                } 
                else {
                    nextToken = IDENT; // Otherwise, treat it as an identifier
                }
                break;
            case DIGIT:
                output += nextChar;
                
                getChar();
                boolean isFloat = false;
                while (charClass == DIGIT || (!isFloat && nextChar == '.')) {
                    if (nextChar == '.') {
                        isFloat = true;
                    }
                    output += nextChar;
                    
                    getChar();
                }
                nextToken = INT_LIT;
                break;
            case UNKNOWN:
                lookup(nextChar);
                getChar();
                break;
            case EOF:
                nextToken = EOF;
                break;
        }
        System.out.printf("Next token is: %d, Next Lexeme is %s%n", nextToken, output);
        output = "";
        return nextToken;
    }

    
}
