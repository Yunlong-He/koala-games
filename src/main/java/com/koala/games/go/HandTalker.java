package com.koala.games.go;

import com.koala.cwt.GUITools;
import com.koala.cwt.NumberTimerListener;
import com.koala.cwt.grid.Grid;
import com.koala.cwt.grid.GridEvent;
import com.koala.cwt.grid.GridListener;
import com.koala.media.SoundPlayer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URL;

/**
 *-----------------------------  Local Play  --------------------------------------------
 *
 */
public class HandTalker extends JFrame implements GridListener,ActionListener,NumberTimerListener {

	final int WAITING = 0;
	final int PLAYING = 1;
	final int REMOVING = 2;

	ImageIcon icoRemove,icoContinue, icoShowNum, icoNotShow, icoSound, icoNoSound;
    JButton btNewMatch,btRemove,btUndo,btCount,btResign,btVariant;
    JButton btSound,btShowNum,btHelp;
	PlayerPad gpBlack,gpWhite,player;

    JMenuItem muLocalGame,muNetGame,muAddressBook,muDemo,muExit;
    JMenuItem muSound,muColors,muUI;
    JMenuItem muHelpContent,muAbout;

	GoBoard board;
	DefaultBoardModel boardModel;
    Match match;

	SoundPlayer sp = new SoundPlayer(ClassLoader.getSystemResource("go/putstone.wav"));
	boolean play_music;

	int state;	//game state
	int curPlayer;	//which side is to lauch

    public HandTalker() {
		super("手谈--本地对局");

		boardModel = new DefaultBoardModel(GoBoardModel.DEFAULT_BOARD_SIZE);
        board = new GoBoard(boardModel);
        board.addGridListener(this);

		gpBlack    = new PlayerPad(this, GoPlayer.BLACK,"Player1","NRANK");
		gpWhite    = new PlayerPad(this, GoPlayer.WHITE,"Player2","NRANK");

    	JPanel infoPad = new JPanel();
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		infoPad.setLayout(gbl);

        JPanel pp1 = new JPanel(){
            public Dimension getSize() {
                return new Dimension(100,40);
            }
        };
        JPanel pp2 = new JPanel(){
            public Dimension getSize() {
                return new Dimension(100,40);
            }
        };


        GUITools.addComponentTo(infoPad, pp1,           gbl, gc, 0, 0, 6, 1, 0.0, 0.0);
        GUITools.addComponentTo(infoPad, gpBlack,       gbl, gc, 0, 1, 6, 1, 0.0, 0.0);
        GUITools.addComponentTo(infoPad, gpWhite,       gbl, gc, 0, 2, 6, 1, 0.0, 0.0);
        GUITools.addComponentTo(infoPad, pp2,           gbl, gc, 0, 3, 6, 1, 0.0, 0.0);

        JTextArea textArea = new JTextArea();
        textArea.setLineWrap(true);
        JSplitPane splitPane = new JSplitPane(
                                JSplitPane.VERTICAL_SPLIT,
                                infoPad,
                                new JScrollPane(textArea));

		setBackground(Color.lightGray);
		getContentPane().setLayout(new BorderLayout());
//        getContentPane().add("North", createMenubar());
        getContentPane().add("Center", board);
        getContentPane().add("East", splitPane);
        getContentPane().add("North", createBoardToolBar());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setMaximizedBounds(new Rectangle(0,0,
        pack();
		show();
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

		state = PLAYING;
		setPlayState(WAITING);
		play_music = true;
	}

	/**
	 * Create the toolbar.  By default this reads the
	 * resource file for the definition of the toolbar.
	 */
	private Component createBoardToolBar() {
		JButton b;
		JToolBar toolbar = new JToolBar();
		toolbar.setOrientation(JToolBar.HORIZONTAL);
		toolbar.setFloatable(false);
		toolbar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);

        icoContinue= new ImageIcon(ClassLoader.getSystemResource("go/continue.gif"));
        icoRemove  = new ImageIcon(ClassLoader.getSystemResource("go/remove.gif"));
        icoSound   = new ImageIcon(ClassLoader.getSystemResource("go/sound1.gif"));
        icoNoSound = new ImageIcon(ClassLoader.getSystemResource("go/sound2.gif"));
        icoShowNum = new ImageIcon(ClassLoader.getSystemResource("go/shownum1.gif"));
        icoNotShow = new ImageIcon(ClassLoader.getSystemResource("go/shownum2.gif"));


        btNewMatch = createToolButton("newmatch.gif","申请比赛");
        btUndo     = createToolButton("undo.gif","悔棋");
        btResign   = createToolButton("resign.gif","认输");
        btRemove   = createToolButton("remove.gif","清除死子");
        btCount    = createToolButton("count.gif","点目");
        btVariant  = createToolButton("variant.gif","参考图");
        btShowNum  = createToolButton("shownum1.gif","棋子序号");
        btSound    = createToolButton("sound2.gif","音效");
        btHelp     = createToolButton("help.gif","帮助");

        btShowNum.setIcon(icoShowNum);

        toolbar.add(btNewMatch);
        toolbar.add(btUndo);
        toolbar.add(btResign);
        toolbar.add(btRemove);
        toolbar.add(btCount);
        toolbar.add(btVariant);
		toolbar.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("go/separater.gif"))));
        toolbar.add(btShowNum);
        toolbar.add(btSound);
		toolbar.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("go/separater.gif"))));
        toolbar.add(btHelp);

        toolbar.setBorder(BorderFactory.createRaisedBevelBorder());
//        toolbar.setBorderPainted(true);
        return toolbar;
	}

    private JButton createToolButton(String ico, String tip) {
        JButton bt;
        bt = new JButton(new ImageIcon(ClassLoader.getSystemResource("go/" + ico))) {
            public float getAlignmentY() { return 0.5f; }
        };
        bt.setRequestFocusEnabled(false);
        bt.setMargin(new Insets(1,1,1,1));
        bt.setToolTipText(tip);
        bt.addActionListener(this);
        return bt;
    }

	void setPlayState(int pstate){
		if(state == pstate) return;

		if(pstate == PLAYING){
			board.setEnabled(true);
			btNewMatch.setEnabled(false);
			btResign.setEnabled(true);
			btRemove.setEnabled(true);
			btUndo.setEnabled(true);
			btCount.setEnabled(true);
		}
		else if(pstate == REMOVING){
			board.setEnabled(true);
			btNewMatch.setEnabled(false);
			btResign.setEnabled(true);
			btRemove.setEnabled(true);
			btUndo.setEnabled(true);
			btCount.setEnabled(true);
		}
		else if(pstate == WAITING){
			btRemove.setToolTipText("清除死子");
			btRemove.setIcon(icoRemove);
			board.setEnabled(false);
			btNewMatch.setEnabled(true);
			btResign.setEnabled(false);
			btRemove.setEnabled(false);
			btUndo.setEnabled(false);
			btCount.setEnabled(false);
		}
		else return;
		state = pstate;
	}

    public void actionPerformed(ActionEvent e) {
		JButton bt = (JButton) e.getSource();
		if (bt == btNewMatch) {
			match = LocalMatchDialog.showChallengeDialog(new JFrame(),"申请比赛");
			if (match == null) return;

			try{
				boardModel.reset(match.boardSize);
				board.setNumberStart(match.preputCount, 1);
                setPlayState(PLAYING);
				gpBlack.setTime(match.timeLen);
				gpWhite.setTime(match.timeLen);
				gpBlack.startTimer();
				curPlayer = GoPlayer.BLACK;
			}
			catch(Exception ge){
//				ge.printStackTrace();
				return;
			}
		} else if (bt == btResign) {
			board.setEnabled(false);
			if (state == PLAYING){
				setPlayState(REMOVING);
				if (curPlayer == GoPlayer.BLACK)
					stopPlay("白棋中盘胜");
				else
					stopPlay("黑棋中盘胜");
			}

		} else if (bt == btUndo) {
			try{
				Movement move = boardModel.getLastMovement();
                if (move == null) return;
                int deadCount = move.captiveCount();
                if (move.player == GoPlayer.BLACK) {
					gpBlack.addDead(-deadCount);
				}
				else {
					gpWhite.addDead(-deadCount);
				}
				boardModel.undo();
				exchangeSide();
			}
			catch(Exception ge){
//				ge.printStackTrace();
			}

		} else if (bt == btRemove) {
			if (state == PLAYING){
				setPlayState(REMOVING);
				if (curPlayer == GoPlayer.BLACK)
					gpBlack.stopTimer();
				else
					gpWhite.stopTimer();
				bt.setToolTipText("继续下棋");
				bt.setIcon(icoContinue);
			}
			else if (state == REMOVING){
				try{
					boardModel.restore();
				}
				catch(Exception ge){
//					ge.printStackTrace();
				}

                curPlayer = boardModel.getLastMovement().player;
				exchangeSide();
				if (curPlayer == GoPlayer.BLACK)
					gpBlack.startTimer();
				else
					gpWhite.startTimer();
				setPlayState(PLAYING);
				bt.setToolTipText("清除死子");
				bt.setIcon(icoRemove);
			}
		} else if (bt == btCount) {
				boardModel.divideDomain();

				int blackDomain = 0;
				int whiteDomain = 0;
		    	for (int i = 0 ; i < boardModel.getBoardSize() ; i++)
				for (int j = 0 ; j < boardModel.getBoardSize() ; j++){
					GoPoint gp = boardModel.getPoint(i,j);
					if (gp.getHolder() == GoPoint.BLACK)
						blackDomain++;
					else if(gp.getHolder() == GoPoint.WHITE){
						whiteDomain++;
					}
				}

				int rs = blackDomain - whiteDomain - 5;
				String ss = "";
				if (rs > 0){
					ss = "黑胜" + rs + "目半";
				}
				else if (rs == 0){
					ss = "白胜半目";
				}
				else {
					ss = "白胜" + (rs + 1) + "目半";
				}

				if (JOptionPane.showConfirmDialog(btCount,ss,"",JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					stopPlay(ss);
				}
				else {
					try{
						boardModel.restore();
					}
					catch(Exception ge){
//						ge.printStackTrace();
					}
					curPlayer = boardModel.getLastMovement().player;
					exchangeSide();
					if (curPlayer == GoPlayer.BLACK)
						gpBlack.startTimer();
					else
						gpWhite.startTimer();
					setPlayState(PLAYING);
				}
		} else if (bt == btShowNum) {
			if (board.isShowingNumber()){
				board.setShowingNumber(false);
				btShowNum.setIcon(icoShowNum);
			}
			else {
				board.setShowingNumber(true);
				btShowNum.setIcon(icoNotShow);
			}
		} else if (bt == btSound) {
			if (play_music){
				play_music = false;
				btSound.setIcon(icoSound);
			}
			else {
				play_music = true;
				btSound.setIcon(icoNoSound);
			}
		} else if (bt == btVariant) {
			if (state == PLAYING) {
                match.setMovements(boardModel.createMatchRecord());
				LocalDemo ht = new LocalDemo();
                ht.initDemo(match);
			}
		}
	}

	public void timeExpired(Component player){
		if (player == gpBlack) {
			JOptionPane.showMessageDialog(btCount,"黑超时判负");
			stopPlay("黑超时判负");
		}
		else {
			JOptionPane.showMessageDialog(btCount,"白超时判负");
			stopPlay("白超时判负");
		}

	}

    public void stopPlay(String ss) {
		gpBlack.stopTimer();
		gpWhite.stopTimer();
		setPlayState(WAITING);
    }



	private void exchangeSide(){
		if (curPlayer == GoPlayer.BLACK) {
			curPlayer = GoPlayer.WHITE;
//			sb.showStatus(1,"white thinking");
			gpBlack.stopTimer();
			gpWhite.startTimer();
		}
		else{
			curPlayer = GoPlayer.BLACK;
//			sb.showStatus(1,"black thinking");
			gpWhite.stopTimer();
			gpBlack.startTimer();
		}
	}

	/**
	 * 棋手点击了某个位置
	 */
    public void cubicClicked(Grid grid, GridEvent evt){
		if(state == PLAYING){
			try{
                boardModel.performPut(evt.col, evt.row, curPlayer);

                int deadCount = boardModel.getLastMovement().captiveCount();
				if (curPlayer == GoPlayer.BLACK) {
					gpBlack.addDead(deadCount);
				}
				else {
					gpWhite.addDead(deadCount);
				}

				if (play_music) {
					sp.play();
				}

	            if (boardModel.getMovementCount() >= match.preputCount) {
	                exchangeSide();
	            }

			}
			catch(GoException ge){
//				ge.printStackTrace();
			}
        }
		else if(state == REMOVING){
			GoPoint gp = boardModel.getPoint(evt.col, evt.row);
			if (gp.getState() == GoPoint.EMPTY) return;
			if (gp.getState() == GoPoint.BLACK)
				curPlayer = GoPlayer.BLACK;
			else
				curPlayer = GoPlayer.WHITE;
			try{
				boardModel.performRemove(evt.col, evt.row);
				int deadCount = boardModel.getLastMovement().captiveCount();
				if (curPlayer == GoPlayer.BLACK) {
					gpBlack.addDead(deadCount);
				}
				else {
					gpWhite.addDead(deadCount);
				}
			}
			catch(GoException ge){
//				ge.printStackTrace();
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

   	public static void main(String[] args) {
	    try {
		    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		    //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

	        String vers = System.getProperty("java.version");
	        if (vers.compareTo("1.1.2") < 0) {
	            System.out.println("!!!WARNING: Swing must be run with a " +
	                               		"1.1.2 or higher version VM!!!");
	        }
			HandTalker ht = new HandTalker();
	       } catch (Throwable t) {
	          	System.out.println("uncaught exception: " + t);
	            t.printStackTrace();
	       }
    }
}