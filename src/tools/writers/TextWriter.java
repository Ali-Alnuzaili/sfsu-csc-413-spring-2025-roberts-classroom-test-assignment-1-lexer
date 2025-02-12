package tools.writers;

import java.util.List;

import tools.grammar.Grammar;

public class TextWriter extends GrammarWriter {

    public TextWriter(Grammar grammar) {
        super(grammar);
    }

    @Override
    public void write() throws Exception {
        System.out.println("=== Grammar (may include terminals not used this semester) ===");
        System.out.printf("%d non terminals, %d AST nodes, %d terminals, %d productions%n",
                grammar.getNonTerminals().size(), grammar.getAstCount(),
                grammar.getTerminals().size(), grammar.getProductions().size());

        System.out.println("\n=== Productions ===");
        System.out.println(String.join(System.lineSeparator(), grammar.getProductions()));

        System.out.println(String.format("\n=== Keywords (%d)===", grammar.getKeywords().size()));
        this.outputSymbols(grammar.getKeywords());

        System.out.println(String.format("=== Operators (%d) ===", grammar.getOperators().size()));
        this.outputSymbols(grammar.getOperators());

        System.out.println(String.format("=== Separators (%d) ===", grammar.getSeparators().size()));
        this.outputSymbols(grammar.getSeparators());

        System.out.println("=== Non Terminals ===");
        System.out.println(String.join(System.lineSeparator(), grammar.getNonTerminals()));

        System.out.println("\n=== AST Nodes (the (s) notation means the node will have a symbol) ===");
        System.out.println(String.join(", ", grammar.getAstNodes()));
        System.out.println();
    }

    private void outputSymbols(List<String> symbols) {
        List<String> displaySet = symbols.stream().map(
                symbol -> String.format("%-10s %s", symbol,
                        this.grammar.getSymbolicConstant(symbol).getConstantName()))
                .toList();

        System.out.println(String.join(System.lineSeparator(), displaySet));
        System.out.println();
    }
}
