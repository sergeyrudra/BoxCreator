package com.flywolf.furniture;

public class CommonStatic {
	public final static int defaultX = 400;
	public final static int defaultY = 350;
	public final static int defaultZ = 450;
	public final static int defaultRackQuantity = 1;

	public static enum BoxesType {
		box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12, box13, box14, box15, box16, box17, box18, box19, box20, box21, box22, box23, box24, box25
	}

	public static enum SettingsType {
		EDGE_THICK,FURNITURE_COST, HOUR_COST, RENT_COST, HOURS_MAKE, PANEL_Y, MEZZANINE_Y, MARGIN_COST, DSP_THICK, SOCLE_Y, FACADE_CLEARANCE, REAR_DEEP, DSP_COST, REAR_COST, EDGE_COST, LEFT_PART_X, RIGHT_PART_X, CLOSET_DEEP, RACK_DEEP, REAR_MARG, TABLE_LEDGE_X, TABLE_LEDGE_Z, COMPUTER_X, STRUT_WIDTH, PULL_OUT_DRAWER_HEIGHT, PULL_OUT_DRAWER_GUIDES_INDENT, PULL_OUT_DRAWER_HEIGHT_INDENT
	}

	public static enum Details {
		BACK, BOTTOM, RACK, REAR, FACADE, TOP, SOCLE, BACK_MIDDLE, STRUT, PANEL, PULL_OUT_DRAWER_BACK, PULL_OUT_DRAWER_BOTTOM, PULL_OUT_DRAWER_REAR_FRONT, PULL_OUT_DRAWER_FACADE
	}

	public static class BoxSetting {
		SettingsType type;
		int value;
		String additional;

		public BoxSetting(SettingsType type, int value) {
			super();
			this.type = type;
			this.value = value;
		}
	}

	public static int pullOutDrawerDeep(int z) {
		// 250мм, 300мм, 350мм, 400мм, 450мм, 500мм, 550мм, 600
		int margin=1;
		if (z > 600 + margin)
			return 600;
		if (z > 550 + margin)
			return 550;
		if (z > 500 + margin)
			return 500;
		if (z > 450 + margin)
			return 450;
		if (z > 4000 + margin)
			return 400;
		if (z > 350 + margin)
			return 350;
		if (z > 300 + margin)
			return 300;
		if (z > 250 + margin)
			return 250;
		return z - margin;

	}

	public static class Detail {
		CommonStatic.Details name;
		int width;
		int length;
		int widthEdge;
		int lengthEdge;
		int quantity;
		String material;
		String description;
		int additional;

		
		public Detail(CommonStatic.Details name, int width, int length,
				int widthEdge, int lengthEdge, int quantity, int dbQuantity, int edgeThick, String description, int additional) {
			super();
			this.name = name;
			this.width = width-Math.round((lengthEdge*(edgeThick/100)));
			this.length = length-Math.round((widthEdge*(edgeThick/100)));
			this.widthEdge = widthEdge;
			this.lengthEdge = lengthEdge;
			this.quantity = quantity * dbQuantity;
			this.material = "dsp";
			this.description = description;
			this.additional = additional;
		}
		
		public Detail(CommonStatic.Details name, int width, int length,
				int widthEdge, int lengthEdge, int quantity, int dbQuantity, int edgeThick) {
			super();
			this.name = name;
			this.width = width-Math.round((lengthEdge*(edgeThick/100)));
			this.length = length-Math.round((widthEdge*(edgeThick/100)));
			this.widthEdge = widthEdge;
			this.lengthEdge = lengthEdge;
			this.quantity = quantity * dbQuantity;
			this.material = "dsp";
		}

		public Detail(CommonStatic.Details name, String material, int width,
				int length, int widthEdge, int lengthEdge, int quantity,
				int dbQuantity) {
			super();
			this.name = name;
			this.material = material;
			this.width = width;
			this.length = length;
			this.widthEdge = widthEdge;
			this.lengthEdge = lengthEdge;
			this.quantity = quantity * dbQuantity;
		}

		public CommonStatic.Details getName() {
			return name;
		}

		public String getMaterial() {
			return material;
		}

		public void setMaterial(String material) {
			this.material = material;
		}

		public void setName(CommonStatic.Details name) {
			this.name = name;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public int getWidthEdge() {
			return widthEdge;
		}

		public void setWidthEdge(int widthEdge) {
			this.widthEdge = widthEdge;
		}

		public int getLengthEdge() {
			return lengthEdge;
		}

		public void setLengthEdge(int lengthEdge) {
			this.lengthEdge = lengthEdge;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

	}

}
