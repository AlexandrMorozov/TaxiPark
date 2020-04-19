package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
    @Column(name="position")
    private String employeePosition;
    @NotNull
    @Column(name="login")
    private String login;
    @NotNull
    @Column(name="password")
    private String password;



    public Personnel(int personnelID, String fullName,String employeePosition,String login)
    {
        this.personnelID=personnelID;
        this.fullName=fullName;
        this.employeePosition=employeePosition;
        this.login=login;
    }

    public Personnel(String fullName,String dateOfBirth,String placeOfBirth, String passportID,
                     String address,String educationDegree,String phoneNumber, Integer transportID,
                     String employeePosition,String login, String password)
    {
        this.fullName=fullName;
        this.dateOfBirth=dateOfBirth;
        this.placeOfBirth=placeOfBirth;
        this.passportID=passportID;
        this.address=address;
        this.educationDegree=educationDegree;
        this.phoneNumber=phoneNumber;
        this.transportID=transportID;
        this.employeePosition=employeePosition;
        this.login=login;
        this.password=password;
    }

    public Personnel()
    {

    }

    public Personnel(int personnelID,String login,String fullName)
    {
        this.personnelID=personnelID;
        this.fullName=fullName;
        this.login=login;
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

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmployeePosition() {
        return employeePosition;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
