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

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 1;

            while ((line = reader.readLine()) != null) {
                System.out.printf("%3d: %s%n", lineNumber, line);

                // Improved Tokenization: Splits by spaces, parentheses, and special characters
                String[] words = line.split("(?<=[()=<>+\\-*/{}])|(?=[()=<>+\\-*/{}])|\\s+");

                int position = 0;
                for (String word : words) {
                    word = word.trim(); // Trim spaces to avoid leading spaces in tokens
                    if (!word.isEmpty()) {
                        TokenKind kind = classifyToken(word);
                        tokens.add(new Token(word, position, position + word.length() - 1, lineNumber, kind));
                        position += word.length() + 1; // Move to next token
                    }
                }

                lineNumber++;
            }

            // Print formatted tokens
            System.out.println("\nTokens:");
            for (Token token : tokens) {
                System.out.println(token);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath);
            e.printStackTrace();
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
            case "=>": return TokenKind.To; // New separator

            // Separators
            case "{": return TokenKind.LeftBrace;
            case "}": return TokenKind.RightBrace;
            case "(": return TokenKind.LeftParen;
            case ")": return TokenKind.RightParen;
            case ",": return TokenKind.Comma;

            // Identifiers & Numbers
            default:
                if (word.matches("^[a-zA-Z_][a-zA-Z0-9_]*$")) {
                    return TokenKind.Identifier; // Recognizes variable names like i, j, write
                }
                if (word.matches("^[0-9]+$")) {
                    return TokenKind.IntLit; // Recognizes numbers like 7, 42
                }
                if (word.matches("^0[xX][0-9a-fA-F]+$")) {
                    return TokenKind.HexLit; // Recognizes hexadecimal numbers (0x1a2F, 0XABC)
                }
                if (word.matches("^\"[a-zA-Z0-9]\"$")) {
                    return TokenKind.CharLit; // Recognizes char literals ("a", "9")
                }
                return TokenKind.BogusToken; // Default if nothing matches
        }
    }
}
