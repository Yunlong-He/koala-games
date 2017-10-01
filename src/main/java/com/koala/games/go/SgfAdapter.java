package com.koala.games.go;


import java.io.*;
//import java.util.StringTokenizer;
import java.util.*;

public final class SgfAdapter {
    static Movement root = null;
    static Movement lastMove = null;

    public static Match readMatch(InputStream input) {

        Vector record = new Vector();
        Match match = new Match();

        try {
            if ( ((char)input.read()) != '(' ) return null;
            if ( ((char)input.read()) != ';' ) return null;

            byte[] item = new byte[512];
            int stack_size = 0;

            boolean end = false;
            while(!end) {
                int len = 0;
//                System.out.println("start new item");
                while(true) {
                    int c = input.read();
                    if (c == -1) break;

                    if ( ((char)c) == '(' ) {
                        if ( ((char)input.read()) != ';' ) return null;
                        if (stack_size == 0) {
                            if (len > 0) {
                                parseItem(match, record, item, len);
                            }
                        }
                        stack_size++;
                        break;
                    }

                    if ( ((char)c) == ')' ) {
                        if (stack_size == 0) {
                            if (len > 0) {
                                parseItem(match, record, item, len);
                            }
                            end = true;
                        }
                        else {
                            stack_size--;
                        }
                        break;
                    }

                    if ( ((char)c) == ';' ) {
                        if (stack_size == 0) {
                            if (len > 0) {
                                parseItem(match, record, item, len);
                            }
                        }
//                        System.out.println("one item found: " + len);
                        break;
                    }

                    item[len] = (byte) c;
                    len++;
                    if ( ((char)c) == '[' ) {
                        while(true) {
                            int c2 = input.read();
                            if (c2 == -1) break;
//                            System.out.print(""+(char)c2);
//                            System.out.print(""+len);
                            item[len] = (byte) c2;
                            len++;
                            if ( ((char)c2) == ']' ) {
                                break;
                            }
                        }
                    }//if [
                }// while true
            }//while
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        match.setMovements(root);
//        match.setRecord(record);
        return match;
    }

    protected static void parseItem(Match match, Vector v, byte[] buf, int len) {
        System.out.println("\nparsing item: " + new String(buf, 0, len));
        Movement current = new Movement();
        if (lastMove != null) {
            lastMove.add(current);
        }
        else {
            root = current;
        }

        lastMove = current;

        String attrName = null, attrValue;
        int pos1 = 0, pos2 = -1;
        while (true) {
            pos1 = 0;
            for (int i = pos2+1; i < len; i++) {
                if ( ((char)buf[i]) == '[') {
                    pos1 = i;

                    if (pos1-pos2 > 1) {
                        attrName = new String(buf, pos2+1, pos1);
                    }
                    break;
                }
            }
            if (pos1 == 0) break;

            pos2 = 0;
            for (int i = pos1+1; i < len; i++) {
    //            System.out.println("" + (char)buf[i] + "<"+(int)buf[i]);
                if ( ((char)buf[i]) == ']') {
                    pos2 = i;
                    break;
                }
            }
            if (pos2 == 0) break;
            System.out.println("\nposition: " + pos1 + "," + pos2);

            attrValue = new String(buf, pos1+1, pos2-pos1-1);
            parseAttribute(match, current, attrName, attrValue);
        }
        System.out.println("\nmovement: " + current.col + "," + current.row);
//        v.addElement(current);
    }

    protected static void parseAttribute(Match match, Movement current, String attrName, String attrValue) {
        System.out.println("parsing: " + attrName + "," + attrValue);
        if (attrName.equals("B")) {
            if (attrValue.length() != 2) {
                return;
            }
            current.player = GoPlayer.BLACK;
            char xx = attrValue.charAt(0);
            char yy = attrValue.charAt(1);
            current.col = xx - 'a';
            current.row = yy - 'a';
        }
        else if (attrName.equals("W")) {
            if (attrValue.length() != 2) {
                return;
            }
            current.player = GoPlayer.WHITE;
            char xx = attrValue.charAt(0);
            char yy = attrValue.charAt(1);
            current.col = xx - 'a';
            current.row = yy - 'a';
        }
        else if (attrName.equals("AB")) {
            if (attrValue.length() != 2) {
                return;
            }
            char xx = attrValue.charAt(0);
            char yy = attrValue.charAt(1);
            current.addForce(xx-'a', yy-'a', GoPlayer.BLACK);
        }
        else if (attrName.equals("AW")) {
            if (attrValue.length() != 2) {
                return;
            }
            char xx = attrValue.charAt(0);
            char yy = attrValue.charAt(1);
            current.addForce(xx-'a', yy-'a', GoPlayer.WHITE);
        }
        else if (attrName.equals("AE")) {
            if (attrValue.length() != 2) {
                return;
            }
            char xx = attrValue.charAt(0);
            char yy = attrValue.charAt(1);
            current.addForce(xx-'a', yy-'a', GoPlayer.UNKNOWN);
        }
        else if (attrName.equals("C")) {
            current.setComment(attrValue);
        }
/*      else if (attrName.equals("CR")) {
            if (attrValue.length() != 2) {
                return;
            }
            char xx = attrValue.charAt(0);
            char yy = attrValue.charAt(1);
            current.addTip(xx-'a', yy-'a', GoPoint.CIRCLE);
        }
        else if (attrName.equals("TR")) {
            if (attrValue.length() != 2) {
                return;
            }
            char xx = attrValue.charAt(0);
            char yy = attrValue.charAt(1);
            current.addTip(xx-'a', yy-'a', GoPoint.TRIANGLE);
        }
        else if (attrName.equals("LB")) {
            if (attrValue.length() != 4) {
                return;
            }
            char xx = attrValue.charAt(0);
            char yy = attrValue.charAt(1);
            char tip = attrValue.charAt(3);
            current.addTip(xx-'a', yy-'a', tip);
        }
        else if (attrName.equals("SQ")) {
            if (attrValue.length() != 4) {
                return;
            }
            char xx = attrValue.charAt(0);
            char yy = attrValue.charAt(1);
            current.addTip(xx-'a', yy-'a', GoPoint.SQUARE);
        }
*/        else if (attrName.equals("SZ")) {
            match.boardSize = Integer.parseInt(attrValue);
        }
        else if (attrName.equals("PB")) {
            match.blackName = attrValue;
        }
        else if (attrName.equals("PW")) {
            match.whiteName = attrValue;
        }
        else if (attrName.equals("BR")) {
            match.blackRank = attrValue;
        }
        else if (attrName.equals("WR")) {
            match.whiteRank = attrValue;
        }
        else if (attrName.equals("EV")) {
            match.matchName = attrValue;
        }
        else if (attrName.equals("DT")) {
            match.matchDate = attrValue;
        }
        else if (attrName.equals("PC")) {
            match.place = attrValue;
        }
        else if (attrName.equals("RE")) {
            match.result = attrValue;
        }
        else if (attrName.equals("SO")) {
            match.source = attrValue;
        }
        else if (attrName.equals("TM")) {
            match.timeLen = Integer.parseInt(attrValue);
        }
    }
}
