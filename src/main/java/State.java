import java.util.HashMap;
import java.util.Map;

/**
 * Created by madhur on 13 Oct.
 */
public class State {

  private String state;

  private boolean isTerminal;

  private boolean isInitial;

  private Map<Event, State> transitions = new HashMap<Event, State>();


  /**
   * Constructor for the class.
   *
   * @param state return State object
   */
  public State(String state) {

    this.state = state;
    this.isTerminal = false;
    this.isInitial = false;
  }

  /**
   * Constructor for the unit test.
   * @param state state
   * @param isTerminal if its a final state
   * @param isInitial if its a initial state
   */
  public State(String state, boolean isTerminal, boolean isInitial) {
    this.state = state;
    this.isTerminal = isTerminal;
    this.isInitial = isInitial;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public boolean isTerminal() {
    return isTerminal;
  }

  public void setTerminal(boolean terminal) {
    isTerminal = terminal;
  }

  public boolean isInitial() {
    return isInitial;
  }

  public void setInitial(boolean initial) {
    isInitial = initial;
  }

  @Override
  public String toString() {
    return state;
  }

  /**
   * Transition method. Given an event, will return the new state or throw exception if wrong event
   * @param event event
   * @return New State
   * @throws StateException state exception
   */
  public State transition(Event event) throws StateException {
    if (transitions.containsKey(event)) {
      return transitions.get(event);
    }

    throw new StateException("No transition found for State: " + this.getState()
            + " and event: " + event.getEvent());

  }

  /**
   * Adds the transition to a state.
   * @param event event
   * @param state state
   */
  public void addTransition(Event event, State state) {
    transitions.put(event, state);
  }
}
