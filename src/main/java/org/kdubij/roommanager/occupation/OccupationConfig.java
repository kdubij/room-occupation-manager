package org.kdubij.roommanager.occupation;

import org.kdubij.roommanager.guests.PotentialGuestRetriever;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OccupationConfig {
    @Bean
    RoomOccupationService roomOccupationService(PotentialGuestRetriever potentialGuestRetriever) {
        return new RoomOccupationService(potentialGuestRetriever);
    }
}
