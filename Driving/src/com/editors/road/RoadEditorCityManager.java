package com.editors.road;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.Point3D;
import com.Point4D;
import com.PolygonMesh;
import com.SPLine;
import com.SPNode;
import com.SquareMesh;
import com.editors.DoubleTextField;

public class RoadEditorCityManager extends JDialog implements ActionListener{
	
	private JPanel center;
	private JPanel bottom;
	
	int NX=10;
	int NY=10;
	
	double DX=200;
	double DY=200;
	
	double X0=0;
	double Y0=0;
	
	int WIDTH=230;
	int HEIGHT=230;
	private DoubleTextField NX_Field;
	private DoubleTextField NY_Field;
	
	private DoubleTextField X0_Field; 
	private DoubleTextField Y0_Field;
	
	private Object returnValue=null;
	
	JButton update=null;
	JButton cancel=null;
	
	SquareMesh squareMesh=null;
	
	boolean is_expand_mode=false;
	

	public RoadEditorCityManager(SquareMesh squareMesh){
		
		if(squareMesh!=null){
			is_expand_mode=true;
			this.squareMesh=squareMesh;
		}
		
		setTitle("Create new grid");
		setLayout(null);


		setSize(WIDTH,HEIGHT);
		setModal(true);
		center=new JPanel(null);
		center.setBounds(0,0,WIDTH,HEIGHT);
		add(center);
		
		int r=10;
		
		JLabel jlb=new JLabel("NX:");
		jlb.setBounds(10,r,30,20);
		center.add(jlb);
		
		NX_Field=new DoubleTextField();
		NX_Field.setBounds(50,r,100,20);
		NX_Field.setToolTipText("NX Blocks");
		center.add(NX_Field);
		
		if(is_expand_mode){
			
			jlb=new JLabel(">"+squareMesh.getNumx());
			jlb.setBounds(160, r, 100, 20);
			center.add(jlb);
		}
		
		r+=30;
		
		jlb=new JLabel("NY:");
		jlb.setBounds(10,r,30,20);
		center.add(jlb);
				
		NY_Field=new DoubleTextField();
		NY_Field.setBounds(50,r,100,20);
		NY_Field.setToolTipText("NY Blocks");
		center.add(NY_Field);
		
		if(is_expand_mode){
			
			jlb=new JLabel(">"+squareMesh.getNumy());
			jlb.setBounds(160, r, 100, 20);
			center.add(jlb);
		}
		
		NX_Field.setText(NX);
		NY_Field.setText(NY);

		
		r+=30;
		
		jlb=new JLabel("X0:");
		jlb.setBounds(10,r,30,20);
		center.add(jlb);
		
		X0_Field=new DoubleTextField();
		X0_Field.setBounds(50,r,100,20);
		center.add(X0_Field);
		
		r+=30;
		
		jlb=new JLabel("Y0:");
		jlb.setBounds(10,r,30,20);
		center.add(jlb);
		
		Y0_Field=new DoubleTextField();
		Y0_Field.setBounds(50,r,100,20);
		center.add(Y0_Field);
		
		X0_Field.setText(0);
		Y0_Field.setText(0);
		
		
		if(squareMesh!=null){
			
			NX_Field.setText(squareMesh.getNumx());
			NY_Field.setText(squareMesh.getNumy());
			X0_Field.setText(squareMesh.getX0());
			Y0_Field.setText(squareMesh.getY0());
			setTitle("Expand terrain grid");
			
			
			
			X0_Field.setEditable(false);
			Y0_Field.setEditable(false);
		}
		
		r+=30;
		
		update=new JButton("Update");
		update.setBounds(10,r,80,20);
		center.add(update);
		update.addActionListener(this);
		
		cancel=new JButton("Cancel");
		cancel.setBounds(100,r,80,20);
		center.add(cancel);
		cancel.addActionListener(this);
		
		returnValue=null;
		
        setVisible(true);

		
	}



	public void actionPerformed(ActionEvent arg0) {


		Object obj = arg0.getSource();
		
		if(obj==update){
			
			update();
			dispose();

		}
		else if(obj==cancel){
			returnValue=null;
			dispose();
		}
		
		
	}



	private void update() {
		try{
			
			NX=(int) NX_Field.getvalue();
			NY=(int) NY_Field.getvalue();
						
			X0= X0_Field.getvalue();
			Y0= Y0_Field.getvalue();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		returnValue=this;
		
		
		
	}



	public Object getReturnValue() {
		return returnValue;
	}



	public int getNX() {
		return NX;
	}



	public void setNX(int nX) {
		NX = nX;
	}



	public int getNY() {
		return NY;
	}



	public void setNY(int nY) {
		NY = nY;
	}



	public double getDX() {
		return DX;
	}



	public void setDX(double dX) {
		DX = dX;
	}



	public double getDY() {
		return DY;
	}



	public void setDY(double dY) {
		DY = dY;
	}



	public double getX0() {
		return X0;
	}



	public void setX0(double x0) {
		X0 = x0;
	}



	public double getY0() {
		return Y0;
	}



	public void setY0(double y0) {
		Y0 = y0;
	}



	public static void buildCustomCity1(PolygonMesh terrainMesh, Vector splines,
			RoadEditorCityManager roadECM, Vector drawObjects, CubicMesh[] objectMeshes) {
		
		
	
		int nx_blocks=roadECM.NX;
		int ny_blocks=roadECM.NY;
		
		int road_textures=2;
		int block_xtextures=2;
		int block_ytextures=3;
		
		int numx=nx_blocks*block_xtextures+(nx_blocks+1)*road_textures;
		int numy=ny_blocks*block_ytextures+(ny_blocks+1)*road_textures;
		
		double dx=roadECM.DX;
		double dy=roadECM.DY;
		
		double x_0=roadECM.X0;
		double y_0=roadECM.Y0;
		
		//new road
		
		splines.clear();
		
		/*roadMesh.polygonData=new Vector();*/
		
		Point4D[] newPoints = new Point4D[numy*numx];
		 
		for(int i=0;i<numx;i++){
			for(int j=0;j<numy;j++){
				
				int tot=i+j*numx;
				
				double x=x_0+dx*i;
				double y=y_0+dy*j;
				
				newPoints[tot]=new Point4D(x,y,0);
				
			}

		}
		
	
		
		for(int i=0;i<numx;i++){
			//for(int j=0;j<numy-1;j++){
				
				if(i%(block_xtextures+road_textures)==1){
					
					Vector vTexturePoints=RoadEditor.buildTemplateTexturePoints(200);
					
					SPLine sp=new SPLine(vTexturePoints);
			
					int tot=i+0*numx;
					Point4D p=newPoints[tot];
							
					SPNode spn0=new SPNode(p.x,p.y,p.z,"FFFFFF",0);					
					sp.addSPNode(spn0);
					
					tot=i+(numy-1)*numx;
					p=newPoints[tot];
					SPNode spn1=new SPNode(p.x,p.y,p.z,"FFFFFF",0);					
					sp.addSPNode(spn1);
					
					splines.add(sp);
				}
				
				
				/*if(i%(block_xtextures+road_textures)>road_textures-1
						
				&& 	j%(block_ytextures+road_textures)>road_textures-1	
						)
					continue;
				
				int tot=i+j*numx;
				
				double x=x_0+dx*i;
				double y=y_0+dy*j;
				
				newPoints[tot]=new Point4D(x,y,0);
				
				int pl1=i+numx*j;
				int pl2=i+numx*(j+1);
				int pl3=i+1+numx*(j+1);
				int pl4=i+1+numx*j;
				
				LineData ld=new LineData(pl1, pl4, pl3, pl2);
				
				if(i%(block_xtextures+road_textures)>road_textures-1)
					ld.setTexture_index(3);
				else
					ld.setTexture_index(0);

				
			    */
				
			//}

		}
		
		///// new terrain
		
		double gapX=-dx;
		double gapY=-dy;

        int numTerrainx=numx+2;
        int numTerrainy=numy+2;
		
        Point3D[] newTerrainPoints = new Point3D[numTerrainx*numTerrainy];

		
		for(int i=0;i<numTerrainx;i++)
			for(int j=0;j<numTerrainy;j++)
			{
				
				Point4D p=new Point4D(i*dx+x_0+gapX,j*dy+y_0+gapY,0);
			
				newTerrainPoints[i+j*numTerrainx]=p;

			}

		terrainMesh.points=newTerrainPoints;
		terrainMesh.polygonData=new Vector();
		
		Vector vTexturePoints=RoadEditor.buildTemplateTexturePoints(200);
		
		for(int i=0;i<numTerrainx-1;i++)
			for(int j=0;j<numTerrainy-1;j++){ 


				//lower base
				
				int pl1=pos(i,j,numTerrainx,numTerrainy);
				int pl2=pos(i+1,j,numTerrainx,numTerrainy);
				int pl3=pos(i+1,j+1,numTerrainx,numTerrainy);
				int pl4=pos(i,j+1,numTerrainx,numTerrainy);
				
				Point3D pt0=(Point3D) vTexturePoints.elementAt(0);
				Point3D pt1=(Point3D) vTexturePoints.elementAt(1);
				Point3D pt2=(Point3D) vTexturePoints.elementAt(2);
				Point3D pt3=(Point3D) vTexturePoints.elementAt(3);

				LineData ld=new LineData();
				ld.addIndex(pl1,0,pt0.x,pt0.y);
				ld.addIndex(pl2,1,pt1.x,pt1.y);
				ld.addIndex(pl3,2,pt2.x,pt2.y);
				ld.addIndex(pl4,3,pt3.x,pt3.y);
				
				ld.setTexture_index(0);
				
				terrainMesh.polygonData.add(ld);
				
				
				
			}
		
		//add objects
		
		drawObjects.clear();
		
		for(int i=0;i<numx-1;i++){
			for(int j=0;j<numy-1;j++){
				
				if(i%(block_xtextures+road_textures)==road_textures
						
				&& 	j%(block_ytextures+road_textures)==road_textures	
						)
					{
					
						int tot=i+j*numx;
					    Point3D p = newPoints[tot];
					    
					    //set object=4 w=200px,l=600px
					    
					    double dpx=(block_xtextures*dx-200)*0.5;
					    double dpy=(block_ytextures*dy-600)*0.5;
					    
					    DrawObject dro=new DrawObject();
					    dro.setX(p.x+dpx);
					    dro.setY(p.y+dpy);
					    dro.setIndex(4);
						dro.dx=objectMeshes[dro.index].getDeltaX2()-objectMeshes[dro.index].getDeltaX();
						dro.dy=objectMeshes[dro.index].getDeltaY2()-objectMeshes[dro.index].getDeltaY();
						dro.dz=objectMeshes[dro.index].getDeltaX();
					    dro.setHexColor("FFFFFF");
					    drawObjects.add(dro);
					    
						if(DrawObject.IS_3D){
							CubicMesh mesh=objectMeshes[dro.getIndex()].clone();
							
							Point3D point = mesh.point000;
							
							double ddx=-point.x+dro.x;
							double ddy=-point.y+dro.y;
							double ddz=-point.z+dro.z;
							
							mesh.translate(ddx,ddy,ddz);
							
							Point3D center=mesh.findCentroid();
							
							if(dro.rotation_angle!=0)
								mesh.rotate(center.x,center.y,Math.cos(dro.rotation_angle),Math.sin(dro.rotation_angle));
							
							dro.setMesh(mesh);
						}
					
					}
			}
		}
	}
	
	
	public static int pos(int i, int j,  int numx, int numy) {
		
		return (i+j*numx);
	}	
	
	
}
