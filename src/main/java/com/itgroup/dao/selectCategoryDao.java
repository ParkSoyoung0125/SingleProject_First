package com.itgroup.dao;

import com.itgroup.bean.MenuView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class selectCategoryDao extends SuperDao{
    public selectCategoryDao() {super();}

    //  menu, menu_purpose, purpose, cuisine, nutrient 테이블 조인하여 각 데이터 SELECT 해오기 / 메인기능 1번
    public final String BASE_SQL = "select distinct\n" +
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

    // 음식분류별 행 출력
    public List<MenuView> selectCategoryCuisine(int num){
        List<MenuView> bean = new ArrayList<>();
        Connection conn = this.getConnection();
        ResultSet rs = null;

        String sql =  BASE_SQL +
                "where c.cuisine_id = ? order by c.cuisine_name";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,num);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                setMenuView(rs);
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

    // 영양소별 행 출력
    public List<MenuView> selectCategoryNutrient(int num){
        List<MenuView> bean = new ArrayList<>();
        Connection conn = this.getConnection();
        ResultSet rs = null;

        String sql =  BASE_SQL +
                "where n.nutrient_id = ? order by n.nutrient_name";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,num);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                setMenuView(rs);
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

    // 목적별 행 출력
    public List<MenuView> selectCategoryPurpose(int num){
        List<MenuView> bean = new ArrayList<>();
        Connection conn = this.getConnection();
        ResultSet rs = null;

        String sql =  BASE_SQL +
                "where m.purpose_id = ? order by p.purpose_name";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1,num);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                setMenuView(rs);
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
