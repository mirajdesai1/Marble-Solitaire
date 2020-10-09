package cs3500.marblesolitaire;

import cs3500.marblesolitaire.controller.MarbleSolitaireController;
import cs3500.marblesolitaire.controller.MarbleSolitaireControllerImpl;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl;
import java.io.InputStreamReader;

/**
 * Play a game of Marble Solitaire. Pass in arguments to the main method to initialize the game.
 * All moves to the game should be a number greater than or equal to 1 indicating what row or column
 * you would like to move from and move to.
 */
public final class MarbleSolitaire {


  //create a Marble Solitaire model with the given specs
  private static MarbleSolitaireModel createModel(String model, int size, int holeRow, int holeCol,
      boolean isSizeDefault,
      boolean isHoleDefault) {
    switch (model) {
      case "english":
        if (isSizeDefault) {
          size = 3;
        }
        if (isHoleDefault) {
          holeRow = (size - 1) + size / 2;
          holeCol = (size - 1) + size / 2;
        }
        return new MarbleSolitaireModelImpl(size, holeRow, holeCol);
      case "european":
        if (isSizeDefault) {
          size = 3;
        }
        if (isHoleDefault) {
          holeRow = (size - 1) + size / 2;
          holeCol = (size - 1) + size / 2;
        }
        return new EuropeanSolitaireModelImpl(size, holeRow, holeCol);
      default:
        if (isSizeDefault) {
          size = 5;
        }
        if (isHoleDefault) {
          holeRow = 0;
          holeCol = 0;
        }
        return new TriangleSolitaireModelImpl(size, holeRow, holeCol);
    }
  }

  /**
   * Play a game of Marble Solitaire by passing in an array of arguments. Either 'english',
   * 'european', or 'triangle' to initialize the board. '-size' followed by some positive integer to
   * initialize the size of the board. '-hole' followed by two positive integers to set the initial
   * hole positive.
   *
   * @param args an array of commands that will dictate what kind of Marble Solitaire board is
   *             created.
   */
  public static void main(String[] args) {
    String model = "";
    boolean isSizeDefault = true;
    boolean isHoleDefault = true;
    int size = 0;
    int holeRow = 0;
    int holeCol = 0;
    MarbleSolitaireController controller = new MarbleSolitaireControllerImpl(
        new InputStreamReader(System.in),
        System.out);

    for (int i = 0; i < args.length; i++) {
      switch (args[i]) {
        case "-size":
          size = Integer.parseInt(args[i + 1]);
          i++;
          isSizeDefault = false;
          break;
        case "-hole":
          holeRow = Integer.parseInt(args[i + 1]);
          holeCol = Integer.parseInt(args[i + 2]);
          i += 2;
          isHoleDefault = false;
          break;
        case "english":
        case "european":
        case "triangle":
          model = args[i];
          break;
        default:
          break;
      }
    }

    MarbleSolitaireModel marbleSolitaireGame = createModel(model, size, holeRow, holeCol,
        isSizeDefault, isHoleDefault);
    controller.playGame(marbleSolitaireGame);

  }
}