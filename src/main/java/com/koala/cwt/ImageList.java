package com.koala.cwt;

import java.awt.Image;

/**
 * ImageList represents a group of images with the same size
 */
public interface ImageList {

   public int getImageWidth();

   public int getImageHeight();

   public int getImageCount();

   public Image getImage(int index);

   public void addImage(Image img);

   public void removeAllImages();

}