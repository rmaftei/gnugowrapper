package org.rmaftei.gnugowrapper

import spock.lang.Specification

class GnuGoWrapper_GTP_Test extends Specification {
    def TIMEOUT = 500L
    def MODE = GnuGoMode.GTP
    def BOARDSIZE = Boardsize.SMALL
    def PATH = 'gnugo'

    void 'build wrapper'() {
        given:
            def gnugo = new GnuGoWrapper.Builder(PATH, MODE)
                    .boardsize(BOARDSIZE)
                    .level(5)
                    .handicap(3)
                    .komi(0.5)
                    .build()
        when:
            gnugo.start()
        then:
            gnugo.live()
    }

    void 'getting the output'() {
        given:
            GnuGoWrapper gnugo = new GnuGoWrapper.Builder(PATH, MODE).boardsize(BOARDSIZE).build();
        when:
            gnugo.start()
        and:
            gnugo.sendCommand(Command.SHOWBOARD)
            Thread.sleep(1000L)
        then:
            !gnugo.getOutputAsString(TIMEOUT).isEmpty()
    }

    void 'generating a move'() {
        given:
            GnuGoWrapper gnugo = new GnuGoWrapper.Builder(PATH, MODE).boardsize(BOARDSIZE).build();
        when:
            gnugo.start()
        and:
            gnugo.live()
        and:
            gnugo.sendCommand(Command.GENERATE_BLACK_MOVE)
        and:
            gnugo.sendCommand(Command.SHOWBOARD)
            Thread.sleep(1000L)
        then:
            gnugo.getOutputAsString(TIMEOUT).contains('X')

    }
}
