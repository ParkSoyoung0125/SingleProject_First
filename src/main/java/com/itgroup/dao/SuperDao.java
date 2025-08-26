package com.itgroup.dao;

import com.itgroup.bean.MenuView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SuperDao {
    public SuperDao() {

        String driver = "oracle.jdbc.driver.OracleDriver";
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException e) {
            System.out.println("해당 드라이브가 존재하지 않습니다.");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection conn  = null; // 접속객체
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "sundori";
        String password = "hello1234";
        try {
            conn = DriverManager.getConnection(url,id,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // select 사용 시 자주 사용되는 출력 형태를 super에 선언하여 필요시 계속 재사용함.
     public String formatViews(MenuView mv){
         return "메뉴명 : " + mv.getMenuName() +
                 " | 분류 : " + mv.getCuisineName() +
                 " | 주영양소 : " + mv.getNutrientName() +
                 " | 목적 : " + mv.getPurposeName() +
                 " | 칼로리(kCal) : " + mv.getKcal();
     }

    public MenuView setMenuView(ResultSet rs) throws SQLException {
        MenuView menuV = new MenuView();
        menuV.setMenuName(rs.getString("menu_name"));
        menuV.setCuisineName(rs.getString("cuisine_name"));
        menuV.setNutrientName(rs.getString("nutrient_name"));
        menuV.setPurposeName(rs.getString("purpose_name"));
        menuV.setKcal(rs.getInt("kcal"));
        return menuV;
    }

}
