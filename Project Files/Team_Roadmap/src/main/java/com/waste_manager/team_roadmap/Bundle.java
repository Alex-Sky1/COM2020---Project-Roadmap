package com.waste_manager.team_roadmap;

public class Bundle {
    private int postingID;
    private int sellerID;
    private String category;
    private String[] contents;
    private String[] allergens;
    private int quantity;
    private float price;
    private int discount;
    private int pickUpWindow;
    private boolean reserved;
    private boolean expired;

    public Bundle(){
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

    int getSellerID(){
        return sellerID;
    }
    void setSellerID(int sellerID){
        this.sellerID = sellerID;
    }

    String getCategory(){
        return category;
    }
    void setCategory(String category){
        this.category = category;
    }

    String[] getContents(){
        return contents;
    }
    void setContents(String[] contents){
        this.contents = contents;
    }

    String[] getAllergens(){
        return allergens;
    }
    void setAllergens(String[] allergens){
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
