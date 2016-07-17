package com;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class DrawObject implements Cloneable{
	
	public static final boolean IS_3D=true;
	
	private double x;
	private double y;
	private double z;
	
	private double dx;
	private double dy;
	private double dz;
	
	private int index=0;
	
	private double rotation_angle=0;
	

	private String hexColor=null;
	private boolean selected=false;
	
	private ArrayList<Polygon3D> polygons=new ArrayList<Polygon3D>();
	private PolygonMesh mesh=new PolygonMesh();


	private int deltaX=0;
	private int deltaY=0;  
	private int deltaX2=0;
	
	private Polygon3D base=null;
	private Polygon3D border=null;

	@Override
	public Object clone()  {
		DrawObject dro=new DrawObject();

		dro.setX(getX());
		dro.setY(getY());
		dro.setZ(getZ());
		dro.setDx(getDx());
		dro.setDy(getDy());
		dro.setDz(getDz());
		dro.setIndex(getIndex());
		dro.setHexColor(getHexColor());
		dro.rotation_angle=rotation_angle;

		//polygons vector!
		for(int i=0;i<polygons.size();i++){
			
			Polygon3D polig=(Polygon3D) polygons.get(i);
			dro.addPolygon(polig.clone());
		}
		dro.setMesh(getMesh().clone());		
		dro.calculateBase();
		
		return dro;
	}
	
	private void addPolygon(Polygon3D poly){
		
		polygons.add(poly);
	}
	
	public ArrayList<Polygon3D> getPolygons() {
		return polygons;
	}

	public void setPolygons(ArrayList<Polygon3D> polygons) {
		this.polygons = polygons;
	}
	
	private static BufferedImage fromImageToBufferedImage(Image image,Color color){
	
    	image=Transparency.makeColorTransparent(image,color);
    	
    	int width=image.getWidth(null);
    	int height=image.getHeight(null);
    	
		BufferedImage bufImage=
			new BufferedImage(width,height,
					BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2 = (Graphics2D) bufImage.getGraphics();
	
		g2.setColor(new Color(255,255,255,0));
		g2.fillRect(0,0,width,height);
	
		g2.drawImage(image,0,0,
				width,height,null);
		
		
		return bufImage;

    }
    
    public static Texture fromImageToTexture(Image image,Color color){
    	
    	BufferedImage bi=fromImageToBufferedImage(image, color);
    	return new Texture(bi);
    }
	
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the dx
	 */
	public double getDx() {
		return dx;
	}
	/**
	 * @param dx the dx to set
	 */
	public void setDx(double dx) {
		this.dx = dx;
	}
	/**
	 * @return the dy
	 */
	public double getDy() {
		return dy;
	}
	/**
	 * @param dy the dy to set
	 */
	public void setDy(double dy) {
		this.dy = dy;
	}
	/**
	 * @return the dz
	 */
	public double getDz() {
		return dz;
	}
	/**
	 * @param dz the dz to set
	 */
	public void setDz(double dz) {
		this.dz = dz;
	}
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	/**
	 * @return the z
	 */
	public double getZ() {
		return z;
	}
	/**
	 * @param z the z to set
	 */
	public void setZ(double z) {
		this.z = z;
	}

	public double getRotation_angle() {
		return rotation_angle;
	}

	public void setRotation_angle(double rotation_angle) {
		this.rotation_angle = rotation_angle;
	}

	public static DrawObject[] cloneObjectsArray(DrawObject[] drawObjects) {
		DrawObject[] newDrawObjects=new DrawObject[drawObjects.length];
		
		for(int i=0;i<drawObjects.length;i++){
			
			newDrawObjects[i] =(DrawObject) drawObjects[i].clone();
			
			
		}
		
		return newDrawObjects;
	}
	
	@Override
	public String toString() {
		
		String rot=""+Math.round(rotation_angle*1000)/1000.0;
		
		String str=getX()+" "+getY()+" "+getZ()+" "+
				+getIndex()+"["+rot+" "+getHexColor()+"]";
		
		return str;
	}


	public String getHexColor() {
		return hexColor;
	}

	public void setHexColor(String hexColor) {
		this.hexColor = hexColor;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public PolygonMesh getMesh() {
		return mesh;
	}

	public void setMesh(PolygonMesh mesh) {
		this.mesh = mesh;
		calculateBorder();
	}
	
	public int getDeltaX() {
		return deltaX;
	}

	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}

	public int getDeltaY() {
		return deltaY;
	}

	public void setDeltaY(int deltaY) {
		this.deltaY = deltaY;
	}

	public int getDeltaX2() {
		return deltaX2;
	}

	public void setDeltaX2(int deltaX2) {
		this.deltaX2 = deltaX2;
	}
	

	public Polygon3D getBorder() {
		return border;
	}

	public void setBorder(Polygon3D border) {
		this.border = border;
	}
	
	public void calculateBase(){

		int[] cx=new int[4];
		int[] cy=new int[4];
		int[] cz=new int[4];

		cx[0]=(int) x;
		cy[0]=(int) y;
		cz[0]=(int) z;
		cx[1]=(int) (x+dx);
		cy[1]=(int) y;
		cz[1]=(int) z;
		cx[2]=(int) (x+dx);
		cy[2]=(int) (y+dy);
		cz[2]=(int) z;
		cx[3]=(int) x;
		cy[3]=(int) (y+dy);
		cz[3]=(int) z;

		base=new Polygon3D(4,cx,cy,cz);


	}
	
	private void calculateBorder() {
		
		
		if(mesh==null || mesh.xpoints==null)
			return;
		
		double maxX=0;
		double minX=0;
		double maxY=0;
		double minY=0;
		
		
		boolean start=true;
		
		for(int i=0;i<mesh.xpoints.length;i++){
			
			
			Point3D point= new Point3D(mesh.xpoints[i],mesh.ypoints[i],mesh.zpoints[i]);
			
			if(point.z-z>1)
				continue;
			
			if(start){
				
				maxX=point.x;
				minX=point.x;
				maxY=point.y;
				minY=point.y;
				
				start=false;
				continue;
			}
			
			maxX=Math.max(point.x,maxX);
			minX=Math.min(point.x,minX);
			maxY=Math.max(point.y,maxY);
			minY=Math.min(point.y,minY);
				

		}

		double dx=maxX-minX;
		double dy=maxY-minY;
		
		
		int[] cx=new int[4];
		int[] cy=new int[4];
		int[] cz=new int[4];

		cx[0]=(int) minX;
		cy[0]=(int) minY;
		cz[0]=(int) z;
		cx[1]=(int) (minX+dx);
		cy[1]=(int) minY;
		cz[1]=(int) z;
		cx[2]=(int) (minX+dx);
		cy[2]=(int) (minY+dy);
		cz[2]=(int) z;
		cx[3]=(int) minX;
		cy[3]=(int) (minY+dy);
		cz[3]=(int) z;

		border=new Polygon3D(4,cx,cy,cz);

	}

	public Polygon3D getBase() {
		return base;
	}

	public void setBase(Polygon3D base) {
		this.base = base;
	}
	

}
