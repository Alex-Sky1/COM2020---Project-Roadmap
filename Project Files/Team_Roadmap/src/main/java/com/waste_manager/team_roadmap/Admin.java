package com.waste_manager.team_roadmap;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;
    @Column(nullable = false)
    private String dName;
    @Column(nullable = false)
    private String password;
    @OneToOne(optional = false)
    @JoinColumn(name = "seller_id", referencedColumnName = "ID")
    private Seller sellerView;
    @OneToOne(optional = false)
    @JoinColumn(name = "customer_id", referencedColumnName = "ID")
    private Customer customerView;

    public Admin() {}

    public Admin(String dName, String password, Seller sellerView, Customer customerView) {
        this.dName = dName;
        this.password = password;
        this.sellerView = sellerView;
        this.customerView = customerView;
    }

    public Long getID() {return ID;}
    public void setID(Long ID) {this.ID = ID;}

    public String getDName() {return dName;}
    public void setDName(String dName) {this.dName = dName;}

    public String getPassword() {return password;}
    public void setPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);}

    public Seller getSellerView() {return sellerView;}
    public void setSellerView(Seller sellerView) {this.sellerView = sellerView;}

    public Customer getCustomerView() {return customerView;}
    public void setCustomerView(Customer customerView) {this.customerView = customerView;}

}
