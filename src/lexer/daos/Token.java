package lexer.daos;

public class Token {
    private String lexeme;
    private int left;
    private int right;
    private int lineNumber;
    private TokenKind tokenKind;

    public Token(String lexeme, int left, int right, int lineNumber, TokenKind tokenKind) {
        this.lexeme = lexeme;
        this.left = left;
        this.right = right;
        this.lineNumber = lineNumber;
        this.tokenKind = tokenKind;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String toString() {
        return String.format("%-20s left: %-8d right: %-8d line: %-8d %s", lexeme, left, right, lineNumber, tokenKind);
    }
}

