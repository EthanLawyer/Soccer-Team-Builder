package soccerteam;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.*;
import java.awt.*;

public class SoccerTeamView extends JFrame implements IView{
  private JTextArea teamMembersArea;
  private JTextArea actionResultArea;
  private JPanel rightLowerPanel;
  private IController controller;

  public SoccerTeamView(String title) {
    super(title);

    setLayout(new GridLayout(1, 2));
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setBackground(Color.LIGHT_GRAY);

    // Left half setup
    teamMembersArea = new JTextArea();
    teamMembersArea.setEditable(false);
    teamMembersArea.setOpaque(false);

    actionResultArea = new JTextArea();
    actionResultArea.setEditable(false);
    actionResultArea.setOpaque(false);

    JSplitPane leftHalf = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(teamMembersArea), new JScrollPane(actionResultArea));
    leftHalf.setDividerLocation(2.0 / 3.0);

    add(leftHalf);

    // Right half setup
    JPanel rightUpperPanel = new JPanel();
    rightUpperPanel.setLayout(new GridLayout(5, 1));
    rightUpperPanel.add(new JLabel("Please select an action:"));

    JButton buildTeamBtn = new JButton("Build a Team");
    buildTeamBtn.addActionListener(e -> showBuildTeamForm());

    JButton addPlayerBtn = new JButton("Add a Player");
    addPlayerBtn.addActionListener(e -> showAddPlayerForm());

    JButton removePlayerBtn = new JButton("Remove a Player");
    removePlayerBtn.addActionListener(e -> showRemovePlayerForm());

    JButton selectLineupBtn = new JButton("Select Starting Lineup");
    selectLineupBtn.addActionListener(e -> {
      rightLowerPanel.removeAll();
      repaint();
      controller.selectStartingLineup();
    });

    rightUpperPanel.add(buildTeamBtn);
    rightUpperPanel.add(addPlayerBtn);
    rightUpperPanel.add(removePlayerBtn);
    rightUpperPanel.add(selectLineupBtn);

    rightLowerPanel = new JPanel();

    JSplitPane rightHalf = new JSplitPane(JSplitPane.VERTICAL_SPLIT, rightUpperPanel, rightLowerPanel);
    add(rightHalf);

    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setVisible(true);
  }

  @Override
  public void setController(IController controller) {
    this.controller = controller;
  }

  @Override
  public void showBuildTeamForm() {
    rightLowerPanel.removeAll();
    rightLowerPanel.setLayout(new FlowLayout());

    JTextField teamNameField = new JTextField(20);
    JButton createTeamBtn = new JButton("Create Team");
    createTeamBtn.addActionListener(e -> {
      controller.buildTeam(teamNameField.getText());
    });

    rightLowerPanel.add(new JLabel("Team Name:"));
    rightLowerPanel.add(teamNameField);
    rightLowerPanel.add(createTeamBtn);

    revalidate();
    repaint();
  }

  @Override
  public void showAddPlayerForm() {
    rightLowerPanel.removeAll();

    JLabel firstNameLabel = new JLabel("First Name:");
    JTextField firstNameField = new JTextField(15);

    JLabel lastNameLabel = new JLabel("Last Name:");
    JTextField lastNameField = new JTextField(15);

    JLabel birthDayLabel = new JLabel("Date of Birth (Day):");
    JComboBox<Integer> birthDayDropdown = new JComboBox<>();
    for (int i = 1; i <= 31; i++) {
      birthDayDropdown.addItem(i);
    }

    JLabel birthMonthLabel = new JLabel("Date of Birth (Month):");
    JComboBox<Integer> birthMonthDropdown = new JComboBox<>();
    for (int i = 1; i <= 12; i++) {
      birthMonthDropdown.addItem(i);
    };

    JLabel birthYearLabel = new JLabel("Date of Birth (Year):");
    JComboBox<Integer> birthYearDropdown = new JComboBox<>();
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    for (int i = currentYear; i >= currentYear - 100; i--) { // For the last 100 years
      birthYearDropdown.addItem(i);
    }

    JLabel positionLabel = new JLabel("Position:");
    JComboBox<Position> positionDropdown = new JComboBox<>(Position.values());

    JLabel skillLevelLabel = new JLabel("Skill Level:");
    JComboBox<Integer> skillLevelDropdown = new JComboBox<>();
    for (int i = 1; i <= 5; i++) {
      skillLevelDropdown.addItem(i);
    }

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        Integer day = (Integer) birthDayDropdown.getSelectedIndex() + 1; // +1 because index is 0-based
        Integer month = (Integer) birthMonthDropdown.getSelectedItem();
        String year = birthYearDropdown.getSelectedItem().toString();
        String position = positionDropdown.getSelectedItem().toString();
        int skillLevel = (int) skillLevelDropdown.getSelectedItem();
        String dateOfBirth = year + "-" + String.format("%02d", month)+ "-" + String.format("%02d", day);

        controller.addPlayer(firstName, lastName, dateOfBirth, position, skillLevel);
      }
    });
      rightLowerPanel.add(firstNameLabel);
      rightLowerPanel.add(firstNameField);
      rightLowerPanel.add(lastNameLabel);
      rightLowerPanel.add(lastNameField);
      rightLowerPanel.add(birthDayLabel);
      rightLowerPanel.add(birthDayDropdown);
      rightLowerPanel.add(birthMonthLabel);
      rightLowerPanel.add(birthMonthDropdown);
      rightLowerPanel.add(birthYearLabel);
      rightLowerPanel.add(birthYearDropdown);
      rightLowerPanel.add(positionLabel);
      rightLowerPanel.add(positionDropdown);
      rightLowerPanel.add(skillLevelLabel);
      rightLowerPanel.add(skillLevelDropdown);
      rightLowerPanel.add(submitButton);
      rightLowerPanel.revalidate();
      rightLowerPanel.repaint();
    }


  @Override
  public void showRemovePlayerForm() {
    rightLowerPanel.removeAll();
    rightLowerPanel.setLayout(new FlowLayout());

    JTextField jerseyNumberField = new JTextField(10);
    JButton removePlayerBtn = new JButton("Remove Player");
    removePlayerBtn.addActionListener(e -> controller.removePlayer(Integer.parseInt(jerseyNumberField.getText())));

    rightLowerPanel.add(new JLabel("Jersey Number:"));
    rightLowerPanel.add(jerseyNumberField);
    rightLowerPanel.add(removePlayerBtn);

    revalidate();
    repaint();
  }

  @Override
  public void showTeam(String allPlayers, String startingLineup) {
    teamMembersArea.setText(allPlayers + "\n\n" + startingLineup);
  }

  @Override
  public void showActionResult(String result) {
    actionResultArea.setText(result);
  }

  @Override
  // Method to update the lower part of the left half of the GUI with action results.
  public void updateActionResult(String message) {
    actionResultArea.setText(message);
  }

  @Override
  // Method to update the upper part of the left half of the GUI with team members and starting lineup.
  public void updateTeamMembers(String allPlayers, String startingLineup) {
    teamMembersArea.setText(allPlayers + "\n\n" + startingLineup);
  }
}
