package com.koala.games.clearball;

import java.awt.Toolkit;
import java.net.URL;

import com.koala.cwt.DefaultImageList;

public class CBImageList extends DefaultImageList {

	public CBImageList(int w, int h){
	   super(w, h);

		try{
			Toolkit tk = Toolkit.getDefaultToolkit();
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/empty.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/umber.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/cyan.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/blue.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/green.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/yellow.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/red.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/pink.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/umber2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/cyan2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/blue2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/green2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/yellow2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/red2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("clearball/pink2.gif")));
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

