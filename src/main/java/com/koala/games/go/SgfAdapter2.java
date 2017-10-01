package com.koala.games.go;


import java.io.*;
import java.util.Stack;

/**
 * Class SgfQipuAdapter is used to parse match information from one sgf input stream.
 *
 * If content is readable and parsed ok, one match instance returned.
 * If content is readable and parsed failed, return null.
 */
public class SgfAdapter2 {

/***********************************************************************************
 *                                                                                 *
 *   SGF Go File Properties                                                        *
 *                                                                                 *
 ***********************************************************************************/

    /*
     * Move Properties                 B, KO, MN, W  
     */
    static final int MV_BLACK = 101;      //B[]
    static final int MV_WHITE = 102;      //W[]
//    static final int MV_FORCE = 103;      //KO[]
//    static final int MV_MARKNUM = 104;    //MN[]

    /*
     * Setup Properties                AB, AE, AW, PL  
     */
    static final int AD_BLACK = 201;      //AB[]
    static final int AD_WHITE = 202;      //AW[]
    static final int AD_EMPTY = 203;      //AE[]
//    static final int AD_PLAYER = 204;     //PL[]

    /*
     * Node Annotation Properties      C, DM, GB, GW, HO, N, UC, V  
     */
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
     */
//    static final int MA_BADMOVE    = 401;   //BM[]
//    static final int MA_DOUBTFUL   = 402;   //DO[]
//    static final int MA_INTEREST   = 403;   //IT[]
//    static final int MA_TESUJI     = 404;   //TE[]  --good move

    /*    
     * Markup Properties               AR, CR, DD, LB, LN, MA, SL, SQ, TR  
     */
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
     */

    static final int RT_SIZE          = 601;      //SZ[]
//    static final int RT_APPLICATION   = 602;      //AP[]
//    static final int RT_CHARSET       = 603;      //CA[]
//    static final int RT_FILEFORMAT    = 604;      //FF[]
//    static final int RT_GAMETYPE      = 605;      //GM[]
//    static final int RT_SHOWTYPE      = 606;      //ST[]
    
    /*
     * Game Info Properties            AN, BR, BT, CP, DT, EV, GN, GC, ON, OT, PB, PC, PW, RE, RO, RU, SO, TM, US, WR, WT  
     */
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
     */
//    static final int TM_BLACKLEFT = 0;     //BL[]
//    static final int TM_BMOVELEFT = 0;     //OB[]
//    static final int TM_WMOVELEFT = 0;     //OW[]
//    static final int TM_WHITELEFT = 0;     //WL[]

    /*
     * Miscellaneous Properties        FG, PM, VW  
     */
//    static final int MS_FIGURE = 0;        //FG[]
//    static final int MS_PRINT = 0;         //PM[]
//    static final int MS_VIEW = 0;          //VW[]

/***********************************************************************************
 *                                                                                 *
 *   Parser for SGF Go File                                                        *
 *                                                                                 *
 ***********************************************************************************/


    private String src;

    private Match match;
    private Movement root;
    private Movement current;

    private Stack forks = new Stack();
    private int pos;
    
    public SgfAdapter2(String source) {
        src = source;
        match = null;
        parseMatch();
    }

    public Match parseMatch() {
        match = new Match();
        root = null;
        current = root;
        pos = 0;
        
        while(true) {
            String item = getNextItem();
            if (item == null) {
                break;
            }
            
            Movement move = parseMovement(item);
            if (current != null) {
                current.add(move);
            }
            else {
                root = move;
            }
            current = move;
        }
        
        match.setMovements(root);
        
        return match;        
    }
    
    public void saveMatch(Match mt, String filename) {

        if (filename == null) {
            System.out.println("please input one sgf file name");
            return ;
        }
        
        System.out.println("begin to save match to " + filename);
        
        try {
            PrintWriter out = new PrintWriter(new FileOutputStream(filename));
        
            // write match information
        
            // write movements
            out.print("(;" + movementTreeToString(mt.getRootMovement()));
            
            out.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
/**************************************************************************************
 *
 * private methods for read sgf file
 *
 **************************************************************************************/
    
    private Movement parseMovement(String source) {
        Movement move = new Movement();
//        System.out.println("movment parsed:" + source);
        
        int t_pos = 0;
        while(true) {
            t_pos = getNextProperty(source);
            if (t_pos < 0) {
                break;
            }
            
            String prop = source.substring(0, t_pos);
            if (prop.startsWith("B[")) {
                if (prop.length() < 4) {
                    return null;
                }
                move.player = GoPlayer.BLACK;
                char xx = prop.charAt(2);
                char yy = prop.charAt(3);
                move.col = xx - 'a';
                move.row = yy - 'a';
            }
            else if (prop.startsWith("W[")) {
                if (prop.length() < 4) {
                    return null;
                }
                move.player = GoPlayer.WHITE;
                char xx = prop.charAt(2);
                char yy = prop.charAt(3);
                move.col = xx - 'a';
                move.row = yy - 'a';
            }
            else if (prop.startsWith("AB[")) {
                int ab_pos = 2;
                
                while (true) {
                    int ab_pos2 = prop.indexOf(']', ab_pos);
                    if (ab_pos2 < 0) {
                        break;
                    }
                    if ((ab_pos2-ab_pos) != 3) {
                        break;
                    }
                    char xx = prop.charAt(ab_pos + 1);
                    char yy = prop.charAt(ab_pos + 2);
                    move.addForce(xx-'a', yy-'a', GoPlayer.BLACK);
                    
                    ab_pos = ab_pos2 + 1;
                    if (ab_pos >= prop.length()) {
                        break;
                    }
                }
            }
            else if (prop.startsWith("AW[")) {
                int ab_pos = 2;
                
                while (true) {
                    int ab_pos2 = prop.indexOf(']', ab_pos);
                    if (ab_pos2 < 0) {
                        break;
                    }
                    if ((ab_pos2-ab_pos) != 3) {
                        break;
                    }
                    char xx = prop.charAt(ab_pos + 1);
                    char yy = prop.charAt(ab_pos + 2);
                    move.addForce(xx-'a', yy-'a', GoPlayer.WHITE);
                    
                    ab_pos = ab_pos2 + 1;
                    if (ab_pos >= prop.length()) {
                        break;
                    }
                }
            }
            else if (prop.startsWith("AE[")) {
                int ab_pos = 2;
                
                while (true) {
                    int ab_pos2 = prop.indexOf(']', ab_pos);
                    if (ab_pos2 < 0) {
                        break;
                    }
                    if ((ab_pos2-ab_pos) != 3) {
                        break;
                    }
                    char xx = prop.charAt(ab_pos + 1);
                    char yy = prop.charAt(ab_pos + 2);
                    move.addForce(xx-'a', yy-'a', GoPlayer.UNKNOWN);
                    
                    ab_pos = ab_pos2 + 1;
                    if (ab_pos >= prop.length()) {
                        break;
                    }
                }
            }
            else if (prop.startsWith("C[")) {
                move.setComment(prop.substring(2, prop.length()-1));
            }
            else if (prop.startsWith("CR[")) {
                int ab_pos = 2;
                
                while (true) {
                    int ab_pos2 = prop.indexOf(']', ab_pos);
                    if (ab_pos2 < 0) {
                        break;
                    }
                    if ((ab_pos2-ab_pos) != 3) {
                        break;
                    }
                    char xx = prop.charAt(ab_pos + 1);
                    char yy = prop.charAt(ab_pos + 2);
                    move.addTip(xx-'a', yy-'a', GoPoint.CIRCLE);
                    
                    ab_pos = ab_pos2 + 1;
                    if (ab_pos >= prop.length()) {
                        break;
                    }
                }                
            }
            else if (prop.startsWith("LB[")) {
                int ab_pos = 2;
                
                while (true) {
                    int ab_pos2 = prop.indexOf(']', ab_pos);
                    if (ab_pos2 < 0) {
                        break;
                    }
                    if ((ab_pos2-ab_pos) < 4) {
                        break;
                    }
                    char xx = prop.charAt(ab_pos + 1);
                    char yy = prop.charAt(ab_pos + 2);
                    char tip = prop.charAt(ab_pos + 4);
                    move.addTip(xx-'a', yy-'a', tip);
//                    System.out.println("prop is " + prop);
//                    System.out.println("---------tip found");
                    ab_pos = ab_pos2 + 1;
                    if (ab_pos >= prop.length()) {
                        break;
                    }
                }                
            }
            else if (prop.startsWith("SQ[")) {
                int ab_pos = 2;
                
                while (true) {
                    int ab_pos2 = prop.indexOf(']', ab_pos);
                    if (ab_pos2 < 0) {
                        break;
                    }
                    if ((ab_pos2-ab_pos) != 3) {
                        break;
                    }
                    char xx = prop.charAt(ab_pos + 1);
                    char yy = prop.charAt(ab_pos + 2);
                    move.addTip(xx-'a', yy-'a', GoPoint.SQUARE);
                    
                    ab_pos = ab_pos2 + 1;
                    if (ab_pos >= prop.length()) {
                        break;
                    }
                }                
            }
            else if (prop.startsWith("TR[")) {
                int ab_pos = 2;
                
                while (true) {
                    int ab_pos2 = prop.indexOf(']', ab_pos);
                    if (ab_pos2 < 0) {
                        break;
                    }
                    if ((ab_pos2-ab_pos) != 3) {
                        break;
                    }
                    char xx = prop.charAt(ab_pos + 1);
                    char yy = prop.charAt(ab_pos + 2);
                    move.addTip(xx-'a', yy-'a', GoPoint.TRIANGLE);
                    
                    ab_pos = ab_pos2 + 1;
                    if (ab_pos >= prop.length()) {
                        break;
                    }
                }                
            }
            else if (prop.startsWith("SZ[")) {
                int ab_pos = prop.indexOf(']');
                match.boardSize = Integer.parseInt(prop.substring(3, ab_pos));
            }
            else if (prop.startsWith("PB[")) {
                int ab_pos = prop.indexOf(']');
                match.blackName = prop.substring(3, ab_pos);
            }
            else if (prop.startsWith("PW[")) {
                int ab_pos = prop.indexOf(']');
                match.whiteName = prop.substring(3, ab_pos);
            }
            else if (prop.startsWith("BR[")) {
                int ab_pos = prop.indexOf(']');
                match.blackRank = prop.substring(3, ab_pos);
            }
            else if (prop.startsWith("WR[")) {
                int ab_pos = prop.indexOf(']');
                match.whiteRank = prop.substring(3, ab_pos);
            }
            else if (prop.startsWith("EV[")) {
                int ab_pos = prop.indexOf(']');
                match.matchName = prop.substring(3, ab_pos);
            }
            else if (prop.startsWith("DT[")) {
                int ab_pos = prop.indexOf(']');
                match.matchDate = prop.substring(3, ab_pos);
            }
            else if (prop.startsWith("PC[")) {
                int ab_pos = prop.indexOf(']');
                match.place = prop.substring(3, ab_pos);
            }
            else if (prop.startsWith("RE[")) {
                int ab_pos = prop.indexOf(']');
                match.result = prop.substring(3, ab_pos);
            }
            else if (prop.startsWith("SO[")) {
                int ab_pos = prop.indexOf(']');
                match.source = prop.substring(3, ab_pos);
            }
            else if (prop.startsWith("TM[")) {
                int ab_pos = prop.indexOf(']');
                match.timeLen = Integer.parseInt(prop.substring(3, ab_pos));
            }
            
            t_pos = skipSpaces(source, t_pos);
            if (t_pos < 0) {
                break;
            }
            source = source.substring(t_pos);
        }
        
        
        return move;
    }
    
    
    private int getNextProperty(String source) {
//        System.out.println(source);
        int in_name = 0;
        int in_value = 1;
        int out_value = 2;
        
        int state = in_name;
        int t_pos = 0;
        
        char c = source.charAt(t_pos);
        while (true) {
            if (c == '[') {
                if (state == in_value) {
                    return -1;
                }
                state = in_value;
            }
            else if (c == ']') {
                if (state != in_value) {
                    return -1;
                }
                state = out_value;
            }
            else {
                if (state == out_value) {
                    break;
                }
            }
            t_pos++;
            if (t_pos >= source.length()) {
                break;
            }
            c = source.charAt(t_pos);
        }
        
        return t_pos;        
    }
    
    /**
     * @return:     null    no item found
     *              string  string for this item, like "B[as]LB[gh:c]C[...]"
     */
    private String getNextItem() {
        StringBuffer buf = new StringBuffer();
        char c;
        
        while(true) {
            pos = skipSpaces(src, pos);
            if (pos < 0) {
                return null;
            }
                                
            c = src.charAt(pos);
            
            if (c == '(') {
//                System.out.println("pushing one movement to stack!");
                forks.push(current);
                pos++;
            }
            else if (c == ')') {
                while(src.charAt(pos) == ')') {
//                System.out.println("poping one movement from stack!");
                    current = (Movement) forks.pop();
                    pos++;
                    if (pos >= src.length()) {
                        return null;
                    }
                }
            }
            else if (c == ';') {
                pos++;
                break;
            }
            else {
                return null;
            }
        }    
        pos = skipSpaces(src, pos);
        if (pos < 0) {
            return null;
        }
        
        c = src.charAt(pos);
        while ((c != ';') && (c != '(') && (c != ')')) {
            if (c == '[') {
                int end = src.indexOf(']', pos);
                if (end < pos) {
                    return null;
                }
                buf.append(src.substring(pos, end+1));
                pos = end;
            }
            else {
                buf.append(c);
            }
            pos++;
            if (pos >= src.length()) {
                break;
            }
            c = src.charAt(pos);
        }
        
        return buf.toString();
        
    }
    
    private int skipSpaces(String source, int position) {
        if (position >= source.length()) {
            return -1;
        }
        char c = source.charAt(position);
        while ((c == ' ') || (c == '\t') || (c == '\n')) {
            position++;
            if (position >= source.length()) {
                return -1;
            }
            c = source.charAt(position);
        }        
        return position;
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
    private String movementTreeToString(Movement root) {
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

    private String movementToString(Movement move) {
        
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