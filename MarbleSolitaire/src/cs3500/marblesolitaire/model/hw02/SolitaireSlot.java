package cs3500.marblesolitaire.model.hw02;

/**
 * Represents the possible contents of a slot in Marble Solitaire.
 */
public enum SolitaireSlot {
  Marble(), Empty();

  SolitaireSlot() { }

  @Override
  public String toString() {
    switch (this) {
      case Marble: return "O";
      case Empty: return "_";
      default: throw new IllegalArgumentException("Invalid solitaire slot.");
    }
  }
}
