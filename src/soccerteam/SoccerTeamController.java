package soccerteam;

public class SoccerTeamController implements IController{

  private ITeam team;
  private IView view;

  public SoccerTeamController(IView view) {
    this.view = view;
  }

  @Override
  public void buildTeam(String teamName) {
    this.team = new Team(teamName);
    updateTeamView();
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
      String allPlayers = team.getAllPlayersText();
      String startingLineup = team.getStartingLineupText();
      view.updateTeamMembers(allPlayers, startingLineup);
    } else {
      view.updateTeamMembers("", "");
    }
  }

  // Optionally, you can have setters/getters or other utility methods here as needed.
}
