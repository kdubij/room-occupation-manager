package org.kdubij.roommanager.guests;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class GuestRetrieverConfig {
    @Bean
    PotentialGuestRetriever potentialGuestRetriever() {
        return new MockedPotentialGuestRetriever();
    }
}
