import static org.junit.Assert.assertEquals;

import cs3500.marblesolitaire.model.hw02.SolitaireSlot;
import org.junit.Test;

/**
 * Tests for the methods implemented in the SolitaireSlot enumeration.
 */
public class SolitaireSlotTest {

  @Test
  public void testToString() {
    assertEquals("O", SolitaireSlot.Marble.toString());
    assertEquals("_", SolitaireSlot.Empty.toString());
  }
}