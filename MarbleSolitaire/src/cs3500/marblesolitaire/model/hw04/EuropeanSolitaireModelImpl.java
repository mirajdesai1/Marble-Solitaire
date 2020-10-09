package cs3500.marblesolitaire.model.hw04;

import cs3500.marblesolitaire.model.hw02.SolitaireSlot;

/**
 * Represents a European model of Marble Solitaire. The corners between the arms of the cross are
 * filled in to produce an octagon shape.
 */
public class EuropeanSolitaireModelImpl extends AbstractMarbleSolitaireModel {

  /**
   * Constructs a default game of European Marble Solitaire with arm thickness size of 3 and
   * initial hole position at the center.
   */
  public EuropeanSolitaireModelImpl() {
    super(3, 3, 3);
  }

  /**
   * Constructs a European Marble Solitaire game with the given arm thickness and a hole located
   * at the center.
   * @param armThickness the arm thickness of the board
   * @throws IllegalArgumentException if the given arm thickness is not 3 or greater or is not odd
   */
  public EuropeanSolitaireModelImpl(int armThickness) throws IllegalArgumentException {
    super(armThickness, (armThickness - 1) + armThickness / 2,
        (armThickness - 1) + armThickness / 2);
  }

  /**
   * Constructs a European Marble Solitaire game with a default size 3 arm thickness, and a hole
   * located at the given position.
   * @param row the row at which the initial hole is to be located
   * @param col the column at which the initial hole is to be located
   * @throws IllegalArgumentException if the given position is an invalid hole position
   */
  public EuropeanSolitaireModelImpl(int row, int col) throws IllegalArgumentException {
    super(3, row, col);
  }

  /**
   * Constructs a European Marble Solitaire game with the given arm thickness and initial hole
   * position.
   * @param armThickness the arm thickness of the board
   * @param row the row at which the initial hole is to be located
   * @param col the column at which the initial hole is to be located
   * @throws IllegalArgumentException if the arm thickness or the hole position are invalid
   */
  public EuropeanSolitaireModelImpl(int armThickness, int row, int col)
      throws IllegalArgumentException {
    super(armThickness, row, col);
  }

  @Override
  protected SolitaireSlot[][] createBoard(int size, int row, int col) {
    if (!(size > 1 && size % 2 == 1)) {
      throw new IllegalArgumentException("Invalid arm thickness.");
    }

    SolitaireSlot[][] board = new SolitaireSlot[size + 2 * (size - 1)][size + 2 * (size - 1)];
    if (row < 0 || row >= board.length || col < 0 || col >= board.length) {
      throw new IllegalArgumentException("Invalid empty cell position (" + row + "," + col + ")");
    }
    int upper = 2 * size - 1;
    int lower = size - 2;

    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board.length; j++) {
        if (i == row && j == col) {
          if (!(j > lower && j < upper)) {
            throw new IllegalArgumentException(
                "Invalid empty cell position (" + row + "," + col + ")");
          } else {
            board[i][j] = SolitaireSlot.Empty;
          }
        } else if (j > lower && j < upper) {
          board[i][j] = SolitaireSlot.Marble;
        }
      }
      if (i < size - 1) {
        lower--;
        upper++;
      } else if (i >= 2 * size - 2) {
        lower++;
        upper--;
      }
    }
    return board;
  }
}
