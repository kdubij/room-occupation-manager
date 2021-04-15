package org.kdubij.roommanager.guests;

import java.util.List;

public class MockedPotentialGuestRetriever implements PotentialGuestRetriever {
    @Override
    public List<Double> retrieve() {
        return List.of(23d, 45d, 155d, 374d, 22d, 99.99d, 100d, 101d, 115d, 209d);
    }
}
