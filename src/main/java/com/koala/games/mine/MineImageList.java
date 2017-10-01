package com.koala.games.mine;

import java.awt.Toolkit;
import java.net.URL;

import com.koala.cwt.DefaultImageList;

public class MineImageList extends DefaultImageList {

    public MineImageList(int w, int h){
	   super(w, h);

		try{
			Toolkit tk = Toolkit.getDefaultToolkit();
			addImage(tk.getImage(ClassLoader.getSystemResource("mine/0.gif")));

			addImage(tk.getImage(ClassLoader.getSystemResource("mine/1.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("mine/2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("mine/3.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("mine/4.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("mine/5.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("mine/6.gif")));

			addImage(tk.getImage(ClassLoader.getSystemResource("mine/7.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("mine/8.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("mine/mine.gif")));
            addImage(tk.getImage(ClassLoader.getSystemResource("mine/blank.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("mine/blast.gif")));
            addImage(tk.getImage(ClassLoader.getSystemResource("mine/bad.gif")));
            addImage(tk.getImage(ClassLoader.getSystemResource("mine/flag.gif")));
            addImage(tk.getImage(ClassLoader.getSystemResource("mine/doubt.gif")));

		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}
}
