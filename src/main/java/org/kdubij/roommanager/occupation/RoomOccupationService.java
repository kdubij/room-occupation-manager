package org.kdubij.roommanager.occupation;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kdubij.roommanager.guests.PotentialGuestRetriever;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
class RoomOccupationService {
    private static final int MIN_PREMIUM_ROOM_PRICE = 100;
    private final PotentialGuestRetriever potentialGuestRetriever;

    OccupationResult occupy(Integer freePremiumRooms, Integer freeEconomyRooms) {
        var sortedPotentialGuests = retrieveSortedPotentialGuests();
        var occupiedPremiumRoomsGuests = findPremiumRoomsGuests(freePremiumRooms, sortedPotentialGuests);
        var potentialEconomyRoomsGuests = findPotentialEconomyRoomGuests(sortedPotentialGuests);

        if (isPreferenceUpgradePossible(freePremiumRooms, freeEconomyRooms, occupiedPremiumRoomsGuests.size(), potentialEconomyRoomsGuests.size())) {
            doPreferenceUpgrade(freePremiumRooms, occupiedPremiumRoomsGuests, potentialEconomyRoomsGuests);
        }
        var economyRoomsGuests = occupyFreeRooms(freeEconomyRooms, potentialEconomyRoomsGuests);

        return new OccupationResult(occupiedPremiumRoomsGuests, economyRoomsGuests);
    }

    private List<Double> retrieveSortedPotentialGuests() {
        var potentialGuests = potentialGuestRetriever.retrieve();
        log.info("[OCCUPATION] Retrieved potential guests {potentialGuests={}}", potentialGuests);
        return potentialGuests
                .stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private List<Double> occupyFreeRooms(Integer economyRooms, List<Double> potentialEconomyGuests) {
        return potentialEconomyGuests.stream()
                .limit(economyRooms)
                .collect(Collectors.toList());
    }

    private void doPreferenceUpgrade(Integer premiumRooms, List<Double> occupiedPremiumRooms, List<Double> potentialEconomyGuests) {
        var freePremiumRooms = premiumRooms - occupiedPremiumRooms.size();
        var upgradedRooms = occupyFreeRooms(freePremiumRooms, potentialEconomyGuests);
        log.info("[OCCUPATION] Preference upgrade done {upgradedRooms={}}", upgradedRooms);
        potentialEconomyGuests.removeAll(upgradedRooms);
        occupiedPremiumRooms.addAll(upgradedRooms);
    }

    private List<Double> findPotentialEconomyRoomGuests(List<Double> sortedPotentialGuests) {
        return sortedPotentialGuests.stream()
                .filter(Predicate.not(this::isPremiumRoomPriceReached))
                .collect(Collectors.toList());
    }

    private List<Double> findPremiumRoomsGuests(Integer premiumRooms, List<Double> sortedPotentialGuests) {
        return sortedPotentialGuests.stream()
                .filter(this::isPremiumRoomPriceReached)
                .limit(premiumRooms)
                .collect(Collectors.toList());
    }

    private boolean isPremiumRoomPriceReached(Double guestPrice) {
        return guestPrice >= MIN_PREMIUM_ROOM_PRICE;
    }

    private boolean isPreferenceUpgradePossible(int premiumRooms, int economyRooms, int premiumNormalPriceGuests, int potentialEconomyGuests) {
        return areAllEconomyRoomsOccupied(economyRooms, potentialEconomyGuests) && areAnyPremiumRoomsFree(premiumRooms, premiumNormalPriceGuests);
    }

    private boolean areAnyPremiumRoomsFree(int premiumRooms, int occupiedPremiumRooms) {
        return occupiedPremiumRooms < premiumRooms;
    }

    private boolean areAllEconomyRoomsOccupied(int economyRooms, int potentialEconomyGuests) {
        return potentialEconomyGuests > economyRooms;
    }
}
