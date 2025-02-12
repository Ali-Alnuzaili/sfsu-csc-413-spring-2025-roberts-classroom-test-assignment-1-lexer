package tools.grammar;

public class NonTerminal extends Node {
    private String name;

    public NonTerminal(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
