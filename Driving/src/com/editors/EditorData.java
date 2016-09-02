package com.editors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.CubicMesh;
import com.LineData;
import com.Point4D;
import com.PolygonMesh;
import com.Texture;
import com.main.loader.LoadingProgressPanel;

public class EditorData {
	
	public static Texture[] worldTextures;
	private static Texture[] objects=null;
	private static CubicMesh[] object3D=null;
	public static Texture[] objectTextures=null;
	public static  CubicMesh[] objectMeshes;
	
	private static PolygonMesh ringShape;
	private static PolygonMesh circleShape;
	public static Texture whiteTexture=null;
	public static Texture redTexture=null;
	public static Texture cyanTexture=null;
	
	public static CubicMesh[] splinesMeshes=null;
	public static Texture[] splinesTextures=null;	
	public static Texture[] splinesEditorTextures=null;
	
	public static String[] splineDescriptions;
	public static String[] objectDescriptions;
	
	private static double maxR=100;
	private static double minR=80;
	private static int n=10;

	

	public static void initialize(LoadingProgressPanel loadingProgressPanel,double scale) { 
		
		loadMeshesAndTextures(loadingProgressPanel,scale);
		
		BufferedImage bf=new BufferedImage(100,100,BufferedImage.TYPE_INT_RGB);
		Graphics2D graph=(Graphics2D) bf.getGraphics();
		graph.setColor(Color.WHITE);
		graph.fillRect(0,0,100,100);
		whiteTexture=new Texture(bf);
		
		graph.setColor(Color.RED);
		graph.fillRect(0,0,100,100);
		redTexture=new Texture(bf);
		
		graph.setColor(Color.CYAN);
		graph.fillRect(0,0,100,100);
		cyanTexture=new Texture(bf);
		
		buildRingShape();
		buildCircleShape();
	
	}
	
	private static void loadMeshesAndTextures(LoadingProgressPanel loadingProgressPanel, double scale) {
	
		try {

			

			
			File directoryImg=new File("lib");
			File[] files=directoryImg.listFiles();
			
			
			
			ArrayList<File> vObjects=new ArrayList<File>();
			
				for(int i=0;files!=null && i<files.length;i++){
					if(files[i].getName().startsWith("object_")){
						
						vObjects.add(files[i]);
					}		
				}
			
			
			objects=new Texture[vObjects.size()];
			
			for(int j=0;j<vObjects.size();j++){
				
				File fileObj=(File) vObjects.get(j);
				
				objects[j]=new Texture(ImageIO.read(fileObj));
						
						
			}
			
			if(loadingProgressPanel!=null)
				loadingProgressPanel.incrementValue(20);
		
			ArrayList<File> vRoadTextures=new ArrayList<File>();
			
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("world_texture_") && files[i].getName().endsWith("_0.jpg")){
					
					vRoadTextures.add(files[i]);
					
				}		
			}
			
			worldTextures=new Texture[vRoadTextures.size()];

			for(int i=0;i<vRoadTextures.size();i++){
				
				worldTextures[i]=new Texture(ImageIO.read(new File("lib/world_texture_"+i+"_0.jpg")));
				
				loadMipMaps(worldTextures[i],"lib/world_texture_"+i);
				
		
			}
			
			if(loadingProgressPanel!=null)
				loadingProgressPanel.incrementValue(5);

			ArrayList<File> v3DObjects=new ArrayList<File>();
			
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
			objectDescriptions=new String[vObjects.size()];
			
			if(loadingProgressPanel!=null)
				loadingProgressPanel.incrementValue(5);
			
			int loading3DSize=v3DObjects.size();
			int loading3DBlockNumbers=30;
			int loading3DBlockSize=loading3DSize/loading3DBlockNumbers;
			if(loading3DBlockSize==0){
				loading3DBlockSize=1;
			}	
			for(int i=0;i<loading3DSize;i++){
					
				object3D[i]=CubicMesh.loadMeshFromFile(new File("lib/object3D_"+i),scale);
				objectMeshes[i]=CubicMesh.loadMeshFromFile(new File("lib/object3D_"+i),scale);
				objectTextures[i]=new Texture(ImageIO.read(new File("lib/object3D_texture_"+i+".jpg")));
				objectDescriptions[i]=objectMeshes[i].getDescription();
				
				if(loadingProgressPanel!=null && i%loading3DBlockSize==0)
					loadingProgressPanel.incrementValue(1);
			}
			
			ArrayList<File> vSPlineMeshes=new ArrayList<File>();
			
			for(int i=0;i<files.length;i++){
				if(files[i].getName().startsWith("spline_mesh_")
						){

					vSPlineMeshes.add(files[i]);

				}		
			}

			splinesMeshes=new CubicMesh[vSPlineMeshes.size()];
			splinesTextures=new Texture[vSPlineMeshes.size()];
            splinesEditorTextures=new Texture[vSPlineMeshes.size()];
            splineDescriptions=new String[vSPlineMeshes.size()];
            
    		if(loadingProgressPanel!=null)
				loadingProgressPanel.incrementValue(5);
            
			int loadingSplinesSize=vSPlineMeshes.size();
			for(int i=0;i<loadingSplinesSize;i++){

				splinesMeshes[i]=CubicMesh.loadMeshFromFile(new File("lib/spline_mesh_"+i),scale);
				splinesTextures[i]=new Texture(ImageIO.read(new File("lib/spline_texture_"+i+".jpg")));
				splinesEditorTextures[i]=new Texture(ImageIO.read(new File("lib/spline_editor_"+i+".jpg")));
				
				splineDescriptions[i]=splinesMeshes[i].getDescription();
				
				if(loadingProgressPanel!=null)
					loadingProgressPanel.incrementValue(1);
			}
			if(loadingProgressPanel!=null)
				loadingProgressPanel.incrementValue(10);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	private static void loadMipMaps(Texture texture, String fileBase) throws IOException {
		
		File mipFile = new File(fileBase+"_1.jpg");
		if(mipFile.exists()){
			
			Texture mipTexture = new Texture(ImageIO.read(new File(fileBase+"_1.jpg")));
			texture.setMipTexture1(mipTexture);
		}
		
		mipFile = new File(fileBase+"_2.jpg");
		if(mipFile.exists()){
			
			Texture mipTexture = new Texture(ImageIO.read(new File(fileBase+"_2.jpg")));
			texture.setMipTexture2(mipTexture);
		}
		
		mipFile = new File(fileBase+"_3.jpg");
		if(mipFile.exists()){
			
			Texture mipTexture = new Texture(ImageIO.read(new File(fileBase+"_3.jpg")));
			texture.setMipTexture3(mipTexture);
		}
		
	}

	private static void buildCircleShape() {
	
	
		double dteta=2*Math.PI/n;
		
		//Vector vTexturePoints=RoadEditor.buildTemplateTexturePoints(100);
		//circleShape.setTexturePoints(vTexturePoints);
		
		ArrayList<LineData> polygonData=new ArrayList<LineData>();
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
		
		ringShape=buildRingShape(minR,maxR);
	}

	private static PolygonMesh buildRingShape(double minR,double maxR) {
	
				
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
		
		ArrayList<LineData> polygonData=new ArrayList<LineData>();
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
	
		
		PolygonMesh ringShape=new PolygonMesh(points,polygonData);
		return ringShape;
		
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
	
	public static PolygonMesh getRing(double x,double y,double z,double minR,double maxR){
		
		PolygonMesh ring=buildRingShape(minR, maxR);
		
		ring.translate(x,y,z);
		
		return ring;
	}


}
