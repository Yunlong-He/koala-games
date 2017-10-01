package com.koala.games.fillpit;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Point;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.Timer;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import com.koala.cwt.GUITools;
import com.koala.cwt.ImageList;
import com.koala.cwt.grid.*;


public class FillPit extends JFrame implements GridListener, ActionListener {

	JMenuItem muNewGame,muStopGame,muExit;
	JMenuItem muSound,muUI;
	JMenuItem muOptions;
	JMenuItem muRules, muContact, muAbout;

	FPGridModel model;
	ImageList imgLib;
	ImageGrid grid,pgrid;

	JLabel lbMScore,lbScore;

	int score;
	int xStart, yStart, cStart;
	boolean waitNextClick;

	boolean playing, paused;
	Timer mTimer;
	GridExplorer walker;

    public FillPit() {
		super("Fill Pit");

		model = new FPGridModel(10,10);
		imgLib = new FPImageList(40,40);
		grid = new ImageGrid(model, imgLib);
		grid.addGridListener(this);
		pgrid = new ImageGrid(model.getPreviewModel(), imgLib);

		walker = new GridExplorer(model);

		lbMScore = new JLabel("最高分:2820");
		lbScore = new JLabel("得分:0");

        getContentPane().setLayout(new BorderLayout());
        JMenuBar mb = createMenubar();
        getContentPane().add("North",mb);

        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		panel.setLayout(gbl);
		GUITools.addComponentTo(panel, lbMScore, gbl, gc, 0, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(panel, pgrid,    gbl, gc, 1, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(panel, lbScore,  gbl, gc, 2, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(panel, grid,     gbl, gc, 0, 1, 3, 1, 3.0, 1.0);

        getContentPane().add("Center", panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		show();

		mTimer = new Timer(200,this);
		playing = false;

        startNewGame();
    }

	protected JMenuBar createMenubar() {
		JMenuItem mi;
		JMenu mu;
		JMenuBar mb = new JMenuBar();

		mu = new JMenu("Game");
		muNewGame = new JMenuItem("New Game");
		muNewGame.addActionListener(this);
		mu.add(muNewGame);
		mu.addSeparator();
		muStopGame = new JMenuItem("Stop Game");
		muStopGame.addActionListener(this);
		mu.add(muStopGame);
		mu.addSeparator();
		muExit = new JMenuItem("Exit");
		muExit.addActionListener(this);
		mu.add(muExit);
		mb.add(mu);

		mu = new JMenu("Effect");
		muSound = new JMenuItem("Sound");
		muSound.addActionListener(this);
		mu.add(muSound);
		muUI = new JMenuItem("Window Style");
		muUI.addActionListener(this);
		mu.add(muUI);
		mb.add(mu);

		mu = new JMenu("Advanced");
		muOptions = new JMenuItem("Options");
		muOptions.addActionListener(this);
		mu.add(muOptions);
		mb.add(mu);

		mu = new JMenu("Help");
		muRules = new JMenuItem("Rules");
		muRules.addActionListener(this);
		mu.add(muRules);
		mu.addSeparator();
		muContact = new JMenuItem("Contact Author");
		muContact.addActionListener(this);
		mu.add(muContact);
		muAbout = new JMenuItem("About FillPit");
		muAbout.addActionListener(this);
		mu.add(muAbout);
		mb.add(mu);

		return mb;
	}

	public void actionPerformed(ActionEvent e){
	   Object source = e.getSource();

	   if (source instanceof Timer) {
   		if (waitNextClick) {
   		   int value = model.getValue(xStart,yStart);
   		   if (value != cStart) {
   		      model.setValue(xStart, yStart, cStart);
   		   }
   		   else {
   		      if (value == FPGridModel.SMILE)
   		         model.setValue(xStart, yStart, FPGridModel._SMILE);
   		      else
   		         model.setValue(xStart, yStart, FPGridModel._GRAY);
   		   }
   		}
   		return;
   	}

		JMenuItem c = (JMenuItem) source;
		if (c == muNewGame) {
		   startNewGame();
		}
		else if (c == muStopGame) {
		   stopGame();
		}
      else if (c == muExit) {
         if (playing) {
            int submit = JOptionPane.showConfirmDialog(this,"Do you really want to stop game and exit?","Fill Pit",JOptionPane.YES_NO_OPTION);
            if (submit == JOptionPane.NO_OPTION)
               return;
         }
         System.exit(0);
      }
   }

	public void setScore(int sc) {
		lbScore.setText("得分：" + sc);
	}

   /**
    * one MouseClicked event occured
    */
	public void cubicClicked(Grid _grid, GridEvent e) {
		if (!playing) return;
		if (paused) return;

		if (waitNextClick) {
		   paused = true;
		   mTimer.stop();
		   (new MoveThread(this,e.col, e.row)).start();
		}
		else {
			int c = model.getValue(e.col, e.row);

         if (model.getBallCount(e.col, e.row) == 1) {
				xStart = e.col;
				yStart = e.row;
				cStart = c;
				waitNextClick = true;
				mTimer.start();
			}

		}
	}

   void startNewGame() {
      if (playing) {
         int submit = JOptionPane.showConfirmDialog(this,"Do you really want to stop game and exit?","Clear Ball",JOptionPane.YES_NO_OPTION);
         if (submit == JOptionPane.NO_OPTION) {
            return;
         }
         stopGame();
      }

      model.reset();

		xStart = -1;
		yStart = -1;
		waitNextClick = false;

		setScore(0);
		model.addRandomPoints();

		paused = false;
		playing = true;
		mTimer.stop();
   }

   void stopGame() {
		paused = false;
      playing = false;
      mTimer.stop();

      if (waitNextClick) {
		   int value = model.getValue(xStart,yStart);
		   if (value != cStart) {
		      model.setValue(xStart, yStart, cStart);
		   }
      }
   }

	class MoveThread extends Thread {
		int interval = 100;
      int xEnd, yEnd;
      JFrame frame;

      public MoveThread(JFrame frm, int xx, int yy) {
         xEnd = xx;
         yEnd = yy;
         frame = frm;
		}

   	public void run(){
         // whether the same ball clicked
         if ((xEnd == xStart) && (yEnd == yStart)) {
            waitNextClick = false;
            model.setValue(xStart,yStart,cStart);
   		   paused = false;
            return;
         }

         // whether this ball can be put into the destination pit
         int cEnd = model.getBallColor(xEnd, yEnd);

         if (cStart != FPGridModel.SMILE) {
            if ( (cEnd != FPGridModel.EMPTY) && (cEnd != FPGridModel.SMILE) && (cEnd != cStart) ){
               model.setValue(xStart,yStart,cStart);
               // whether a new single ball clicked
               if (model.getBallCount(xEnd, yEnd) == 1) {
         				xStart = xEnd;
         				yStart = yEnd;
         				cStart = cEnd;
               }
               paused = false;
     				mTimer.start();
     				return;
            }
         }

         // try to move the ball to the destination

         // get shortest path
         Point[] path = walker.getShortestPath(xStart, yStart, xEnd, yEnd);

         // no way to move
         if (path == null) {
            mTimer.start();
   		   paused = false;
            return;
         }

         // move the ball along the path, each step wait 100 millonsecond
         if (path.length < 2) {
            mTimer.start();
   		   paused = false;
            return;
         }

         waitNextClick = false;
         model.setValue(xStart,yStart,cStart);

         // move the ball along the path, each step wait 100 millonsecond
         Point[] ps = new Point[path.length-1];
         System.arraycopy(path, 0, ps, 0, path.length-1);

         int i;
         for (i = 0; i < ps.length - 1; i++) {
      		model.setValue(ps[i+1].x, ps[i+1].y, model.getValue(ps[i].x, ps[i].y));
      		model.setValue(ps[i].x, ps[i].y, FPGridModel.EMPTY);

      		try {
      			Thread.sleep(interval);
      		}
      		catch (Exception e) {}
         }

         // move the ball into pit
    		model.setValue(ps[i].x, ps[i].y, FPGridModel.EMPTY);
  			int cleared = model.addOneBall(xEnd, yEnd, cStart);
  			if (cleared > 0) {
  				score += cleared;
  				setScore(score);
   		   paused = false;
  				return;
  			}

   		if(!model.addRandomPoints()) {
            JOptionPane.showMessageDialog(frame,"Game Over", "Fill Pit", JOptionPane.INFORMATION_MESSAGE);
            stopGame();
			}
 		   paused = false;
		}
	}

   public static void main(String[] args) {
        	try {
	    		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        		String vers = System.getProperty("java.version");
        		if (vers.compareTo("1.1.2") < 0) {
            			System.out.println("!!!WARNING: Swing must be run with a " +
                               		"1.1.2 or higher version VM!!!");
        		}
			FillPit ht = new FillPit();
        	} catch (Throwable t) {
            		System.out.println("uncaught exception: " + t);
            		t.printStackTrace();
        	}
    	}



}