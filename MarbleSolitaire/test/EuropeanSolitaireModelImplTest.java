import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModelImpl;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests for the European model implementation of Marble Solitiare.
 */
public class EuropeanSolitaireModelImplTest {

  MarbleSolitaireModel defaultCons;
  MarbleSolitaireModel size5WithCenterHole;
  MarbleSolitaireModel size3WithNorthHole;
  MarbleSolitaireModel size5WithSouthHole;
  MarbleSolitaireModel size3WithCornerHole;

  private void initData() {
    defaultCons = new EuropeanSolitaireModelImpl();
    size5WithCenterHole = new EuropeanSolitaireModelImpl(5);
    size3WithNorthHole = new EuropeanSolitaireModelImpl(0, 3);
    size5WithSouthHole = new EuropeanSolitaireModelImpl(5, 12, 6);
    size3WithCornerHole = new EuropeanSolitaireModelImpl(1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consTwoDisallowsNegative() {
    new EuropeanSolitaireModelImpl(-5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consTwoDisallowsEvenThickness() {
    new EuropeanSolitaireModelImpl(6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consThreeDisallowsNegativePosition() {
    new EuropeanSolitaireModelImpl(-1, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consThreeDisallowsNullPosition() {
    new EuropeanSolitaireModelImpl(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consFourDisallowsNegativeArm() {
    new EuropeanSolitaireModelImpl(-7, 7, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consFourDisallowsEvenArm() {
    new EuropeanSolitaireModelImpl(4, 5, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consFourDisallowsNegativePosition() {
    new EuropeanSolitaireModelImpl(3, -3, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consFourDisallowsNullPosition() {
    new EuropeanSolitaireModelImpl(5, 0, 2);
  }



  @Test
  public void testGetGameState() {
    initData();
    assertEquals("    O O O\n"
        + "  O O O O O\n"
        + "O O O O O O O\n"
        + "O O O _ O O O\n"
        + "O O O O O O O\n"
        + "  O O O O O\n"
        + "    O O O", defaultCons.getGameState());

    assertEquals("        O O O O O\n"
        + "      O O O O O O O\n"
        + "    O O O O O O O O O\n"
        + "  O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O _ O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "  O O O O O O O O O O O\n"
        + "    O O O O O O O O O\n"
        + "      O O O O O O O\n"
        + "        O O O O O", size5WithCenterHole.getGameState());

    assertEquals("    O _ O\n"
        + "  O O O O O\n"
        + "O O O O O O O\n"
        + "O O O O O O O\n"
        + "O O O O O O O\n"
        + "  O O O O O\n"
        + "    O O O", size3WithNorthHole.getGameState());

    assertEquals("        O O O O O\n"
        + "      O O O O O O O\n"
        + "    O O O O O O O O O\n"
        + "  O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "O O O O O O O O O O O O O\n"
        + "  O O O O O O O O O O O\n"
        + "    O O O O O O O O O\n"
        + "      O O O O O O O\n"
        + "        O O _ O O", size5WithSouthHole.getGameState());

    assertEquals("    O O O\n"
        + "  _ O O O O\n"
        + "O O O O O O O\n"
        + "O O O O O O O\n"
        + "O O O O O O O\n"
        + "  O O O O O\n"
        + "    O O O", size3WithCornerHole.getGameState());
  }

  @Test
  public void testGetScore() {
    initData();
    assertEquals(36, defaultCons.getScore());
    assertEquals(36, size3WithCornerHole.getScore());
    assertEquals(36, size3WithNorthHole.getScore());
    assertEquals(128, size5WithCenterHole.getScore());
    assertEquals(128, size5WithSouthHole.getScore());
  }

  @Test
  public void testMove() {
    initData();
    //move north to south
    defaultCons.move(1, 3, 3, 3);
    assertEquals("    O O O\n"
        + "  O O _ O O\n"
        + "O O O _ O O O\n"
        + "O O O O O O O\n"
        + "O O O O O O O\n"
        + "  O O O O O\n"
        + "    O O O", defaultCons.getGameState());
    assertEquals(35, defaultCons.getScore());

    //move south to north
    defaultCons.move(4, 3, 2, 3);
    assertEquals("    O O O\n"
        + "  O O _ O O\n"
        + "O O O O O O O\n"
        + "O O O _ O O O\n"
        + "O O O _ O O O\n"
        + "  O O O O O\n"
        + "    O O O", defaultCons.getGameState());
    assertEquals(34, defaultCons.getScore());

    //move west to east
    defaultCons.move(3, 1, 3, 3);
    assertEquals("    O O O\n"
        + "  O O _ O O\n"
        + "O O O O O O O\n"
        + "O _ _ O O O O\n"
        + "O O O _ O O O\n"
        + "  O O O O O\n"
        + "    O O O", defaultCons.getGameState());
    assertEquals(33, defaultCons.getScore());

    //move east to west
    defaultCons.move(4, 5, 4, 3);
    assertEquals("    O O O\n"
        + "  O O _ O O\n"
        + "O O O O O O O\n"
        + "O _ _ O O O O\n"
        + "O O O O _ _ O\n"
        + "  O O O O O\n"
        + "    O O O", defaultCons.getGameState());
    assertEquals(32, defaultCons.getScore());

    //move corner piece
    defaultCons.move(1, 1, 3, 1);
    assertEquals("    O O O\n"
        + "  _ O _ O O\n"
        + "O _ O O O O O\n"
        + "O O _ O O O O\n"
        + "O O O O _ _ O\n"
        + "  O O O O O\n"
        + "    O O O", defaultCons.getGameState());
    assertEquals(31, defaultCons.getScore());
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
    defaultCons.move(1, 1, 3, 3);
  }

  @Test
  public void testIsGameOver() {
    initData();
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(3, 1, 3, 3);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(1, 1, 3, 1);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 1, 2, 1);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 3, 4, 1);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 0, 4, 2);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(5, 2, 3, 2);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(2, 0, 4, 0);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(2, 2, 4, 2);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(0, 2, 2, 2);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(2, 2, 2, 0);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(0, 4, 0, 2);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(2, 3, 0, 3);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(0, 2, 0, 4);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(1, 5, 1, 3);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(3, 4, 1, 4);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(1, 4, 1, 2);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(3, 5, 1, 5);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 5, 4, 3);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 3, 2, 3);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(6, 3, 4, 3);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 2, 4, 4);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(5, 4, 3, 4);

    assertTrue(defaultCons.isGameOver());
    assertEquals(14, defaultCons.getScore());
    assertEquals("    _ _ O\n"
        + "  _ O _ _ O\n"
        + "O _ _ O _ _ O\n"
        + "_ _ _ _ O _ O\n"
        + "O _ _ _ _ _ O\n"
        + "  O _ _ _ O\n"
        + "    O _ O", defaultCons.getGameState());
  }
}
