# Game of Life - Game Rules Documentation

This document explains all the game rules implemented in the Game of Life project, including traditional and custom variations.

## 🎮 Overview

Conway's Game of Life is a cellular automaton where cells evolve based on simple rules that determine whether they live, die, or reproduce. This implementation includes several rule variations that create different behaviors and patterns.

## 🔄 Core Concepts

### Cell States
- **Dead**: Empty cell (represented as `dead`)
- **Alive**: Living cell (various types: `alive_a`, `alive_b`, `alive_c`)

### Generation Rules
Each generation follows two main phases:
1. **Survival Check**: Determines if living cells remain alive
2. **Birth Check**: Determines if dead cells become alive

### Neighbor Counting
- **2D Version**: Each cell has up to 8 neighbors (Moore neighborhood)
- **3D Version**: Extended neighborhood with depth consideration

## 📜 Implemented Rule Sets

### 1. Standard Conway's Rules (`GameRule_Standard`)

The classic Game of Life rules as defined by John Conway.

#### Survival Rules
- A living cell with **2 or 3** living neighbors survives
- All other living cells die (underpopulation or overpopulation)

#### Birth Rules
- A dead cell with exactly **3** living neighbors becomes alive
- All other dead cells remain dead

#### Implementation Details
```java
// Survival: 2-3 neighbors keep cell alive
if (dictState.get(cell) == 2 || dictState.get(cell) == 3) {
    return new CellState_Alive();
}

// Birth: exactly 3 neighbors revive dead cell
if (dictState.get(cell) == 3) {
    return new CellState_Alive();
}
```

#### Characteristics
- **Cell Types**: Single alive state
- **Behavior**: Classic patterns (gliders, oscillators, still lifes)
- **Stability**: Well-studied, predictable patterns

---

### 2. HighLife Rules (`GameRule_HighLife`)

An extension of Conway's rules that adds more birth conditions.

#### Survival Rules
- A living cell with **2 or 3** living neighbors survives
- Same as standard rules

#### Birth Rules
- A dead cell with **3 or 6** living neighbors becomes alive
- Additional birth condition creates more complex patterns

#### Implementation Details
```java
// Survival: same as standard (2-3 neighbors)
if (dictState.get(cell) == 2 || dictState.get(cell) == 3) {
    return new CellState_Alive();
}

// Birth: 3 or 6 neighbors revive dead cell
if (dictState.get(cell) == 3 || dictState.get(cell) == 6) {
    return new CellState_Alive();
}
```

#### Characteristics
- **Cell Types**: Single alive state
- **Behavior**: More replicators and complex patterns
- **Notable Patterns**: Self-replicating patterns emerge more frequently

---

### 3. Immigration Game Rules (`GameRule_ImigrationGame`)

A multi-state cellular automaton with two types of living cells.

#### Cell Types
- **Type A** (`alive_a`): Primary living state
- **Type B** (`alive_b`): Secondary living state
- **Dead** (`dead`): Empty state

#### Survival Rules
- A living cell survives if it has **2 or 3** total living neighbors
- Cell adopts the majority type among neighbors
- Type A wins in case of ties

#### Birth Rules
- A dead cell becomes alive if it has exactly **3** living neighbors
- New cell adopts the majority neighbor type
- Type A wins in case of ties

#### Implementation Details
```java
// Count both types of neighbors
int Alive_A_count = 0;
int Alive_B_count = 0;
int total = Alive_A_count + Alive_B_count;

// Survival: 2-3 total neighbors
if (total == 2 || total == 3) {
    return (Alive_A_count >= Alive_B_count) ? 
           new CellState_Alive() : new CellState_Alive_B();
}

// Birth: exactly 3 neighbors
if (total == 3) {
    return (Alive_A_count >= Alive_B_count) ? 
           new CellState_Alive() : new CellState_Alive_B();
}
```

#### Characteristics
- **Cell Types**: Two alive states plus dead
- **Behavior**: Immigration patterns, territorial competition
- **Dynamics**: Populations compete for space and influence

---

### 4. Generic Rules (`GameRule_Generic`) - 3D Version Only

A fully customizable rule system allowing users to define their own Game of Life variants.

#### Configurable Parameters
- **Custom Cell Types**: Add multiple alive cell states
- **Survival Conditions**: Define which neighbor counts keep cells alive
- **Birth Conditions**: Define which neighbor counts revive dead cells
- **Majority Logic**: New cells adopt the most common neighbor type

#### Configuration Methods
```java
GameRule_Generic gameRule = new GameRule_Generic();

// Add custom cell types
gameRule.addCellState_Alive(new CellState_Alive_A());
gameRule.addCellState_Alive(new CellState_Alive_B());
gameRule.addCellState_Alive(customCellState);

// Define survival conditions
gameRule.addKeepAliveCase(1);  // Survive with 1 neighbor
gameRule.addKeepAliveCase(3);  // Survive with 3 neighbors
gameRule.addKeepAliveCase(5);  // Survive with 5 neighbors

// Define birth conditions
gameRule.addReviveCase(3);     // Born with 3 neighbors
gameRule.addReviveCase(5);     // Born with 5 neighbors
gameRule.addReviveCase(7);     // Born with 7 neighbors
```

#### Example: Amoeba Pattern
The 3D version demonstrates an "Amoeba" configuration:
- **3 Cell Types**: A, B, and C
- **Survival**: 1, 3, 5, or 8 neighbors
- **Birth**: 3, 5, or 7 neighbors
- **Behavior**: Chaotic, organic-like growth patterns

#### Implementation Details
```java
// Calculate total neighbors of all alive types
int total = 0;
for (int i = 0; i < cellStates.size(); i++) {
    total += counters[i];
}

// Check survival conditions
for (int keepAliveCase : keepAliveCases) {
    if (total == keepAliveCase) {
        return getStateMajority(counters);
    }
}

// Check birth conditions
for (int reviveCase : reviveCases) {
    if (total == reviveCase) {
        return getStateMajority(counters);
    }
}
```

#### Characteristics
- **Cell Types**: Unlimited custom states
- **Behavior**: Completely customizable
- **Flexibility**: Create entirely new Game of Life variants

---

## 🎯 Rule Comparison Table

| Rule Set | Cell Types | Survival | Birth | Special Features |
|----------|------------|----------|-------|------------------|
| **Standard** | 1 | 2-3 neighbors | 3 neighbors | Classic Conway's rules |
| **HighLife** | 1 | 2-3 neighbors | 3, 6 neighbors | More replicators |
| **Immigration** | 2 | 2-3 neighbors | 3 neighbors | Competing populations |
| **Generic** | Unlimited | Configurable | Configurable | Fully customizable |

## 🔬 Pattern Analysis

### Standard Patterns
- **Still Lifes**: Block, beehive, loaf
- **Oscillators**: Blinker, toad, beacon
- **Spaceships**: Glider, lightweight spaceship

### HighLife Patterns
- **Replicators**: Self-copying patterns
- **Complex Oscillators**: Higher-period patterns
- **Unusual Spaceships**: Different velocities

### Immigration Patterns
- **Territorial Waves**: Competing expansion fronts
- **Mixed Populations**: Stable coexistence patterns
- **Immigration Streams**: Directional population flow

### Generic Patterns
- **Chaotic Growth**: Amoeba-like expansion
- **Custom Oscillators**: User-defined periods
- **Novel Behaviors**: Entirely new pattern types

## 🛠️ Technical Implementation

### Architecture
All rules inherit from the abstract `GameRule` class:

```java
public abstract class GameRule {
    protected abstract CellState shouldKeepAlive(HashMap<CellState, Integer> dictState);
    protected abstract CellState shouldRevive(HashMap<CellState, Integer> dictState);
    public abstract List<CellState> getOptions();
    public abstract HashMap<String, String> getImageOptions();
}
```

### State Management
- **HashMap Counting**: Neighbors counted by state type
- **Majority Logic**: Most common neighbor type wins
- **Tie Breaking**: Default rules for equal counts

### 3D Extensions
The 3D version includes depth parameters:
```java
protected CellState shouldKeepAlive(HashMap<CellState, Integer> dictState, int depth);
protected CellState shouldRevive(HashMap<CellState, Integer> dictState, int depth);
```

## 🎲 Creating Custom Rules

### Steps to Add New Rules
1. **Extend GameRule**: Create new class inheriting from `GameRule`
2. **Implement Methods**: Define survival and birth logic
3. **Configure States**: Specify available cell types
4. **Add Images**: Define visual representations
5. **Test Patterns**: Verify rule behavior

### Example Custom Rule
```java
public class GameRule_Custom extends GameRule {
    protected CellState shouldKeepAlive(HashMap<CellState, Integer> dictState) {
        // Custom survival logic
        int aliveNeighbors = countAliveNeighbors(dictState);
        return (aliveNeighbors == 4 || aliveNeighbors == 5) ? 
               new CellState_Alive() : new CellState_Dead();
    }
    
    protected CellState shouldRevive(HashMap<CellState, Integer> dictState) {
        // Custom birth logic
        int aliveNeighbors = countAliveNeighbors(dictState);
        return (aliveNeighbors == 2) ? 
               new CellState_Alive() : new CellState_Dead();
    }
}
```

## 📚 Further Reading

### Classic References
- Conway's original Game of Life paper
- "Winning Ways for Your Mathematical Plays" by Berlekamp, Conway, and Guy
- LifeWiki: https://www.conwaylife.com/

### Advanced Topics
- Cellular automaton theory
- Complex systems and emergence
- Computational biology applications

### Pattern Collections
- Online pattern databases
- Community-created rule sets
- Historical pattern discoveries

---

*Explore the fascinating world of cellular automata through these diverse rule implementations!* 🌱