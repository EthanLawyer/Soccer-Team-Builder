package soccerteam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SoccerTeamModel implements SoccerTeam{
private String teamName;
private int teamSize;
private HashMap<Integer, Iplayer> players;
private ArrayList<Integer> startingLineup;

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
    this.startingLineup = new ArrayList<>();
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
    return teamSize >= 10 && teamSize <= 20;
  }

  @Override
  public int generateJerseyNumber() {
    Random random = new Random();
    int newNumber = random.nextInt(20) + 1;
    while ( players.containsKey(newNumber) ){
      newNumber = random.nextInt(20) + 1;
    }
    return newNumber;
  }

  @Override
  public void addPlayer(Iplayer player) throws IllegalStateException {
    if ( teamSize >= 20 ){
      throw new IllegalStateException("Error. Team is already full.");
    }
    players.put(player.getJerseyNumber(), player);
    teamSize++;
  }

  @Override
  public void removePlayer(int jerseyNumber) throws IllegalArgumentException {
    if ( players.containsKey(jerseyNumber) ) {
      players.remove(jerseyNumber);
      teamSize--;
      for ( int i = 0; i < startingLineup.size(); i++ ){
        if ( startingLineup.get(i) == jerseyNumber ){
          startingLineup.remove(i);
        }
      }
    } else {
      throw new IllegalArgumentException("Error. The inputted jersey number is not"
                                        + " on this team.");
    }
  }

  @Override
  public void selectStartingLineup() throws IllegalStateException {

    }
    throw new IllegalStateException("Error. The team is not valid yet.");
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
