package soccerteam;

public interface IView {
  void setController(IController controller);

  void showBuildTeamForm();

  void showAddPlayerForm();

  void showRemovePlayerForm();

  void showTeam(String allPlayers, String startingLineup);

  void showActionResult(String result);

  // Method to update the lower part of the left half of the GUI with action results.
  void updateActionResult(String message);

  // Method to update the upper part of the left half of the GUI with team members and starting lineup.
  void updateTeamMembers(String allPlayers, String startingLineup);
}
