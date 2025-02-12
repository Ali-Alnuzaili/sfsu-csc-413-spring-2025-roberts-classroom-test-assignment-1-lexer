package tools.readers;

import tools.exceptions.GrammarException;
import tools.grammar.Grammar;

public abstract class LineReader {
    protected Grammar grammar;

    public LineReader(Grammar grammar) {
        this.grammar = grammar;
    }

    public abstract void readLine(GrammarReader reader) throws GrammarException;
}
