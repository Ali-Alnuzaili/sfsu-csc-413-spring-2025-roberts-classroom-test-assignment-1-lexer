package tools.readers;

import java.util.Arrays;
import java.util.List;

import tools.exceptions.GrammarException;
import tools.grammar.Grammar;

public class OperatorReader extends LineReader {
    public OperatorReader(Grammar grammar) {
        super(grammar);
    }

    @Override
    public void readLine(GrammarReader reader) throws GrammarException {
        if (!reader.hasNext()) {
            throw new GrammarException("Unexpected end of file while reading operators.");
        }

        String nextLine = reader.next();

        if (reader.isDebugging()) {
            System.out.printf("Processing operator definition: %s%n", nextLine);
        }

        List<String> nextOperatorDefinition = Arrays.asList(nextLine.split("\\s+")).stream()
                .map(item -> item.trim()).toList();

        try {
            this.grammar.addOperator(nextOperatorDefinition.get(0), nextOperatorDefinition.get(1));
        } catch (IndexOutOfBoundsException e) {
            throw new GrammarException(String.format("Invalid operator definition: \"%s\"", nextLine));
        }
    }

}
