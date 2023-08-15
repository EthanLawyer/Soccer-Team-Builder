package soccerteam;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import javax.swing.*;
import java.awt.*;

public class SoccerTeamView extends JFrame implements IView{
  private final JTextArea teamDisplayArea;
  private final JTextArea actionResultArea;
  private JPanel inputPanel;
  private IController controller;

  public SoccerTeamView(String title) {
    super(title);

    setLayout(new GridLayout(1, 2));
    setSize(1600, 1200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    getContentPane().setBackground(Color.LIGHT_GRAY);

    // Left half setup
    teamDisplayArea = new JTextArea();
    teamDisplayArea.setEditable(false);
    teamDisplayArea.setOpaque(false);

    actionResultArea = new JTextArea();
    actionResultArea.setEditable(false);
    actionResultArea.setOpaque(false);

    JSplitPane leftHalf = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(teamDisplayArea), new JScrollPane(actionResultArea));

    //leftHalf.setResizeWeight(0.0);
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
      inputPanel.removeAll();
      repaint();
      controller.selectStartingLineup();
    });

    rightUpperPanel.add(buildTeamBtn);
    rightUpperPanel.add(addPlayerBtn);
    rightUpperPanel.add(removePlayerBtn);
    rightUpperPanel.add(selectLineupBtn);

    inputPanel = new JPanel();

    JSplitPane rightHalf = new JSplitPane(JSplitPane.VERTICAL_SPLIT, rightUpperPanel, inputPanel);
    add(rightHalf);

    //pack();
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setVisible(true);
    leftHalf.setDividerLocation(0.80);
  }

  @Override
  public void setController(IController controller) {
    this.controller = controller;
  }

  @Override
  public void showBuildTeamForm() {
    inputPanel.removeAll();
    inputPanel.setLayout(new FlowLayout());

    JTextField teamNameField = new JTextField(20);
    JButton createTeamBtn = new JButton("Create Team");
    createTeamBtn.addActionListener(e -> {
      controller.buildTeam(teamNameField.getText());
    });

    inputPanel.add(new JLabel("Team Name:"));
    inputPanel.add(teamNameField);
    inputPanel.add(createTeamBtn);

    revalidate();
    repaint();
  }

  @Override
  public void showAddPlayerForm() {
    inputPanel.removeAll();

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
    }

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
        Integer day = birthDayDropdown.getSelectedIndex() + 1; // +1 because index is 0-based
        Integer month = (Integer) birthMonthDropdown.getSelectedItem();
        String year = birthYearDropdown.getSelectedItem().toString();
        String position = positionDropdown.getSelectedItem().toString();
        int skillLevel = (int) skillLevelDropdown.getSelectedItem();
        String dateOfBirth = year + "-" + String.format("%02d", month)+ "-" + String.format("%02d", day);

        controller.addPlayer(firstName, lastName, dateOfBirth, position, skillLevel);
      }
    });
      inputPanel.add(firstNameLabel);
      inputPanel.add(firstNameField);
      inputPanel.add(lastNameLabel);
      inputPanel.add(lastNameField);
      inputPanel.add(birthDayLabel);
      inputPanel.add(birthDayDropdown);
      inputPanel.add(birthMonthLabel);
      inputPanel.add(birthMonthDropdown);
      inputPanel.add(birthYearLabel);
      inputPanel.add(birthYearDropdown);
      inputPanel.add(positionLabel);
      inputPanel.add(positionDropdown);
      inputPanel.add(skillLevelLabel);
      inputPanel.add(skillLevelDropdown);
      inputPanel.add(submitButton);
      inputPanel.revalidate();
      inputPanel.repaint();
    }


  @Override
  public void showRemovePlayerForm() {
    inputPanel.removeAll();
    inputPanel.setLayout(new FlowLayout());

    JTextField jerseyNumberField = new JTextField(10);
    JButton removePlayerBtn = new JButton("Remove Player");
    removePlayerBtn.addActionListener(e -> controller.removePlayer(Integer.parseInt(jerseyNumberField.getText())));

    inputPanel.add(new JLabel("Jersey Number:"));
    inputPanel.add(jerseyNumberField);
    inputPanel.add(removePlayerBtn);

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
