package soccerteam;

/**
 * The driver class for soccer team project.
 */
public class Main {
  public static void main(String[] args) {
    IView view = new SoccerTeamView("Soccer Team Builder");
    IController controller = new SoccerTeamController(view);
    view.setController(controller);
  }
}
