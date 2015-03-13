package org.rmaftei.gnugowrapper.io;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Output implements Runnable {

    private List<String> buffer = null;

    private BufferedReader br = null;

    private boolean terminate = false;

    private CountDownLatch done = null;

    public Output(InputStream inputStream) {
        buffer = new ArrayList<String>();
        br = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                String line;
                while (true) {
                    line = br.readLine();

                    if(line == null || terminate) {
                        done.countDown();
                        break;
                    }
                    buffer.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * @param timeout Timeout in seconds
     * @return The output as a list of strings
     */
    public List<String> getOutput(long timeout) {
        done = new CountDownLatch(1);

        try {
            done.await(timeout, TimeUnit.MILLISECONDS);

            List<String> tmp = new ArrayList<String>(buffer);

            clearStringBuffer();

            return tmp;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    private void clearStringBuffer() {
        buffer.clear();
    }

    public void stop() {
        terminate = true;
    }
}
