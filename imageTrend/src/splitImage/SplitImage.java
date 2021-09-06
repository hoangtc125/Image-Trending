package splitImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class SplitImage {

    private static BufferedImage img = null,
    							 res = null,
    							 src = null;
    private static ArrayList<BufferedImage> subSet = new ArrayList<BufferedImage>();
    private static ArrayList<SubImage> sub = new ArrayList<SubImage>(),
									   faceSet = new ArrayList<SubImage>();
	private static int width,
					   height;
	private static long start = System.currentTimeMillis(),
			   			end;
	private static SubImage srcImage;
	private static Color myWhite = new Color(255, 255, 255), // Color white
				   	     myRed = new Color(255, 0, 0),
				   	     myBlack = new Color(0, 0, 0);
	private static File f = null;
	private static int splitRGB = -14600000,
					   minRange = 999999999,
					   maxRange = -999999999;
	private static Desktop desktop = Desktop.getDesktop();
	private static String filePath = "face47.png";
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
    
	// tìm các vùng đen theo dfs // không khuyến khích sử dụng
    public static void processImage1() {
    	int count = 0;
        for(PointImage p : srcImage.getPoint()) {
        	if(p.isCheck() == false && p.getP() != splitRGB) {
        		
                res = whiteImage();
        		res.setRGB(p.getX(), p.getY(), p.getP());
        		find(p);
        		subSet.add(res);
        		count++;
        		// lưu ảnh
                try {
//                	for(int i = 0; i < subSet.size(); i++) {
                        f = new File("C:\\Users\\ADMIN\\Desktop\\split image\\chg_" + count + ".png");
                        ImageIO.write(subSet.get(0), "png", f);
                        System.out.println("done");
//                	}
                } catch (IOException e) {
                    System.out.println(e);
                }
                subSet.remove(0);
//        		break;
        	}
        }
    }
    
    // tìm điểm đen tiếp theo từ điểm đen p cho trước theo dfs
	public static void find(PointImage p) {
		for(int yy = -1; yy <= 1; yy++) {
			for(int xx = -1; xx <= 1; xx++) {
				if(p.getX() + xx >= 0 && p.getY() + yy >= 0 &&
				   p.getX() + xx < width && p.getY() + yy < height &&
				   xx * xx + yy * yy != 0 &&
				   srcImage.getPointImage(xx + p.getX(), yy + p.getY()).isCheck() == false &&
				   srcImage.getPointImage(xx + p.getX(), yy + p.getY()).getP() != splitRGB) {
					
		        		srcImage.getPointImage(xx + p.getX(), yy + p.getY()).setCheck(true);
		        		res.setRGB(xx + p.getX(), yy + p.getY(), srcImage.getPointImage(xx + p.getX(), yy + p.getY()).getP());
		        		find(srcImage.getPointImage(xx + p.getX(), yy + p.getY()));
	        		
				}
			}
		}
	}
	
	// nếu điểm đen currentPoint có điểm đen kề nó PrevPoint
	public static void processImage2(PointImage currentPoint, PointImage PrevPoint) {
		// xét nếu currentPoint là điểm đầu tiên của tập hợp, thêm 
		if(currentPoint.isCheck() == true) {
			for(SubImage subImage : sub) {
				if(subImage.getPoint().contains(currentPoint) == true && subImage.getPoint().contains(PrevPoint) == false) {
					for (SubImage subImage2 : sub) {
						if(subImage2.getPoint().contains(PrevPoint)) {
							for (PointImage pointImage : subImage2.getPoint()) {
								subImage.addImagePoint(pointImage);
							}
							sub.remove(subImage2);
							break;
						}
					}
					break;
				}
			}
		} else {
			for (SubImage subImage : sub) {
				if(subImage.getPoint().contains(PrevPoint)) {
					subImage.addImagePoint(currentPoint);
					currentPoint.setCheck(true);
					break;
				}
			}
		}
	}
	
	public static void PrintImage(ArrayList<SubImage> tmp) {

		
        // đọc hình ảnh từ máy tính
        try {
            f = new File("C:\\Users\\ADMIN\\Desktop\\split image\\detec\\" + filePath);
            img = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
        
        for(int i = 0; i < tmp.size(); i++) {
        	for(int y = tmp.get(i).getNorth() * ZoomImage.count; y <= tmp.get(i).getSouth() * ZoomImage.count; y++) {
        		for(int x = tmp.get(i).getWest() * ZoomImage.count; x <= tmp.get(i).getEast() * ZoomImage.count; x++) {
        			if((y == tmp.get(i).getNorth() * ZoomImage.count || y == tmp.get(i).getSouth() * ZoomImage.count) &&
        				y < src.getHeight() && x < src.getWidth()) {
        				src.setRGB(x, y, myRed.getRGB());
        			} else {
						if((x == tmp.get(i).getWest()  * ZoomImage.count|| x == tmp.get(i).getEast() * ZoomImage.count) &&
        				y < src.getHeight() && x < src.getWidth()) {
							src.setRGB(x, y, myRed.getRGB());
						}
					}
        		}
        	}
        }
        try {
    		f = new File("C:\\Users\\ADMIN\\Desktop\\split image\\res\\" + filePath);
            ImageIO.write(src, "png", f);
            desktop.open(f);
            System.out.println("done");
        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("time " + (System.currentTimeMillis() - start));
	}
	
	public static void findFace() {
		SubImage s1 = new SubImage(), 
				 s2 = new SubImage(), 
				 s3 = new SubImage(), 
				 s4 = new SubImage();
		for(int i = 0; i < sub.size(); i++) {
			for(int j = 0; j < sub.size(); j++) {
				if(j == i) continue;
				if((sub.get(i).getNorth() <= sub.get(j).getSouth() && sub.get(i).getSouth() >= sub.get(j).getNorth()) &&
					sub.get(i).getEast() < sub.get(j).getWest() && sub.get(i).getChecked() == 0 && sub.get(j).getChecked() == 0 &&
					sub.get(i).getEast() - sub.get(i).getWest() > sub.get(i).getSouth() - sub.get(i).getNorth() &&
					sub.get(j).getEast() - sub.get(j).getWest() > sub.get(j).getSouth() - sub.get(j).getNorth()) {
					// 2 lông mày thẳng hàng ngang và 2 lông mày hình chữ nhật nằm ngang
					s1 = sub.get(i);
					s2 = sub.get(j);
					for(int k = 0; k < sub.size(); k++) {
						if(k == i || k == j) continue;
						if(sub.get(i).getWest() <= sub.get(k).getEast() && sub.get(i).getEast() >= sub.get(k).getWest() &&
						   sub.get(i).getSouth() < sub.get(k).getNorth() && sub.get(k).getChecked() == 0 &&
						   sub.get(j).getWest() > sub.get(k).getEast() &&
						   sub.get(k).getEast() - sub.get(k).getWest() > sub.get(k).getSouth() - sub.get(k).getNorth()) {
							// mắt trái nằm thẳng dưới lông mày trái, không nằm dưới lông mày phải và là hình chữ nhật nằm ngang
							s3 = sub.get(k);
							for(int h = 0; h < sub.size(); h++) {
								if(h == k || h == j || h == i) continue; 
								if(sub.get(h).getNorth() <= sub.get(k).getSouth() && sub.get(h).getSouth() >= sub.get(k).getNorth() &&
								   sub.get(h).getWest() <= sub.get(j).getEast() && sub.get(h).getEast() >= sub.get(j).getWest() &&
								   sub.get(h).getNorth() > sub.get(j).getSouth() && sub.get(h).getWest() > sub.get(i).getEast()) {
									// 2 mắt thẳng hàng ngang, mắt phải nằm dưới lông mày phải và không nằm dưới lông mày trái
									int count = 0;
									for(int m = 0; m < sub.size(); m++) {
										if(m == i || m == j || m == k || m == h) continue;
										if(((s1.getSouth() < sub.get(m).getNorth() && s3.getNorth() > sub.get(m).getSouth()) ||
										   (s2.getSouth() < sub.get(m).getNorth() && sub.get(h).getNorth() > sub.get(m).getSouth())) &&
										   (s1.getWest() > s3.getWest() ? s3.getWest() : s1.getWest()) < sub.get(m).getEast() && 
										   (s2.getEast() > s4.getEast() ? s2.getEast() : s4.getEast()) > sub.get(m).getWest()) {
											// kiểm tra giữa lông mày và mắt không được có vật nào ở giữa
											count = 1;
											break;
										}
										if(((s1.getEast() < sub.get(m).getWest() && s2.getWest() > sub.get(m).getEast()) ||
										   (s3.getEast() < sub.get(m).getWest() && s4.getWest() > sub.get(m).getEast())) &&
										   (s1.getNorth() < s2.getNorth() ? s1.getNorth() : s2.getNorth()) < sub.get(m).getSouth() &&
										   (s2.getSouth() > s4.getSouth() ? s2.getSouth() : s4.getSouth()) > sub.get(m).getNorth()) {
											count = 1;
											break;
										}
									}
									if(count == 0 && sub.get(h).getChecked() == 0 &&
									   ((s3.getNorth() - s1.getSouth()) < s3.getSouth() - s3.getNorth() + s1.getSouth() - s1.getNorth()) &&
									   ((sub.get(h).getNorth() - s2.getSouth()) < sub.get(h).getSouth() - sub.get(h).getNorth() + s2.getSouth() - s2.getNorth()) &&   
									   ((s2.getEast() - s2.getWest()) / (s1.getEast() - s1.getWest()) < 2) &&
									   ((sub.get(h).getEast() - sub.get(h).getWest()) / (s3.getEast() - s3.getWest()) < 2) &&
									   ((s1.getEast() - s1.getWest()) / (s2.getEast() - s2.getWest()) < 2) &&
									   ((s3.getEast() - s3.getWest()) / (sub.get(h).getEast() - sub.get(h).getWest()) < 2) &&
									   sub.get(h).getEast() - sub.get(h).getWest() > sub.get(h).getSouth() - sub.get(h).getNorth()) {
										s4 = sub.get(h);
										faceSet.add(s1);
										faceSet.add(s2);
										faceSet.add(s3);
										faceSet.add(s4);
										sub.get(i).setChecked(1);
										sub.get(j).setChecked(1);
										sub.get(k).setChecked(1);
										sub.get(h).setChecked(1);
										SubImage face = new SubImage();
										face.setWest(s3.getWest() - (s4.getWest() - s3.getEast()) / 2);
										face.setEast(s4.getEast() + (s4.getWest() - s3.getEast()) / 2);
										face.setNorth(s3.getNorth() - (s3.getSouth() - s1.getNorth()) * 2);
										face.setSouth(s3.getSouth() + (s3.getSouth() - s1.getNorth()) * 4);
										face.setWest(face.getWest() >= 0 ? face.getWest() : 0);
										face.setEast(face.getEast() <= width ? face.getEast() : width);
										face.setNorth(face.getNorth() >= 0 ? face.getNorth() : 0);
										face.setSouth(face.getSouth() <= height ? face.getSouth() : height);
										faceSet.add(face);
										findFace();
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
    public static void main(String args[]) throws IOException {
    	
        // đọc hình ảnh từ máy tính
        try {
            f = new File("C:\\Users\\ADMIN\\Desktop\\split image\\detec\\" + filePath);
            src = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println(e);
        }
        img = ZoomImage.DownSize(src, 1000);
//        img = src;

        // lấy chiều cao và chiều rộng của ảnh
        width = img.getWidth();
        height = img.getHeight();
        srcImage = new SubImage(height, width);
        splitRGB = img.getRGB(10, 10);
        for(int y = 0; y < height; y++) {
        	for(int x = 0; x < width; x++) {
        		if(img.getRGB(x, y) > maxRange) maxRange = img.getRGB(x, y);
        		if(img.getRGB(x, y) < minRange) minRange = img.getRGB(x, y);
        	}
        }
//        splitRGB = ((maxRange - minRange) / 3 + minRange) > splitRGB ? splitRGB : ((maxRange - minRange) / 3 + minRange);
        splitRGB = ((maxRange - minRange) / 3 + minRange);

        for (int y = 0; y < height; y++) {
        	if((int)(1.0 * (y + 1) / height * 100) > end) {
        		end = (int)(1.0 * (y + 1) / height * 100);
        		System.out.println(end + " %");
        	}
            for (int x = 0; x < width; x++) {
                // x,y là toạ độ của ảnh để sửa các giá trị pixel
            	
                int p = img.getRGB(x, y);
                srcImage.addPoint(x, y, p);
                
                if(p < splitRGB) {
                	if(x - 1 >= 0 && srcImage.getPointImage(x - 1, y).getP() < splitRGB) {
                		processImage2(srcImage.getPointImage(x, y), srcImage.getPointImage(x - 1, y));
                		
                	} 
                	if(x - 1 >= 0 && y - 1 >= 0 && srcImage.getPointImage(x - 1, y - 1).getP() < splitRGB) {
                		processImage2(srcImage.getPointImage(x, y), srcImage.getPointImage(x - 1, y - 1));
                		
                	} 
                	if(y - 1 >= 0 && srcImage.getPointImage(x, y - 1).getP() < splitRGB) {
                		processImage2(srcImage.getPointImage(x, y), srcImage.getPointImage(x, y - 1));
                		
                	} 
                	if(x + 1 < width && y - 1 >= 0 && srcImage.getPointImage(x + 1, y - 1).getP() < splitRGB) {
                		processImage2(srcImage.getPointImage(x, y), srcImage.getPointImage(x + 1, y - 1));
                		
                	} 
                	if(srcImage.getPointImage(x, y).isCheck() == false) {
						SubImage subImage = new SubImage();
						subImage.setHeight(height);
						subImage.setWidth(width);
						subImage.addImagePoint(srcImage.getPointImage(x, y));
						sub.add(subImage);
					}
                }
            }
        }
		for(int i = sub.size() - 1; i >= 0; i--) {
			if(sub.get(i).getEast() - sub.get(i).getWest() < 10 && sub.get(i).getSouth() - sub.get(i).getNorth() < 10) sub.remove(i);
		}
        findFace();
		PrintImage(faceSet);
    }
}