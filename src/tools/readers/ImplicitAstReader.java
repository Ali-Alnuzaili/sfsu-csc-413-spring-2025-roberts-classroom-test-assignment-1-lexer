package tools.readers;

import tools.exceptions.GrammarException;
import tools.grammar.Grammar;

public class ImplicitAstReader extends LineReader {

    public ImplicitAstReader(Grammar grammar) {
        super(grammar);
    }

    @Override
    public void readLine(GrammarReader reader) throws GrammarException {
        String line = reader.next().trim();

        if (line.indexOf("#") > -1) {
            line = line.substring(0, line.indexOf("#")).trim();
        }

        if (reader.isDebugging()) {
            System.out.printf("Reading implicit AST line: %s%n", line);
        }

        this.grammar.addImplicitAst(line);
    }

}
