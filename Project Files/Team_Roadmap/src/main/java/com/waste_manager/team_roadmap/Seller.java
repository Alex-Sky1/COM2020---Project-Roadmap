package com.waste_manager.team_roadmap;

public class Seller {
    private int sellerID;
    private String name;
    private int[] location;
    private int[] openingHours;
    private String contactStub;

    public Seller() {}

    public boolean login(String username, String password){return true;}
    public boolean signup(String fname, String lname, String businessName, String email, String phone, String password, String address1, String postcode, String county){return true;}
    public void createBundle(String category, String[] contents, float price, String[] allergens, int quantity, int discountLevel, int pickupWindow){}
    public Bundle[] viewCurrentBundles(){return null;}
    public Bundle[] viewAllBundles(){return null;}
    public Forecast viewForecast(){return null;}
    public boolean verifyClaimCode(String code){return true;}
    public void changeReservationStatus(){}
    public void issueResponse(int issueID, String Response, boolean fix){}
    public int[] calculateSellThrough(){return null;}
    public int calculateWasteProxy(){return 0;}
    public int[] calculatePricingEffectiveness(){return null;}
    public String[] calculateOperationalInsights(){return null;}
    public void recordActionTaken(){}


    public int getSellerID(){return sellerID;}
    public void setSellerID(int sellerID){this.sellerID = sellerID;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public int[] getLocation(){return location;}
    public void setLocation(int[] location){this.location = location;}

    public int[] getOpeningHours(){return openingHours;}
    public void setOpeningHours(int[] openingHours){this.openingHours = openingHours;}

    public String getContactStub(){return contactStub;}
    public void setContactStub(String contactStub){this.contactStub = contactStub;}

}
