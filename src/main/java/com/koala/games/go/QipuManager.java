package com.koala.games.go;

import java.io.*;

public class QipuManager {
    public static final int SGF = 0;
    public static final int MGT = 1;
    public static final int MAN = 2;
    public static final int XML = 3;

    public static Match readMatchFromFile(String filename, int filetype) {

        if (filename == null) {
            System.out.println("please input one sgf file name");
            return null;
        }

        try {

            FileInputStream fis = new FileInputStream(filename);
            return SgfAdapter.readMatch(fis);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void saveMatchToFile(Match match, String filename, int filetype) {
        if (filename == null) {
            System.out.println("please input one sgf file name");
            return;
        }

        try {

            FileOutputStream fos = new FileOutputStream(filename);
//            SgfAdapter.writeMatch(match, fos);
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}