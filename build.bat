@echo off
REM Game of Life - Build Script for Windows
REM This script compiles and optionally runs the Game of Life projects

setlocal enabledelayedexpansion

REM Configuration
set JAVA_CMD=java
set JAVAC_CMD=javac
set MAIN_CLASS_2D=br.unb.cic.lp.gol.Main
set MAIN_CLASS_3D=br.unb.cic.lp.gol.Main

REM Colors for output (if supported)
set RED=[31m
set GREEN=[32m
set YELLOW=[33m
set BLUE=[34m
set NC=[0m

goto :main

REM Function to print status messages
:print_status
echo [INFO] %~1
goto :eof

:print_success
echo [SUCCESS] %~1
goto :eof

:print_error
echo [ERROR] %~1
goto :eof

:print_warning
echo [WARNING] %~1
goto :eof

REM Function to check Java installation
:check_java
call :print_status "Checking Java installation..."

where %JAVA_CMD% >nul 2>&1
if %errorlevel% neq 0 (
    call :print_error "Java runtime not found. Please install Java JDK 8 or higher."
    exit /b 1
)

where %JAVAC_CMD% >nul 2>&1
if %errorlevel% neq 0 (
    call :print_error "Java compiler not found. Please install Java JDK 8 or higher."
    exit /b 1
)

for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
    set JAVA_VERSION=!JAVA_VERSION:"=!
)

call :print_success "Java found: !JAVA_VERSION!"
goto :eof

REM Function to compile 2D version
:compile_2d
call :print_status "Compiling 2D Game of Life..."

REM Create bin directory if it doesn't exist
if not exist "GameOfLife\bin" mkdir "GameOfLife\bin"

REM Compile all Java files
%JAVAC_CMD% -d GameOfLife\bin -cp GameOfLife\src ^
    GameOfLife\src\br\unb\cic\lp\gol\*.java ^
    GameOfLife\src\br\unb\cic\lp\rules\*.java ^
    GameOfLife\src\br\unb\cic\lp\states\*.java ^
    GameOfLife\src\br\unb\cic\lp\savedStates\*.java

if %errorlevel% equ 0 (
    call :print_success "2D version compiled successfully!"
) else (
    call :print_error "Failed to compile 2D version"
    exit /b 1
)
goto :eof

REM Function to compile 3D version
:compile_3d
call :print_status "Compiling 3D Game of Life..."

REM Create bin directory if it doesn't exist
if not exist "GameOfLife3D\bin" mkdir "GameOfLife3D\bin"

REM Compile all Java files
%JAVAC_CMD% -d GameOfLife3D\bin -cp GameOfLife3D\src ^
    GameOfLife3D\src\br\unb\cic\lp\gol\*.java ^
    GameOfLife3D\src\br\unb\cic\lp\rules\*.java ^
    GameOfLife3D\src\br\unb\cic\lp\states\*.java ^
    GameOfLife3D\src\br\unb\cic\lp\savedStates\*.java

if %errorlevel% equ 0 (
    call :print_success "3D version compiled successfully!"
) else (
    call :print_error "Failed to compile 3D version"
    exit /b 1
)
goto :eof

REM Function to run 2D version
:run_2d
call :print_status "Running 2D Game of Life..."
cd GameOfLife
%JAVA_CMD% -cp bin %MAIN_CLASS_2D%
cd ..
goto :eof

REM Function to run 3D version
:run_3d
call :print_status "Running 3D Game of Life..."
cd GameOfLife3D
%JAVA_CMD% -cp bin %MAIN_CLASS_3D%
cd ..
goto :eof

REM Function to clean compiled files
:clean
call :print_status "Cleaning compiled files..."

if exist "GameOfLife\bin" (
    del /q "GameOfLife\bin\*" 2>nul
    for /d %%d in ("GameOfLife\bin\*") do rmdir /s /q "%%d" 2>nul
    call :print_success "Cleaned 2D bin directory"
)

if exist "GameOfLife3D\bin" (
    del /q "GameOfLife3D\bin\*" 2>nul
    for /d %%d in ("GameOfLife3D\bin\*") do rmdir /s /q "%%d" 2>nul
    call :print_success "Cleaned 3D bin directory"
)

call :print_success "Clean completed!"
goto :eof

REM Function to display help
:show_help
echo Game of Life - Build Script
echo Usage: %~nx0 [OPTION]
echo.
echo Options:
echo   all        Compile both 2D and 3D versions
echo   2d         Compile 2D version only
echo   3d         Compile 3D version only
echo   run-2d     Compile and run 2D version
echo   run-3d     Compile and run 3D version
echo   clean      Remove all compiled files
echo   help       Show this help message
echo.
echo Examples:
echo   %~nx0 all         # Compile both versions
echo   %~nx0 run-2d      # Compile and run 2D version
echo   %~nx0 clean       # Clean all compiled files
goto :eof

REM Main script logic
:main
call :print_status "Game of Life Build Script"
call :print_status "=========================="

REM Check if script is run from correct directory
if not exist "GameOfLife" (
    call :print_error "GameOfLife directory not found. Please run this script from the root GameofLife directory"
    exit /b 1
)

if not exist "GameOfLife3D" (
    call :print_error "GameOfLife3D directory not found. Please run this script from the root GameofLife directory"
    exit /b 1
)

REM Check Java installation
call :check_java
if %errorlevel% neq 0 exit /b 1

REM Parse command line arguments
set "OPTION=%~1"
if "%OPTION%"=="" set "OPTION=all"

if "%OPTION%"=="all" (
    call :compile_2d
    if %errorlevel% neq 0 exit /b 1
    call :compile_3d
    if %errorlevel% neq 0 exit /b 1
    call :print_success "All versions compiled successfully!"
) else if "%OPTION%"=="2d" (
    call :compile_2d
    if %errorlevel% neq 0 exit /b 1
) else if "%OPTION%"=="3d" (
    call :compile_3d
    if %errorlevel% neq 0 exit /b 1
) else if "%OPTION%"=="run-2d" (
    call :compile_2d
    if %errorlevel% neq 0 exit /b 1
    call :run_2d
) else if "%OPTION%"=="run-3d" (
    call :compile_3d
    if %errorlevel% neq 0 exit /b 1
    call :run_3d
) else if "%OPTION%"=="clean" (
    call :clean
) else if "%OPTION%"=="help" (
    call :show_help
) else if "%OPTION%"=="-h" (
    call :show_help
) else if "%OPTION%"=="--help" (
    call :show_help
) else (
    call :print_error "Unknown option: %OPTION%"
    call :show_help
    exit /b 1
)

exit /b 0