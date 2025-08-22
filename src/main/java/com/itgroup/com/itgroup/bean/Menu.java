package com.itgroup.com.itgroup.bean;

public class Menu {
    private int menuId;
    private String menuName;
    private int cuisineId;
    private int pkNtrntId;
    private int menuKcal;

    @Override
    public String toString() {
        return "Menu{" +
                "menuId=" + menuId +
                ", menuName='" + menuName + '\'' +
                ", cuisineId=" + cuisineId +
                ", pkNtrntId=" + pkNtrntId +
                ", menuKcal=" + menuKcal +
                '}';
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public int getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(int cuisineId) {
        this.cuisineId = cuisineId;
    }

    public int getPkNtrntId() {
        return pkNtrntId;
    }

    public void setPkNtrntId(int pkNtrntId) {
        this.pkNtrntId = pkNtrntId;
    }

    public int getMenuKcal() {
        return menuKcal;
    }

    public void setMenuKcal(int menuKcal) {
        this.menuKcal = menuKcal;
    }

    public Menu(int menuId, String menuName, int cuisineId, int pkNtrntId, int menuKcal) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.cuisineId = cuisineId;
        this.pkNtrntId = pkNtrntId;
        this.menuKcal = menuKcal;
    }
}
