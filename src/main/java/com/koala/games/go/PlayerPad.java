package com.koala.games.go;

import com.koala.cwt.GUITools;
import com.koala.cwt.NumberTimer;
import com.koala.cwt.NumberTimerListener;

import java.awt.*;
import javax.swing.*;
import java.net.URL;

public class PlayerPad extends JPanel {

	GoPlayer player;
	JLabel lbName,lbDead;

	NumberTimer timer;

    public PlayerPad(NumberTimerListener l, int side, String name, String rank) {
		super();

   		ImageIcon[] timeImages = new ImageIcon[11];
		for (int i = 0; i < 11; i++){
			timeImages[i] = new ImageIcon(ClassLoader.getSystemResource("go/timer" + i + ".gif"));
		}
//		ImageIcon bicon = new ImageIcon(ClassLoader.getSystemResource("go/redball.gif"));

		player = new GoPlayer(side,name,rank,0);

		JLabel icon;
		if (side == GoPlayer.BLACK) {
            icon = new JLabel(new ImageIcon(ClassLoader.getSystemResource("go/black.jpg")));
		}
		else {
			icon = new JLabel(new ImageIcon(ClassLoader.getSystemResource("go/white.jpg")));
		}
        lbName = new JLabel(name + "[" + rank + "]");
		lbDead = new JLabel("���ӣ�0");
		timer = new NumberTimer(this,timeImages);
//		timer = new NumberTimer(this);
		timer.setLapseType(NumberTimer.DECREASE);
        timer.addNumberTimerListener(l);

//		setBackground(new Color(200,200,220));
//      setBackground(new Color(96,136,184));
        setBackground(new Color(206,207,255));
        GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.NONE;
        gc.insets = new Insets(6,5,6,5);
        setLayout(gbl);

        GUITools.addComponentTo(this, icon,     gbl, gc, 0, 0, 1, 3, 0.0, 0.0);
		GUITools.addComponentTo(this, lbName,   gbl, gc, 1, 0, 1, 1, 1.0, 0.0);
		GUITools.addComponentTo(this, lbDead,   gbl, gc, 1, 1, 1, 1, 1.0, 0.0);
		GUITools.addComponentTo(this, timer,    gbl, gc, 1, 2, 1, 1, 1.0, 0.0);
		
//		setBorder(BorderFactory.createTitledBorder(""));
//      setBorder(BorderFactory.createEtchedBorder(getBackground().darker(), getBackground().brighter()));
//        setBorder(BorderFactory.createEtchedBorder(new Color(0,0,100), new Color(150,150,250)));
//        setBorder(BorderFactory.createMatteBorder(bicon.getIconHeight(), bicon.getIconWidth(),bicon.getIconHeight(), bicon.getIconWidth(),bicon));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.gray, 5),
                BorderFactory.createRaisedBevelBorder()),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.blue, 3),
                BorderFactory.createLoweredBevelBorder())));

	}
/*
	public Dimension getSize() {
		return new Dimension(100,200);
	}
*/
	/**
	 * dead operations
	 */
	public void addDead(int count){
		player.addDead(count);
		lbDead.setText("���ӣ�" + player.getDead());
	}
/*
	public void setDead(int count){
		dead = count;
		lbDead.setText("���ӣ�" + dead);
	}
*/
	/**
	 * Timer operation
	 */
	public void stopTimer() {
		timer.stop();
	}

	public void startTimer() {
		timer.start();
	}

	public void setTimerListener(NumberTimerListener l) {
		timer.addNumberTimerListener(l);
	}

	public void setTime(int curTime){
		player.setTimeLeft(curTime);
		timer.setTime(curTime);
	}
    protected URL getResource(String name) {
        if (name != null) {
            URL url = this.getClass().getResource(name);
            return url;
        }
        return null;
    }

}