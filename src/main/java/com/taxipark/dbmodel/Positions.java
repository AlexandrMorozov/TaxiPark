package com.taxipark.dbmodel;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Positions
{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @NotNull
    @Column(name="positionid")
    private int positionID;
    @NotNull
    @Column(name="positionname")
    private String positionName;
    @NotNull
    @Column(name="positionsalary")
    private String positionSalary;
    @NotNull
    @Column(name="positioncoefficient")
    private boolean positionCoefficient;

    public Positions()
    {

    }


    public boolean isPositionCoefficient() {
        return positionCoefficient;
    }

    public int getPositionID() {
        return positionID;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getPositionSalary() {
        return positionSalary;
    }

    public void setPositionCoefficient(boolean positionCoefficient) {
        this.positionCoefficient = positionCoefficient;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public void setPositionSalary(String positionSalary) {
        this.positionSalary = positionSalary;
    }
}
