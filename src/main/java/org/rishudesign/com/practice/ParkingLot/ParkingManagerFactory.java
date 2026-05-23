package org.rishudesign.com.practice.ParkingLot;


public class ParkingManagerFactory {
    private final ParkingSpotManager
            twoWheelerManager;

    private final ParkingSpotManager
            fourWheelerManager;

    public ParkingManagerFactory(
            ParkingSpotManager two,
            ParkingSpotManager four
    ) {

        this.twoWheelerManager = two;
        this.fourWheelerManager = four;
    }

    public ParkingSpotManager getManager(
            VechileType type
    ) {

        switch (type) {

            case TWO_WHEELER:
                return twoWheelerManager;

            case FOUR_WHEELER:
                return fourWheelerManager;

            default:
                throw new IllegalArgumentException();
        }
    }
}
