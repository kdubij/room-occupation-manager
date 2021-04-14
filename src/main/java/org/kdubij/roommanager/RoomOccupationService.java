package org.kdubij.roommanager;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class RoomOccupationService {
    private static final int MIN_PREMIUM_ROOM_PRICE = 100;
    private final List<Double> potentialGuests = List.of(23d, 45d, 155d, 374d, 22d, 99.99d, 100d, 101d, 115d, 209d);

    OccupationResult occupy(Integer freePremiumRooms, Integer freeEconomyRooms) {
        var occupiedPremiumRoomsGuests = findPremiumRoomsGuests(freePremiumRooms);
        var potentialEconomyRoomsGuests = findPotentialEconomyRoomGuests();

        if (isPreferenceUpgradePossible(freePremiumRooms, freeEconomyRooms, occupiedPremiumRoomsGuests.size(), potentialEconomyRoomsGuests.size())) {
            doPreferenceUpgrade(freePremiumRooms, occupiedPremiumRoomsGuests, potentialEconomyRoomsGuests);
        }
        var economyRoomsGuests = occupyFreeRooms(freeEconomyRooms, potentialEconomyRoomsGuests);

        return new OccupationResult(occupiedPremiumRoomsGuests, economyRoomsGuests);
    }

    private List<Double> occupyFreeRooms(Integer economyRooms, List<Double> potentialEconomyGuests) {
        return potentialEconomyGuests.stream()
                .limit(economyRooms)
                .collect(Collectors.toList());
    }

    private void doPreferenceUpgrade(Integer premiumRooms, List<Double> occupiedPremiumRooms, List<Double> potentialEconomyGuests) {
        var freePremiumRooms = premiumRooms - occupiedPremiumRooms.size();
        var upgradedRooms = occupyFreeRooms(freePremiumRooms, potentialEconomyGuests);
        potentialEconomyGuests.removeAll(upgradedRooms);
        occupiedPremiumRooms.addAll(upgradedRooms);
    }

    private List<Double> findPotentialEconomyRoomGuests() {
        return potentialGuests.stream()
                .sorted(Comparator.reverseOrder())
                .filter(Predicate.not(this::isPremiumRoomPriceReached))
                .collect(Collectors.toList());
    }

    private List<Double> findPremiumRoomsGuests(Integer premiumRooms) {
        return potentialGuests.stream()
                .sorted(Comparator.reverseOrder())
                .filter(this::isPremiumRoomPriceReached)
                .limit(premiumRooms)
                .collect(Collectors.toList());
    }

    private boolean isPremiumRoomPriceReached(Double guestPrice) {
        return guestPrice >= MIN_PREMIUM_ROOM_PRICE;
    }

    private boolean isPreferenceUpgradePossible(Integer premiumRooms, Integer economyRooms, int premiumNormalPriceGuests, int potentialEconomyGuests) {
        return areAllEconomyRoomsOccupied(economyRooms, potentialEconomyGuests) && areAnyPremiumRoomsFree(premiumRooms, premiumNormalPriceGuests);
    }

    private boolean areAnyPremiumRoomsFree(Integer premiumRooms, int occupiedPremiumRooms) {
        return occupiedPremiumRooms < premiumRooms;
    }

    private boolean areAllEconomyRoomsOccupied(Integer economyRooms, int potentialEconomyGuests) {
        return potentialEconomyGuests > economyRooms;
    }
}
