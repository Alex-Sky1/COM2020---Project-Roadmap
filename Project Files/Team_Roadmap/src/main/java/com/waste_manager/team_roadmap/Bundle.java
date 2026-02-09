package com.waste_manager.team_roadmap;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.io.Serializable;

@Entity
public class Bundle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postingID;
    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id", referencedColumnName = "ID")
    private Seller seller;
    @Column(nullable = false)
    private String category;
    private ArrayList<String> contents;
    private ArrayList<String> allergens;
    private int quantity;
    @Column(nullable = false)
    private float price;
    @Column(nullable = false)
    private int discount;
    @Column(nullable = false)
    private int pickUpWindow;
    @Column(nullable = false)
    private boolean reserved;
    @Column(nullable = false)
    private boolean expired;

    public Bundle(Seller thisSeller, String thisCategory, ArrayList<String> thisContents,
                  ArrayList<String> thisAllergens, int thisQuantity, float thisPrice, int thisDiscount, int thisPickUpWindow,
                  boolean thisReserved, boolean thisExpired){

        this.seller = thisSeller;
        this.category = thisCategory;
        this.contents = thisContents;
        this.allergens = thisAllergens;
        this.quantity = thisQuantity;
        this.price = thisPrice;
        this.discount = thisDiscount;
        this.pickUpWindow = thisPickUpWindow;
        this.reserved = thisReserved;
        this.expired = thisExpired;
    }


    Bundle getBundle(){
        return this;
    }


    int getPostingID(){
        return postingID;
    }

    void setPostingID(int postingID){
        this.postingID = postingID;
    }

    Seller getSeller(){
        return this.seller;
    }

    void setSeller(Seller seller){
        this.seller = seller;
    }

    String getCategory(){
        return category;
    }
    void setCategory(String category){
        this.category = category;
    }

    ArrayList<String> getContents(){
        return this.contents;
    }
    void setContents(ArrayList<String> contents){
        this.contents = contents;
    }

    ArrayList<String> getAllergens(){
        return this.allergens;
    }
    void setAllergens(ArrayList<String> allergens){
        this.allergens = allergens;
    }

    int getQuantity(){
        return quantity;
    }
    void setQuantity(int quantity){
        this.quantity = quantity;
    }

    float getPrice(){
        return price;
    }
    void setPrice(float price){
        this.price = price;
    }

    int getPickUpWindow(){
        return pickUpWindow;
    }
    void setPickUpWindow(int pickUpWindow){
        this.pickUpWindow = pickUpWindow;
    }

    boolean getReserved(){
        return reserved;
    }
    void setReserved(boolean reserved){
        this.reserved = reserved;
    }

    boolean getExpired(){
        return expired;
    }
    void setExpired(boolean expired){
        this.expired = expired;
    }
    int getDiscount(){
        return discount;
    }
    void setDiscount(int discount){
        this.discount = discount;
    }

}
