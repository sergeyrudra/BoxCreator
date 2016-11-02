package com.flywolf.furniture;

import java.util.ArrayList;

import com.flywolf.furniture.CommonStatic.BoxesType;
import com.flywolf.furniture.CommonStatic.Detail;

public class BoxesFactory {

	    public Box getBox(BoxesType type) {
	    	Box writer = null;
	        if (type== BoxesType.box1) {
	            writer = new Box1();
	        } else if (type== BoxesType.box2) {
	            writer = new Box2();
	        } else if (type== BoxesType.box3) {
	            writer = new Box3();
	        } else if (type== BoxesType.box4) {
	            writer = new Box4();
	        } else if (type== BoxesType.box5) {
	            writer = new Box5();
	        } else if (type== BoxesType.box6) {
	            writer = new Box6();
	        } else if (type== BoxesType.box7) {
	            writer = new Box7();
	        } else if (type== BoxesType.box8) {
	            writer = new Box8();
	        } else if (type== BoxesType.box9) {
	            writer = new Box9();
	        } else if (type== BoxesType.box10) {
	            writer = new Box10();
	        } else if (type== BoxesType.box11) {
	            writer = new Box11();
	        } else if (type== BoxesType.box12) {
	            writer = new Box12();
	        } else if (type== BoxesType.box13) {
	            writer = new Box13();
	        } else if (type== BoxesType.box14) {
	            writer = new Box14();
	        } else if (type== BoxesType.box15) {
	            writer = new Box15();
	        } else if (type== BoxesType.box16) {
	            writer = new Box16();
	        } else if (type== BoxesType.box17) {
	            writer = new Box17();
	        } else if (type== BoxesType.box18) {
	            writer = new Box18();
	        } else if (type== BoxesType.box19) {
	            writer = new Box19();
	        } else if (type== BoxesType.box20) {
	            writer = new Box20();
	        } else if (type== BoxesType.box21) {
	            writer = new Box21();
	        } else if (type== BoxesType.box22) {
	            writer = new Box22();
	        } else if (type== BoxesType.box23) {
	            writer = new Box23();
	        } else if (type== BoxesType.box24) {
	            writer = new Box24();
	        } else if (type== BoxesType.box25) {
	            writer = new Box25();
	        }
	        else{
	        	//throw new Exception("no box");
	            //writer = new Box1();
	        	
	        }
	        return writer;
	    }

	    private int getEdgeLong(ArrayList<Detail> details) {
			int res = 0;
			for (Detail d : details) {
				res += ((d.getLengthEdge() * d.getLength()) + (d.getWidthEdge() * d
						.getWidth())) * d.getQuantity();

			}
			return res;
		}
}
