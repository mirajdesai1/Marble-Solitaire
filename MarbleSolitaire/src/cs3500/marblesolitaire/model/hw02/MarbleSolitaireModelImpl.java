package cs3500.marblesolitaire.model.hw02;

import cs3500.marblesolitaire.model.hw04.AbstractMarbleSolitaireModel;

/**
 * Represents a single game of Marble Solitaire with customizable initial hole position and board
 * size. The game is played until there are no valid moves left.
 */
public class MarbleSolitaireModelImpl extends AbstractMarbleSolitaireModel {

  /**
   * Constructs a default game of Marble Solitaire with a central hole and arm thickness of 3.
   */
  public MarbleSolitaireModelImpl() {
    super(3, 3, 3);
  }

  /**
   * Constructs a game of Marble Solitaire with an arm thickness of 3 and a hole located at the
   * given position.
   *
   * @param sRow the row at which the hole is to be located
   * @param sCol the column at which the hole is to be located
   * @throws IllegalArgumentException if the given position is an invalid hole position
   */
  public MarbleSolitaireModelImpl(int sRow, int sCol) throws IllegalArgumentException {
    super(3, sRow, sCol);
  }

  /**
   * Constructs a game of Marble Solitaire with the given arm thickness and a central hole.
   *
   * @param armThickness the arm thickness the game is to be made with
   * @throws IllegalArgumentException if the given arm thickness is one or is an even number
   */
  public MarbleSolitaireModelImpl(int armThickness) throws IllegalArgumentException {
    super(armThickness, (armThickness - 1) + armThickness / 2,
        (armThickness - 1) + armThickness / 2);
  }

  /**
   * Constructs a game of Marble Solitaire with the given arm thickness and a hole located at the
   * given position.
   *
   * @param armThickness the arm thickness the game is to be made with
   * @param sRow         the row at which the hole is to be located
   * @param sCol         the column at which the hole is to be located
   * @throws IllegalArgumentException if the arm thickness or the given position is an invalid hole
   *                                  position
   */
  public MarbleSolitaireModelImpl(int armThickness, int sRow, int sCol) throws
      IllegalArgumentException {
    super(armThickness, sRow, sCol);
  }

  @Override
  protected SolitaireSlot[][] createBoard(int armThickness, int sRow, int sCol) throws
      IllegalArgumentException {

    if (!(armThickness > 1 && armThickness % 2 == 1)) {
      throw new IllegalArgumentException("Invalid arm thickness.");
    }
    int lower = armThickness - 2;
    int upper = 2 * armThickness - 1;
    SolitaireSlot[][] temp = new SolitaireSlot[armThickness + 2 * (armThickness - 1)][armThickness
        + 2 * (armThickness - 1)];

    if (sRow < 0 || sRow >= temp.length || sCol < 0 || sCol >= temp.length
        || ! ((sRow > lower && sRow < upper) || (sCol > lower && sCol < upper))) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol + ")");
    }

    for (int i = 0; i < temp.length; i++) {
      for (int j = 0; j < temp.length; j++) {
        if (i == sRow && j == sCol) {
          temp[i][j] = SolitaireSlot.Empty;
        } else if ((i > lower && i < upper) || (j > lower && j < upper)) {
          temp[i][j] = SolitaireSlot.Marble;
        }
      }
    }
    return temp;
  }


}
