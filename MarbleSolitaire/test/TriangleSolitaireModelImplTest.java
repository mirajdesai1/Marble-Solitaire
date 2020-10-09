import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModelImpl;
import org.junit.Test;

/**
 * Tests for the triangle model implementation of Marble Solitaire.
 */
public class TriangleSolitaireModelImplTest {

  MarbleSolitaireModel defaultCons;
  MarbleSolitaireModel size6WithNorthHole;
  MarbleSolitaireModel size7WithNortheastHole;
  MarbleSolitaireModel size5WithCenteredHole;

  private void initData() {
    defaultCons = new TriangleSolitaireModelImpl();
    size6WithNorthHole = new TriangleSolitaireModelImpl(6);
    size7WithNortheastHole = new TriangleSolitaireModelImpl(7, 2, 2);
    size5WithCenteredHole = new TriangleSolitaireModelImpl(2, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consTwoDisallowsNegativeLength() {
    new TriangleSolitaireModelImpl(-5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consThreeDisallowsNullPosition() {
    new TriangleSolitaireModelImpl(0, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consThreeDisallowsNegativeInput() {
    new TriangleSolitaireModelImpl(3, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consFourDisallowsNegativeLength() {
    new TriangleSolitaireModelImpl(-5, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consFourDisallowsNullHolePosition() {
    new TriangleSolitaireModelImpl(7, 2, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void consFourDisallowsNegativeHoleInput() {
    new TriangleSolitaireModelImpl(7, 2, -1);
  }

  @Test
  public void getScore() {
    initData();
    assertEquals(14, defaultCons.getScore());
    assertEquals(14, size5WithCenteredHole.getScore());
    assertEquals(20, size6WithNorthHole.getScore());
    assertEquals(27, size7WithNortheastHole.getScore());
  }


  @Test
  public void getGameState() {
    initData();
    assertEquals("    _\n"
        + "   O O\n"
        + "  O O O\n"
        + " O O O O\n"
        + "O O O O O", defaultCons.getGameState());
    assertEquals("     _\n"
        + "    O O\n"
        + "   O O O\n"
        + "  O O O O\n"
        + " O O O O O\n"
        + "O O O O O O", size6WithNorthHole.getGameState());
    assertEquals("      O\n"
        + "     O O\n"
        + "    O O _\n"
        + "   O O O O\n"
        + "  O O O O O\n"
        + " O O O O O O\n"
        + "O O O O O O O", size7WithNortheastHole.getGameState());
    assertEquals("    O\n"
        + "   O O\n"
        + "  O _ O\n"
        + " O O O O\n"
        + "O O O O O", size5WithCenteredHole.getGameState());
  }

  @Test
  public void testMove() {
    initData();
    //moving diagonally north
    size7WithNortheastHole.move(4, 4, 2, 2);
    assertEquals("      O\n"
        + "     O O\n"
        + "    O O O\n"
        + "   O O O _\n"
        + "  O O O O _\n"
        + " O O O O O O\n"
        + "O O O O O O O", size7WithNortheastHole.getGameState());

    //move south to north
    size7WithNortheastHole.move(5, 3, 3, 3);
    assertEquals("      O\n"
        + "     O O\n"
        + "    O O O\n"
        + "   O O O O\n"
        + "  O O O _ _\n"
        + " O O O _ O O\n"
        + "O O O O O O O", size7WithNortheastHole.getGameState());

    //move east to west
    size7WithNortheastHole.move(5, 5, 5, 3);
    assertEquals("      O\n"
        + "     O O\n"
        + "    O O O\n"
        + "   O O O O\n"
        + "  O O O _ _\n"
        + " O O O O _ _\n"
        + "O O O O O O O", size7WithNortheastHole.getGameState());

    //move west to east
    size7WithNortheastHole.move(4, 1, 4, 3);
    assertEquals("      O\n"
        + "     O O\n"
        + "    O O O\n"
        + "   O O O O\n"
        + "  O _ _ O _\n"
        + " O O O O _ _\n"
        + "O O O O O O O", size7WithNortheastHole.getGameState());

    //move north to south
    size7WithNortheastHole.move(2, 1, 4, 1);
    assertEquals("      O\n"
        + "     O O\n"
        + "    O _ O\n"
        + "   O _ O O\n"
        + "  O O _ O _\n"
        + " O O O O _ _\n"
        + "O O O O O O O", size7WithNortheastHole.getGameState());

    //moving diagonally south
    size7WithNortheastHole.move(2, 2, 4, 4);
    assertEquals("      O\n"
        + "     O O\n"
        + "    O _ _\n"
        + "   O _ O _\n"
        + "  O O _ O O\n"
        + " O O O O _ _\n"
        + "O O O O O O O", size7WithNortheastHole.getGameState());
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveNull() {
    initData();
    defaultCons.move(0, 1, 0, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveEmpty() {
    initData();
    defaultCons.move(0, 0, 2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToNull() {
    initData();
    defaultCons.move(3, 3, 3, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveToMarble() {
    initData();
    defaultCons.move(4, 0, 4, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidFromAndToPos() {
    initData();
    defaultCons.move(3, 0, 3, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void tryingToJumpOverANonMarble() {
    initData();
    defaultCons.move(1, 0, -1, 0);
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
  public void invalidDiagonalMove() {
    initData();
    defaultCons.move(1, 1, 3, 3);
  }

  @Test
  public void testIsGameOver() {
    initData();
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(2, 2, 0, 0);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(3, 1, 1, 1);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 2, 2, 2);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 0, 4, 2);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 3, 4, 1);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(2, 0, 4, 0);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(0, 0, 2, 0);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(2, 2, 0, 0);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 4, 2, 2);
    assertFalse(defaultCons.isGameOver());
    defaultCons.move(4, 0, 4, 2);

    assertTrue(defaultCons.isGameOver());
    assertEquals("    O\n"
        + "   _ _\n"
        + "  O _ O\n"
        + " _ _ _ _\n"
        + "_ _ O _ _", defaultCons.getGameState());
    assertEquals(4, defaultCons.getScore());

  }
}