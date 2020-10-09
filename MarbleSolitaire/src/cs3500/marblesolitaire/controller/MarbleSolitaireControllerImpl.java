package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import java.io.IOException;
import java.util.Scanner;

/**
 * What controls the flow of a game of Marble Solitaire. Processes user inputs and determines what *
 * to do with these inputs. All inputs should start at 1 indicating a row/column number.
 */
public class MarbleSolitaireControllerImpl implements MarbleSolitaireController {

  private final Appendable out;
  private final Readable in;

  /**
   * Constructs a Marble Solitaire controller with a the given readable and appendable inputs.
   *
   * @param rd inputs given to the program
   * @param ap an Appendable object that holds all outputs given by the program
   * @throws IllegalArgumentException if either of the given inputs are null
   */
  public MarbleSolitaireControllerImpl(Readable rd, Appendable ap) throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Requires non-null inputs.");
    }

    this.out = ap;
    this.in = rd;
  }

  /**
   * Appends the given string to Appendable out.
   * @param str the string to append to the output
   */
  private void appendToOut(String str) {
    try {
      out.append(str);
    } catch (IOException ioe) {
      throw new IllegalStateException("Append failed", ioe);
    }
  }

  /**
   * Assigns a move and a count within a size 2 int array.
   * @param input the input given by the user
   * @param count represents what move the user is on
   * @return an int array of size 2 that contains the move at index 0 and the count at index 1
   */
  private int[] assignDigit(String input, int count) {
    int[] temp = new int[2];
    temp[1] = count;
    try {
      int digit = Integer.parseInt(input);
      if (digit <= 0) {
        System.out.print("Re-enter value: ");
        return temp;
      }
      temp[0] = digit;
      temp[1] = count + 1;
      return temp;
    } catch (NumberFormatException nfe) {
      System.out.print("Re-enter value: ");
      return temp;
    }
  }


  @Override
  public void playGame(MarbleSolitaireModel model) {
    if (model == null) {
      throw new IllegalArgumentException("Requires non-null model.");
    }
    Scanner scan = new Scanner(in);
    int[] move = new int[4];
    boolean initialState = true;
    int count = 0;

    while (!model.isGameOver()) {
      if (initialState) {
        appendToOut(model.getGameState() + "\nScore: " + model.getScore() + "\n");
        System.out.print("Enter a move: ");
        initialState = false;
      }

      if (count == 4) {
        try {
          model.move(move[0] - 1, move[1] - 1, move[2] - 1, move[3] - 1);
          initialState = true;
          count = 0;
          continue;
        } catch (IllegalArgumentException iae) {
          appendToOut("Invalid move. Play again. " + iae.getMessage() + "\n");
          count = 0;
        }
      }

      if (!scan.hasNext()) {
        throw new IllegalStateException("No more inputs left.");
      }
      String input = scan.next();

      if (input.equals("q") || input.equals("Q")) {
        appendToOut("Game quit!\nState of game when quit:\n" + model.getGameState() + "\nScore: "
            + model.getScore() + "\n");
        return;
      }

      int[] moveAndCount = assignDigit(input, count);
      move[count] = moveAndCount[0];
      count = moveAndCount[1];
    }

    appendToOut("Game over!\n" + model.getGameState() + "\nScore: " + model.getScore() + "\n");
  }
}
