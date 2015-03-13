package org.rmaftei.gnugowrapper

import spock.lang.Specification

/**
 * Created by rmaftei
 */
class GnuGoWrapper_ASCII_Test extends Specification {
    def TIMEOUT = 2L
    def MODE = GnuGoMode.ASCII
    def BOARDSIZE = Boardsize.SMALL
    def PATH = 'gnugo'

    void 'build wrapper'() {
        given:
            def gnugo = new GnuGoWrapper.Builder(PATH, MODE).boardsize(BOARDSIZE).build()
        when:
            gnugo.start()
        then:
            gnugo.live()
            gnugo.stop()
    }

    void 'getting the output'() {
        given:
            GnuGoWrapper gnugo = new GnuGoWrapper.Builder(PATH, MODE).boardsize(BOARDSIZE).build();
        when:
            gnugo.start()
        then:
            Thread.sleep(1000L)
            !gnugo.getOutputAsString(TIMEOUT).isEmpty()
    }

    void 'making a move'() {
        given:
            GnuGoWrapper gnugo = new GnuGoWrapper.Builder(PATH, MODE).boardsize(BOARDSIZE).build();
        when:
            gnugo.start()
        and:
            gnugo.live()
        and:
            gnugo.sendCommand(Command.PLAY_BLACK, 'C3')
        then:
            Thread.sleep(1000L)
            gnugo.getOutputAsString(TIMEOUT).contains('X')
    }
}
