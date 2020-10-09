package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.SolitaireSlot;
import java.util.ArrayList;

/**
 * Represents an abstract marble solitaire model that contains methods pertinent to all models.
 */
public abstract class AbstractMarbleSolitaireModel implements MarbleSolitaireModel {

  protected final SolitaireSlot[][] gameBoard;

  /**
   * Constructs a Marble Solitaire game with the given size and hole position.
   * @param size the size of the game board
   * @param row the row at which the initial hole is to be located
   * @param col the column at which the initial hole is to be located
   * @throws IllegalArgumentException if either the given size of the game board or the initial hole
   *         position are invalid
   */
  public AbstractMarbleSolitaireModel(int size, int row, int col) throws IllegalArgumentException {
    this.gameBoard = createBoard(size, row, col);
  }

  /**
   * Create a 2-Dimensional board of SolitaireSlots that represents a Marble Solitaire game.
   * @param size the size of the game board
   * @param row the row at which the hole is to be located
   * @param col the column at which the hole is to be located
   * @return a 2-Dimensional SolitaireSlot board representing a Marble Solitaire game
   */
  protected abstract SolitaireSlot[][] createBoard(int size, int row, int col);

  /**
   * Move a single marble from a given position to another given position. A move is valid only if
   * the from and to positions are valid. Specific implementations may place additional constraints
   * on the validity of a move.
   *
   * @param fromRow the row number of the position to be moved from (starts at 0)
   * @param fromCol the column number of the position to be moved from (starts at 0)
   * @param toRow   the row number of the position to be moved to (starts at 0)
   * @param toCol   the column number of the position to be moved to (starts at 0)
   * @throws IllegalArgumentException if the slot the user is trying to move is not a marble
   * @throws IllegalArgumentException if the slot the user is trying to move to is not empty
   * @throws IllegalArgumentException if the to and from positions are not two spaces apart
   * @throws IllegalArgumentException if the user is not jumping over a marble
   */
  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    if (!(fromRow >= 0 && fromRow < gameBoard.length
        && fromCol >= 0 && fromCol < gameBoard.length
        && toRow >= 0 && toRow < gameBoard.length
        && toCol >= 0 && toCol < gameBoard.length)) {
      throw new IllegalArgumentException("Invalid position entered.");
    }
    if (gameBoard[fromRow][fromCol] != SolitaireSlot.Marble) {
      throw new IllegalArgumentException("The slot you are trying to move is not a marble.");
    }
    if (gameBoard[toRow][toCol] != SolitaireSlot.Empty) {
      throw new IllegalArgumentException("The slot you are trying to move to is not empty.");
    }
    if (!((fromRow == toRow && Math.abs(fromCol - toCol) == 2)
        || (fromCol == toCol && Math.abs(fromRow - toRow) == 2)
        || checkDiagonal(fromRow, fromCol, toRow, toCol))) {
      throw new IllegalArgumentException("To and from positions are not two spaces apart.");
    }

    int middleRow = (fromRow - toRow) / 2;
    int middleCol = (fromCol - toCol) / 2;
    if (gameBoard[toRow + middleRow][toCol + middleCol] != SolitaireSlot.Marble) {
      throw new IllegalArgumentException("The slot you are trying to jump over is not a marble.");
    }

    gameBoard[fromRow][fromCol] = SolitaireSlot.Empty;
    gameBoard[toRow + middleRow][toCol + middleCol] = SolitaireSlot.Empty;
    gameBoard[toRow][toCol] = SolitaireSlot.Marble;
  }

  /**
   * Determine if there is a valid diagonal move from and to the given positions.
   * @param fromRow the row at which the slot is moving from
   * @param fromCol the column at which the slot is moving from
   * @param toRow the row at which the slot is moving to
   * @param toCol the column at which the slot is moving to
   * @return true if there is a valid diagonal move from the given position to the given position,
   *         false otherwise
   */
  protected boolean checkDiagonal(int fromRow, int fromCol, int toRow, int toCol) {
    return false;
  }

  /**
   * Determines if the given SolitaireSlot position in the board has any valid diagonal moves
   * respective to the model.
   * @param row the row at which the SolitaireSlot is located
   * @param col the column at which the solitaireSlot is located
   * @return true if the slot has a diagonal move, false otherwise
   */
  protected boolean hasDiagonalMove(int row, int col) {
    return false;
  }

  @Override
  public boolean isGameOver() {
    for (int i = 0; i < gameBoard.length; i++) {
      for (int j = 0; j < gameBoard.length; j++) {
        if (gameBoard[i][j] == SolitaireSlot.Marble) {
          if (hasValidMoves(i, j)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  /**
   * Determines if the given SolitaireSlot has any valid moves left.
   * @param row the row at which the slot is located
   * @param col the column at which the slot is located
   * @return true if the slot has any valid moves, false otherwise
   */
  private boolean hasValidMoves(int row, int col) {
    if (!(row >= 0 && row < gameBoard.length && col >= 0 && col < gameBoard.length)) {
      throw new IllegalArgumentException("Invalid position.");
    }
    if (gameBoard[row][col] == SolitaireSlot.Marble) {
      if (row > 1) {
        if (gameBoard[row - 1][col] == SolitaireSlot.Marble) {
          if (gameBoard[row - 2][col] == SolitaireSlot.Empty) {
            return true;
          }
        }
      }
      if (row < gameBoard.length - 2) {
        if (gameBoard[row + 1][col] == SolitaireSlot.Marble) {
          if (gameBoard[row + 2][col] == SolitaireSlot.Empty) {
            return true;
          }
        }
      }
      if (col > 1) {
        if (gameBoard[row][col - 1] == SolitaireSlot.Marble) {
          if (gameBoard[row][col - 2] == SolitaireSlot.Empty) {
            return true;
          }
        }
      }
      if (col < gameBoard.length - 2) {
        if (gameBoard[row][col + 1] == SolitaireSlot.Marble) {
          if (gameBoard[row][col + 2] == SolitaireSlot.Empty) {
            return true;
          }
        }
      }
    }
    return hasDiagonalMove(row, col);
  }

  @Override
  public String getGameState() {
    StringBuilder gameState = new StringBuilder();
    ArrayList<String> temp;

    for (SolitaireSlot[] row: gameBoard) {
      temp = new ArrayList<>();

      for (int j = 0; j < row.length; j++) {
        if (row[j] == null) {
          if (j == 0 || row[j - 1] == null) {
            temp.add(" ");
          } else {
            break;
          }
        } else {
          temp.add(row[j].toString());
        }
      }
      gameState.append(String.join(" ", temp) + "\n");
    }
    return gameState.substring(0, gameState.length() - 1);
  }

  @Override
  public int getScore() {
    int score = 0;
    for (SolitaireSlot[] row: gameBoard) {
      for (SolitaireSlot slot: row) {
        if (slot == SolitaireSlot.Marble) {
          score += 1;
        }
      }
    }
    return score;
  }
}