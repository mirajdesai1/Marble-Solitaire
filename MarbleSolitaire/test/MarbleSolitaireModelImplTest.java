import static org.junit.Assert.assertEquals;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelImpl;
import org.junit.Test;

/**
 * Tests for the MarbleSolitaireModelImpl class, which implements the methods in
 * MarbleSolitaireModel.
 */
public class MarbleSolitaireModelImplTest {

  MarbleSolitaireModel defaultCons;
  MarbleSolitaireModel size5WithCenteredHole;
  MarbleSolitaireModel size3WithNorthHole;
  MarbleSolitaireModel size5WithSouthHole;

  /**
   * Data to be initialized before each test to avoid problems that may arise through mutation.
   */
  private void initData() {
    defaultCons = new MarbleSolitaireModelImpl();
    size5WithCenteredHole = new MarbleSolitaireModelImpl(5);
    size3WithNorthHole = new MarbleSolitaireModelImpl(0, 3);
    size5WithSouthHole = new MarbleSolitaireModelImpl(5, 12, 6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTwoDisallowsEvenArmThickness() {
    new MarbleSolitaireModelImpl(6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorTwoDisallowsOne() {
    new MarbleSolitaireModelImpl(1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorThreeDisallowsInvalidHolePos() {
    new MarbleSolitaireModelImpl(0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorThreeDisallowsNegativeHole() {
    new MarbleSolitaireModelImpl(-2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFourDisallowsEvenArmThickness() {
    new MarbleSolitaireModelImpl(8, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructorFourDisallowsOne() {
    new MarbleSolitaireModelImpl(1, 3, 3);
  }


  @Test(expected = IllegalArgumentException.class)
  public void constructorFourDisallowsInvalidHolePos() {
    new MarbleSolitaireModelImpl(3, 6, 6);
  }


  @Test
  public void testGetGameState() {
    initData();
    assertEquals("    O O O\n"
        + "    O O O\n"
        + "O O O O O O O\n"
        + "O O O _ O O O\n"
        + "O O O O O O O\n"
        + "    O O O\n"
        + "    O O O", defaultCons.getGameState());
    assertEquals("        O O O O O\n"
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
        + "        O O O O O", size5WithCenteredHole.getGameState());
    assertEquals("    O _ O\n"
        + "    O O O\n"
        + "O O O O O O O\n"
        + "O O O O O O O\n"
        + "O O O O O O O\n"
        + "    O O O\n"
        + "    O O O", size3WithNorthHole.getGameState());
    assertEquals("        O O O O O\n"
        + "        O O O O O\n"
        + "        O O O O O\n"
        + "        O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "        O O O O O\n"
        + "        O O O O O\n"
        + "        O O O O O\n"
        + "        O O _ O O", size5WithSouthHole.getGameState());
  }

  @Test
  public void testGetScore() {
    initData();
    assertEquals(32, defaultCons.getScore());
    assertEquals(32, size3WithNorthHole.getScore());
    assertEquals(104, size5WithCenteredHole.getScore());
    assertEquals(104, size5WithSouthHole.getScore());
  }

  @Test
  public void testMove3x3() {
    initData();
    //move north to south
    defaultCons.move(1, 3, 3, 3);
    assertEquals("    O O O\n"
        + "    O _ O\n"
        + "O O O _ O O O\n"
        + "O O O O O O O\n"
        + "O O O O O O O\n"
        + "    O O O\n"
        + "    O O O", defaultCons.getGameState());
    assertEquals(31, defaultCons.getScore());
    //move south to north
    defaultCons.move(4, 3, 2, 3);
    assertEquals("    O O O\n"
        + "    O _ O\n"
        + "O O O O O O O\n"
        + "O O O _ O O O\n"
        + "O O O _ O O O\n"
        + "    O O O\n"
        + "    O O O", defaultCons.getGameState());
    //move west to east
    defaultCons.move(3, 1, 3, 3);
    assertEquals("    O O O\n"
        + "    O _ O\n"
        + "O O O O O O O\n"
        + "O _ _ O O O O\n"
        + "O O O _ O O O\n"
        + "    O O O\n"
        + "    O O O", defaultCons.getGameState());
    //move east to west
    defaultCons.move(4, 5, 4, 3);
    assertEquals("    O O O\n"
        + "    O _ O\n"
        + "O O O O O O O\n"
        + "O _ _ O O O O\n"
        + "O O O O _ _ O\n"
        + "    O O O\n"
        + "    O O O", defaultCons.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveNull() {
    initData();
    defaultCons.move(0, 0, 0, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveEmpty() {
    initData();
    defaultCons.move(3, 3, 3, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToNull() {
    initData();
    defaultCons.move(0, 3, 0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToMarble() {
    initData();
    defaultCons.move(0, 3, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidFromAndToPos() {
    initData();
    defaultCons.move(3, 0, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryingToJumpOverANonMarble() {
    initData();
    defaultCons.move(3, 2, 3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeInput() {
    initData();
    defaultCons.move(-1, 5, 2, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeInput1() {
    initData();
    defaultCons.move(1, 3, -1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void allNegative() {
    initData();
    defaultCons.move(-1, -3, -1, -5);
  }


  @Test(expected = IllegalArgumentException.class)
  public void notInBoard() {
    initData();
    defaultCons.move(3, 2, 16, 17);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveDiagonally() {
    initData();
    defaultCons.move(2, 2, 3, 3);
  }

  @Test
  public void testIsGameOver() {
    //Shortest possible game found from "durangobill.com/Peg33.html"
    initData();
    assertEquals(false, defaultCons.isGameOver());
    defaultCons.move(1, 3, 3, 3);
    assertEquals(false, defaultCons.isGameOver());
    defaultCons.move(4, 3, 2, 3);
    assertEquals(false, defaultCons.isGameOver());
    defaultCons.move(3, 1, 3, 3);
    assertEquals(false, defaultCons.isGameOver());
    defaultCons.move(3, 4, 3, 2);
    assertEquals(false, defaultCons.isGameOver());
    defaultCons.move(3, 6, 3, 4);
    assertEquals(false, defaultCons.isGameOver());
    defaultCons.move(6, 3, 4, 3);
    assertEquals(true, defaultCons.isGameOver());
    assertEquals("    O O O\n"
        + "    O _ O\n"
        + "O O O O O O O\n"
        + "O _ O _ O _ _\n"
        + "O O O O O O O\n"
        + "    O _ O\n"
        + "    O _ O", defaultCons.getGameState());

  }


}