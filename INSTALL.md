# Game of Life - Installation Guide

This guide provides detailed instructions for setting up and running the Game of Life project on different operating systems.

## 📋 Prerequisites

### Java Development Kit (JDK)
- **Version**: JDK 8 or higher
- **Recommended**: JDK 11 or JDK 17 (LTS versions)
- **Required Components**: 
  - Java Runtime Environment (JRE)
  - Java Compiler (`javac`)

### System Requirements
- **Operating System**: Windows, macOS, or Linux
- **Memory**: Minimum 512MB RAM (1GB+ recommended)
- **Storage**: 50MB free space
- **Display**: Graphics support for GUI version

## 🚀 Installation Steps

### Step 1: Verify Java Installation

First, check if Java is already installed on your system:

```bash
# Check Java runtime version
java -version

# Check Java compiler version
javac -version
```

If Java is not installed or you have an older version, proceed to Step 2.

### Step 2: Install Java JDK

#### Windows
1. **Download JDK**:
   - Visit [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - Download the Windows x64 installer

2. **Install JDK**:
   - Run the downloaded installer
   - Follow the installation wizard
   - Note the installation directory (e.g., `C:\Program Files\Java\jdk-11.0.x`)

3. **Set Environment Variables**:
   - Open System Properties → Advanced → Environment Variables
   - Add `JAVA_HOME` system variable pointing to JDK installation directory
   - Add `%JAVA_HOME%\bin` to the `PATH` variable

4. **Verify Installation**:
   ```cmd
   java -version
   javac -version
   ```

#### macOS
1. **Using Homebrew** (Recommended):
   ```bash
   # Install Homebrew if not already installed
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   
   # Install OpenJDK
   brew install openjdk@11
   
   # Add to PATH
   echo 'export PATH="/opt/homebrew/opt/openjdk@11/bin:$PATH"' >> ~/.zshrc
   source ~/.zshrc
   ```

2. **Manual Installation**:
   - Download JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
   - Install the .dmg package
   - Set `JAVA_HOME` in your shell profile:
     ```bash
     echo 'export JAVA_HOME=$(/usr/libexec/java_home)' >> ~/.zshrc
     source ~/.zshrc
     ```

3. **Verify Installation**:
   ```bash
   java -version
   javac -version
   ```

#### Linux (Ubuntu/Debian)
1. **Update Package Index**:
   ```bash
   sudo apt update
   ```

2. **Install OpenJDK**:
   ```bash
   # Install JDK 11
   sudo apt install openjdk-11-jdk
   
   # Or install JDK 17
   sudo apt install openjdk-17-jdk
   ```

3. **Set JAVA_HOME** (if needed):
   ```bash
   echo 'export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64' >> ~/.bashrc
   source ~/.bashrc
   ```

4. **Verify Installation**:
   ```bash
   java -version
   javac -version
   ```

#### Linux (CentOS/RHEL/Fedora)
1. **Install OpenJDK**:
   ```bash
   # CentOS/RHEL
   sudo yum install java-11-openjdk-devel
   
   # Fedora
   sudo dnf install java-11-openjdk-devel
   ```

2. **Set JAVA_HOME**:
   ```bash
   echo 'export JAVA_HOME=/usr/lib/jvm/java-11-openjdk' >> ~/.bashrc
   source ~/.bashrc
   ```

### Step 3: Download Game of Life

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd GameofLife
   ```

2. **Or Download ZIP**:
   - Download the project as a ZIP file
   - Extract to your preferred directory
   - Navigate to the project directory

### Step 4: Build the Project

Choose one of the following methods:

#### Option A: Using Makefile (Linux/macOS)
```bash
# Compile both versions
make all

# Or compile specific version
make compile-2d
make compile-3d
```

#### Option B: Using Build Scripts
```bash
# Linux/macOS
./build.sh all

# Windows
build.bat all
```

#### Option C: Manual Compilation
```bash
# For 2D version
cd GameOfLife
javac -d bin -cp src src/br/unb/cic/lp/gol/*.java src/br/unb/cic/lp/rules/*.java src/br/unb/cic/lp/states/*.java src/br/unb/cic/lp/savedStates/*.java

# For 3D version
cd GameOfLife3D
javac -d bin -cp src src/br/unb/cic/lp/gol/*.java src/br/unb/cic/lp/rules/*.java src/br/unb/cic/lp/states/*.java src/br/unb/cic/lp/savedStates/*.java
```

## 🎮 Running the Game

### Quick Start
```bash
# Run 2D version
make run-2d
# or
./build.sh run-2d

# Run 3D version
make run-3d
# or
./build.sh run-3d
```

### Manual Execution
```bash
# 2D version
cd GameOfLife
java -cp bin br.unb.cic.lp.gol.Main

# 3D version
cd GameOfLife3D
java -cp bin br.unb.cic.lp.gol.Main
```

## 🛠️ Development Setup

### IDE Configuration

#### IntelliJ IDEA
1. Open IntelliJ IDEA
2. Choose "Open or Import"
3. Select the GameofLife directory
4. Configure Project SDK to use your installed JDK
5. Mark `src` directories as Source Roots

#### Eclipse
1. Open Eclipse
2. File → Import → Existing Projects into Workspace
3. Select the GameofLife directory
4. Import both GameOfLife and GameOfLife3D projects
5. Configure Build Path to use correct JDK

#### VS Code
1. Install Java Extension Pack
2. Open the GameofLife directory
3. VS Code will automatically detect the Java projects
4. Configure java.home in settings if needed

### Build Tools Integration

#### Maven (Optional)
If you want to use Maven, create a `pom.xml` in the root directory:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <groupId>br.unb.cic.lp</groupId>
    <artifactId>game-of-life</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>
    
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
</project>
```

## 🔧 Troubleshooting

### Common Issues

#### 1. "java: command not found"
- **Solution**: Java is not installed or not in PATH
- **Fix**: Install JDK and add to PATH (see Step 2)

#### 2. "javac: command not found"
- **Solution**: JDK is not installed (only JRE)
- **Fix**: Install full JDK package

#### 3. "Could not find or load main class"
- **Solution**: Classpath is incorrect
- **Fix**: Ensure you're in the correct directory and using proper classpath

#### 4. "UnsupportedClassVersionError"
- **Solution**: Java version mismatch
- **Fix**: Ensure JDK version matches compiled bytecode version

#### 5. GUI doesn't appear
- **Solution**: Display/X11 forwarding issues
- **Fix**: Ensure GUI support is available (install X11 on Linux)

### Performance Issues

#### Memory Settings
For large simulations, increase heap size:
```bash
java -Xmx1024m -cp bin br.unb.cic.lp.gol.Main
```

#### Graphics Performance
For better GUI performance on Linux:
```bash
java -Dsun.java2d.opengl=true -cp bin br.unb.cic.lp.gol.Main
```

## 📝 Testing Installation

Run the test compilation to verify everything works:
```bash
# Using Makefile
make test-compile

# Using build scripts
./build.sh all
build.bat all
```

## 🆘 Getting Help

If you encounter issues:

1. **Check Prerequisites**: Ensure Java JDK is properly installed
2. **Verify Environment**: Check `JAVA_HOME` and `PATH` variables
3. **Review Logs**: Check console output for specific error messages
4. **Platform-Specific**: Some issues may be OS-specific

### Support Resources
- Java Documentation: https://docs.oracle.com/en/java/
- OpenJDK Documentation: https://openjdk.org/
- Project Repository: [Link to issues page]

## 🚀 Next Steps

After successful installation:
1. Read the [README.md](README.md) for usage instructions
2. Check [GAME_RULES.md](GAME_RULES.md) for game rule explanations
3. Explore the source code to understand the implementation
4. Try modifying game rules or adding new features

---

*Happy coding! 🎮*