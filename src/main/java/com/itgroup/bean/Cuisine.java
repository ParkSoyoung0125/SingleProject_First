package com.itgroup.bean;

public class Cuisine {
    private int cusineId;
    private String cusineCode;
    private String cusineName;

    public Cuisine(int cusineId, String cusineCode, String cusineName) {
        this.cusineId = cusineId;
        this.cusineCode = cusineCode;
        this.cusineName = cusineName;
    }

    @Override
    public String toString() {
        return "Cuisine{" +
                "cusineId=" + cusineId +
                ", cusineCode='" + cusineCode + '\'' +
                ", cusineName='" + cusineName + '\'' +
                '}';
    }

    public int getCusineId() {
        return cusineId;
    }

    public void setCusineId(int cusineId) {
        this.cusineId = cusineId;
    }

    public String getCusineCode() {
        return cusineCode;
    }

    public void setCusineCode(String cusineCode) {
        this.cusineCode = cusineCode;
    }

    public String getCusineName() {
        return cusineName;
    }

    public void setCusineName(String cusineName) {
        this.cusineName = cusineName;
    }
}
