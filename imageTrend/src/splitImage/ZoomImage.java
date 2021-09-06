package splitImage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ZoomImage {
	private static BufferedImage img = null,
			 					 res = null;
	private static Color myWhite = new Color(255, 255, 255), // Color white
		     			 myRed = new Color(255, 0, 0),
		     			 myBlack = new Color(0, 0, 0);
	private static File f = null;
	private static int mergeRGB;
	public static int width,
					   height,
					   count = 5;
	
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
	
	public static BufferedImage UpSize(BufferedImage tmp, int size) {
		count = size;
        height = tmp.getHeight();
        width = tmp.getWidth();
        res = new BufferedImage(width * count, height * count, BufferedImage.TYPE_INT_RGB);
		for(int j = 0; j < height * count; j += count) {
        	for(int i = 0; i < width * count; i += count) {
        		for(int y = j; y < j + count; y++) {
        			for(int x = i; x < i + count; x++) {
        				res.setRGB(x, y, tmp.getRGB(i / count, j / count));
        			}
        		}
        	}
        }
		return res;
	}
	
	public static BufferedImage DownSize(BufferedImage tmp, int size) {
        height = tmp.getHeight();
        width = tmp.getWidth();
		count = ((height > width ? height : width) / size) + 1;
		if(count > 1) {
			res = new BufferedImage(width / (count), height / (count), BufferedImage.TYPE_INT_RGB);
			for(int y = 0; y < tmp.getHeight(); y++) {
				for(int x = 0; x < tmp.getWidth(); x++) {
					if(x % count == 0 && y % count == 0 && x / count < res.getWidth() && y / count < res.getHeight()) {
						res.setRGB(x / count, y / count, tmp.getRGB(x, y));
					}
				}
			}
		} else {
			res = tmp;
		}
		return res;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// đọc hình ảnh từ máy tính
        try {
            f = new File("C:\\Users\\ADMIN\\Desktop\\split image\\detec\\face8.png");
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
        res = UpSize(img, 8);
        
        
        try {
            f = new File("C:\\Users\\ADMIN\\Desktop\\split image\\zoom image2.png");
            ImageIO.write(res, "png", f);
            System.out.println("done");
        } catch (IOException e) {
            System.out.println(e);
        }
        
	}

}
