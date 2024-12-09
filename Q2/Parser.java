import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Parser extends ParseFunctions
{
  
  public static void main(String[] args)
  {
    
    // Replace the pathname with the path of your file
    // Influenced by buffered reader example from https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
    File input = new File("C://Users//greym//CSCE322-PA2//front.txt");
    try (BufferedReader br = new BufferedReader(new FileReader(input))) {
        while ((readInput = br.readLine()) != null) {
          ParseFunctions.lexme += (readInput);
        }
    } catch (IOException e) {
        System.out.println("File not Found");
    }
    ParseFunctions.lexLen = ParseFunctions.lexme.length();
    // System.out.println(ParseFunctions.lexme);
    ParseFunctions.reservedKeywords.add("int");
    ParseFunctions.reservedKeywords.add("float");
    ParseFunctions.reservedKeywords.add("char");
    ParseFunctions.reservedKeywords.add("double");
    getChar();
    System.out.println("Enter <function>");
    function();
  }
  
  public static void function()
  {
    
    lex();
    System.out.println("Enter <type>");
    type();
    System.out.println("Enter <ident>");
    ident();

    if (nextToken == LEFT_PAREN)
    {
      lex();
      System.out.println("Enter <params>");
      params();
    } 
    
    if (nextToken == RIGHT_PAREN) 
    {
      lex(); 
    }
    
    if (nextToken == LEFT_CURLY)
    {
      lex();
      System.out.println("Enter <statements>");
      statements();
    }
    else
    {
      lex();
    }
    
    System.out.println("Exit <function>");

  }

  public static void params()
  {
  
    System.out.println("Enter <param>");
    param();

    if (nextToken == COMMA)
    {
      lex();
      System.out.println("Enter <params>");
      params();
    }

    System.out.println("Exit <params>");
  }

  public static void param()
  {
    System.out.println("Enter <type>");
    type();
    System.out.println("Enter <ident>");
    ident();
    System.out.println("Exit <param>");
  }

  public static void statements()
  {
    while (nextToken != RIGHT_CURLY)
    {
      if (nextToken == RETURN)
      {
        lex();
        System.out.println("Enter <returns>");
        returns();
      }
      else
      {
        lex();
        System.out.println("Enter <assign>");
        assign();
      }
      
    }
    System.out.println("Exit <statements>");
  }

  public static void assign()
  {
    System.out.println("Enter <ident>");
    ident();
    if (nextToken == ASSIGN_OP)
    {
      lex();
      expr();
    }
    System.out.println("Exit <assign>");
  }

  public static void expr()
  {
    System.out.println("Enter <term>");
    term();

    while (nextToken == ADD_OP || nextToken == SUB_OP)
    {
      lex();
      term();
    }
    System.out.println("Exit <expr>");
  }

  public static void term()
  {
   
    while (nextToken == MULT_OP || nextToken == DIV_OP)
    {
      lex();
      System.out.println("Enter <factor>");
      factor();
    }
    System.out.println("Exit <term>");
  }

  public static void factor()
  {
    System.out.println("Enter <factor>");
    if (nextToken == IDENT)
    {
      System.out.println("Enter <ident>");
      ident();
    }
    else if (nextToken == INT_LIT)
    {
      System.out.println("Enter <constant>");
      constant();
    }
    else
    {
      if (nextToken == LEFT_PAREN)
      {
        if (nextToken == KEYWORD)
        {
          System.out.println("Enter <type>");
          type();
        }
        else
        {
            System.out.println("Enter <expr>");
          expr();
        }

        if (nextToken == RIGHT_PAREN)
        {
          lex();
        }
      }
    }
    System.out.println("Exit <factor>");
  }

  public static void ident()
  {
    if (nextToken == IDENT)
    {
      lex();
    }
    System.out.println("Exit <ident>");
  }

  public static void type()
  {
    if (nextToken == KEYWORD)
    {
      lex();
    }
    System.out.println("Exit <type>");
  }

  public static void constant()
  {
    lex();
    System.out.println("Exit <constant>");
  }

  public static void returns()
  {
    System.out.print("return ");
    ident();
    System.out.println("Exit <returns>");
  }

}