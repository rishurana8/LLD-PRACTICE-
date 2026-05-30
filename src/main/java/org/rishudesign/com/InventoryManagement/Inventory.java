package org.rishudesign.com.InventoryManagement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    private Product product;
    private int availableQuantity;
    private int reservedQuantity;


    public Inventory(Product p,int quantity){

        this.product  = p;
        this.availableQuantity = quantity;
        this.reservedQuantity = 0;
    }

    public int getProductId(){
        return this.product.getId();
    }

    public Product getProduct(){
        return this.product;
    }


    public int getAvailableQuantity(){
        return this.availableQuantity;
    }

    public int getReservedQuantity(){
        return this.reservedQuantity;
    }

    public boolean reserve(int qty){
        if(availableQuantity<qty){
            return false;
        }
        availableQuantity-=qty;
        reservedQuantity+=qty;
        return true;
    }

    public void release(int qty) {

        if (reservedQuantity < qty) {
            throw new RuntimeException("Invalid release quantity");
        }

        reservedQuantity -= qty;
        availableQuantity += qty;
    }

    public void confirm(int qty) {

        if (reservedQuantity < qty) {
            throw new RuntimeException("Invalid confirm quantity");
        }

        reservedQuantity -= qty;
    }
}
