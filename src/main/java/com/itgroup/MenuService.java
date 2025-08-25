package com.itgroup;

import com.itgroup.bean.Menu;
import com.itgroup.dao.MenuDao;
import com.itgroup.dao.MenuPurposeDao;

import java.sql.Connection;
import java.sql.SQLException;

public class MenuService {
    private MenuDao mdao = new MenuDao();
    private MenuPurposeDao mpseDao = new MenuPurposeDao();

    // Dao 클래스에 만들어놓은 nextMenuId(), insertMenu(), insertMenuPurpose()를 하나의 트랜잭션으로 묶는 메소드
    public long insertMenuPurpose(Menu menu, int purposeId) throws SQLException {
        Connection conn = null;
        long newId = -1;
        try{
            conn = mdao.getConnection();
            conn.setAutoCommit(false); // 만약의 사태에 대비해 오토커밋 명시적으로 꺼두기

            newId = mdao.nextMenuId(conn); // MenuDao에서 받아온 시퀀스(PK)
            menu.setMenuId(newId);  //newid에 할당

            mdao.insertMenu(conn,menu); // 부모테이블 insert 실행
            mpseDao.insertMenuPurpose(conn,newId,purposeId); // 자식테이블 Insert 실행

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
}
