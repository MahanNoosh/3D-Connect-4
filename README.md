# 🧠 3D Tic-Tac-Toe (Java Swing)

This is a 3D version of Tic Tac Toe made in Java using Java Swing. The board is a 4x4x4 cube, and the game allows either two players to play against each other or one player to play against a basic AI.

> 🎓 **This project was created as my Grade 12 Computer Science final project.**

---

## 📈 What I Learned

While making this project, I learned:

- How to build GUIs using Java Swing
- How to simulate 3D logic in a 2D space using isometric projection
- The structure and logic behind AI decision-making
- How to manage larger codebases with multiple files and classes
- Game development fundamentals

---

## 🎮 Features

- ✅ **Single Player Mode** – Play against a basic AI
- ✅ **Multiplayer Mode** – Play against a friend locally
- ✅ **3D Win Detection** – All 3D lines are checked for winning conditions
- ✅ **Isometric Projection** – The board is drawn using a simple isometric rendering
- ✅ **Mouse-Based Input** – Click on cubes to make moves
- ✅ **Auto-Restart** – Click after a game ends to start a new one

---

## 🧊 Gameplay Overview

The game is played on a 4x4x4 cube, totaling 64 cells. The first player to align 4 of their marks (Red or Blue) in a straight line (in any direction: vertical, horizontal, depth, or diagonal across layers) wins.

---

## 🌐 Technologies Used

- Java 8+
- Java Swing (for GUI)
- Object-Oriented Programming (OOP)
- Basic and effective AI Logic
- Isometric 2D rendering for a 3D effect

---

## 🧠 AI Behavior

The AI is relatively basic and works by prioritizing:
1. Heuristic evaluation to weigh board states
2. Immediate winning moves
3. Blocking the player's winning moves
4. Pick a move based on weights

The AI logic is located in the `ThreeDAI.java` file and can be improved further using techniques like Minimax or Alpha-Beta pruning.

---

## 🧩 File Descriptions

- `ThreeDTicTacToe.java` – Main game loop and rendering logic
- `Cube.java` – Represents individual cubes in the grid
- `ThreeDAI.java` – Contains the AI logic
- `WinChecker.java` – Handles win/tie detection logic
- `MyFrame.java` – Main GUI frame and application entry point

---

## 🚀 How to Run

### Requirements
- Java 8 or higher
- A Java IDE (e.g., IntelliJ, Eclipse) or terminal

### Steps

```bash
# Clone the repository
git clone https://github.com/your-username/3d-tic-tac-toe.git
cd 3d-tic-tac-toe

# Compile the Java files
javac *.java

# Run the program
java MyFrame
```
---

## 📊 Win Detection Logic

The win detection works by checking all possible 76 winning lines in a 4x4x4 grid. These include:

- 16 rows (4 per layer × 4 layers)
- 16 columns (4 per layer × 4 layers)
- 16 verticals (same position across 4 layers)
- 4 long diagonals per layer × 4 layers = 16
- 4 vertical diagonals in each column and row
- 4 space diagonals (corner to corner in 3D)

This was implemented using 3D vector math and direction-based line checking.

---

## 🧠 How AI Could Be Improved

Right now the AI is rule-based. Here are ideas for making it more intelligent:

- Implement Minimax algorithm with alpha-beta pruning
- Add difficulty levels (Easy, Medium, Hard)
- Simulate future moves using recursion

Contributions for AI improvement are welcome!

---

## 🛠️ How to Contribute

If you'd like to improve the project:

1. Fork the repository
2. Create a new branch
3. Make your changes
4. Submit a pull request

Suggestions and bug reports are welcome via [Issues](https://github.com/your-username/3d-tic-tac-toe/issues)!
