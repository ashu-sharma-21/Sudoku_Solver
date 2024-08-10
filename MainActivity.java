package com.example.sudoku_solver;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText[][] cells = new EditText[9][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the grid cells programmatically
        GridLayout gridLayout = findViewById(R.id.gridLayout);
        initializeGrid(gridLayout);

        // Validate Button Logic
        Button btnValidate = findViewById(R.id.btnValidate);
        btnValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (validateSudoku()) {
                        Toast.makeText(MainActivity.this, "Valid Sudoku!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Sudoku!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Solve Button Logic
        Button btnSolve = findViewById(R.id.btnSolve);
        btnSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (validateSudoku()) {
                        if (solveSudoku()) {
                            displaySolution();
                        } else {
                            Toast.makeText(MainActivity.this, "Sudoku cannot be solved!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid Sudoku!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Initialize the grid programmatically
    private void initializeGrid(GridLayout gridLayout) {
        gridLayout.setRowCount(9);
        gridLayout.setColumnCount(9);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                EditText editText = new EditText(this);
                editText.setLayoutParams(new GridLayout.LayoutParams());
                editText.setWidth(100);
                editText.setHeight(100);
                editText.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
                editText.setGravity(Gravity.CENTER);
                editText.setTextSize(18);
                editText.setBackgroundResource(android.R.color.white);
                editText.setMaxLines(1);
                gridLayout.addView(editText);
                cells[i][j] = editText;
            }
        }
    }

    // Method to validate the Sudoku grid
    private boolean validateSudoku() {
        boolean[] seen;
        int num;

        // Validate rows and columns
        for (int i = 0; i < 9; i++) {
            seen = new boolean[9];
            for (int j = 0; j < 9; j++) {
                String value = cells[i][j].getText().toString();
                if (!value.isEmpty()) {
                    num = Integer.parseInt(value) - 1;
                    if (num < 0 || num >= 9 || seen[num]) {
                        return false;
                    }
                    seen[num] = true;
                }
            }
            seen = new boolean[9];
            for (int j = 0; j < 9; j++) {
                String value = cells[j][i].getText().toString();
                if (!value.isEmpty()) {
                    num = Integer.parseInt(value) - 1;
                    if (num < 0 || num >= 9 || seen[num]) {
                        return false;
                    }
                    seen[num] = true;
                }
            }
        }

        // Validate 3x3 sub-grids
        for (int block = 0; block < 9; block++) {
            seen = new boolean[9];
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    int row = i + (block / 3) * 3;
                    int col = j + (block % 3) * 3;
                    String value = cells[row][col].getText().toString();
                    if (!value.isEmpty()) {
                        num = Integer.parseInt(value) - 1;
                        if (num < 0 || num >= 9 || seen[num]) {
                            return false;
                        }
                        seen[num] = true;
                    }
                }
            }
        }

        return true;
    }

    // Method to solve the Sudoku using backtracking
    private boolean solveSudoku() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (cells[row][col].getText().toString().isEmpty()) {
                    for (int num = 1; num <= 9; num++) {
                        if (isSafe(row, col, num)) {
                            cells[row][col].setText(String.valueOf(num));
                            if (solveSudoku()) {
                                return true;
                            } else {
                                cells[row][col].setText("");
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Check if a number can be placed in a cell
    private boolean isSafe(int row, int col, int num) {
        String numString = String.valueOf(num);

        // Check the row
        for (int i = 0; i < 9; i++) {
            if (cells[row][i].getText().toString().equals(numString)) {
                return false;
            }
        }

        // Check the column
        for (int i = 0; i < 9; i++) {
            if (cells[i][col].getText().toString().equals(numString)) {
                return false;
            }
        }

        // Check the 3x3 grid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (cells[i + startRow][j + startCol].getText().toString().equals(numString)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Display the solution on the grid
    private void displaySolution() {
        Toast.makeText(MainActivity.this, "Sudoku Solved!", Toast.LENGTH_SHORT).show();
    }
}
