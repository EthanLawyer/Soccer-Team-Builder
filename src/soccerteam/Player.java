package soccerteam;

import java.time.LocalDate;

/**
 * A player in a soccer team.
 */
public class Player implements IPlayer {
  private final String fistName;
  private final String lastName;
  private final LocalDate dateOfBirth;
  private Position preferredPosition;
  protected int skillLevel;
  private int jerseyNumber;
  protected Position actualPosition;

  /**
   * Constructor.
   * @throws  IllegalArgumentException  when the skill level is not in range
   */
  public Player(String fistName, String lastName, LocalDate dateOfBirth,
            Position preferredPosition, int skillLevel)
  {
    if ( skillLevel <= 0 || skillLevel > 5 ) {
      throw new IllegalArgumentException("Error. Skill level must be an int within 1 and 5.");
    }
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

  @Override
  public void setActualPosition(Position position) throws IllegalStateException{
   if ( jerseyNumber != -1 ) {
     actualPosition = position;
   } else {
     throw new IllegalStateException("Error. A player should be in team to have actual position.");
   }
  }

  @Override
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
    String result = jerseyNumber + ". " + fistName + " " + lastName;
    if ( actualPosition != Position.BENCH ){
      result = result + ", " + actualPosition.toString();
    }
    return result;
  }
}
