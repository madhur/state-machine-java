import java.util.ArrayList;


/**
 * Created by madhur on 13 Oct.
 */
public class Fsm {

  /**
   * Entry point of the program.
   * @param args Any command line arguments
   */
  public static void main(String[] args) {
    try {

      System.out.println("Enter the list of states (Newline to stop): ");
      ArrayList<String> textLines = StateUtil.readText();
      ArrayList<State> statesList = StateUtil.getStates(textLines);


      boolean enteredInitialState = false;
      boolean enteredFinalState = false;

      while (!enteredInitialState) {
        try {
          System.out.println("Enter the initial state: ");
          String initialState = StateUtil.readSingleText();

          StateUtil.markInitialState(statesList, initialState);
          enteredInitialState = true;
        } catch (Exception ex) {
          System.out.println(ex.getMessage());
        }
      }

      while (!enteredFinalState) {
        try {
          System.out.println("Enter the terminal states (Newline to stop): ");
          textLines = StateUtil.readText();
          StateUtil.markTerimalStates(statesList, textLines);
          enteredFinalState = true;
        } catch (Exception ex) {
          System.out.println(ex.getMessage());
        }
      }

      System.out.println("Enter the events (Newline to stop): ");
      textLines = StateUtil.readText();
      ArrayList<Event> events = StateUtil.getEvents(textLines);

      System.out.println("Enter the state trasnitions (For ex, \"0S,1R,1S\" (Newline to stop): ");
      StateUtil.readTransitions(statesList, events);

      StateUtil.analyze(statesList, events);

      StateUtil.handleTransitions(statesList, events);

    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      System.exit(1);
    }


  }


}
