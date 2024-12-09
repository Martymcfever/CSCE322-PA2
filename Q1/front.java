import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class front {

    // Variables
    static int charClass;
    static char nextChar;
    static int lexCounter = 0;
    static List<Character> lexme = new ArrayList<>();
    static int lexLen;
    static int token;
    static int nextToken;
    static String readInput;
    static String output = "";
    static boolean endOfFile = false; // New flag to track EOF

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

    public static void main(String[] args) {
        // Initialize reserved keywords
        reservedKeywords.add("int");
        reservedKeywords.add("float");
        reservedKeywords.add("char");
        reservedKeywords.add("double");

        // Replace the pathname with the path of your file
        // Influenced by buffered reader example from https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
        File input = new File("Enter local filepath");
        try (BufferedReader br = new BufferedReader(new FileReader(input))) {
            while ((readInput = br.readLine()) != null) {
                lexLen = readInput.length();
                System.out.println(readInput);
                
                getChar();

                do {
                    
                    lex();
                    System.out.println(lexme);
                } while (nextToken != EOF && lexCounter < lexLen);

                lexCounter = 0;

                
            }
            endOfFile = true; // Set the EOF flag at the end of the file
            lex(); // Call lex one last time to output EOF
        } catch (IOException e) {
            System.out.println("File not Found");
        }
    }

    public static int lookup(char ch) {
        switch (ch) {
            case '(':
                addChar();
                nextToken = LEFT_PAREN;
                output += "(";
                break;
            case ')':
                addChar();
                nextToken = RIGHT_PAREN;
                output += ")";
                break;
            case '+':
                addChar();
                nextToken = ADD_OP;
                output += "+";
                break;
            case '-':
                addChar();
                nextToken = SUB_OP;
                output += "-";
                break;
            case '*':
                addChar();
                nextToken = MULT_OP;
                output += "*";
                break;
            case '=':
                addChar();
                nextToken = ASSIGN_OP;
                output += "=";
                break;
            case '/':
                addChar();
                nextToken = DIV_OP;
                output += "/";
                break;
            case ',':
                addChar();
                nextToken = COMMA;
                output += ",";
                break;
            case ';':
                addChar();
                nextToken = SEMICOLON;
                output += ";";
                break;
            case '{':
                addChar();
                nextToken = LEFT_CURLY;
                output += "{";
                break;
            case '}':
                addChar();
                nextToken = RIGHT_CURLY;
                output += "}";
                break;
            case '.':
                addChar();
                nextToken = PERIOD;
                output += ".";
                break;
            default:
                addChar();
                nextToken = EOF;
                break;
        }
        return nextToken;
    }

    public static void addChar() {
        if (lexme.size() <= 98) {
            lexme.add(nextChar);
        } else {
            System.out.println("Error - lexeme is too long \n");
        }
    }

    public static void getChar() {
        if (lexCounter < lexLen) {
            nextChar = readInput.charAt(lexCounter);
            if (Character.isLetter(nextChar)) {
                charClass = LETTER;
            } else if (Character.isDigit(nextChar)) {
                charClass = DIGIT;
            } else {
                charClass = UNKNOWN;
            }

            lexCounter++;
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
                addChar();
                getChar();
                while (charClass == LETTER || charClass == DIGIT) {
                    output += nextChar;
                    addChar();
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
                addChar();
                getChar();
                boolean isFloat = false;
                while (charClass == DIGIT || (!isFloat && nextChar == '.')) {
                    if (nextChar == '.') {
                        isFloat = true;
                    }
                    output += nextChar;
                    addChar();
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
