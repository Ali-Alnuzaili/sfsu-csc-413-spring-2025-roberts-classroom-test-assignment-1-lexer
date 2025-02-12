package lexer.daos;

public class Symbol {
    public final TokenKind kind;
    public final String lexeme;

    public Symbol(TokenKind kind, String lexeme) {
        this.kind = kind;
        this.lexeme = lexeme;
    }
}
