package tools.grammar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Grammar {
    private Map<String, SymbolicConstant> symbolicConstants;
    private Map<String, List<String>> productions;

    private Map<String, Node> nodes;
    private Set<String> implicitAstNodes;
    private Set<String> astsWithSymbols;

    private List<String> terminals;

    public Grammar() {
        this.symbolicConstants = new HashMap<>();
        this.productions = new HashMap<>();

        this.nodes = new HashMap<>();
        this.implicitAstNodes = new HashSet<>();
        this.astsWithSymbols = new HashSet<>();

        this.terminals = new ArrayList<>();
    }

    public List<String> getTerminals() {
        return this.terminals;
    }

    public List<String> getNonTerminals() {
        return this.productions.keySet().stream().toList();
    }

    public List<String> getAstNodes() {
        return this.nodes.entrySet().stream()
                .filter(entry -> entry.getValue() instanceof NonTerminal
                        && !this.implicitAstNodes.contains(entry.getKey()))
                .map(entry -> {
                    if (this.astsWithSymbols.contains(entry.getKey())) {
                        return String.format("%s (s)", entry.getKey());
                    } else {
                        return entry.getKey();
                    }
                }).sorted().toList();
    }

    public int getAstCount() {
        return this.getAstNodes().size();
    }

    public List<String> getProductions() {
        return this.productions.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(production -> String.format("%-15s -> %s", entry.getKey(), production)))
                .toList();
    }

    public List<String> getKeywords() {
        return this.symbolicConstants.entrySet().stream()
                .filter(entry -> entry.getValue().getType().equals(SymbolicConstant.Type.KEYWORD))
                .map(entry -> entry.getValue().getLexeme()).sorted().toList();
    }

    public List<String> getOperators() {
        return this.symbolicConstants.entrySet().stream()
                .filter(entry -> entry.getValue().getType().equals(SymbolicConstant.Type.OPERATOR))
                .map(entry -> entry.getValue().getLexeme()).sorted().toList();
    }

    public List<String> getSeparators() {
        return this.symbolicConstants.entrySet().stream()
                .filter(entry -> entry.getValue().getType().equals(SymbolicConstant.Type.SEPARATOR))
                .map(entry -> entry.getValue().getLexeme()).sorted().toList();
    }

    public SymbolicConstant getSymbolicConstant(String lexeme) {
        return this.symbolicConstants.get(lexeme);
    }

    private void addSymbolicConstant(String lexeme, SymbolicConstant terminal) {
        this.symbolicConstants.put(lexeme, terminal);
        this.terminals.add(lexeme);
    }

    public void addOperator(String lexeme, String constantName) {
        this.addSymbolicConstant(lexeme, new SymbolicConstant(lexeme, SymbolicConstant.Type.OPERATOR, constantName));
    }

    public void addSeparator(String lexeme, String constantName) {
        this.addSymbolicConstant(lexeme, new SymbolicConstant(lexeme, SymbolicConstant.Type.SEPARATOR, constantName));
    }

    public void addKeyword(String lexeme) {
        this.addSymbolicConstant(lexeme, new SymbolicConstant(lexeme, SymbolicConstant.Type.KEYWORD,
                String.format("%s%s", lexeme.toUpperCase().charAt(0), lexeme.substring(1))));
    }

    public void addImplicitAst(String astName) {
        this.implicitAstNodes.add(astName);
    }

    public void addAstWithSymbol(String astName) {
        this.astsWithSymbols.add(astName);
    }

    public void addType(String lexeme) {
        String capitalizedType = String.format("%s%s", lexeme.toUpperCase().charAt(0), lexeme.substring(1));

        // This is the _type_ keyword
        this.addSymbolicConstant(lexeme, new SymbolicConstant(
                lexeme, SymbolicConstant.Type.KEYWORD, String.format("%sType", capitalizedType)));

        // This is for literal values of the type
        String literalLexeme = String.format("<%s>", lexeme);
        this.addSymbolicConstant(literalLexeme, new SymbolicConstant(
                lexeme, SymbolicConstant.Type.LITERAL, String.format("%sLit", capitalizedType)));

        // Types have nodes used in declarations and to represent literals
        this.nodes.put(String.format("%s_TYPE", lexeme.toUpperCase()), new NonTerminal(String.format("%s_TYPE",
                lexeme.toUpperCase())));
        this.nodes.put(String.format("%s_LIT", lexeme.toUpperCase()), new NonTerminal(String.format("%s_LIT",
                lexeme.toUpperCase())));

        // The literal requires a symbol
        this.addAstWithSymbol(String.format("%s_LIT", lexeme.toUpperCase()));
    }

    public void addProduction(String productionNonTerminal, String production) {
        if (!this.productions.containsKey(productionNonTerminal)) {
            this.productions.put(productionNonTerminal, new ArrayList<>());
        }

        this.productions.get(productionNonTerminal).add(production);

        // Now process the production into terminals and non terminals
        List<String> productionParts = Arrays.asList(production.split("\\s+")).stream().map(item -> item.trim())
                .toList();
        NonTerminal parent = this.getNonTerminal(productionNonTerminal);

        for (String part : productionParts) {
            if (part.startsWith("'") && productionNonTerminal.equals("TYPE")) {
                this.addType(part.replace("'", ""));
            } else if (part.startsWith("'")) {
                String lexeme = part.replace("'", "");

                // If this terminal is an operator or separator, it will already have been added
                // to the keywords
                if (this.getSymbolicConstant(lexeme) == null) {
                    this.addKeyword(lexeme);
                }

                parent.addChild(new Terminal(lexeme, this.getSymbolicConstant(lexeme)));
            } else {
                parent.addChild(new NonTerminal(part));
            }
        }
    }

    public NonTerminal getNonTerminal(String nonTerminal) {
        if (!this.nodes.containsKey(nonTerminal)) {
            this.nodes.put(nonTerminal, new NonTerminal(nonTerminal));
        }

        return (NonTerminal) this.nodes.get(nonTerminal);
    }
}
