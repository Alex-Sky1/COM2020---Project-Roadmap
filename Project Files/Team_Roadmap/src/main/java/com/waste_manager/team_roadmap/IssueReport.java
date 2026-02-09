package com.waste_manager.team_roadmap;

public class IssueReport {
    private long issueID;
    private long postingID;
    private long customerID;
    private String type;
    private String description;
    private boolean fixed;
    private String sellerResponse;

    public IssueReport(long thisPostingID, long thisCustomerID, String thisType, String thisDescription,
                       boolean thisFixed, String thisSellerResponse){

        this.postingID = thisPostingID;
        this.customerID = thisCustomerID;
        this.type = thisType;
        this.description = thisDescription;
        this.fixed = thisFixed;
        this.sellerResponse = thisSellerResponse;
    }


    public long getIssueID() {return issueID;}
    public void setIssueID(int issueID){this.issueID = issueID;}

    public long getPostingID(){return postingID;}
    public void setPostingID(int postingID){this.postingID = postingID;}

    public long getCustomerID(){return customerID;}
    public void setCustomerID(int customerID){this.customerID = customerID;}

    public String getType(){return type;}
    public void setType(String type){this.type = type;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}

    public boolean getFixed(){return fixed;}
    public void setFixed(boolean fixed){this.fixed = fixed;}

    public String getSellerResponse(){return sellerResponse;}
    public void setSellerResponse(String sellerResponse){this.sellerResponse = sellerResponse;}
}
