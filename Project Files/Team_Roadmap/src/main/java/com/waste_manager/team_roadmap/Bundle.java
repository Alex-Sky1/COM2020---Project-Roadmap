package com.waste_manager.team_roadmap;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.io.Serializable;

@Entity
public class Bundle implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
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
    LocalDateTime timeStamp;
    @Column(nullable = false)
    private int discount;
    @Column(nullable = false)
    private int pickUpWindow;
    @Column(nullable = false)
    private boolean reserved;
    @Column(nullable = false)
    private boolean expired;

    public Bundle() {}
    public Bundle(Seller thisSeller, String thisCategory, ArrayList<String> thisContents,
                  ArrayList<String> thisAllergens, int thisQuantity, LocalDateTime thisTimeStamp, float thisPrice, int thisDiscount, int thisPickUpWindow,
                  boolean thisReserved, boolean thisExpired){

        this.seller = thisSeller;
        this.category = thisCategory;
        this.contents = thisContents;
        this.allergens = thisAllergens;
        this.quantity = thisQuantity;
        this.timeStamp = thisTimeStamp;
        this.price = thisPrice;
        this.discount = thisDiscount;
        this.pickUpWindow = thisPickUpWindow;
        this.reserved = thisReserved;
        this.expired = thisExpired;
    }


    Bundle getBundle(){
        return this;
    }

    public String getContentsAsString(){
        StringBuilder contentsString = new StringBuilder();
        if(this.contents.isEmpty()){
            return "";
        }
        for (String content : this.contents){
            contentsString.append(content).append(",");
        }
        contentsString.deleteCharAt(contentsString.length() - 1);
        return contentsString.toString();
    }

    public String getAllergensAsString(){
        StringBuilder allergensString = new StringBuilder();
        if (this.allergens.isEmpty()){
            return "";
        }
        for (String allergen : this.allergens){
            allergensString.append(allergen).append(",");
        }
        allergensString.deleteCharAt(allergensString.length() - 1);

        return allergensString.toString();
    }

    public String getPickUpWindowAsString(){
        return pickUpWindow + ":00";
    }
    public int getPostingID(){
        return ID;
    }
    public void setPostingID(int postingID){
        this.ID = postingID;
    }

    public Seller getSeller(){
        return this.seller;
    }
    public void setSeller(Seller seller){
        this.seller = seller;
    }

    public String getCategory(){
        return category;
    }
    public void setCategory(String category){
        this.category = category;
    }

    public ArrayList<String> getContents(){
        return this.contents;
    }
    public void setContents(ArrayList<String> contents){
        this.contents = contents;
    }

    public ArrayList<String> getAllergens(){
        return this.allergens;
    }
    public void setAllergens(ArrayList<String> allergens){
        this.allergens = allergens;
    }

    public int getQuantity(){
        return quantity;
    }
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public float getPrice(){
        return price;
    }
    public void setPrice(float price){
        this.price = price;
    }

    public int getPickUpWindow(){
        return pickUpWindow;
    }
    public void setPickUpWindow(int pickUpWindow){
        this.pickUpWindow = pickUpWindow;
    }

    public boolean getReserved(){
        return reserved;
    }
    public void setReserved(boolean reserved){
        this.reserved = reserved;
    }

    public boolean getExpired(){
        return expired;
    }
    public void setExpired(boolean expired){
        this.expired = expired;
    }

    public int getDiscount(){
        return discount;
    }
    public void setDiscount(int discount){
        this.discount = discount;
    }

}
