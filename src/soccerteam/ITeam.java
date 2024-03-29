package soccerteam;

/**
 * A soccer team.
 */
public interface ITeam {
  /**
   * Gets the name of this team.
   * @return a string
   */
  String getName();


  /**
   * Gets the size of this team.
   * @return int
   */
  int getSize();


  /** Checks if the team is validly created.
   * @return boolean
   */
  boolean isValidTeam();


  /**
   * Adds a Player to this team.
   * @param     firstName    the first name of the player
   * @param     lastName     the last name of the player
   * @param     dateOfBirth  the date of birth of the player, in the format of YYYY-MM-DD
   * @param     preferredPosition   the preferred position of the player, case-insensitive
   * @param     skillLevel   the player's skill level, in int format within 1 and 5 (max 5)
   * @throws  IllegalArgumentException  1. when the player is not under 10 years old, or
   *                                    2. when the date format is not correct, or
   *                                    3. when the preferred position is not valid, or
   *                                    4. when the skill level is out of range
   *                                    5. when there is already a player with the same name
   */
  void addPlayer(String firstName, String lastName, String dateOfBirth, String preferredPosition,
      int skillLevel) throws IllegalArgumentException;


  /**
   * Removes a player from this team.
   * @param   jerseyNumber  the jersey number of the player to be removed
   * @throws IllegalStateException    when the team has only 10 players
   * @throws IllegalArgumentException when the inputted jersey number is
   *                                  not on this team
   */
  void removePlayer(int jerseyNumber) throws IllegalStateException, IllegalArgumentException;


  /**
   * Selects the starting lineup of 7 players. The most skilled 7 players
   * will be selected. If possible, these players will be assigned their
   * preferred positions, but not necessary. However, the goalie should be
   * played by Goalie as long as there is a Goalie on team, and other positions
   * should not be substituted by Goalie unless there are not enough players
   * in such positions.
   * @throws IllegalStateException when the team is not valid yet
   */
  void selectStartingLineup() throws IllegalStateException;


  /**
   * Gets all the players on this team. For every player, first name, last name, and jersey number
   * will be returned. The list will be sorted in alphabetical order (last name).
   * @return a string
   */
  String getAllPlayersText();


  /**
   * Gets the starting lineup of this team. For every player, first name, last name, jersey number,
   * and position will be returned. The list will be sorted by position (goalie, defender, midfielder,
   * forward). Players with the same position will be ordered alphabetically (last name).
   * @return a string
   */
  String getStartingLineupText();
}
