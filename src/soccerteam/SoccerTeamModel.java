package soccerteam;

import java.util.HashMap;

public class SoccerTeamModel implements SoccerTeam{
private String teamName;
private int teamSize;
private HashMap<Integer, Iplayer> players;

  /**
   * Constructor.
   * Players will be a hashmap, where keys are jersey numbers, and values
   * are Iplayer objects.
   * @param   teamName  the name of this team
   */
  public SoccerTeamModel(String teamName) {
    this.teamName = teamName;
    this.teamSize = 0;
    this.players = new HashMap<>();
  }

  @Override
  public String getName() {
    return teamName;
  }

  @Override
  public int getSize() {
    return teamSize;
  }

  @Override
  public boolean isValidTeam() {
    if (teamSize >= 10 && teamSize <= 20) {
      return true;
    } else {
      return false;
    }
  }

  @Override public int generateJerseyNumber() {
    return 0;
  }

  @Override public void addPlayer(Iplayer player) {

  }

  @Override public void removePlayer(int jerseyNumber) {

  }

  @Override public void selectStartingLineup() {

  }

  @Override
  public String getAllPlayers() {
    return null;
  }

  @Override
  public String getStartingLineup() {
    return null;
  }
}
