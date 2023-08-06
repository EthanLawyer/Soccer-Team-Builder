package soccerteam;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

public class Team implements ITeam {
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
  public Team(String teamName, ArrayList<IPlayer> players)
      throws IllegalArgumentException
  {
    // If less than 10 players, a team cannot be created.
    if ( players.size() < 10 ) {
      throw new IllegalArgumentException("Error. At least 10 players are "
                                        + "needed. Please add more.");
    }
    // Any player that is not under 10 years old will be ignored.
    ArrayList<IPlayer> toBeRemoved = new ArrayList<>();
    for ( IPlayer player : players ) {
      int age = Period.between(player.getDateOfBirth(), LocalDate.now()).getYears();
      if ( age >= 10 ) {
        toBeRemoved.add(player);
      }
    }
    for ( IPlayer player : toBeRemoved ) {
      players.remove(player);
    }

    // If more than 20 players, the least skilled ones will be ignored.
    if ( players.size() > 20 ) {
      int redundant = players.size() - 20;
      players.sort(Comparator.comparing(IPlayer::getSkillLevel));
      players.subList(0, redundant).clear();
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
    this.teamSize = teamPlayers.size();
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
    return random.nextInt(20) + 1;
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
    // Each time wipe the previous starting lineup and create a new one.
    startingLineup = new ArrayList<>();

    // Create a TreeSet sortedPlayers of all players based on their skill levels (max first).
    TreeSet<IPlayer> sortedPlayers = new TreeSet<>(
        Comparator.comparingInt(IPlayer::getSkillLevel).reversed().thenComparing(IPlayer::getJerseyNumber));
    for ( Map.Entry<Integer, IPlayer> entry : teamPlayers.entrySet() ) {
      IPlayer player = entry.getValue();
      sortedPlayers.add(player);
    }

    // Create TreeSet (max) of all Goalies, and move them from sortedPlayers to here.
    // Goalies will be removed from sortedPlayers because the goalie should be played by goalie
    // as long as there is a goalie on team, and other positions should not be substituted by
    // Goalie unless players are not enough.
    TreeSet<IPlayer> goalies = new TreeSet<>(
        Comparator.comparing(IPlayer::getSkillLevel).reversed().thenComparing(IPlayer::getJerseyNumber));
    for ( IPlayer player : sortedPlayers ) {
      if ( player.getPreferredPosition() == Position.GOALIE ) {
        goalies.add(player);
      }
    }
    for ( IPlayer player : goalies ) {
      sortedPlayers.remove(player);
    }

    // Select the highest skilled Goalie (if exists in goalies' PriorityQueue).
    if ( goalies.size() >= 1 ) {
      IPlayer goalie = goalies.first();
      goalie.setActualPosition(Position.GOALIE);
      startingLineup.add(goalie);
    }
    // Select the other 6 players in the starting lineup.
    // If there are less than 6 players left after removing Goalies, shorted
    // position should be substituted with the highest ranking goalie left.
    while ( sortedPlayers.size() < 6 ) {
      sortedPlayers.add(goalies.pollFirst());
    }
    ArrayList<IPlayer> otherSixLineups = new ArrayList<>();
    while ( otherSixLineups.size() < 6 ) {
      otherSixLineups.add(sortedPlayers.pollFirst());
    }
    // Select the defenders for starting lineup.
    ArrayList<IPlayer> startingDefendersTemp = new ArrayList<>();
    for ( IPlayer player : otherSixLineups ) {
      Position position = player.getPreferredPosition();
      if ( position == Position.DEFENDER ) {
        startingDefendersTemp.add(player);
      }
    }
    int countDefenders = 0;
    for ( IPlayer player : startingDefendersTemp ) {
      if ( countDefenders < 2 ) {
        startingLineup.add(player);
        player.setActualPosition(Position.DEFENDER);
        otherSixLineups.remove(player);
        countDefenders++;
      }
    }
    // Select the midfielders for starting lineup.
    ArrayList<IPlayer> startingMidfieldersTemp = new ArrayList<>();
    for ( IPlayer player : otherSixLineups ) {
      Position position = player.getPreferredPosition();
      if (position == Position.MIDFIELDER) {
        startingMidfieldersTemp.add(player);
      }
    }
    int countMidfielders = 0;
    for ( IPlayer player : startingMidfieldersTemp ) {
      if ( countMidfielders < 3 ) {
        startingLineup.add(player);
        player.setActualPosition(Position.MIDFIELDER);
        otherSixLineups.remove(player);
        countMidfielders++;
      }
    }
    // Select the forward for starting lineup.
    ArrayList<IPlayer> startingForwardsTemp = new ArrayList<>();
    for ( IPlayer player : otherSixLineups ) {
      Position position = player.getPreferredPosition();
      if (position == Position.FORWARD) {
        startingForwardsTemp.add(player);
      }
    }
    int countForwards = 0;
    for ( IPlayer player : startingForwardsTemp ) {
      if ( countForwards < 1 ) {
        startingLineup.add(player);
        player.setActualPosition(Position.FORWARD);
        otherSixLineups.remove(player);
        countForwards++;
      }
    }

    // Check if the starting lineup has been properly selected.
    // Make up for shorted goalie first (if any).
    if (goalies.size() < 1) {
      IPlayer switchedGoalie = sortedPlayers.pollFirst();
      startingLineup.add(switchedGoalie);
      switchedGoalie.setActualPosition(Position.GOALIE);
    }
    // Make up for other shorted positions (if any).
    if (otherSixLineups.size() > 0) {
      // Make up for shorted defenders (if any).
      while ( startingDefendersTemp.size() < 2 ) {
        IPlayer switchedDefender = otherSixLineups.remove(0);
        startingDefendersTemp.add(switchedDefender);
        startingLineup.add(switchedDefender);
        switchedDefender.setActualPosition(Position.DEFENDER);
      }
      // Make up for shorted midfielders (if any).
      while ( startingMidfieldersTemp.size() < 3 ) {
        IPlayer switchedMidfielder = otherSixLineups.remove(0);
        startingMidfieldersTemp.add(switchedMidfielder);
        startingLineup.add(switchedMidfielder);
        switchedMidfielder.setActualPosition(Position.MIDFIELDER);
      }
      // Make up for shorted forward (if any).
      while ( startingForwardsTemp.size() < 1 ) {
        IPlayer switchedForward = otherSixLineups.remove(0);
        startingForwardsTemp.add(switchedForward);
        startingLineup.add(switchedForward);
        switchedForward.setActualPosition(Position.FORWARD);
      }
    }
  }

  @Override
  public String getAllPlayersText() {
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
    if ( startingLineup.size() == 0 ) {
      return "The starting lineup has not been selected.";
    }
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
    ArrayList<IPlayer> toBeRemoved = new ArrayList<>();
    for ( IPlayer player : playerList ) {
      if ( player.getActualPosition() == Position.DEFENDER ) {
        result.append("\n").append(player);
        toBeRemoved.add(player);
      }
    }
    for ( IPlayer player : toBeRemoved ) {
      playerList.remove(player);
    }
    toBeRemoved.clear();
    for ( IPlayer player : playerList ) {
      if ( player.getActualPosition() == Position.MIDFIELDER ) {
        result.append("\n").append(player);
        toBeRemoved.add(player);
      }
    }
    for ( IPlayer player : toBeRemoved ) {
      playerList.remove(player);
    }
    toBeRemoved.clear();
    result.append("\n").append(playerList.get(0));
    return result.toString();
  }
}
