import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * A mock model of the marble solitaire model strictly to test that the controller is sending the
 * correct values to the model.
 */
public class MockModel implements MarbleSolitaireModel {

  private final StringBuilder log;

  /**
   * Constructs a mock model with the given StringBuilder object to keep track of inputs.
   *
   * @param log a log to keep track of inputs sent to this mock model
   * @throws IllegalArgumentException if the given log is null
   */
  public MockModel(StringBuilder log) throws IllegalArgumentException {
    if (log == null) {
      throw new IllegalArgumentException("Requires non-null input.");
    }
    this.log = log;
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) {
    log.append(String
        .format("fromRow = %d, fromCol = %d, toRow = %d, toCol = %d\n", fromRow, fromCol, toRow,
            toCol));
  }

  @Override
  public boolean isGameOver() {
    return false;
  }

  @Override
  public String getGameState() {
    return "";
  }

  @Override
  public int getScore() {
    return 0;
  }
}
