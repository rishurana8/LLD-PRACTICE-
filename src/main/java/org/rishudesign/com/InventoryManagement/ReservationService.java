package org.rishudesign.com.InventoryManagement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ReservationService {
   private Warehouse warehouse;
   private Map<Integer, ReentrantLock>lockMap = new ConcurrentHashMap<>();
   private Map<String,Reservation> reservations = new ConcurrentHashMap<>();

   public ReservationService(Warehouse warehouse){
         this.warehouse = warehouse;

      for (Integer productId :
              warehouse.getInventoryMap().keySet()) {

         lockMap.putIfAbsent(
                 productId,
                 new ReentrantLock()
         );
      }
   }


   public Reservation reserveProducts(
           Map<Integer, Integer> productQtyMap) {

      List<Integer> productIds =
              new ArrayList<>(productQtyMap.keySet());

      Collections.sort(productIds);

      List<ReentrantLock> acquiredLocks =
              new ArrayList<>();

      try {

         // Acquire locks
         for (Integer productId : productIds) {

            lockMap.putIfAbsent(
                    productId,
                    new ReentrantLock()
            );

            ReentrantLock lock =
                    lockMap.get(productId);

            lock.lock();

            acquiredLocks.add(lock);
         }

         // Validate inventory
         for (Integer productId : productIds) {

            int qty = productQtyMap.get(productId);

            Inventory inventory =
                    warehouse.getInventory(productId);

            if (inventory == null ||
                    inventory.getAvailableQuantity() < qty) {

               return null;
            }
         }

         // Reserve inventory
         for (Integer productId : productIds) {

            int qty = productQtyMap.get(productId);

            Inventory inventory =
                    warehouse.getInventory(productId);

            boolean success =
                    inventory.reserve(qty);

         }

         String reservationId =
                 UUID.randomUUID().toString();

         Reservation reservation =
                 new Reservation(
                         reservationId,
                         ReservationStatus.RESERVED,
                         productQtyMap
                 );

         reservations.put(
                 reservationId,
                 reservation
         );

         return reservation;

      } finally {

         for (ReentrantLock lock : acquiredLocks) {
            lock.unlock();
         }
      }
   }

   public void confirmReservation(String reservationId) {

      Reservation reservation =
              reservations.get(reservationId);

      if (reservation == null) {
         throw new RuntimeException("Reservation not found");
      }

      synchronized (reservation) {

         if (reservation.getStatus()
                 != ReservationStatus.RESERVED) {

            throw new RuntimeException(
                    "Reservation already processed"
            );
         }

         for (Map.Entry<Integer, Integer> entry :
                 reservation.getReservedItems().entrySet()) {

            Inventory inventory =
                    warehouse.getInventory(entry.getKey());

            inventory.confirm(entry.getValue());
         }

         reservation.setStatus(
                 ReservationStatus.CONFIRMED
         );
      }
   }


}
