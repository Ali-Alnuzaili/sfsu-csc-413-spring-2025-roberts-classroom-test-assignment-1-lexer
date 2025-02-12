package tools;

import java.nio.file.Path;
import java.util.Arrays;

import tools.grammar.Grammar;
import tools.readers.GrammarReader;
import tools.writers.SummaryWriter;
import tools.writers.SymbolTableWriter;
import tools.writers.TextWriter;
import tools.writers.TokenKindWriter;

public class CompilerTools {
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || Arrays.asList(args).contains("-help") || Arrays.asList(args).contains("-h")) {
            System.out.println("Usage: java CompilerTools <grammar-definition-filepath>");
            System.out.println("Options:");
            System.out.printf("   %-15s %s%n", "-d --debug",
                    "Debug mode to output debugging information while reading the grammar");
            System.out.printf("   %-15s %s%n", "-h --help", "Show this help message");
            System.out.printf("   %-15s %s%n", "-v --verbose", "Output result of reading the grammar file");
            System.out.printf("   %-15s %s%n", "-s --summary", "Output concise result of reading the grammar file");
            System.out.printf("   %-15s %s%n", "-1 --lexer",
                    "(Re)Generate the files for the lexer (token kind enumeration and symbol table)");
            System.out.printf("   %-15s %s%n", "-2 --parser",
                    "(Re)Generate the files for the lexer (ASTs) and basic visitors");
            System.exit(1);
        }

        boolean debug = hasCommandLineArgument(args, "--debug", "-d");
        boolean verbose = hasCommandLineArgument(args, "--verbose", "-v");
        boolean summary = hasCommandLineArgument(args, "--summary", "-s");

        boolean parser = hasCommandLineArgument(args, "--parser", "-2");
        boolean lexer = hasCommandLineArgument(args, "--lexer", "-1") || parser;

        Grammar grammar = new GrammarReader(Path.of(args[0]), debug).read();

        if (summary) {
            new SummaryWriter(grammar).write();
        }

        if (verbose) {
            new TextWriter(grammar).write();
        }

        if (parser) {
            System.out.println("If we're on assignment 2 and you see this, I messed up");
        }

        if (lexer) {
            new TokenKindWriter(grammar).write();
            new SymbolTableWriter(grammar).write();
        }
    }

    private static boolean hasCommandLineArgument(String[] args, String... options) {
        return args.length > 1 && Arrays.asList(args).stream().anyMatch(arg -> Arrays.asList(options).contains(arg));
    }
}
