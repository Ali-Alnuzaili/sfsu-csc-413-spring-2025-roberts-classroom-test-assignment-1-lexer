package tools.readers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

import tools.exceptions.GrammarException;
import tools.grammar.Grammar;

public class GrammarReader implements Iterator<String> {
    public enum Mode {
        PRODUCTIONS, OPERATORS, SEPARATORS, UNKNOWN, INITIALIZED, IMPLICIT_ASTS, ASTS_WITH_SYMBOLS
    }

    private List<String> lines;
    private int index;

    private Mode mode;
    private LineReader current;

    private Grammar grammar;

    private boolean debug;

    public GrammarReader(Path grammarDefinitionPath) throws IOException {
        this(grammarDefinitionPath, false);
    }

    public GrammarReader(Path grammarDefinitionPath, boolean debug) throws IOException {
        this.lines = Files.readAllLines(grammarDefinitionPath).stream().filter(line -> !line.isBlank())
                .toList();
        this.index = 0;
        this.mode = Mode.INITIALIZED;
        this.debug = debug;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.lines.size();
    }

    @Override
    public String next() {
        return this.lines.get(this.index++);
    }

    public Grammar read() throws GrammarException {
        this.grammar = new Grammar();

        while (this.hasNext()) {
            if (this.isDebugging()) {
                System.out.printf("Reading line %3d: %s%n", this.index, this.lines.get(this.index));
            }

            this.getNextLine();
            this.current.readLine(this);
        }

        return this.grammar;
    }

    private void getNextLine() throws GrammarException {
        if (this.grammar == null) {
            throw new GrammarException("Grammar not initialized");
        }

        if (this.mode == Mode.INITIALIZED || this.lines.get(this.index).startsWith("#")) {
            this.current = new CommentReader(this.grammar);
        } else if (this.mode == Mode.PRODUCTIONS) {
            this.current = new ProductionReader(this.grammar);
        } else if (this.mode == Mode.OPERATORS) {
            this.current = new OperatorReader(this.grammar);
        } else if (this.mode == Mode.SEPARATORS) {
            this.current = new SeparatorReader(this.grammar);
        } else if (this.mode == Mode.IMPLICIT_ASTS) {
            this.current = new ImplicitAstReader(this.grammar);
        } else if (this.mode == Mode.ASTS_WITH_SYMBOLS) {
            this.current = new AstWithSymbolReader(this.grammar);
        } else {
            throw new GrammarException("Invalid mode");
        }
    }

    public void setMode(Mode mode) {
        if (this.isDebugging()) {
            System.out.printf("Setting mode to %s%n", mode);
        }

        this.mode = mode;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public boolean isDebugging() {
        return this.debug;
    }
}
