package com.itgroup;

import com.itgroup.bean.Menu;
import com.itgroup.bean.MenuView;
import com.itgroup.dao.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuManager {
    private InsertMenuDao mDao = null;
    private DeleteDao dDao = null;
    private selectMenuDao sDao = null;
    private selectCalDao scDao = null;
    private selectCategoryDao sCategoryDao = null;
    private updateDao uDao = null;
    private SuperDao superDao = null;

    private Scanner scan = null;
    private MenuService menuService = null;

    public MenuManager() {
        this.mDao = new InsertMenuDao();
        this.dDao = new DeleteDao();
        this.sDao = new selectMenuDao();
        this.scDao = new selectCalDao();
        this.sCategoryDao = new selectCategoryDao();
        this.uDao = new updateDao();
        this.superDao = new SuperDao();
        this.scan = new Scanner(System.in);
        this.menuService = new MenuService();
    }

    // 카테고리별 메뉴 추천
    public void selectCategoryCal() throws SQLException {
        List<MenuView> mnV = new ArrayList<>();
        int no = 1;
        while (true){
            System.out.println("카테고리별 메뉴 추천 페이지 입니다.\n" +
                    "원하시는 카테고리를 골라 번호를 기입해주세요.\n" +
                    "1. 주영양소별 | 2. 음식분류별 | 3. 목적별 | 0. 나가기");
            int number = sCategoryDao.scanNUM(0,3);  // 0 ~ 3 사이의 정수가 아닌 모든 값(문자열, 범위 밖 정수) 필터링

            switch (number){
                case 1:
                    System.out.println("주요 영양소별 메뉴 추천 페이지입니다.\n" +
                            "추천받고 싶은 영양소의 번호를 기입해주세요.\n" +
                            "1. 탄수화물 | 2. 단백질 | 3. 지방 | 4. 식이섬유 | 0. 나가기");
                    int num = sCategoryDao.scanNUM(0, superDao.getMaxNutriId()); // 1 ~ 4 사이의 정수가 아닌 모든 값(문자열, 범위 밖 정수) 필터링
                    if(num == 0) {
                        System.out.println("'나가기'를 선택하셨습니다.\n메인 화면으로 돌아갑니다.\n");
                        return;
                    }
                    mnV = sCategoryDao.selectCategoryNutrient(num); // 영양소별로 해당하는 모든 데이터 중복없이 갖고오기
                    if(mnV == null || mnV.isEmpty()) {  // 데이터 없을 경우 이전페이지로 돌아가기
                        System.out.println("해당하는 데이터가 없습니다. 이전 페이지로 돌아갑니다.");
                    } else {
                        for(MenuView bean : mnV){
                            String msg ="(" + no++ + ") " +
                                    sCategoryDao.formatViews(bean); // 데이터 출력시 필요한 포맷 세팅(SuperDao)
                            System.out.println(msg);    // DB에서 가져온 List 출력
                        }
                        System.out.println("==============목록 끝.============== 이전페이지로 나갑니다.");
                    }
                    System.out.println();
                    break;
                case 2:
                    System.out.println("음식 분류별 메뉴 추천 페이지입니다.\n" +
                            "추천받고 싶은 음식의 분류에 해당하는 번호를 기입해주세요.\n" +
                            "1. 한식 | 2. 중식 | 3. 일식 | 4. 양식 | 0. 나가기");
                    num = sCategoryDao.scanNUM(0, superDao.getMaxCuisineId()); // 1 ~ 4 사이의 정수가 아닌 모든 값(문자열, 범위 밖 정수) 필터링
                    if(num == 0) { // 0을 선택했을 경우
                        System.out.println("'나가기'를 선택하셨습니다.\n메인 화면으로 돌아갑니다.\n");
                        return;
                    }
                    mnV = sCategoryDao.selectCategoryCuisine(num); // 분류별로 해당하는 모든 데이터 중복없이 갖고오기
                    if(mnV == null || mnV.isEmpty()) {  // 데이터 없을 경우 이전페이지로 돌아가기
                        System.out.println("해당하는 데이터가 없습니다. 이전 페이지로 돌아갑니다.");
                    } else {
                        for (MenuView bean : mnV) {
                            String msg = "(" + no++ + ") " +
                                    sCategoryDao.formatViews(bean); // 데이터 출력시 필요한 포맷 세팅(SuperDao)
                            System.out.println(msg);    // DB에서 가져온 List 출력
                        }
                        System.out.println("==============목록 끝.============== 이전페이지로 나갑니다.");
                    }
                    System.out.println();
                    break;
                case 3:
                    System.out.println("목적별 메뉴 추천 페이지입니다.\n" +
                            "원하시는 목적에 해당하는 번호를 기입해주세요.\n" +
                            "1. 다이어트 | 2. 벌크업 | 3. 환자식 | 4. 일반식 | 0. 나가기");
                    num = sCategoryDao.scanNUM(0, superDao.getMaxPurpsId()); // 1 ~ 4 사이의 정수가 아닌 모든 값(문자열, 범위 밖 정수) 필터링
                    if(num == 0) {
                        System.out.println("'나가기'를 선택하셨습니다.\n메인 화면으로 돌아갑니다.\n");
                        return;
                    }
                    mnV = sCategoryDao.selectCategoryPurpose(num); // 분류별로 해당하는 모든 데이터 중복없이 갖고오기
                    if(mnV == null || mnV.isEmpty()) {  // 데이터 없을 경우 이전페이지로 돌아가기
                        System.out.println("해당하는 데이터가 없습니다. 이전 페이지로 돌아갑니다.");
                    } else {
                        for(MenuView bean : mnV) {
                            String msg = "(" + no++ + ") " +
                                    sCategoryDao.formatViews(bean); // 데이터 출력시 필요한 포맷 세팅(SuperDao)
                            System.out.println(msg);    // DB에서 가져온 List 출력
                        }
                        System.out.println("==============목록 끝.============== 이전페이지로 나갑니다.");
                    }
                    System.out.println();
                    break;
                case 0:
                    System.out.println("현재 페이지를 종료합니다.\n" +
                            "메인 화면으로 나갑니다.");
                    System.out.println();
                    return;
            }
        }

    }


    // 기입한 칼로리에 따라 해당 칼로리 이상 또는 이하로 조회될 리스트 출력
    public void selectCal() {
        List<MenuView> menuVs = new ArrayList<>();
        System.out.println("조회할 칼로리(kcal)를 기입해주세요.(단위 : kcal)");
        int number = scDao.scanNUM(0,5000); // 0~5000사이의 정수만 기입 가능.

        System.out.println("원하시는 정보를 골라 숫자로 기입해주세요(예시 : 1, 2).\n(1) 해당 칼로리 이상 메뉴 추천 | (2) 해당 칼로리 이하 메뉴 추천");

        int check = scDao.scanNUM(1,2); // 숫자 1 또는 2만 기입 가능
        if (check == 1){
            menuVs = scDao.selectCalUP(number); // 기입한 칼로리 이상의 데이터만 LIST로 가져옴
            for(MenuView mnVs : menuVs){
                int no = 1;
                String msg ="(" + no++ + ") " +
                        scDao.formatViews(mnVs);    // 가져온 데이터를 포맷에 담아 출력.
                System.out.println(msg);
            }
            System.out.println("============ 목록의 끝입니다. ============");
            System.out.println();
        } else if (check == 2) {
            menuVs = scDao.selectCalDown(number);   // 기입한 칼로리 이하의 데이터만 LIST로 가져옴
            for(MenuView mnVs : menuVs){
                int no = 1;
                String msg ="(" + no++ + ") " +
                        scDao.formatViews(mnVs);    // 가져온 데이터를 포맷에 담아 출력.
                System.out.println(msg);
            }
            System.out.println("============ 목록의 끝입니다. ============");
            System.out.println();
        } else {
        }
    }

    // 메뉴 정보 검색
    public void selectMenuName() {
        List<MenuView> menuViews = new ArrayList<>();
        System.out.println("메뉴 정보 검색 페이지 입니다.\n검색할 메뉴명을 입력해주세요.");
        String menuName = superDao.scanSTR();
        menuViews = sDao.selectMenuName(menuName);  // 기입한 메뉴명과 일치하는 데이터 가져오기
        if (menuViews.isEmpty()) {  // 일치하는 정보가 없으면 뒤로 가기
            System.out.println("기입하신 정보와 일치하는 데이터가 존재하지 않습니다.");
            System.out.println("메인 화면으로 돌아갑니다.");
            return;
        }
        for (MenuView menuView : menuViews) {   // 데이터가 있을 경우
            System.out.println(sDao.formatViews(menuView)); // 포맷에 담아서 list로 출력
        }
        System.out.println("============ 목록 끝 ============ / 메인화면으로 나갑니다.");
        System.out.println();
    }

    // 1인 권장량 칼로리 확인
    public void recomdDailyCal() {
        System.out.println("1일 권장량 칼로리를 확인하는 페이지 입니다.");
        System.out.println("다음 중 원하는 타입을 번호로 기입해주세요.");
        System.out.println("1. 남자(성인) | 2. 여자(성인) | 3. 노인(60세 이상) | 4. 성장기 | 5. 임산부 | 0. 현재 페이지 나가기");
        int num = sDao.scanNUM(0,5); // 0 ~ 5 사이의 숫자만 입력가능
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
            String menuName = scan.nextLine().trim();   // 공백없이 문자받기(문자열 오류 방지)
            if(superDao.checkMenuaName(menuName)){
                System.out.println("이미 존재하는 메뉴입니다.");
                return;
            }
            if (!menuName.matches("^[가-힣\\s]+$")) { // 영어나 숫자 기입 불가하게 정규표현식으로 필터링
                System.out.println("메뉴명은 한글과 공백만 입력 가능합니다.");
                return;
            }

            System.out.println("메뉴 분류 중 해당하는 분류를 번호로 기입해주세요\n1.한식 | 2.중식 | 3.일식 | 4.양식");
            int cuisineID = mDao.scanNUM(1,4);  // 1 ~ 4 사이의 정수만 기입 가능하게 필터링

            System.out.println("영양소 분류 중 해당하는 주영양소를 번호로 기입해주세요.\n1.탄수화물 | 2.단백질 | 3.지방 | 4.식이섬유");
            int nutrientId = mDao.scanNUM(1,4); // 1 ~ 4 사이의 정수만 기입 가능하게 필터링

            System.out.println("음식의 칼로리를 기입해주세요.");
            int kCal = mDao.scanNUM(1,5000);    // 1 ~ 5000 사이의 정수만 기입 가능하게 필터링

            System.out.println("음식의 목적을 기입해주세요.\n1.다이어트 | 2.벌크업 | 3.환자식 | 4.일반식");
            int purposeId = mDao.scanNUM(1,4); // 1 ~ 4 사이의 정수만 기입 가능하게 필터링

            menu.setMenuName(menuName); // 메뉴명
            menu.setCuisineId(cuisineID);   // 분류코드
            menu.setPkNtrntId(nutrientId);  // 영양소코드
            menu.setMenuKcal(kCal); // 칼로리
            menu.setMenuPurpsId(purposeId);

            newId = menuService.insertMenuPurpose(menu); // menu객체와 scanner로 입력받은 목적ID INSERT

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
    public void updateMenu() throws SQLException {
            List<MenuView> menuViews = new ArrayList<>();
            MenuView menuView = new MenuView();
            selectAll(); // 리스트 전체출력

            System.out.println("수정할 메뉴의 ID를 입력해주세요. 뒤로 가기를 원하시면 0을 입력해주세요.");
            int num = superDao.scanNUM(superDao.getMINMenuId(), superDao.getMaxMenuId());
            if (num == 0) {   // 0 기입시 뒤로 돌아가기
                System.out.println("메뉴로 돌아갑니다.");
                return;
            } else if (superDao.checkMenuID(num) != 1){  // 0이 아닐 경우 기입한 숫자가 DB에 존재하는 정보인지 확인
                System.out.println("해당하는 정보가 없습니다.\n이전 페이지로 돌아갑니다.");
                return;
            } else {}
            System.out.println("변경을 원하지 않으시면 엔터(Enter)키를 눌러주세요.");
            menuService.updateMenuPurpose(num, scan); // 변경할 데이터를 입력받아서 update 하기


        // 변경하려는 정보 받고 엔터는 null 받기
//      // menu객체에 넘기기
//        menu.setMenuName(menuName); // 메뉴명
//        menu.setCuisineId(cuisineID);   // 분류코드
//        menu.setPkNtrntId(nutrientId);  // 영양소코드
//        menu.setMenuKcal(kCal); // 칼로리
        // updateMenuPurpose에 넘겨서 sql문 실행시키기

        // 잘 수행됐는지 rs 받아와서 결과값 출력하기
    }

    // 타 메소드에 사용되는 기초 메소드 <- Delete 기능에 사용
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
        int back = 0;
        try {
            selectAll();

            System.out.println("삭제할 메뉴의 ID를 적어주세요.(뒤로 가시려면 숫자 0을 적어주세요.)");
            int deleteMenu = dDao.scanNUM(0, dDao.getMaxMenuId()); // 0 ~ DB에 있는 MENU_ID의 최대값 사이의 정수만 기입
            if (deleteMenu == back) {   // 0 기입시 뒤로 돌아가기
                System.out.println("메뉴로 돌아갑니다.");
                return;
            } else if (superDao.checkMenuID(deleteMenu) != 1){  // 0이 아닐 경우 기입한 숫자가 DB에 존재하는 정보인지 확인
                System.out.println("해당하는 정보가 없습니다.\n이전 페이지로 돌아갑니다.");
                return;
            } else {}

            System.out.println("정말로 삭제하시겠습니까? 삭제하시려면 해당 메뉴의 ID를 다시 한번 입력해주세요.");
            String comment = "현재 삭제중인 메뉴는 \n" + sDao.formatViews(sDao.selectMenuId(deleteMenu)) + "\n입니다.";
            System.out.println(comment); // 현재 삭제중인 데이터 재확인

            int checkMenu = dDao.scanNUM(0, dDao.getMaxMenuId());   // 재기입한 번호가 기존에 기입한 번호와 일치하는지 확인
            if (checkMenu != deleteMenu) {  // 처음 기입한 번호와 일치하지 않을 시 뒤로 가기
                System.out.println("기입하신 정보가 일치하지 않습니다.");
                return;
            }

            cnt = dDao.deleteMenu(checkMenu);   // 해당 ID를 가진 데이터 삭제

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
