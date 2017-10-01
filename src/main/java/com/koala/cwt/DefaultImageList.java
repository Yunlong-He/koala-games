package com.koala.cwt;

import java.awt.Image;
import java.util.Vector;
import java.awt.MediaTracker;
import java.awt.Button;

/**
 * ImageList represents a group of images with the same size
 */
public class DefaultImageList implements ImageList{
   protected int width, height;
   MediaTracker tracker = new MediaTracker(new Button());

	Vector images = new Vector();

   public DefaultImageList(int w, int h) {
      width = w;
      height = h;
   }

   public int getImageWidth() {
      return width;
   }

   public int getImageHeight() {
      return height;
   }

   public int getImageCount() {
      return images.size();
   }

   public Image getImage(int  index) {
      return (Image) images.elementAt(index);
   }

   public void addImage(Image img) {
      images.addElement(img);
      try {
         tracker.addImage(img,0);
         tracker.waitForID(0);
      }
      catch (InterruptedException e) {
         e.printStackTrace();
      }
   }

   public void removeAllImages() {
      images.removeAllElements();
   }

}