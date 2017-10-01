package com.koala.games.go;

import java.awt.*;
import java.net.URL;

import com.koala.cwt.grid.Grid;

/**
 * One GoBoard has following featurs:
 *  show grids, stars
 *  show chessmen, white and black
 *  show current chessman
 *  show chessman number
 *  show tips
 */
public final class GoBoard extends Grid {

    Image back_img, black_img, white_img;
    boolean show_number;
    int startIndex, startNumber;

    public GoBoard(GoBoardModel gm){
        super(gm.getGridModel(), 420, 420, 20, 20, 20, 20);
		setStrechable(true);
        try{
            Toolkit tk = Toolkit.getDefaultToolkit();
            back_img = tk.getImage(ClassLoader.getSystemResource("go/back1.jpg"));
            black_img = tk.getImage(ClassLoader.getSystemResource("go/black.jpg"));
            white_img = tk.getImage(ClassLoader.getSystemResource("go/white.jpg"));
        }catch(Exception e){
            back_img = null;
        }
        
        show_number = false;
    }
    
    public boolean isShowingNumber() {
    	return show_number;
    }
    
    public void setShowingNumber(boolean p_show){
//    	System.out.println("show number " + p_show);
    	show_number = p_show;
    	repaint();
    }
    
	public void setNumberStart(int si, int sn) {
		startIndex = si;
		startNumber = sn;
	}
    

	public void strech(int w, int h) {
        int mysize = w;
        if (w > h) {
            mysize = h;
        }
        if (mysize < 210) {
            mysize = 210;
        }
        
        float ratio = ((float)mysize) / ((float)420);
        
        setCubicWidth((int)(20 * ratio));
        setCubicHeight((int)(20 * ratio));
        
        setLeftBorder((int)(20 * ratio));
        setTopBorder((int)(20 * ratio));

        setGridWidth((int)(420 * ratio));
        setGridHeight((int)(420 * ratio));
    }

    public void drawBackground(Graphics g){
//        g.drawImage(back_img, getLeftBorder(), getTopBorder(), getGridWidth(), getGridHeight(), this);
        g.drawImage(back_img, 0, 0, getGridWidth(), getGridHeight(), this);
        
        g.setColor(Color.black);
//       Graphics g2 = g.create(getLeftBorder(), getLeftBorder(), getWidth() - getLeftBorder(), getWidth() - getLeftBorder());
        int b = getLeftBorder();
        int cb = getCubicWidth();

        for (int i = 0 ; i < getModel().getRows() ; i++){
			g.drawLine(b + cb / 2, b + i * cb + cb / 2, b + getModel().getRows() * cb - cb / 2, b + i * cb + cb / 2);
            g.drawLine(b + i * cb + cb / 2, b + cb / 2, b + i * cb + cb / 2, b + getModel().getRows() * cb - cb / 2);
		}
        g.drawLine(b + cb / 2 - 1, b + cb / 2 - 1, b + getModel().getRows() * cb - cb / 2 + 1, b + cb / 2 - 1);
        g.drawLine(b + cb / 2 - 1, b + getModel().getRows() * cb - cb / 2 + 1, b + getModel().getRows() * cb - cb / 2 + 1, b + getModel().getRows() * cb - cb / 2 + 1);
        g.drawLine(b + cb / 2 - 1, b + cb / 2 - 1, b + cb / 2 - 1, b + getModel().getRows() * cb - cb / 2 + 1);
        g.drawLine(b + getModel().getRows() * cb - cb / 2 + 1, b + cb / 2 - 1, b + getModel().getRows() * cb - cb / 2 + 1, b + getModel().getRows() * cb - cb / 2 + 1);
        
        if (getModel().getRows() == GoBoardModel.DEFAULT_BOARD_SIZE){
            Point[] p = new Point[9];
            p[0] = new Point(3,3);
            p[1] = new Point(3,9);
            p[2] = new Point(3,15);
            p[3] = new Point(9,3);
            p[4] = new Point(9,9);
            p[5] = new Point(9,15);
            p[6] = new Point(15,3);
            p[7] = new Point(15,9);
            p[8] = new Point(15,15);
            for (int i = 0 ; i < p.length; i++){
                g.fillOval(b + p[i].x * cb + cb / 2 - 4, b + p[i].y * cb + cb / 2 - 4, 8, 8);
            }
        }
    }

    public void drawCubic(Graphics g, int col, int row){
        GoPoint p = (GoPoint) getModel().getObject(col, row);

        int cb = getCubicWidth();
        int border = getLeftBorder();

        Color cmColor = Color.black, tipColor = Color.black;
        Image cmImg = black_img;
        boolean isEmpty = false;

        if (p.getState() == GoPoint.BLACK) {
            cmColor = Color.black;
            tipColor = Color.white;
            cmImg = black_img;
        }
        else if (p.getState() == GoPoint.WHITE) {
            tipColor = Color.black;
            cmColor = Color.white;
            cmImg = white_img;
        }
        else {
            isEmpty = true;
        }

        if (!isEmpty) {
    		g.setColor(cmColor);
            g.drawImage(cmImg, cb / 20, cb / 20, cb * 9 / 10, cb * 9 / 10, this);
            //g.fillOval(cb / 10, cb / 10, cb * 4 / 5, cb * 4 / 5);
            int num = p.getNumber();
            if (show_number) {
	            if (num != GoPoint.NONE) {
	            	num = num - startIndex + startNumber;
					g.setColor(tipColor);
					if (num < 10){
						Font ft = g.getFont();
						g.setFont(new Font(ft.getName(), ft.getStyle(), 16));
	                    g.drawString("" + num, cb * 2/5 - 1, cb * 2/3 - 1);
					}
					else if (num < 100){
						Font ft = g.getFont();
						g.setFont(new Font(ft.getName(), ft.getStyle(), 14));
	                    g.drawString("" + num, cb/4 - 1, cb * 2/3 - 2);
					}
					else {
                        Font ft = g.getFont();
                        g.setFont(new Font(ft.getName(), ft.getStyle(), 12));
                        g.drawString("" + num, cb/5 - 1, cb * 2/3 - 2);
                    }
				}
			}
        }

        if (p.getTip() != GoPoint.NONE) {
            g.setColor(Color.red);
            int c = p.getTip();
            if (c == GoPoint.TRIANGLE) {
                int[] xx = {cb/2, cb * 3/4, cb/4};
                int[] yy = {cb/5, cb * 3/5, cb * 3/5};
                g.fillPolygon(xx, yy, 3);
            }
            else if (c == GoPoint.SQUARE) {
                g.fillRect(cb/4, cb/4, cb/2, cb/2);
            }
            else if (c == GoPoint.CIRCLE) {
                g.drawOval(cb/4, cb/4, cb/2, cb/2);
            }
            else if (c == GoPoint.CROSS) {
                g.drawLine(cb/4, cb/4, cb * 3/4, cb * 3/4);
                g.drawLine(cb/4, cb * 3/4, cb * 3/4, cb/4);
            }
            else {
                if (isEmpty) {
                    g.setColor(new Color(206,207, 250));
                    g.drawLine(cb/4, cb/2, cb * 3/4, cb/2);
                    g.drawLine(cb/2, cb/4, cb/2, cb * 3/4);
                    g.setColor(Color.red);
                }
                char cp = (char)c;
                Font ft = g.getFont();
                g.setFont(new Font(ft.getName(), ft.getStyle(), 12));
                g.drawString("" + cp, cb * 2/5, cb * 2/3);
            }
        }

        if (isEmpty) {
            if (p.getHolder() == GoPlayer.BLACK) {
                g.setColor(Color.black);
			    g.fillOval(cb / 3, cb / 3, cb / 3, cb / 3);
			}
            else if (p.getHolder() == GoPlayer.WHITE) {
                g.setColor(Color.white);
			    g.fillOval(cb / 3, cb / 3, cb / 3, cb / 3);
			}
		}	
        else {
            if (p.getStyle() == GoPoint.HIGHLIGHT) {
                g.setColor(Color.red);
                g.fillOval(cb / 3, cb / 3, cb / 3, cb / 3);        
            }
        }
    }

    protected URL getResource(String name) {
        if (name != null) {
            URL url = this.getClass().getResource(name);
            return url;
        }
        return null;
    }

}

