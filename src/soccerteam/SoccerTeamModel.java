package soccerteam;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;

public class SoccerTeamModel implements SoccerTeam{
private String teamName;
private int teamSize;
private HashMap<Integer, Iplayer> players;
private ArrayList<Iplayer> startingLineup;

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
        if ( startingLineup.get(i).getJerseyNumber() == jerseyNumber ){
          startingLineup.remove(i);
          break;
        }
      }
    } else {
      throw new IllegalArgumentException("Error. The inputted jersey number is not"
                                        + " on this team.");
    }
  }

  @Override
  public void selectStartingLineup() throws IllegalStateException {
    // Only select starting lineup for a valid team.
    if ( isValidTeam() ) {

      // Create priority queues (max) of all players.
      PriorityQueue<Iplayer> sortedPlayers = new PriorityQueue<>(
          Comparator.comparing(Iplayer::getSkillLevel).reversed());
      for ( Map.Entry<Integer, Iplayer> entry : players.entrySet() ) {
        Iplayer player = entry.getValue();
        sortedPlayers.add(player);
      }

      // Create priority queues (max) of all Goalies, and move them from sortedPlayers to here.
      PriorityQueue<Iplayer> goalies = new PriorityQueue<>(
          Comparator.comparing(Iplayer::getSkillLevel).reversed());
      for (Iplayer player : sortedPlayers) {
        if (player.getPreferredPosition() == Position.GOALIE) {
          goalies.add(player);
          sortedPlayers.remove(player);
        }
      }

      // Each time wipe the previous starting lineup and create a new one.
      startingLineup = new ArrayList<>();

      // Select the highest skilled Goalie (if exists in goalies' PriorityQueue).
      if ( goalies.size() >= 1 ) {
        Iplayer goalie = goalies.peek();
        startingLineup.add(goalie);
        goalie.setActualPosition(Position.GOALIE);
      }

      // Select the other 6 players in the starting lineup.
      ArrayList<Iplayer> otherSixLineups = new ArrayList<>();
      while ( otherSixLineups.size() < 6 ) {
        otherSixLineups.add(sortedPlayers.poll());
      }

      // Select the defenders for starting lineup.
      ArrayList<Integer> defendersIndex = new ArrayList<>();
      for ( int i = 0; i < otherSixLineups.size(); i++ ){
        Iplayer player = otherSixLineups.get(i);
        Position position = player.getPreferredPosition();
        while ( defendersIndex.size() < 2 ) {
          if (position == Position.DEFENDER) {
            startingLineup.add(player);
            player.setActualPosition(Position.DEFENDER);
            defendersIndex.add(i);
          }
        }
      }
      if ( defendersIndex.size() > 0 ) {
        for (int index : defendersIndex) {
          otherSixLineups.remove(index);
        }
      }

      // Select the midfielders for starting lineup.
      ArrayList<Integer> midfieldersIndex = new ArrayList<>();
      for ( int i = 0; i < otherSixLineups.size(); i++ ){
        Iplayer player = otherSixLineups.get(i);
        Position position = player.getPreferredPosition();
        while ( midfieldersIndex.size() < 3 ) {
          if (position == Position.MIDFIELDER) {
            startingLineup.add(player);
            player.setActualPosition(Position.MIDFIELDER);
            midfieldersIndex.add(i);
          }
        }
      }
      if ( midfieldersIndex.size() > 0 ) {
        for (int index : midfieldersIndex) {
          otherSixLineups.remove(index);
        }
      }

      // Select the forward for starting lineup.
      ArrayList<Integer> forwardIndex = new ArrayList<>();
      for ( int i = 0; i < otherSixLineups.size(); i++ ){
        Iplayer player = otherSixLineups.get(i);
        Position position = player.getPreferredPosition();
        while ( forwardIndex.size() < 1 ) {
          if (position == Position.FORWARD) {
            startingLineup.add(player);
            player.setActualPosition(Position.FORWARD);
            forwardIndex.add(i);
          }
        }
      }
      if ( forwardIndex.size() > 0 ) {
        for (int index : forwardIndex) {
          otherSixLineups.remove(index);
        }
      }

      // Check if the starting lineup has been properly selected.

      // Make up for shorted goalie (if any).
      if ( goalies.size() < 1 ) {
        Iplayer switchedGoalie = sortedPlayers.poll();
        startingLineup.add(switchedGoalie);
        switchedGoalie.setActualPosition(Position.GOALIE);
      }
      // Make up for other shorted positions (if any).
      if ( otherSixLineups.size() > 0 ) {
        // Make up for shorted defenders (if any).
        if ( defendersIndex.size() < 2 ) {
          int neededDefenders = 2 - defendersIndex.size();
          for ( int i = 0; i < neededDefenders; i++ ){
            Iplayer switchedDefender = otherSixLineups.remove(0);
            startingLineup.add(switchedDefender);
            switchedDefender.setActualPosition(Position.DEFENDER);
          }
        }
        // Make up for shorted midfielders (if any).
        if ( midfieldersIndex.size() < 3 ) {
          int neededMidfielders = 3 - midfieldersIndex.size();
          for ( int i = 0; i < neededMidfielders; i++ ){
            Iplayer switchedMidfielder = otherSixLineups.remove(0);
            startingLineup.add(switchedMidfielder);
            switchedMidfielder.setActualPosition(Position.MIDFIELDER);
          }
        }
        // Make up for shorted forward (if any).
        if ( forwardIndex.size() < 1 ) {
          Iplayer switchedForward = otherSixLineups.remove(0);
          startingLineup.add(switchedForward);
          switchedForward.setActualPosition(Position.FORWARD);
        }
      }
    } else {
      throw new IllegalStateException("Error. The team is not valid yet.");
    }
  }

  @Override
  public String getAllPlayers() {
    List<Iplayer> playerList = new ArrayList<>(players.values());
    playerList.sort(Comparator.comparing(Iplayer::getLastName));
    StringBuilder result = new StringBuilder();
    for (Iplayer player : playerList) {
      result.append(player).append("\n");
    }
    return result.toString();
  }

  @Override
  public String getStartingLineup() {
    ArrayList<Iplayer> playerList = new ArrayList<>(startingLineup);
    playerList.sort(Comparator.comparing(Iplayer::getLastName));
    StringBuilder result = new StringBuilder();
    for ( Iplayer player : playerList ) {
      if ( player.getActualPosition() == Position.GOALIE ) {
        result.append(player);
        playerList.remove(player);
        break;
      }
    }
    for ( Iplayer player : playerList ) {
      if ( player.getActualPosition() == Position.DEFENDER ) {
        result.append(player);
        playerList.remove(player);
      }
    }
    for ( Iplayer player : playerList ) {
      if ( player.getActualPosition() == Position.MIDFIELDER ) {
        result.append(player);
        playerList.remove(player);
      }
    }
    result.append(playerList.get(0));
    return result.toString();
  }
}
