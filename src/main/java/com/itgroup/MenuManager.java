package com.itgroup;

import com.itgroup.dao.MenuDao;

import java.util.Scanner;

public class MenuManager {
    private MenuDao mDao = null;
    private Scanner scan = null;

    public MenuManager() {
        this.mDao = new MenuDao();
        this.scan = new Scanner(System.in);
    }





}
