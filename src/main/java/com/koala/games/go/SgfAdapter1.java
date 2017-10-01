package com.koala.games.go;


import java.io.*;
import java.util.Stack;

/***********************************************************************************
 *                                                                                 *
 *   SGF Go File Properties                                                        *
 *                                                                                 *
 ***********************************************************************************/

    /*
     * Move Properties                 B, KO, MN, W
     * /
    static final int MV_BLACK = 101;      //B[]
    static final int MV_WHITE = 102;      //W[]
//    static final int MV_FORCE = 103;      //KO[]
//    static final int MV_MARKNUM = 104;    //MN[]

    /*
     * Setup Properties                AB, AE, AW, PL
     * /
    static final int AD_BLACK = 201;      //AB[]
    static final int AD_WHITE = 202;      //AW[]
    static final int AD_EMPTY = 203;      //AE[]
//    static final int AD_PLAYER = 204;     //PL[]

    /*
     * Node Annotation Properties      C, DM, GB, GW, HO, N, UC, V
     * /
    static final int NA_COMMENT  = 301;   //C[]
//    static final int NA_DEUCE    = 302;   //DM[]
//    static final int NA_BGOOD    = 303;   //GB[]
//    static final int NA_WGOOD    = 304;   //GW[]
//    static final int NA_HOTSPOT  = 305;   //HO[]
//    static final int NA_NODENAME = 306;   //N[]
//    static final int NA_UNCLEAR  = 307;   //UC[]
//    static final int NA_VALUE    = 308;   //V[]

    /*
     * Move Annotation Properties      BM, DO, IT, TE
     * /
//    static final int MA_BADMOVE    = 401;   //BM[]
//    static final int MA_DOUBTFUL   = 402;   //DO[]
//    static final int MA_INTEREST   = 403;   //IT[]
//    static final int MA_TESUJI     = 404;   //TE[]  --good move

    /*
     * Markup Properties               AR, CR, DD, LB, LN, MA, SL, SQ, TR
     * /
    static final int MK_CIRCLE    = 501;    //CR[]
    static final int MK_LABEL     = 502;    //LB[]
    static final int MK_CROSS     = 503;    //MA[]
    static final int MK_SQURE     = 504;    //SQ[]
    static final int MK_TRIANGLE  = 505;    //TR[]
//    static final int MK_LINE      = 506;    //LN[]   ---Point:Point
//    static final int MK_SELECT    = 507;    //SL[]
//    static final int MK_ARROW     = 508;    //AR[]   ---Point:Point
//    static final int MK_DIM       = 509;    //DD[]   ---gray out points

    /*
     * Root Properties                 AP, CA, FF, GM, ST, SZ
     * /

    static final int RT_SIZE          = 601;      //SZ[]
//    static final int RT_APPLICATION   = 602;      //AP[]
//    static final int RT_CHARSET       = 603;      //CA[]
//    static final int RT_FILEFORMAT    = 604;      //FF[]
//    static final int RT_GAMETYPE      = 605;      //GM[]
//    static final int RT_SHOWTYPE      = 606;      //ST[]

    /*
     * Game Info Properties            AN, BR, BT, CP, DT, EV, GN, GC, ON, OT, PB, PC, PW, RE, RO, RU, SO, TM, US, WR, WT
     * /
    static final int GM_BLACK     = 701;     //PB[]
    static final int GM_WHITE     = 702;     //PW[]
    static final int GM_BLACKRANK = 703;     //BR[]
    static final int GM_WHITERANK = 704;     //WR[]
    static final int GM_EVENT     = 705;     //EV[]
    static final int GM_DATE      = 706;     //DT[]
    static final int GM_PLACE     = 707;     //PC[]
    static final int GM_RESULT    = 708;     //RE[]
    static final int GM_SOURCE    = 709;     //SO[]
    static final int GM_TIME      = 710;     //TM[]
//    static final int GM_WHITETEAM   = 0;     //WT[]
//    static final int GM_USER        = 0;     //US[]
//    static final int GM_ANNOTATOR   = 0;     //AN[]
//    static final int GM_BLACKTEAM   = 0;     //BT[]
//    static final int GM_COPYRIGHT   = 0;     //CP[]
//    static final int GM_GAMENAME    = 0;     //GN[]
//    static final int GM_GAMECONTEXT = 0;     //GC[]
//    static final int GM_OPENING     = 0;     //ON[]
//    static final int GM_OVERTIME    = 0;     //OT[]
//    static final int GM_ROUND       = 0;     //RO[]
//    static final int GM_RULE        = 0;     //RU[]

    /*
     * Timing Properties               BL, OB, OW, WL
     * /
//    static final int TM_BLACKLEFT = 0;     //BL[]
//    static final int TM_BMOVELEFT = 0;     //OB[]
//    static final int TM_WMOVELEFT = 0;     //OW[]
//    static final int TM_WHITELEFT = 0;     //WL[]

    /*
     * Miscellaneous Properties        FG, PM, VW
     * /
//    static final int MS_FIGURE = 0;        //FG[]
//    static final int MS_PRINT = 0;         //PM[]
//    static final int MS_VIEW = 0;          //VW[]
 *************************************************************************************/

public final class SgfAdapter1 {

    public static void writeMatch(Match mt, OutputStream output) {
        try {
            PrintWriter out = new PrintWriter(output);

            // write movements
            out.print("(;" + movementTreeToString(mt.getRootMovement()));
            out.close();

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Match readMatch(InputStream input) {
        Stack stack = new Stack();

        Match match = new Match();
        Movement current = null;
        Movement last = null;
        String attr_name = "";
        StringBuffer buf = new StringBuffer("");

//        DataInputStream dinput = new DataInputStream(input);
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        try {

            String source = reader.readLine();
            int pos = 0;
            boolean readingValue = false;

            while(true) {
                if (pos == source.length()) {
                    source = reader.readLine();
                    source += "\n";
                    pos = 0;
                }
                char c = source.charAt(pos);
                pos++;

                boolean stop = false;
                int lc;

                switch (c) {
                    case '(':
                        if (readingValue) {
                            buf.append(c);
                            break;
                        }

                        buf.setLength(0);

                        if (current != null) {
                            System.out.println("forking:");
                            stack.push(current);
                        }

                        break;

                    case ';':
                        if (readingValue) {
                            buf.append(c);
                            break;
                        }

                        if (current != null) {
                            last = current;
                            current = null;
                        }

                        break;

                    case '[':
                        String cid = (buf.toString()).trim();
                        if (!cid.equals("")) {
                            attr_name = cid;
                        }
                        buf.setLength(0);
                        stack.push(new Integer(c));

                        readingValue = true;

                        break;

                    case ']':
                        lc = (char) (((Integer)stack.pop()).intValue());
                        if (lc != '[') {
                            throw new SgfException("invalid format");
                        }

                        if (current == null) {
                            current = new Movement();
                            if (last != null) {
                                last.add(current);
                            }
                        }
                        String attr_value = (buf.toString()).trim();
                        parseAttribute(match, current, attr_name, attr_value);

                        buf.setLength(0);

                        readingValue = false;

                        break;

                    case ')':
                        if (readingValue) {
                            buf.append(c);
                            break;
                        }

                        if (!stack.empty()) {
                            System.out.println("closing fork:");
                            current = (Movement) stack.pop();
                        }
                        else {
                            stop = true;
                        }
                        break;

                    default:
                        buf.append(c);
                        break;
                }

                if (stop) {
                    break;
                }
            }
        }
        catch (SgfException sgf_e) {
            sgf_e.printStackTrace();
            return null;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Movement root = last;
        while (root.getParent() != null) {
            root = (Movement) root.getParent();
        }
        match.setMovements(root);

        return match;
    }

    protected static void parseAttribute(Match match, Movement current, String attrName, String attrValue) {
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
        else if (attrName.equals("CR")) {
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
        else if (attrName.equals("SZ")) {
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

/**************************************************************************************
 *
 * private methods for write sgf file
    private String comment;
    private Vector tips;

    protected int col, row;
    protected int player;

    private Vector captives;
    private Vector forcePut; *
 **************************************************************************************/

    protected static String movementTreeToString(Movement root) {
        String output = "";
        System.out.println("saving movement!");

        output += movementToString(root);

        if (root.getChildCount() == 0) {
            output += ")\n";
            return output;
        }
        else if (root.getChildCount() == 1) {
            output += ";\n";
            output += movementTreeToString((Movement)root.getChildAt(0));
            return output;
        }
        else {
            for (int i = 0; i < root.getChildCount(); i++) {
                output += "\n(;";
                output += movementTreeToString((Movement)root.getChildAt(i));
            }
            output += ")\n";
        }
        System.out.println("movement string is " + output);
        return output;
    }

    protected static String movementToString(Movement move) {

        String output = "";

        // write move
        if ((move.col != -1) && (move.row != -1)) {
            if (move.player == GoPlayer.BLACK) {
                output += ("B[" + (char)(move.col+'a') + (char)(move.row+'a') + "]");
            }
            else {
                output += ("W[" + (char)(move.col+'a') + (char)(move.row+'a') + "]");
            }
        }

        // write comment
        if (move.getComment() != null) {
            output += ("C[" + move.getComment() + "]");
        }

        // write tips
        for (int i = 0; i < move.getTipCount(); i++) {
            Movement.HandTip t = move.tipAt(i);
            if (t.tip == GoPoint.TRIANGLE) {
                output += ("TR[" + (char)(t.col+'a') + (char)(t.row+'a') + "]");
            }
            else if (t.tip == GoPoint.CIRCLE) {
                output += ("CR[" + (char)(t.col+'a') + (char)(t.row+'a') + "]");
            }
            else if (t.tip == GoPoint.CROSS) {
                output += ("MA[" + (char)(t.col+'a') + (char)(t.row+'a') + "]");
            }
            else if (t.tip == GoPoint.SQUARE) {
                output += ("SQ[" + (char)(t.col+'a') + (char)(t.row+'a') + "]");
            }
            else {
                output += ("LB[" + (char)(t.col+'a') + (char)(t.row+'a') + ":" + (char)t.tip + "]");
            }
        }

        //write force put
        for (int i = 0; i < move.getForceCount(); i++) {
            Movement.HandTip t = move.forceAt(i);
            if (t.tip == GoPlayer.BLACK) {
                output += ("AB[" + (char)(t.col+'a') + (char)(t.row+'a') + "]");
            }
            else {
                output += ("AW[" + (char)(t.col+'a') + (char)(t.row+'a') + "]");
            }
        }

        return output;
    }

}
