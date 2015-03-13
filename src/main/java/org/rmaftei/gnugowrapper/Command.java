package org.rmaftei.gnugowrapper;

public final class Command {
    public final static Command SHOWBOARD = new Command("showboard");
    public final static Command UNDO = new Command("undo");
    public final static Command LAST_MOVE = new Command("last_move");
    public final static Command GENERATE_WHITE_MOVE = new Command("genmove_white");
    public final static Command GENERATE_BLACK_MOVE = new Command("genmove_black");
    public final static Command FINAL_SCORE = new Command("final_score");
    public final static Command PLAY_BLACK = new Command("play black");
    public final static Command PLAY_WHITE = new Command("play white");
    public final static Command QUIT = new Command("quit");

    private final static Command HANDICAP = new Command("fixed_handicap");
    public final static Command HANDICAP_2 = new Command(HANDICAP, "2");
    public final static Command HANDICAP_3 = new Command(HANDICAP, "3");
    public final static Command HANDICAP_4 = new Command(HANDICAP, "4");
    public final static Command HANDICAP_5 = new Command(HANDICAP, "5");
    public final static Command HANDICAP_6 = new Command(HANDICAP, "6");
    public final static Command HANDICAP_7 = new Command(HANDICAP, "7");
    public final static Command HANDICAP_8 = new Command(HANDICAP, "8");
    public final static Command HANDICAP_9 = new Command(HANDICAP, "9");

    private String command;

    private Command(String command) {
        this.command = command;
    }

    private Command(Command command, String argument) {
        this.command = command.toString() + " " + argument;
    }

    public boolean isSame(String stringCommand) {
        return this.command.equals(stringCommand.toLowerCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Command command1 = (Command) o;

        if (command != null ? !command.equals(command1.command) : command1.command != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return command != null ? command.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.command;
    }
}
