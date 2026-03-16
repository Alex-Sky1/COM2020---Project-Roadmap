package com.waste_manager.team_roadmap;

import jakarta.persistence.*;

@Entity
public class IssueReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long issueID;
    @ManyToOne(optional = false)
    @JoinColumn(name = "bundle_id", referencedColumnName = "ID")
    private Bundle bundle;
    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "ID")
    private Customer customer;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean resolved;
    @Column
    private String sellerResponse;

    public IssueReport(Bundle bundle, Customer customer, String thisType, String thisDescription,
                       boolean thisResolved, String thisSellerResponse){

        this.bundle = bundle;
        this.customer = customer;
        this.type = thisType;
        this.description = thisDescription;
        this.resolved = thisResolved;
        this.sellerResponse = thisSellerResponse;
    }

    public IssueReport() {

    }

    public long getIssueID() {return issueID;}
    public void setIssueID(int issueID){this.issueID = issueID;}

    public Bundle getBundle(){return bundle;}
    public void setBundle(Bundle bundle){this.bundle = bundle;}

    public Customer getCustomer(){return customer;}
    public void setCustomer(Customer customer){this.customer = customer;}

    public String getType(){return type;}
    public void setType(String type){this.type = type;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}

    public boolean getResolved(){return resolved;}
    public void setResolved(boolean resolved){this.resolved = resolved;}

    public String getSellerResponse(){return sellerResponse;}
    public void setSellerResponse(String sellerResponse){this.sellerResponse = sellerResponse;}
}
