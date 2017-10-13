import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by madhur on 14 Oct.
 */
public class FunctionalTests {

  static ArrayList<State> states = new ArrayList<State>();
  static ArrayList<Event> events = new ArrayList<Event>();

  static State zeroState = new State("0S", false, true);
  static State firstState = new State("1S", false, false);
  static State secondState = new State("2S", false, false);
  static State thirdState = new State("3S", false, false);
  static State fourthState = new State("4S", false, false);
  static State cancelState = new State("CANCELLED", true , false);
  static State completedState = new State("COMPLETED", true, false);

  static Event firstEvent = new Event("1R");
  static Event secondEvent = new Event("2R");
  static Event cancelEvent = new Event("CANCEL");
  static Event buyEvent = new Event("BUY");


  @BeforeClass
  public static void setup() {

    states.add(zeroState);
    states.add(firstState);
    states.add(secondState);
    states.add(thirdState);
    states.add(fourthState);
    states.add(cancelState);
    states.add(completedState);


    events.add(firstEvent);
    events.add(secondEvent);
    events.add(cancelEvent);
    events.add(buyEvent);

    zeroState.addTransition(firstEvent, firstState);
    zeroState.addTransition(secondEvent, secondState);
    firstState.addTransition(firstEvent, secondState);
    firstState.addTransition(secondEvent, thirdState);
    secondState.addTransition(firstEvent, thirdState);
    secondState.addTransition(secondEvent, fourthState);
    thirdState.addTransition(firstEvent, fourthState);
    fourthState.addTransition(buyEvent, completedState);

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

  @AfterClass
  public static void tearDown() {

  }
}
