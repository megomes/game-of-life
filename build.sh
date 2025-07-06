#!/bin/bash

# Game of Life - Build Script for Linux/macOS
# This script compiles and optionally runs the Game of Life projects

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
JAVA_CMD="java"
JAVAC_CMD="javac"
MAIN_CLASS_2D="br.unb.cic.lp.gol.Main"
MAIN_CLASS_3D="br.unb.cic.lp.gol.Main"

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

# Function to check Java installation
check_java() {
    print_status "Checking Java installation..."
    
    if ! command -v $JAVA_CMD &> /dev/null; then
        print_error "Java runtime not found. Please install Java JDK 8 or higher."
        exit 1
    fi
    
    if ! command -v $JAVAC_CMD &> /dev/null; then
        print_error "Java compiler not found. Please install Java JDK 8 or higher."
        exit 1
    fi
    
    JAVA_VERSION=$($JAVA_CMD -version 2>&1 | head -1 | cut -d'"' -f2)
    print_success "Java found: $JAVA_VERSION"
}

# Function to compile 2D version
compile_2d() {
    print_status "Compiling 2D Game of Life..."
    
    # Create bin directory if it doesn't exist
    mkdir -p GameOfLife/bin
    
    # Compile all Java files
    $JAVAC_CMD -encoding ISO-8859-1 -d GameOfLife/bin -cp GameOfLife/src \
        GameOfLife/src/br/unb/cic/lp/gol/*.java \
        GameOfLife/src/br/unb/cic/lp/rules/*.java \
        GameOfLife/src/br/unb/cic/lp/states/*.java \
        GameOfLife/src/br/unb/cic/lp/savedStates/*.java
    
    if [ $? -eq 0 ]; then
        # Copy image resources
        cp GameOfLife/src/br/unb/cic/lp/gol/*.png GameOfLife/bin/br/unb/cic/lp/gol/ 2>/dev/null || true
        print_success "2D version compiled successfully!"
    else
        print_error "Failed to compile 2D version"
        exit 1
    fi
}

# Function to compile 3D version
compile_3d() {
    print_status "Compiling 3D Game of Life..."
    
    # Create bin directory if it doesn't exist
    mkdir -p GameOfLife3D/bin
    
    # Compile all Java files
    $JAVAC_CMD -encoding ISO-8859-1 -d GameOfLife3D/bin -cp GameOfLife3D/src \
        GameOfLife3D/src/br/unb/cic/lp/gol/*.java \
        GameOfLife3D/src/br/unb/cic/lp/rules/*.java \
        GameOfLife3D/src/br/unb/cic/lp/states/*.java \
        GameOfLife3D/src/br/unb/cic/lp/savedStates/*.java
    
    if [ $? -eq 0 ]; then
        # Copy image resources
        cp GameOfLife3D/src/br/unb/cic/lp/gol/*.png GameOfLife3D/bin/br/unb/cic/lp/gol/ 2>/dev/null || true
        print_success "3D version compiled successfully!"
    else
        print_error "Failed to compile 3D version"
        exit 1
    fi
}

# Function to run 2D version
run_2d() {
    print_status "Running 2D Game of Life..."
    cd GameOfLife
    $JAVA_CMD -cp bin $MAIN_CLASS_2D
    cd ..
}

# Function to run 3D version
run_3d() {
    print_status "Running 3D Game of Life..."
    cd GameOfLife3D
    $JAVA_CMD -cp bin $MAIN_CLASS_3D
    cd ..
}

# Function to clean compiled files
clean() {
    print_status "Cleaning compiled files..."
    
    if [ -d "GameOfLife/bin" ]; then
        rm -rf GameOfLife/bin/*
        print_success "Cleaned 2D bin directory"
    fi
    
    if [ -d "GameOfLife3D/bin" ]; then
        rm -rf GameOfLife3D/bin/*
        print_success "Cleaned 3D bin directory"
    fi
    
    print_success "Clean completed!"
}

# Function to display help
show_help() {
    echo "Game of Life - Build Script"
    echo "Usage: $0 [OPTION]"
    echo ""
    echo "Options:"
    echo "  all        Compile both 2D and 3D versions"
    echo "  2d         Compile 2D version only"
    echo "  3d         Compile 3D version only"
    echo "  run-2d     Compile and run 2D version"
    echo "  run-3d     Compile and run 3D version"
    echo "  clean      Remove all compiled files"
    echo "  help       Show this help message"
    echo ""
    echo "Examples:"
    echo "  $0 all         # Compile both versions"
    echo "  $0 run-2d      # Compile and run 2D version"
    echo "  $0 clean       # Clean all compiled files"
}

# Main script logic
main() {
    print_status "Game of Life Build Script"
    print_status "=========================="
    
    # Check if script is run from correct directory
    if [ ! -d "GameOfLife" ] || [ ! -d "GameOfLife3D" ]; then
        print_error "Please run this script from the root GameofLife directory"
        exit 1
    fi
    
    # Check Java installation
    check_java
    
    # Parse command line arguments
    case "${1:-all}" in
        "all")
            compile_2d
            compile_3d
            print_success "All versions compiled successfully!"
            ;;
        "2d")
            compile_2d
            ;;
        "3d")
            compile_3d
            ;;
        "run-2d")
            compile_2d
            run_2d
            ;;
        "run-3d")
            compile_3d
            run_3d
            ;;
        "clean")
            clean
            ;;
        "help"|"-h"|"--help")
            show_help
            ;;
        *)
            print_error "Unknown option: $1"
            show_help
            exit 1
            ;;
    esac
}

# Run main function
main "$@"