package com.anthdel.verletsystem;

public class Color {
	public static final Color WHITE = new Color(1, 1, 1, 1);
	public static final Color BLACK = new Color(0, 0, 0, 1);
	public static final Color RED = new Color(1, 0, 0, 1);
	public static final Color GREEN = new Color(0, 1, 0, 1);
	public static final Color BLUE = new Color(0, 0, 1, 1);
	
	public float r, g, b, a;

	public Color(float r, float g, float b, float a) {
		setColor(r, g, b, a);
	}

	public void setColor(Color color) {
		this.setColor(color.r, color.g, color.b, color.a);
	}

	public void setColor(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		clamp();
	}

	public void lerp(Color colorB, float percentage) {
		r += (colorB.r - r) * percentage;
		g += (colorB.g - g) * percentage;
		b += (colorB.b - b) * percentage;
		a += (colorB.a - a) * percentage;
	}
	
	private void clamp() {
		if (r > 1) r = 1; else if (r < 0) r = 0;
		if (g > 1) g = 1; else if (g < 0) g = 0;
		if (b > 1) b = 1; else if (b < 0) b = 0;
		if (a > 1) a = 1; else if (a < 0) a = 0;
	}

}
