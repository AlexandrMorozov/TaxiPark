package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Driver_Data
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Column(name="driverlicenseid")
    private int driverLicenseId;
    @NotNull
    @Column(name="ownerid")
    private int ownerId;
    @NotNull
    @Column(name="licensenum")
    private String licenseNum;
    @NotNull
    @Column(name="licenseissuedby")
    private String licenseIssuedBy;
    @NotNull
    @Column(name="licensecategory")
    private String licenseCategory;
    @NotNull
    @Column(name="licensevaliduntil")
    private String licenseValidUntil;
    @NotNull
    @Column(name="medexvaliduntil")
    private String medExValidUntil;


    public Driver_Data()
    {

    }

    public Driver_Data(int ownerId,String licenseNum, String licenseIssuedBy,
                     String licenseCategory,String licenseValidUntil,String medExValidUntil)
    {
        this.ownerId=ownerId;
        this.licenseNum=licenseNum;
        this.licenseIssuedBy=licenseIssuedBy;
        this.licenseCategory=licenseCategory;
        this.licenseValidUntil=licenseValidUntil;
        this.medExValidUntil=medExValidUntil;
    }

    public int getDriverLicenseId() {
        return driverLicenseId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getLicenseCategory() {
        return licenseCategory;
    }

    public String getLicenseIssuedBy() {
        return licenseIssuedBy;
    }

    public String getLicenseNum() {
        return licenseNum;
    }

    public String getLicenseValidUntil() {
        return licenseValidUntil;
    }

    public String getMedExValidUntil() {
        return medExValidUntil;
    }

    public void setDriverLicenseId(int driverLicenseId) {
        this.driverLicenseId = driverLicenseId;
    }

    public void setLicenseCategory(String licenseCategory) {
        this.licenseCategory = licenseCategory;
    }

    public void setLicenseIssuedBy(String licenseIssuedBy) {
        this.licenseIssuedBy = licenseIssuedBy;
    }

    public void setLicenseNum(String licenseNum) {
        this.licenseNum = licenseNum;
    }

    public void setLicenseValidUntil(String licenseValidUntil) {
        this.licenseValidUntil = licenseValidUntil;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public void setMedExValidUntil(String medExValidUntil) {
        this.medExValidUntil = medExValidUntil;
    }
}
