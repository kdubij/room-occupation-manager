package org.kdubij.roommanager.occupation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OccupationControllerIT extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    def "Should successfully occupy rooms"() {
        given:
        def request = new OccupationRequest(7, 1)

        when:
        def response = restTemplate.postForEntity("/occupations", request, OccupationResult)

        then:
        response.statusCode == HttpStatus.OK
        verifyAll(response.body) {
            premiumRoomUsage == 7
            premiumRoomPrice == 1153.99
            economyRoomUsage == 1
            economyRoomPrice == 45.00
        }

    }
}
