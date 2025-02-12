package tools.writers;

import tools.grammar.Grammar;

public class SummaryWriter extends GrammarWriter {

    public SummaryWriter(Grammar grammar) {
        super(grammar);
    }

    @Override
    public void write() throws Exception {
        System.out.println("=== Grammar (may include terminals not used this semester) ===");
        System.out.printf("%d non terminals, %d AST nodes, %d terminals, %d productions%n",
                grammar.getNonTerminals().size(), grammar.getAstCount(),
                grammar.getTerminals().size(), grammar.getProductions().size());

        System.out.println(String.format("Keywords (%d): %s", grammar.getKeywords().size(),
                String.join(", ", grammar.getKeywords())));
        System.out.println(String.format("Operators (%d): %s", grammar.getOperators().size(),
                String.join(", ", grammar.getOperators())));
        System.out.println(String.format("Separators (%d): %s", grammar.getSeparators().size(),
                String.join(", ", grammar.getSeparators())));
        System.out.println();
    }

}
