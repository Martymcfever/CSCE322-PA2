# Programing Assignment 2

###  Contributor
- Grey McGlaughlin - Q1 and Q2
- Taylor Runge - Q1 and Q2

### Compile Instructions
- Install the latest version of [Java](https://www.java.com)
- In the ```Q1/front.java``` and ```Q2/Parser.java``` files set your local filepath to the included ```front.txt``` and ```parser.txt``` files. The location to do so is marked with comments and can be found on <ins>**line 57**</ins> and <ins>**line 14**</ins> repectivly.
- In your terminal navigate to the folder that contains either ```front.java``` or ```Parser.java```and run the command ```java filename.java``` replacing filename with the file you would like to run
  - Note that ```ParseFunctions.java``` is a helper file to ```Parser.java``` and is not run directly

## Question Notes
### Q1

The output for Q1 is a lexical analysis of the contents of ```front.txt```

### Q2

The output of Q2 extends upon the lexical analiser of Q1 to give a parse trace for the contents of ```pareser.txt```.

It uses the following gramer rules
```
<function> -> <type> <ident> "(" <params> ")" "{" <statements> "}"
<params> -> <param> | <param> , <params>
<param> -> <type> <ident>
<statements> -> <assign> ";" | <return> ";" |  <assign> ";" <statements>
<assign> -> <ident> "=" <expr>

<expr> -> <term> | <term> {( + | - )  <term>}
<term> -> <factor> | { ( * |  / ) <factor> }
<factor> -> <ident> |  <constant> | "(" <expr> ")" | "(" <type> ")"

<ident> -> characters
<constant> -> numbers
<type> -> var types

<return> -> "return" <ident> ";"
```

The results of our testing gave us the trace for this [Parse Tree](./Q2/CSCE322%20Parse%20tree.pdf)

### Assumptions

The one assumption that we make is for Q2 and its that typecasting is done by the multiplication opperand.