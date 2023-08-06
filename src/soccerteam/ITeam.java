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


  /**
   * Returns whether this team has valid number of Players. A team is valid when
   * there are no less than 10 players and no more than 20 players.
   * @return boolean
   */
  boolean isValidTeam();


  /**
   * Adds a Player to this team.
   * @param    player    an IPlayer object
   * @throws  IllegalArgumentException  when the player is not under 10 years old
   * @throws  IllegalStateException   when the team is full
   */
  void addPlayer(IPlayer player) throws IllegalStateException, IllegalArgumentException;


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
   * should not be substituted by Goalie unless players are not enough.
   * @throws IllegalStateException when the team is not valid yet
   */
  void selectStartingLineup() throws IllegalStateException;


  /**
   * Gets all the players on this team.
   * @return a string
   */
  String getAllPlayers();


  /**
   * Gets the starting lineup of this team.
   * @return a string
   */
  String getStartingLineup();
}
