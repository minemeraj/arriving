package com.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Hashtable;
import java.util.Vector;

import com.CubicMesh;
import com.DrawObject;
import com.LineData;
import com.Point3D;
import com.Point4D;
import com.Polygon3D;
import com.PolygonMesh;
import com.ZBuffer;

public class Shader extends Renderer3D{


	public static ZBuffer[] lightZbuffer;

	public static int[] stencilZbuffer;

	ShadowVolume[] shadowVolumes=null;




	public void buildScreen(BufferedImage buf) {

		int length=rgb.length;

		for(int i=0;i<length;i++){


			ZBuffer zb=roadZbuffer[i];
			//set
			if(zb.getZ()>0)
				rgb[i]=stencil(zb.getRgbColor(), stencilZbuffer[i]); 
			else
				rgb[i]=zb.getRgbColor();
			//clean
			zb.set(0,0,0,greenRgb,true);
			stencilZbuffer[i]=0;   


		}

		buf.getRaster().setDataElements( 0,0,WIDTH,HEIGHT,rgb);
		//buf.setRGB(0,0,WIDTH,HEIGHT,rgb,0,WIDTH);


	}

	private int stencil(int rgbColor, int stencil) {

		if(stencil==0)
			return rgbColor;


		int alphas=0xff & (rgbColor>>24);
		int rs = 0xff & (rgbColor>>16);
		int gs = 0xff & (rgbColor >>8);
		int bs = 0xff & rgbColor;

		rs=(int) (0.5*rs);
		gs=(int) (0.5*gs);
		bs=(int) (0.5*bs);

		return alphas <<24 | rs <<16 | gs <<8 | bs;
	}

	public void buildNewZBuffers() {




		super.buildNewZBuffers();

		int lenght=roadZbuffer.length;
		rgb = new int[lenght];	

		for(int i=0;i<lightZbuffer.length;i++){

			lightZbuffer[i]=new ZBuffer(greenRgb,0);

		}

		stencilZbuffer=new int[lenght];

	}



	/*public int calculateShadowColor(double xi, double yi, double zi, double cosin, int argbs) {


		double factor=(lightIntensity*(0.75+0.25*cosin));

		if(!isShadowMap){

			//here calculate shadow with map
			Point4D p_light=calculateLightTransformedPoint(new Point4D(xi,yi,zi),false);

			int p_screen_light_x=(int)calculPespX(p_light);
			int p_screen_light_y=(int)calculPerspY(p_light);

			if(p_screen_light_x>0 && p_screen_light_x<WIDTH && p_screen_light_y>0 && p_screen_light_y<HEIGHT){


				int tot=WIDTH*p_screen_light_y+p_screen_light_x;

				ZBuffer zb_light=lightZbuffer[tot];

				//the depth is yi,adding a bias of 1
				if(zb_light.getZ()<p_light.y-1 && zb_light.p_z>p_light.z+2)				
					factor=factor*0.75;  

			}

		}

		int alphas=0xff & (argbs>>24);
		int rs = 0xff & (argbs>>16);
		int gs = 0xff & (argbs >>8);
		int bs = 0xff & argbs;

		rs=(int) (factor*rs);
		gs=(int) (factor*gs);
		bs=(int) (factor*bs);

		return alphas <<24 | rs <<16 | gs <<8 | bs;

	}*/





	public void calculateShadowMap() {}


	public Point4D calculateLightTransformedPoint(Point3D p,boolean isReal){

		Point4D p_light=new Point4D();

		if(isReal){



			p_light.x=lightPoint.cos[0][0]*(p.x-lightPoint.position.x)+lightPoint.cos[0][1]*(p.y-lightPoint.position.y)+lightPoint.cos[0][2]*(p.z-lightPoint.position.z);
			p_light.y=lightPoint.cos[1][0]*(p.x-lightPoint.position.x)+lightPoint.cos[1][1]*(p.y-lightPoint.position.y)+lightPoint.cos[1][2]*(p.z-lightPoint.position.z);
			p_light.z=lightPoint.cos[2][0]*(p.x-lightPoint.position.x)+lightPoint.cos[2][1]*(p.y-lightPoint.position.y)+lightPoint.cos[2][2]*(p.z-lightPoint.position.z);

		}
		else{



			double lPoint_x=lightPoint.position.x-POSX;
			double lPoint_y=lightPoint.position.y-POSY;
			double lPoint_z=lightPoint.position.z+MOVZ;

			double p_x=p.x;
			double p_y=p.y;
			double p_z=p.z;

			if(VIEW_TYPE==FRONT_VIEW){



			}
			else{
				// reverse rear point
				p_x=-p_x;
				p_y=2*SCREEN_DISTANCE-p_y;

			}

			//System.out.println(lPoint);

			p_light.x=lightPoint.cos[0][0]*(p_x-lPoint_x)+lightPoint.cos[0][1]*(p_y-lPoint_y)+lightPoint.cos[0][2]*(p_z-lPoint_z);
			p_light.y=lightPoint.cos[1][0]*(p_x-lPoint_x)+lightPoint.cos[1][1]*(p_y-lPoint_y)+lightPoint.cos[1][2]*(p_z-lPoint_z);
			p_light.z=lightPoint.cos[2][0]*(p_x-lPoint_x)+lightPoint.cos[2][1]*(p_y-lPoint_y)+lightPoint.cos[2][2]*(p_z-lPoint_z);

		}


		return p_light;

	}



	public void calculateLightTransformedPolygon(Polygon3D pol,boolean isReal){

		for (int i = 0; i < pol.npoints; i++) {

			Point3D p=new Point3D(pol.xpoints[i],pol.ypoints[i],pol.zpoints[i]);

			Point3D p_light=calculateLightTransformedPoint(p,isReal);

			pol.xpoints[i]=(int) p_light.x;
			pol.ypoints[i]=(int) p_light.y;
			pol.zpoints[i]=(int) p_light.z;

		}


	}

	public Point4D calculateLightTransformedVersor(Point3D p,boolean isReal){

		Point4D p_light=new Point4D();

		p_light.x=lightPoint.cos[0][0]*(p.x)+lightPoint.cos[0][1]*(p.y)+lightPoint.cos[0][2]*(p.z);
		p_light.y=lightPoint.cos[1][0]*(p.x)+lightPoint.cos[1][1]*(p.y)+lightPoint.cos[1][2]*(p.z);
		p_light.z=lightPoint.cos[2][0]*(p.x)+lightPoint.cos[2][1]*(p.y)+lightPoint.cos[2][2]*(p.z);

		return p_light;
	}

	public double calculatePercentageCloserFiltering(double[] pcf_values, double z) {

		double total=0;		


		for (int i = 0; i < pcf_values.length; i++) {

			//bias=4
			if(z>4+pcf_values[i])
				total=total+1.0;
		}

		return total/pcf_values.length;
	}


	public void processShadowMapForPCF() {



		for(int y=0;y<HEIGHT;y++){

			for (int x = 0; x < WIDTH ; x++) {



				Vector values=new Vector();

				int tot=y*WIDTH+x;	

				ZBuffer zb=lightZbuffer[tot];

				int minX=x-1;
				int maxX=x+1;

				if(minX<0)
					minX=x;

				if(maxX>=WIDTH)
					maxX=WIDTH-1;

				int minY=y-1;
				int maxY=y+1;

				if(minY<0)
					minY=y;

				if(maxY>=HEIGHT)
					maxY=HEIGHT-1;

				for(int i=minX;i<maxX;i++){

					for (int j = minY; j < maxY; j++) {

						ZBuffer zb_p=lightZbuffer[j*WIDTH+i];
						values.add(zb_p.getZ());

					}					


				}


				int size=values.size();
				zb.pcf_values=new double[size];

				for (int c = 0; c < size; c++) {
					zb.pcf_values[c]=(Double)values.elementAt(c);
				}

			}

		}



	}

	public void calculateShadowVolumes(DrawObject[] drawObjects ){


		shadowVolumes=new ShadowVolume[drawObjects.length];

		for(int i=0;i<drawObjects.length;i++){

			DrawObject dro=drawObjects[i];
			shadowVolumes[i]=buildShadowVolumeBox((CubicMesh)dro.getMesh());
		}	


	}
	
	public  ShadowVolume buildShadowVolumeBox(CubicMesh cm) {
		
		ShadowVolume shadowVolume=initShadowVolume(cm);
		buildShadowVolumeBox(shadowVolume,cm);
		
		return shadowVolume;
		
	}

	public ShadowVolume initShadowVolume(CubicMesh cm) {

		ShadowVolume shadowVolume=new ShadowVolume();
		shadowVolume.allTriangles=new Vector();

		int polSize=cm.polygonData.size();



		for(int i=0;i<polSize;i++){

			LineData ld=cm.polygonData.elementAt(i);

			LineData[] triangles = LineData.divideIntoTriangles(ld);

			for (int j = 0; j < triangles.length; j++) {


				ShadowTriangle st=new ShadowTriangle(triangles[j]);

				shadowVolume.allTriangles.add(st);
			}		

		}

		int aSize= shadowVolume.allTriangles.size(); 

		for (int i = 0; i <aSize; i++) {



			ShadowTriangle triangle0 = (ShadowTriangle) shadowVolume.allTriangles.elementAt(i);


			//ADJACENT TRIANGLES

			Vector adjacentTriangles=new Vector();

			for (int j = 0; j <aSize; j++) {

				if(j==i)
					continue;

				int commonVertices=0;

				LineData triangle1 = (LineData) shadowVolume.allTriangles.elementAt(j);

				int iSize=triangle1.size();

				for (int k = 0; k < iSize; k++) {

					if(
							triangle0.getIndex(0)==triangle1.getIndex(k) || 
							triangle0.getIndex(1)==triangle1.getIndex(k) || 
							triangle0.getIndex(2)==triangle1.getIndex(k)  

							)
						commonVertices++;

					if(k==iSize-2 && commonVertices<1)
						break;

					if(commonVertices==2){
						adjacentTriangles.add(new Integer(j));
						break;
					}

				}



				//can't have more than 3 adjacent triangles?


			}

			if(adjacentTriangles.size()>0)
				triangle0.setAdjacentTriangles(adjacentTriangles);
		}	


		return shadowVolume;
	}

	public  ShadowVolume buildShadowVolumeBox(ShadowVolume shadowVolume,CubicMesh cm) {

		shadowVolume.initFaces();

		Vector edges=new Vector();

        int aSize= shadowVolume.allTriangles.size(); 
        
        Hashtable hEdges=new Hashtable();

		for (int i = 0; i <aSize; i++) {
			


			ShadowTriangle triangle0 = (ShadowTriangle) shadowVolume.allTriangles.elementAt(i);

			Polygon3D polTriangle0=PolygonMesh.getBodyPolygon(cm.points,triangle0);

			if(!isFacing(polTriangle0,Polygon3D.findNormal(polTriangle0),lightPoint.position))
				continue;

			//ADJACENT TRIANGLES

			int[] adjacentTriangles = triangle0.getAdjacentTriangles();

			
			//FINDING EDGES

            

			for (int j = 0; j < adjacentTriangles.length; j++) {
				LineData triangle1 =  (LineData) shadowVolume.allTriangles.elementAt(adjacentTriangles[j]);
				Polygon3D adjPolTriangle=PolygonMesh.getBodyPolygon(cm.points,triangle1);

				if(

						! isFacing(adjPolTriangle,Polygon3D.findNormal(adjPolTriangle),lightPoint.position)

						){

					


					for (int k = 0; k < triangle0.size(); k++) {

						if(
								(
										triangle0.getIndex(k)==triangle1.getIndex(0)  ||
										triangle0.getIndex(k)==triangle1.getIndex(1)  ||
										triangle0.getIndex(k)==triangle1.getIndex(2)  
								)
								&& 
								(
									
									triangle0.getIndex((k+1)%3)==triangle1.getIndex(0)  ||
									triangle0.getIndex((k+1)%3)==triangle1.getIndex(1)  ||
									triangle0.getIndex((k+1)%3)==triangle1.getIndex(2)  
								)

							){
							
								LineData edge=new LineData();
							
								edge.addIndex(triangle0.getIndex(k));
								edge.addIndex(triangle0.getIndex((k+1)%3));
								
								String key0=edge.getIndex(0)+"_"+edge.getIndex(1);
								String key1=edge.getIndex(1)+"_"+edge.getIndex(0);
								if(hEdges.get(key0)==null && hEdges.get(key1)==null ){
									
									edges.add(edge);
									
									hEdges.put(key0,"");
									hEdges.put(key1,"");
								}
							}
					}



					
				}

			}


		}

		//BUILD FRONT CAP
		
		double average_y=0;

		for (int i = 0; i < shadowVolume.allTriangles.size(); i++) {
			

			LineData triangle0 = (LineData) shadowVolume.allTriangles.elementAt(i);
			Polygon3D polTriangle0=PolygonMesh.getBodyPolygon(cm.points,triangle0);

			if(isFacing(polTriangle0,Polygon3D.findNormal(polTriangle0),lightPoint.position)){
				shadowVolume.addToFrontCap(polTriangle0);
				average_y+=Polygon3D.findCentroid(polTriangle0) .y;
			}	
		}	
			
		//double  extrusion=2.0;
		
		average_y=average_y/shadowVolume.frontCap.size();
		double  extrusion=(1000+average_y-lightPoint.position.y)/(average_y-lightPoint.position.y);
		
		//System.out.println(average_y+" "+extrusion+" "+(average_y-lightPoint.position.y));

		//BUILD BACK CAP

		for (int i = 0; i < shadowVolume.frontCap.size(); i++) {

			Polygon3D triangle_front = (Polygon3D) shadowVolume.frontCap.elementAt(i);

			Polygon3D triangle_back=new Polygon3D(3);

			//invert vertices order:j-> 2-j	
			for (int j = 0; j < triangle_front.npoints; j++) {


				triangle_back.xpoints[j]=(int) (extrusion*(triangle_front.xpoints[2-j]-lightPoint.position.x)+lightPoint.position.x);
				triangle_back.ypoints[j]=(int) (extrusion*(triangle_front.ypoints[2-j]-lightPoint.position.y)+lightPoint.position.y);
				triangle_back.zpoints[j]=(int) (extrusion*(triangle_front.zpoints[2-j]-lightPoint.position.z)+lightPoint.position.z);

			}


			shadowVolume.addToBackCap(triangle_back);

		}



		//BUILD FACES

		int eSize=edges.size();
		
		for (int j = 0; j < eSize; j++) {

			LineData edge = (LineData) edges.elementAt(j);

			Vector facePoints=new Vector();

			Point3D p0=cm.points[edge.getIndex(1)];
			Point3D p1=cm.points[edge.getIndex(0)];

			//cast to int the points cause the front cap triangles are build with integer coordinates
			Point3D p2=new Point3D(
					extrusion*((int)p1.x-lightPoint.position.x)+lightPoint.position.x,
					extrusion*((int)p1.y-lightPoint.position.y)+lightPoint.position.y,
					extrusion*((int)p1.z-lightPoint.position.z)+lightPoint.position.z
					);

			Point3D p3=new Point3D(
					extrusion*((int)p0.x-lightPoint.position.x)+lightPoint.position.x,
					extrusion*((int)p0.y-lightPoint.position.y)+lightPoint.position.y,
					extrusion*((int)p0.z-lightPoint.position.z)+lightPoint.position.z
					);

			facePoints.add(p0);
			facePoints.add(p1);
			facePoints.add(p2);
			facePoints.add(p3);

			Polygon3D pol=new Polygon3D(facePoints);
			shadowVolume.addToFaces(pol);

		}


		shadowVolume.buildPolygonsArray();
		
		return shadowVolume;

	}

	/**
	 *  CALCULATE SHADOW VOLUMES
	 * 		
	 *	RENDER NORMAL Z BUFFER SCREEN
	 *	
	 *	RENDER WITHOUT DRAWING AND Z-BUFFERING THE SHADOW VOLUMES
	 *	
	 *	RENDER FRONT AND BACK FACING FACES, DECREMENT AND INCREMENT STENCIL BUFFER
	 *	  
	 *	FINALLY APPLY STENCIL BUFFER
	 * 
	 * 
	 * @param p
	 * @param observer
	 * @param shadowVolumes
	 * @return
	 */

	public void calculateStencilBuffer( ){

		isStencilBuffer=true;

		for (int i = 0; i < shadowVolumes.length; i++) {

			ShadowVolume sv= shadowVolumes[i];

			for (int j = 0; j < sv.allPolygons.length; j++) {

				Polygon3D pol = sv.allPolygons[j];

				Polygon3D polTrans=pol.clone();
				buildTransformedPolygon(polTrans);

				decomposeClippedPolygonIntoZBuffer(polTrans,Color.red,null,roadZbuffer);
			}

		}

		isStencilBuffer=false;

	}

	/**
	 * Z-FAIL STENCIL BUFFER
	 * 
	 */

	public void stencilBuffer(int tot, boolean isFacing) {

		if(isFacing)
			stencilZbuffer[tot]-=1;
		else
			stencilZbuffer[tot]+=1;

	}

	public class ShadowVolume{

		public Vector frontCap=null;
		public Vector backCap=null;
		public Vector faces=null;
		
		public Vector allTriangles=null;

		public Polygon3D[] allPolygons=null;

		public void addToFrontCap(Polygon3D pol){

			frontCap.add(pol);
			
		}

		public void addToBackCap(Polygon3D pol){

			backCap.add(pol);
		
		}

		public void addToFaces(Polygon3D pol){

			faces.add(pol);
		
		}
		
		public void initFaces(){
			
			frontCap=new Vector();
			backCap=new Vector();
			faces=new Vector();
			
			
		}
		
		public void buildPolygonsArray(){
			
			int size=frontCap.size()+backCap.size()+faces.size();
			
			Vector vAllpolygons=new Vector();
			
			int counter=0;
			
			for (int i = 0; i < frontCap.size(); i++) {
				Polygon3D pol = (Polygon3D) frontCap.elementAt(i);
				//allPolygons[counter]=pol;
				vAllpolygons.add(pol);
				counter++;
			}
			
			for (int i = 0; i < faces.size(); i++) {
				Polygon3D pol = (Polygon3D) faces.elementAt(i);
				vAllpolygons.add(pol);
				//allPolygons[counter]=pol;
				counter++;
			}
			
			for (int i = 0; i < backCap.size(); i++) {
				Polygon3D pol = (Polygon3D) backCap.elementAt(i);
				vAllpolygons.add(pol);
				//allPolygons[counter]=pol;
				counter++;
			}
			
						
			allPolygons=new Polygon3D[vAllpolygons.size()];
			
			for (int i = 0; i < vAllpolygons.size(); i++) {
				allPolygons[i]= (Polygon3D) vAllpolygons.elementAt(i);
			}
			
		}

		public Polygon3D[] getAllPolygons() {
			return allPolygons;
		}

		public void setAllPolygons(Polygon3D[] allPolygons) {
			this.allPolygons = allPolygons;
		}

	}
	
	public class ShadowTriangle extends LineData {
		
		public ShadowTriangle(LineData ld) {
			
			
			for(int i=0;i<ld.size();i++){
				
	
				
				addIndex(ld.getIndex(i));

				
			}
		}

		public void setAdjacentTriangles(Vector vAdjacentTriangles) {
			
			adjacentTriangles=new int[vAdjacentTriangles.size()];
			
			for (int i = 0; i < vAdjacentTriangles.size(); i++) {
				adjacentTriangles[i]= ((Integer) vAdjacentTriangles.elementAt(i)).intValue();
			}
			
		}

		public int[] getAdjacentTriangles() {
			return adjacentTriangles;
		}



		int[] adjacentTriangles=null;
		
	}

}
