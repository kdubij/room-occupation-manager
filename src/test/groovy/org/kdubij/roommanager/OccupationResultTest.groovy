package org.kdubij.roommanager

import spock.lang.Specification

class OccupationResultTest extends Specification {

    def "Occupation result should be created correctly for empty lists"() {
        given:
        def premiumPrices = []
        def economyPrices = []

        when:
        def result = new OccupationResult(premiumPrices, economyPrices)

        then:
        verifyAll(result) {
            premiumRoomUsage == 0
            premiumRoomPrice == 0
            economyRoomUsage == 0
            economyRoomPrice == 0
        }
    }

    def "Occupation result should be created correctly"() {
        given:
        def premiumPrices = [1d, 1d, 1d]
        def economyPrices = [2d, 2d]

        when:
        def result = new OccupationResult(premiumPrices, economyPrices)

        then:
        verifyAll(result) {
            premiumRoomUsage == 3
            premiumRoomPrice == 3
            economyRoomUsage == 2
            economyRoomPrice == 4
        }
    }
}
