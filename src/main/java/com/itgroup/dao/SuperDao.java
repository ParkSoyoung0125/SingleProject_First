package com.itgroup.dao;

import com.itgroup.bean.MenuView;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SuperDao {
    public SuperDao() {

        String driver = "oracle.jdbc.OracleDriver";
        try {
            Class.forName(driver);

        } catch (ClassNotFoundException e) {
            System.out.println("해당 드라이브가 존재하지 않습니다.");
            e.printStackTrace();
        }
    }

    private final Scanner scan = new Scanner(System.in);

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
    public String scanSTR() {

        while (true) {
            String str = scan.nextLine().trim();
            if (str.isEmpty()) return null;
            if (!str.matches("^[가-힣\\s]+$")) {   // 한글, 공백만 허용
                System.out.println("한글과 공백만 입력 가능합니다.");
                System.out.println("정상 범위 내의 문자를 다시 입력해주세요.");
            }
            if (str.matches("^[가-힣\\s]+$")) {   // 한글, 공백만 허용
                return str;
            }
        }
    }

    public int getMaxMenuId() throws SQLException {
        String sql = "SELECT NVL(MAX(MENU_ID), 0) FROM MENU";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public int getMINMenuId() throws SQLException {
        String sql = "SELECT NVL(MIN(MENU_ID), 0) FROM MENU";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }

    public int getMaxCuisineId() throws SQLException {
        String sql = "SELECT NVL(MAX(Cuisine_ID), 0) FROM Cuisine";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }


    public int getMaxNutriId() throws SQLException {
        String sql = "SELECT NVL(MAX(Nutrient_ID), 0) FROM Nutrient";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }


    public int getMaxPurpsId() throws SQLException {
        String sql = "SELECT NVL(MAX(PURPOSE_ID), 0) FROM PURPOSE";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }


    // 입력한 MENU_ID가 DB에 없을경우 리턴
    public int checkMenuID(int num) throws SQLException {
        String sql = "select 1 from menu where menu_id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = -1;
        try {
            conn = this.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, num);
            rs = pstmt.executeQuery();
            while (rs.next()){
                result = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                if(rs != null) {rs.close();}
                if(pstmt != null) {pstmt.close();}
                if(conn != null) {conn.close();} }
            catch (Exception ignore){
            }
        }
        return result;
    }

    // 입력한 MENU_ID가 DB에 없을경우 리턴
    public boolean checkMenuaName(String menuName) throws SQLException {
        String sql = "select 1 from menu where menu_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, menuName);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // 한 행이라도 나오면 존재
            }
        }
        catch (Exception e) {
        e.printStackTrace();
        return false;
    }
    }

    public final String SELECT_BASE_SQL = "select\n" +
            "        m.menu_id as menu_id,\n" +
            "        m.menu_name as menu_name,\n" +
            "       c.cuisine_name as cuisine_name,\n" +
            "       n.nutrient_name as nutrient_name,\n" +
            "       p.purpose_name as purpose_name,\n" +
            "       m.kcal as kcal \n" +            "        from menu m \n" +
            "join purpose p on p.purpose_id = p.purpose_id \n" +
            "join cuisine c on m.cuisine_id = c.cuisine_id\n" +
            "join nutrient n on n.nutrient_id = m.primary_nutrient_id\n";

    // 메뉴명으로 데이터를 가져오는 메소드(중복데이터가 있으면 전부 가져오게끔 생성)
    public List<MenuView> selectMenuName(String mName){
        List<MenuView> menuVs = new ArrayList<>();
        Connection conn = this.getConnection();
        ResultSet rs = null;
        String sql = SELECT_BASE_SQL + "where m.menu_Name = trim(?) order by m.menu_id";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,mName);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                menuVs.add(setMenuView(rs));
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) try{
                conn.close();
            } catch (Exception ignore){
            }
        }
        return menuVs;
    }

}
