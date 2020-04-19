package com.taxipark.dbmodel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Services_Category
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Column(name="servicecategoryid")
    private int serviceCategoryID;
    @NotNull
    @Column(name="servicecategoryname")
    private String serviceCategoryName;
    @NotNull
    @Column(name="servicecategorydescription")
    private String serviceCategoryDescription;
    @NotNull
    @Column(name="servicecategoryfoto")
    private String serviceCategoryFoto;
    @NotNull
    @Column(name="servicecategorytype")
    private String serviceCategoryType;

    public Services_Category()
    {

    }

    public Services_Category(int serviceCategoryID, String serviceCategoryName)
    {
        this.serviceCategoryID=serviceCategoryID;
        this.serviceCategoryName=serviceCategoryName;
    }

    public Services_Category(String serviceCategoryName, String serviceCategoryDescription,
                             String serviceCategoryFoto, String serviceCategoryType)
    {
        this.serviceCategoryName=serviceCategoryName;
        this.serviceCategoryDescription=serviceCategoryDescription;
        this.serviceCategoryFoto=serviceCategoryFoto;
        this.serviceCategoryType=serviceCategoryType;
    }

    public Services_Category(int serviceCategoryID,String serviceCategoryName,String serviceCategoryType)
    {
        this.serviceCategoryID=serviceCategoryID;
        this.serviceCategoryName=serviceCategoryName;
        this.serviceCategoryType=serviceCategoryType;
    }

    public int getServiceCategoryID() {
        return serviceCategoryID;
    }

    public String getServiceCategoryDescription() {
        return serviceCategoryDescription;
    }

    public String getServiceCategoryName() {
        return serviceCategoryName;
    }

    public String getServiceCategoryFoto() {
        return serviceCategoryFoto;
    }

    public String getServiceCategoryType() {
        return serviceCategoryType;
    }

    public void setServiceCategoryDescription(String serviceCategoryDescription) {
        this.serviceCategoryDescription = serviceCategoryDescription;
    }

    public void setServiceCategoryID(int serviceCategoryID) {
        this.serviceCategoryID = serviceCategoryID;
    }

    public void setServiceCategoryName(String serviceCategoryName) {
        this.serviceCategoryName = serviceCategoryName;
    }

    public void setServiceCategoryFoto(String serviceCategoryFoto) {
        this.serviceCategoryFoto = serviceCategoryFoto;
    }

    public void setServiceCategoryType(String serviceCategoryType) {
        this.serviceCategoryType = serviceCategoryType;
    }
}
