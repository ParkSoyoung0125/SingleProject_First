package com.itgroup.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteDao extends SuperDao{
    public DeleteDao (){super();}

    // 메뉴 정보(한 행씩)를 삭제하는 메소드(DB에 CASCADE 옵션으로 MENU테이블에서만 삭제)
    public int deleteMenu(String id) {
        String deleteSql = "DELETE FROM MENU WHERE TRIM(UPPER(MENU_NAME)) = TRIM(UPPER(?))"; // 공백제거
        int rs = -1;
        Connection conn = this.getConnection();

        try(PreparedStatement pstmt = conn.prepareStatement(deleteSql)){
            pstmt.setString(1,id);
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
