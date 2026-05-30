    package org.rishudesign.com.InventoryManagement;

    public class Product {
        int id;
        double price;
        String Description;

        public Product(int id,String description,double price){
            this.id = id;
            this.Description = description;
            this.price = price;
        }

        public int getId(){
            return this.id;
        }

        public String getDescription() {
            return Description;
        }

        public double getPrice() {
            return price;
        }
    }
