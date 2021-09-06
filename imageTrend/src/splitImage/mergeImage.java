package splitImage;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadFactory;

import javax.imageio.ImageIO;

class Sleep extends Thread {
	public static void Sleep() throws InterruptedException {
		Thread.sleep(300);
	}
}

public class mergeImage{
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
	private static Desktop desktop = Desktop.getDesktop();

	public static BufferedImage whiteImage() {
		BufferedImage tmp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // x,y là toạ độ của ảnh để sửa các giá trị pixel
            	tmp.setRGB(x, y, splitRGB);
            }
        }
		return tmp;
	}
	
	public static void addSign(BufferedImage des) {
		// đọc hình ảnh từ máy tính
		BufferedImage sign = null;
        try {
            f = new File("C:\\Users\\ADMIN\\Desktop\\split image\\my sign.png");
            sign = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
        int signHeight = sign.getHeight(),
        	signWidth = sign.getWidth();
        if(des.getHeight() >= signHeight && des.getWidth() >= signWidth) {
        	for(int y = 0; y < signHeight; y++) {
        		for(int x = 0; x < signWidth; x++) {
        			if(sign.getRGB(x, y) != myWhite.getRGB()) {
        				des.setRGB(des.getWidth() - signWidth + x, des.getHeight() - signHeight + y, sign.getRGB(x, y));
        			}
        		}
        	}
        }
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		// đọc hình ảnh từ máy tính
        try {
            f = new File("C:\\Users\\ADMIN\\Desktop\\split image\\chg_" + count + ".png");
            res = ImageIO.read(f);
            desktop.open(f);
            Sleep.Sleep();
            System.out.println("Loading ... " + (100 / (8 - count) + "%"));
            count++;
        } catch (IOException e) {
            System.out.println(e);
        }
        
        // lấy chiều cao và chiều rộng của ảnh
        width = res.getWidth();
        height = res.getHeight();
        splitRGB = res.getRGB(10, 10);
        
        while(count <= 7) {
        	// đọc hình ảnh từ máy tính
            try {
                f = new File("C:\\Users\\ADMIN\\Desktop\\split image\\chg_" + count + ".png");
                img = ImageIO.read(f);
                desktop.open(f);
                Sleep.Sleep();
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                    	if(img.getRGB(x, y) != myWhite.getRGB()) {
                    		res.setRGB(x, y, img.getRGB(x, y));
                    	}
                    }
                }
                System.out.println("Loading ... " + (100 / (8 - count) + "%"));
                count++;
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        
        addSign(res);
        
     // lưu ảnh
        try {
            f = new File("C:\\Users\\ADMIN\\Desktop\\split image\\made by TCHoang.png");
            ImageIO.write(res, "png", f);
            desktop.open(f);
            System.out.println("done");
        } catch (IOException e) {
            System.out.println(e);
        }
	}

}
