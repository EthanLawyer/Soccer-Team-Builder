package soccerteam;

import java.time.LocalDate;

/**
 * A player in a soccer team.
 */
public class Player implements Iplayer{
  protected String fistName;
  protected String lastName;
  protected LocalDate dateOfBirth;
  Position preferredPosition;
  int skillLevel;
  protected int jerseyNumber;
  Position actualPosition;

  /**
   * Constructor.
   */
  public Player(String fistName, String lastName, LocalDate dateOfBirth,
            Position preferredPosition, int skillLevel, int jerseyNumber)
  {
    this.fistName = fistName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.preferredPosition = preferredPosition;
    this.skillLevel = skillLevel;
    this.jerseyNumber = jerseyNumber;
    this.actualPosition = Position.BENCH;
  }
}
