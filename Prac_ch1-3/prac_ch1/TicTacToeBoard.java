/*
練習 1.4：井字遊戲棋盤
使用二維陣列實作井字遊戲棋盤：
1. 初始化 3x3 的遊戲棋盤
2. 實作放置棋子的功能（檢查位置是否有效）
3. 檢查獲勝條件（行、列、對角線）
4. 判斷遊戲是否結束（獲勝或平手）
*/

import java.util.Scanner;

public class TicTacToeBoard {
    
    // Constants for game pieces
    public static final char EMPTY = ' ';
    public static final char PLAYER_X = 'X';
    public static final char PLAYER_O = 'O';
    
    // Board size
    private static final int BOARD_SIZE = 3;
    
    // Game board
    private char[][] board;
    
    /**
     * Constructor - initializes empty 3x3 board
     */
    public TicTacToeBoard() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
    }
    
    /**
     * Initialize the game board with empty spaces
     */
    public void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY;
            }
        }
    }
    
    /**
     * Place a piece on the board
     */
    public boolean placePiece(int row, int col, char player) {
        // Validate player
        if (player != PLAYER_X && player != PLAYER_O) {
            throw new IllegalArgumentException("Player must be 'X' or 'O'");
        }
        
        // Check if position is valid and empty
        if (isValidPosition(row, col) && isEmpty(row, col)) {
            board[row][col] = player;
            return true;
        }
        
        return false;
    }
    
    /**
     * Check if a position is valid (within board bounds)
     */
    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE;
    }
    
    /**
     * Check if a position is empty
     */
    public boolean isEmpty(int row, int col) {
        if (!isValidPosition(row, col)) {
            return false;
        }
        return board[row][col] == EMPTY;
    }
    
    /**
     * Check if there's a winner
     */
    public char checkWinner() {
        // Check rows
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] != EMPTY && 
                board[i][0] == board[i][1] && 
                board[i][1] == board[i][2]) {
                return board[i][0];
            }
        }
        
        // Check columns
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[0][j] != EMPTY && 
                board[0][j] == board[1][j] && 
                board[1][j] == board[2][j]) {
                return board[0][j];
            }
        }
        
        // Check main diagonal (top-left to bottom-right)
        if (board[0][0] != EMPTY && 
            board[0][0] == board[1][1] && 
            board[1][1] == board[2][2]) {
            return board[0][0];
        }
        
        // Check anti-diagonal (top-right to bottom-left)
        if (board[0][2] != EMPTY && 
            board[0][2] == board[1][1] && 
            board[1][1] == board[2][0]) {
            return board[0][2];
        }
        
        return EMPTY; // No winner
    }
    
    /**
     * Check if the board is full
     */
    public boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Check if the game is over
     */
    public boolean isGameOver() {
        return checkWinner() != EMPTY || isBoardFull();
    }
    
    /**
     * Display the game board
     */
    public void displayBoard() {
        System.out.println("\nCurrent Board:");
        System.out.println("   0   1   2");
        System.out.println("  -----------");
        
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + " |");
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(" " + board[i][j] + " |");
            }
            System.out.println();
            System.out.println("  -----------");
        }
    }
    
    /**
     * Get game status as string
     */
    public String getGameStatus() {
        char winner = checkWinner();
        if (winner != EMPTY) {
            return "Player " + winner + " wins!";
        } else if (isBoardFull()) {
            return "Game is a tie!";
        } else {
            return "Game in progress";
        }
    }
    
    /**
     * Reset the board to empty state
     */
    public void resetBoard() {
        initializeBoard();
    }
    
    /**
     * Play the interactive tic-tac-toe game
     */
    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        char currentPlayer = PLAYER_X;
        
        System.out.println("Welcome to Tic Tac Toe!");
        System.out.println("Players take turns placing X and O");
        System.out.println("Enter row and column (0-2) separated by space");
        System.out.println("Example: '1 2' places piece at row 1, column 2");
        
        while (!isGameOver()) {
            displayBoard();
            System.out.println("\nPlayer " + currentPlayer + "'s turn");
            System.out.print("Enter row and column (0-2): ");
            
            try {
                String input = scanner.nextLine().trim();
                
                // Handle quit command
                if (input.equalsIgnoreCase("quit") || input.equalsIgnoreCase("exit")) {
                    System.out.println("Thanks for playing!");
                    return;
                }
                
                // Parse input
                String[] parts = input.split("\\s+");
                if (parts.length != 2) {
                    System.out.println("Please enter exactly two numbers separated by space");
                    continue;
                }
                
                int row = Integer.parseInt(parts[0]);
                int col = Integer.parseInt(parts[1]);
                
                // Attempt to place piece
                if (placePiece(row, col, currentPlayer)) {
                    // Check if game is over after this move
                    char winner = checkWinner();
                    if (winner != EMPTY) {
                        displayBoard();
                        System.out.println("\n" + "=".repeat(30));
                        System.out.println("Player " + winner + " wins!");
                        System.out.println("=".repeat(30));
                        break;
                    } else if (isBoardFull()) {
                        displayBoard();
                        System.out.println("\n" + "=".repeat(30));
                        System.out.println("It's a tie!");
                        System.out.println("=".repeat(30));
                        break;
                    }
                    
                    // Switch players
                    currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
                } else {
                    if (!isValidPosition(row, col)) {
                        System.out.println("Invalid position! Please enter numbers between 0-2");
                    } else {
                        System.out.println("Position already taken! Choose an empty spot");
                    }
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter two numbers (0-2)");
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=".repeat(40));
        System.out.println("      INTERACTIVE TIC TAC TOE");
        System.out.println("=".repeat(40));
        
        while (true) {
            TicTacToeBoard game = new TicTacToeBoard();
            game.playGame();
            
            // Ask if player wants to play again
            System.out.print("\nWould you like to play again? (y/n): ");
            String playAgain = scanner.nextLine().trim().toLowerCase();
            
            if (!playAgain.equals("y") && !playAgain.equals("yes")) {
                System.out.println("\nThanks for playing Tic Tac Toe!");
                System.out.println("Goodbye!");
                break;
            }
            
            System.out.println("\n" + "=".repeat(40));
            System.out.println("      STARTING NEW GAME");
            System.out.println("=".repeat(40));
        }
        
        scanner.close();
    }
}