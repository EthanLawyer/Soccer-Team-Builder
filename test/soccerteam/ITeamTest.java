package soccerteam;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for ITeam.
 */
public class ITeamTest {
  private IPlayer player1;
  private IPlayer player2;
  private IPlayer player3;
  private IPlayer player4;
  private IPlayer player5;
  private IPlayer player6;
  private IPlayer player7;
  private IPlayer player8;
  private IPlayer player9;
  private IPlayer player10;
  private IPlayer player11;
  private IPlayer player12;
  private IPlayer player13;
  private IPlayer player14;
  private IPlayer player15;
  private IPlayer player16;
  private IPlayer player17;
  private IPlayer player18;
  private IPlayer player19;
  private IPlayer player20;
  private IPlayer player21;
  private IPlayer player22;

  /**
   * Setup 22 players.
   */
  @Before public void setUp() {
    player1 = new Player("John", "Doe", LocalDate.of(2018, 8, 5), Position.GOALIE, 4);
    player2 = new Player("David", "Finch", LocalDate.of(2017, 5, 5), Position.GOALIE, 3);
    player3 = new Player("Francis", "Underwood", LocalDate.of(2019, 5, 23), Position.DEFENDER, 4);
    player4 = new Player("Doug", "Stamper", LocalDate.of(2018, 8, 5), Position.MIDFIELDER, 4);
    player5 = new Player("Remy", "Danton", LocalDate.of(2018, 6, 15), Position.DEFENDER, 2);
    player6 = new Player("Walter", "White", LocalDate.of(2015, 1, 22), Position.FORWARD, 5);
    player7 = new Player("James", "McGill", LocalDate.of(2016, 3, 30), Position.MIDFIELDER, 4);
    player8 = new Player("Jesse", "Pinkman", LocalDate.of(2014, 11, 25), Position.DEFENDER, 4);
    player9 = new Player("Gustavo", "Fring", LocalDate.of(2018, 8, 5), Position.FORWARD, 3);
    player10 = new Player("Joe", "Johnson", LocalDate.of(2010, 12, 11), Position.DEFENDER, 4);
    player11 = new Player("Zack", "Daniels", LocalDate.of(2017, 10, 19), Position.DEFENDER, 4);
    player12 = new Player("William", "Lee", LocalDate.of(2016, 7, 1), Position.GOALIE, 3);
    player13 = new Player("Michael", "Jordan", LocalDate.of(2019, 5, 23), Position.DEFENDER, 4);
    player14 = new Player("Ming", "Yao", LocalDate.of(2018, 8, 5), Position.MIDFIELDER, 4);
    player15 = new Player("Jamie", "Oliver", LocalDate.of(2018, 6, 15), Position.DEFENDER, 1);
    player16 = new Player("James", "Harden", LocalDate.of(2015, 1, 22), Position.FORWARD, 5);
    player17 = new Player("Derrick", "Rose", LocalDate.of(2016, 3, 30), Position.MIDFIELDER, 4);
    player18 = new Player("Harvey", "Spector", LocalDate.of(2014, 11, 25), Position.DEFENDER, 4);
    player19 = new Player("Michael", "Ross", LocalDate.of(2018, 8, 5), Position.GOALIE, 3);
    player20 = new Player("Louis", "Litt", LocalDate.of(2018, 12, 11), Position.GOALIE, 4);
    player21 = new Player("William", "Gates", LocalDate.of(2017, 10, 19), Position.GOALIE, 4);
    player22 = new Player("Elon", "Musk", LocalDate.of(2016, 7, 1), Position.GOALIE, 3);
  }

  /**
   * Test the constructor under correct settings.
   */
  @Test
  public void testConstructor(){
    // Create a list of the first 22 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player10); // should be ignored
    players.add(player11);
    players.add(player12);
    players.add(player13);
    players.add(player14);
    players.add(player15); // should be ignored
    players.add(player16);
    players.add(player17);
    players.add(player18);
    players.add(player19);
    players.add(player20);
    players.add(player21);
    players.add(player22);

    // Create a team using the list of 22 players, the lowest one (Jamie Oliver)
    // and the over 10-year-old one (Joe Johnson) will be ignored.
    ITeam team = new Team("Vancouver U10", players);

    assertEquals("Vancouver U10", team.getName());
    assertEquals(20, team.getSize());
    assertEquals(new ArrayList<>(), team.getStartingLineup());

    int countIgnored = 0;
    for ( IPlayer player : team.getTeamPlayers().values() ) {
      if ( (Objects.equals(player.getFirstName(), player10.getFirstName()) &&
            Objects.equals(player.getLastName(), player10.getLastName()))
        ||  (Objects.equals(player.getFirstName(), player15.getFirstName()) &&
            Objects.equals(player.getLastName(), player15.getLastName())))
      {
        countIgnored++;
      }
    }
    assertEquals(0, countIgnored);
  }

  /**
   * Test the constructor when less than 10 players are inputted.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidConstructor(){
    // Create a list of 9 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);

    new Team("Vancouver U10", players);
  }

  /**
   * Test addPlayer method.
   */
  @Test
  public void testAddPlayer(){
    // Create a list of 10 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player11);

    ITeam team = new Team("Vancouver U10", players);

    team.addPlayer(player12);
    assertEquals(11, team.getSize());
    assertNotEquals(-1, player12.getJerseyNumber());
  }

  /**
   * Test adding player to a full team.
   */
  @Test(expected = IllegalStateException.class)
  public void testAddingToFullTeam(){
    // Create a team of 20 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player11);
    players.add(player12);
    players.add(player13);
    players.add(player14);
    players.add(player16);
    players.add(player17);
    players.add(player18);
    players.add(player19);
    players.add(player20);
    players.add(player21);
    players.add(player22);

    ITeam team = new Team("Vancouver U10", players);

    // add a new player to it
    IPlayer player23 = new Player("XX", "XXX", LocalDate.of(2010, 12, 11), Position.DEFENDER, 4);
    team.addPlayer(player23);
  }


  /**
    * Test adding a player over 10 years old to a full team.
    */
  @Test(expected = IllegalArgumentException.class)
  public void testAddingOverAgePlayer() {
    // Create a team of 10 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player11);

    ITeam team = new Team("Vancouver U10", players);

    // add an overage player
    team.addPlayer(player10);
  }

  /**
   * Test removePlayer method.
   */
  @Test
  public void testRemovePlayer(){
    // Create a list of 11 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player11);
    players.add(player12);

    ITeam team = new Team("Vancouver U10", players);
    team.selectStartingLineup();

    team.removePlayer(player1.getJerseyNumber());
    assertEquals(10, team.getSize());
  }

  /**
   * Test removing from a team of 10 players.
   */
  @Test(expected = IllegalStateException.class)
  public void testRemoveFromATeamOf10Players() {
    // Create a list of 10 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player11);

    ITeam team = new Team("Vancouver U10", players);

    // remove player11
    team.removePlayer(player11.getJerseyNumber());
  }


  /**
   * Test removing a not existing player.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveNotExistingPlayer() {
    // Create a list of 11 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player11);
    players.add(player12);

    ITeam team = new Team("Vancouver U10", players);

    // remove a not existing player
    team.removePlayer(0);
  }


  /**
   * Test selectStartingLineup method.
   */
  @Test
  public void testSelectStartingLineup(){
    // Create a list of the first 17 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player11);
    players.add(player12);
    players.add(player13);
    players.add(player14);
    players.add(player15);
    players.add(player16);
    players.add(player17);
    players.add(player18);

    ITeam team = new Team("Vancouver U10", players);
    team.selectStartingLineup();

    ArrayList<IPlayer> expectedStartingLineup = new ArrayList<>();
    expectedStartingLineup.add(player1);
    expectedStartingLineup.add(player3);
    expectedStartingLineup.add(player4);
    expectedStartingLineup.add(player6);
    expectedStartingLineup.add(player7);
    expectedStartingLineup.add(player8);
    expectedStartingLineup.add(player16);

    int errorCount = 0;
    for ( IPlayer player : expectedStartingLineup ) {
      if ( !team.getStartingLineup().contains(player) ){
        errorCount++;
      }
    }
    assertEquals(0, errorCount);
  }


  /**
   * Test selectStartingLineup method on an edge case: no goalie.
   */
  @Test
  public void testSelectStartingLineupNoGoalie(){
    // Create a list of no goalie.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player11);
    players.add(player13);
    players.add(player14);
    players.add(player15);
    players.add(player16);
    players.add(player17);
    players.add(player18);

    ITeam team = new Team("Vancouver U10", players);
    team.selectStartingLineup();

    ArrayList<IPlayer> expectedStartingLineup = new ArrayList<>();
    expectedStartingLineup.add(player3);
    expectedStartingLineup.add(player4);
    expectedStartingLineup.add(player6);
    expectedStartingLineup.add(player7);
    expectedStartingLineup.add(player8);
    expectedStartingLineup.add(player16);
    expectedStartingLineup.add(player11);

    int errorCount = 0;
    for ( IPlayer player : expectedStartingLineup ) {
      if ( !team.getStartingLineup().contains(player) ){
        errorCount++;
      }
    }
    assertEquals(0, errorCount);
  }


  /**
   * Test selectStartingLineup method on an edge case: too many goalies.
   */
  @Test
  public void testSelectStartingLineupTooManyGoalies(){
    // Create a list of 10 players, in which 6 are goalies.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1); // goalie
    players.add(player2); // goalie
    players.add(player3);
    players.add(player4);
    players.add(player12); // goalie
    players.add(player16);
    players.add(player19); // goalie
    players.add(player20); // goalie
    players.add(player21); // goalie
    players.add(player22); // goalie

    ITeam team = new Team("Vancouver U10", players);
    team.selectStartingLineup();

    ArrayList<IPlayer> expectedStartingLineup = new ArrayList<>();
    expectedStartingLineup.add(player1);
    expectedStartingLineup.add(player2);
    expectedStartingLineup.add(player3);
    expectedStartingLineup.add(player4);
    expectedStartingLineup.add(player16);
    expectedStartingLineup.add(player20);
    expectedStartingLineup.add(player21);


    int errorCount = 0;
    for ( IPlayer player : expectedStartingLineup ) {
      if ( !team.getStartingLineup().contains(player) ){
        errorCount++;
      }
    }
    assertEquals(0, errorCount);
  }


  /**
   * Test getAllPlayersText method.
   */
  @Test
  public void testGetAllPlayersText(){
    // Create a list of 10 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player11);

    ITeam team = new Team("Vancouver U10", players);

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
    // Create a list of the first 17 players.
    ArrayList<IPlayer> players = new ArrayList<>();
    players.add(player1);
    players.add(player2);
    players.add(player3);
    players.add(player4);
    players.add(player5);
    players.add(player6);
    players.add(player7);
    players.add(player8);
    players.add(player9);
    players.add(player11);
    players.add(player12);
    players.add(player13);
    players.add(player14);
    players.add(player15);
    players.add(player16);
    players.add(player17);
    players.add(player18);

    ITeam team = new Team("Vancouver U10", players);

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


