package soccerteam;

import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

/**
 * Test suite for IPlayer.
 */
public class IPlayerTest {
  private IPlayer player1;

  /**
   * Setup 22 players.
   */
  @Before
  public void setUp(){
    player1 = new Player("John", "Doe", LocalDate.of(2018, 8, 5), Position.GOALIE, 4);
  }
  // Test the constructor under correct settings.
  @Test
  public void testCorrectConstructor(){
    assertEquals("John", player1.getFirstName());
    assertEquals("Doe", player1.getLastName());
    assertEquals(LocalDate.of(2018, 8, 5), player1.getDateOfBirth());
    assertEquals(-1, player1.getJerseyNumber());
    assertEquals(4, player1.getSkillLevel());
    assertEquals(Position.GOALIE, player1.getPreferredPosition());
    assertEquals(Position.BENCH, player1.getActualPosition());
  }

  /**
   * Test constructor when skill level is out of range.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSkillLevel1(){
    new Player("Kevin", "Durant", LocalDate.of(2016, 7, 1), Position.GOALIE, 7);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSkillLevel2(){
    new Player("Kevin", "Durant", LocalDate.of(2016, 7, 1), Position.GOALIE, 0);
  }

  /**
   * Test setSkillLevel method.
   */
  @Test
  public void testSetSkillLevel(){
    player1.setSkillLevel(5);
    assertEquals(5, player1.getSkillLevel());
  }
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSetSkillLevel1(){
    player1.setSkillLevel(0);
  }
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSetSkillLevel2(){
    player1.setSkillLevel(6);
  }

  /**
   * Test setJerseyNumber method.
   */
  @Test
  public void testSetJerseyNumber(){
    player1.setJerseyNumber(12);
    assertEquals(12, player1.getJerseyNumber());
  }
  @Test(expected = IllegalArgumentException.class)
  public void testSetInvalidJerseyNumber(){
    player1.setJerseyNumber(0);
  }
  @Test(expected = IllegalStateException.class)
  public void testSetJerseyForOnTeamPlayer(){
    player1.setJerseyNumber(12);
    player1.setJerseyNumber(16);
  }

  /**
   * Test setActualPosition method.
   */
  @Test
  public void testSetActualPosition(){
    player1.setJerseyNumber(12);
    player1.setActualPosition(Position.GOALIE);
    assertEquals(Position.GOALIE, player1.getActualPosition());
  }
  @Test(expected = IllegalStateException.class)
  public void testSetActualPositionForPlayerNotOnTeam(){
    player1.setActualPosition(Position.GOALIE);
  }

  /**
   * Test toString method.
   */
  @Test
  public void testToString(){
    String text = "-1. John Doe";
    assertEquals(text, player1.toString());
    player1.setJerseyNumber(12);
    player1.setActualPosition(Position.GOALIE);
    text = "12. John Doe, Goalie";
    assertEquals(text, player1.toString());
  }
}
