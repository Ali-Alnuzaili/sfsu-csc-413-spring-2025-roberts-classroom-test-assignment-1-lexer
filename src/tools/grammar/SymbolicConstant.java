package tools.grammar;

public class SymbolicConstant {
    public enum Type {
        OPERATOR, SEPARATOR, KEYWORD, LITERAL
    }

    private String lexeme;
    private Type type;
    private String constantName;

    public SymbolicConstant(String lexeme, Type type, String constantName) {
        this.lexeme = lexeme;
        this.type = type;
        this.constantName = constantName;
    }

    public String getLexeme() {
        return this.lexeme;
    }

    public Type getType() {
        return this.type;
    }

    public String getConstantName() {
        return this.constantName;
    }
}
