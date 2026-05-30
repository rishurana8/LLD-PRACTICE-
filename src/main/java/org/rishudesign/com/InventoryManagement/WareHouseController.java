//package org.rishudesign.com.InventoryManagement;
//import org.rishudesign.com.MovieBooking.enums.SeatStatus;
//
//import java.util.*;
//import java.util.concurrent.locks.ReentrantLock;
//
//import static org.rishudesign.com.InventoryManagement.ReservationStatus.RESERVED;
//
//public class WareHouseController {
//    private WareHouse warehouse;
//    private Map<Integer, ReentrantLock>  locks= new HashMap<>();
//    private final Map<Integer, ReservationStatus>seatStatusMap = new HashMap<>();
//
//    public WareHouseController(WareHouse warehouse){
//        this.warehouse = warehouse;
//        for(Inventory inv : warehouse.getInventory()){
//             locks.put(inv.getProductId(),new ReentrantLock());
//        }
//    }
//
//    public void addItem(int product,int Quantity){
//        warehouse.addItem(product,Quantity);
//    }
//
//    public void removeItem(int productId){
//        warehouse.removeItem(productId);
//    }
//
//    public boolean lockProducts(List<Map.Entry<Integer,Integer>>prodDetails){
//        // Fix: sort by productId (key) using comparator since Map.Entry isn't Comparable
//        List<Map.Entry<Integer, Integer>> sorted = new ArrayList<>(prodDetails);
//        sorted.sort(Comparator.comparingInt(Map.Entry::getKey));
//
//        List<ReentrantLock> acquiredLocks = new ArrayList<>();
//
//        try{
//            for(Map.Entry<Integer,Integer>mp: sorted){
//                int prodId = mp.getKey();
//                int quantity = mp.getValue();
//
//                ReentrantLock lock = locks.get(prodId);
//                lock.lock();
//                acquiredLocks.add(lock);
//            }
//
//          for(Map.Entry<Integer,Integer>mp: sorted){
//              int prodId = mp.getKey();
//              int qt = mp.getValue();
//
//             if(seatStatusMap.get(prodId)!=ReservationStatus.AVAILABLE ){
//                 return false;
//             }
//
//
//          }
//
//            for(Map.Entry<Integer,Integer>mp: sorted){
//                int prodId = mp.getKey();
//                int qt = mp.getValue();
//
//                seatStatusMap.put(prodId,RESERVED);
//            }
//            return true;
//
//        }finally{
//            for(ReentrantLock lock: acquiredLocks){
//                lock.unlock();
//            }
//        }
//
//    }
//
//    public void confirmBooking(){
//
//    }
//}
