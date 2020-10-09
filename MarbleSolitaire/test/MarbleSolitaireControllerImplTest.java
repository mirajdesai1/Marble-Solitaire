import static org.junit.Assert.assertEquals;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl;
import java.io.StringReader;
import org.junit.Test;


/**
 * Tests for the controller implementation of Marble Solitaire.
 */
public class MarbleSolitaireControllerImplTest {

  //code taken from lecture 9 on mocks and test harnesses
  static Interaction inputs(String in) {
    return (input, output) -> {
      input.append(in);
    };
  }

  static Interaction prints(String... lines) {
    return (input, output) -> {
      for (String line : lines) {
        output.append(line).append('\n');
      }
    };
  }

  String[] testRun(MarbleSolitaireModel model, Interaction... interactions) {
    StringBuilder fakeUserInput = new StringBuilder();
    StringBuilder expectedOutput = new StringBuilder();

    for (Interaction interaction : interactions) {
      interaction.apply(fakeUserInput, expectedOutput);
    }

    StringReader input = new StringReader(fakeUserInput.toString());
    StringBuilder actualOutput = new StringBuilder();

    MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(input, actualOutput);
    controller.playGame(model);

    return new String[]{expectedOutput.toString(), actualOutput.toString()};
  }


  @Test(expected = IllegalArgumentException.class)
  public void NullReadableThrowsException() {
    new MarbleSolitaireControllerImpl(null, new StringBuilder());
  }

  @Test(expected = IllegalArgumentException.class)
  public void NullAppendableThrowsException() {
    new MarbleSolitaireControllerImpl(new StringReader("2 2 q"), null);
  }

  @Test(expected = IllegalStateException.class)
  public void EmptyInputThrowsException() {
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl();
    StringBuilder gameLog = new StringBuilder();
    MarbleSolitaireController ms = new MarbleSolitaireControllerImpl(new StringReader(""),
        gameLog);
    ms.playGame(model);
  }

  @Test(expected = IllegalStateException.class)
  public void GameIsNotOverButUserDoesNotEndGame() {
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl();
    StringBuilder gameLog = new StringBuilder();
    MarbleSolitaireController ms = new MarbleSolitaireControllerImpl(new StringReader("2 4 4 4"),
        gameLog);
    ms.playGame(model);
  }


  @Test(expected = IllegalArgumentException.class)
  public void NullModelThrowsException() {
    MarbleSolitaireController ms = new MarbleSolitaireControllerImpl(new StringReader("q"),
        new StringBuilder());
    ms.playGame(null);
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    MarbleSolitaireModel m = new MarbleSolitaireModelImpl();
    StringReader input = new StringReader("2 4 4 4 5 4 3 4 4 2 4 4 4 5 4 3 4 7 4 5 7 4 5 4");
    Appendable gameLog = new FailingAppendable();
    MarbleSolitaireController c = new MarbleSolitaireControllerImpl(input, gameLog);
    c.playGame(m);
  }


  @Test
  public void testFullGameToCompletion() {
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl();
    String[] output = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("2 4 4 4 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31"),
        inputs("5 4 3 4 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 30"),
        inputs("4 2 4 4 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ _ O O O O\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 29"),
        inputs("4 5 4 3 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ O _ _ O O\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 28"),
        inputs("4 7 4 5 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ O _ O _ _\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 27"),
        inputs("7 4 5 4 q"),
        prints("Game over!"),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ O _ O _ _\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O _ O\n"
            + "Score: 26"));
    assertEquals(output[0], output[1]);
  }

  @Test
  public void testBogusInputsDoNotEffectGame() {
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl();
    String[] output = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("& -3 u k "),
        inputs("2 4 4 4 -3 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31"),
        inputs("5 4 bogus 3 4 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 30"),
        inputs("4 cs3500 2 4 4 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ _ O O O O\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 29"),
        inputs("4 5 4 fundies 3 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ O _ _ O O\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 28"),
        inputs("4 7 4 - 5 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ O _ O _ _\n"
            + "O O O _ O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 27"),
        inputs("7 4 % 5 ^ 4 ! q"),
        prints("Game over!"),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O O O O O\n"
            + "O _ O _ O _ _\n"
            + "O O O O O O O\n"
            + "    O _ O\n"
            + "    O _ O\n"
            + "Score: 26"));
    assertEquals(output[0], output[1]);
  }

  @Test
  public void testSingleMoveThenQuit() {
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl();
    String[] output = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("2 4 4 4 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31"),
        inputs("q"),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31"));
    assertEquals(output[0], output[1]);
  }

  @Test
  public void testInvalidMoveThenValidMove() {
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl();
    String[] output = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("4 4 4 6 "),
        prints("Invalid move. Play again. The slot you are trying to move is not a marble."),
        inputs("2 4 4 4 "),
        prints("    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31"),
        inputs("q"),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O _ O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31"));
    assertEquals(output[0], output[1]);
  }

  @Test
  public void testControllerSendingCorrectInputs() {
    StringReader in = new StringReader("2 4 4 4 5 4 3 4 4 2 4 4 4 5 4 3 4 7 4 5 7 4 5 4 q");
    StringBuilder outputDoesNotMatter = new StringBuilder();
    MarbleSolitaireController mockGame = new MarbleSolitaireControllerImpl(in, outputDoesNotMatter);

    StringBuilder log = new StringBuilder();
    MarbleSolitaireModel mockModel = new MockModel(log);
    mockGame.playGame(mockModel);
    assertEquals("fromRow = 1, fromCol = 3, toRow = 3, toCol = 3\n"
        + "fromRow = 4, fromCol = 3, toRow = 2, toCol = 3\n"
        + "fromRow = 3, fromCol = 1, toRow = 3, toCol = 3\n"
        + "fromRow = 3, fromCol = 4, toRow = 3, toCol = 2\n"
        + "fromRow = 3, fromCol = 6, toRow = 3, toCol = 4\n"
        + "fromRow = 6, fromCol = 3, toRow = 4, toCol = 3\n", log.toString());
  }

  @Test
  public void testControllerSendingCorrectInputsWithBogusInputs() {
    StringReader in = new StringReader(
        "2 bogus 4 -3 4 -12 4 !& 5 ( 4 3 4 4 2 4 4 $$ 4 + 5 cs3500 4 QQ 3 4 7 4 5 TE 7 4 5 4 q");
    StringBuilder outputDoesNotMatter = new StringBuilder();
    MarbleSolitaireController mockGame = new MarbleSolitaireControllerImpl(in, outputDoesNotMatter);

    StringBuilder log = new StringBuilder();
    MarbleSolitaireModel mockModel = new MockModel(log);
    mockGame.playGame(mockModel);
    assertEquals("fromRow = 1, fromCol = 3, toRow = 3, toCol = 3\n"
        + "fromRow = 4, fromCol = 3, toRow = 2, toCol = 3\n"
        + "fromRow = 3, fromCol = 1, toRow = 3, toCol = 3\n"
        + "fromRow = 3, fromCol = 4, toRow = 3, toCol = 2\n"
        + "fromRow = 3, fromCol = 6, toRow = 3, toCol = 4\n"
        + "fromRow = 6, fromCol = 3, toRow = 4, toCol = 3\n", log.toString());
  }

  @Test
  public void testControllerDoesNotSendInputsWhenAllAreBogus() {
    StringReader in = new StringReader("! fsa -2 -3 -4 -5 -6 -123 miraj desai Q");
    StringBuilder outputDoesNotMatter = new StringBuilder();
    MarbleSolitaireController mockGame = new MarbleSolitaireControllerImpl(in, outputDoesNotMatter);

    StringBuilder log = new StringBuilder();
    MarbleSolitaireModel mockModel = new MockModel(log);
    mockGame.playGame(mockModel);
    assertEquals("", log.toString());
  }

  @Test
  public void testImmediateGameEnd() {
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl(5);
    String[] outputsq = testRun(model, prints("        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O _ O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "Score: 104"),
        inputs("q"),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O _ O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "O O O O O O O O O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "        O O O O O\n"
            + "Score: 104"));
    assertEquals(outputsq[0], outputsq[1]);

    MarbleSolitaireModel model1 = new MarbleSolitaireModelImpl(3, 4);
    String[] outputsQ = testRun(model1, prints("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O _ O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("Q"),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O O _ O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"));
    assertEquals(outputsQ[0], outputsQ[1]);
  }

  @Test
  public void testQInsteadOfRowOrColumnInputs() {
    //testing 'q' instead of fromRow
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl(3, 2, 3);
    String[] output = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("q 1 4 3 4"),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"));
    assertEquals(output[0], output[1]);

    //testing 'Q' instead of fromCol
    String[] output1 = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("1 Q 4 3 4"),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"));
    assertEquals(output1[0], output1[1]);

    //testing 'Q' instead of toRow
    String[] output2 = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("1 4 Q 3 4"),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"));
    assertEquals(output2[0], output2[1]);

    //testing 'q' instead of toCol
    String[] output3 = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("1 4 3 Q 4"),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"));
    assertEquals(output3[0], output3[1]);
  }

  @Test
  public void testVariousErrorMessages() {
    MarbleSolitaireModel model = new MarbleSolitaireModelImpl();

    //moving a non-marble slot
    String[] output = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("4 4 4 6 q"),
        prints("Invalid move. Play again. The slot you are trying to move is not a marble."),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"));
    assertEquals(output[0], output[1]);

    //moving to a non-empty slot
    String[] output1 = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("3 2 3 4 q"),
        prints("Invalid move. Play again. The slot you are trying to move to is not empty."),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"));
    assertEquals(output1[0], output1[1]);

    //to and from positions are not two spaces apart
    String[] output2 = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("4 1 4 4 q"),
        prints("Invalid move. Play again. To and from positions are not two spaces apart."),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"));
    assertEquals(output2[0], output2[1]);

    //jumping over a non-marble
    MarbleSolitaireModel mutatedModel = new MarbleSolitaireModelImpl();
    mutatedModel.move(3, 1, 3, 3);
    String[] output3 = testRun(mutatedModel, prints("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O _ _ O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31"),
        inputs("4 1 4 3 q"),
        prints("Invalid move. Play again. The slot you are trying to jump over is not a marble."),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O _ _ O O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 31"));
    assertEquals(output3[0], output3[1]);

    //completely unreasonable move
    String[] output4 = testRun(model, prints("    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"),
        inputs("43 143 41 49 q"),
        prints("Invalid move. Play again. Invalid position entered."),
        prints("Game quit!\n"
            + "State of game when quit:\n"
            + "    O O O\n"
            + "    O O O\n"
            + "O O O O O O O\n"
            + "O O O _ O O O\n"
            + "O O O O O O O\n"
            + "    O O O\n"
            + "    O O O\n"
            + "Score: 32"));
    assertEquals(output4[0], output4[1]);
  }
}