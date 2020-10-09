package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

/**
 * What controls the flow of a game of Marble Solitaire. Processes user inputs and determines what
 * to do with these inputs. All inputs should start at 1 indicating a row/column number.
 */
public interface MarbleSolitaireController {

  /**
   * Plays a game of Marble Solitaire with the given Marble Solitaire model. The user moves marbles
   * until there are no valid moves left or the user has quit the game, and the playGame method
   * ends. Moves should be numbers starting at 1 indicating the row/column number.
   *
   * @param model a non-null Marble Solitaire model
   * @throws IllegalArgumentException if the given Marble Solitaire model is null
   * @throws IllegalStateException    if the controller is unable to successfully receive input or
   *                                  transmit output
   */
  void playGame(MarbleSolitaireModel model) throws IllegalArgumentException, IllegalStateException;
}
