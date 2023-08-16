package soccerteam;

/**
 * The controller of soccer team, an implementation of IController interface.
 */
public class SoccerTeamController implements IController{
  private ITeam team;
  private final IView view;

  /**
   * Constructor.
   * @param   view  the IView object that to interact with.
   */
  public SoccerTeamController(IView view) {
    this.view = view;
  }

  @Override
  public void buildTeam(String teamName) {
    this.team = new Team(teamName);
    updateTeamView(); // when the team is just created, top-left area of view shows nothing.
  }

  @Override
  public void addPlayer(String firstName, String lastName, String dateOfBirth, String position,
      int skillLevel) {
    try {
      team.addPlayer(firstName, lastName, dateOfBirth, position, skillLevel);
      view.updateActionResult("Added a player: " + firstName + " " + lastName);
      updateTeamView();
    } catch (NullPointerException e){
      view.updateActionResult("Error: Team is not created yet.");
    } catch (Exception e) {
      view.updateActionResult("Error: " + e.getMessage());
    }
  }

  @Override
  public void removePlayer(int jerseyNumber) {
    try {
      team.removePlayer(jerseyNumber);
      view.updateActionResult("Removed player with jersey number: " + jerseyNumber);
      updateTeamView();
    } catch (NullPointerException e){
      view.updateActionResult("Error: Team is not created yet.");
    } catch (Exception e) {
      view.updateActionResult("Error: " + e.getMessage());
    }
  }

  @Override
  public void selectStartingLineup() {
    try {
      team.selectStartingLineup();
      view.updateActionResult("Starting lineup selected.");
      updateTeamView();
    } catch (NullPointerException e){
      view.updateActionResult("Error: Team is not created yet.");
    } catch (Exception e) {
      view.updateActionResult("Error: " + e.getMessage());
    }
  }

  @Override
  public void updateTeamView() {
    if (team != null) {
      String teamName = "Team Name: " + team.getName() + "\n\n";
      String allPlayers = "Team Players:\n" + team.getAllPlayersText() + "\n";
      String startingLineup = "Starting Lineups:\n" +team.getStartingLineupText();
      String warning = "";
      if ( team.getSize() < 10 ) {
        warning = "\n\nWarning:\nLess than 10 players on team, please add more!";
      }
      view.updateTeamMembers(teamName, allPlayers, startingLineup, warning);
    } else {
      // when the team is just created, top-left area of view shows nothing.
      view.updateTeamMembers("", "", "", "");
    }
  }
}
