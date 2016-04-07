package com.editors.object;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.LineData;
import com.Point3D;
import com.PolygonMesh;
import com.editors.DoubleTextField;

public class ObjectEditorTemplatePanel  extends JDialog implements ActionListener{
	
	private final int WIDTH=300;
	private final int BOTTOM_HEIGHT=100;
	private final int HEIGHT=200;
	private DoubleTextField box_side=null;
	private DoubleTextField box_num_x=null;
	private DoubleTextField box_num_y=null;
	private DoubleTextField box_num_z=null;
	private DoubleTextField prism_radius;
	private DoubleTextField prism_num;
	private DoubleTextField sphere_radius;
	private DoubleTextField sphere_parallels;
	
	
	private int TEMPLATE_TYPE=-1;
	private int TEMPLATE_TYPE_BOX=0;
	private int TEMPLATE_TYPE_PRISM=1;
	private int TEMPLATE_TYPE_SPHERE=2; 
	private int TEMPLATE_TYPE_PLANE=3;
	
	private JButton generate=null;
	private JButton delete=null;
	private JTabbedPane jtb;
	private DoubleTextField prism_height;
	private DoubleTextField prism_num_z;
	private DoubleTextField sphere_meridians;
	
	private DoubleTextField plane_side_x;
	private DoubleTextField plane_num_x;
	private DoubleTextField plane_num_y;
	private DoubleTextField plane_side_y;
	private DoubleTextField plane_z_value;
	
	public ObjectEditorTemplatePanel(){

		TEMPLATE_TYPE=-1;
		setSize(WIDTH,HEIGHT+BOTTOM_HEIGHT);
		setLayout(null);
		setModal(true);
		setTitle("Choose template");
		
		jtb=new JTabbedPane();
		jtb.setBounds(0, 0, WIDTH, HEIGHT);
        add(jtb); 
        jtb.add("box",getBoxPanel());
        jtb.add("prism",getPrismPanel());
        jtb.add("sphere",getSpherePanel());
        jtb.add("plane",getPlanePanel());
        
        generate=new JButton("generate template");
        generate.setBounds(10,HEIGHT+10,150,20);
        generate.addActionListener(this);
        add(generate);
        delete=new JButton("Cancel");
        delete.setBounds(180,HEIGHT+10,100,20);
        delete.addActionListener(this);
        add(delete);

		setVisible(true);

	}
	
	private Component getPlanePanel() {
		
		JPanel plane=new JPanel(); 
		plane.setLayout(null);
		
		int r=10;
		int column=100;
		
		JLabel jlb=new JLabel("Side X");
		jlb.setBounds(5, r, 100, 20);
		plane.add(jlb);

		plane_side_x=new DoubleTextField();
		plane_side_x.setBounds(column, r, 100, 20);
		plane.add(plane_side_x);

		r+=30;
		
		jlb=new JLabel("Side Y");
		jlb.setBounds(5, r, 100, 20);
		plane.add(jlb);

		plane_side_y=new DoubleTextField();
		plane_side_y.setBounds(column, r, 100, 20);
		plane.add(plane_side_y);
		
		r+=30;

		jlb=new JLabel("Num X points");
		jlb.setBounds(5, r, 100, 20);
		plane.add(jlb);
		plane_num_x=new DoubleTextField();
		plane_num_x.setBounds(column, r, 100, 20);
		plane.add(plane_num_x);

		r+=30;
		
		jlb=new JLabel("Num Y points");
		jlb.setBounds(5, r, 100, 20);
		plane.add(jlb);
		plane_num_y=new DoubleTextField();
		plane_num_y.setBounds(column, r, 100, 20);
		plane.add(plane_num_y);
		
		r+=30;
		
		jlb=new JLabel("Z value");
		jlb.setBounds(5, r, 100, 20);
		plane.add(jlb);

		plane_z_value=new DoubleTextField();
		plane_z_value.setBounds(column, r, 100, 20);
		plane.add(plane_z_value);
		
		r+=30;
		
		return plane;
	}

	private Component getSpherePanel() {
		
		JPanel sphere=new JPanel();
		sphere.setLayout(null);
		
		int r=10;
		int column=100;

		JLabel jlb=new JLabel("Radius");
		jlb.setBounds(5, r, 100, 20);
		sphere.add(jlb);

		sphere_radius=new DoubleTextField();
		sphere_radius.setBounds(column, r, 100, 20);
		sphere.add(sphere_radius);

		r+=30;

		jlb=new JLabel("N Parallels");
		jlb.setBounds(5, r, 100, 20);
		sphere.add(jlb);
		sphere_parallels=new DoubleTextField();
		sphere_parallels.setBounds(column, r, 100, 20);
		sphere.add(sphere_parallels);
		
		r+=30;

		jlb=new JLabel("N Meridians");
		jlb.setBounds(5, r, 100, 20);
		sphere.add(jlb);
		sphere_meridians=new DoubleTextField();
		sphere_meridians.setBounds(column, r, 100, 20);
		sphere.add(sphere_meridians);

		
		
		return sphere;
		
	}

	private Component getPrismPanel() {
		
		JPanel prism=new JPanel();
		prism.setLayout(null);
		
		int r=10;
		int column=100;

		JLabel jlb=new JLabel("Radius");
		jlb.setBounds(5, r, 100, 20);
		prism.add(jlb);

		prism_radius=new DoubleTextField();
		prism_radius.setBounds(column, r, 100, 20);
		prism.add(prism_radius);
		
		r+=30;

		jlb=new JLabel("Height");
		jlb.setBounds(5, r, 100, 20);
		prism.add(jlb);
		prism_height=new DoubleTextField();
		prism_height.setBounds(column, r, 100, 20);
		prism.add(prism_height);

		r+=30;

		jlb=new JLabel("N sides");
		jlb.setBounds(5, r, 100, 20);
		prism.add(jlb);
		prism_num=new DoubleTextField();
		prism_num.setBounds(column, r, 100, 20);
		prism.add(prism_num);

		r+=30;

		jlb=new JLabel("N Z points");
		jlb.setBounds(5, r, 100, 20);
		prism.add(jlb);
		prism_num_z=new DoubleTextField();
		prism_num_z.setBounds(column, r, 100, 20);
		prism.add(prism_num_z);
		
		return prism;
		
	}

	private Component getBoxPanel() {
		
		JPanel box=new JPanel();
		box.setLayout(null);
		
		int r=10;
		int column=100;

		JLabel jlb=new JLabel("Side");
		jlb.setBounds(5, r, 100, 20);
		box.add(jlb);

		box_side=new DoubleTextField();
		box_side.setBounds(column, r, 100, 20);
		box.add(box_side);

		r+=30;

		jlb=new JLabel("Num X points");
		jlb.setBounds(5, r, 100, 20);
		box.add(jlb);
		box_num_x=new DoubleTextField();
		box_num_x.setBounds(column, r, 100, 20);
		box.add(box_num_x);

		r+=30;
		jlb=new JLabel("Num Y points");
		jlb.setBounds(5, r, 100, 20);
		box.add(jlb);
		box_num_y=new DoubleTextField();
		box_num_y.setBounds(column, r, 100, 20);
		box.add(box_num_y);

		r+=30;
		jlb=new JLabel("Num Z points" );
		jlb.setBounds(5, r, 100, 20);
		box.add(jlb);
		box_num_z=new DoubleTextField();
		box_num_z.setBounds(column, r, 100, 20);
		box.add(box_num_z);
		
		return box;
		
	}

	public PolygonMesh getTemplate(){
		
		
		if(TEMPLATE_TYPE==-1) 
			return null;
		
	
		
		PolygonMesh pm=new PolygonMesh();
		
		if(TEMPLATE_TYPE==TEMPLATE_TYPE_BOX){

			String sside=box_side.getText();
			String snumx=box_num_x.getText();
			String snumy=box_num_y.getText();
			String snumz=box_num_z.getText();

			if(sside==null || sside.length()==0 || snumx==null || snumx.length()==0 
					|| snumy==null || snumy.length()==0
					|| snumz==null || snumz.length()==0
			)
				return null;

			double side=box_side.getvalue();
			int numx=(int) box_num_x.getvalue();
			int numy=(int)box_num_y.getvalue();
			int numz=(int)box_num_z.getvalue();

			int num=2*((numx-1)+(numy-1));
			int base=numx*numy;
			int sideTot=num*(numz-2);
			int tot=base*2+sideTot;
			pm.points=new Point3D[tot];
			
			if(numz<2){
				
				JOptionPane.showMessageDialog(this, "You nedd at least 2 Z points !","Error for box",JOptionPane.ERROR_MESSAGE);
				return null;
			}

			double dx=side*1.0/(numx-1);
			double dy=side*1.0/(numy-1);
			double dz=side*1.0/(numz-1);

			for(int i=0;i<numx;i++)
				for(int j=0;j<numy;j++)
				{

                    //upper base
					pm.points[i+j*numx]=new Point3D(
							i*dx,j*dy,0	
					);
					//lower base
					pm.points[i+j*numx+base+sideTot]=new Point3D(
							i*dx,j*dy,dz*(numz-1)	
					);
					
					
					
					
					
				}
	        //sides
			for(int i=0;i<numx;i++)
				for(int j=0;j<numy;j++)
				{

					if(i!=0 && j!=0 && j!=numy-1 && i!=numx-1)
						continue;
						
					for (int k = 1; k < numz-1; k++) {


						int ii=calculateBoxBorder(i,j,numx,num);
					    //System.out.println(i+" "+j+" "+k+" "+ii);
						
						pm.points[ii+base+num*(k-1)]=new Point3D(
								i*dx,j*dy,dz*k);
					}



				}
			

			//links bases and sides			
			//needs numz>2 !
			if(numz>2){
				for(int i=0;i<numx-1;i++)
				{

					for(int j=0;j<numy;j=j+numy-1){		



						int ii1=calculateBoxBorder(i,j, numx, num);
						int ii2=calculateBoxBorder(i+1,j, numx, num);

						//lower base
						int i1=i+j*numx;
						int i2=i+1+j*numx;

						int i3=ii2+base+num*(i-1);
						int i4=ii1+base+num*(i-1);


						if(j==0)
							pm.addPolygonData(new LineData(i1,i2,i3,i4));							
						else
							pm.addPolygonData(new LineData(i2,i1,i4,i3));


						//upper base
						i1=i+j*numx+base+num*(numz-2);
						i2=i+1+j*numx+base+num*(numz-2);

						i3=ii2+base+num*(numz-2-1);
						i4=ii1+base+num*(numz-2-1);

						if(j==0)
							pm.addPolygonData(new LineData(i2,i1,i4,i3));
						else
							pm.addPolygonData(new LineData(i1,i2,i3,i4));

					}

				}

				for(int j=0;j<numy-1;j++){
					for(int i=0;i<numx;i=i+numx-1)
					{
						int ii1=calculateBoxBorder(i,j, numx, num);
						int ii2=calculateBoxBorder(i,j+1, numx, num);

						//lower base
						int i1=i+j*numx;
						int i2=i+(1+j)*numx;

						int i3=ii2+base+num*(1-1);
						int i4=ii1+base+num*(1-1);

						if(i==0)
							pm.addPolygonData(new LineData(i1,i4,i3,i2));											
						else
							pm.addPolygonData(new LineData(i1,i2,i3,i4));	
								

						//upper base
						i1=i+j*numx+base+num*(numz-2);
						i2=i+(1+j)*numx+base+num*(numz-2);

						i3=ii2+base+num*(numz-2-1);
						i4=ii1+base+num*(numz-2-1);

						if(i==0)
							pm.addPolygonData(new LineData(i1,i2,i3,i4));						
						else
							pm.addPolygonData(new LineData(i1,i4,i3,i2));
							

					}

				}
			}
			else {
				
				for(int i=0;i<numx-1;i++)
				{

					for(int j=0;j<numy;j=j+numy-1){		

						//lower base
						int i1=i+j*numx;
						int i2=i+1+j*numx;

						//upper base
						int i3=i+1+j*numx+base+num*(numz-2);
						int i4=i+j*numx+base+num*(numz-2);
                       
						if(j==0)
							pm.addPolygonData(new LineData(i1,i2,i3,i4));
						else
							pm.addPolygonData(new LineData(i2,i1,i4,i3));
					}

				}

				for(int j=0;j<numy-1;j++){
					for(int i=0;i<numx;i=i+numx-1)
					{

						//lower base
						int i1=i+j*numx;
						int i2=i+(1+j)*numx;


						//upper base
						int i4=i+j*numx+base+num*(numz-2);
						int i3=i+(1+j)*numx+base+num*(numz-2);

						if(i==0)
							pm.addPolygonData(new LineData(i1,i4,i3,i2));						
						else
							pm.addPolygonData(new LineData(i1,i2,i3,i4));	
							
					}

				}
				
				
			}
			//link sides points
			for (int i = 0; i < num; i++) {

	
				for (int k = 1; k < numz-2; k++) {
				
					pm.addPolygonData(new LineData(i+base+num*(k-1),
							(i+1)%num+base+num*(k-1),
							(i+1)%num+base+num*(k),
							i+base+num*(k))

					);


				}
			}
			//build bases nets
			for(int i=0;i<numx-1;i++)
				for(int j=0;j<numy-1;j++){

	
					//lower base
					int pl1=i+numx*j;
					int pl2=i+numx*(j+1);
					int pl3=i+1+numx*(j+1);
					int pl4=i+1+numx*j;
										
					pm.addPolygonData(new LineData(pl1, pl2, pl3, pl4));
					
					
					//upper base
					int pu1=i+numx*j+base+num*(numz-2);
					int pu2=i+1+numx*j+base+num*(numz-2);
					int pu3=i+1+numx*(j+1)+base+num*(numz-2);
					int pu4=i+numx*(j+1)+base+num*(numz-2);
					
										
					pm.addPolygonData(new LineData(pu1, pu2, pu3, pu4));
					
				}


		}
		else if(TEMPLATE_TYPE==TEMPLATE_TYPE_PRISM){

			String sside=prism_radius.getText();
			String snum=prism_num.getText();
			String sheight=prism_height.getText();
			String snumz=prism_num_z.getText();

			if(sside==null || sside.length()==0 || snum==null 
					|| snum.length()==0 || sheight==null || sheight.length()==0
					|| snumz==null || snumz.length()==0
			)
				return null;

			double radius=prism_radius.getvalue();
			int num=(int) prism_num.getvalue();
			int numz=(int) prism_num_z.getvalue();
			double height=prism_height.getvalue();

			double angle=Math.PI*2/num;
		
			double dz=height/(numz-1);

			int numTot=num*numz;
			pm.points=new Point3D[numTot];
			
			if(numz<2){
				
				JOptionPane.showMessageDialog(this, "You need at least 2 Z points !","Error for prism",JOptionPane.ERROR_MESSAGE);
				return null;
			}

			for (int i = 0; i < num; i++) {

				double x=radius*Math.cos(angle*i);
				double y=radius*Math.sin(angle*i);

				for (int k = 0; k < numz; k++) {

					double z=dz*k;


					pm.points[k*num+i]=new Point3D(x,y,z);
				}
			}

			
			LineData upperBase=new LineData();
			LineData lowerBase=new LineData();
			for (int i = 0; i < num; i++) {

				lowerBase.addIndex(num-i-1);
				upperBase.addIndex(i+(numz-1)*num);

				for (int k = 0; k < numz-1; k++) {

					pm.addPolygonData(new LineData(k*num+i,
							k*num+(i+1)%num,
							(k+1)*num+(i+1)%num,							
							(k+1)*num+i


					));


				}
			}
			pm.addPolygonData(upperBase);
			pm.addPolygonData(lowerBase);


		}
		else if(TEMPLATE_TYPE==TEMPLATE_TYPE_SPHERE){

			String sradius=sphere_radius.getText();
			String sparallels=sphere_parallels.getText();
			String smeridians=sphere_parallels.getText();

			if(sradius==null || sradius.length()==0 || sparallels==null || sparallels.length()==0 
					|| smeridians==null || smeridians.length()==0 	
			)
				return null;

			double radius=sphere_radius.getvalue();
			int parallels=(int) sphere_parallels.getvalue();
			int meridians=(int) sphere_meridians.getvalue();
			double fi=Math.PI*2/(meridians);
			double teta=Math.PI/(parallels-1);

			int tot=(parallels-2)*meridians+2;
			pm.points=new Point3D[tot];	
			
			if(parallels<=1 || meridians<=1){
				
				JOptionPane.showMessageDialog(this, "You nedd at least 2 parallels and 2 meridians !","Error for sphere",JOptionPane.ERROR_MESSAGE);
				return null;
			}

			pm.points[0]=new Point3D(0,0,radius);			
			for (int i = 0; i < meridians; i++) {



				for (int k = 1; k < parallels-1; k++) {

					double x=radius*Math.cos(fi*i)*Math.sin(teta*k);
					double y=radius*Math.sin(fi*i)*Math.sin(teta*k);
					double z=radius*Math.cos(teta*k);

					pm.points[1+i+(k-1)*meridians]=new Point3D(x,y,z);
				}
			}
			pm.points[tot-1]=new Point3D(0,0,-radius);


			for (int i = 0; i < meridians; i++) {

				pm.addPolygonData(new LineData(0,
						1+i,
						1+(1+i)%meridians
				));
				
				pm.addPolygonData(new LineData(1+(1+i)%meridians+(parallels-2-1)*meridians,
						1+i+(parallels-2-1)*meridians,
						tot-1
						
				));
				
			

				for (int k = 1; k < parallels-2; k++) {



					pm.addPolygonData(new LineData(1+i+(k-1)*meridians,
							1+i+(k)*meridians,
							1+(1+i)%meridians+(k)*meridians,
							1+(1+i)%meridians+(k-1)*meridians
							


					));
				}
			}


		}
		else if(TEMPLATE_TYPE==TEMPLATE_TYPE_PLANE){
			
		
	
			String ssidex=plane_side_x.getText();
			String ssidey=plane_side_y.getText();
			
			String snumx=plane_num_x.getText();
			String snumy=plane_num_y.getText();


			String sz_value=plane_z_value.getText();
			
			if(ssidex==null || ssidex.length()==0 
					|| ssidey==null || ssidey.length()==0
					|| snumx==null || snumx.length()==0 
					|| snumy==null || snumy.length()==0
					|| sz_value.length()==0
			)
				return null;
			
			double sidex=plane_side_x.getvalue();
			double sidey=plane_side_y.getvalue();
			
			double z_value=plane_z_value.getvalue();
			
			int numx=(int) plane_num_x.getvalue();
			int numy=(int) plane_num_y.getvalue();
			
			double dx=sidex*1.0/(numx-1);
			double dy=sidey*1.0/(numy-1);
			
			int tot=numx*numy;
			pm.points=new Point3D[tot];
		

			for(int i=0;i<numx;i++)
				for(int j=0;j<numy;j++)
				{

                    //upper base
					pm.points[i+j*numx]=new Point3D(
							i*dx,j*dy,z_value	
					);

				}
			
			for(int i=0;i<numx-1;i++)
				for(int j=0;j<numy-1;j++){

	
					//lower base
					int pl1=i+numx*j;
					int pl2=i+numx*(j+1);
					int pl3=i+1+numx*(j+1);
					int pl4=i+1+numx*j;
										
					pm.addPolygonData(new LineData(pl1, pl4, pl3, pl2));
					
				}
			
			
		}
		
		return pm;
		
	}
	

	private int calculateBoxBorder(int i, int j, int numx, int num) {
		
		int ii=num-(i+j);
		
		if(i==numx-1 || ( j==0))
			ii=(i+j);
		
		return ii;
	}

	@Override
	public void actionPerformed(ActionEvent e) {


		Object o=e.getSource();

		if(o==generate){
			
			if(jtb.getSelectedIndex()==0)
				TEMPLATE_TYPE=TEMPLATE_TYPE_BOX;
			else if(jtb.getSelectedIndex()==1)
				TEMPLATE_TYPE=TEMPLATE_TYPE_PRISM;
			else if(jtb.getSelectedIndex()==2)
				TEMPLATE_TYPE=TEMPLATE_TYPE_SPHERE;
			else if(jtb.getSelectedIndex()==3)
				TEMPLATE_TYPE=TEMPLATE_TYPE_PLANE;
			
			dispose();	
		}
		else if(o==delete){

			TEMPLATE_TYPE=-1;
			dispose();	
		}

	}

}
