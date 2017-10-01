package com.koala.games.go;

import com.koala.cwt.GUITools;
import com.koala.cwt.grid.Grid;
import com.koala.cwt.grid.GridEvent;
import com.koala.cwt.grid.GridListener;
import com.koala.media.SoundPlayer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.tree.*;
import javax.swing.*;
import java.io.File;
import java.net.URL;

/**
 *-----------------------------  Local Play  --------------------------------------------
 */
public class LocalDemo extends JFrame implements GridListener, ActionListener{
	ImageIcon icoSound, icoNoSound, icoShowNum, icoNotShow;
    JButton btNew, btOpen, btLoad, btClose, btSave,Undo,btSound,btShowNum, btHelp;
    JButton btFirst, btPrevious, btNext, btLast, btPreComment, btNextComment;
    JButton btResetComment, btEditComment, btSaveComment;

	JButton btCloseVar, btNextSib, btPreSib;
	JButton btDelStone;
	JToggleButton btAddStone, btTriangle, btSquare, btCircle, btCross, btChar;
	JComboBox cboChar;
	JTextArea txtComment;

	GoBoard board;
	DefaultBoardModel boardModel;
//	int curPos;

    Match match;
	Movement root, current;

	boolean play_music;

	SoundPlayer sp = new SoundPlayer(getResource("/resources/putstone.wav"));

    public LocalDemo() {
		super("手谈--棋谱演示");

    	boardModel = new DefaultBoardModel(GoBoardModel.DEFAULT_BOARD_SIZE);
	    board = new GoBoard(boardModel);
	    board.addGridListener(this);

        btResetComment = createToolButton("reset_comment.gif","重置注解");
        btSaveComment = createToolButton("save_comment.gif","保存注解");
        btEditComment = createToolButton("edit.gif","编辑注解");
        txtComment = new JTextArea();
        txtComment.setWrapStyleWord(true);
        txtComment.setLineWrap(true);

        JPanel infoPad = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.insets = new Insets(5,15,5,15);
        infoPad.setLayout(gbl);

        GUITools.addComponentTo(infoPad, btResetComment,    gbl, gc, 0, 0, 1, 1, 0.0, 0.0);
        GUITools.addComponentTo(infoPad, btEditComment,     gbl, gc, 1, 0, 1, 1, 0.0, 0.0);
        GUITools.addComponentTo(infoPad, btSaveComment,     gbl, gc, 2, 0, 1, 1, 0.0, 0.0);
        GUITools.addComponentTo(infoPad, new JScrollPane(txtComment),     gbl, gc, 0, 1, 3, 1, 0.0, 1.0);

		setBackground(Color.lightGray);
		getContentPane().setLayout(new BorderLayout());
        getContentPane().add("Center", board);
        getContentPane().add("East", infoPad);
        getContentPane().add("North", createBoardToolBar());
        getContentPane().add("South", createDemoToolBar());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        show();
//        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

       	board.setNumberStart(0, 1);
		play_music = true;

        txtComment.setEditable(false);
        txtComment.setEnabled(false);

        initDemo(null);
    }

    private Component createBoardToolBar() {
        JButton b;
        JToolBar toolbar = new JToolBar();
        toolbar.setOrientation(JToolBar.HORIZONTAL);
        toolbar.setFloatable(false);
        toolbar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);

        icoSound   = new ImageIcon(ClassLoader.getSystemResource("go/sound1.gif"));
        icoNoSound = new ImageIcon(ClassLoader.getSystemResource("go/sound2.gif"));
        icoShowNum = new ImageIcon(ClassLoader.getSystemResource("go/shownum1.gif"));
        icoNotShow = new ImageIcon(ClassLoader.getSystemResource("go/shownum2.gif"));

        btNew    = createToolButton("new.gif","新建棋谱");
        btOpen    = createToolButton("open.gif","打开棋谱");
        btLoad    = createToolButton("library.gif","打开棋谱库");
        btClose = createToolButton("close.gif","关闭演示");
        btSave     = createToolButton("save.gif","保存棋谱");
        btShowNum = createToolButton("shownum1.gif","棋子序号");
        btSound = createToolButton("sound2.gif","音效");
        btHelp = createToolButton("help.gif","帮助");

        btShowNum.setIcon(icoShowNum);

        toolbar.add(btNew);
        toolbar.add(btOpen);
        toolbar.add(btLoad);
        toolbar.add(btClose);
        toolbar.add(btSave);
        toolbar.add(Box.createHorizontalStrut(5));
        toolbar.add(btShowNum);
        toolbar.add(btSound);
        toolbar.add(Box.createHorizontalStrut(5));
        toolbar.add(btHelp);

        toolbar.setBorder(BorderFactory.createRaisedBevelBorder());
        return toolbar;
    }

    private Component createDemoToolBar() {
        JButton b;
        JToolBar toolbar = new JToolBar();
        toolbar.setOrientation(JToolBar.HORIZONTAL);
        toolbar.setFloatable(false);
        toolbar.putClientProperty("JToolBar.isRollover", Boolean.TRUE);

        btFirst    = createToolButton("first.gif","第一步");
        btPrevious = createToolButton("previous.gif","上一步");
        btNext     = createToolButton("next.gif","下一步");
        btLast     = createToolButton("last.gif","最后一步");
        btPreComment= createToolButton("pre_comment.gif","上次评论");
        btNextComment= createToolButton("next_comment.gif","下次评论");

        btCloseVar  = createToolButton("to_parent.gif","关闭当前参考");
        btNextSib   = createToolButton("sib_next.gif","下一参考图");
        btPreSib    = createToolButton("sib_pre.gif","上一参考图");

        btSquare    = createToolToggleButton("square.gif","标记方块");
        btTriangle  = createToolToggleButton("triangle.gif","标记三角");
        btCircle    = createToolToggleButton("circle.gif","标记圆形");
        btCross     = createToolToggleButton("xxx.gif","标记X");
        btChar      = createToolToggleButton("char.gif","标记字符");
        btAddStone  = createToolToggleButton("addstone.gif", "添加棋子");
		ButtonGroup group = new ButtonGroup();
		group.add(btSquare);
		group.add(btTriangle);
		group.add(btCircle);
		group.add(btCross);
		group.add(btChar);
		group.add(btAddStone);

		cboChar = new JComboBox();
        for (int i = 0; i < 26; i++) {
            char c = (char) (97 + i);
            cboChar.addItem("" + c);
        }
        cboChar.setMaximumSize(cboChar.getPreferredSize());
        btDelStone    = createToolButton("sib_del.gif","删除棋子");

        toolbar.add(btFirst);
        toolbar.add(btPreComment);
        toolbar.add(btPrevious);
        toolbar.add(btNext);
        toolbar.add(btNextComment);
        toolbar.add(btLast);
		toolbar.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("go/separater.gif"))));
        toolbar.add(btCloseVar);
        toolbar.add(btNextSib);
        toolbar.add(btPreSib);
		toolbar.add(new JLabel(new ImageIcon(ClassLoader.getSystemResource("go/separater.gif"))));
        toolbar.add(btSquare);
        toolbar.add(btTriangle);
        toolbar.add(btCircle);
        toolbar.add(btCross);
        toolbar.add(btChar);
        toolbar.add(cboChar);
        toolbar.add(btAddStone);
        toolbar.add(btDelStone);

        toolbar.setBorder(BorderFactory.createRaisedBevelBorder());
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

    private JToggleButton createToolToggleButton(String ico, String tip) {
        JToggleButton bt;
        bt = new JToggleButton(new ImageIcon(ClassLoader.getSystemResource("go/" + ico))) {
            public float getAlignmentY() { return 0.5f; }
        };
        bt.setRequestFocusEnabled(false);
        bt.setMargin(new Insets(1,1,1,1));
        bt.setToolTipText(tip);
        //bt.addActionListener(this);
        return bt;
    }

    public void actionPerformed(ActionEvent e) {
		JButton bt = (JButton) e.getSource();
        if (bt == btOpen) {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(this);
            File openFile = chooser.getSelectedFile();
            if (openFile != null) {
                Match m = QipuManager.readMatchFromFile(openFile.getAbsolutePath(), QipuManager.SGF);
                initDemo(m);
            }
        } else if (bt == btLoad) {
            Match m = QipuLibrary.getMatch(new JFrame(), "选择棋谱");
            initDemo(m);
        } else if (bt == btSave) {
            JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(this);
            File saveFile = chooser.getSelectedFile();
            if (saveFile.exists()) {
                String ss = "文件已存在，覆盖么？";
                if (JOptionPane.showConfirmDialog(btSave,ss,"",JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION){
                    return;
                }
            }
            System.out.println("try to save match......");
            QipuManager.saveMatchToFile(match, saveFile.getAbsolutePath(), QipuManager.SGF);

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
		} else if (bt == btNext) {
			if (current.isLeaf()) {
				return;
			}

			showMovement((Movement) current.getFirstChild());

		} else if (bt == btPrevious) {
			if (current == root) {
				return;
			}

			undo();

		} else if (bt == btCloseVar) {
			Movement move = current;
			while (move != root) {
				move = (Movement) move.getParent();
				if (move.getChildCount() > 1) {
					break;
				}
			}
			if (move == root) {
				return;
			}

			while(current != move) {
				undo();
			}
		} else if (bt == btNextSib) {
			TreeNode node = current.getNextSibling();
			if (node == null) {
				return;
			}

			undo();
			showMovement((Movement)node);
		} else if (bt == btPreSib) {
			TreeNode node = current.getPreviousSibling();
			if (node == null) {
				return;
			}

			undo();
			showMovement((Movement)node);
		} else if (bt == btDelStone) {
			if (!current.isLeaf()) {
				return;
			}

			Movement move = current;
			undo();
//			((DefaultTreeModel)recordtree.getModel()).removeNodeFromParent(move);
            move.removeFromParent();

		} else if (bt == btResetComment) {
            txtComment.setText(current.getComment());
		} else if (bt == btEditComment) {
            txtComment.setEditable(true);
            txtComment.setEnabled(true);
        } else if (bt == btSaveComment) {
            current.setComment(txtComment.getText());
            txtComment.setEditable(false);
            txtComment.setEnabled(false);
/*      } else if (bt == btCross) {
		} else if (bt == btChar) {
*/		}
	}

    public void cubicClicked(Grid grid, GridEvent evt) {
        if(btAddStone.getModel().isSelected()){
        	int player = GoPlayer.getOpponent(current.player);
			try{
                boardModel.performPut(evt.col, evt.row, player);
				hideTips();

				if (play_music) {
					sp.play();
				}

				Movement mv = new Movement(evt.col, evt.row, player);
				current.add(mv);
				current = mv;

				updateState();
			}
			catch(GoException ge){
			}
        }
        else if(btTriangle.getModel().isSelected()){
            GoPoint p = boardModel.getPoint(evt.col, evt.row);
            if (p.getState() != GoPoint.EMPTY)  {
                boardModel.setTip(evt.col, evt.row, GoPoint.TRIANGLE);
                current.addTip(evt.col, evt.row, GoPoint.TRIANGLE);
            }
        }
        else if(btSquare.getModel().isSelected()){
            GoPoint p = boardModel.getPoint(evt.col, evt.row);
            if (p.getState() != GoPoint.EMPTY)  {
                boardModel.setTip(evt.col, evt.row, GoPoint.SQUARE);
                current.addTip(evt.col, evt.row, GoPoint.SQUARE);
            }
        }
        else if(btCircle.getModel().isSelected()){
            GoPoint p = boardModel.getPoint(evt.col, evt.row);
            if (p.getState() != GoPoint.EMPTY)  {
                boardModel.setTip(evt.col, evt.row, GoPoint.CIRCLE);
                current.addTip(evt.col, evt.row, GoPoint.CIRCLE);
            }
        }
        else if(btCross.getModel().isSelected()){
            GoPoint p = boardModel.getPoint(evt.col, evt.row);
            if (p.getState() != GoPoint.EMPTY)  {
                boardModel.setTip(evt.col, evt.row, GoPoint.CROSS);
                current.addTip(evt.col, evt.row, GoPoint.CROSS);
            }
        }
        else if(btChar.getModel().isSelected()){
            GoPoint p = boardModel.getPoint(evt.col, evt.row);
            if (p.getState() == GoPoint.EMPTY)  {
                boardModel.setTip(evt.col, evt.row, (int)(98 + cboChar.getSelectedIndex()));
                current.addTip(evt.col, evt.row, (int)(98 + cboChar.getSelectedIndex()));
            }
        }
//    JToggleButton btAddStone, btTriangle, btSquare, btCircle, btCross, btChar;

    }

    /**
     * put stone, show number, tips and comment of current movement
     */
    private void showMovement(Movement move) {
        System.out.println("move at (" + move.col + "," + move.row + ")");
        System.out.println("tip count is  " + move.getTipCount());
        System.out.println("comment is  " + move.getComment());

		try{
            hideTips();

            current = move;
            showTips();
            boardModel.performPut(move.col, move.row, move.player);
			if (play_music) {
				sp.play();
			}
			updateState();
		}
		catch(GoException ge){
//		  ge.printStackTrace();
		}
    }
    /**
     * undo one movement
     */
    private void undo() {
		TreeNode node = current.getParent();
		if (node == null) {
			return;
		}

		try {
			hideTips();
			boardModel.undo();
			current = (Movement) node;
			showTips();
			//show comment
			updateState();
		}
		catch(Exception ge){
		}
    }

    private void hideTips(){
        if (current == null) return;

		for (int i = 0; i < current.getTipCount(); i++) {
			Movement.HandTip t = current.tipAt(i);
			boardModel.setTip(t.col, t.row, GoPoint.NONE);
		}
    }
    private void showTips(){
        for (int i = 0; i < current.getForceCount(); i++) {
            Movement.HandTip t = current.forceAt(i);
            boardModel.forcePut(t.col, t.row, t.tip);
        }
        txtComment.setText(current.getComment());

        for (int i = 0; i < current.getTipCount(); i++) {
			Movement.HandTip t = current.tipAt(i);
			boardModel.setTip(t.col, t.row, t.tip);
		}
    }

    protected URL getResource(String name) {
        if (name != null) {
            URL url = this.getClass().getResource(name);
            return url;
        }
        return null;
    }

    public void initDemo(Match m) {
        match = m;
        boardModel.reset();
        if (match == null) {
            btFirst.setEnabled(false);
            btPrevious.setEnabled(false);
            btNext.setEnabled(false);
            btLast.setEnabled(false);
            btPreComment.setEnabled(false);
            btNextComment.setEnabled(false);

            root = null;
            current = null;
            return;
        }

        root = match.getRootMovement();
        showMovement(root);
        updateState();
    }

    private void updateState() {
/*    ImageIcon icoSound, icoNoSound, icoShowNum, icoNotShow;
    JButton btNew, btOpen, btClose,btSave,Undo,btSound,btShowNum, btHelp;
    JButton btFirst, btPrevious, btNext, btLast, btPreComment, btNextComment;
    JButton btResetComment, btEditComment, btSaveComment;

    JButton btCloseVar, btNextSib, btPreSib;
    JButton btDelStone;
    JToggleButton btAddStone, btTriangle, btSquare, btCircle, btCross, btChar;
    JComboBox cboChar;
    JTextArea txtComment;
*/

        if (current.getChildCount() > 0) {
            btNext.setEnabled(true);
            btLast.setEnabled(true);
        }

        if (current.getParent() != root) {
            btFirst.setEnabled(true);
            btPrevious.setEnabled(true);
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
			LocalDemo ht = new LocalDemo();
	       } catch (Throwable t) {
	          	System.out.println("uncaught exception: " + t);
	            t.printStackTrace();
	       }
    }

    class RecordTreeCellRenderer implements TreeCellRenderer {
        ImageIcon icoRoot;
        ImageIcon icoGeneral;
        ImageIcon icoParent;
        ImageIcon icoCurrent;

    	public RecordTreeCellRenderer() {
	        icoRoot    = new ImageIcon(getResource("/resources/node_parent.gif"));
	        icoGeneral = new ImageIcon(getResource("/resources/node_general.gif"));
	        icoCurrent = new ImageIcon(getResource("/resources/node_cur.gif"));
    	}

    	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
    		Movement move = (Movement) value;
    		if (move == root) {
    			return new JLabel(icoRoot);
    		}
			else if (move == current) {
    			return new JLabel(icoCurrent);
    		}
			else {
    			return new JLabel(icoGeneral);
    		}
    	}

    }
}