package tools.readers;

import tools.exceptions.GrammarException;
import tools.grammar.Grammar;

public class AstWithSymbolReader extends LineReader {

    public AstWithSymbolReader(Grammar grammar) {
        super(grammar);
    }

    @Override
    public void readLine(GrammarReader reader) throws GrammarException {
        String line = reader.next().trim();

        if (line.indexOf("#") > -1) {
            line = line.substring(0, line.indexOf("#")).trim();
        }

        if (reader.isDebugging()) {
            System.out.printf("Reading AST with symbol line: %s%n", line);
        }

        this.grammar.addAstWithSymbol(line);
    }

}
