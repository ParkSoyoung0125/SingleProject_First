package com.itgroup.bean;

public class Nutrient {
    private int ntrntId;
    private String ntrntCode;
    private String ntrntName;

    public Nutrient(int ntrntId, String ntrntCode, String ntrntName) {
        this.ntrntId = ntrntId;
        this.ntrntCode = ntrntCode;
        this.ntrntName = ntrntName;
    }

    @Override
    public String toString() {
        return "Nutrient{" +
                "ntrntId=" + ntrntId +
                ", ntrntCode='" + ntrntCode + '\'' +
                ", ntrntName='" + ntrntName + '\'' +
                '}';
    }

    public int getNtrntId() {
        return ntrntId;
    }

    public void setNtrntId(int ntrntId) {
        this.ntrntId = ntrntId;
    }

    public String getNtrntCode() {
        return ntrntCode;
    }

    public void setNtrntCode(String ntrntCode) {
        this.ntrntCode = ntrntCode;
    }

    public String getNtrntName() {
        return ntrntName;
    }

    public void setNtrntName(String ntrntName) {
        this.ntrntName = ntrntName;
    }
}
