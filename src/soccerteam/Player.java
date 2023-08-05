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
            Position preferredPosition, int skillLevel)
  {
    this.fistName = fistName;
    this.lastName = lastName;
    this.dateOfBirth = dateOfBirth;
    this.preferredPosition = preferredPosition;
    this.skillLevel = skillLevel;
    this.jerseyNumber = -1; // Jersey number will be -1 at default, and is only changeable when it is -1.
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


  /**
   * Sets the jersey number of a player, which is a unique number between 1 and 20, and cannot
   * be changed once created. The jersey number is assigned once a team is created.
   * @param  number  the assigned jersey number
   * @throws IllegalArgumentException when the jersey number is not valid
   * @throws IllegalStateException when this player already has a jersey number
   */
  public void setJerseyNumber(int number) throws IllegalArgumentException, IllegalStateException{
    if ( number < 1 || number > 20 ){
      throw new IllegalArgumentException("Invalid number. Jersey number should be within 1 and 20.");
    }
    if ( this.jerseyNumber != -1 ){
      throw new IllegalStateException("Error. This player already has a jersey number.");
    }
    this.jerseyNumber = number;
  }

  /**
   * Returns this player's first name, last name, jersey number, and position (only when the
   * actual position is assigned) in a string format.
   */
  @Override
  public String toString(){
    String result = fistName + " " + lastName + " (" + jerseyNumber + ")";
    if ( actualPosition != Position.BENCH ){
      result = result + ", " + actualPosition.toString();
    }
    return result;
  }
}
