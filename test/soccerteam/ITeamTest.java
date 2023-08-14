package soccerteam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for ITeam.
 */
public class ITeamTest {
  ITeam team;

  /**
   * Setup 22 players.
   */
  @Before
  public void setUp() {
    team = new Team("Vancouver U10");
  }

  /**
   * Test the constructor under correct settings.
   */
  @Test
  public void testConstructor(){
    assertEquals("Vancouver U10", team.getName());
    assertEquals(0, team.getSize());
    assertFalse(team.isValidTeam());
  }

  /**
   * Test isValidTeam method.
   */
  @Test
  public void testInvalidTeam(){
    assertFalse(team.isValidTeam());
    // Add 9 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);

    assertFalse(team.isValidTeam());
    // Add another player.
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);
    assertTrue(team.isValidTeam());
  }

  /**
   * Test addPlayer method.
   */
  @Test
  public void testAddPlayer(){
    // Add 15 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);
    team.addPlayer("Zack", "Daniels", "2017-10-19", "defender", 4);
    team.addPlayer("William", "Lee", "2016-07-01", "goalie", 3);
    team.addPlayer("Michael", "Jordan", "2019-05-23", "defender", 4);
    team.addPlayer("Ming", "Yao", "2018-08-05", "midfielder", 4);
    team.addPlayer("Jamie", "Oliver", "2018-06-15", "defender", 1);

    assertEquals(15, team.getSize());
    String allPlayersText = team.getAllPlayersText();
    String[] addedPlayers = {"John Doe", "David Finch", "Francis Underwood", "Doug Stamper", "Remy Danton", "Walter White"
        , "James McGill", "Jesse Pinkman", "Gustavo Fring", "Joe Johnson", "Zack Daniels", "William Lee", "Michael Jordan"
        , "Ming Yao", "Jamie Oliver"};
    int count = 0;
    for (String playerName : addedPlayers){
      if ( allPlayersText.contains(playerName) ){
        count++;
      }
    }
    assertEquals(15, count);
  }

  /**
   * Test adding player to a full team, but the new player is more skilled than the least skilled
   * player on team.
   */
  @Test
  public void testAddingHighSkillPlayerToFullTeam(){
    // Add 20 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);
    team.addPlayer("Zack", "Daniels", "2017-10-19", "defender", 4);
    team.addPlayer("William", "Lee", "2016-07-01", "goalie", 3);
    team.addPlayer("Michael", "Jordan", "2019-05-23", "defender", 4);
    team.addPlayer("Ming", "Yao", "2018-08-05", "midfielder", 4);
    team.addPlayer("Jamie", "Oliver", "2018-06-15", "defender", 3);
    team.addPlayer("James", "Harden", "2015-01-22", "forward", 5);
    team.addPlayer("Derrick", "Rose", "2016-03-30", "midfielder", 4);
    team.addPlayer("Michael", "Ross", "2018-08-05", "forward", 3);
    team.addPlayer("Louis", "Litt", "2018-12-11", "defender", 4);
    team.addPlayer("William", "Gates", "2017-10-19", "defender", 4);

    // Add a new player to the team, and Jamie Oliver as the least skilled player will be removed.
    team.addPlayer("Elon", "Musk", "2016-07-01", "goalie", 3);

    assertEquals(20, team.getSize());
    int hasRemyDanton = 0;
    String[] allPlayers = team.getAllPlayersText().split("\\n");
    for ( String player : allPlayers ) {
      if ( player.contains("Remy Danton") ) {
        hasRemyDanton++;
      }
    }
    assertEquals(0, hasRemyDanton);
  }


  /**
   * Test adding player to a full team, but the new player is lower skilled than the least skilled
   * player on team.
   */
  @Test
  public void testAddingLowSkillPlayerToFullTeam(){
    // Add 20 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);
    team.addPlayer("Zack", "Daniels", "2017-10-19", "defender", 4);
    team.addPlayer("William", "Lee", "2016-07-01", "goalie", 3);
    team.addPlayer("Michael", "Jordan", "2019-05-23", "defender", 4);
    team.addPlayer("Ming", "Yao", "2018-08-05", "midfielder", 4);
    team.addPlayer("Jamie", "Oliver", "2018-06-15", "defender", 2);
    team.addPlayer("James", "Harden", "2015-01-22", "forward", 5);
    team.addPlayer("Derrick", "Rose", "2016-03-30", "midfielder", 4);
    team.addPlayer("Michael", "Ross", "2018-08-05", "forward", 3);
    team.addPlayer("Louis", "Litt", "2018-12-11", "defender", 4);
    team.addPlayer("William", "Gates", "2017-10-19", "defender", 4);

    String originalTeam = team.getAllPlayersText();

    // Add a new player to the team, but he is lower skilled than the least skilled player on team,
    // the team should remain unchanged.
    team.addPlayer("Elon", "Musk", "2016-07-01", "goalie", 1);

    String currentTeam = team.getAllPlayersText();
    assertEquals(originalTeam, currentTeam);
  }


  /**
   * Test adding a player using invalid date of birth format.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingPlayerInvalidDateFormat() {
    // Add 20 players.
    team.addPlayer("John", "Doe", "2017-Aug-05", "goalie", 4);
  }


  /**
    * Test adding a player over 10 years old to a full team.
    */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingOverAgePlayer() {
    // Add 20 players.
    team.addPlayer("John", "Doe", "2010-08-05", "goalie", 4);
  }


  /**
   * Test adding a player using invalid preferred position.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingPlayerInvalidPosition() {
    // Add 20 players.
    team.addPlayer("John", "Doe", "2017-05-05", "coach", 4);
  }


  /**
   * Test adding a player using invalid skill level.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingPlayerInvalidSkillLevel1() {
    // Add 20 players.
    team.addPlayer("John", "Doe", "2017-05-05", "goalie", 6);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testAddingPlayerInvalidSkillLevel2() {
    // Add 20 players.
    team.addPlayer("John", "Doe", "2017-05-05", "goalie", 0);
  }


  /**
   * Test removePlayer method.
   */
  @Test
  public void testRemovePlayer(){
    // Add 20 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);
    team.addPlayer("Zack", "Daniels", "2017-10-19", "defender", 4);
    team.addPlayer("William", "Lee", "2016-07-01", "goalie", 3);
    team.addPlayer("Michael", "Jordan", "2019-05-23", "defender", 4);
    team.addPlayer("Ming", "Yao", "2018-08-05", "midfielder", 4);
    team.addPlayer("Jamie", "Oliver", "2018-06-15", "defender", 1);
    team.addPlayer("James", "Harden", "2015-01-22", "forward", 5);
    team.addPlayer("Derrick", "Rose", "2016-03-30", "midfielder", 4);
    team.addPlayer("Michael", "Ross", "2018-08-05", "forward", 3);
    team.addPlayer("Louis", "Litt", "2018-12-11", "defender", 4);
    team.addPlayer("William", "Gates", "2017-10-19", "defender", 4);

    team.selectStartingLineup();

    // Remove 10 players.
    team.removePlayer(1);
    team.removePlayer(2);
    team.removePlayer(3);
    team.removePlayer(4);
    team.removePlayer(5);
    team.removePlayer(6);
    team.removePlayer(7);
    team.removePlayer(8);
    team.removePlayer(9);
    team.removePlayer(10);

    assertEquals(10, team.getSize());
  }

  /**
   * Test removing from a team of 10 players.
   */
  @Test(expected = IllegalStateException.class)
  public void testRemoveFromATeamOf10Players() {
    // Add 20 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);
    team.addPlayer("Zack", "Daniels", "2017-10-19", "defender", 4);
    team.addPlayer("William", "Lee", "2016-07-01", "goalie", 3);
    team.addPlayer("Michael", "Jordan", "2019-05-23", "defender", 4);
    team.addPlayer("Ming", "Yao", "2018-08-05", "midfielder", 4);
    team.addPlayer("Jamie", "Oliver", "2018-06-15", "defender", 1);
    team.addPlayer("James", "Harden", "2015-01-22", "forward", 5);
    team.addPlayer("Derrick", "Rose", "2016-03-30", "midfielder", 4);
    team.addPlayer("Michael", "Ross", "2018-08-05", "forward", 3);
    team.addPlayer("Louis", "Litt", "2018-12-11", "defender", 4);
    team.addPlayer("William", "Gates", "2017-10-19", "defender", 4);

    // Remove 11 players.
    team.removePlayer(1);
    team.removePlayer(2);
    team.removePlayer(3);
    team.removePlayer(4);
    team.removePlayer(5);
    team.removePlayer(6);
    team.removePlayer(7);
    team.removePlayer(8);
    team.removePlayer(9);
    team.removePlayer(10);
    team.removePlayer(11);
  }


  /**
   * Test removing a not existing player.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNotExistingPlayer() {
    // Add 11 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);
    team.addPlayer("Zack", "Daniels", "2017-10-19", "defender", 4);

    // Remove non-existing players.
    team.removePlayer(22);
  }


  /**
   * Test selectStartingLineup method.
   */
  @Test
  public void testSelectStartingLineup(){
    // Add 17 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 5);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);
    team.addPlayer("Zack", "Daniels", "2017-10-19", "defender", 4);
    team.addPlayer("William", "Lee", "2016-07-01", "goalie", 3);
    team.addPlayer("Michael", "Jordan", "2019-05-23", "defender", 4);
    team.addPlayer("Ming", "Yao", "2018-08-05", "midfielder", 4);
    team.addPlayer("Jamie", "Oliver", "2018-06-15", "defender", 1);
    team.addPlayer("James", "Harden", "2015-01-22", "forward", 5);
    team.addPlayer("Derrick", "Rose", "2016-03-30", "midfielder", 4);

    team.selectStartingLineup();
    team.selectStartingLineup();

    int hasExpectedStartingLineup = 0;
    String[] allStartingLineup = team.getStartingLineupText().split("\\n");
    for ( String player : allStartingLineup ) {
      if ( player.contains("John Doe") || player.contains("Francis Underwood") || player.contains("Jesse Pinkman")
          || player.contains("James McGill") || player.contains("Derrick Rose") || player.contains("Walter White")
          || player.contains("James Harden") )
      {
        hasExpectedStartingLineup++;
      }
    }
    assertEquals(7, hasExpectedStartingLineup);
  }


  /**
   * Test selectStartingLineup for an invalid team (9 players).
   */
  @Test(expected = IllegalStateException.class)
  public void testSelectStartingLineupInvalidTeam() {
    // Add 9 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 5);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);

    team.selectStartingLineup();
  }


  /**
   * Test selectStartingLineup method on an edge case: no goalie.
   */
  @Test
  public void testSelectStartingLineupNoGoalie(){
    // Add 10 players with no goalies.
    team.addPlayer("John", "Doe", "2018-08-05", "forward", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "defender", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);

    team.selectStartingLineup();

    int hasExpectedStartingLineup = 0;
    String[] allStartingLineup = team.getStartingLineupText().split("\\n");
    for ( String player : allStartingLineup ) {
      if ( player.contains("Joe Johnson") || player.contains("Doug Stamper") || player.contains("Jesse Pinkman")
          || player.contains("James McGill") || player.contains("Francis Underwood") || player.contains("Walter White")
          || player.contains("John Doe") )
      {
        hasExpectedStartingLineup++;
      }
    }
    assertEquals(7, hasExpectedStartingLineup);
  }


  /**
   * Test selectStartingLineup method on an edge case: too many goalies.
   */
  @Test
  public void testSelectStartingLineupTooManyGoalies(){
    // Add 10 players, in which 7 are goalies.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "goalie", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "goalie", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "goalie", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "goalie", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "goalie", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "midfielder", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "midfielder", 4);

    team.selectStartingLineup();

    int hasExpectedStartingLineup = 0;
    String[] allStartingLineup = team.getStartingLineupText().split("\\n");
    for ( String player : allStartingLineup ) {
      if ( player.contains("John Doe") || player.contains("Gustavo Fring") || player.contains("Jesse Pinkman")
          || player.contains("James McGill") || player.contains("Joe Johnson") || player.contains("Walter White")
          || player.contains("Doug Stamper") )
      {
        hasExpectedStartingLineup++;
      }
    }
    assertEquals(7, hasExpectedStartingLineup);
  }


  /**
   * Test getAllPlayersText method.
   */
  @Test
  public void testGetAllPlayersText(){
    // Add 10 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);

    String allPlayers = team.getAllPlayersText();
    String[] lines = allPlayers.split("\r?\n");
    int lineCount = lines.length;
    assertEquals(10, lineCount);
  }

  /**
   * Test the getStartingLineupText method.
   */
  @Test
  public void testGetStartingLineupText(){
    // Add 10 players.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);

    // Test before the starting lineup is selected.
    String lineup = "The starting lineup has not been selected.";
    assertEquals(lineup, team.getStartingLineupText());

    // Test after the starting lineup is selected.
    team.selectStartingLineup();
    lineup = team.getStartingLineupText();
    String[] lines = lineup.split("\r?\n");
    int lineCount = lines.length;
    assertEquals(7, lineCount);
  }
}


