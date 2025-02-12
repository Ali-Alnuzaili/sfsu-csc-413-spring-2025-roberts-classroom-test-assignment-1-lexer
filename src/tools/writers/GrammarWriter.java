package tools.writers;

import tools.grammar.Grammar;

public abstract class GrammarWriter {
    protected Grammar grammar;

    public GrammarWriter(Grammar grammar) {
        this.grammar = grammar;
    }

    public abstract void write() throws Exception;
}
