package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Personnel
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Column(name="personnelid")
    private int personnelID;
    @NotNull
    @Column(name="fullname")
    private String fullName;
    @NotNull
    @Column(name="dateofbirth")
    private String dateOfBirth;
    @NotNull
    @Column(name="placeofbirth")
    private String placeOfBirth;
    @NotNull
    @Column(name="passportid")
    private String passportID;
    @NotNull
    @Column(name="address")
    private String address;
    @NotNull
    @Column(name="educationdegree")
    private String educationDegree;
    @NotNull
    @Column(name="phonenumber")
    private String phoneNumber;
    @NotNull
    @Column(name="transportID")
    private Integer transportID;
    @NotNull
    @Column(name="login")
    private String login;
    @NotNull
    @Column(name="password")
    private String password;

    @NotNull
    @ManyToOne(targetEntity = Positions.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "position")
    private Positions personnelProfession;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "assignedEmployee")
    private List<ClientOrder> relatedOrders;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "driver")
    private List<Driver_Data> driverData;

    public Personnel(int personnelID, String fullName,String login)
    {
        this.personnelID=personnelID;
        this.fullName=fullName;
        this.login=login;
    }

    public Personnel(String fullName,String dateOfBirth,String placeOfBirth, String passportID,
                     String address,String educationDegree,String phoneNumber, Integer transportID,
                     Positions personnelProfession,String login, String password)
    {
        this.fullName=fullName;
        this.dateOfBirth=dateOfBirth;
        this.placeOfBirth=placeOfBirth;
        this.passportID=passportID;
        this.address=address;
        this.educationDegree=educationDegree;
        this.phoneNumber=phoneNumber;
        this.transportID=transportID;
        this.personnelProfession=personnelProfession;
        this.login=login;
        this.password=password;
    }

    public Personnel( int personnelID,String fullName,String dateOfBirth,String placeOfBirth, String passportID,
                     String address,String educationDegree,String phoneNumber, Integer transportID,
                     Positions personnelProfession,String login, String password)
    {
        this.personnelID=personnelID;
        this.fullName=fullName;
        this.dateOfBirth=dateOfBirth;
        this.placeOfBirth=placeOfBirth;
        this.passportID=passportID;
        this.address=address;
        this.educationDegree=educationDegree;
        this.phoneNumber=phoneNumber;
        this.transportID=transportID;
        this.personnelProfession=personnelProfession;
        this.login=login;
        this.password=password;
    }

    public Personnel()
    {

    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTransportID(Integer transportID) {
        this.transportID = transportID;
    }

    public void setRelatedOrders(List<ClientOrder> relatedOrders) {
        this.relatedOrders = relatedOrders;
    }

    public void setEducationDegree(String educationDegree) {
        this.educationDegree = educationDegree;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPassportID(String passportID) {
        this.passportID = passportID;
    }

    public void setPersonnelID(int personnelID) {
        this.personnelID = personnelID;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDriverData(List<Driver_Data> driverData) {
        this.driverData = driverData;
    }

    public int getPersonnelID() {
        return personnelID;
    }

    public String getAddress() {
        return address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEducationDegree() {
        return educationDegree;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassportID() {
        return passportID;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Integer getTransportID() {
        return transportID;
    }

    public List<ClientOrder> getRelatedOrders() {
        return relatedOrders;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Positions getPersonnelProfession() {
        return personnelProfession;
    }

    public void setPersonnelProfession(Positions personnelProfession)
    {
        this.personnelProfession = personnelProfession;
    }

    public List<Driver_Data> getDriverData() {
        return driverData;
    }

}
