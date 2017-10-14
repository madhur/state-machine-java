import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by madhur on 14 Oct.
 */
public class StateUtil {

  /**
   * Marks terminal states.
   *
   * @param statesList List of states
   * @param textLines  Input string
   * @throws StateException stateexception
   */
  public static void markTerimalStates(ArrayList<State> statesList, ArrayList<String> textLines)
          throws StateException {
    for (String terminalState : textLines) {

      State targetState = containsState(statesList, terminalState);
      if (targetState == null) {
        throw new StateException("The state was not found");
      }

      if (targetState.getStateType() == StateType.INITIAL) {
        throw new StateException("State cannot be both initial and terminal");
      }

      targetState.setStateType(StateType.TERMINAL);

    }
  }

  /**
   * Mark initial state.
   *
   * @param statesList   list of states
   * @param initialState initial state string
   * @throws StateException stateexception
   */
  public static void markInitialState(ArrayList<State> statesList, String initialState) throws
          StateException {
    State targetState = containsState(statesList, initialState);

    if (targetState == null) {
      throw new StateException("The state was not found");
    }

    targetState.setStateType(StateType.INITIAL);
  }

  /**
   * Get states list from list of strings.
   *
   * @param stringStates list of strings
   * @return ArrayList of states
   */
  public static ArrayList<State> getStates(ArrayList<String> stringStates) {
    ArrayList<State> states = new ArrayList<State>();
    for (String state : stringStates) {
      states.add(new State(state));
    }

    return states;

  }

  /**
   * get list of events from list of strings.
   *
   * @param stringEvents List of string events
   * @return ArrayList of events.
   */
  public static ArrayList<Event> getEvents(ArrayList<String> stringEvents) {
    ArrayList<Event> eventList = new ArrayList<Event>();
    for (String event : stringEvents) {
      eventList.add(new Event(event));
    }

    return eventList;

  }

  /**
   * Checks if the list of states contains the state string.
   *
   * @param list  list of states
   * @param state single state string
   * @return State object
   * @throws StateException state exception
   */
  public static State containsState(List<State> list, String state) throws StateException {
    for (State object : list) {
      if (object.getState().equalsIgnoreCase(state)) {
        return object;
      }
    }

    throw new StateException("State not found: " + state);
  }

  /**
   * Checks if the list of events contains a particular event.
   *
   * @param list  list of events
   * @param event string event
   * @return Event object
   * @throws StateException stateexception
   */
  public static Event containsEvent(List<Event> list, String event) throws StateException {
    for (Event object : list) {
      if (object.getEvent().equalsIgnoreCase(event)) {
        return object;
      }
    }

    throw new StateException("Event not found: " + event);
  }


  /**
   * Reads multiple lines of text from console.
   *
   * @return ArrayList of strings
   */
  public static ArrayList<String> readText() {
    ArrayList<String> lines = new ArrayList<String>();
    Scanner sc = new Scanner(System.in);
    String line;
    while (!(line = sc.nextLine()).equals("")) {
      lines.add(line);
    }

    return lines;
  }

  /**
   * Read single line of text from console.
   *
   * @return String object
   */
  public static String readSingleText() {
    Scanner sc = new Scanner(System.in);
    String text = sc.nextLine();
    return text;
  }

  /**
   * ReadTranstions from the consoles.
   *
   * @param statesList list of states
   * @param eventList  list of events
   * @throws StateException state exception.
   */
  public static void readTransitions(ArrayList<State> statesList, ArrayList<Event> eventList)
          throws StateException {

    Scanner sc = new Scanner(System.in);
    String line;
    while (!(line = sc.nextLine()).equals("")) {
      try {
        String[] parts = line.split(",");
        if (parts.length < 3) {
          throw new StateException("Invalid transition String");
        }

        State initialState = containsState(statesList, parts[0]);
        State finalState = containsState(statesList, parts[2]);
        Event event = containsEvent(eventList, parts[1]);

        initialState.addTransition(event, finalState);
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }

    }

  }

  /**
   * Handles the transition loop from the console.
   *
   * @param statesList list of states
   * @param events     list of events
   * @throws StateException state exception
   */
  public static void handleTransitions(ArrayList<State> statesList, ArrayList<Event> events)
          throws StateException {

    State currentState = getInitialState(statesList);
    System.out.println("The initial state is: ");
    System.out.println("< " + currentState.getState());
    String input;
    Scanner sc = new Scanner(System.in);

    while (true) {
      try {
        System.out.print("> ");
        input = sc.nextLine();
        Event event = containsEvent(events, input);
        if (event == null) {
          throw new StateException("Event not found: " + input);
        }

        currentState = currentState.transition(event);
        if (currentState.getStateType() == StateType.TERMINAL) {
          System.out.println("<! " + currentState.getState());
          break;
        }
        System.out.println("< " + currentState.getState());
      } catch (Exception ex) {
        System.out.println(ex.getMessage());
      }

    }

  }

  private static State getInitialState(ArrayList<State> statesList) throws StateException {
    for (State state : statesList) {
      if (state.getStateType() == StateType.INITIAL) {
        return state;
      }
    }

    throw new StateException("No initial state found");
  }

  /**
   * Analyze method for reachablity.
   *
   * @param statesList list of states
   */
  public static List<State> analyzeTerminalStates(ArrayList<State> statesList) {

    ArrayList<State> terminalStates = new ArrayList<State>();

    for (State state : statesList) {

      if (state.getStateType() == StateType.TERMINAL) {
        terminalStates.add(state);
      }
    }

    ArrayList<Transition> transitions = new ArrayList<Transition>();

    for (State state : statesList) {
      for (Event event : state.getTransitions().keySet()) {
        transitions.add(new Transition(state, state.getTransitions().get(event), event));
      }
    }

    return checkReachableTerminal(transitions, terminalStates);

  }

  private static List<State> checkReachableTerminal(ArrayList<Transition> transitions,
                                                    ArrayList<State> terminalStates) {

    List<State> unreachableTerminalStates = new ArrayList<State>();

    for (State terminalState : terminalStates) {
      boolean result = checkReachabilityTerminal(transitions, terminalState);
      if (!result) {
        System.out.println("Terminal state is not reachable: " + terminalState.getState());
        unreachableTerminalStates.add(terminalState);
      }

    }

    return unreachableTerminalStates;

  }

  private static boolean checkReachabilityTerminal(ArrayList<Transition> transitions,
                                                   State terminalState) {

    if (terminalState.getStateType() == StateType.INITIAL) {
      return true;
    }

    for (Transition transition : transitions) {
      if (transition.getFinalState() == terminalState &&
              terminalState.getStateType() != StateType.INITIAL) {
        return checkReachabilityTerminal(transitions, transition.getInitialState());
      }
    }

    return false;
  }


  /**
   * Analyze method for reachablity.
   *
   * @param statesList list of states
   */
  public static List<State> analyzeIntermediateStates(ArrayList<State> statesList) {

    ArrayList<State> intermediateStates = new ArrayList<State>();

    for (State state : statesList) {

      if (state.getStateType() == StateType.INTERMEDIATE) {
        intermediateStates.add(state);
      }
    }

    ArrayList<Transition> transitions = new ArrayList<Transition>();

    for (State state : statesList) {
      for (Event event : state.getTransitions().keySet()) {
        transitions.add(new Transition(state, state.getTransitions().get(event), event));
      }
    }

    return checkReachableIntermediate(transitions, intermediateStates);

  }

  private static List<State> checkReachableIntermediate(ArrayList<Transition> transitions,
                                                        ArrayList<State> intermediateStates) {

    List<State> unreachableintermediateStates = new ArrayList<State>();

    for (State intermediateState : intermediateStates) {
      boolean result = checkReachabilityIntermediate(transitions, intermediateState);
      if (!result) {
        System.out.println("Intermediate state is not reachable to terminal state: "
                + intermediateState.getState());
        unreachableintermediateStates.add(intermediateState);
      }

    }

    return unreachableintermediateStates;

  }




  private static boolean checkReachabilityIntermediate(ArrayList<Transition> transitions,
                                                       State intermediateState) {

    if (intermediateState.getStateType() == StateType.TERMINAL) {
      return true;
    }

    for (Transition transition : transitions) {
      if (transition.getFinalState() == intermediateState &&
              intermediateState.getStateType() != StateType.TERMINAL) {
        return checkReachabilityTerminal(transitions, transition.getFinalState());
      }
    }

    return false;

  }
}
