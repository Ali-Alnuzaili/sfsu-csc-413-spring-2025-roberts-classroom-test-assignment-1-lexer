package tools.readers;

import java.util.Arrays;
import java.util.List;

import tools.exceptions.GrammarException;
import tools.grammar.Grammar;

public class ProductionReader extends LineReader {
    public ProductionReader(Grammar grammar) {
        super(grammar);
    }

    @Override
    public void readLine(GrammarReader reader) throws GrammarException {
        if (!reader.hasNext()) {
            throw new GrammarException("Unexpected end of file while reading productions.");
        }

        String nextLine = reader.next();

        if (reader.isDebugging()) {
            System.out.printf("Processing production definition: %s%n", nextLine);
        }

        List<String> initialProduction = Arrays.asList(nextLine.split("::=")).stream().map(item -> item.trim())
                .toList();

        String nonTerminal = initialProduction.get(0);
        // Note that the production separator requires a space before and after to
        // differentiate it from the or operator
        List<String> productions = Arrays.asList(initialProduction.get(1).split(" \\| ")).stream()
                .map(item -> item.trim()).map(item -> {
                    // Remove any grouping symbols from the production
                    if (item.startsWith("(")) {
                        return item.substring(1, item.length() - 1);
                    } else if (item.endsWith(")*")) {
                        // This is a little hacky; if I ever need to support different
                        // repetition patterns, this will need to be updated
                        return item.substring(0, item.length() - 2);
                    } else {
                        return item;
                    }
                }).toList();

        if (reader.isDebugging()) {
            System.out.printf("Non terminal %s has %d productions%n", nonTerminal, productions.size());
        }

        for (String production : productions) {
            this.grammar.addProduction(nonTerminal, production);
        }
    }
}
