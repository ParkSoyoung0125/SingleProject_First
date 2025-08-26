package com.itgroup.dao;

import java.util.Scanner;

public class updateDao extends SuperDao {
    public updateDao () {super();}

    long menuId = 0;
    String menuName = null;    // null이면 유지
    Integer kcal = null;       // null이면 유지
    Integer cuisineId = null;  // null이면 유지
    Integer nutrientId = null; // null이면 유지
    Integer oldPurposeId = null; // null이면 삭제 안함
    Integer newPurposeId = null;  // null이면 추가/변경 안함

    public int updateMenu(int num){
        Scanner scan = new Scanner(System.in);



        return 0;
    }

}
