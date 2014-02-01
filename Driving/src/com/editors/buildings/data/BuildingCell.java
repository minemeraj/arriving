package com.editors.buildings.data;

import java.awt.Point;
import java.awt.Rectangle;

import com.editors.DoubleTextField;

public class BuildingCell {
	
	double nw_x=0;
	double nw_y=0;
	double x_side=0;
	double y_side=0;
	
	public static int CENTER=0;
	public static int NORTH=1;
	public static int SOUTH=2;
	public static int WEST=3;
	public static int EAST=4;
	
	public BuildingCell northCell=null;
	public BuildingCell southCell=null;
	public BuildingCell eastCell=null;
	public BuildingCell westCell=null;
	
	   
    boolean selected=false;

	
	public BuildingCell(double nw_x, double nw_y, double x_side, double y_side) {
		super();
		this.nw_x = nw_x;
		this.nw_y = nw_y;
		this.x_side = x_side;
		this.y_side = y_side;
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

	public BuildingCell getNorthCell() {
		return northCell;
	}

	public void setNorthCell(BuildingCell northCell) {
		this.northCell = northCell;
		//northCell.southCell=this;
	}

	public BuildingCell getSouthCell() {
		return southCell;
	}

	public void setSouthCell(BuildingCell southCell) {
		this.southCell = southCell;
		//southCell.northCell=this;
	}

	public BuildingCell getEastCell() {
		return eastCell;
	}

	public void setEastCell(BuildingCell eastCell) {
		this.eastCell = eastCell;
		//eastCell.westCell=this;
	}

	public BuildingCell getWestCell() {
		return westCell;
	}

	public void setWestCell(BuildingCell westCell) {
		this.westCell = westCell;
		//westCell.eastCell=this;
	}


	
	public String toString() {
	
		return nw_x+","+nw_y+","+x_side+","+y_side;
	}

	public void addCell(int position) {
		
		if(position==NORTH && getNorthCell()==null){
			
			BuildingCell nCell=new BuildingCell(nw_x,nw_y+y_side,x_side,y_side);
			setNorthCell(nCell);
			
		}else if(position==SOUTH && getSouthCell()==null){
			
			BuildingCell nCell=new BuildingCell(nw_x,nw_y-y_side,x_side,y_side);
			setSouthCell(nCell);
			
		}else if(position==EAST && getEastCell()==null){
			
			BuildingCell nCell=new BuildingCell(nw_x+x_side,nw_y,x_side,y_side);
			setEastCell(nCell);
			
		}else if(position==WEST && getWestCell()==null){
			
			BuildingCell nCell=new BuildingCell(nw_x-x_side,nw_y,x_side,y_side);
			setWestCell(nCell);
			
		}
		
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


}
