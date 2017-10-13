/**
 * Created by madhur on 14 Oct.
 */
public class StateNotFoundException extends Exception {

  @Override
  public String toString() {
    return "The entered state was not found";
  }

  @Override
  public String getMessage() {
    return "The entered state was not found";
  }
}
