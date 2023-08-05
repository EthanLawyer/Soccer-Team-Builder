package soccerteam;

import java.util.HashMap;

public class SoccerTeamModel implements SoccerTeam{
private String teamName;
private int teamSize;
private HashMap<Integer, Iplayer> players;

  /**
   * Constructor.
   */
  public SoccerTeamModel(String teamName) {
    this.teamName = teamName;
    this.teamSize = 0;
    this.players = new HashMap<>();
  }

  @Override public String getName() {
    return null;
  }

  @Override public int getSize() {
    return 0;
  }

  @Override public boolean isValidTeam() {
    return false;
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

  @Override public String getAllPlayers() {
    return null;
  }

  @Override public String getStartingLineup() {
    return null;
  }
}
