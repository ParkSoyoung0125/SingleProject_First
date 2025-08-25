package com.itgroup.bean;

public class MenuPurpose {
    private int menuMapId;
    private int purposeMapId;

    public MenuPurpose(int menuMapId, int purposeMapId) {
        this.menuMapId = menuMapId;
        this.purposeMapId = purposeMapId;
    }

    @Override
    public String toString() {
        return "MenuPurpose{" +
                "menuMapId=" + menuMapId +
                ", purposeMapId=" + purposeMapId +
                '}';
    }

    public int getMenuMapId() {
        return menuMapId;
    }

    public void setMenuMapId(int menuMapId) {
        this.menuMapId = menuMapId;
    }

    public int getPurposeMapId() {
        return purposeMapId;
    }

    public void setPurposeMapId(int purposeMapId) {
        this.purposeMapId = purposeMapId;
    }
}
