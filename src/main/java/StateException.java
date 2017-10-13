/**
 * Created by madhur on 14 Oct.
 */
public class StateException extends  Exception {

  private String message;

  public StateException(String msg) {
    this.message = msg;
  }

  @Override
  public String toString() {
    return message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
