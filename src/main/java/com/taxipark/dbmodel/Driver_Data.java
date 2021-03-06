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

    @NotNull
    @ManyToOne(targetEntity = Personnel.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerid")
    private Personnel driver;


    public Driver_Data()
    {

    }

    public Driver_Data(Personnel driver,String licenseNum, String licenseIssuedBy,
                     String licenseCategory,String licenseValidUntil,String medExValidUntil)
    {
        this.driver=driver;
        this.licenseNum=licenseNum;
        this.licenseIssuedBy=licenseIssuedBy;
        this.licenseCategory=licenseCategory;
        this.licenseValidUntil=licenseValidUntil;
        this.medExValidUntil=medExValidUntil;
    }

    public int getDriverLicenseId() {
        return driverLicenseId;
    }

    public Personnel getDriver() {
        return driver;
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

    public void setDriver(Personnel driver) {
        this.driver = driver;
    }

    public void setMedExValidUntil(String medExValidUntil) {
        this.medExValidUntil = medExValidUntil;
    }
}
