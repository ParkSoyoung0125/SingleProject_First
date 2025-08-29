package com.itgroup.dao;

import com.itgroup.bean.MenuView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class selectCalDao extends SuperDao{
    public selectCalDao() {super();}

    public final String BASE_SQL = "select\n" +
            "        m.menu_id as menu_id,\n" +
            "        m.menu_name as menu_name,\n" +
            "       c.cuisine_name as cuisine_name,\n" +
            "       n.nutrient_name as nutrient_name,\n" +
            "       p.purpose_name as purpose_name,\n" +
            "       m.kcal as kcal \n" +
            "        from menu m \n" +
            "join purpose p on p.purpose_id = m.purpose_id \n" +
            "join cuisine c on m.cuisine_id = c.cuisine_id\n" +
            "join nutrient n on n.nutrient_id = m.primary_nutrient_id\n";

    // 기입한 칼로리 이상의 값을 가지고 있는 행 출력 / 메인 기능 2번
    public List<MenuView> selectCalUP(int cal){
        List<MenuView> bean = new ArrayList<>();
        Connection conn = this.getConnection();
        ResultSet rs = null;

        String sql = BASE_SQL + "where m.kcal >= ? order by m.menu_id";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,cal);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                bean.add(setMenuView(rs));
            }

        }catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(conn != null) try{
                    conn.close();
                } catch (Exception ignore){
                }
            }
        return bean;
    }

    // 기입한 칼로리 이하의 값을 가지고 있는 행 출력
    public List<MenuView> selectCalDown(int cal){
        List<MenuView> bean = new ArrayList<>();
        Connection conn = this.getConnection();
        ResultSet rs = null;

        String sql = BASE_SQL + "where m.kcal <= ? order by m.menu_name";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,cal);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                bean.add(setMenuView(rs));
            }

        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(conn != null) try{
                conn.close();
            } catch (Exception ignore){
            }
        }
        return bean;
    }


}
