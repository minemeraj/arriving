package com.editors.object;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.RepaintManager;
import javax.swing.event.MenuEvent;

import com.LineData;
import com.Point3D;
import com.Polygon3D;
import com.PolygonMesh;
import com.Texture;
import com.editors.Editor;
import com.main.HelpPanel;

/**
 * @author Piazza Francesco Giovanni ,Tecnes Milano http://www.tecnes.com
 *
 */

class ObjectEditor extends Editor implements ActionListener {

	static int HEIGHT = 700;
	static int WIDTH = 800;

	int RIGHT_BORDER = 330;
	int BOTTOM_BORDER = 100;

	private JMenuBar jmb;
	private JMenu jm_load;

	private AbstractButton jmt_load_mesh;
	private JMenuItem jmt_save_mesh;
	private JMenuItem jmt_save_base_texture;
	private JMenu jm_save;

	private JMenu jm_change;
	private JMenuItem jmt_undo_last;
	private JMenuItem jmt_transform;

	private JMenu jm_edit;
	private JMenuItem jmt_insert_template;
	private JCheckBoxMenuItem jmt_show_normals;
	private JMenu jm_view;
	private JMenuItem jmt_3Dview;
	private JMenuItem jmt_top_view;
	private JMenuItem jmt_left_view;
	private JMenuItem jmt_front_view;

	private JMenuItem hmt_preview;
	private JMenuItem jmt_rescale_selected;
	private JMenuItem jmt_copy_selection;
	JCheckBoxMenuItem jmt_show_shading;
	private JMenu jmt_other;
	private JMenuItem jmt_help;
	private JMenuItem jmt_load_texture;
	private JMenuItem jmt_discharge_texture;

	Texture currentTexture = null;
	private JCheckBoxMenuItem jmt_show_texture;
	private JMenuItem jmt_save_custom_mesh;

	ObjectEditorPanel mainPanel = null;

	public static void main(String[] args) {

		ObjectEditor re = new ObjectEditor();

	}

	private void initialize() {

		mainPanel.initialize();

	}

	public ObjectEditor() {

		setTitle("Object editor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setSize(WIDTH + RIGHT_BORDER, HEIGHT + BOTTOM_BORDER);

		buildMenuBar();

		RepaintManager.setCurrentManager(new RepaintManager() {
			@Override
			public void paintDirtyRegions() {

				super.paintDirtyRegions();
				if (redrawAfterMenu) {
					mainPanel.displayAll();
					redrawAfterMenu = false;
				}
			}

		});

		mainPanel = new ObjectEditorPanel(this);
		mainPanel.setBounds(0, 0, WIDTH + RIGHT_BORDER, HEIGHT + BOTTOM_BORDER);
		add(mainPanel);

		currentDirectory = new File("lib");
		forceReading = true;

		setVisible(true);

		initialize();

	}

	@Override
	public void paint(Graphics arg0) {
		super.paint(arg0);
		mainPanel.displayAll();

	}

	private void buildMenuBar() {
		jmb = new JMenuBar();
		jm_load = new JMenu("Load");
		jm_load.addMenuListener(this);
		jmb.add(jm_load);

		jmt_load_mesh = new JMenuItem("Load Mesh");
		jmt_load_mesh.addActionListener(this);
		jm_load.add(jmt_load_mesh);

		jm_load.addSeparator();

		jmt_load_texture = new JMenuItem("Load texture");
		jmt_load_texture.addActionListener(this);
		jm_load.add(jmt_load_texture);

		jmt_discharge_texture = new JMenuItem("Discharge texture");
		jmt_discharge_texture.addActionListener(this);
		jm_load.add(jmt_discharge_texture);

		jm_save = new JMenu("Save");
		jm_save.addMenuListener(this);

		jmt_save_mesh = new JMenuItem("Save mesh");
		jmt_save_mesh.addActionListener(this);
		jm_save.add(jmt_save_mesh);

		jmt_save_base_texture = new JMenuItem("Save base texture");
		jmt_save_base_texture.addActionListener(this);
		jm_save.add(jmt_save_base_texture);

		jm_save.addSeparator();

		jmt_save_custom_mesh = new JMenuItem("Save custom mesh");
		jmt_save_custom_mesh.addActionListener(this);
		jm_save.add(jmt_save_custom_mesh);

		jmb.add(jm_save);

		jm_change = new JMenu("Change");
		jm_change.addMenuListener(this);
		jmt_undo_last = new JMenuItem("Undo last");
		jmt_undo_last.setEnabled(false);
		jmt_undo_last.addActionListener(this);
		jm_change.add(jmt_undo_last);
		jmt_transform = new JMenuItem("Transform");
		jmt_transform.addActionListener(this);
		jm_change.add(jmt_transform);
		jmt_rescale_selected = new JMenuItem("Rescale Selected");
		jmt_rescale_selected.addActionListener(this);
		jm_change.add(jmt_rescale_selected);

		jmb.add(jm_change);

		jm_edit = new JMenu("Edit");
		jm_edit.addMenuListener(this);
		jmt_insert_template = new JMenuItem("Insert template");
		jm_edit.add(jmt_insert_template);
		jmt_insert_template.addActionListener(this);

		jmt_copy_selection = new JMenuItem("Copy selection");
		jm_edit.add(jmt_copy_selection);
		jmt_copy_selection.addActionListener(this);

		jm_edit.addSeparator();

		jmt_show_shading = new JCheckBoxMenuItem("Show shading");
		jmt_show_shading.setState(false);
		jmt_show_shading.addActionListener(this);
		jm_edit.add(jmt_show_shading);

		jmt_show_normals = new JCheckBoxMenuItem("Show normals");
		jmt_show_normals.setState(false);
		jmt_show_normals.addActionListener(this);
		jm_edit.add(jmt_show_normals);

		jmt_show_texture = new JCheckBoxMenuItem("Show texture");
		jmt_show_texture.setState(false);
		jmt_show_texture.addActionListener(this);
		jm_edit.add(jmt_show_texture);

		jmb.add(jm_edit);

		jm_view = new JMenu("View");
		jm_view.addMenuListener(this);
		jmt_3Dview = new JMenuItem("3D view");
		jm_view.add(jmt_3Dview);
		jmt_3Dview.addActionListener(this);

		jmt_top_view = new JMenuItem("Top view");
		jmt_top_view.addActionListener(this);
		jm_view.add(jmt_top_view);

		jmt_left_view = new JMenuItem("Left view");
		jmt_left_view.addActionListener(this);
		jm_view.add(jmt_left_view);

		jmt_front_view = new JMenuItem("Front view");
		jmt_front_view.addActionListener(this);
		jm_view.add(jmt_front_view);

		jm_view.addSeparator();

		hmt_preview = new JMenuItem("Preview");
		hmt_preview.addActionListener(this);
		jm_view.add(hmt_preview);

		jmb.add(jm_view);

		jmt_other = new JMenu("Other");
		jmt_other.addMenuListener(this);

		jmb.add(jmt_other);

		jmt_help = new JMenuItem("Help");
		jmt_help.addActionListener(this);
		jmt_other.add(jmt_help);

		setJMenuBar(jmb);
	}

	private void help() {

		HelpPanel hp = new HelpPanel(300, 200, this.getX() + 100, this.getY(), HelpPanel.OBJECT_EDITOR_HELP_TEXT, this);

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object o = arg0.getSource();

		if (o == jmt_save_mesh) {
			getMeshDescription();
			saveLines();
		} else if (o == jmt_save_base_texture) {
			saveBaseCubicTexture();
		} else if (o == jmt_save_custom_mesh) {
			saveLines(true);
		} else if (o == jmt_load_mesh) {

			jmt_show_texture.setSelected(false);
			currentTexture = null;
			loadPointsFromFile();
			setMeshDescription();
		} else if (o == jmt_undo_last) {
			undo();
		} else if (o == jmt_transform) {
			transform();
		} else if (o == jmt_rescale_selected) {
			rescaleSelcted();
		} else if (o == jmt_insert_template) {
			getTemplate();
		} else if (o == jmt_copy_selection) {
			copySelection();
		} else if (o == jmt_3Dview) {
			mainPanel.set3DView();
		} else if (o == jmt_top_view) {

			mainPanel.setTopView();
		} else if (o == jmt_left_view) {

			mainPanel.setLeftView();
		} else if (o == jmt_front_view) {

			mainPanel.setFrontView();
		} else if (o == hmt_preview) {

			preview();
		} else if (o == jmt_help) {

			help();

		} else if (o == jmt_load_texture) {

			loadTexture();

		} else if (o == jmt_discharge_texture) {

			dischargeTexture();

		}
	}

	private void getMeshDescription() {

		PolygonMesh mesh = meshes[ACTIVE_PANEL];

		if (mesh != null && mainPanel.getMeshDescription() != null)
			mesh.setDescription(mainPanel.getMeshDescription());

	}

	private void setMeshDescription() {

		PolygonMesh mesh = meshes[ACTIVE_PANEL];
		if (mesh != null)
			mainPanel.setMeshDescription(mesh.getDescription());
	}

	@Override
	public void preview() {

		ObjectEditorPreviewPanel oepp = new ObjectEditorPreviewPanel(this);

	}

	private void dischargeTexture() {

		jmt_show_texture.setSelected(false);
		currentTexture = null;

	}

	private void loadTexture() {

		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.setDialogTitle("Load texture");
		if (currentDirectory != null)
			fc.setCurrentDirectory(currentDirectory);
		if (currentFile != null)
			fc.setSelectedFile(currentFile);

		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory = fc.getCurrentDirectory();
			currentFile = fc.getSelectedFile();
			File file = fc.getSelectedFile();
			loadTexture(file);
			repaint();

		}

	}

	private void loadTexture(File file) {

		try {

			currentTexture = new Texture(ImageIO.read(file));
			jmt_show_texture.setSelected(true);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void getTemplate() {

		ObjectEditorTemplatePanel oetp = new ObjectEditorTemplatePanel();

		PolygonMesh pm = oetp.getTemplate();

		if (pm != null) {

			PolygonMesh mesh = meshes[ACTIVE_PANEL];

			ArrayList<Point3D> vPoints = new ArrayList<Point3D>();
			for (int i = 0; i < pm.xpoints.length; i++) {
				vPoints.add(new Point3D(mesh.xpoints[i], mesh.ypoints[i], mesh.zpoints[i]));
			}
			mesh.setPoints(vPoints);
			mesh.polygonData = pm.polygonData;
			mainPanel.displayAll();
		}

	}

	private void copySelection() {

		prepareUndo();

		PolygonMesh mesh = meshes[ACTIVE_PANEL];

		ObjectEditorCopyPanel oetp = new ObjectEditorCopyPanel(mesh.xpoints, mesh.ypoints, mesh.zpoints,
				mesh.polygonData);

		PolygonMesh pm = oetp.getCopy();

		if (pm != null) {
			ArrayList<Point3D> aPoints = new ArrayList<Point3D>();
			for (int i = 0; i < pm.xpoints.length; i++) {
				aPoints.add(new Point3D(mesh.xpoints[i], mesh.ypoints[i], mesh.zpoints[i]));
			}
			mesh.setPoints(aPoints);
			mesh.polygonData = pm.polygonData;
			mainPanel.displayAll();
		}

	}

	private void transform() {

		ObjectEditorTransformPanel oetp = new ObjectEditorTransformPanel(this);

	}

	private void rescaleSelcted() {

		ObjectEditorRescalePanel oerp = new ObjectEditorRescalePanel(this);

	}

	/*
	 * public void savePolyFormat() {
	 * 
	 * fc = new JFileChooser(); fc.setDialogType(JFileChooser.SAVE_DIALOG);
	 * fc.setDialogTitle("Save other format"); if(currentDirectory!=null)
	 * fc.setCurrentDirectory(currentDirectory); int returnVal =
	 * fc.showOpenDialog(null);
	 * 
	 * if (returnVal == JFileChooser.APPROVE_OPTION) {
	 * currentDirectory=fc.getCurrentDirectory(); File file =
	 * fc.getSelectedFile(); savePolyFormat(file);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * private void savePolyFormat(File file) {
	 * 
	 * PolygonMesh mesh=meshes[ACTIVE_PANEL];
	 * 
	 * PrintWriter pr; try { pr = new PrintWriter(new FileOutputStream(file));
	 * 
	 * for(int i=0;i<mesh.polygonData.size();i++){
	 * 
	 * LineData ld=(LineData) mesh.polygonData.elementAt(i);
	 * 
	 * pr.println(decomposePolyFormat(ld));
	 * 
	 * }
	 * 
	 * pr.close();
	 * 
	 * } catch (FileNotFoundException e) {
	 * 
	 * e.printStackTrace(); } }
	 */

	private void saveBaseCubicTexture() {

		fc = new JFileChooser();
		fc.setDialogType(JFileChooser.SAVE_DIALOG);
		fc.setDialogTitle("Save basic texture");
		if (currentDirectory != null)
			fc.setCurrentDirectory(currentDirectory);
		int returnVal = fc.showOpenDialog(null);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			currentDirectory = fc.getCurrentDirectory();
			File file = fc.getSelectedFile();
			saveBaseCubicTexture(file);

		}

	}

	private void saveBaseCubicTexture(File file) {

		PolygonMesh mesh = meshes[ACTIVE_PANEL];

		Color backgroundColor = Color.green;
		Color back_color = Color.BLUE;
		Color top_color = Color.RED;
		Color bottom_color = Color.MAGENTA;
		Color left_color = Color.YELLOW;
		Color right_color = Color.ORANGE;
		Color front_color = Color.CYAN;

		int IMG_WIDTH = 0;
		int IMG_HEIGHT = 0;

		int deltaX = 0;
		int deltaY = 0;
		int deltaX2 = 0;

		double minx = 0;
		double miny = 0;
		double minz = 0;

		double maxx = 0;
		double maxy = 0;
		double maxz = 0;

		// find maxs
		for (int j = 0; j < mesh.xpoints.length; j++) {

			Point3D point = new Point3D(mesh.xpoints[j], mesh.ypoints[j], mesh.zpoints[j]);

			if (j == 0) {

				minx = point.x;
				miny = point.y;
				minz = point.z;

				maxx = point.x;
				maxy = point.y;
				maxz = point.z;
			} else {

				maxx = (int) Math.max(point.x, maxx);
				maxz = (int) Math.max(point.z, maxz);
				maxy = (int) Math.max(point.y, maxy);

				minx = (int) Math.min(point.x, minx);
				minz = (int) Math.min(point.z, minz);
				miny = (int) Math.min(point.y, miny);
			}

		}

		deltaX2 = (int) (maxx - minx) + 1;
		deltaX = (int) (maxz - minz) + 1;
		deltaY = (int) (maxy - miny) + 1;

		deltaX2 = deltaX2 + deltaX;

		IMG_HEIGHT = deltaY + deltaX + deltaX;
		IMG_WIDTH = deltaX2 + deltaX2;

		BufferedImage buf = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_BYTE_INDEXED);

		try {

			int DX = 0;

			Graphics2D bufGraphics = (Graphics2D) buf.getGraphics();

			bufGraphics.setColor(backgroundColor);
			bufGraphics.fillRect(DX + 0, 0, IMG_WIDTH, IMG_HEIGHT);

			/*
			 * bufGraphics.setColor(front_color);
			 * bufGraphics.fillRect(DX+deltaX,0,deltaX2-deltaX,deltaX);
			 * bufGraphics.setColor(top_color);
			 * bufGraphics.fillRect(DX+deltaX,deltaX,deltaX2-deltaX,deltaY);
			 * bufGraphics.setColor(left_color);
			 * bufGraphics.fillRect(DX+0,deltaX,deltaX,deltaY);
			 * bufGraphics.setColor(right_color);
			 * bufGraphics.fillRect(DX+deltaX2,deltaX,deltaX,deltaY);
			 * bufGraphics.setColor(back_color);
			 * bufGraphics.fillRect(DX+deltaX,deltaY+deltaX,deltaX2-deltaX,
			 * deltaX);
			 */

			// draw lines for reference

			bufGraphics.setColor(new Color(0, 0, 0));
			bufGraphics.setStroke(new BasicStroke(0.1f));
			for (int j = 0; j < mesh.polygonData.size(); j++) {

				LineData ld = mesh.polygonData.get(j);

				for (int k = 0; k < ld.size(); k++) {

					Point3D point0 = new Point3D(mesh.xpoints[ld.getIndex(k)], mesh.ypoints[ld.getIndex(k)],
							mesh.zpoints[ld.getIndex(k)]);
					Point3D point1 = new Point3D(mesh.xpoints[ld.getIndex((k + 1) % ld.size())],
							mesh.ypoints[ld.getIndex((k + 1) % ld.size())],
							mesh.zpoints[ld.getIndex((k + 1) % ld.size())]);
					// top
					bufGraphics.setColor(top_color);
					bufGraphics.drawLine(DX + (int) (point0.x - minx + deltaX), (int) (-point0.y + maxy + deltaX),
							DX + (int) (point1.x - minx + deltaX), (int) (-point1.y + maxy + deltaX));
					// front
					bufGraphics.setColor(front_color);
					bufGraphics.drawLine(DX + (int) (point0.x - minx + deltaX), (int) (point0.z - minz),
							DX + (int) (point1.x - minx + deltaX), (int) (point1.z - minz));
					// left
					bufGraphics.setColor(left_color);
					bufGraphics.drawLine(DX + (int) (point0.z - minz), (int) (-point0.y + maxy + deltaX),
							DX + (int) (point1.z - minz), (int) (-point1.y + maxy + deltaX));
					// right
					bufGraphics.setColor(right_color);
					bufGraphics.drawLine(DX + (int) (-point0.z + maxz + deltaX2), (int) (-point0.y + maxy + deltaX),
							DX + (int) (-point1.z + maxz + deltaX2), (int) (-point1.y + maxy + deltaX));
					// back
					bufGraphics.setColor(back_color);
					bufGraphics.drawLine(DX + (int) (point0.x - minx + deltaX),
							(int) (-point0.z + maxz + deltaY + deltaX), DX + (int) (point1.x - minx + deltaX),
							(int) (-point1.z + maxz + deltaY + deltaX));
					// bottom
					bufGraphics.setColor(bottom_color);
					bufGraphics.drawLine(DX + (int) (point0.x - minx + deltaX + deltaX2),
							(int) (-point0.y + maxy + deltaX), DX + (int) (point1.x - minx + deltaX + deltaX2),
							(int) (-point1.y + maxy + deltaX));

				}

			}

			ImageIO.write(buf, "gif", file);

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void undo() {
		super.undo();
		if (oldMeshes[ACTIVE_PANEL].isEmpty())
			jmt_undo_last.setEnabled(false);

		firePropertyChange("ObjectEditorUndo", false, true);
	}

	@Override
	public void prepareUndo() {
		jmt_undo_last.setEnabled(true);
		super.prepareUndo();

	}

	@Override
	public void decomposeObjVertices(PrintWriter pr, PolygonMesh mesh, boolean isCustom) {

		if (isCustom) {

			for (int i = 0; i < mesh.texturePoints.size(); i++) {
				Point3D pt = mesh.texturePoints.get(i);
				pr.print("\nvt=");
				pr.print(pt.x + " " + pt.y);
			}
			return;
		}

		int DX = 0;

		int deltaX = 0;
		int deltaY = 0;
		int deltaX2 = 0;

		double minx = 0;
		double miny = 0;
		double minz = 0;

		double maxx = 0;
		double maxy = 0;
		double maxz = 0;

		// find maxs
		for (int j = 0; j < mesh.xpoints.length; j++) {

			Point3D point = new Point3D(mesh.xpoints[j], mesh.ypoints[j], mesh.zpoints[j]);

			if (j == 0) {

				minx = point.x;
				miny = point.y;
				minz = point.z;

				maxx = point.x;
				maxy = point.y;
				maxz = point.z;
			} else {

				maxx = (int) Math.max(point.x, maxx);
				maxz = (int) Math.max(point.z, maxz);
				maxy = (int) Math.max(point.y, maxy);

				minx = (int) Math.min(point.x, minx);
				minz = (int) Math.min(point.z, minz);
				miny = (int) Math.min(point.y, miny);
			}

		}

		deltaX2 = (int) (maxx - minx) + 1;
		deltaX = (int) (maxz - minz) + 1;
		deltaY = (int) (maxy - miny) + 1;

		for (int i = 0; i < mesh.xpoints.length; i++) {

			Point3D p = new Point3D(mesh.xpoints[i], mesh.ypoints[i], mesh.zpoints[i]);

			/*
			 * public static final int CAR_BACK=0; public static final int
			 * CAR_TOP=1; public static final int CAR_LEFT=2; public static
			 * final int CAR_RIGHT=3; public static final int CAR_FRONT=4;
			 * public static final int CAR_BOTTOM=5;
			 */

			// back
			pr.print("\nvt=");
			pr.print(DX + (int) (p.x - minx + deltaX) + " " + (int) (p.z - minz));
			// top
			pr.print("\nvt=");
			pr.print(DX + (int) (p.x - minx + deltaX) + " " + (int) (p.y - miny + deltaX));
			// left
			pr.print("\nvt=");
			pr.print(DX + (int) (p.z - minz) + " " + (int) (p.y - miny + deltaX));
			// right
			pr.print("\nvt=");
			pr.print(DX + (int) (-p.z + maxz + deltaX2 + deltaX) + " " + (int) (p.y - miny + deltaX));
			// front
			pr.print("\nvt=");
			pr.print(DX + (int) (p.x - minx + deltaX) + " " + (int) (-p.z + maxz + deltaY + deltaX));
			// bottom
			pr.print("\nvt=");
			pr.print(DX + (int) (p.x - minx + 2 * deltaX + deltaX2) + " " + (int) (p.y - miny + deltaX));

		}
		pr.print("\n");

	}

	static double[][] getRotationMatrix(Point3D versor, double teta) {

		return getRotationMatrix(versor.x, versor.y, versor.z, teta);
	}

	/**
	 *
	 *
	 * ROTATION MATRIX OF TETA AROUND (X,Y,Z) AXIS
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @param teta
	 * @return
	 */

	private static final double[][] getRotationMatrix(double x, double y, double z, double teta) {

		double[][] matrix = new double[3][3];
		matrix[0][0] = Math.cos(teta) + x * x * (1.0 - Math.cos(teta));
		matrix[0][1] = y * x * (1.0 - Math.cos(teta)) - z * Math.sin(teta);
		matrix[0][2] = z * x * (1.0 - Math.cos(teta)) + y * Math.sin(teta);
		matrix[1][0] = y * x * (1.0 - Math.cos(teta)) + z * Math.sin(teta);
		matrix[1][1] = Math.cos(teta) + y * y * (1.0 - Math.cos(teta));
		matrix[1][2] = z * y * (1.0 - Math.cos(teta)) - x * Math.sin(teta);
		matrix[2][0] = z * x * (1.0 - Math.cos(teta)) - y * Math.sin(teta);
		matrix[2][1] = z * y * (1.0 - Math.cos(teta)) + x * Math.sin(teta);
		matrix[2][2] = Math.cos(teta) + z * z * (1.0 - Math.cos(teta));
		return matrix;
	}

	/**
	 * ROTATE AROUND POINT (X0,Y0,Z0) WITH MATRIX MATRIX
	 *
	 *
	 * @param ds
	 * @param matrix
	 * @param x0
	 * @param y0
	 * @param z0
	 */

	private static void rotate(Point3D p, double[][] matrix, double x0, double y0, double z0) {

		double xx = (p.x - x0);
		double yy = (p.y - y0);
		double zz = (p.z - z0);

		p.x = matrix[0][0] * xx + matrix[0][1] * yy + matrix[0][2] * zz + x0;
		p.y = matrix[1][0] * xx + matrix[1][1] * yy + matrix[1][2] * zz + y0;
		p.z = matrix[2][0] * xx + matrix[2][1] * yy + matrix[2][2] * zz + z0;

	}

	static void rotate(Point3D[] ds, double[][] matrix, double x0, double y0, double z0) {

		for (int i = 0; i < ds.length; i++) {

			rotate(ds[i], matrix, x0, y0, z0);
		}

	}

	static void rotate(double[] xpoints, double[] ypoints, double[] zpoints, double[][] matrix, double x0, double y0,
			double z0) {

		for (int i = 0; i < xpoints.length; i++) {

			double xx = (xpoints[i] - x0);
			double yy = (ypoints[i] - y0);
			double zz = (zpoints[i] - z0);

			xpoints[i] = matrix[0][0] * xx + matrix[0][1] * yy + matrix[0][2] * zz + x0;
			ypoints[i] = matrix[1][0] * xx + matrix[1][1] * yy + matrix[1][2] * zz + y0;
			zpoints[i] = matrix[2][0] * xx + matrix[2][1] * yy + matrix[2][2] * zz + z0;
		}

	}

	@Override
	public void menuSelected(MenuEvent arg0) {

		super.menuSelected(arg0);

		Object o = arg0.getSource();

	}

	@Override
	public Polygon3D buildPolygon(LineData ld, Point3D[] points, boolean isReal) {

		int size = ld.size();

		int[] cxr = new int[size];
		int[] cyr = new int[size];
		int[] czr = new int[size];

		for (int i = 0; i < size; i++) {

			int num = ld.getIndex(i);

			Point3D p = points[num];

			if (isReal) {

				// real coordinates
				cxr[i] = (int) (p.x);
				cyr[i] = (int) (p.y);
				czr[i] = (int) (p.z);

			} else {

				// cxr[i]=convertX(p.x);
				// cyr[i]=convertY(p.y);

			}

		}

		Polygon3D p3dr = new Polygon3D(size, cxr, cyr, czr);

		return p3dr;

	}

	public boolean isShowNornals() {

		return jmt_show_normals.isSelected();
	}

	public boolean isShowShading() {

		return jmt_show_shading.isSelected();
	}

	public boolean isShowTexture() {

		return jmt_show_texture.isSelected();
	}

	public void setShowTexture(boolean b) {

		jmt_show_texture.setSelected(b);

	}

}
