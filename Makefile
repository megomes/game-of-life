# Game of Life - Makefile
# Provides easy compilation and execution commands for both 2D and 3D versions

# Java compiler and runtime
JAVAC = javac
JAVA = java

# Source and binary directories
SRC_2D = GameOfLife/src
BIN_2D = GameOfLife/bin
SRC_3D = GameOfLife3D/src
BIN_3D = GameOfLife3D/bin

# Main class paths
MAIN_2D = br.unb.cic.lp.gol.Main
MAIN_3D = br.unb.cic.lp.gol.Main

# Package paths for compilation
PACKAGES_2D = $(SRC_2D)/br/unb/cic/lp/gol/*.java $(SRC_2D)/br/unb/cic/lp/rules/*.java $(SRC_2D)/br/unb/cic/lp/states/*.java $(SRC_2D)/br/unb/cic/lp/savedStates/*.java
PACKAGES_3D = $(SRC_3D)/br/unb/cic/lp/gol/*.java $(SRC_3D)/br/unb/cic/lp/rules/*.java $(SRC_3D)/br/unb/cic/lp/states/*.java $(SRC_3D)/br/unb/cic/lp/savedStates/*.java

# Default target
.PHONY: all clean help run-2d run-3d compile-2d compile-3d

all: compile-2d compile-3d

# Help target
help:
	@echo "Game of Life - Available commands:"
	@echo "  make all       - Compile both 2D and 3D versions"
	@echo "  make compile-2d - Compile 2D version only"
	@echo "  make compile-3d - Compile 3D version only"
	@echo "  make run-2d    - Compile and run 2D version"
	@echo "  make run-3d    - Compile and run 3D version"
	@echo "  make clean     - Remove all compiled files"
	@echo "  make help      - Show this help message"

# Create bin directories if they don't exist
$(BIN_2D):
	mkdir -p $(BIN_2D)

$(BIN_3D):
	mkdir -p $(BIN_3D)

# Compile 2D version
compile-2d: $(BIN_2D)
	@echo "Compiling 2D Game of Life..."
	$(JAVAC) -encoding ISO-8859-1 -d $(BIN_2D) -cp $(SRC_2D) $(PACKAGES_2D)
	@echo "Copying image resources..."
	cp $(SRC_2D)/br/unb/cic/lp/gol/*.png $(BIN_2D)/br/unb/cic/lp/gol/ 2>/dev/null || true
	@echo "2D version compiled successfully!"

# Compile 3D version
compile-3d: $(BIN_3D)
	@echo "Compiling 3D Game of Life..."
	$(JAVAC) -encoding ISO-8859-1 -d $(BIN_3D) -cp $(SRC_3D) $(PACKAGES_3D)
	@echo "Copying image resources..."
	cp $(SRC_3D)/br/unb/cic/lp/gol/*.png $(BIN_3D)/br/unb/cic/lp/gol/ 2>/dev/null || true
	@echo "3D version compiled successfully!"

# Run 2D version
run-2d: compile-2d
	@echo "Running 2D Game of Life..."
	cd GameOfLife && $(JAVA) -cp bin $(MAIN_2D)

# Run 3D version
run-3d: compile-3d
	@echo "Running 3D Game of Life..."
	cd GameOfLife3D && $(JAVA) -cp bin $(MAIN_3D)

# Clean compiled files
clean:
	@echo "Cleaning compiled files..."
	rm -rf $(BIN_2D)/*
	rm -rf $(BIN_3D)/*
	@echo "Clean completed!"

# Quick test compilation (no execution)
test-compile: compile-2d compile-3d
	@echo "Both versions compiled successfully!"

# Development targets
dev-2d: compile-2d
	@echo "Development mode - 2D version ready"

dev-3d: compile-3d
	@echo "Development mode - 3D version ready"

# Install dependencies (Java check)
check-java:
	@echo "Checking Java installation..."
	@$(JAVA) -version
	@$(JAVAC) -version
	@echo "Java environment is ready!"

# Build info
info:
	@echo "Game of Life - Build Information"
	@echo "================================"
	@echo "2D Source: $(SRC_2D)"
	@echo "2D Binary: $(BIN_2D)"
	@echo "3D Source: $(SRC_3D)"
	@echo "3D Binary: $(BIN_3D)"
	@echo "Main Class 2D: $(MAIN_2D)"
	@echo "Main Class 3D: $(MAIN_3D)"
	@echo "Java Compiler: $(JAVAC)"
	@echo "Java Runtime: $(JAVA)"