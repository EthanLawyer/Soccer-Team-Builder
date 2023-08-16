package soccerteam;

/**
 * The view interface of soccer team program.
 */

public interface IView {

  /**
   * Define the controller that the view will interact with.
   * @param   controller  the controller to interact with
   */
  void setController(IController controller);

  /**
   * Display the input panel for creating a team.
   */
  void showBuildTeamForm();

  /**
   * Display the input panel for adding a player.
   */
  void showAddPlayerForm();

  /**
   * Display the input panel to remove a player.
   */
  void showRemovePlayerForm();

  /**
   * Update the result of user's action, display the result on the bottom-left of the GUI.
   * @param   message   the result of user's action
   */
  void updateActionResult(String message);

  /**
   * Update the team information, including team name, the list of all current players, and the list
   * of current starting lineups. The information will be displayed on the top-left of the GUI.
   * @param   teamName        the team name
   * @param   allPlayers      the list of all current players
   * @param   startingLineup  the list of all current starting lineups
   */
  void updateTeamMembers(String teamName, String allPlayers, String startingLineup);
}
