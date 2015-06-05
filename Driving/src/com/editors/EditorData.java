package com.editors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.Point4D;
import com.PolygonMesh;
import com.Texture;

public class EditorData {
	
	public static Texture[] worldTextures;
	public static Texture[] objects=null;
	public static CubicMesh[] object3D=null;
	public static Texture[] objectTextures=null;
	public static  CubicMesh[] objectMeshes;
	
	public static PolygonMesh ringShape;
	public static PolygonMesh circleShape;
	public static Texture whiteTexture=null;
	public static Texture redTexture=null;
	
	public static CubicMesh[] splinesMeshes=null;
	public static Texture[] splinesTextures=null;	
	public static Texture[] splinesEditorTextures=null;
	
	public static double maxR=100;
	public static double minR=80;
	public static int n=10;

	public static void initialize() { 
		
		loadMeshesAndTextures();
		
		BufferedImage bf=new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
		Graphics2D graph=(Graphics2D) bf.getGraphics();
		graph.setColor(Color.WHITE);
		graph.fillRect(0,0,100,100);
		whiteTexture=new Texture(bf);
		
		graph.setColor(Color.RED);
		graph.fillRect(0,0,100,100);
		redTexture=new Texture(bf);
		
		buildRingShape();
		buildCircleShape();
	
	}
	
	public static void loadMeshesAndTextures() {
	
		try {

			

			
			File directoryImg=new File("lib");
			File[] files=directoryImg.listFiles();
			
			
			
			Vector vObjects=new Vector();
			
				for(int i=0;i<files.length;i++){
					if(files[i].getName().startsWith("object_")){
						
						vObjects.add(files[i]);
					}		
				}
			
			
			objects=new Texture[vObjects.size()];
			
			for(int j=0;j<vObjects.size();j++){
				
				File fileObj=(File) vObjects.elementAt(j);
				
				objects[j]=new Texture(ImageIO.read(fileObj));
						
						
			}
		
			Vector vRoadTextures=new Vector();
			
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("world_texture_")){
					
					vRoadTextures.add(files[i]);
					
				}		
			}
			
			worldTextures=new Texture[vRoadTextures.size()];

			for(int i=0;i<vRoadTextures.size();i++){
				
				worldTextures[i]=new Texture(ImageIO.read(new File("lib/world_texture_"+i+".jpg")));
			}

			Vector v3DObjects=new Vector();
			
				for(int i=0;i<files.length;i++){
					if(files[i].getName().startsWith("object3D_")
					   && 	!files[i].getName().startsWith("object3D_texture")	
					){
						
						v3DObjects.add(files[i]);
						
					}		
				}

			object3D=new CubicMesh[v3DObjects.size()];
			objectTextures=new Texture[v3DObjects.size()];
			objectMeshes=new CubicMesh[vObjects.size()];
			
		
			for(int i=0;i<v3DObjects.size();i++){
					
				object3D[i]=CubicMesh.loadMeshFromFile(new File("lib/object3D_"+i));
				objectMeshes[i]=CubicMesh.loadMeshFromFile(new File("lib/object3D_"+i));
				objectTextures[i]=new Texture(ImageIO.read(new File("lib/object3D_texture_"+i+".jpg")));
			
			}
			

			
			Vector vSPlineMeshes=new Vector();
			
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("spline_mesh_")
						){

					vSPlineMeshes.add(files[i]);

				}		
			}

			splinesMeshes=new CubicMesh[vSPlineMeshes.size()];
			splinesTextures=new Texture[vSPlineMeshes.size()];
            splinesEditorTextures=new Texture[vSPlineMeshes.size()];


			for(int i=0;i<vSPlineMeshes.size();i++){

				splinesMeshes[i]=CubicMesh.loadMeshFromFile(new File("lib/spline_mesh_"+i));
				splinesTextures[i]=new Texture(ImageIO.read(new File("lib/spline_texture_"+i+".jpg")));
				splinesTextures[i]=new Texture(ImageIO.read(new File("lib/spline_editor_"+i+".jpg")));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private static void buildCircleShape() {
	
	
		double dteta=2*Math.PI/n;
		
		//Vector vTexturePoints=RoadEditor.buildTemplateTexturePoints(100);
		//circleShape.setTexturePoints(vTexturePoints);
		
		Vector polygonData=new Vector();
		Point4D[] points=new Point4D[n];
		
		LineData ld=new LineData();
		for (int i = 0; i <= n; i++) {
			
			double teta=i*dteta;
			
			double x=maxR*Math.cos(teta);
			double y=maxR*Math.sin(teta);			
			
			if(i<n)
				points[i]=new Point4D(x,y,0);
			
			ld.addIndex(i%n,i%n,x,y);
		}
		
		polygonData.add(ld);
		circleShape=new PolygonMesh(points,polygonData);

		
	}

	private static void buildRingShape() {
	

		
		double dteta=2*Math.PI/n;
		
		//Vector vTexturePoints=RoadEditor.buildTemplateTexturePoints(100);
		//circleShape.setTexturePoints(vTexturePoints);
		
		Point4D[] points=new Point4D[2*n];
		
		
		for (int i = 0; i < n; i++) {
			
			double teta=i*dteta;
			
			double x=maxR*Math.cos(teta);
			double y=maxR*Math.sin(teta);
			
			double xm=minR*Math.cos(teta);
			double ym=minR*Math.sin(teta);
			
			points[i]=new Point4D(x,y,0);
			points[i+n]=new Point4D(xm,ym,0);
			
			
		}
		
		Vector polygonData=new Vector();
		for (int i = 0; i < n; i++) {
			
			int indx0=i;
			int indx1=(i+1)%n;
			int indx2=(i+1)%n+n;
			int indx3=i+n;
			
			Point4D p0= points[indx0];
			Point4D p1= points[indx1];
			Point4D p2= points[indx2];
			Point4D p3= points[indx3];
			
			LineData ld=new LineData();
			ld.addIndex(indx0,indx0,p0.x,p0.y);
			ld.addIndex(indx1,indx1,p1.x,p1.y);
			ld.addIndex(indx2,indx2,p2.x,p2.y);
			ld.addIndex(indx3,indx3,p3.x,p3.y);
			
			polygonData.add(ld);
		}
		
		
		
		
		
		ringShape=new PolygonMesh(points,polygonData);
		
	}

	public static PolygonMesh getRing(double x,double y,double z){
		
		PolygonMesh ring=ringShape.clone();
		
		ring.translate(x,y,z);
		
		return ring;
	}
	
	public static PolygonMesh getCircle(double x,double y,double z){
		
		PolygonMesh circle=circleShape.clone();
		
		circle.translate(x,y,z);
		
		return circle;
	}

}
