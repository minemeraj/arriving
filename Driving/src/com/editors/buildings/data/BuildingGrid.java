package com.editors.buildings.data;

import java.util.Vector;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class BuildingGrid {
	

	double nw_x=0;
	double nw_y=0;
	double x_side=0;
	double y_side=0;
	double z_side=0;
	
	int xnum=0;
	int ynum=0;
	
	public BuildingCell[][] cells=null;
	
	public BuildingGrid(){}
	
	public BuildingGrid(double nw_x, double nw_y, double x_side, double y_side,double z_side,
			int xnum, int ynum) {
		super();
		this.nw_x = nw_x;
		this.nw_y = nw_y;
		this.x_side = x_side;
		this.y_side = y_side;
		this.z_side = z_side;
		this.xnum = xnum;
		this.ynum = ynum;
		
		cells=new BuildingCell[xnum][ynum];
		
		for (int i = 0; i < xnum; i++) {
			
			for (int j = 0; j <ynum; j++) {
				cells[i][j]=new BuildingCell(nw_x+(i-1)*x_side,nw_y+(j-1)*y_side,x_side,y_side,z_side,i,j);
			}
			
		}
	}
	
	public Object clone(){
		
		BuildingGrid grid=new BuildingGrid(nw_x,nw_y,x_side,y_side,z_side,xnum,ynum);
		
		for (int i = 0; i < xnum; i++) {
			
			for (int j = 0; j <ynum; j++) {
				grid.cells[i][j].setFilled(cells[i][j].isFilled());
			}
			
		}
		
		return grid;
		
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
	public int getXnum() {
		return xnum;
	}
	public void setXnum(int xnum) {
		this.xnum = xnum;
	}
	public int getYnum() {
		return ynum;
	}
	public void setYnum(int ynum) {
		this.ynum = ynum;
	}

	public String toString() {
		
		return nw_x+","+nw_y+","+x_side+","+y_side+","+z_side+","+xnum+","+ynum;
	}
	
	public static BuildingGrid buildGrid(String str) {
		
		String[] vals = str.split(",");
		
		double nw_x =Double.parseDouble(vals[0]);
		double nw_y = Double.parseDouble(vals[1]);
		double x_side =Double.parseDouble(vals[2]);
		double y_side = Double.parseDouble(vals[3]);
		double z_side = Double.parseDouble(vals[4]);
		int xnum = Integer.parseInt(vals[5]);
		int ynum =  Integer.parseInt(vals[6]);
		
		BuildingGrid grid=new BuildingGrid(nw_x,nw_y,x_side,y_side,z_side,xnum,ynum);
	
		return grid;
	}

	public BuildingCell[][] getCells() {
		return cells;
	}

	public void setCells(BuildingCell[][] cells) {
		this.cells = cells;
	}
	
	public PolygonMesh buildMesh(){



		Vector points=new Vector();
		points.setSize((xnum+1)*(ynum+1)*2);

		Vector polyData=new Vector();

		for (int k = 0; k < 2; k++) {

			for (int i = 0; i < xnum+1; i++) {

				for (int j = 0; j <ynum+1; j++) {

					Point3D p=new Point3D(i*x_side,j*y_side,k*z_side);
					points.setElementAt(p,pos(i,j,k));
				}

			}

		}


		for (int i = 0; i < xnum; i++) {

			for (int j = 0; j <ynum; j++) {



				if(!cells[i][j].isFilled())
					continue;

				LineData topLD=new LineData();
				topLD.addIndex(pos(i,j,1));
				topLD.addIndex(pos(i+1,j,1));					
				topLD.addIndex(pos(i+1,j+1,1));
				topLD.addIndex(pos(i,j+1,1));	
				topLD.setData(""+Renderer3D.CAR_TOP);
				polyData.add(topLD);


				LineData bottomLD=new LineData();
				bottomLD.addIndex(pos(i,j,0));
				bottomLD.addIndex(pos(i,j+1,0));					
				bottomLD.addIndex(pos(i+1,j+1,0));
				bottomLD.addIndex(pos(i+1,j,0));
				bottomLD.setData(""+Renderer3D.CAR_BOTTOM);
				polyData.add(bottomLD);
				
				LineData leftLD=new LineData();
				leftLD.addIndex(pos(i,j,0));
				leftLD.addIndex(pos(i,j,1));								
				leftLD.addIndex(pos(i,j+1,1));
				leftLD.addIndex(pos(i,j+1,0));		
				leftLD.setData(""+Renderer3D.CAR_LEFT);
				if(i==0 || !cells[i-1][j].isFilled())
					polyData.add(leftLD);
				
				
				LineData rightLD=new LineData();
				rightLD.addIndex(pos(i+1,j,0));
				rightLD.addIndex(pos(i+1,j+1,0));					
				rightLD.addIndex(pos(i+1,j+1,1));
				rightLD.addIndex(pos(i+1,j,1));	
				rightLD.setData(""+Renderer3D.CAR_RIGHT);
				if(i==xnum-1 || !cells[i+1][j].isFilled())				
					polyData.add(rightLD);
				
				
				LineData backLD=new LineData();
				backLD.addIndex(pos(i,j,0));
				backLD.addIndex(pos(i+1,j,0));					
				backLD.addIndex(pos(i+1,j,1));
				backLD.addIndex(pos(i,j,1));
				backLD.setData(""+Renderer3D.CAR_BACK);	
				if(j==0 || !cells[i][j-1].isFilled())				
					polyData.add(backLD);
				
				LineData frontLD=new LineData();
				frontLD.addIndex(pos(i,j+1,0));
				frontLD.addIndex(pos(i,j+1,1));					
				frontLD.addIndex(pos(i+1,j+1,1));
				frontLD.addIndex(pos(i+1,j+1,0));	
				frontLD.setData(""+Renderer3D.CAR_FRONT);
				if(j==ynum-1 || !cells[i][j+1].isFilled())				
					polyData.add(frontLD);

			}
		}	



		PolygonMesh pm=new PolygonMesh(points,polyData);

		PolygonMesh spm=PolygonMesh.simplifyMesh(pm);
		return spm;


	}
	
	public int pos(int i, int j, int k){
		
		return (i+(xnum+1)*j)*2+k;
	}

}
