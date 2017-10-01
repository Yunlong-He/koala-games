package com.koala.games.fillpit;

import java.awt.Toolkit;
import java.net.URL;

import com.koala.cwt.DefaultImageList;

public class FPImageList extends DefaultImageList {

	public FPImageList(int w, int h){
	   super(w, h);

		try{
			Toolkit tk = Toolkit.getDefaultToolkit();
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/empty.gif")));

			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/blue1.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/cyan1.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/green1.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/red1.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/smile1.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/white1.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/yellow1.gif")));

			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/blue2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/cyan2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/green2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/red2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/smile2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/white2.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/yellow2.gif")));

			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/blue3.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/cyan3.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/green3.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/red3.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/smile3.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/white3.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/yellow3.gif")));

			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/blue4.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/cyan4.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/green4.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/red4.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/smile4.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/white4.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/yellow4.gif")));

			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/smile0.gif")));
			addImage(tk.getImage(ClassLoader.getSystemResource("fillpit/gray.gif")));

		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		}
	}

}
