package org.kdubij.roommanager.occupation

import org.kdubij.roommanager.guests.MockedPotentialGuestRetriever
import org.kdubij.roommanager.occupation.RoomOccupationService
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class RoomOccupationServiceTest extends Specification {

    @Subject
    def service = new RoomOccupationService(new MockedPotentialGuestRetriever())

    @Unroll
    def "Should pass scenario #scenarioNumber"() {
        given:
        def premiumRooms = givenPremiumRooms
        def economyRooms = givenEconomyRooms

        when:
        def result = service.occupy(premiumRooms, economyRooms)

        then:
        verifyAll(result) {
            premiumRoomUsage == expectedPremiumRooms
            premiumRoomPrice == expectedPremiumPrice
            economyRoomUsage == expectedEconomyRooms
            economyRoomPrice == expectedEconomyPrice
        }

        where:
        scenarioNumber | givenPremiumRooms | givenEconomyRooms | expectedPremiumRooms | expectedEconomyRooms | expectedPremiumPrice | expectedEconomyPrice
        1              | 3                 | 3                 | 3                    | 3                    | 738                  | 167.99
        2              | 7                 | 5                 | 6                    | 4                    | 1054                 | 189.99
        3              | 2                 | 7                 | 2                    | 4                    | 583                  | 189.99
        4              | 7                 | 1                 | 7                    | 1                    | 1153.99              | 45.00
        5              | 10                | 0                 | 10                   | 0                    | 1243.99              | 0
        6              | 0                 | 10                | 0                    | 4                    | 0                    | 189.99
        7              | 11                | 0                 | 10                   | 0                    | 1243.99              | 0
        8              | 0                 | 11                | 0                    | 4                    | 0                    | 189.99
    }

}
