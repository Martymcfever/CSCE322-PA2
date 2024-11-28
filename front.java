// Imports
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class front
{


    // Variables
    static int charClass;
    static char nextChar;
    static int lexCounter = 0;
    static List lexme = new ArrayList();
    static int lexLen;
    static int token;
    static int nextToken;
    static String readInput;
    static String output = "";


    // Character Classes
    final static int LETTER = 0;
    final static int DIGIT = 1;
    final static int UNKNOWN = 99;

    // Tokken Codes
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

    public static void main(String[] args)
    {
      //Replace the pathname with the path of your file
      File input = new File("C://Users//greym//CSCE322-PA2//front.txt");
      try (BufferedReader br = new BufferedReader(new FileReader(input))) {
        while((readInput = br.readLine()) != null)
        {
          lexLen = readInput.length();
          getChar();
          
          do
          {
            lex();
          } while (nextToken != EOF);
          lexCounter = 0;
        }
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
        default:
          addChar();
          nextToken = EOF;
          break;

      }
      return nextToken;
    }

    public static void addChar()
    {
      if (lexme.size() <= 98)
      {
        lexme.add(nextChar);
      }
      else
      {
        System.out.println("Error - lexeme is too long \n");
      }
    }

    public static void getChar()
    {
        if(lexCounter != lexLen)
        {
          nextChar = readInput.charAt(lexCounter);
          if(Character.isLetter(nextChar))
          {
            charClass = LETTER;
          }
          else if (Character.isDigit(nextChar))
          {
            charClass = DIGIT;
          }
          else
          {
            charClass = UNKNOWN;
          }
          
          lexCounter++;

        }
        else
        {
          charClass = EOF;
        }
    }

    public static void getNonBlank()
    {
      while (Character.isWhitespace(nextChar))
      {
        getChar();
      }
      
    }

    public static int lex()
    {
      
      getNonBlank();
      switch (charClass) 
      {
        case LETTER:
          output += nextChar;
          addChar();
          getChar();
          while (charClass == LETTER || charClass == DIGIT)
          {
            output += nextChar;
            addChar();
            getChar();
            
          }
          nextToken = IDENT;
          break;
        case DIGIT:
          output += nextChar;
          addChar();
          getChar();
          while (charClass == DIGIT)
          {
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
          // lexme.set(0, 'E');
          // lexme.set(1, 'O');
          // lexme.set(2, 'F');
          output += "EOF";
          break;
      }
      System.out.printf("Next token is: %d, Next Lexem is %s \n", nextToken,output);
      output = "";
      return nextToken;
    }

}
