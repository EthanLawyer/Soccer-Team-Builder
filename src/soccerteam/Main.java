package soccerteam;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The driver class for soccer team project.
 */
public class Main {
  public static void main(String[] args) {

    // Create a team.
    ITeam team = new Team("Vancouver U10");

    // Add 20 players to the team.
    team.addPlayer("John", "Doe", "2018-08-05", "goalie", 4);
    team.addPlayer("David", "Finch", "2017-05-05", "goalie", 3);
    team.addPlayer("Francis", "Underwood", "2019-05-23", "defender", 4);
    team.addPlayer("Doug", "Stamper", "2018-08-05", "midfielder", 4);
    team.addPlayer("Remy", "Danton", "2018-06-15", "defender", 2);
    team.addPlayer("Walter", "White", "2015-01-22", "forward", 5);
    team.addPlayer("James", "McGill", "2016-03-30", "midfielder", 4);
    team.addPlayer("Jesse", "Pinkman", "2014-11-25", "defender", 4);
    team.addPlayer("Gustavo", "Fring", "2018-08-05", "forward", 3);
    team.addPlayer("Joe", "Johnson", "2018-12-11", "defender", 4);
    team.addPlayer("Zack", "Daniels", "2017-10-19", "defender", 4);
    team.addPlayer("William", "Lee", "2016-07-01", "goalie", 3);
    team.addPlayer("Michael", "Jordan", "2019-05-23", "defender", 4);
    team.addPlayer("Ming", "Yao", "2018-08-05", "midfielder", 4);
    team.addPlayer("Jamie", "Oliver", "2018-06-15", "defender", 1);
    team.addPlayer("James", "Harden", "2015-01-22", "forward", 5);
    team.addPlayer("Derrick", "Rose", "2016-03-30", "midfielder", 4);
    team.addPlayer("Michael", "Ross", "2018-08-05", "forward", 3);
    team.addPlayer("Louis", "Litt", "2018-12-11", "defender", 4);
    team.addPlayer("William", "Gates", "2017-10-19", "defender", 4);

    // Print the whole team.
    System.out.println("The current team members:");
    System.out.println(team.getAllPlayersText());

    // Remove the player with jersey number 15 from the team, then add a new player to the team.
    // then print the updated team members.
    System.out.println("The original number 15 is:");
    System.out.println(team.getTeamPlayers().get(15));
    team.removePlayer(15);
    team.addPlayer("Elon", "Musk", "2016-07-01", "goalie", 3);
    System.out.println("The new number 15 is (should be Elon Musk):");
    System.out.println(team.getTeamPlayers().get(15));

    // Print the starting lineup (has not selected yet).
    System.out.println("\nThe current starting lineup (not selected yet):");
    System.out.println(team.getStartingLineupText());

    // Select the starting lineup.
    team.selectStartingLineup();

    // Print the starting lineup (now it's selected).
    System.out.println("\nThe current starting lineup (after selected):");
    System.out.println(team.getStartingLineupText());
  }
}
