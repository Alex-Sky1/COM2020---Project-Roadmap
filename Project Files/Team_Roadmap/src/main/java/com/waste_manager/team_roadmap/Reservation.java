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
    private LocalDateTime timeStamp;
    @Column(nullable = false)
    private String claimCode;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private String weatherFlag;

    public Reservation(){};
    public Reservation(Bundle bundle, Customer customer, LocalDateTime thisTimeStamp,
                       String thisClaimCode, String thisStatus, String thisWeatherFlag){
        this.bundle = bundle;
        this.customer = customer;
        this.timeStamp = thisTimeStamp;
        this.claimCode = thisClaimCode;
        this.status = thisStatus;
        this.weatherFlag = thisWeatherFlag;
    }


    public long getReservationID() {return ID;}
    public void setReservationID(int reservationID) {this.ID = reservationID;}

    public Bundle getBundle() {return bundle;}
    public void setBundle(Bundle bundle) {this.bundle = bundle;}

    public Customer getCustomer() {return customer;}
    public void setCustomer(Customer customer) {this.customer = customer;}

    public LocalDateTime getTimeStamp() {return timeStamp;}
    public void setTimeStamp(LocalDateTime timeStamp) {this.timeStamp = timeStamp;}

    public String getClaimCode() {return claimCode;}
    public void setClaimCode(String claimCode) {this.claimCode = claimCode;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public String getWeatherFlag() {return weatherFlag;}
    public void setWeatherFlag(String weatherFlag) {this.weatherFlag = weatherFlag;}

}
