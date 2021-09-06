package splitImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AddMySign {
	private static BufferedImage img = null,
			 					 res = null;
	private static Color myWhite = new Color(255, 255, 255), // Color white
		     			 myRed = new Color(255, 0, 0),
		     			 myBlack = new Color(0, 0, 0);
	private static File f = null;
	private static int splitRGB;
	private static int width,
					   height,
					   count = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// đọc hình ảnh từ máy tính
        try {
            f = new File("C:\\Users\\ADMIN\\desktop\\split image\\my sign.png");
            img = ImageIO.read(f);
            f = new File("C:\\Users\\ADMIN\\Pictures\\meme\\merge pixel.png");
            res = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
        int imgHeight = img.getHeight(),
        	imgWidth = img.getWidth();
        splitRGB = img.getRGB(0, 0);
        if(res.getHeight() >= imgHeight && res.getWidth() >= imgWidth) {
        	for(int y = 0; y < imgHeight; y++) {
        		for(int x = 0; x < imgWidth; x++) {
        			if(img.getRGB(x, y) != splitRGB) {
        				res.setRGB(res.getWidth() - imgWidth + x, res.getHeight() - imgHeight + y, myWhite.getRGB());
        			}
        		}
        	}
        }// lưu ảnh
        try {
            f = new File("C:\\Users\\ADMIN\\Pictures\\meme\\merge pixel.png");
            ImageIO.write(res, "png", f);
            System.out.println("done");
        } catch (IOException e) {
            System.out.println(e);
        }
        
	}

}
