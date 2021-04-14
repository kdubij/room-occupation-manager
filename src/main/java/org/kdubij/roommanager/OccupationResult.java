package org.kdubij.roommanager;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class OccupationResult {
    private Integer premiumRoomUsage;
    private Double premiumRoomPrice;
    private Integer economyRoomUsage;
    private Double economyRoomPrice;

}
