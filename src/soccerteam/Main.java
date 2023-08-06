package soccerteam;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The driver class for soccer team project.
 */
public class Main {
  public static void main(String[] args) {
    // Create players.
    IPlayer player1 = new Player("John", "Doe", LocalDate.of(2018, 8, 5), Position.GOALIE, 4);
    IPlayer player2 = new Player("David", "Finch", LocalDate.of(2017, 5, 5), Position.GOALIE, 3);
    IPlayer player3 = new Player("Francis", "Underwood", LocalDate.of(2019, 5, 23), Position.DEFENDER, 4);
    IPlayer player4 = new Player("Doug", "Stamper", LocalDate.of(2018, 8, 5), Position.MIDFIELDER, 4);
    IPlayer player5 = new Player("Remy", "Danton", LocalDate.of(2018, 6, 15), Position.DEFENDER, 2);
    IPlayer player6 = new Player("Walter", "White", LocalDate.of(2015, 1, 22), Position.FORWARD, 5);
    IPlayer player7 = new Player("James", "McGill", LocalDate.of(2016, 3, 30), Position.MIDFIELDER, 4);
    IPlayer player8 = new Player("Jesse", "Pinkman", LocalDate.of(2014, 11, 25), Position.DEFENDER, 4);
    IPlayer player9 = new Player("Gustavo", "Fring", LocalDate.of(2018, 8, 5), Position.FORWARD, 3);
    IPlayer player10 = new Player("Joe", "Johnson", LocalDate.of(2018, 12, 11), Position.DEFENDER, 4);
    IPlayer player11 = new Player("Zack", "Daniels", LocalDate.of(2017, 10, 19), Position.DEFENDER, 4);
    IPlayer player12 = new Player("William", "Lee", LocalDate.of(2016, 7, 1), Position.GOALIE, 3);
    IPlayer player13 = new Player("Michael", "Jordan", LocalDate.of(2019, 5, 23), Position.DEFENDER, 4);
    IPlayer player14 = new Player("Ming", "Yao", LocalDate.of(2018, 8, 5), Position.MIDFIELDER, 4);
    IPlayer player15 = new Player("Jamie", "Oliver", LocalDate.of(2018, 6, 15), Position.DEFENDER, 1);
    IPlayer player16 = new Player("James", "Harden", LocalDate.of(2015, 1, 22), Position.FORWARD, 5);
    IPlayer player17 = new Player("Derrick", "Rose", LocalDate.of(2016, 3, 30), Position.MIDFIELDER, 4);
    IPlayer player18 = new Player("Harvey", "Spector", LocalDate.of(2014, 11, 25), Position.DEFENDER, 4);
    IPlayer player19 = new Player("Michael", "Ross", LocalDate.of(2018, 8, 5), Position.FORWARD, 3);
    IPlayer player20 = new Player("Louis", "Litt", LocalDate.of(2018, 12, 11), Position.DEFENDER, 4);
    IPlayer player21 = new Player("William", "Gates", LocalDate.of(2017, 10, 19), Position.DEFENDER, 4);
    IPlayer player22 = new Player("Elon", "Musk", LocalDate.of(2016, 7, 1), Position.GOALIE, 3);

    // Create a list of the first 21 players.
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
    players.add(player10);
    players.add(player11);
    players.add(player12);
    players.add(player13);
    players.add(player14);
    players.add(player15);
    players.add(player16);
    players.add(player17);
    players.add(player18);
    players.add(player19);
    players.add(player20);
    players.add(player21);

    // Create a team using the list of 21 players, the lowest one (Jamie Oliver) will be ignored.
    ITeam team = new Team("Vancouver U10", players);

    // Print the whole team.
    System.out.println("The current team members:");
    System.out.println(team.getAllPlayersText());

    // Remove player9 (Gustavo Fring) from the team, and add the player22 (Elon Musk) to the team,
    // then print the updated team members.
    int jersey = player9.getJerseyNumber();
    team.removePlayer(jersey);
    team.addPlayer(player22);
    System.out.println("The current team members (after removing Gustavo Fring and adding Elon Musk):");
    System.out.println(team.getAllPlayersText());

    // Print the starting lineup (has not selected yet).
    System.out.println("The current starting lineup (not selected yet):");
    System.out.println(team.getStartingLineup());

    // Select the starting lineup.
    team.selectStartingLineup();

    // Print the starting lineup (now it's selected).
    System.out.println("\nThe current starting lineup (after selected):");
    System.out.println(team.getStartingLineup());
  }
}
