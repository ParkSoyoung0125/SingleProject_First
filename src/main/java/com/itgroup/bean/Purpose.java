package com.itgroup.bean;

public class Purpose {
    private int prpsId;
    private String prpsCode;
    private String prpsName;

    public Purpose(int prpsId, String prpsCode, String prpsName) {
        this.prpsId = prpsId;
        this.prpsCode = prpsCode;
        this.prpsName = prpsName;
    }

    @Override
    public String toString() {
        return "Purpose{" +
                "prpsId=" + prpsId +
                ", prpsCode='" + prpsCode + '\'' +
                ", prpsName='" + prpsName + '\'' +
                '}';
    }

    public int getPrpsId() {
        return prpsId;
    }

    public void setPrpsId(int prpsId) {
        this.prpsId = prpsId;
    }

    public String getPrpsCode() {
        return prpsCode;
    }

    public void setPrpsCode(String prpsCode) {
        this.prpsCode = prpsCode;
    }

    public String getPrpsName() {
        return prpsName;
    }

    public void setPrpsName(String prpsName) {
        this.prpsName = prpsName;
    }
}
