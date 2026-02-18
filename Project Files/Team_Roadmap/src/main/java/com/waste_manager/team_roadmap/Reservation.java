package com.waste_manager.team_roadmap;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ID;
    @OneToOne
    @JoinColumn(name = "bundle_id", referencedColumnName = "ID")
    private Bundle bundle;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "ID")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "ID")
    private Seller seller;
    private LocalDateTime timeStamp;
    private LocalDateTime pickupTimeStamp;
    @Column(nullable = false)
    private String claimCode;
    @Column(nullable = false)
    private Boolean noShow;
    @Column(nullable = false)
    private Boolean collected;
    @Column(nullable = false)
    private String weatherFlag;

    public Reservation(){}
    public Reservation(Bundle bundle, Customer customer, Seller seller, LocalDateTime thisTimeStamp,
                       String thisClaimCode, boolean thisNoShow, boolean thisCollected, String thisWeatherFlag){
        this.bundle = bundle;
        this.customer = customer;
        this.seller = seller;
        this.timeStamp = thisTimeStamp;
        this.claimCode = thisClaimCode;
        this.noShow = thisNoShow;
        this.collected = thisCollected;
        this.weatherFlag = thisWeatherFlag;
    }


    public long getReservationID() {return ID;}
    public void setReservationID(int reservationID) {this.ID = reservationID;}

    public Bundle getBundle() {return bundle;}
    public void setBundle(Bundle bundle) {this.bundle = bundle;}

    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}

    public Seller getSeller() {return seller;}
    public void setSeller(Seller seller) {this.seller = seller;}

    public LocalDateTime getTimeStamp() {return timeStamp;}
    public void setTimeStamp(LocalDateTime timeStamp) {this.timeStamp = timeStamp;}

    public LocalDateTime getPickupTimeStamp() {return pickupTimeStamp;}
    public void setPickupTimeStamp(LocalDateTime pickupTimeStamp) {this.pickupTimeStamp = pickupTimeStamp;}

    public String getClaimCode() {return claimCode;}
    public void setClaimCode(String claimCode) {this.claimCode = claimCode;}

    public Boolean getNoShow() {return noShow;}
    public void setNoShow(Boolean noShow) {this.noShow = noShow;}

    public Boolean getCollected() {return collected;}
    public void setCollected(Boolean collected) {this.collected = collected;}

    public String getWeatherFlag() {return weatherFlag;}
    public void setWeatherFlag(String weatherFlag) {this.weatherFlag = weatherFlag;}
}
