package org.kdubij.roommanager.occupation;

import lombok.*;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class OccupationResult {
    private Integer premiumRoomUsage;
    private Double premiumRoomPrice;
    private Integer economyRoomUsage;
    private Double economyRoomPrice;

    OccupationResult(List<Double> premiumPrices, List<Double> economyPrices) {
        this.premiumRoomUsage = premiumPrices.size();
        this.premiumRoomPrice = sumPrices(premiumPrices);
        this.economyRoomUsage = economyPrices.size();
        this.economyRoomPrice = sumPrices(economyPrices);
    }

    private Double sumPrices(List<Double> economyPrices) {
        return economyPrices.stream().reduce(0d, Double::sum);
    }
}
