package com.itgroup;

import java.sql.*;
import java.util.Scanner;

// CREATE USER oraman IDENTIFIED BY oracle DEFAULT TABLESPACE users TEMPORARY TABLESPACE temp;
//GRANT connect, resource TO oraman;
//ALTER USER oraman ACCOUNT UNLOCK;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);
        MenuManager menuManager = new MenuManager();

        System.out.println("사용자 맞춤 메뉴 추천 프로그램에 오신 것을 환영합니다.");

        while (true){
            System.out.println("사용할 메뉴를 골라 입력해주세요.");
            System.out.println("1: 카테고리별 메뉴 추천 | 2: 칼로리별 메뉴 추천 | 3: 1인 권장량 칼로리 확인 | 4: 새 메뉴 등록 | 5: 메뉴 정보 업데이트 | 6. 메뉴 삭제 | 0. 프로그램 종료");
            int number = scan.nextInt();
            switch (number){
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    System.exit(0);
                    break;
                case 1: // 영양소별, 분류별, 용도별 메뉴 추천
                    break;

                case 2: // 기입한 칼로리를 기준으로 이상, 이하의 메뉴 추천
//                menuManager.selectCal();
                    break;

                case 3: // 1인 권장량 칼로리 확인(남,여,노인,소아)
                    break;

                case 4: // 메뉴 등록

                    menuManager.insertMenu();
                    break;

                case 5: // 메뉴 수정
                    break;

                case 6: // 메뉴 삭제
                    menuManager.deleteMenu();
                    break;

            }
        }
    }
}