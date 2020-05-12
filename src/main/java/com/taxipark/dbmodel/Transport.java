package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Transport
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Column(name="transportid")
    private int transportID;
    @NotNull
    @Column(name="carbrand")
    private String carBrand;
    @NotNull
    @Column(name="carmodel")
    private String carModel;
    @NotNull
    @Column(name="carcolor")
    private String carColor;
    @NotNull
    @Column(name="licenseplate")
    private String licensePlate;
    @NotNull
    @Column(name="vin")
    private String vin;
    @NotNull
    @Column(name="carcassnum")
    private String carcassNum;
    @NotNull
    @Column(name="enginenum")
    private String engineNum;
    @NotNull
    @Column(name="yearofmanufacture")
    private String yearOfManufacture;
    @NotNull
    @Column(name="otherattributes")
    private String otherAttributes;

   /* @OneToMany(fetch = FetchType.LAZY,mappedBy = "attachedTransport")
    private List<Personnel> attachedPersonnel;*/

    public Transport()
    {

    }

    public Transport(int transportID,String carBrand,String carModel,String vin)
    {
        this.transportID=transportID;
        this.carBrand=carBrand;
        this.carModel=carModel;
        this.vin=vin;
    }

    public Transport(int transportID,String carBrand,String carModel,String vin,String licensePlate)
    {
        this.transportID=transportID;
        this.carBrand=carBrand;
        this.carModel=carModel;
        this.licensePlate=licensePlate;
        this.vin=vin;
    }

    public Transport(String carBrand,String carModel,String carColor, String licensePlate,
                     String vin,String carcassNum,String engineNum, String yearOfManufacture, String otherAttributes)
    {
        this.carBrand=carBrand;
        this.carModel=carModel;
        this.carColor=carColor;
        this.licensePlate=licensePlate;
        this.vin=vin;
        this.carcassNum=carcassNum;
        this.engineNum=engineNum;
        this.yearOfManufacture=yearOfManufacture;
        this.otherAttributes=otherAttributes;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public void setCarcassNum(String carcassNum) {
        this.carcassNum = carcassNum;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public void setEngineNum(String engineNum) {
        this.engineNum = engineNum;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setOtherAttributes(String otherAttributes) {
        this.otherAttributes = otherAttributes;
    }

    public void setTransportID(int transportID) {
        this.transportID = transportID;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setYearOfManufacture(String yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

   /* public void setAttachedPersonnel(List<Personnel> attachedPersonnel) {
        this.attachedPersonnel = attachedPersonnel;
    }*/

    public int getTransportID() {
        return transportID;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public String getCarcassNum() {
        return carcassNum;
    }

    public String getCarColor() {
        return carColor;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getEngineNum() {
        return engineNum;
    }

    public String getVin() {
        return vin;
    }

    public String getOtherAttributes() {
        return otherAttributes;
    }

    public String getYearOfManufacture() {
        return yearOfManufacture;
    }

   /* public List<Personnel> getAttachedPersonnel() {
        return attachedPersonnel;
    }*/
}
