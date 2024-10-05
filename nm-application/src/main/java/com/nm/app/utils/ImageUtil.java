package com.nm.app.utils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 * @author Nabil Mansouri
 * 
 */
public class ImageUtil {
	public static Dimension getDimension(byte[] img) throws IOException {
		BufferedImage bimg = ImageIO.read(new ByteArrayInputStream(img));
		int width = bimg.getWidth();
		int height = bimg.getHeight();
		Dimension dim = new Dimension(width, height);
		return dim;
	}

}
