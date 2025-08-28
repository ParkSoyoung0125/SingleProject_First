package com.itgroup;

import com.itgroup.bean.Menu;
import com.itgroup.bean.MenuView;
import com.itgroup.dao.InsertMenuDao;
import com.itgroup.dao.updateDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuService {
    private InsertMenuDao mdao = new InsertMenuDao();
    private updateDao udao = new updateDao();

    // 메인 기능 5번
    // Dao 클래스에 만들어놓은 nextMenuId(), insertMenu()를 하나의 트랜잭션으로 묶는 메소드<-INSERT용
    public long insertMenuPurpose(Menu menu) throws SQLException {
        Connection conn = null;
        long newId = -1;
        try{
            conn = mdao.getConnection();
            conn.setAutoCommit(false); // 만약의 사태에 대비해 오토커밋 명시적으로 꺼두기

            newId = mdao.nextMenuId(conn); // MenuDao에서 받아온 시퀀스(PK)
            menu.setMenuId(newId);  //newid에 PK 할당

            mdao.insertMenu(conn,menu); // insert 실행


            conn.commit();
            return newId;

        } catch (Exception e) {
            if (conn != null) try {
                conn.rollback();
            } catch (Exception ignore) {
                throw e;
            }
            e.printStackTrace();
        } finally {
            if(conn != null) try{
                conn.close();
            } catch (Exception ignore){
            }
        }
        return newId;
    }

    // update(MENU), delete(MENU_PURPOSE), merge(MENU_PURPOSE) 세개의 메소드를 묶을 update 메소드
    public void updateMenuPurpose(int menuId, Scanner sc) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int rs = 0;
        try {
            conn = udao.getConnection(); // Connection 연결
            conn.setAutoCommit(false); // 트랜잭션을 위해 오토커밋 명시적으로 꺼두기

            udao.updateMenu(conn,menuId,sc); // menu테이블 업데이트하기

            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
