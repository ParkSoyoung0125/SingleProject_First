package com.itgroup;

import com.itgroup.bean.Menu;
import com.itgroup.dao.MenuDao;

import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuManager {
    private MenuDao mDao = null;
    private Scanner scan = null;
    private MenuService menuService = null;

    public MenuManager() {
        this.mDao = new MenuDao();
        this.scan = new Scanner(System.in);
        this.menuService = new MenuService();
    }


//    public void selectCal() {
//        List<Member> Menus = mDao.selectCal();
//    }

    public void insertMenu() throws SQLException {
        Menu menu = new Menu();
        long newId = 0;
        int cnt = -1;
        try {
            System.out.println("하기의 안내에 따라 메뉴 정보를 입력해주세요.");

            System.out.print("메뉴명 : ");
            String menuName = scan.nextLine().trim();
            if (!menuName.matches("^[가-힣\\s]+$")) {
                System.out.println("메뉴명은 한글과 공백만 입력 가능합니다.");
                return;
            }

            System.out.println("메뉴 분류 중 해당하는 분류를 번호로 기입해주세요\n1.한식 | 2.중식 | 3.일식 | 4.양식");
            int cuisineID = Integer.parseInt(scan.nextLine().trim());
            if (cuisineID == Integer.MIN_VALUE) return;
            if (cuisineID < 1 || cuisineID > 4) {
                System.out.println("분류ID는 1~4 범위여야 합니다.");
                return;
            }

            System.out.println("영양소 분류 중 해당하는 주영양소를 번호로 기입해주세요.\n1.탄수화물 | 2.단백질 | 3.지방 | 4.식이섬유");
            int nutrientId = Integer.parseInt(scan.nextLine());
            if (nutrientId == Integer.MIN_VALUE) return;
            if (nutrientId < 1 || nutrientId > 4) {
                System.out.println("주영양소ID는 1~4 범위여야 합니다.");
                return;
            }

            System.out.println("음식의 칼로리를 기입해주세요.");
            int kCal = Integer.parseInt(scan.nextLine().trim());
            if (kCal == Integer.MIN_VALUE) return;
            if (kCal < 1 || kCal > 5000) {
                System.out.println("칼로리는 1~5000 범위여야 합니다.");
                return;
            }

            System.out.println("음식의 목적을 기입해주세요.\n1.다이어트 | 2.벌크업 | 3.환자식");
            int purposeId = Integer.parseInt(scan.nextLine().trim());
            if (purposeId == Integer.MIN_VALUE) return;
            if (purposeId < 1 || purposeId > 3) {
                System.out.println("목적ID는 1~3 범위여야 합니다.");
                return;
            }

            menu.setMenuName(menuName);
            menu.setCuisineId(cuisineID);
            menu.setPkNtrntId(nutrientId);
            menu.setMenuKcal(kCal);

            newId = menuService.insertMenuPurpose(menu, purposeId);

            System.out.println("메뉴 등록이 완료되었습니다.");
        } catch (SQLException e) {
            System.out.println("숫자 입력이 올바르지 않습니다.");
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
            System.out.println("등록 중 오류가 발생했습니다. 뒤로 돌아갑니다.");
        } finally {
//            scan.close(); // Scanner 닫기
        }
    }

    public void deleteMenu() {
        Menu menu = new Menu();
        int cnt = -1;
        try {
        System.out.println("삭제할 메뉴명을 적어주세요");
        String deleteMenu = scan.nextLine().trim();
//        if (!deleteMenu.matches("^[가-힣\\s]+$")) {
//            System.out.println("메뉴명은 한글과 공백만 입력 가능합니다.");
//            return;
//        }
        System.out.println("정말로 삭제하시겠습니까? 삭제하시려면 해당 메뉴명을 다시 한번 입력해주세요.");
        String checkMenu = scan.nextLine().trim();
            if (!checkMenu.equals(deleteMenu)) {
                System.out.println("기입하신 정보가 일치하지 않습니다.");
                return;
            }
            cnt = mDao.deleteMenu(checkMenu);
            } catch (Exception e) {
                System.out.println("삭제 중 오류가 발생하였습니다.");
                e.printStackTrace();
                return;
            }

        if (cnt == -1){
                System.out.println("메뉴 정보 삭제에 실패하였습니다.");
            } else if (cnt == 0) {
                System.out.println("해당 메뉴 정보가 존재하지 않습니다.");
            } else {
                System.out.println("메뉴 삭제에 성공하였습니다.");
            }

    }
}
