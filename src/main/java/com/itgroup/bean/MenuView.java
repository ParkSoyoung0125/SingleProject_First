package com.itgroup.bean;

public class MenuView {

    private long menuId;
    private String menuName;
    private String cuisineName;
    private String nutrientName;
    private String purposeName;
    private int kcal;

    public MenuView() {
    }

    @Override
    public String toString() {
        return "MenuView{" +
                "menuName='" + menuName + '\'' +
                ", cuisineName='" + cuisineName + '\'' +
                ", nutrientName='" + nutrientName + '\'' +
                ", purposeName='" + purposeName + '\'' +
                ", kcal=" + kcal +
                '}';
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

    public String getNutrientName() {
        return nutrientName;
    }

    public void setNutrientName(String nutrientName) {
        this.nutrientName = nutrientName;
    }

    public String getPurposeName() {
        return purposeName;
    }

    public void setPurposeName(String purposeName) {
        this.purposeName = purposeName;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public MenuView(String menuName, String cuisineName, String nutrientName, String purposeName, int kcal) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.cuisineName = cuisineName;
        this.nutrientName = nutrientName;
        this.purposeName = purposeName;
        this.kcal = kcal;
    }
}
