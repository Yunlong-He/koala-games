package com.koala.games.clearball;

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


import com.koala.cwt.grid.GridExplorer;
import com.koala.cwt.GUITools;
import com.koala.cwt.grid.GridEvent;
import com.koala.cwt.grid.GridListener;
import com.koala.cwt.grid.Grid;
import com.koala.cwt.grid.ImageGrid;
import com.koala.cwt.ImageList;


public class ClearBall extends JFrame implements GridListener, ActionListener {
   JMenuItem muNewGame,muMachine, muStopGame,muExit;
   JMenuItem muSound,muUI;
   JMenuItem muOptions;
   JMenuItem muRules, muContact, muAbout;


	boolean playing, paused;

	CBGridModel model;
	ImageList imgLib;
	ImageGrid grid,pgrid;


	JLabel lbMScore,lbScore;

	int score;
	int xStart, yStart;
	boolean waitNextClick;

	Timer mTimer;
	GridExplorer walker;
    MoveMachine machine;

    public ClearBall() {
		super("Clear Ball");

		model = new CBGridModel(10,10);
		imgLib = new CBImageList(40,40);
		grid = new ImageGrid(model, imgLib);
		grid.addGridListener(this);
		pgrid = new ImageGrid(model.getPreviewModel(), imgLib);

		walker = new GridExplorer(model);
        machine = new MoveMachine(model);

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

		xStart = -1;
		yStart = -1;
		waitNextClick = false;

		setScore(0);
		model.addRandomPoints();

		playing = true;
		paused = false;
		mTimer = new Timer(200,this);
		mTimer.stop();
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
      muMachine = new JMenuItem("Auto Move");
      muMachine.addActionListener(this);
      mu.add(muMachine);
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
      muAbout = new JMenuItem("About ClearBall");
      muAbout.addActionListener(this);
      mu.add(muAbout);
      mb.add(mu);

      return mb;
   }

	public void actionPerformed(ActionEvent e){
      Object source = e.getSource();

      if (source instanceof Timer) {
         if (waitNextClick) {
   			int c = model.getValue(xStart, yStart);
   			if (c > 7) c = c - 7;
   			else c = c + 7;
   			model.setValue(xStart, yStart, c);
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
      else if (c == muMachine) {
         helpMove();
      }
      else if (c == muExit) {
         if (playing) {
            int submit = JOptionPane.showConfirmDialog(this,"Do you really want to stop game and exit?","Clear Ball",JOptionPane.YES_NO_OPTION);
            if (submit == JOptionPane.NO_OPTION)
               return;
         }
         System.exit(0);
      }
   }

	public void setScore(int sc) {
		lbScore.setText("得分：" + sc*2);
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
		   (new MoveThread(this, e.col, e.row)).start();

//            machine.initValue();
//           System.out.println("potential is " + machine.getPotentialValue());
		}
		else {
            machine.initValue();
//            System.out.println("potential is " + machine.getPotentialValue());

//            machine.initValue();
            MoveMachine.Move mv = machine.getBestMove(e.col, e.row);
            if (mv != null) {
                System.out.println("best move: "+ e.col + "," + e.row + " [" + mv.endX + "," + mv.endY + "]");
            }

            int c = model.getValue(e.col, e.row);

         if (c != CBGridModel.EMPTY) {
				xStart = e.col;
				yStart = e.row;
				waitNextClick = true;
				mTimer.start();
			}
		}
	}

	public void helpMove() {
        MoveMachine.Move mv = machine.getBestMove();
        if (mv == null) {
            return;
        }
        if (mv != null) {
           System.out.println("best move: ["+ mv.startX + "," + mv.startY + "] ---> [" + mv.endX + "," + mv.endY + "]");
        }

        xStart = mv.startX;
        yStart = mv.startY;

        paused = true;
        mTimer.stop();
        (new MoveThread(this, mv.endX, mv.endY)).start();

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
         if (value >7) {
            model.setValue(xStart, yStart, value - 7);
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
         int c1 = model.getValue(xStart,yStart);
         if (c1 > 7) c1 = c1 - 7;

         // whether the same ball clicked
         if ((xEnd == xStart) && (yEnd == yStart)) {
            waitNextClick = false;
            model.setValue(xStart,yStart,c1);
		      paused = false;
            return;
         }

         // whether a new Ball clicked
         int c2 = model.getValue(xEnd, yEnd);
         if (c2 != CBGridModel.EMPTY) {
            model.setValue(xStart,yStart,c1);
            xStart = xEnd;
            yStart = yEnd;
            mTimer.start();
   		   paused = false;
            return;
         }

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
         model.setValue(xStart,yStart,c1);

         for (int i = 0; i < path.length - 1; i++) {
      		model.setValue(path[i+1].x, path[i+1].y, model.getValue(path[i].x, path[i].y));
      		model.setValue(path[i].x, path[i].y, CBGridModel.EMPTY);

      		try {
      			Thread.sleep(interval);
      		}
      		catch (Exception e) {}
         }

  			int cleared = model.clearFive(xEnd,yEnd);
  			if (cleared > 0) {
  				score += cleared;
  				setScore(score);
   		   paused = false;
  				return;
  			}

   		if(!model.addRandomPoints()) {
            JOptionPane.showMessageDialog(frame,"Game Over", "Clear Ball", JOptionPane.INFORMATION_MESSAGE);
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
			ClearBall ht = new ClearBall();
        	} catch (Throwable t) {
            		System.out.println("uncaught exception: " + t);
            		t.printStackTrace();
        	}
    	}



}