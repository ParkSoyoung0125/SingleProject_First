package com.itgroup.dao;

import com.itgroup.bean.Menu;

import java.lang.reflect.Member;
import java.sql.*;
import java.util.List;

public class MenuPurposeDao extends SuperDao {

    // superDao에 선언한 Driver, Connection 호출.
    public MenuPurposeDao(){ super(); }


    // MenuDao.nextMenuId로 받은 시퀀스를 FK로 참조하여 메뉴ID와 용도ID를 MENU_PURPOSE 테이블에 INSERT(용도 중복용 매핑 테이블)
    // 자식테이블 INSERT
    public void insertMenuPurpose(Connection conn,long menuId, int purposeId) throws SQLException {

        String sqlInsertC = "INSERT INTO MENU_PURPOSE (MENU_ID, PURPOSE_ID)\n" +
                " VALUES (?, ?)";

        // finally를 선언하지 않고 try() 안에 명시하여 close()를 호출하는 실수를 줄임.
        // 트랜잭션으로 묶는 중에 커넥션 닫아버리면 안되기 때문에 pstmt만 닫음.
        try (PreparedStatement pstmtChild = conn.prepareStatement(sqlInsertC)){

            pstmtChild.setLong(1, menuId);
            pstmtChild.setInt(2, purposeId);
            pstmtChild.executeUpdate();
        }
    }

}
