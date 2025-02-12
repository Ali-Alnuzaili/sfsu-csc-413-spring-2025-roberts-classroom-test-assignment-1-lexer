package tools.readers;

import tools.exceptions.GrammarException;
import tools.grammar.Grammar;

public class CommentReader extends LineReader {

    public CommentReader(Grammar grammar) {
        super(grammar);
    }

    @Override
    public void readLine(GrammarReader reader) throws GrammarException {
        if (!reader.hasNext()) {
            throw new GrammarException("Unexpected end of file in CommentReader#readLine");
        }

        String line = reader.next();

        if (!line.startsWith("#")) {
            throw new GrammarException(String.format("Expected comment, but got \"%s\"", line));
        }

        line = line.substring(1).trim();

        switch (line) {
            case "GRAMMAR_PRODUCTIONS":
                reader.setMode(GrammarReader.Mode.PRODUCTIONS);
                break;
            case "SEPARATOR_NAMES":
                reader.setMode(GrammarReader.Mode.SEPARATORS);
                break;
            case "OPERATOR_NAMES":
                reader.setMode(GrammarReader.Mode.OPERATORS);
                break;
            case "IMPLICIT_ASTS":
                reader.setMode(GrammarReader.Mode.IMPLICIT_ASTS);
                break;
            case "ASTS_WITH_SYMBOLS":
                reader.setMode(GrammarReader.Mode.ASTS_WITH_SYMBOLS);
                break;
            default:
                // Ignore other comments
                return;
        }
    }

}
