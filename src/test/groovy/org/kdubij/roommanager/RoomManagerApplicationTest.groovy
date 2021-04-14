package org.kdubij.roommanager

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class RoomManagerApplicationTest extends Specification {

    def "Should context start"() {
        expect:
        true
    }

}
