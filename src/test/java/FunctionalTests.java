import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madhur on 14 Oct.
 */
public class FunctionalTests {

  static ArrayList<State> states = new ArrayList<State>();
  static ArrayList<Event> events = new ArrayList<Event>();

  static State zeroState = new State("0S", StateType.INITIAL);
  static State firstState = new State("1S");
  static State secondState = new State("2S");
  static State thirdState = new State("3S");
  static State fourthState = new State("4S");

  static State phantomState = new State("PHANTOM");

  static State cancelState = new State("CANCELLED", StateType.TERMINAL);
  static State completedState = new State("COMPLETED", StateType.TERMINAL);

  static Event firstEvent = new Event("1R");
  static Event secondEvent = new Event("2R");
  static Event cancelEvent = new Event("CANCEL");
  static Event buyEvent = new Event("BUY");

  static Event phantomEvent = new Event("PHANTOM");


  @BeforeClass
  public static void setup() {

    states.add(zeroState);
    states.add(firstState);
    states.add(secondState);
    states.add(thirdState);
    states.add(fourthState);
    states.add(cancelState);
    states.add(completedState);

    states.add(phantomState);


    events.add(firstEvent);
    events.add(secondEvent);
    events.add(cancelEvent);
    events.add(buyEvent);

    events.add(phantomEvent);

    zeroState.addTransition(firstEvent, firstState);
    zeroState.addTransition(secondEvent, secondState);
    firstState.addTransition(firstEvent, secondState);
    firstState.addTransition(secondEvent, thirdState);
    secondState.addTransition(firstEvent, thirdState);
    secondState.addTransition(secondEvent, fourthState);

    //secondState.addTransition(firstEvent, phantomState);

    thirdState.addTransition(firstEvent, fourthState);
    fourthState.addTransition(buyEvent, completedState);

   // phantomState.addTransition(phantomEvent, cancelState);

  //  fourthState.addTransition(cancelEvent, cancelState);

  }

  @Test
  public void FirstEventTest() throws StateException {
    State newState = zeroState.transition(firstEvent);
    Assert.assertEquals(newState, firstState);
  }

  @Test
  public void SecondEventTest() throws StateException {
    State newState = firstState.transition(secondEvent);
    Assert.assertEquals(newState, thirdState);
  }

  @Test
  public void FirstEventTest2() throws StateException {
    State newState = thirdState.transition(firstEvent);
    Assert.assertEquals(newState, fourthState);
  }

  @Test
  public void buyEventTest() throws StateException {
    State newState = fourthState.transition(buyEvent);
    Assert.assertEquals(newState, completedState);
  }


  @Test(expected = StateException.class)
  public void invalidTransitionTest() throws StateException {
    State newState = thirdState.transition(secondEvent);
  }

  @Test()
  public void reachablityTerminal() {
    List<State> unreachableStates = StateUtil.analyzeTerminalStates(states);
    Assert.assertEquals(unreachableStates.size(), 1);
    Assert.assertEquals(unreachableStates.get(0), cancelState);

  }

  @Test()
  public void reachablityIntermediate() {
    List<State> unreachableStates = StateUtil.analyzeIntermediateStates(states);
    Assert.assertEquals(unreachableStates.size(), 1);
    Assert.assertEquals(unreachableStates.get(0), phantomState);
  }


  @AfterClass
  public static void tearDown() {

  }
}
