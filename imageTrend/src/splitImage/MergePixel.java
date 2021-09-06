package splitImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MergePixel {
	private static BufferedImage img = null,
			 					 res = null;
	private static Color myWhite = new Color(255, 255, 255), // Color white
		     			 myRed = new Color(255, 0, 0),
		     			 myBlack = new Color(0, 0, 0);
	private static File f = null;
	private static int mergeRGB;
	private static int width,
					   height,
					   count;
	
	public static BufferedImage whiteImage() {
		BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // x,y là toạ độ của ảnh để sửa các giá trị pixel
            	tmp.setRGB(x, y, 0);
            }
        }
		return tmp;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// đọc hình ảnh từ máy tính
        try {
            f = new File("C:\\Users\\ADMIN\\Pictures\\meme\\deep.jpg");
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
        height = img.getHeight();
        width = img.getWidth();
        count = (height > width ? width : height) / 50;
        res = new BufferedImage(width - width % count, height - height % count, BufferedImage.TYPE_INT_RGB);
        
        for(int j = 0; j < height - height % count; j += count) {
        	for(int i = 0; i < width - width % count; i += count) {
//        		mergeRGB = 0;
//        		for(int y = j; y < j + count; y++) {
//        			for(int x = i; x < i + count; x++) {
//        				mergeRGB += img.getRGB(x, y);
//        			}
//        		}
//        		mergeRGB /= count * count;
        		mergeRGB = img.getRGB(i + count / 2, j + count / 2);
        		for(int y = j; y < j + count; y++) {
        			for(int x = i; x < i + count; x++) {
        				res.setRGB(x, y, mergeRGB);
        			}
        		}
        	}
        }
        
        try {
            f = new File("C:\\Users\\ADMIN\\Pictures\\meme\\merge pixel.png");
            ImageIO.write(res, "png", f);
            System.out.println("done");
        } catch (IOException e) {
            System.out.println(e);
        }
        
	}

}
