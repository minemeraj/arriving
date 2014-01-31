package com.editors.buildings.data;

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
	
	BuildingCell northCell=null;
	BuildingCell southCell=null;
	BuildingCell eastCell=null;
	BuildingCell westhCell=null;
	
	   
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
	}

	public BuildingCell getSouthCell() {
		return southCell;
	}

	public void setSouthCell(BuildingCell southCell) {
		this.southCell = southCell;
	}

	public BuildingCell getEastCell() {
		return eastCell;
	}

	public void setEastCell(BuildingCell eastCell) {
		this.eastCell = eastCell;
	}

	public BuildingCell getWesthCell() {
		return westhCell;
	}

	public void setWesthCell(BuildingCell westhCell) {
		this.westhCell = westhCell;
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
			
		}else if(position==WEST && getWesthCell()==null){
			
			BuildingCell nCell=new BuildingCell(nw_x-x_side,nw_y,x_side,y_side);
			setWesthCell(nCell);
			
		}
		
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}


}
