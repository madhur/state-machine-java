import java.util.HashMap;
import java.util.Map;

/**
 * Created by madhur on 13 Oct.
 */
public class State {

  private String state;

  public StateType getStateType() {
    return stateType;
  }

  public void setStateType(StateType stateType) {
    this.stateType = stateType;
  }

  private StateType stateType;

  public Map<Event, State> getTransitions() {
    return transitions;
  }

  private Map<Event, State> transitions = new HashMap<Event, State>();


  /**
   * Constructor for the class.
   *
   * @param state return State object
   */
  public State(String state) {

    this.state = state;
    this.stateType = StateType.INTERMEDIATE;
  }

  /**
   * Constructor for the unit test.
   * @param state state
   *
   */
  public State(String state, StateType stateType) {
    this.state = state;
    this.stateType = stateType;
  }

  public String getState() {
    return state;
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
