package soccerteam;

/**
 * The position of a player.
 */
public enum Position {
  GOALIE {
    @Override
    public String toString() {
      return "Goalie";
    }
  },
  DEFENDER {
    @Override
    public String toString() {
      return "Defender";
    }
  },
  MIDFIELDER {
    @Override
    public String toString() {
      return "Midfielder";
    }
  },
  FORWARD {
    @Override
    public String toString() {
      return "Forward";
    }
  },
  BENCH {}
}
