/**
 * Created by madhur on 13 Oct.
 */
public class Event {

  public Event(String event) {
    this.event = event;
  }

  public String getEvent() {
    return event;
  }

  private String event;

  @Override
  public String toString() {
    return event;
  }
}
