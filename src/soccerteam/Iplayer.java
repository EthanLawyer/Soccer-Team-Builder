package soccerteam;

import java.time.LocalDate;

/**
 * A player in a ball game team.
 */
public interface Iplayer {
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
   * Gets the preferred position of this player
   * (goalie, defender, midfielder, forward).
   * @return the {@link Player} of this player's preferred position
   */
  Player getPreferredPosition();


  /**
   * Gets the skill level of this player, which is a number between
   * 1 and 5 based on the coach's assessment (1 = lowest skill level,
   * 5 = highest skill level).
   * @return the skill level of this player
   */
  int getSkillLevel();


  /**
   * Gets the jersey number of this player, which is a unique number
   * between 1 and 20, and cannot be changed once created. The jersey
   * number is assigned once a team is created.
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
   * @param   skillLevel  the skill level of this player
   */
  void setSkillLevel(int skillLevel);
}
