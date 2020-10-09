package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.SolitaireSlot;
import java.util.ArrayList;

/**
 * Represents a triangle model of the game Marble Solitaire. The pegs are arranged in a
 * triangular shape.
 */
public class TriangleSolitaireModelImpl extends AbstractMarbleSolitaireModel {

  /**
   * Constructs a default game of triangle Marble Solitaire with size 5 and hole located at the top
   * of the triangle.
   */
  public TriangleSolitaireModelImpl() {
    super(5, 0, 0);
  }

  /**
   * Constructs a game of triangle Marble Solitaire with the given side length and hole located at
   * the top of the triangle.
   * @param sideLength the side length of the triangle
   * @throws IllegalArgumentException if the given side length is less than or equal to 0
   */
  public TriangleSolitaireModelImpl(int sideLength) throws IllegalArgumentException {
    super(sideLength, 0, 0);
  }

  /**
   * Constructs a game of triangle Marble Solitaire with a default size of 5 and a hole located
   * at the given position.
   * @param row the row at which the initial hole is to be located
   * @param col the column at which the initial hole is to be located
   * @throws IllegalArgumentException if the given hole position is invalid
   */
  public TriangleSolitaireModelImpl(int row, int col) throws IllegalArgumentException {
    super(5, row, col);
  }

  /**
   * Constructs a game of triangle Marble Solitaire with the given side length and hole position.
   * @param sideLength the side length of the triangle
   * @param row the row at which the initial hole is to be located
   * @param col the column at which the initial hole is to be located
   * @throws IllegalArgumentException if the side length or the initial hole position are invalid
   */
  public TriangleSolitaireModelImpl(int sideLength, int row, int col)
      throws IllegalArgumentException {
    super(sideLength, row, col);
  }

  @Override
  protected SolitaireSlot[][] createBoard(int size, int row, int col) {
    if (size < 1) {
      throw new IllegalArgumentException("The specified dimension is invalid.");
    }
    if (row < 0 || row >= size || col < 0 || col >= size || col > row) {
      throw new IllegalArgumentException("The specified hole position is invalid.");
    }

    SolitaireSlot[][] board = new SolitaireSlot[size][size];

    for (int i = 0; i < size; i++) {
      for (int j = 0; j < i + 1; j++) {
        if (i == row && j == col) {
          board[i][j] = SolitaireSlot.Empty;
        } else {
          board[i][j] = SolitaireSlot.Marble;
        }
      }
    }
    return board;
  }

  @Override
  protected boolean checkDiagonal(int fromRow, int fromCol, int toRow, int toCol) {
    return (Math.abs(fromRow - toRow) == 2) && (Math.abs(fromCol - toCol) == 2);
  }

  @Override
  protected boolean hasDiagonalMove(int row, int col) {

    if (gameBoard[row][col] == SolitaireSlot.Marble) {
      if (row > 1) {
        if (col > 1) {
          if (gameBoard[row - 1][col - 1] == SolitaireSlot.Marble) {
            if (gameBoard[row - 2][col - 2] == SolitaireSlot.Empty) {
              return true;
            }
          }
        }
        if (col < gameBoard.length - 2) {
          if (gameBoard[row - 1][col + 1] == SolitaireSlot.Marble) {
            if (gameBoard[row - 2][col + 2] == SolitaireSlot.Empty) {
              return true;
            }
          }
        }
      }
      if (row < gameBoard.length - 2) {
        if (col > 1) {
          if (gameBoard[row + 1][col - 1] == SolitaireSlot.Marble) {
            if (gameBoard[row + 2][col - 2] == SolitaireSlot.Empty) {
              return true;
            }
          }
        }
        if (col < gameBoard.length - 2) {
          if (gameBoard[row + 1][col + 1] == SolitaireSlot.Marble) {
            if (gameBoard[row + 2][col + 2] == SolitaireSlot.Empty) {
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  @Override
  public String getGameState() {
    StringBuilder gameState = new StringBuilder();
    ArrayList<String> temp;
    for (int i = 0; i < gameBoard.length; i++) {
      temp = new ArrayList<>();
      for (int j = 0; j < i + 1; j++) {
        temp.add(gameBoard[i][j].toString());
      }
      gameState.append(" ".repeat(gameBoard.length - i - 1) + String.join(" ", temp) + "\n");
    }

    return gameState.substring(0, gameState.length() - 1);
  }
}
