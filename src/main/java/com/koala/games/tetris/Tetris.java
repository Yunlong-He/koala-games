
package com.koala.games.tetris;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.koala.cwt.GUITools;
import com.koala.cwt.grid.ImageGrid;


public class Tetris extends JFrame implements ActionListener,KeyListener{
	public final static int READY = 0;
	public final static int PLAYING = 0;

	int[] intervals = {1000,700,500,350,250,200,160,130,110,100};

	ImageGrid board;
	TetrisModel model;
	TetrisImageList imgList;
	
	
	JLabel lbScores,lbLevel;
	int scores,level;

	Timer mTimer;
	
	int state;		
	
	public Tetris(){
		super();
		addKeyListener(this);
		
		model = new TetrisModel(10,20);
		imgList = new TetrisImageList(20, 20);
		board = new ImageGrid(model, imgList);
		board.setSize(200,400);

		JPanel pp = new JPanel();
		pp.add(board);

		lbScores = new JLabel("得分：0");
		lbLevel = new JLabel("等级:0");
		
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		getContentPane().setLayout(gbl);
		
		GUITools.addComponentTo(this.getContentPane(), pp,      gbl, gc, 0, 0, 1, 2, 1.0, 1.0);
		GUITools.addComponentTo(this.getContentPane(), lbScores,gbl, gc, 0, 1, 1, 1, 1.0, 1.0);
		GUITools.addComponentTo(this.getContentPane(), lbLevel, gbl, gc, 1, 1, 1, 1, 1.0, 1.0);

    	setTitle("Tetris");
		setBackground(Color.black);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setSize(500, 600);
	    show();
	    	
	    newGame();
	    state = PLAYING;
	}
	
	public void keyPressed(KeyEvent e){
		int keyCode = e.getKeyCode();
		switch(keyCode){
		case 37:
			if (state == PLAYING){
				model.goLeft();
			}
			break;
		case 38:
			if (state == PLAYING){
				model.clockwise();
			}
			break;
		case 39:
			if (state == PLAYING){
				model.goRight();
			}
			break;
		case 40:
			if (state == PLAYING){
				while (model.goDown());
			}
			break;
		}		
	}
	
	public void keyReleased(KeyEvent e){
	}

	public void keyTyped(KeyEvent e){
		int keyCode = e.getKeyCode();
		System.out.println("key typed = " + keyCode);
	}

	public void newGame(){
		scores = 0;
		level = 0;
		lbScores.setText("得分：" + scores);
		lbLevel.setText("等级：" + level);
		
		model.createNewShape();
		mTimer = new Timer(intervals[level],this);
		mTimer.start();
	}
	
	public boolean refreshScores(int lines){
		
		switch (lines){
		case 1:
			scores += 100;
			break;
		case 2:
			scores += 300;
			break;
		case 3:
			scores += 600;
			break;
		case 4:
			scores += 1000;
			break;
		default:
			break;	
		}
		
		int newLevel = scores / 1000;
		lbScores.setText("得分：" + scores);
		if (newLevel > level){
			lbLevel.setText("等级：" + level);
			level = newLevel;
			if (level == 10){
				JOptionPane.showMessageDialog(this,"Congratulations!");
				return false;
			}
			mTimer = new Timer(intervals[level],this);
		}
		return true;
	}
	

	public void actionPerformed(ActionEvent e){
		if (!model.goDown()){
			mTimer.stop();

			int lines = model.removeFullLines();
			if (lines > 0){
//				System.out.println("" + lines + " lines removed");
				if (!refreshScores(lines)){
					System.out.println("game complete!");
					return;
				}
				
			}
			
						
			if (model.createNewShape()){
				System.out.println("new shape");
				mTimer.start();
			}
			else {
				System.out.println("game complete");
			}
		}
	}

	public static void main(String[] args){
	    	try {
	    		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
	    		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	
	    		String vers = System.getProperty("java.version");
	    		if (vers.compareTo("1.1.2") < 0) {
	        			System.out.println("!!!WARNING: Swing must be run with a " +
	                           		"1.1.2 or higher version VM!!!");
	    		}
	    		Tetris tetris = new Tetris();
	    	} catch (Throwable t) {
	        		System.out.println("uncaught exception: " + t);
	        		t.printStackTrace();
	    	}
	}
	
}
