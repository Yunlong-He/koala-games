package com.koala.games.go;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.koala.cwt.GUITools;

public final class LocalMatchDialog extends Dialog implements ActionListener{

	JComboBox cboBoardSize,cboPrePut,cboKomi;
	JComboBox cboTime,cboYomi,cboYomiTimes;
	JButton btSubmit,btCancel;


	/**
	 * whether the dialog data is submited
	 */
	private boolean submited;

	/**
	 * the data user submited
	 */
	private Match match;

	/**
	 * show local match rules dialog
	 *
	 * why??? class methods have access to instance's private data
	 */
	public static Match showChallengeDialog(JFrame frm, String title){
		LocalMatchDialog md = new LocalMatchDialog(frm, title);
		md.show();
		if (md.submited)
			return md.match;
		else
			return null;

	}

	/*
	 * initialize match dialog with match info
	 *
	 * @frm: parent frame
	 */
	private LocalMatchDialog(JFrame frm, String title){
		super(frm,title,true);

		addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					submited = false;
					hide();
				}
			}
		);
		setResizable(false);

		/**
		 * initialize components
		 */
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
//		Insets nst = new Insets(5,3,0,0);
//		gc.insets = nst;

		btSubmit = new JButton("确定");
		btSubmit.addActionListener(this);
		btCancel = new JButton("取消");
		btCancel.addActionListener(this);

		setSize(330,200);

		JPanel rulesPanel = createRulesPanel();
		JPanel timesPanel = createTimesPanel();

		/**
		 * layout components
		 */
		setLayout(gbl);
//		gc.insets = new Insets(8,8,20,20);
		GUITools.addComponentTo(this,rulesPanel, gbl, gc, 0, 0, 2, 1, 0.8, 0.0);
		GUITools.addComponentTo(this,timesPanel, gbl, gc, 0, 1, 2, 1, 0.8, 0.0);
//		gc.fill = GridBagConstraints.NONE;
		gc.insets = new Insets(10,50,0,50);
		GUITools.addComponentTo(this,btSubmit,   gbl, gc, 0, 2, 1, 1, 0.0, 0.0);
		GUITools.addComponentTo(this,btCancel,   gbl, gc, 1, 2, 1, 1, 0.0, 0.0);

		/**
		 * initialize data
		 */
		submited = false;
		match = new Match();

	}

	private JPanel createTimesPanel(){
		cboTime = new JComboBox();
		cboTime.addItem("10分钟");
		cboTime.addItem("30分钟");
		cboTime.addItem("1个小时");
		cboTime.addItem("2个小时");
		cboTime.addItem("4个小时");
		cboTime.addItem("8个小时");
		cboTime.addItem("24个小时");
		cboYomi = new JComboBox();
		cboYomi.addItem("30秒");
		cboYomi.addItem("60秒");
		cboYomiTimes = new JComboBox();
		cboYomiTimes.addItem("1次");
		cboYomiTimes.addItem("2次");
		cboYomiTimes.addItem("3次");
		cboYomiTimes.addItem("4次");
		cboYomiTimes.addItem("5次");

/*		JPanel rulesPanel = new JPanel();
		timesPanel.setBorder(new TitledBorder("时间限制"));
		GridBagLayout jpLayout = new GridBagLayout();
		GridBagConstraints jpCons = new GridBagConstraints();
		timesPanel.setLayout(jpLayout);
		GUITools.addComponentTo(timesPanel,new JLabel("时间"),jpLayout, jpCons, 0, 0, 1, 1, 1.0, 0.0);
		GUITools.addComponentTo(timesPanel,cboTime,    jpLayout, jpCons, 3, 0, 1, 1, 1.0, 0.0);
		GUITools.addComponentTo(timesPanel,new JLabel("读秒"),jpLayout, jpCons, 0, 1, 1, 1, 1.0, 0.0);
		GUITools.addComponentTo(timesPanel,cboYomi,      jpLayout, jpCons, 1, 1, 1, 1, 1.0, 0.0);
		GUITools.addComponentTo(timesPanel,cboYomiTimes, jpLayout, jpCons, 3, 1, 1, 1, 1.0, 0.0);
*/
		JPanel timesPanel = new JPanel();
		timesPanel.setBorder(new TitledBorder("时间限制"));
		timesPanel.setLayout(new FlowLayout(FlowLayout.LEFT,13,2));
		timesPanel.add(new JLabel("时间"));
		timesPanel.add(cboTime);
		timesPanel.add(new JLabel("读秒"));
		timesPanel.add(cboYomi);
		timesPanel.add(cboYomiTimes);

		return timesPanel;
	}

	private JPanel createRulesPanel(){
		cboBoardSize = new JComboBox();
		cboBoardSize.addItem("19 路");
		cboBoardSize.addItem("13 路");
		cboBoardSize.addItem("9 路");
		cboPrePut = new JComboBox();
		cboPrePut.addItem("分先");
		cboPrePut.addItem("让2子");
		cboPrePut.addItem("让3子");
		cboPrePut.addItem("让4子");
		cboPrePut.addItem("让5子");
		cboPrePut.addItem("让6子");
		cboPrePut.addItem("让7子");
		cboPrePut.addItem("让8子");
		cboPrePut.addItem("让9子");
		cboKomi = new JComboBox();
		cboKomi.addItem("不贴目");
		cboKomi.addItem("黑贴5目半");
		cboKomi.addItem("黑贴6目半");

/*		JPanel extendPanel = new JPanel();
		rulesPanel.setBorder(new TitledBorder("规则"));
		GridBagLayout jpLayout = new GridBagLayout();
		GridBagConstraints jpCons = new GridBagConstraints();
		rulesPanel.setLayout(jpLayout);
		GUITools.addComponentTo(rulesPanel,new JLabel("对局方式"),  jpLayout, jpCons, 0, 0, 3, 1, 1.0, 0.0);
		GUITools.addComponentTo(rulesPanel,cboBoardSize, jpLayout, jpCons, 3, 0, 1, 1, 1.0, 0.0);
		GUITools.addComponentTo(rulesPanel,cboPrePut,    jpLayout, jpCons, 1, 1, 1, 1, 1.0, 0.0);
		GUITools.addComponentTo(rulesPanel,cboKomi,      jpLayout, jpCons, 3, 1, 1, 1, 1.0, 0.0);
*/
		JPanel rulesPanel = new JPanel();
		rulesPanel.setBorder(new TitledBorder("规则"));
		rulesPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
		rulesPanel.add(new JLabel("对局方式"));
		rulesPanel.add(cboBoardSize);
		rulesPanel.add(cboPrePut);
		rulesPanel.add(cboKomi);
		return rulesPanel;
	}

	/**
	 * perform action fired by clicking "button" submit or "cancel"

	 */
    public void actionPerformed(ActionEvent e) {
		JButton bt = (JButton) e.getSource();
		if (bt == btSubmit) {
			saveMatchInfo();
			submited = true;
			hide();
		} else if (bt == btCancel) {
			submited = false;
			hide();
		}
    }


	/**
	 * read match info from dialog
	 */
	private void saveMatchInfo(){
		/**
		 * rules info
		 */
		switch(cboBoardSize.getSelectedIndex()){
		case 0:
			match.boardSize = 19;
			break;
		case 1:
			match.boardSize = 13;
			break;
		case 2:
			match.boardSize = 9;
			break;
		default:
			match.boardSize = 19;
			break;
		}

		switch (cboTime.getSelectedIndex()){
			case 0:
				match.timeLen = 600;
				break;
			case 1:
				match.timeLen = 1800;
				break;
			case 2:
				match.timeLen = 3600;
				break;
			case 3:
				match.timeLen = 7200;
				break;
			case 4:
				match.timeLen = 14400;
				break;
			case 5:
				match.timeLen = 28800;
				break;
			default:
				match.timeLen = 24 * 3600;
				break;
		}

		match.preputCount = cboPrePut.getSelectedIndex() + 1;
		if (match.preputCount == 1) match.preputCount--;

		switch(cboKomi.getSelectedIndex()){
		case 0:
			match.komi = 0.0;
			break;
		case 1:
			match.komi = 5.5;
			break;
		case 2:
			match.komi = 6.5;
			break;
		default:
			match.komi = 5.5;
			break;
		}

		match.yomi = 30 * (1 + cboYomi.getSelectedIndex());

		match.yomiTimes = 1 + cboYomi.getSelectedIndex();
	}

	public static void main(String[] args){
       	JFrame frm = new JFrame();
       	JFrame frm1 = new JFrame();
       	frm.setTitle("HandTalker");
		frm.setBackground(Color.lightGray);
		frm.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e) {
			    		System.exit(0);
				}
			});
		frm.pack();
		frm.setSize(500, 600);
       	frm.show();
       	LocalMatchDialog.showChallengeDialog(new JFrame(),"申请比赛");
	}

}