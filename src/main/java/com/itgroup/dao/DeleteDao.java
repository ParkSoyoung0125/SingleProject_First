package com.itgroup.dao;

import java.sql.*;

public class DeleteDao extends SuperDao{
    public DeleteDao (){super();}

    // 입력가능한 MENU_ID의 범위를 알려주는 메소드
    public int getMaxMenuId() throws SQLException {
        String sql = "SELECT NVL(MAX(MENU_ID), 0) FROM MENU";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }

    // 입력받은 MENU_ID가 DB에 존재하는지 확인하는 메소드
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

    // 메뉴 정보(한 행씩)를 삭제하는 메소드(DB에 CASCADE 옵션으로 MENU테이블에서만 삭제) / 메인 기능 7번
    public int deleteMenu(int id) {
        String deleteSql = "DELETE FROM MENU WHERE MENU_ID = ?"; // 공백제거
        int rs = -1;
        Connection conn = this.getConnection();

        try(PreparedStatement pstmt = conn.prepareStatement(deleteSql)){
            pstmt.setInt(1,id);
            rs = pstmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            try {
                if(conn != null){conn.rollback();}
            } catch (Exception e2){
                e2.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (conn != null){conn.close();}
            } catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return rs;
    }
}
