package splitImage;

public class PointImage {
	private int x,
				y,
				p;
	private boolean check = false;

	public PointImage() {
		super();
		this.check = false;
	}
	
	public PointImage(int x, int y, int p) {
		super();
		this.x = x;
		this.y = y;
		this.p = p;
		this.check = false;
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
	
}
