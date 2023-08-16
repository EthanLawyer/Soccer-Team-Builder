package soccerteam;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * The view of soccer team, an implementation of IView interface.
 * The view is a fixed-size window of 800 * 800, will exit when clicking the "x" button.
 * The view consists of 4 areas:
 * 1. the top-left displays the real-time information of the team name, players list and starting lineup;
 * 2. the bottom-left displays the result of each of the user's actions;
 * 3. the top-right displays 4 buttons to let the user choose action, namely to create a team, to add
 *    a player, to remove a player or to select the starting lineup.
 * 4. the bottom-right displays an input panel based on the chosen action of the player, which will
 *    clear and change once a button on the top-right area is clicked.
 */
public class SoccerTeamView extends JFrame implements IView {
  private final JTextArea teamDisplayArea;
  private final JTextArea actionResultArea;
  private JPanel inputPanel;
  private IController controller;

  /**
   * Constructor.
   * The left and right half of the view is implemented by GridLayout, and the top and bottom of
   * each half is implemented with a JSplitPane.
   * @param   title   the text to be displayed on the title of the GUI window.
   */
  public SoccerTeamView(String title) {
    super(title);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(1,2));
    setSize(800, 800);
    getContentPane().setBackground(Color.LIGHT_GRAY);

    // Top-left area setup (the team players information display area).
    teamDisplayArea = new JTextArea();
    teamDisplayArea.setEditable(false);
    teamDisplayArea.setOpaque(false);
    JScrollPane topLeft = new JScrollPane(teamDisplayArea); // Make it scrollable in case of too many texts.

    // Bottom-left area setup (the action result display area).
    actionResultArea = new JTextArea();
    actionResultArea.setEditable(false);
    actionResultArea.setOpaque(false);
    JScrollPane bottomLeft = new JScrollPane(actionResultArea); // Make it scrollable in case of too many texts.

    // Put the top-left and bottom-left part into a vertically split panel.
    JSplitPane leftHalf = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topLeft, bottomLeft);
    this.add(leftHalf);

    // Top-right area setup (a GridLayout JPanel that holds 4 action buttons).
    JPanel topRight = new JPanel();
    topRight.setLayout(new GridLayout(5, 1));
        // the title message.
    JLabel topRightMessage = new JLabel("Please select an action:");
    topRightMessage.setFont(new Font("Default", Font.BOLD, 16));
    topRight.add(topRightMessage);
        // the build team button.
    JButton buildTeamBtn = new JButton("Build a Team");
    buildTeamBtn.setPreferredSize(new Dimension(300, 45));
    buildTeamBtn.addActionListener(e -> showBuildTeamForm());
    topRight.add(buildTeamBtn);
        // the add player button.
    JButton addPlayerBtn = new JButton("Add a Player");
    addPlayerBtn.addActionListener(e -> showAddPlayerForm());
    topRight.add(addPlayerBtn);
        // the remove player button.
    JButton removePlayerBtn = new JButton("Remove a Player");
    removePlayerBtn.addActionListener(e -> showRemovePlayerForm());
    topRight.add(removePlayerBtn);
        // the select starting lineup button.
    JButton selectLineupBtn = new JButton("Select Starting Lineup");
    selectLineupBtn.addActionListener(e -> {
      inputPanel.removeAll();
      repaint();
      controller.selectStartingLineup();
    });
    topRight.add(selectLineupBtn);

    // Bottom-right area setup (the input panel based on the chosen action of the player, which will
    // be updated by the show method of each action.
    inputPanel = new JPanel();

    // Put the top-right and bottom-right part into a vertically split panel.
    JSplitPane rightHalf = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topRight, inputPanel);
    this.add(rightHalf);

    changeFontSize(this, 16); // Change font size of all the contents.
    setResizable(false);
    setVisible(true);
    leftHalf.setDividerLocation(0.8); // Set the proportion of the top-left: bottom-left to 4:1.
  }


  /**
   * Change the font size of all the components in their container.
   * @param   container   the container of components
   * @param   fontSize    the intended font size
   */
  private void changeFontSize(Container container, int fontSize) {
    for (Component component : container.getComponents()) {
      component.setFont(new Font("Default", Font.PLAIN, fontSize));
      if (component instanceof Container) {
        changeFontSize((Container) component, fontSize);
      }
    }
  }

  @Override
  public void setController(IController controller) {
    this.controller = controller;
  }

  @Override
  public void showBuildTeamForm() {
    inputPanel.removeAll();

    JPanel messagePanel = new JPanel();
    messagePanel.add(new JLabel("Team Name:"));

    JTextField teamNameField = new JTextField();
    teamNameField.setPreferredSize(new Dimension(300,45));
    JPanel nameInputPanel = new JPanel();
    nameInputPanel.add(teamNameField);

    JButton createTeamBtn = new JButton("Create Team");
    createTeamBtn.setPreferredSize(new Dimension(200, 45));
    createTeamBtn.addActionListener((ActionEvent e) -> controller.buildTeam(teamNameField.getText()));
    JPanel buttonPanel = new JPanel();
    buttonPanel.add(createTeamBtn);

    inputPanel.setLayout(new GridLayout(6,1));
    inputPanel.add(new JPanel());
    inputPanel.add(messagePanel);
    inputPanel.add(nameInputPanel);
    inputPanel.add(buttonPanel);
    changeFontSize(inputPanel, 16);
    revalidate();
    repaint();
  }

  @Override
  public void showAddPlayerForm() {
    inputPanel.removeAll();

    JLabel firstNameLabel = new JLabel("First Name:");
    JTextField firstNameField = new JTextField(15);
    JPanel firstNamePanel = new JPanel();
    firstNamePanel.setLayout(new FlowLayout());
    firstNamePanel.add(firstNameLabel);
    firstNamePanel.add(firstNameField);

    JLabel lastNameLabel = new JLabel("Last Name:");
    JTextField lastNameField = new JTextField(15);
    JPanel lastNamePanel = new JPanel();
    lastNamePanel.setLayout(new FlowLayout());
    lastNamePanel.add(lastNameLabel);
    lastNamePanel.add(lastNameField);

    JLabel birthDateLabel = new JLabel("Date of Birth (Y-M-D):");
    JComboBox<Integer> birthYearDropdown = new JComboBox<>();
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    for (int i = currentYear; i >= currentYear - 50; i--) {
      birthYearDropdown.addItem(i);
    }

    JComboBox<Integer> birthDayDropdown = new JComboBox<>();
    for (int i = 1; i <= 31; i++) {
      birthDayDropdown.addItem(i);
    }

    JComboBox<Integer> birthMonthDropdown = new JComboBox<>();
    for (int i = 1; i <= 12; i++) {
      birthMonthDropdown.addItem(i);
    }
    JPanel birthDatePanel = new JPanel();
    birthDatePanel.setLayout(new FlowLayout());
    birthDatePanel.add(birthDateLabel);
    birthDatePanel.add(birthYearDropdown);
    birthDatePanel.add(birthMonthDropdown);
    birthDatePanel.add(birthDayDropdown);

    JLabel positionLabel = new JLabel("Position:");
    JComboBox<Position> positionDropdown = new JComboBox<>();
    for ( Position position : Position.values() ){
      if ( position != Position.BENCH ){
        positionDropdown.addItem(position);
      }
    }
    JPanel positionPanel = new JPanel();
    positionPanel.setLayout(new FlowLayout());
    positionPanel.add(positionLabel);
    positionPanel.add(positionDropdown);

    JLabel skillLevelLabel = new JLabel("Skill Level:");
    JComboBox<Integer> skillLevelDropdown = new JComboBox<>();
    for (int i = 1; i <= 5; i++) {
      skillLevelDropdown.addItem(i);
    }
    JPanel skillLevelPanel = new JPanel();
    skillLevelPanel.setLayout(new FlowLayout());
    skillLevelPanel.add(skillLevelLabel);
    skillLevelPanel.add(skillLevelDropdown);

    JPanel submitButtonPanel = new JPanel();
    JButton submitButton = new JButton("Submit");
    submitButton.setPreferredSize(new Dimension(100, 45));
    submitButtonPanel.add(submitButton);
    submitButton.addActionListener(e -> {
      String firstName = firstNameField.getText();
      String lastName = lastNameField.getText();
      Integer day = (Integer) birthDayDropdown.getSelectedItem();
      Integer month = (Integer) birthMonthDropdown.getSelectedItem();
      String year = birthYearDropdown.getSelectedItem().toString();
      String position = positionDropdown.getSelectedItem().toString();
      int skillLevel = (int) skillLevelDropdown.getSelectedItem();
      String dateOfBirth = year + "-" + String.format("%02d", month)+ "-" + String.format("%02d", day);

      controller.addPlayer(firstName, lastName, dateOfBirth, position, skillLevel);
    });

      inputPanel.setLayout(new GridLayout(8,1));
      inputPanel.add(new JPanel());
      inputPanel.add(firstNamePanel);
      inputPanel.add(lastNamePanel);
      inputPanel.add(birthDatePanel);
      inputPanel.add(positionPanel);
      inputPanel.add(skillLevelPanel);
      inputPanel.add(submitButtonPanel);

      changeFontSize(inputPanel, 16);
      inputPanel.revalidate();
      inputPanel.repaint();
    }


  @Override
  public void showRemovePlayerForm() {
    inputPanel.removeAll();

    JComboBox<Integer> jerseyNumberDropdown = new JComboBox<>();
    for (int i = 1; i <= 20; i++) {
      jerseyNumberDropdown.addItem(i);
    }
    JPanel jerseyNumberPanel = new JPanel();
    jerseyNumberPanel.add(new JLabel("Jersey Number:"));
    jerseyNumberPanel.add(jerseyNumberDropdown);

    JButton removePlayerBtn = new JButton("Remove Player");
    removePlayerBtn.setPreferredSize(new Dimension(200,45));
    removePlayerBtn.addActionListener((ActionEvent e) -> {
      controller.removePlayer(Integer.parseInt(jerseyNumberDropdown.getSelectedItem().toString()));
    });
    JPanel removeButtonPanel = new JPanel();
    removeButtonPanel.add(removePlayerBtn);


    inputPanel.setLayout(new GridLayout(6,1));
    inputPanel.add(new JPanel());
    inputPanel.add(jerseyNumberPanel);
    inputPanel.add(removeButtonPanel);

    changeFontSize(inputPanel, 16);
    revalidate();
    repaint();
  }

  @Override
  // Method to update the lower part of the left half of the GUI with action results.
  public void updateActionResult(String message) {
    actionResultArea.setText(message);
  }

  @Override
  // Method to update the upper part of the left half of the GUI with team members and starting lineup.
  public void updateTeamMembers(String teamName, String allPlayers, String startingLineup) {
    teamDisplayArea.setText(teamName + allPlayers + startingLineup);
  }
}
