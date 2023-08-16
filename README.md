# CS5004 Final Project -- Soccer Team Builder

### _Author: Yixiao Zhu (Ethan)_

This is the final project for NEU Summer 2023 CS5004: Object-Oriented Design.
 

## Overview
 
 This program works as a U10 soccer team builder, designed for a coach to manage players on his/her team. 
 
 This program is developed in Java, and comes with a GUI interface. 
 
 
## Features
 Through the GUI interface, the user can:
 - Create a soccer team
 - Add player(s) to the team
 - Remove player(s) from the team
 - Select the starting lineup
 - Read the real-time list of players and starting lineup
 - Be notified of the result of each action, including any error occured

 A few speacial things to notice:
 - Jersey numbers are unique and are assigned randomly between 1 and 20 (inclusive) when a player is added to the team. Once assigned, it cannot be changed.
 - For U10 team, only player's under 10 years of age can be added to it. If you attemp to add a player over 10 years-old, an error message will display on the bottom-left area.
 - The team should have at least 10 players. When there are not enough players, the top-left area of the interface will always show a warning message, and you will not be able to remove player(s) or select the starting lineup.
 - Maximum capacity of a team is 20 players. If a team is full, and you attemp to add another player, then if the new player has a higher skill level than the least skilled on team, the least skilled player will be removed, and the new player will be added; otherwise the new player will be ignored, and the team will remain unchanged.
 - The logic of selecting the starting lineup is as follows:
    - In principle, the most skilled 7 players will be selected.
    - If possible, these players will be assigned their preferred positions, but not necessary. 
    - However, the goalie should be played by the most skilled Goalie as long as there is a Goalie on team.
    - If there is no Goalie on team, the other 6 positions will be selected first, and then the most skilled player of the remaining players will play Goalie.
    - Among the other 6 starting lineups other than the Goalie, they will by default play their preferred position. But if some positions are not filled, the remaining of the 6 players will substitute.
   

## How To Run

Open `soccerteam.jar` under `/res` directory to run.

Java Runtime Environment (JRE) or Java Development Kit (JDK) may be required.


## How to Use the Program

The interface consists of 4 areas:
1. the top-left displays the real-time information of the team name, players list and starting lineup;
2. the bottom-left displays the result of each of the user's actions;
3. the top-right displays 4 buttons to let the user choose action, namely to create a team, to add a player, to remove a player or to select the starting lineup.
4. the bottom-right displays an input panel based on the chosen action of the player, which will clear and change once a button on the top-right area is clicked.

To start with, please create a team first. Otherwise error will be shown on the bottom-left area.

Please pay attention to the bottom left area for action status / error.


## Design/Model Changes

 The program is designed under MVC priciple.
 
 The Model part has two interfaces, one for the player and one for the team. However, the team interface will be the only interface that interacts with Controller, and the player interface is hidden from any interface other than the team.
 
 The view is designed with both Java AWT library and Swing library.

## Assumptions
Subject to the requirements of this final project, it is based on the following assumptions:

- Players are children under 10 years old.
- 7 players on the field, in a 2-3-1 form: 
  - 1 Goalie
  - 2 Defenders
  - 3 Midfielders
  - 1 Forward
- The team must have a minimum of 10 players and a maximum of 20

## Limitations
Aside from any limitation caused by the above assumptions, this program have the following potential limitations:
 - A player can only have one preferred position.
 - Skill levels are integers between 1 and 5 (inclusive), may not be enough to accurately represent the skill level of players.
 - The starting lineup is automatically selected based on my designed logic, the user is not allowed to choose or change part of it.
 - There is no storage function, each time the user has to start from scratch.



## Citations
This program is not referencing any resource other than the official java documentation.
