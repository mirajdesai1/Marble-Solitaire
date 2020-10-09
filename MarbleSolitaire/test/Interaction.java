/**
 * An interaction with the user consists of some input to send the program
 * and some output to expect.  We represent it as an object that takes in two
 * StringBuilders and produces the intended effects on them
 */
public interface Interaction {
  //code taken from lecture 9 on mocks and test harnesses
  void apply(StringBuilder in, StringBuilder out);
}
