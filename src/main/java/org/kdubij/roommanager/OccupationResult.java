package org.kdubij.roommanager;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@EqualsAndHashCode
public class OccupationResult {
    private final Integer premiumRoomUsage;
    private final Double premiumRoomPrice;
    private final Integer economyRoomUsage;
    private final Double economyRoomPrice;

    public OccupationResult(List<Double> premiumPrices, List<Double> economyPrices) {
        this.premiumRoomUsage = premiumPrices.size();
        this.premiumRoomPrice = sumPrices(premiumPrices);
        this.economyRoomUsage = economyPrices.size();
        this.economyRoomPrice = sumPrices(economyPrices);
    }

    private Double sumPrices(List<Double> economyPrices) {
        return economyPrices.stream().reduce(0d, Double::sum);
    }
}
