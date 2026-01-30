package com.waste_manager.team_roadmap;
import java.time.LocalDateTime;

public class Reservation {
    private int reservationID;
    private int postingID;
    private int customerID;
    private LocalDateTime timeStamp;
    private String claimCode;
    private String status;
    private String weatherFlag;

    public Reservation(){}


    public int getReservationID() {return reservationID;}
    public void setReservationID(int reservationID) {this.reservationID = reservationID;}

    public int getPostingID() {return postingID;}
    public void setPostingID(int postingID) {this.postingID = postingID;}

    public int getCustomerID() {return customerID;}
    public void setCustomerID(int customerID) {this.customerID = customerID;}

    public LocalDateTime getTimeStamp() {return timeStamp;}
    public void setTimeStamp(LocalDateTime timeStamp) {this.timeStamp = timeStamp;}

    public String getClaimCode() {return claimCode;}
    public void setClaimCode(String claimCode) {this.claimCode = claimCode;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    public String getWeatherFlag() {return weatherFlag;}
    public void setWeatherFlag(String weatherFlag) {this.weatherFlag = weatherFlag;}

}
