package com.itgroup;

import com.itgroup.bean.Menu;
import com.itgroup.bean.MenuView;
import com.itgroup.dao.*;

import java.lang.reflect.Member;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuManager {
    private MenuDao mDao = null;
    private DeleteDao dDao = null;
    private selectMenuDao sDao = null;
    private selectCalDao scDao = null;
    private selectCategoryDao sCategoryDao = null;
    private updateDao uDao = null;
    private Scanner scan = null;
    private MenuService menuService = null;

    public MenuManager() {
        this.mDao = new MenuDao();
        this.dDao = new DeleteDao();
        this.sDao = new selectMenuDao();
        this.scDao = new selectCalDao();
        this.sCategoryDao = new selectCategoryDao();
        this.uDao = new updateDao();
        this.scan = new Scanner(System.in);
        this.menuService = new MenuService();
    }

    // 카테고리별 메뉴 추천
    public void selectCategoryCal(){
        List<MenuView> mnV = new ArrayList<>();
        int no = 1;
        while (true){
            System.out.println("카테고리별 메뉴 추천 페이지 입니다.\n" +
                    "원하시는 카테고리를 골라 번호를 기입해주세요.\n" +
                    "1. 주영양소별 | 2. 음식분류별 | 3. 목적별 | 0. 나가기");
            int num = scan.nextInt();
            if (num < 0 || num > 3 ){
                System.out.println("번호는 0 ~ 3까지만 입력 가능합니다.");
                return;
            }
            switch (num){
                case 1:
                    System.out.println("주요 영양소별 메뉴 추천 페이지입니다.\n" +
                            "추천받고 싶은 영양소의 번호를 기입해주세요.)\n" +
                            "1. 탄수화물 | 2. 단백질 | 3. 지방 | 4. 식이섬유");
                    num = scan.nextInt();
                    if (num < 1 || num > 4 ){
                        System.out.println("번호는 1 ~ 4까지만 입력 가능합니다.");
                        return;
                    }
                    mnV = sCategoryDao.selectCategoryNutrient(num);
                    if(mnV == null || mnV.isEmpty()) {
                        System.out.println("해당하는 데이터가 없습니다. 이전 페이지로 돌아갑니다.");
                    } else {
                        for(MenuView bean : mnV){
                            String msg ="(" + no++ + ") " +
                                    sCategoryDao.formatViews(bean);
                            System.out.println(msg);
                        }
                        System.out.println("==============목록 끝.============== 이전페이지로 나갑니다.");
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("음식 분류별 메뉴 추천 페이지입니다.\n" +
                            "추천받고 싶은 음식의 분류에 해당하는 번호를 기입해주세요.\n" +
                            "1. 한식 | 2. 중식 | 3. 일식 | 4. 양식");
                    num = scan.nextInt();
                    if (num < 1 || num > 4 ){
                        System.out.println("번호는 1 ~ 4까지만 입력 가능합니다.");
                        return;
                    }
                    mnV = sCategoryDao.selectCategoryCuisine(num);
                    if(mnV == null || mnV.isEmpty()) {
                        System.out.println("해당하는 데이터가 없습니다. 이전 페이지로 돌아갑니다.");
                    } else {
                        for (MenuView bean : mnV) {
                            String msg = "(" + no++ + ") " +
                                    sCategoryDao.formatViews(bean);
                            System.out.println(msg);
                        }
                        System.out.println("==============목록 끝.============== 이전페이지로 나갑니다.");
                    }
                    System.out.println();
                    break;
                case 3:
                    System.out.println("목적별 메뉴 추천 페이지입니다.\n" +
                            "원하시는 목적에 해당하는 번호를 기입해주세요.)\n" +
                            "1. 다이어트 | 2. 벌크업 | 3. 환자식 | 4. 일반식");
                    num = scan.nextInt();
                    if (num < 1 || num > 4 ){
                        System.out.println("번호는 1 ~ 4까지만 입력 가능합니다.");
                        return;
                    }
                    mnV = sCategoryDao.selectCategoryPurpose(num);
                    if(mnV == null || mnV.isEmpty()) {
                        System.out.println("해당하는 데이터가 없습니다. 이전 페이지로 돌아갑니다.");
                    } else {
                        for(MenuView bean : mnV) {
                            String msg = "(" + no++ + ") " +
                                    sCategoryDao.formatViews(bean);
                            System.out.println(msg);
                        }
                        System.out.println("==============목록 끝.============== 이전페이지로 나갑니다.");
                    }
                    System.out.println();
                    break;
                case 0:
                    System.out.println("현재 페이지를 종료합니다.\n" +
                            "메인 화면으로 나갑니다.");
                    System.out.println();
                    System.out.println();
                    return;
            }
        }

    }


    // 기입한 칼로리에 따라 해당 칼로리 이상 또는 이하로 조회될 리스트 출력
    public void selectCal() {
        List<MenuView> menuVs = new ArrayList<>();
        int no = 1;
        System.out.println("조회할 칼로리(kcal)를 기입해주세요.(단위 : kcal)");
        int cal = scan.nextInt();
        System.out.println("원하시는 정보를 골라 숫자로 기입해주세요(예시 : 1, 2).\n(1) 해당 칼로리 이상 메뉴 추천 | (2) 해당 칼로리 이하 메뉴 추천");

        int check = scan.nextInt();
        if (check == 1){
            menuVs = scDao.selectCalUP(cal);
            for(MenuView mnVs : menuVs){
                String msg ="(" + no++ + ") " +
                        scDao.formatViews(mnVs);
                System.out.println(msg);
            }
            System.out.println("============ 목록의 끝입니다. ============");
            System.out.println();
        } else if (check == 2) {
            menuVs = scDao.selectCalDown(cal);
            for(MenuView mnVs : menuVs){
                String msg ="(" + no++ + ") " +
                        scDao.formatViews(mnVs);
                System.out.println(msg);
            }
            System.out.println("============ 목록의 끝입니다. ============");
            System.out.println();
        } else {
            System.out.println("선택 가능한 번호는 1 또는 2 여야 합니다.");
            System.out.println("메인화면으로 돌아갑니다.");
        }
    }

    // 1인 권장량 칼로리 확인
    public void recomdDailyCal() {
        System.out.println("1일 권장량 칼로리를 확인하는 페이지 입니다.");
        System.out.println("다음 중 원하는 타입을 번호로 기입해주세요.");
        System.out.println("1. 남자(성인) | 2. 여자(성인) | 3. 노인(60세 이상) | 4. 성장기 | 5. 임산부 | 0. 현재 페이지 나가기");
        int num = scan.nextInt();
        if (num < 0 || num > 5 ){
            System.out.println("번호는 0 ~ 5까지만 입력 가능합니다.");
            return;
        }
        switch (num) {
            case 0 :
                System.out.println("현재 페이지를 종료합니다. 메인메뉴로 돌아갑니다.");
                break;
            case 1 :
                System.out.println("성인 남성 1일 권장량 칼로리는 2,600 ~ 2,800kcal 입니다.");
                break;
            case 2 :
                System.out.println("성인 여성 1일 권장량 칼로리는 1,800 ~ 2,200kcal 입니다.");
                break;
            case 3 :
                System.out.println("노년기 남성 1일 권장량 칼로리는 2,000 ~ 2,600kcal\n" +
                        "노년기 여성 1일 권장량 칼로리는 1,600 ~ 2,200kcal 입니다.");
                break;
            case 4 :
                System.out.println("유아(2–3세)의 1일 권장량 칼로리는 1,000 ~ 1,400kcal\n" +
                        "어린이(4–13세)의 1일 권장량 칼로리는 1,200 ~ 1,800kcal\n" +
                        "청소년(14-18세)의 1인 권장량 칼로리는 1,800 ~ 2,400kcal입니다.");
                break;
            case 5 :
                System.out.println("임산부의 1일 권장량 칼로리는 평소 섭취량의 + 340 ~ 450kcal 입니다.\n" +
                        "여성 1일 평균 칼로리 섭취량은 2,000kcal 내외입니다.");
                break;
            default:
                System.out.println("잘못된 번호입니다. 현재 페이지를 종료합니다.");
                break;
            }

    }

    // 메뉴 등록
    public void insertMenu() throws SQLException {
        Menu menu = new Menu();
        long newId = 0;

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
                System.out.println("주영양소 ID는 1~4 범위여야 합니다.");
                return;
            }

            System.out.println("음식의 칼로리를 기입해주세요.");
            int kCal = Integer.parseInt(scan.nextLine().trim());
            if (kCal == Integer.MIN_VALUE) return;
            if (kCal < 1 || kCal > 5000) {
                System.out.println("칼로리는 1~5000 범위여야 합니다.");
                return;
            }

            System.out.println("음식의 목적을 기입해주세요.\n1.다이어트 | 2.벌크업 | 3.환자식 | 4.일반식");
            int purposeId = Integer.parseInt(scan.nextLine().trim());
            if (purposeId == Integer.MIN_VALUE) return;
            if (purposeId < 1 || purposeId > 4) {
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
        }
    }

    // 메뉴 정보 업데이트
    public void updateMenu() {
        List<MenuView> menuVs = new ArrayList<>();
        System.out.println("메뉴 정보 수정 페이지입니다.\n" +
                "수정할 메뉴의 ID를 입력해주세요.");
        menuVs = sDao.selectMenuId();

//        menuService.updateMenuPurpose();
    }
    // 타 메소드에 사용되는 기초 메소드

    public void selectAll(){
        List<MenuView> menuViews = new ArrayList<>();
        menuViews = sDao.selectAll();
        int no = 1;
        for(MenuView menuView : menuViews){
            String msg ="(" + no++ + ") " +
                    sDao.formatViews(menuView);
            System.out.println(msg);
        }
    }
    // 메뉴 삭제

    public void deleteMenu() {
        Menu menu = new Menu();
        int cnt = -1;
        String back = "0";
        try {
            selectAll();
        System.out.println("삭제할 메뉴명을 적어주세요.(해당 메뉴와 관련된 정보는 전부 삭제됩니다, 뒤로 가시려면 숫자 0을 적어주세요.)");
        String deleteMenu = scan.nextLine().trim();
        if (deleteMenu.matches(back)) {
            System.out.println("메뉴로 돌아갑니다.");
            return;
        }
            System.out.println("정말로 삭제하시겠습니까? 삭제하시려면 해당 메뉴명을 다시 한번 입력해주세요.");
        String checkMenu = scan.nextLine().trim();
            if (!checkMenu.equals(deleteMenu)) {
                System.out.println("기입하신 정보가 일치하지 않습니다.");
                return;
            }
            cnt = dDao.deleteMenu(checkMenu);
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
