package soccerteam;

/**
 * The controller interface of the soccer team program.
 */
public interface IController {

  /**
   * Create a Team.
   * @param   teamName  the name of the team
   */
  void buildTeam(String teamName);

  /**
   * Add a player to the team.
   * @param     firstName    the first name of the player
   * @param     lastName     the last name of the player
   * @param     dateOfBirth  the date of birth of the player, in the format of YYYY-MM-DD
   * @param     position     the preferred position of the player, case-insensitive
   * @param     skillLevel   the player's skill level, in int format within 1 and 5 (max 5)
   */
  void addPlayer(String firstName, String lastName, String dateOfBirth, String position,
      int skillLevel);

  /**
   * Remove a player from the team.
   * @param   jerseyNumber  the jersey number of the player to be removed.
   */
  void removePlayer(int jerseyNumber);

  /**
   * Select the starting lineup of the team.
   */
  void selectStartingLineup();

  /**
   * Update the team information display area of the view component, by passing the string of team
   * name, player list and starting lineup list to the updateTeamMembers() method of the view.
   */
  void updateTeamView();
}
