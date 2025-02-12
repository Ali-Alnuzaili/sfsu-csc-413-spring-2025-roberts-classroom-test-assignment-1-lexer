# Note: Anywhere you see @, that means I want to prevent that
# command from printing (and only want to see the result of
#  executing that command)

# Defining some variables to prevent typos
COMPILE_DIR         = out
SRC_DIR             = src
SOURCE_FILE         = sources
LEXER_DIRECTORY     = STUDENT TO REPLACE WITH ABSOLUTE PATH TO LEXER PROJECT

# clean should be used to remove all compiled (.class) files
# 
# the find command on *nix finds all files (-type f) starting from the
# current directory (.), where the filename ends in ".class", and
# deletes (-delete) them
clean:
	@echo "Deleting all class files..."
	@find . -name "*.class" -type f -delete
	@echo "Deleting compiled files..."
	@rm -rf $(COMPILE_DIR)
	@rm -f $(SOURCE_FILE)

init-tools: clean
	@echo "Ensuring project structure..."
	@mkdir -p $(SRC_DIR)
	@mkdir -p tests
	@echo "Replacing existing tools implementation..."
	@rm -rf src/tools
	@cp -r ../0-tools/src/tools src/tools
	@echo "Compiling tools..."
	@javac -d $(COMPILE_DIR) -cp $(SRC_DIR):. src/tools/CompilerTools.java

init-lexer: init-tools
	@echo "Initializing lexer project..."
	@mkdir -p src/lexer
	@mkdir -p src/compiler
	@java -cp $(COMPILE_DIR) tools.CompilerTools src/tools/definition/grammar.txt --summary --lexer

tools:
	@echo "Compiling tools..."
	@java -cp $(COMPILE_DIR):. tools.CompilerTools src/tools/definition/grammar.txt --summary --lexer