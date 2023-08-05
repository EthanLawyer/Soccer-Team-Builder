package soccerteam;

/**
 * A soccer team. A team have a minimum of 10 Players and a maximum of 20.
 * If there are less than 10 players, the team cannot be created unless more
 * players are added. If the team has more than 20 players, the ones with
 * the lowest skill level will be ignored so that we only have 20 players.
 */
public interface SoccerTeam {
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
   * Generates a random jersey number between 1 and 20.
   * @return int
   */
  int generateJerseyNumber();


  /**
   * Adds a Player to this team.
   * @param    player    an Iplayer object
   * @throws  IllegalStateException   when the team is full
   */
  void addPlayer(Iplayer player) throws IllegalStateException;


  /**
   * Removes a player from this team.
   * @param   jerseyNumber  the jersey number of the player to be removed
   * @throws IllegalArgumentException when the inputted jersey number is
   *                                  not on this team
   */
  void removePlayer(int jerseyNumber) throws IllegalArgumentException;


  /**
   * Selects the starting lineup of 7 players. The most skilled 7 players
   * will be selected. If possible, these players will be assigned their
   * preferred positions, but not necessary. However, the Goalie always
   * remains the Goalie and can only be played by Goalie.
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
