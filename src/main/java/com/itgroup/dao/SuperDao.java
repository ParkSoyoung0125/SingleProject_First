package com.itgroup.dao;

import com.itgroup.bean.MenuView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
        Connection conn = null; // 접속객체
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String id = "sundori";
        String password = "hello1234";
        try {
            conn = DriverManager.getConnection(url, id, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    // select 사용 시 자주 사용되는 출력 형태를 super에 선언하여 필요시 계속 재사용함.
    public String formatViews(MenuView mv) {
        return "메뉴ID : " + mv.getMenuId() +
                "  |  메뉴명 : " + mv.getMenuName() +
                "  |  분류 : " + mv.getCuisineName() +
                "  |  주영양소 : " + mv.getNutrientName() +
                "  |  목적 : " + mv.getPurposeName() +
                "  |  칼로리(kCal) : " + mv.getKcal();
    }

    public MenuView setMenuView(ResultSet rs) throws SQLException {
        MenuView menuV = new MenuView();
        menuV.setMenuId(rs.getInt("menu_id"));
        menuV.setMenuName(rs.getString("menu_name"));
        menuV.setCuisineName(rs.getString("cuisine_name"));
        menuV.setNutrientName(rs.getString("nutrient_name"));
        menuV.setPurposeName(rs.getString("purpose_name"));
        menuV.setKcal(rs.getInt("kcal"));
        return menuV;
    }

    public int scanNUM(int min, int max) {
        Scanner scan = new Scanner(System.in);
        int number = -1;

        while (true) {
            String input = scan.nextLine().trim();

            if (!input.matches("^[0-9]+$")) {   // 숫자만 허용
                System.out.println("숫자만 입력 가능합니다.");
                System.out.println("정상 범위 내의 숫자를 다시 입력해주세요.");
                continue;
            }
            number = Integer.parseInt(input);
            if (number < min || number > max) {
                System.out.println("번호는 " + min + " ~ " + max + " 범위 내에서만 입력 가능합니다.");
                System.out.println("정상 범위 내의 숫자를 다시 입력해주세요.");
                continue;
            }
            return number;
        }
    }
}
