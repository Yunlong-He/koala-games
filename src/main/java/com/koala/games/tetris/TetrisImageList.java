package com.koala.games.tetris;

import java.awt.Toolkit;
import java.net.URL;

import com.koala.cwt.DefaultImageList;

public class TetrisImageList extends DefaultImageList {

    public TetrisImageList(int w, int h){
	   super(w, h);

		try{
			Toolkit tk = Toolkit.getDefaultToolkit();
			addImage(tk.getImage(ClassLoader.getSystemResource("tetris/0732.jpg")));
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
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

