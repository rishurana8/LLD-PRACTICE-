package org.rishudesign.com.CarRentalSystem;
import java.util.*;

public class StoreManager {
    private final List<Store> stores;

    public StoreManager(List<Store> stores) {
        this.stores = stores;
    }

    public Store findByCity(String city) {
        return stores.stream()
                .filter(s -> s.getLocation().getCity().equalsIgnoreCase(city))
                .findFirst()
                .orElse(null);
    }
}
