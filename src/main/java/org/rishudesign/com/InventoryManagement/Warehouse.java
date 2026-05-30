package org.rishudesign.com.InventoryManagement;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Warehouse {
    private int id;
    private  String city;
    private int capacity;
    private Map<Integer,Inventory>inventoryMap = new ConcurrentHashMap<>();

   public Warehouse(){

   }

   public Inventory getInventory(int productId){
       return this.inventoryMap.get(productId);
   }

    public void addProduct(Product product, int qty) {

        inventoryMap.putIfAbsent(
                product.getId(),
                new Inventory(product, qty)
        );
    }


    public Map<Integer, Inventory> getInventoryMap() {
        return inventoryMap;
    }





}
