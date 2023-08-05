package soccerteam;

import java.time.LocalDate;

/**
 * A player in a soccer team.
 */
public class Player implements Iplayer{
  private final String fistName;
  private final String lastName;
  private final LocalDate dateOfBirth;
  private Position preferredPosition;
  protected int skillLevel;
  private int jerseyNumber;
  protected Position actualPosition;

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

  @Override
  public String getFirstName() {
    return fistName;
  }

  @Override
  public String getLastName() {
    return lastName;
  }

  @Override
  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  @Override
  public Position getPreferredPosition() {
    return preferredPosition;
  }

  @Override
  public int getSkillLevel() {
    return skillLevel;
  }

  @Override
  public int getJerseyNumber() {
    return jerseyNumber;
  }

  @Override
  public
  Position getActualPosition() {
    return actualPosition;
  }

  @Override
  public void setSkillLevel(int newSkillLevel) throws IllegalArgumentException{
    if ( newSkillLevel >= 1 && newSkillLevel <= 5 ) {
      skillLevel = newSkillLevel;
    } else {
      throw new IllegalArgumentException("Error. Skill level must be within 1 and 5.");
    }
  }
}
