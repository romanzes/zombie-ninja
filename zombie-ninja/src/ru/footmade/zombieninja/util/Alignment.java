package ru.footmade.zombieninja.util;

public class Alignment {
	public static final int LEFT = 1;
	public static final int CENTER_HORIZONTAL = 2;
	public static final int RIGHT = 4;
	public static final int TOP = 8;
	public static final int CENTER_VERTICAL = 16;
	public static final int BOTTOM = 32;
	
	private static final int HORIZONTAL_MASK = LEFT | CENTER_HORIZONTAL | RIGHT;
	private static final int VERTICAL_MASK = TOP | CENTER_VERTICAL | BOTTOM;
	
	public int alignment;
	
	public Alignment(int alignment) {
		this.alignment = alignment;
	}
	
	public Alignment(int horizontal, int vertical) {
		alignment = horizontal | vertical;
	}
	
	public int getHorizontalAlignment() {
		return alignment & HORIZONTAL_MASK;
	}
	
	public int getVerticalAlignment() {
		return alignment & VERTICAL_MASK;
	}
}
