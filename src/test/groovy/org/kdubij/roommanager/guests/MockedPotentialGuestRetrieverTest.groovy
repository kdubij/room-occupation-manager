package org.kdubij.roommanager.guests

import spock.lang.Specification
import spock.lang.Subject

class MockedPotentialGuestRetrieverTest extends Specification {
    @Subject
    def guestRetriever = new MockedPotentialGuestRetriever()

    def "Should return potential guest correctly"(){
        when:
        def retrievedGuests = guestRetriever.retrieve()

        then:
        retrievedGuests == [23d, 45d, 155d, 374d, 22d, 99.99d, 100d, 101d, 115d, 209d]
    }
}
