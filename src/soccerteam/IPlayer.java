package soccerteam;

import java.time.LocalDate;

/**
 * A player in a ball game team.
 */
public interface IPlayer {
  /**
   * Gets the first name of this player.
   * @return a string of this player's first name.
   */
  String getFirstName();


  /**
   * Gets the last name of this player.
   * @return a string of this player's last name
   */
  String getLastName();


  /**
   * Gets the date of birth of this player.
   * @return a LocalDate object of this player's date of birth
   */
  LocalDate getDateOfBirth();


  /**
   * Gets the preferred position of this player (goalie, defender, midfielder, forward).
   *
   * @return the {@link Player} of this player's preferred position
   */
  Position getPreferredPosition();


  /**
   * Gets the skill level of this player, which is a number between
   * 1 and 5.
   * @return the skill level of this player
   */
  int getSkillLevel();


  /**
   * Gets the jersey number of this player.
   * @return the jersey number of this player
   */
  int getJerseyNumber();


  /**
   * Gets the actual position of this player. Players in the starting
   * lineup have their own positions, and the remaining players have a
   * bench position.
   * @return the {@link Position} of this player's actual position
   */
  Position getActualPosition();


  /**
   * Sets the skill level of this player, which is a number between
   * 1 and 5 based on the coach's assessment (1 = lowest skill level,
   * 5 = highest skill level).
   * @param   newSkillLevel  the skill level of this player
   * @throws  IllegalArgumentException  when the inputted skill level is
   *                                    out of range
   */
  void setSkillLevel(int newSkillLevel) throws IllegalArgumentException;


  /**
   * Sets the actual position of a player. Actual position will be BENCH by default,
   * and will only be set when this player is on the starting lineup.
   * @param   position   this player's assigned position on court
   * @throws  IllegalStateException   when this player is not on a team
   */
  void setActualPosition(Position position) throws IllegalStateException;


  /**
   * Sets the jersey number of a player, which is a unique number between 1 and 20, and cannot
   * be changed once created. The jersey number will be -1 by default, and is only assigned
   * when this player is added to a team.
   * @param  number  the assigned jersey number
   * @throws IllegalArgumentException when the jersey number is not valid
   * @throws IllegalStateException when this player already has a jersey number
   */
  void setJerseyNumber(int number) throws IllegalArgumentException, IllegalStateException;
}
