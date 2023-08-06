package soccerteam;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeSet;

public class SoccerTeamModel implements SoccerTeam{
private String teamName;
private int teamSize;
private HashMap<Integer, IPlayer> teamPlayers;
private ArrayList<IPlayer> startingLineup;

  /**
   * Constructor.
   * Players will be a hashmap, where keys are jersey numbers, and values
   * are IPlayer objects.
   * If there are less than 10 players, team cannot be created. If the team
   * has more than 20 players, the ones with the lowest skill level will be
   * ignored so that we only have 20 players.
   * Any player that is not under 10 years of age will be ignored.
   * @param   teamName  the name of this team
   * @param   players   a list of IPlayer objects
   * @throws  IllegalArgumentException  when inputted less than 10 players
   */
  public SoccerTeamModel(String teamName, ArrayList<IPlayer> players)
      throws IllegalArgumentException
  {
    // If less than 10 players, a team cannot be created.
    if ( players.size() < 10 ) {
      throw new IllegalArgumentException("Error. At least 10 players are "
                                        + "needed. Please add more.");
    }
    // Any player that is not under 10 years old will be ignored.
    for ( IPlayer player : players ) {
      int age = Period.between(player.getDateOfBirth(), LocalDate.now()).getYears();
      if ( age >= 10 ) {
        players.remove(player);
      }
    }
    // If more than 20 players, the least skilled ones will be ignored.
    if ( players.size() > 20 ) {
      int redundant = players.size() - 20;
      players.sort(Comparator.comparing(IPlayer::getSkillLevel));
      for ( int i = 0; i < redundant; i++ ) {
        players.remove(0);
      }
    }
    // When number of players is valid, we assign jersey numbers for players,
    // and store them in a treeset to avoid duplication.
    TreeSet<Integer> jerseyNumbers = new TreeSet<>();
    while ( jerseyNumbers.size() < players.size() ) {
      jerseyNumbers.add(generateJerseyNumber());
    }
    // After generating jersey numbers, add each player to the teamPlayers hashmap.
    this.teamPlayers = new HashMap<>();
    for ( int jersey : jerseyNumbers ) {
      IPlayer addedPlayer = players.remove(0);
      addedPlayer.setJerseyNumber(jersey);
      teamPlayers.put(jersey,addedPlayer);
    }
    this.teamName = teamName;
    this.teamSize = 0;
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

  /**
   * Generates a random jersey number between 1 and 20.
   * @return int
   */
  private int generateJerseyNumber() {
    Random random = new Random();
    int newNumber = random.nextInt(20) + 1;
    return newNumber;
  }

  @Override
  public void addPlayer(IPlayer player) throws IllegalStateException, IllegalArgumentException {
    if ( teamSize >= 20 ){
      throw new IllegalStateException("Error. Team is already full.");
    }
    int age = Period.between(player.getDateOfBirth(), LocalDate.now()).getYears();
    if ( age >= 10 ) {
      throw new IllegalArgumentException("Error. The player must be under 10 years old.");
    }
    int newJerseyNumber = generateJerseyNumber();
    while ( teamPlayers.containsKey(newJerseyNumber) ) {
      newJerseyNumber = generateJerseyNumber();
    }
    player.setJerseyNumber(newJerseyNumber);
    teamPlayers.put(newJerseyNumber, player);
    teamSize++;
  }

  @Override
  public void removePlayer(int jerseyNumber) throws IllegalStateException,
                                                    IllegalArgumentException
  {
    if ( teamSize == 10 ) {
      throw new IllegalStateException("Error. Only 10 players left, cannot "
                                    + "remove any player.");
    }
    if ( teamPlayers.containsKey(jerseyNumber) ) {
      teamPlayers.remove(jerseyNumber);
      teamSize--;
      for ( int i = 0; i < startingLineup.size(); i++ ){
        if ( startingLineup.get(i).getJerseyNumber() == jerseyNumber ){
          startingLineup.remove(i);
          break;
        }
      }
    } else {
      throw new IllegalArgumentException("Error. The inputted jersey number "
                                        + "is not on this team.");
    }
  }

  @Override
  public void selectStartingLineup() throws IllegalStateException {

    // Create priority queues (max) of all players.
    PriorityQueue<IPlayer> sortedPlayers = new PriorityQueue<>(
        Comparator.comparing(IPlayer::getSkillLevel).reversed());
    for ( Map.Entry<Integer, IPlayer> entry : teamPlayers.entrySet() ) {
      IPlayer player = entry.getValue();
      sortedPlayers.add(player);
    }

    // Create priority queues (max) of all Goalies, and move them from sortedPlayers to here.
    PriorityQueue<IPlayer> goalies = new PriorityQueue<>(
        Comparator.comparing(IPlayer::getSkillLevel).reversed());
    for (IPlayer player : sortedPlayers) {
      if (player.getPreferredPosition() == Position.GOALIE) {
        goalies.add(player);
        sortedPlayers.remove(player);
      }
    }

    // Each time wipe the previous starting lineup and create a new one.
    startingLineup = new ArrayList<>();

    // Select the highest skilled Goalie (if exists in goalies' PriorityQueue).
    if ( goalies.size() >= 1 ) {
      IPlayer goalie = goalies.peek();
      startingLineup.add(goalie);
      goalie.setActualPosition(Position.GOALIE);
    }

    // Select the other 6 players in the starting lineup.
    ArrayList<IPlayer> otherSixLineups = new ArrayList<>();
    while ( otherSixLineups.size() < 6 ) {
      otherSixLineups.add(sortedPlayers.poll());
    }

    // Select the defenders for starting lineup.
    ArrayList<Integer> defendersIndex = new ArrayList<>();
    for ( int i = 0; i < otherSixLineups.size(); i++ ){
      IPlayer player = otherSixLineups.get(i);
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
      IPlayer player = otherSixLineups.get(i);
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
      IPlayer player = otherSixLineups.get(i);
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
    // Make up for shorted goalie first (if any).
    if ( goalies.size() < 1 ) {
      IPlayer switchedGoalie = sortedPlayers.poll();
      startingLineup.add(switchedGoalie);
      switchedGoalie.setActualPosition(Position.GOALIE);
    }
    // Make up for other shorted positions (if any).
    if ( otherSixLineups.size() > 0 ) {
      // Make up for shorted defenders (if any).
      if ( defendersIndex.size() < 2 ) {
        int neededDefenders = 2 - defendersIndex.size();
        for ( int i = 0; i < neededDefenders; i++ ){
          IPlayer switchedDefender = otherSixLineups.remove(0);
          startingLineup.add(switchedDefender);
          switchedDefender.setActualPosition(Position.DEFENDER);
        }
      }
      // Make up for shorted midfielders (if any).
      if ( midfieldersIndex.size() < 3 ) {
        int neededMidfielders = 3 - midfieldersIndex.size();
        for ( int i = 0; i < neededMidfielders; i++ ){
          IPlayer switchedMidfielder = otherSixLineups.remove(0);
          startingLineup.add(switchedMidfielder);
          switchedMidfielder.setActualPosition(Position.MIDFIELDER);
        }
      }
      // Make up for shorted forward (if any).
      if ( forwardIndex.size() < 1 ) {
        IPlayer switchedForward = otherSixLineups.remove(0);
        startingLineup.add(switchedForward);
        switchedForward.setActualPosition(Position.FORWARD);
      }
    }
  }

  @Override
  public String getAllPlayers() {
    ArrayList<IPlayer> playerList = new ArrayList<>(teamPlayers.values());
    playerList.sort(Comparator.comparing(IPlayer::getLastName));
    StringBuilder result = new StringBuilder();
    for (IPlayer player : playerList) {
      result.append(player).append("\n");
    }
    return result.toString();
  }

  @Override
  public String getStartingLineup() {
    ArrayList<IPlayer> playerList = new ArrayList<>(startingLineup);
    playerList.sort(Comparator.comparing(IPlayer::getLastName));
    StringBuilder result = new StringBuilder();
    for ( IPlayer player : playerList ) {
      if ( player.getActualPosition() == Position.GOALIE ) {
        result.append(player);
        playerList.remove(player);
        break;
      }
    }
    for ( IPlayer player : playerList ) {
      if ( player.getActualPosition() == Position.DEFENDER ) {
        result.append(player);
        playerList.remove(player);
      }
    }
    for ( IPlayer player : playerList ) {
      if ( player.getActualPosition() == Position.MIDFIELDER ) {
        result.append(player);
        playerList.remove(player);
      }
    }
    result.append(playerList.get(0));
    return result.toString();
  }
}
