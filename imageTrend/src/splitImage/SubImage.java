package splitImage;

import java.awt.Color;
import java.util.ArrayList;

public class SubImage {
	private int height,
				width,
				west = 0,
				east = 0,
				north = 0,
				south = 0;
	private int checked = 0;
    private Color myWhite = new Color(255, 255, 255); // Color white
	private ArrayList<PointImage> points = new ArrayList<PointImage>();

	public SubImage() {
		super();
	}
	public SubImage(int height, int width) {
		super();
		this.height = height;
		this.width = width;
		for(int y = 0; y < this.height; y++) {
			for(int x = 0; x < this.width; x++) {
				PointImage point = new PointImage(x, y, myWhite.getRGB());
				points.add(point);
			}
		}
	}
	
	
	public int getChecked() {
		return checked;
	}
	public void setChecked(int checked) {
		this.checked = checked;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
		this.north = height;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
		this.west = width;
	}
	public int getWest() {
		return west;
	}
	public void setWest(int west) {
		this.west = west;
	}
	public int getEast() {
		return east;
	}
	public void setEast(int east) {
		this.east = east;
	}
	public int getNorth() {
		return north;
	}
	public void setNorth(int north) {
		this.north = north;
	}
	public int getSouth() {
		return south;
	}
	public void setSouth(int south) {
		this.south = south;
	}
	public ArrayList<PointImage> getPoint() {
		return points;
	}

	public void setPoint(ArrayList<PointImage> point) {
		this.points = point;
	}
	
	public void addPoint(int x, int y, int p) {
		points.get(x + y * width).setX(x);
		points.get(x + y * width).setY(y);
		points.get(x + y * width).setP(p);
	}
	
	public PointImage getPointImage(int x, int y) {
		return points.get(x + y * width);
	}
	
	public void setPointImage(int x, int y, int p) {
		points.get(x + y * width).setX(x);
		points.get(x + y * width).setY(y);
		points.get(x + y * width).setP(p);
		points.get(x + y * width).setCheck(true);
	}
	
	public void addImagePoint(PointImage currentPoint) {
		points.add(currentPoint);
		if(currentPoint.getX() > east) east = currentPoint.getX();
		if(currentPoint.getX() < west) west = currentPoint.getX();
		if(currentPoint.getY() > south) south = currentPoint.getY();
		if(currentPoint.getY() < north) north = currentPoint.getY();
	}
	
	public void setDefault() {
		for (PointImage pointImage : points) {
			pointImage.setCheck(false);
		}
	}
}
