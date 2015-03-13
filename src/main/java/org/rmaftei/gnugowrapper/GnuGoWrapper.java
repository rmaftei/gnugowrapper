package org.rmaftei.gnugowrapper;

import org.rmaftei.gnugowrapper.exceptions.GnuGOWrapperException;
import org.rmaftei.gnugowrapper.io.Output;

import java.io.*;
import java.util.*;

/**
 * Created by rmaftei
 */
public class GnuGoWrapper {
    private static final String MODE = "--mode";

    private static final String BOARDSIZE = "--boardsize";

    private static final String LEVEL = "--level";

    private static final String KOMI = "--komi";

    private Process gnugoProcess = null;

    private Output output = null;

    private BufferedWriter bw = null;

    private List<String> command;

    private Long handicap = null;

    private final static Map<Long, Command> handicapSizes = new LinkedHashMap<Long, Command>();

    static {
        handicapSizes.put(2L, Command.HANDICAP_2);
        handicapSizes.put(3L, Command.HANDICAP_3);
        handicapSizes.put(4L, Command.HANDICAP_4);
        handicapSizes.put(5L, Command.HANDICAP_5);
        handicapSizes.put(6L, Command.HANDICAP_6);
        handicapSizes.put(7L, Command.HANDICAP_7);
        handicapSizes.put(8L, Command.HANDICAP_8);
        handicapSizes.put(9L, Command.HANDICAP_9);
    }

    private GnuGoWrapper(Builder builder) {
        command = new ArrayList<String>();
        command.add(builder.path);
        command.add(MODE);
        command.add(builder.mode.toString());

        if(builder.boardsize != null) {
            command.add(BOARDSIZE);
            command.add(String.valueOf(builder.boardsize.getSize()));
        }

        if(builder.level != null) {
            command.add(LEVEL);
            command.add(String.valueOf(builder.level));
        }
        if(builder.handicap != null) {
            this.handicap = builder.handicap;
        }
        if(builder.komi != null) {
            command.add(KOMI);
            command.add(String.valueOf(builder.komi));
        }
    }

    public void start() throws GnuGOWrapperException{
        try {
            gnugoProcess = new ProcessBuilder(command).start();
            output = new Output(gnugoProcess.getInputStream());
            bw = new BufferedWriter(new OutputStreamWriter(gnugoProcess.getOutputStream()));

            if(this.handicap != null) {
                Command handicap = handicapSizes.get(this.handicap);

                if(handicap != null) {
                    sendCommand(handicap);
                }
            }

            new Thread(output).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Builder {

        private String path;
        private GnuGoMode mode;
        private Boardsize boardsize;
        private Long level;
        private Long handicap;
        private Double komi;

        public Builder(String path, GnuGoMode mode) {
            this.path = path;
            this.mode = mode;
        }

        public Builder boardsize(Boardsize boardsize) {
            this.boardsize = boardsize;
            return this;
        }

        public Builder level(Long level) {
            this.level = level;
            return this;
        }

        public Builder handicap(Long handicap) {
            this.handicap = handicap;
            return this;
        }

        public Builder komi(Double komi) {
            this.komi = komi;
            return this;
        }

        public GnuGoWrapper build() {
            return new GnuGoWrapper(this);
        }
    }

    public boolean live() {
        return gnugoProcess != null;
    }

    /**
     *
     * @param timeout Timeout in seconds
     * @return The output as a list of strings
     */
    public List<String> getOutput(long timeout) {
        return output.getOutput(timeout);
    }

    /**
     *
     * @param timeout Timeout in seconds
     * @return The output as a string
     */
    public String getOutputAsString(long timeout) {
        StringBuilder outputAsString = new StringBuilder();

        for(String str : getOutput(timeout)) {
            outputAsString.append(str);
            outputAsString.append("\n");
        }

        return outputAsString.toString();
    }

    public void sendCommand(Command command) {
        try {
            bw.write(command.toString());
            bw.write("\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(Command command, String arg) {
        String cmd = new StringBuilder(command.toString()).append(" ").append(arg).toString();

        try {
            bw.write(cmd);
            bw.write("\n");
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            output.stop();
            gnugoProcess.getInputStream().close();
            gnugoProcess.getOutputStream().close();
            gnugoProcess.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
