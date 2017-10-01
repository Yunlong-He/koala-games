package com.koala.games.go;

import java.util.*;
import javax.swing.tree.*;

public class Movement  extends DefaultMutableTreeNode {
    
    private String comment;     // if no comment, comment = null
    private Vector tips;

    protected int col, row;     // invalid position (-1, -1)
    protected int player;       // invalid player(UNKNOWN);

    private Vector captives;    
    private Vector forcePut;

    public Movement(){
        this(-1, -1, GoPlayer.UNKNOWN);
    }
    
    public Movement(int col, int row, int player){
        this.col = col;
        this.row = row;
        this.player = player;
        captives = new Vector();

        comment = "";
        tips = new Vector();
        forcePut = new Vector();        
    }
    
    public boolean isOpened() {
    	return true;
    }
    
    public void addCaptive(int c, int r, int n) {
        captives.addElement(new Captive(c, r, n));
    }

    public int captiveCount(){
        return captives.size();
    }

    public Captive captiveAt(int index){
        Captive p = (Captive) captives.elementAt(index);
        return p;
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String cmt) {
        if (cmt == null) 
            comment = "";
        else           
            comment = cmt;
    }

    public void addForce(int x, int y, int player) {
        forcePut.addElement(new HandTip(x,y,player));
    }

    public int getForceCount(){
        return forcePut.size();
    }

    public HandTip forceAt(int index) {
        HandTip p = (HandTip) forcePut.elementAt(index);
        return p;
    }

    public void addTip(int x, int y, int tip) {
        tips.addElement(new HandTip(x,y,tip));
    }

    public int getTipCount(){
        return tips.size();
    }

    public HandTip tipAt(int index) {
        HandTip p = (HandTip) tips.elementAt(index);
        return p;
    }

    public final class HandTip{
        public int col, row;
        public int tip;

        public HandTip(int _col, int _row, int _tip) {
            col = _col;
            row = _row;
            tip = _tip;
        }
    }

    public class Captive {
        public int col;         // invalid position (-1, -1);
        public int row;
        public int number;      // if number, number = NONE
        
        public Captive(int c, int r, int n) {
            col = c;
            row = r;
            number = n;
        }
    }

}