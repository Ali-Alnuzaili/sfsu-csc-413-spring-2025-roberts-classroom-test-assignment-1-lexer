package tools.grammar;

public class Terminal extends Node {
    private String lexeme;
    private SymbolicConstant symbolicConstant;

    public Terminal(String lexeme, SymbolicConstant symbolicConstant) {
        super();

        this.lexeme = lexeme;
        this.symbolicConstant = symbolicConstant;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public SymbolicConstant getSymbolicConstant() {
        return this.symbolicConstant;
    }

    public SymbolicConstant.Type getType() {
        return this.symbolicConstant.getType();
    }
}
