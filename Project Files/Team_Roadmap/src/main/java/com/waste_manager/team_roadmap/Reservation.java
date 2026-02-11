package com.waste_manager.team_roadmap;
import java.time.LocalDateTime;

public class Reservation {
    private long reservationID;
    private long postingID;
    private long customerID;
    private LocalDateTime timeStamp;
    private String claimCode;
    private String status;
    private String weatherFlag;

    public Reservation( long thisPostingID, long thisCustomerID, LocalDateTime thisTimeStamp,
                       String thisClaimCode, String thisStatus, String thisWeatherFlag){
        this.postingID = thisPostingID;
        this.customerID = thisCustomerID;
        this.timeStamp = thisTimeStamp;
        this.claimCode = thisClaimCode;
        this.status = thisStatus;
        this.weatherFlag = thisWeatherFlag;
    }


    public long getReservationID() {return reservationID;}
    public void setReservationID(int reservationID) {this.reservationID = reservationID;}

    public long getPostingID() {return postingID;}
    public void setPostingID(int postingID) {this.postingID = postingID;}

    public long getCustomerID() {return customerID;}
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
