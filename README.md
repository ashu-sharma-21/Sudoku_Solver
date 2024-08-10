# Sudoku_Solver
The Sudoku Solver is an Android application developed using Java and Android Studio. The app allows users to input a Sudoku puzzle, validate its correctness, and solve it if possible. The app features a user-friendly interface with a 9x9 grid for easy input of Sudoku numbers and provides immediate feedback on the validity and solvability of the puzzle.

Validation Logic-
Sudoku Solver ensures that the numbers you enter follow the basic Sudoku rules:

Rows: No number should repeat in any row.
Columns: No number should repeat in any column.
3x3 Boxes: Each of the nine 3x3 boxes should contain the numbers 1-9 with no repetitions.
The app checks these rules every time you enter a number. If everything is correct, the puzzle is valid; if not, it’s marked as invalid.

Solving Algorithm-
To solve the puzzle, the app uses a method called backtracking:

Find an Empty Cell: The app first looks for any empty cells in the grid.
Try Numbers: For each empty cell, it places numbers 1 to 9.
Check Validity: After placing a number, it verifies if it fits the Sudoku rules.
Continue or Backtrack: If the number fits, it moves to the next empty cell. If not, it removes the number and tries a different one. This process continues until the puzzle is solved.
