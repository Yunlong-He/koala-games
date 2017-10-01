package com.koala.games.mine;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.net.URL;

import com.koala.cwt.GUITools;
import com.koala.cwt.ImageList;
import com.koala.cwt.ImageNumber;
import com.koala.cwt.grid.*;


public class Mine extends JFrame implements GridListener, ActionListener {

	JMenuItem muNewGame,muStopGame,muExit;
	JMenuItem muSound,muUI;
	JMenuItem muOptions;
	JMenuItem muRules, muContact, muAbout;

    MineModel model;
    MineImageList imgLib;
	ImageGrid grid;

	boolean playing;
    
    ImageNumber mines;
    ImageNumber timer;
    
    JButton btGame;
    
    public Mine() {
		super("Java Mine");

		model = new MineModel(10, 10, 20);
		imgLib = new MineImageList(40,40);
		grid = new MineGrid(model, imgLib);
		grid.addGridListener(this);

        mines = new ImageNumber();
        timer = new ImageNumber();
        mines.setValue(20);
        
        btGame = new JButton(new ImageIcon(ClassLoader.getSystemResource("mine/playing.gif")));
        btGame.addActionListener(this);

        getContentPane().setLayout(new BorderLayout());
        JMenuBar mb = createMenubar();
        getContentPane().add("North",mb);

        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
		panel.setLayout(gbl);
        GUITools.addComponentTo(panel, mines,  gbl, gc, 0, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(panel, btGame, gbl, gc, 1, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(panel, timer,  gbl, gc, 2, 0, 1, 1, 1.0, 0.0);
        GUITools.addComponentTo(panel, grid,   gbl, gc, 0, 1, 3, 1, 3.0, 1.0);

        getContentPane().add("Center", panel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		show();

		playing = true;
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
        if (source == btGame) {
            model.reset();
            playing = true;
        }

   }

   /**
    * one MouseClicked event occured
    */
    public void cubicClicked(Grid _grid, GridEvent e) {
        if (!playing) return;

        System.out.println("");
        if ((e.evt.getButton() & MouseEvent.BUTTON1) > 0) {
            System.out.println("button 1 pressed");
        }
    
        if ((e.evt.getButton() & MouseEvent.BUTTON2) > 0) {
            System.out.println("button 2 pressed");
        }

        if (e.evt.getClickCount() == 2) {
            if(model.isCovered(e.col, e.row)) {
                return;
            }
            if (!model.exDiscover(e.col, e.row)) {
                playing = false;
            }
        }
        else {
            if (e.evt.getButton() == MouseEvent.BUTTON1) {
                if (!model.discover(e.col, e.row)) {
                    playing = false;
                }
            }
            else {
                if (model.isCovered(e.col, e.row)) {
                    switch (model.getState(e.col, e.row)) {
                        case MineModel.NORMAL:
                            model.setState(e.col, e.row, MineModel.IN_FLAG);
                            mines.setValue(mines.getValue()-1);
                            break;
                            
                        case MineModel.IN_FLAG:
                            model.setState(e.col, e.row, MineModel.IN_DOUBT);
                            mines.setValue(mines.getValue()+1);
                            break;
                            
                        case MineModel.IN_DOUBT:
                            model.setState(e.col, e.row, MineModel.NORMAL);
                            break;
                    }    
                }
            }
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
            Mine ht = new Mine();
        } catch (Throwable t) {
            System.out.println("uncaught exception: " + t);
            t.printStackTrace();
        }
    }

    class MineGrid extends ImageGrid {
        
        MineGrid(GridModel model, ImageList lst) {
            super(model, lst);
        }
        
        public void drawCubic(Graphics g, int col, int row) {
            int id = ((MineModel)getModel()).getValue(col, row);
    
            Image img = imgList.getImage(id);
            if (img == null) return;
    
            g.drawImage(img, 0, 0, getCubicWidth(), getCubicHeight(),this);
        }          
    }
}