package lexer;

import lexer.daos.Token;
import lexer.daos.TokenKind;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("usage: java lexer.Lexer filename.x");
            return;
        }

        String filePath = args[0];
        List<Token> tokens = new ArrayList<>();
        List<String> sourceLines = new ArrayList<>();
        int lineNumber = 1;  

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Read and store all lines
            while ((line = reader.readLine()) != null) {
                sourceLines.add(line);
                lineNumber++;
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
            return;
        }

        // Print source file lines with line numbers
        System.out.println("\nSource Code:");
        for (int i = 0; i < sourceLines.size(); i++) {
            System.out.printf("%3d: %s%n", i + 1, sourceLines.get(i));
        }

        // Tokenizing the source code
        System.out.println("\nTokens:");
        lineNumber = 1;  
        for (String line : sourceLines) {
            String[] words = line.split("(?<=[()=<>+\\-*/{}])|(?=[()=<>+\\-*/{}])|\\s+");

            int position = 0;
            for (String word : words) {
                word = word.trim(); 
                if (!word.isEmpty()) {
                    TokenKind kind = classifyToken(word);
                    tokens.add(new Token(word, position, position + word.length() - 1, lineNumber, kind));
                    position += word.length() + 1;
                }
            }
            lineNumber++;
        }

        // Print formatted tokens
        for (Token token : tokens) {
            System.out.println(token);
        }
    }

    // Classifies tokens based on keyword/operator
    private static TokenKind classifyToken(String word) {
        switch (word) {
            // Keywords
            case "program": return TokenKind.Program;
            case "int": return TokenKind.IntType;
            case "bool": return TokenKind.BoolType;
            case "char": return TokenKind.CharType;
            case "hex": return TokenKind.HexType;
            case "from": return TokenKind.From;
            case "step": return TokenKind.Step;
            case "return": return TokenKind.Return;
            case "if": return TokenKind.If;
            case "while": return TokenKind.While;

            // Operators
            case "=": return TokenKind.Assign;
            case "==": return TokenKind.Equal;
            case "!=": return TokenKind.NotEqual;
            case ">": return TokenKind.Greater;
            case ">=": return TokenKind.GreaterEqual;
            case "<": return TokenKind.Less;
            case "<=": return TokenKind.LessEqual;
            case "+": return TokenKind.Plus;
            case "-": return TokenKind.Minus;
            case "*": return TokenKind.Multiply;
            case "/": return TokenKind.Divide;
            case "=>": return TokenKind.To;

            // Separators
            case "{": return TokenKind.LeftBrace;
            case "}": return TokenKind.RightBrace;
            case "(": return TokenKind.LeftParen;
            case ")": return TokenKind.RightParen;
            case ",": return TokenKind.Comma;

            // Identifiers & Numbers
            default:
                if (word.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
                    return TokenKind.Identifier;
                }
                if (word.matches("^[0-9]+$")) {
                    return TokenKind.IntLit;
                }
                if (word.matches("^0[xX][0-9a-fA-F]+$")) {
                    return TokenKind.HexLit;
                }
                if (word.matches("^\"[a-zA-Z0-9]\"$")) {
                    return TokenKind.CharLit;
                }
                return TokenKind.BogusToken;
        }
    }
}
