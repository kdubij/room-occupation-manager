package org.kdubij.roommanager.occupation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class OccupationRequest {
    private int freePremiumRooms;
    private int freeEconomyRooms;
}
