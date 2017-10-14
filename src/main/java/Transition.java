/**
 * Created by madhur on 14 Oct.
 */
public class Transition {

  /**
   * constructor method.
   * @param initialState initial state.
   * @param finalState final state.
   * @param event event.
   */
  public Transition(State initialState, State finalState, Event event) {
    this.initialState = initialState;
    this.finalState = finalState;
    this.event = event;
  }

  State initialState;
  State finalState;

  public State getInitialState() {
    return initialState;
  }

  public State getFinalState() {
    return finalState;
  }

  public Event getEvent() {
    return event;
  }

  Event event;
}
