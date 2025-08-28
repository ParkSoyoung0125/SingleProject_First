package com.itgroup.dao;

import java.sql.*;

import com.itgroup.bean.Menu;

public class InsertMenuDao extends SuperDao {

    // superDao에 선언한 Driver, Connection 호출.
    public InsertMenuDao(){ super(); }


    // insert 시에 MENU테이블, MENU_PURPOSE 테이블 에 각각 데이터를 삽입해야하는데
    // MENU_PURPOSE 테이블이 MENU_ID를 FK로 참조하기 때문에 시퀀스(pk) 생성용 메소드

    // Connection : DB 서버와 네트워크로 연결된 세션객체, 이 연결로 SQL이 오감.
    // Statement : conn.createStatement()로 생성되며 문자열 SQL을 DB로 전송, 파라미터가 없는 단순 select문이기 때문에 PreparedStatement말고 Statement를 사용함.
    //      -excuteQuery(sql) : select 실행 -> ResultSet 반환
    //      -excuteUpdate(sql) : insert,update,delete 실행 -> DB반영 행 수 반환
    // ResultSet : select 결과 행을 앞으로 가며 값을 읽음.

    public long nextMenuId(Connection conn) throws SQLException {
        try(Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT MENU_SEQ.NEXTVAL FROM dual"))
            {
            rs.next();
            return rs.getLong(1);
            // 첫번째 컬럼 값을 반환, 첫번째칼럼이 시퀀스 값이기 때문에 반환된 시퀀스 값(PK)로 부모테이블(MENU)과 자식테이블(MENU_PURPORSE)이 FK제약조건이 성립되어 insert 성공.
            }
    }

    // 부모테이블 INSERT
    public void insertMenu(Connection conn,Menu menu) throws SQLException {
        // MENU테이블에 메뉴명, 분류ID, 영양소ID, 칼로리를 INSERT.
        // 분류ID, 영양소ID는 CUISINE 테이블, NUTRIENT 테이블과 매핑하여 일치하는 ID를 MENU테이블에 INSERT.
        String sqlInsertP = "INSERT INTO MENU (MENU_ID, MENU_NAME, CUISINE_ID, PRIMARY_NUTRIENT_ID, KCAL, PURPOSE_ID)\n" +
                " VALUES (?, ?, ?, ?, ?,?)";

        // finally를 선언하지 않고 try() 안에 명시하여 close()를 호출하는 실수를 줄임.
        try (PreparedStatement pstmtParent = conn.prepareStatement(sqlInsertP)){

            pstmtParent.setLong(1, menu.getMenuId());
            pstmtParent.setString(2, menu.getMenuName());
            pstmtParent.setInt(3, menu.getCuisineId());
            pstmtParent.setInt(4, menu.getPkNtrntId());
            pstmtParent.setInt(5, menu.getMenuKcal());
            pstmtParent.setInt(6,menu.getMenuPurpsId());
            pstmtParent.executeUpdate();

        }
    }

//    public void insertMenuPurpose(Connection conn,long menuId, int purposeId) throws SQLException {
//
//        String sqlInsertC = "INSERT INTO MENU_PURPOSE (MENU_ID, PURPOSE_ID)\n" +
//                " VALUES (?, ?)";
//
//        // finally를 선언하지 않고 try() 안에 명시하여 close()를 호출하는 실수를 줄임.
//        // 트랜잭션으로 묶는 중에 커넥션 닫아버리면 안되기 때문에 pstmt만 닫음.
//        try (PreparedStatement pstmtChild = conn.prepareStatement(sqlInsertC)){
//
//            pstmtChild.setLong(1, menuId);
//            pstmtChild.setInt(2, purposeId);
//            pstmtChild.executeUpdate();
//        }
//    }


}
