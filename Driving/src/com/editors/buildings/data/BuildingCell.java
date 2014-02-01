package com.editors.buildings.data;

import java.awt.Point;
import java.awt.Rectangle;

import com.editors.DoubleTextField;

public class BuildingCell {
	
	double nw_x=0;
	double nw_y=0;
	double x_side=0;
	double y_side=0;
	
    int i=0;
    int j=0;
    
    boolean filled=false;	
	   
    boolean selected=false;

	
	public BuildingCell(double nw_x, double nw_y, double x_side, double y_side, int i,int j) {
		super();
		this.nw_x = nw_x;
		this.nw_y = nw_y;
		this.x_side = x_side;
		this.y_side = y_side;
		this.i = i;
		this.j = j;
	}

	public double getNw_x() {
		return nw_x;
	}

	public void setNw_x(double nw_x) {
		this.nw_x = nw_x;
	}

	public double getNw_y() {
		return nw_y;
	}

	public void setNw_y(double nw_y) {
		this.nw_y = nw_y;
	}

	public double getX_side() {
		return x_side;
	}

	public void setX_side(double x_side) {
		this.x_side = x_side;
	}

	public double getY_side() {
		return y_side;
	}

	public void setY_side(double y_side) {
		this.y_side = y_side;
	}


	
	public String toString() {
	
		return i+","+j+","+isFilled();
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean contains(Point pt) {
	

		Rectangle rect=new Rectangle((int)getNw_x(),(int)getNw_y(),(int)getX_side(),(int)getY_side());
		
		//System.out.println("["+pt.x+" "+pt.y+"]"+rect.x+" "+rect.y+" "+rect.width+" "+rect.height+"="+rect.contains(pt));

		
		
		return rect.contains(pt);
	}

	public boolean isFilled() {
		return filled;
	}

	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	

	/*private BuildingCell buildCell(String str) {
		String[] vals = str.split(",");
		
		double nw_x =Double.parseDouble(vals[0]);
		double nw_y = Double.parseDouble(vals[1]);
		double x_side =Double.parseDouble(vals[2]);
		double y_side = Double.parseDouble(vals[3]);
		
		BuildingCell cell=new BuildingCell(nw_x,nw_y,x_side,y_side);
		
		return cell;
	}*/


}
