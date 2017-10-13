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


  public State(String state) {

    this.state = state;
    this.isTerminal = false;
    this.isInitial = false;
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

  public State transition(Event event) throws StateException {
    if(transitions.containsKey(event)) {
      return transitions.get(event);
    }

    throw new StateException("No transition found for State: " + this.getState() + " and event: " + event.getEvent());

  }

  public void addTransition(Event event, State state) {
    transitions.put(event, state);
  }
}
