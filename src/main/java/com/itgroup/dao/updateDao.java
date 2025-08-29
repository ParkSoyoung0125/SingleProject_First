package com.itgroup.dao;

import com.itgroup.bean.Menu;

import java.sql.*;
import java.util.Scanner;

import static java.nio.file.Files.exists;

public class updateDao extends SuperDao {
    public updateDao () {super();}

    public int getMaxMenuId() throws SQLException {
        String sql = "SELECT NVL(MAX(MENU_ID), 0) FROM MENU";
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }

    static String readLineOrNull(Scanner sc,String updateMessage) { // 엔터는 null로 받는 문자열 메소드(지정한 범위 내의 문자열만 받는 기능은 아직...)
        System.out.print(updateMessage);
        String s = sc.nextLine().trim();
        return s.isEmpty() ? null : s;
    }

    // 엔터는 null로 받기+정해진 숫자 범위 외의 정수 입력시 롤백하는 정수형 변환 메소드
    static Integer readIntOrNull(Scanner sc,String updateMessage,  int min, int max) {
        while(true){
            System.out.print(updateMessage);
            String s = sc.nextLine().trim();
            if (s.isEmpty()) return null;
            if (!s.matches("\\d+")) {                     // 숫자만
                System.out.println("숫자만 입력 가능합니다. 다시 입력해주세요.");
                continue;
            }
            int n = Integer.parseInt(s);
            if (n < min || n > max) {
                System.out.printf("번호는 %d ~ %d 범위 내에서만 입력 가능합니다.%n", min, max);
                continue;
            }
            return n;
        }
    }

    // sql문을 실행하여 SQL문이 성공적으로 수행되었으면 true, 안됐으면 false를 반환함.
    static boolean exists(Connection conn, String sql, int id) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id); // 첫번째 플레이스 홀더 자리에 정수 id를 배치하여 결과행이 존재하는지를 도출
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void updateMenu(Connection conn, long menuId, Scanner sc) throws SQLException { // menu테이블 컬럼 업데이트할 메소드 << readNull 메소드 여기서 사용
        try{
            // enter키를 칠 경우 null이 되도록 필터링, 정수는 null값을 지닐수 없으므로 Integer로 받기
            String newUpdateMname = readLineOrNull(sc,"메뉴명(유지할 경우 Enter키) : ");
            Integer newCid = readIntOrNull(sc,"|분류코드| 1.한식 | 2.중식 | 3.일식 | 4.양식 |\n분류ID(변경하지 않을 경우 Enter키) : ",1,4);
            Integer newNid = readIntOrNull(sc,"|영양소코드| 1.탄수화물 | 2.단백질 | 3.지방 | 4.식이섬유 |\n영양소ID(변경하지 않을 경우 Enter키) : ",1,4);
            Integer newUpdateKcal = readIntOrNull(sc,"칼로리(단위:Kcal,변경하지 않을 경우 Enter키) : ",1,5000);
            Integer newPurpsId = readIntOrNull(sc,"|목적코드| 1.다이어트 | 2.벌크업 | 3.환자식 | 4.일반식 |\n(변경하지 않을 경우 Enter키) : ",1,4);

            // 영양소ID와 분류ID 오기입시 롤백되도록 필터링
            // 새로 기입한 영양소ID나 분류ID가 null이 아니면서 DB에 존재하지 않을 때 롤백
            if (newCid != null && !exists(conn, "SELECT 1 FROM CUISINE WHERE CUISINE_ID=?", newCid))
                throw new SQLException("존재하지 않는 CUISINE_ID: " + newCid);
            if (newNid != null && !exists(conn, "SELECT 1 FROM NUTRIENT WHERE NUTRIENT_ID=?", newNid))
                throw new SQLException("존재하지 않는 NUTRIENT_ID: " + newNid);
            if (newPurpsId != null && !exists(conn, "SELECT 1 FROM PURPOSE WHERE PURPOSE_ID=?", newPurpsId))
                throw new SQLException("존재하지 않는 NUTRIENT_ID: " + newPurpsId);

            String sql = "update menu  \n" +
                    "        set menu_name = COALESCE(?,menu_name),\n" +
                    "            CUISINE_ID = COALESCE(?,CUISINE_ID),\n" +
                    "            PRIMARY_NUTRIENT_ID =  COALESCE(?,PRIMARY_NUTRIENT_ID),\n" +
                    "            kcal = COALESCE(?,kcal),\n" +
                    "            PURPOSE_ID = COALESCE(?,PURPOSE_ID)\n" +
                    "        where menu_id = ?";


            try(PreparedStatement pstmt = conn.prepareStatement(sql)) { // sql문 수행하고 pstmt 알아서 종료
                // 새 메뉴명이 null이면 플레이스홀더 1번 자리에 null 세팅, 어떤 SQL 타입인지 명시해야 드라이버가 정상적으로 처리하기 때문에 VARCHAR로 명시. int는 NUMBERIC로 명시함.
                if (newUpdateMname == null) pstmt.setNull(1, java.sql.Types.VARCHAR);
                else pstmt.setString(1, newUpdateMname);
                if (newCid == null) pstmt.setNull(2, Types.NUMERIC);
                else pstmt.setInt(2, newCid);
                if (newNid == null) pstmt.setNull(3, java.sql.Types.NUMERIC);
                else pstmt.setInt(3, newNid);
                if (newUpdateKcal == null) pstmt.setNull(4, Types.NUMERIC);
                else pstmt.setInt(4, newUpdateKcal);
                if (newPurpsId == null) pstmt.setNull(5, Types.NUMERIC);
                else pstmt.setInt(5, newPurpsId);
                pstmt.setLong(6, menuId); // null일 수 없으므로 처리 X

                pstmt.executeUpdate();
            }
            conn.commit();
            System.out.println("정보 수정이 완료되었습니다.");
        } catch (Exception e){
            conn.rollback();
            System.out.println("정보 수정에 실패하였습니다.");
            e.printStackTrace();
        }
    }

}
