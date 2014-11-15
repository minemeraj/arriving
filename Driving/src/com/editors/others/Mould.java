package com.editors.others;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Savepoint;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.main.Renderer3D;

public class Mould extends JFrame implements ActionListener{
	
	JButton saveHorizontalRotation=null;
	JButton cancelRotator=null;
	JPanel rotator=null;
	
	JTextField parallelsNumber=null;
	JTextField meridianNumbers=null;
	

	JTabbedPane tabCenter=null;
	
	private JButton loadProfileImage=null;
	private JButton emptyProfiles=null;

	
	int N_PARALLELS=5;
	int N_MERIDIANS=10;
	
	double pi=Math.PI;
	
	JFileChooser fc = new JFileChooser();
	private File currentDirectory=null;
	
	Profile rotationProfile=null;

	private JPanel readPoints;
	private JButton savePoints; 
	private JButton cancelPoints;
	private JButton loadPointsImage;

	BufferedImage readPointsImage=null;
	private JTextField readPointscolor;
	private JButton rpColorChooser;
	private JTextField rotationColor;
	private JButton rotColorChooser;
	private Color lineColor=Color.RED;
	private JButton loadProfileData;
	private JButton saveVerticalRotationPoints;
	private JCheckBox parallelsOnlyData; 
	
	
	public static void main(String[] args) {
		
		Mould mould=new Mould();
	}
	
	public Mould() {
		
		setTitle("Mould");	
		
		setSize(300,370);
		setLocation(20,20);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		tabCenter=new JTabbedPane();
		add(tabCenter);
		
		//rotator
		rotator=new JPanel();
		rotator.setLayout(null);
		tabCenter.add("Rotator",rotator);
		int r=10;

		JLabel jlb=new JLabel("Num Parallels:");
		jlb.setBounds(10,r,100,20);
		rotator.add(jlb);

		parallelsNumber=new JTextField();
		parallelsNumber.setBounds(120,r,50,20);
		parallelsNumber.setText(""+N_PARALLELS);
		rotator.add(parallelsNumber);
		parallelsOnlyData = new JCheckBox();
		parallelsOnlyData.setBounds(180,r,30,20);
		parallelsOnlyData.addItemListener(
				
		  new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				
				if(parallelsOnlyData.isSelected())
					parallelsNumber.setEnabled(false);
				else
					parallelsNumber.setEnabled(true);
				
			}
		}
				
		);
		rotator.add(parallelsOnlyData);
		
		jlb=new JLabel("Only data");
		jlb.setBounds(210,r,80,20);
		rotator.add(jlb);


		r+=30;

		jlb=new JLabel("Num Meridians:");
		jlb.setBounds(10,r,100,20);
		rotator.add(jlb);

		meridianNumbers=new JTextField();
		meridianNumbers.setBounds(120,r,50,20);
		meridianNumbers.setText(""+N_MERIDIANS);
		rotator.add(meridianNumbers);

		r+=30;
		
		
		loadProfileImage=new JButton("Load Profile Image");
		loadProfileImage.setBounds(10,r,180,20);
		loadProfileImage.addActionListener(this);
		rotator.add(loadProfileImage);
				
		r+=30;
		
		loadProfileData=new JButton("Load Profile Data");
		loadProfileData.setBounds(10,r,180,20);
		loadProfileData.addActionListener(this);
		rotator.add(loadProfileData);
				
		r+=30;
		
		
		emptyProfiles=new JButton("Empty Profiles");
		emptyProfiles.setBounds(10,r,120,20);
		emptyProfiles.addActionListener(this);
		rotator.add(emptyProfiles);
		
		r+=30;
		
		rotationColor= new JTextField(10);
		rotationColor.setBounds(10,r,100,20);
		rotationColor.setBackground(lineColor);
		rotator.add(rotationColor);
		
		rotColorChooser=new JButton(">");
		rotColorChooser.addActionListener(this);
		
	
		rotColorChooser.setBounds(120,r,50,20);
		rotator.add(rotColorChooser);
		
						
		r+=40;

		saveHorizontalRotation=new JButton("Save horizontal Mesh");
		saveHorizontalRotation.setBounds(10,r,200,20);
		saveHorizontalRotation.addActionListener(this);
		rotator.add(saveHorizontalRotation);
		
		
		r+=30;
		
		saveVerticalRotationPoints=new JButton("Save vertical Mesh");
		saveVerticalRotationPoints.setBounds(10,r,200,20);
		saveVerticalRotationPoints.addActionListener(this);
		rotator.add(saveVerticalRotationPoints);
		
		r+=30;

		cancelRotator=new JButton("cancel");
		cancelRotator.setBounds(10,r,100,20);
		cancelRotator.addActionListener(this);
		rotator.add(cancelRotator);
		
		
		///read points in image
		
		r=30;		
		
		readPoints=new JPanel();
		readPoints.setLayout(null);
		tabCenter.add("Read points",readPoints);
		
		loadPointsImage=new JButton("Load points image");
		loadPointsImage.setBounds(10,r,180,20);
		loadPointsImage.addActionListener(this);
		readPoints.add(loadPointsImage);
		
		
		r+=30;
		
		readPointscolor = new JTextField(10);
		readPointscolor.setBounds(10,r,100,20);
		readPoints.add(readPointscolor);
		readPointscolor.setBackground(lineColor);
		
		rpColorChooser=new JButton(">");
		rpColorChooser.addActionListener(this);
		
	
		rpColorChooser.setBounds(120,r,50,20);
		readPoints.add(rpColorChooser);
		
		r+=30;
		
		savePoints=new JButton("Save points");
		savePoints.setBounds(10,r,100,20);
		savePoints.addActionListener(this);
		readPoints.add(savePoints);


		cancelPoints=new JButton("cancel");
		cancelPoints.setBounds(120,r,100,20);
		cancelPoints.addActionListener(this);
		readPoints.add(cancelPoints);
		
		setVisible(true);

		
		
	}
	


	@Override
	public void actionPerformed(ActionEvent e) {

		Object o = e.getSource();

		if (o == saveHorizontalRotation) {
			mould_horizontal_rotate();
		} 
		else if (o == saveVerticalRotationPoints) {
			mould_vertical_rotate();
		} 
		else if (o == cancelRotator || o==cancelPoints) {
			exit();
		} else if (o == loadProfileImage)
			loadRotationProfileImage();
		else if (o == loadProfileData)
			loadRotationProfileData();
		 else if (o == emptyProfiles)
			emptyProfiles();
		 else if (o == loadPointsImage)
			 loadPointsImage();
		 else if (o == savePoints)
			 saveReadPoints(readPointsImage,readPoints.getBackground());
		 else if (o == rpColorChooser)
		     chooseColor(readPointscolor);
		 else if (o == rotColorChooser)
		     chooseColor(rotationColor);
		
		
	}



	private void chooseColor(JTextField jtext) {
		
		
	         Color color = JColorChooser.showDialog(this,"Choose line color",jtext.getBackground());
	         
	         if(color!=null){
	        	 
	        	 lineColor=color;
	        	 jtext.setBackground(lineColor);
	         }
		
	}

	private void saveReadPoints(BufferedImage imageOrig,Color lineColor) {
		

			
		
		int h=imageOrig.getHeight();
		int w=imageOrig.getWidth();
		
		Vector data=new Vector();

		//READ IMAGES TO FIND COLOURED POINTS
		for(int i=0;i<w;i++){
			
			for(int j=0;j<h;j++){

				int argbs = imageOrig.getRGB(i, j);
				int alphas=0xff & (argbs>>24);
				int rs = 0xff & (argbs>>16);
				int gs = 0xff & (argbs >>8);
				int bs = 0xff & argbs;

				if(rs==lineColor.getRed() && gs==lineColor.getGreen() && bs==lineColor.getBlue())
				{
					data.add(new Point3D(i,j,0));
                    break;
				} 

			}
		}	
		
	
		
		PolygonMesh pm=new PolygonMesh();
		
		pm.points=new Point3D[data.size()];

		for (int i = 0; i < data.size(); i++) {
			pm.points[i] = (Point3D) data.elementAt(i);
		}
		
		//rescale mesh points
		
		double maxX=0;
		double minX=0;
		
		double maxY=0;
		double minY=0;

		for (int i = 0; i < pm.points.length; i++) { 
			
			if(i==0){
				
				minX=pm.points[i].getX();
				maxX=pm.points[i].getX();
			}
			else if(pm.points[i].getX()>maxX)
				maxX=pm.points[i].getX();
			else if(pm.points[i].getX()<minX)
				minX=pm.points[i].getX();
			
			
			if(i==0){
				
				maxY=pm.points[i].getY();
				minY=pm.points[i].getY();

			}
			else if(pm.points[i].getY()>maxY)
				maxY=pm.points[i].getY();
			else if(pm.points[i].getY()<minY)
				minY=pm.points[i].getY();
			
		}

		
		for (int i = 0; i < pm.points.length; i++) { 
			
			
			double x=pm.points[i].getX();
			double y=pm.points[i].getY();
			
			pm.points[i].x=x-minX;
			pm.points[i].y=y-minY;

		}

		System.out.println("Found "+ pm.points.length+" points in image");
		saveMesh(pm);
		
		
	
		
	}

	private void mould_horizontal_rotate() {

		if(rotationProfile==null)
			return;

		if(meridianNumbers.getText().equals(""))
			return;

		try{

			N_MERIDIANS=Integer.parseInt(meridianNumbers.getText());

		}
		catch (Exception e) {
			return;
		}
		
		double teta=(2*pi)/(N_MERIDIANS);
		
		PolygonMesh pm=new PolygonMesh();
		
		if(parallelsOnlyData.isSelected()){
			
	
			pm.points=new Point3D[N_PARALLELS*N_MERIDIANS];
			
			N_PARALLELS=rotationProfile.points.length;

			for(int i=0;i<N_PARALLELS;i++){

				double radius=rotationProfile.points[i].y;

				for (int j = 0; j <N_MERIDIANS; j++) {


					double x= rotationProfile.points[i].x;
					double y= (radius*Math.sin(j*teta));
					double z= (radius*Math.cos(j*teta));

					pm.points[f(i,j,N_PARALLELS,N_MERIDIANS)]=
							new Point3D(x,y,z);

				}


			}
			
		}else{
			
			if(parallelsNumber.getText().equals(""))
				return;

			try{

				N_PARALLELS=Integer.parseInt(parallelsNumber.getText()); 


			}
			catch (Exception e) {
				return;
			}

			double dx=rotationProfile.lenX/(N_PARALLELS-1);

			

			pm.points=new Point3D[N_PARALLELS*N_MERIDIANS];

			for(int i=0;i<N_PARALLELS;i++){

				double radius=rotationProfile.foundYApproximation(dx*i);

				for (int j = 0; j <N_MERIDIANS; j++) {


					double x= (dx*i);
					double y= (radius*Math.sin(j*teta));
					double z= (radius*Math.cos(j*teta));

					pm.points[f(i,j,N_PARALLELS,N_MERIDIANS)]=
							new Point3D(x,y,z);

				}


			}
		}




		LineData lowerBase=new LineData();
		LineData upperBase=new LineData();

		for (int j = 0; j <N_MERIDIANS; j++) {

			upperBase.addIndex(f(0,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS));

			upperBase.setData(""+Renderer3D.getFace(upperBase,pm.points));

		}	

		for (int j = N_MERIDIANS-1; j >=0; j--) {

			lowerBase.addIndex(f(N_PARALLELS-1,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS));
			
			lowerBase.setData(""+Renderer3D.getFace(lowerBase,pm.points));
		}

		pm.addPolygonData(upperBase);

		for(int i=0;i<N_PARALLELS-1;i++){




			for (int j = 0; j <N_MERIDIANS; j++) {


				LineData ld=new LineData();


				ld.addIndex(f(i,j,N_PARALLELS,N_MERIDIANS));
				ld.addIndex(f(i+1,j,N_PARALLELS,N_MERIDIANS));
				ld.addIndex(f(i+1,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS));
				ld.addIndex(f(i,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS));
				
				ld.setData(""+Renderer3D.getFace(ld,pm.points));

				pm.addPolygonData(ld);

			}


		}

		pm.addPolygonData(lowerBase);

		saveMesh(pm);

	}

	




	private void mould_vertical_rotate() {

		if(rotationProfile==null)
			return;

		if(meridianNumbers.getText().equals(""))
			return;

		try{

			N_MERIDIANS=Integer.parseInt(meridianNumbers.getText());

		}	catch (Exception e) {
			return;
		}
		
		double teta=(2*pi)/(N_MERIDIANS);
		
		PolygonMesh pm=new PolygonMesh();

		if(parallelsOnlyData.isSelected()){
			
			N_PARALLELS=rotationProfile.points.length;


			pm.points=new Point3D[N_PARALLELS*N_MERIDIANS];

			for(int i=0;i<N_PARALLELS;i++){

				double radius=rotationProfile.points[i].y;

				for (int j = 0; j <N_MERIDIANS; j++) {


					double x= (radius*Math.cos(j*teta));
					double y= (radius*Math.sin(j*teta));
					double z= rotationProfile.points[i].x;

					pm.points[f(i,j,N_PARALLELS,N_MERIDIANS)]=
							new Point3D(x,y,z);

				}


			}

			
		}else{
			
			if(parallelsNumber.getText().equals(""))
				return;

			try{

				N_PARALLELS=Integer.parseInt(parallelsNumber.getText()); 

			}
			catch (Exception e) {
				return;
			}


			double dz=rotationProfile.lenX/(N_PARALLELS-1);


			pm.points=new Point3D[N_PARALLELS*N_MERIDIANS];

			for(int i=0;i<N_PARALLELS;i++){

				double radius=rotationProfile.foundYApproximation(dz*i);

				for (int j = 0; j <N_MERIDIANS; j++) {



					double x= (radius*Math.cos(j*teta));
					double y= (radius*Math.sin(j*teta));
					double z= (dz*i);

					pm.points[f(i,j,N_PARALLELS,N_MERIDIANS)]=
							new Point3D(x,y,z);

				}


			}

		}




		LineData lowerBase=new LineData();
		LineData upperBase=new LineData();

		for (int j = 0; j <N_MERIDIANS; j++) {

			upperBase.addIndex(f(N_PARALLELS-1,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS));

			upperBase.setData(""+Renderer3D.getFace(upperBase,pm.points));

		}	

		for (int j = N_MERIDIANS-1; j >=0; j--) {

			lowerBase.addIndex(f(0,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS));
			
			lowerBase.setData(""+Renderer3D.getFace(lowerBase,pm.points));
		}

		pm.addPolygonData(upperBase);

		for(int i=0;i<N_PARALLELS-1;i++){




			for (int j = 0; j <N_MERIDIANS; j++) {


				LineData ld=new LineData();


				ld.addIndex(f(i,j,N_PARALLELS,N_MERIDIANS));
				ld.addIndex(f(i,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS));
				ld.addIndex(f(i+1,(j+1)%N_MERIDIANS,N_PARALLELS,N_MERIDIANS));
				ld.addIndex(f(i+1,j,N_PARALLELS,N_MERIDIANS));
				
				ld.setData(""+Renderer3D.getFace(ld,pm.points));

				pm.addPolygonData(ld);

			}


		}

		pm.addPolygonData(lowerBase);

		saveMesh(pm);

	}
	
	public int f(int i,int j,int nx,int ny){
		
		return i+j*nx;
	}
	
	private void saveMesh(PolygonMesh pm) {

		try{

					
			Vector vpoints=pm.getPointsAsVector();
			Vector vlines=pm.getPolygonData();

			fc=new JFileChooser();
			fc.setDialogType(JFileChooser.SAVE_DIALOG);
			fc.setDialogTitle("Save Mesh file");
			if(currentDirectory!=null)
				fc.setCurrentDirectory(currentDirectory);
			int returnVal = fc.showOpenDialog(null);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				currentDirectory=fc.getCurrentDirectory();
				saveMesh(file,vpoints,vlines);

			} 

		}catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void saveMesh(File file,Vector vpoints,Vector vlines) {



		PrintWriter pr;
		try {
			pr = new PrintWriter(new FileOutputStream(file));
			
			for(int i=0;i<vpoints.size();i++){

				Point3D p=(Point3D) vpoints.elementAt(i);
				pr.print("v=");
				pr.print(decomposePoint(p));
				if(i<vpoints.size()-1)
					pr.println();
			}	
			
			PolygonMesh mesh=new PolygonMesh(vpoints,vlines);

			decomposeObjVertices(pr,mesh,false);

			for(int i=0;i<vlines.size();i++){

				LineData ld=(LineData) vlines.elementAt(i);

				pr.print("\nf=");
				pr.print(decomposeLineData(ld));

			}	

			pr.close(); 	

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
	
	}
	

	public void decomposeObjVertices(PrintWriter pr,PolygonMesh mesh,boolean isCustom) {
		
		if(isCustom){
			
			for (int i = 0; i < mesh.texturePoints.size(); i++) {
				Point3D pt = (Point3D)  mesh.texturePoints.elementAt(i);
				pr.print("\nvt=");
				pr.print(pt.x+" "+pt.y);
			}
			
			return;
		}
		
		int DX=0;

		
		int deltaX=0;
		int deltaY=0;
		int deltaX2=0;

		double minx=0;
		double miny=0;
		double minz=0;
		
		double maxx=0;
		double maxy=0;
		double maxz=0;
		
		
	      //find maxs
		for(int j=0;j<mesh.points.length;j++){
			
			Point3D point=mesh.points[j];
			
			if(j==0){
				
				minx=point.x;
				miny=point.y;
				minz=point.z;
				
				maxx=point.x;
				maxy=point.y;
				maxz=point.z;
			}
			else{
				
				maxx=(int)Math.max(point.x,maxx);
				maxz=(int)Math.max(point.z,maxz);
				maxy=(int)Math.max(point.y,maxy);
				
				
				minx=(int)Math.min(point.x,minx);
				minz=(int)Math.min(point.z,minz);
				miny=(int)Math.min(point.y,miny);
			}
			
	
		}
		
		deltaX2=(int)(maxx-minx)+1;
		deltaX=(int)(maxz-minz)+1; 
		deltaY=(int)(maxy-miny)+1;

		for(int i=0;i<mesh.points.length;i++){

			Point3D p=mesh.points[i];

			/*public static final int CAR_BACK=0;
			public static final int CAR_TOP=1;
			public static final int CAR_LEFT=2;
			public static final int CAR_RIGHT=3;
			public static final int CAR_FRONT=4;
			public static final int CAR_BOTTOM=5;*/
		
			//back
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+deltaX)+" "+(int)(p.z-minz));					
			//top
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+deltaX)+" "+(int)(p.y-miny+deltaX));
			//left
			pr.print("\nvt=");
			pr.print(DX+(int)(p.z-minz)+" "+(int)(p.y-miny+deltaX));			
			//right
			pr.print("\nvt=");
			pr.print(DX+(int)(-p.z+maxz+deltaX2+deltaX)+" "+(int)(p.y-miny+deltaX));		
			//front
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+deltaX)+" "+(int)(-p.z+maxz+deltaY+deltaX));	
			//bottom
			pr.print("\nvt=");
			pr.print(DX+(int)(p.x-minx+2*deltaX+deltaX2)+" "+(int)(p.y-miny+deltaX));

		}	
		
		
		
	}


	private String decomposePoint(Point3D p) {
		String str="";

		str=p.x+" "+p.y+" "+p.z;

		return str;
	}

	private String decomposeLineData(LineData ld) {

		String str="";
		
		if(ld.data!=null){
			
			str+="["+ld.data+"]";
		    
		}
		for(int j=0;j<ld.size();j++){

			if(j>0)
				str+=" ";
			str+=ld.getIndex(j);
		
			if(ld.data!=null){
					
					int face=Integer.parseInt(ld.data);
		
					str+="/"+(ld.getIndex(j)*6+face);
			}

		}

		return str;
	}

	private void exit() {

		dispose();
        System.exit(0); 
	}

	private void loadRotationProfileImage() {
		
		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load profile");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		
		int returnVal = fc.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			rotationProfile=new Profile(file,rotationColor.getBackground(),true);
		}	
		
	}
	
	
	private void loadPointsImage() {
		
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load points image");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		
		int returnVal = fc.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			try {
				readPointsImage=ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}	
		
	}
	
	
	private void loadRotationProfileData() {
		
		fc=new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load profile data");
		if(currentDirectory!=null)
			fc.setCurrentDirectory(currentDirectory);
		
		int returnVal = fc.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory=fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			rotationProfile=new Profile(file,rotationColor.getBackground(),false);
		}	
		
	}

	private void emptyProfiles() {
		
		rotationProfile=null;
		
	}
	
	public class Profile{

		Point2D.Double [] points=null;
		private Color lineColor;
		

		
		double lenX=0;
		double lenY=0;
		
		
		public Profile(File file,Color lineColor, boolean isImage) {
			
			try {
				this.lineColor = lineColor;
				if(isImage){
					readProfileImage(file);
					
				}	
				else
					readProfileData(file);	
		
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		


		public double foundYApproximation(double len) {
			
			for(int i=0;i<points.length-1;i++){
				
				
				if(points[i].x<=len && points[i+1].x>=len)
				    return points[i].y;
					
			}
			
			return 0;
		}
		
		private void readProfileData(File file) throws Exception {
			
		
				BufferedReader br=new BufferedReader(new FileReader(file));
	
				String line=null;
				
				Vector data=new Vector();
				
				while((line=br.readLine())!=null){
					
					line=line.trim();
					String[] vals=line.split(" ");
					
					double x=java.lang.Double.parseDouble(vals[0]);
					double y=java.lang.Double.parseDouble(vals[1]);
					
					Point2D.Double p=new Point2D.Double(x,y);
					
					data.add(p);
				}
				
				
				br.close();
				
				points=new Point2D.Double[data.size()];

				for (int i = 0; i < data.size(); i++) {
					points[i] = (Point2D.Double) data.elementAt(i);
				}
				
				
				scaleData(-1);
			
		}
		
		

		private void readProfileImage(File fileOrig) throws IOException {
			
			BufferedImage imageOrig=ImageIO.read(fileOrig);
				
			
			int h=imageOrig.getHeight();
			int w=imageOrig.getWidth();
			
			Vector data=new Vector();
			
			int tollerance=5;

			//READ PROFILE
			for(int i=0;i<w;i++){
				
				for(int j=0;j<h;j++){

					int argbs = imageOrig.getRGB(i, j);
					int alphas=0xff & (argbs>>24);
					int rs = 0xff & (argbs>>16);
					int gs = 0xff & (argbs >>8);
					int bs = 0xff & argbs;

					if(		Math.abs(rs-lineColor.getRed())<tollerance &&
							Math.abs(gs-lineColor.getGreen())<tollerance &&
							Math.abs(bs-lineColor.getBlue())<tollerance)
					{
						data.add(new Point2D.Double(i,j));
	                    break;
					} 

				}
			}		
			
			points=new Point2D.Double[data.size()];

			for (int i = 0; i < data.size(); i++) {
				points[i] = (Point2D.Double) data.elementAt(i);
			}
			
		
			scaleData(h);
			
		}
		
		private void scaleData(int h) {
			
			double maxX=0;
			double minX=0;
			
			double maxY=0;
			double minY=0;

			for (int i = 0; i < points.length; i++) { 
				
				if(i==0){
					
					minX=points[i].getX();
					maxX=points[i].getX();
				}
				else if(points[i].getX()>maxX)
					maxX=points[i].getX();
				else if(points[i].getX()<minX)
					minX=points[i].getX();
				
				
				if(i==0){
					
					maxY=points[i].getY();
					minY=points[i].getY();

				}
				else if(points[i].getY()>maxY)
					maxY=points[i].getY();
				else if(points[i].getY()<minY)
					minY=points[i].getY();
				
			}
			
			lenX=maxX-minX; 
			lenY=maxY-minY;
			
			if(h>0){
			
				for (int i = 0; i < points.length; i++) { 
					
					
					double x=points[i].getX();
					double y=points[i].getY();
					
					points[i].setLocation((x-minX),(h-y));
				}
			
			}
			
		}
		
	}
	
}
