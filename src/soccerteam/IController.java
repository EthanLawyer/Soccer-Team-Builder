package soccerteam;

public interface IController {
  void buildTeam(String teamName);

  void addPlayer(String firstName, String lastName, String dateOfBirth, String position,
      int skillLevel);

  void removePlayer(int jerseyNumber);

  void selectStartingLineup();

  void updateTeamView();
}
