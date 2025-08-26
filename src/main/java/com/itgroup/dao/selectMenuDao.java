package com.itgroup.dao;

import com.itgroup.bean.Menu;
import com.itgroup.bean.MenuView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class selectMenuDao extends SuperDao{
    public selectMenuDao() {super();}

    public List<MenuView> selectMenuId(){
        List<MenuView> menuVs = new ArrayList<>();
        Connection conn = this.getConnection();
        ResultSet rs = null;
        String sql = "select distinct\n" +
                "        m.menu_id as menu_id,\n" +
                "        m.menu_name as menu_name,\n" +
                "       c.cuisine_name as cuisine_name,\n" +
                "       n.nutrient_name as nutrient_name,\n" +
                "       p.purpose_name as purpose_name,\n" +
                "       m.kcal as kcal \n" +
                "        from menu m \n" +
                "join menu_purpose mp on m.menu_id = mp.menu_id \n" +
                "join purpose p on p.purpose_id = mp.purpose_id \n" +
                "join cuisine c on m.cuisine_id = c.cuisine_id\n" +
                "join nutrient n on n.nutrient_id = m.primary_nutrient_id\n" +
                "order by m.menu_id;";
        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
            rs = pstmt.executeQuery();

            while (rs.next()) {
                MenuView mnV = new MenuView();
                mnV.setMenuId(rs.getInt("menu_id"));
                mnV.setMenuName(rs.getString("menu_name"));
                mnV.setCuisineName(rs.getString("cuisine_name"));
                mnV.setNutrientName(rs.getString("nutrient_name"));
                mnV.setPurposeName(rs.getString("purpose_name"));
                mnV.setKcal(rs.getInt("kcal"));
                menuVs.add(mnV);
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


    // 총 데이터를 출력할 메소드, 중복작성을 줄이고 재사용성을 늘릴 목적으로 생성.
    public List<MenuView> selectAll(){
        List<MenuView> bean = new ArrayList<>();
        Connection conn = this.getConnection();
        ResultSet rs = null;

        String sql = "select distinct\n" +
                "        m.menu_name as menu_name,\n" +
                "       c.cuisine_name as cuisine_name,\n" +
                "       n.nutrient_name as nutrient_name,\n" +
                "       p.purpose_name as purpose_name,\n" +
                "       m.kcal as kcal \n" +
                "        from menu m \n" +
                "join menu_purpose mp on m.menu_id = mp.menu_id \n" +
                "join purpose p on p.purpose_id = mp.purpose_id \n" +
                "join cuisine c on m.cuisine_id = c.cuisine_id\n" +
                "join nutrient n on n.nutrient_id = m.primary_nutrient_id\n" +
                "order by m.menu_name";

        try(PreparedStatement pstmt = conn.prepareStatement(sql)) {
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
