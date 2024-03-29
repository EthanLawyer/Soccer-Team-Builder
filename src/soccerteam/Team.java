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
private final String teamName;
private int teamSize;
private final HashMap<Integer, IPlayer> teamPlayers;
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
   * @throws  IllegalArgumentException  when inputted less than 10 players
   */
  public Team(String teamName) throws IllegalArgumentException {
    this.teamName = teamName;
    this.teamSize = 0;
    this.teamPlayers = new HashMap<>();
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
  public void addPlayer(String firstName, String lastName, String dateOfBirth, String preferredPosition,
      int skillLevel) throws IllegalStateException, IllegalArgumentException {
    // When the team is full, check if the new player has a higher skill level than the least skilled
    // player on team. If yes, remove the least skilled player and add the new one, otherwise do nothing.
    if ( teamSize >= 20 ){
      ArrayList<IPlayer> sortedPlayers = new ArrayList<>(teamPlayers.values());
      sortedPlayers.sort(Comparator.comparing(IPlayer::getSkillLevel));
      if ( skillLevel > sortedPlayers.get(0).getSkillLevel() ) {
        removePlayer(sortedPlayers.get(0).getJerseyNumber());
      } else {
        return;
      }
    }
    // Check if the date of birth is in correct format.
    LocalDate birthDate;
    try {
      birthDate = LocalDate.parse(dateOfBirth);
    } catch (java.time.format.DateTimeParseException e) {
      throw new IllegalArgumentException("Error. The date of birth should be in the format of YYYY-MM-DD.");
    }
    // Check if the player is over 10.
    int age = Period.between(birthDate, LocalDate.now()).getYears();
    if ( age >= 10 ) {
      throw new IllegalArgumentException("Error. The player must be under 10 years old.");
    }
    // Check if the preferred position is valid.
    Position position;
    try {
      position = Position.valueOf(preferredPosition.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Error. Invalid preferred position.");
    }
    // Check if the skill level is valid.
    if ( skillLevel < 1 || skillLevel > 5 ) {
      throw new IllegalArgumentException("Error. Skill level must be within 1 and 5.");
    }
    // Create player.
    IPlayer player = new Player(firstName, lastName, birthDate, position, skillLevel);
    // Check if this player is already on team.
    for ( IPlayer currentPlayer : teamPlayers.values() ){
      if ( currentPlayer.equals(player) ){
        throw new IllegalArgumentException("Error. This player is already on team.");
      }
    }
    // Assign jersey number.
    int newJerseyNumber = generateJerseyNumber();
    while ( teamPlayers.containsKey(newJerseyNumber) ) {
      newJerseyNumber = generateJerseyNumber();
    }
    player.setJerseyNumber(newJerseyNumber);
    // Add the player to this team.
    teamPlayers.put(newJerseyNumber, player);
    teamSize++;
  }


  @Override
  public void removePlayer(int jerseyNumber) throws IllegalStateException,
                                                    IllegalArgumentException
  {
    if ( teamSize <= 10 ) {
      throw new IllegalStateException("Error. No more than 10 players left, cannot remove any player.");
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
      throw new IllegalArgumentException("Error. The inputted jersey number is not on this team.");
    }
  }


  @Override
  public void selectStartingLineup() throws IllegalStateException {
    // Check if the team has no less than 10 players. If not, throw exception.
    if ( teamSize < 10 ) {
      throw new IllegalStateException("Error. Team has less than 10 players, please add more.");
    }
    // Each time wipe the previous starting lineup and create a new one.
    for ( IPlayer player : startingLineup ) {
      player.setActualPosition(Position.BENCH);
    }
    startingLineup = new ArrayList<>();

    // Create a TreeSet sortedPlayers of all players based on their skill levels (max first).
    // Tie-breaking rule: If same skill level, youngest first; if same age, last name order.
    TreeSet<IPlayer> sortedPlayers = new TreeSet<>(
        Comparator.comparingInt(IPlayer::getSkillLevel).reversed().thenComparing(IPlayer::getDateOfBirth)
            .thenComparing(IPlayer::getLastName).thenComparing(IPlayer::getFirstName).
                thenComparing(IPlayer::getPreferredPosition).thenComparing(IPlayer::getJerseyNumber));
    for ( Map.Entry<Integer, IPlayer> entry : teamPlayers.entrySet() ) {
      IPlayer player = entry.getValue();
      sortedPlayers.add(player);
    }

    // Create TreeSet (max) of all Goalies, and move them from sortedPlayers to here.
    // Goalies will be removed from sortedPlayers because the goalie should be played by goalie
    // as long as there is a goalie on team, and other positions should not be substituted by
    // Goalie unless players are not enough.
    // Tie-breaking rule: If same skill level, youngest first; if same age, last name order.
    TreeSet<IPlayer> goalies = new TreeSet<>(
        Comparator.comparing(IPlayer::getSkillLevel).reversed().thenComparing(IPlayer::getDateOfBirth)
            .thenComparing(IPlayer::getLastName).thenComparing(IPlayer::getFirstName).
                thenComparing(IPlayer::getPreferredPosition).thenComparing(IPlayer::getJerseyNumber));
    for ( IPlayer player : sortedPlayers ) {
      if ( player.getPreferredPosition() == Position.GOALIE ) {
        goalies.add(player);
      }
    }
    for ( IPlayer player : goalies ) {
      sortedPlayers.remove(player);
    }

    // Select the highest skilled Goalie (if exists).
    if (!goalies.isEmpty()) {
      IPlayer goalie = goalies.pollFirst();
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
    if ( goalies.isEmpty() ) {
      IPlayer switchedGoalie = sortedPlayers.pollFirst();
      startingLineup.add(switchedGoalie);
      switchedGoalie.setActualPosition(Position.GOALIE);
    }
    // Make up for other shorted positions (if any).
    if ( !otherSixLineups.isEmpty() ) {
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
      while ( startingForwardsTemp.isEmpty() ) {
        IPlayer switchedForward = otherSixLineups.remove(0);
        startingForwardsTemp.add(switchedForward);
        startingLineup.add(switchedForward);
        switchedForward.setActualPosition(Position.FORWARD);
      }
    }
  }


  @Override
  public String getAllPlayersText() {
    // Create a new ArrayList to store the sorted players.
    ArrayList<IPlayer> playerList = new ArrayList<>(teamPlayers.values());
    // Sort all players by last name.
    playerList.sort(Comparator.comparing(IPlayer::getLastName));
    StringBuilder result = new StringBuilder();
    for (IPlayer player : playerList) {
      result.append(player).append("\n");
    }
    return result.toString();
  }


  @Override
  public String getStartingLineupText() {
    if ( startingLineup.isEmpty() ) {
      return "The starting lineup has not been selected.";
    }
    // Create a new ArrayList to store the sorted starting lineup players.
    ArrayList<IPlayer> playerList = new ArrayList<>(startingLineup);
    // Sort all players by last name.
    playerList.sort(Comparator.comparing(IPlayer::getLastName));
    StringBuilder result = new StringBuilder();
    // Search for goalie first, append to result, then remove from sorted starting lineup players list.
    for ( IPlayer player : playerList ) {
      if ( player.getActualPosition() == Position.GOALIE ) {
        result.append(player);
        playerList.remove(player);
        break;
      }
    }
    // Create a separate list for players to be removed, in order to prevent index error.
    ArrayList<IPlayer> toBeRemoved = new ArrayList<>();
    // Search for defenders, append to result, then remove from sorted starting lineup players list.
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
    // Search for midfielders, append to result, then remove from sorted starting lineup players list.
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
    // The only one left will be the forward.
    result.append("\n").append(playerList.get(0));
    return result.toString();
  }
}
