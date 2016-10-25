package com.editors.mesh;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import com.editors.DoubleTextField;
import com.editors.IntegerTextField;
import com.editors.ValuePair;
import com.editors.models.ATeamVanModel;
import com.editors.models.Airplane0Model;
import com.editors.models.Autobus0Model;
import com.editors.models.BeetleModel;
import com.editors.models.Byke0Model;
import com.editors.models.Camper0Model;
import com.editors.models.Car0Model;
import com.editors.models.CarTransporterModel;
import com.editors.models.F10Model;
import com.editors.models.FighterAircraft0Model;
import com.editors.models.Helicopter0Model;
import com.editors.models.Jeep0Model;
import com.editors.models.OceanLiner0Model;
import com.editors.models.PickupModel;
import com.editors.models.RailWagon0Model;
import com.editors.models.RailWagonBulkModel;
import com.editors.models.RailWagonCoachModel;
import com.editors.models.RailWagonCoalModel;
import com.editors.models.RailWagonTankModel;
import com.editors.models.Sailer0Model;
import com.editors.models.Ship0Model;
import com.editors.models.StarShip0Model;
import com.editors.models.SteamLocomotive0Model;
import com.editors.models.Tank0Model;
import com.editors.models.TankTruck0Model;
import com.editors.models.Tractor0Model;
import com.editors.models.Truck0Model;
import com.editors.models.TrunksTruck0Model;
import com.editors.models.Yacht0Model;

public class CarMeshEditor extends MeshModelEditor implements KeyListener, ItemListener {

    String title = "Car model";
    private DoubleTextField dz_text;
    private DoubleTextField dy_text;
    private DoubleTextField dx_text;
    private JComboBox chooseObject;

    private boolean skipItemChanged = false;
    private DoubleTextField dx_front_text;
    private DoubleTextField dy_front_text;
    private DoubleTextField dz_front_text;
    private DoubleTextField dx_rear_text;
    private DoubleTextField dy_rear_text;
    private DoubleTextField dz_rear_text;
    private DoubleTextField dx_roof_text;
    private DoubleTextField dy_roof_text;
    private DoubleTextField dz_roof_text;
    private DoubleTextField wheel_radius_text;
    private DoubleTextField wheel_width_text;
    private IntegerTextField wheel_rays_text;
    private DoubleTextField rear_overhang_text;
    private DoubleTextField front_overhang_text;
    private DoubleTextField rear_overhang1_text;
    private DoubleTextField front_overhang1_text;

    public static int CAR0 = 0;
    public static int TRUCK0 = 1;
    public static int SHIP0 = 2;
    public static int STARSHIP0 = 3;
    public static int TANK0 = 4;
    public static int AIRPLANE0 = 5;
    public static int F10 = 6;
    public static int BYKE0 = 7;
    public static int TANKTRUCK0 = 8;
    public static int RAILWAGON0 = 9;
    public static int RAILWAGON_TANK = 10;
    public static int RAILWAGON_BULK = 11;
    public static int RAILWAGON_COAL = 12;
    public static int RAILWAGON_COACH = 13;
    public static int CAMPER0 = 14;
    public static int OCEANLINER0 = 15;
    public static int YACHT0 = 16;
    public static int TRACTOR0 = 17;
    public static int FIGHTER_AIRCRAFT0 = 18;
    public static int AUTOBUS = 19;
    public static int PICKUP = 20;
    public static int ATEAMVAN = 21;
    public static int HELICOPTER = 22;
    public static int SAILER = 23;
    public static int JEEP = 24;
    public static int BEETLE = 25;
    public static int TRUNKSTRUCK = 26;
    public static int STEAMLOCOMOTIVE0 = 27;
    public static int CARTRANSPORTER = 28;

    public static void main(String[] args) {

        CarMeshEditor fm = new CarMeshEditor(660, 420);
    }

    public CarMeshEditor(int W, int H) {
        super(W, H);
        setTitle(title);
    }

    @Override
    public void buildCenter() {

        double dx = 100;
        double dy = 100;
        double dz = 100;

        int r = 10;

        JLabel name = new JLabel("Description:");
        name.setBounds(10, r, 120, 20);
        center.add(name);

        r += 30;

        description = new JTextField();
        description.setBounds(10, r, 480, 20);
        description.setToolTipText("Description");
        description.setText("");
        center.add(description);

        r += 30;

        int col0 = 10;
        int col1 = 100;
        int col2 = 230;
        int col3 = 310;
        int col4 = 430;
        int col5 = 480;

        JLabel lx = new JLabel("dx:");
        lx.setBounds(col0, r, 70, 20);
        center.add(lx);
        dx_text = new DoubleTextField(8);
        dx_text.setBounds(col1, r, 100, 20);
        dx_text.setText(dx);
        center.add(dx_text);

        JLabel ly = new JLabel("dy:");
        ly.setBounds(col2, r, 70, 20);
        center.add(ly);
        dy_text = new DoubleTextField(8);
        dy_text.setBounds(col3, r, 100, 20);
        dy_text.setText(dy);
        center.add(dy_text);

        JLabel lz = new JLabel("dz:");
        lz.setBounds(col4, r, 70, 20);
        center.add(lz);
        dz_text = new DoubleTextField(8);
        dz_text.setBounds(col5, r, 100, 20);
        dz_text.setText(dz);
        center.add(dz_text);

        r += 30;

        lx = new JLabel("dxFront:");
        lx.setBounds(col0, r, 70, 20);
        center.add(lx);
        dx_front_text = new DoubleTextField(8);
        dx_front_text.setBounds(col1, r, 100, 20);
        dx_front_text.setText(dx);
        center.add(dx_front_text);

        ly = new JLabel("dyFront:");
        ly.setBounds(col2, r, 70, 20);
        center.add(ly);
        dy_front_text = new DoubleTextField(8);
        dy_front_text.setBounds(col3, r, 100, 20);
        dy_front_text.setText(dy);
        center.add(dy_front_text);

        lz = new JLabel("dzFront:");
        lz.setBounds(col4, r, 70, 20);
        center.add(lz);
        dz_front_text = new DoubleTextField(8);
        dz_front_text.setBounds(col5, r, 100, 20);
        dz_front_text.setText(dz);
        center.add(dz_front_text);

        r += 30;

        lx = new JLabel("dxRear:");
        lx.setBounds(col0, r, 70, 20);
        center.add(lx);
        dx_rear_text = new DoubleTextField(8);
        dx_rear_text.setBounds(col1, r, 100, 20);
        dx_rear_text.setText(dx);
        center.add(dx_rear_text);

        ly = new JLabel("dyRear:");
        ly.setBounds(col2, r, 70, 20);
        center.add(ly);
        dy_rear_text = new DoubleTextField(8);
        dy_rear_text.setBounds(col3, r, 100, 20);
        dy_rear_text.setText(dy);
        center.add(dy_rear_text);

        lz = new JLabel("dzRear:");
        lz.setBounds(col4, r, 70, 20);
        center.add(lz);
        dz_rear_text = new DoubleTextField(8);
        dz_rear_text.setBounds(col5, r, 100, 20);
        dz_rear_text.setText(dz);
        center.add(dz_rear_text);

        r += 30;

        lx = new JLabel("dxRoof:");
        lx.setBounds(col0, r, 70, 20);
        center.add(lx);
        dx_roof_text = new DoubleTextField(8);
        dx_roof_text.setBounds(col1, r, 100, 20);
        dx_roof_text.setText(0);
        center.add(dx_roof_text);

        ly = new JLabel("dyRoof:");
        ly.setBounds(col2, r, 70, 20);
        center.add(ly);
        dy_roof_text = new DoubleTextField(8);
        dy_roof_text.setBounds(col3, r, 100, 20);
        dy_roof_text.setText(0);
        center.add(dy_roof_text);

        lz = new JLabel("dzRoof:");
        lz.setBounds(col4, r, 70, 20);
        center.add(lz);
        dz_roof_text = new DoubleTextField(8);
        dz_roof_text.setBounds(col5, r, 100, 20);
        dz_roof_text.setText(0);
        center.add(dz_roof_text);

        r += 30;

        ly = new JLabel("R Overhang");
        ly.setBounds(col0, r, 70, 20);
        center.add(ly);
        rear_overhang_text = new DoubleTextField(8);
        rear_overhang_text.setBounds(col1, r, 100, 20);
        rear_overhang_text.setText(0);
        center.add(rear_overhang_text);

        lz = new JLabel("F Overhang");
        lz.setBounds(col2, r, 70, 20);
        center.add(lz);
        front_overhang_text = new DoubleTextField(8);
        front_overhang_text.setBounds(col3, r, 100, 20);
        front_overhang_text.setText(0);
        center.add(front_overhang_text);

        r += 30;

        ly = new JLabel("R Overhang1");
        ly.setBounds(col0, r, 90, 20);
        center.add(ly);
        rear_overhang1_text = new DoubleTextField(8);
        rear_overhang1_text.setBounds(col1, r, 100, 20);
        rear_overhang1_text.setText(0);
        center.add(rear_overhang1_text);

        lz = new JLabel("F Overhang1");
        lz.setBounds(col2, r, 90, 20);
        center.add(lz);
        front_overhang1_text = new DoubleTextField(8);
        front_overhang1_text.setBounds(col3, r, 100, 20);
        front_overhang1_text.setText(0);
        center.add(front_overhang1_text);

        r += 30;

        lx = new JLabel("Wheel r:");
        lx.setBounds(col0, r, 70, 20);
        center.add(lx);
        wheel_radius_text = new DoubleTextField(8);
        wheel_radius_text.setBounds(col1, r, 100, 20);
        wheel_radius_text.setText(0);
        center.add(wheel_radius_text);

        ly = new JLabel("Wheel w:");
        ly.setBounds(col2, r, 70, 20);
        center.add(ly);
        wheel_width_text = new DoubleTextField(8);
        wheel_width_text.setBounds(col3, r, 100, 20);
        wheel_width_text.setText(0);
        center.add(wheel_width_text);

        lz = new JLabel("N# rays:");
        lz.setBounds(col4, r, 70, 20);
        center.add(lz);
        wheel_rays_text = new IntegerTextField(8);
        wheel_rays_text.setBounds(col5, r, 100, 20);
        wheel_rays_text.setText(0);
        center.add(wheel_rays_text);

        r += 30;

        JLabel jlb = new JLabel("Car type:");
        jlb.setBounds(col0, r, 100, 20);
        center.add(jlb);

        chooseObject = new JComboBox();
        chooseObject.setBounds(col1, r, 150, 20);
        chooseObject.addKeyListener(this);
        chooseObject.addItem(new ValuePair("-1", ""));
        chooseObject.addItem(new ValuePair("" + AIRPLANE0, Airplane0Model.NAME));
        chooseObject.addItem(new ValuePair("" + ATEAMVAN, ATeamVanModel.NAME));
        chooseObject.addItem(new ValuePair("" + AUTOBUS, Autobus0Model.NAME));
        chooseObject.addItem(new ValuePair("" + BEETLE, BeetleModel.NAME));
        chooseObject.addItem(new ValuePair("" + BYKE0, Byke0Model.NAME));
        chooseObject.addItem(new ValuePair("" + CAMPER0, Camper0Model.NAME));
        chooseObject.addItem(new ValuePair("" + CAR0, Car0Model.NAME));
        chooseObject.addItem(new ValuePair("" + CARTRANSPORTER, CarTransporterModel.NAME));
        chooseObject.addItem(new ValuePair("" + FIGHTER_AIRCRAFT0, FighterAircraft0Model.NAME));
        chooseObject.addItem(new ValuePair("" + F10, F10Model.NAME));
        chooseObject.addItem(new ValuePair("" + HELICOPTER, Helicopter0Model.NAME));
        chooseObject.addItem(new ValuePair("" + JEEP, Jeep0Model.NAME));
        chooseObject.addItem(new ValuePair("" + OCEANLINER0, OceanLiner0Model.NAME));
        chooseObject.addItem(new ValuePair("" + PICKUP, PickupModel.NAME));
        chooseObject.addItem(new ValuePair("" + RAILWAGON0, "Railwagon"));
        chooseObject.addItem(new ValuePair("" + RAILWAGON_BULK, "Railwagon Bulk"));
        chooseObject.addItem(new ValuePair("" + RAILWAGON_COAL, "Railwagon Coal"));
        chooseObject.addItem(new ValuePair("" + RAILWAGON_COACH, "Railwagon Coach"));
        chooseObject.addItem(new ValuePair("" + RAILWAGON_TANK, "Railwagon Tank "));
        chooseObject.addItem(new ValuePair("" + SAILER, Sailer0Model.NAME));
        chooseObject.addItem(new ValuePair("" + SHIP0, Ship0Model.NAME));
        chooseObject.addItem(new ValuePair("" + STARSHIP0, StarShip0Model.NAME));
        chooseObject.addItem(new ValuePair("" + STEAMLOCOMOTIVE0, SteamLocomotive0Model.NAME));
        chooseObject.addItem(new ValuePair("" + TANK0, Tank0Model.NAME));
        chooseObject.addItem(new ValuePair("" + TANKTRUCK0, TankTruck0Model.NAME));
        chooseObject.addItem(new ValuePair("" + TRACTOR0, Tractor0Model.NAME));
        chooseObject.addItem(new ValuePair("" + TRUCK0, Truck0Model.NAME));
        chooseObject.addItem(new ValuePair("" + TRUNKSTRUCK, TrunksTruck0Model.NAME));
        chooseObject.addItem(new ValuePair("" + YACHT0, Yacht0Model.NAME));
        chooseObject.addItemListener(this);

        chooseObject.setSelectedIndex(0);
        center.add(chooseObject);

        r += 40;

        meshButton = new JButton("Mesh");
        meshButton.setBounds(10, r, 80, 20);
        meshButton.addActionListener(this);
        center.add(meshButton);

        textureButton = new JButton("Texture");
        textureButton.setBounds(120, r, 90, 20);
        textureButton.addActionListener(this);
        center.add(textureButton);

    }

    @Override
    public void initMesh() {

        double dx = dx_text.getvalue();
        double dy = dy_text.getvalue();
        double dz = dz_text.getvalue();

        double dxf = dx_front_text.getvalue();
        double dyf = dy_front_text.getvalue();
        double dzf = dz_front_text.getvalue();

        double dxr = dx_rear_text.getvalue();
        double dyr = dy_rear_text.getvalue();
        double dzr = dz_rear_text.getvalue();

        double dxRoof = dx_roof_text.getvalue();
        double dyRoof = dy_roof_text.getvalue();
        double dzRoof = dz_roof_text.getvalue();

        double rearOverhang = rear_overhang_text.getvalue();
        double frontOverhang = front_overhang_text.getvalue();

        double rearOverhang1 = rear_overhang1_text.getvalue();
        double frontOverhang1 = front_overhang1_text.getvalue();

        double wheelRadius = wheel_radius_text.getvalue();
        double wheelWidth = wheel_width_text.getvalue();
        int wheelRays = wheel_rays_text.getvalue();

        ValuePair vp = (ValuePair) chooseObject.getSelectedItem();

        int val = Integer.parseInt(vp.getId());
        if (val < 0) {
            val = CAR0;
        }

        if (val == CAR0) {
            meshModel = new Car0Model(dx, dy, dz);
        } else if (val == BEETLE) {
            meshModel = new BeetleModel(dx, dy, dz);
        } else if (val == TRUCK0) {
            meshModel = new Truck0Model(dxRoof, dyRoof, dzRoof, dxf, dyf, dzf, dxr, dyr, dzr, dx, dy, dz, rearOverhang,
                    frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == TANKTRUCK0) {
            meshModel = new TankTruck0Model(dxRoof, dyRoof, dzRoof, dxf, dyf, dzf, dxr, dyr, dzr, dx, dy, dz,
                    rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == TRUNKSTRUCK) {
            meshModel = new TrunksTruck0Model(dxRoof, dyRoof, dzRoof, dxf, dyf, dzf, dxr, dyr, dzr, dx, dy, dz,
                    rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == PICKUP) {
            meshModel = new PickupModel(dxRoof, dyRoof, dzRoof, dxf, dyf, dzf, dxr, dyr, dzr, dx, dy, dz, rearOverhang,
                    frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == CAMPER0) {
            meshModel = new Camper0Model(dxRoof, dyRoof, dzRoof, dxf, dyf, dzf, dxr, dyr, dzr, dx, dy, dz, rearOverhang,
                    frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == AUTOBUS) {
            meshModel = new Autobus0Model(dxRoof, dyRoof, dzRoof, dxf, dyf, dzf, dxr, dyr, dzr, dx, dy, dz,
                    rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == ATEAMVAN) {
            meshModel = new ATeamVanModel(dxRoof, dyRoof, dzRoof, dxf, dyf, dzf, dxr, dyr, dzr, dx, dy, dz,
                    rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == JEEP) {
            meshModel = new Jeep0Model(dxRoof, dyRoof, dzRoof, dxf, dyf, dzf, dxr, dyr, dzr, dx, dy, dz, rearOverhang,
                    frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == CARTRANSPORTER) {
            meshModel = new CarTransporterModel(dx, dy, dz, dxf, dyf, dzf, dxRoof, dyRoof, dzRoof, wheelRadius,
                    wheelWidth, wheelRays);
        } else if (val == SHIP0) {
            meshModel = new Ship0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof, rearOverhang,
                    frontOverhang, rearOverhang1, frontOverhang1);
        } else if (val == OCEANLINER0) {
            meshModel = new OceanLiner0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof,
                    rearOverhang, frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == SAILER) {
            meshModel = new Sailer0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof, rearOverhang,
                    frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == YACHT0) {
            meshModel = new Yacht0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof, rearOverhang,
                    frontOverhang, rearOverhang1, frontOverhang1);
        } else if (val == STARSHIP0) {
            meshModel = new StarShip0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof);
        } else if (val == AIRPLANE0) {

            meshModel = new Airplane0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof);
        } else if (val == FIGHTER_AIRCRAFT0) {

            meshModel = new FighterAircraft0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof);
        } else if (val == HELICOPTER) {
            meshModel = new Helicopter0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof);
        } else if (val == BYKE0) {
            meshModel = new Byke0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof, wheelRadius,
                    wheelWidth, wheelRays);
        } else if (val == F10) {
            meshModel = new F10Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof, rearOverhang,
                    frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == TANK0) {
            meshModel = new Tank0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof, rearOverhang,
                    frontOverhang, rearOverhang1, frontOverhang1, wheelRadius, wheelWidth, wheelRays);
        } else if (val == STEAMLOCOMOTIVE0) {
            meshModel = new SteamLocomotive0Model(dx, dy, dz, dxf, dyf, dzf, dxRoof, dyRoof, dzRoof, wheelRadius,
                    wheelWidth, wheelRays);
        } else if (val == RAILWAGON0) {
            meshModel = new RailWagon0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof,
                    rearOverhang, frontOverhang, wheelRadius, wheelWidth, wheelRays);
        } else if (val == RAILWAGON_TANK) {
            meshModel = new RailWagonTankModel(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof,
                    rearOverhang, frontOverhang, wheelRadius, wheelWidth, wheelRays);
        } else if (val == RAILWAGON_BULK) {
            meshModel = new RailWagonBulkModel(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof,
                    rearOverhang, frontOverhang, wheelRadius, wheelWidth, wheelRays);
        } else if (val == RAILWAGON_COAL) {
            meshModel = new RailWagonCoalModel(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof,
                    rearOverhang, frontOverhang, wheelRadius, wheelWidth, wheelRays);
        } else if (val == RAILWAGON_COACH) {
            meshModel = new RailWagonCoachModel(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof,
                    rearOverhang, frontOverhang, wheelRadius, wheelWidth, wheelRays);
        } else if (val == TRACTOR0) {
            meshModel = new Tractor0Model(dx, dy, dz, dxf, dyf, dzf, dxr, dyr, dzr, dxRoof, dyRoof, dzRoof, wheelRadius,
                    wheelWidth, wheelRays);
        } else {
            meshModel = new Car0Model(dx, dy, dz);
        }

        meshModel.setDescription(description.getText());

        meshModel.initMesh();
    }

    @Override
    public void printTexture(File file) {

        meshModel.printTexture(file);

    }

    @Override
    public void printMeshData(PrintWriter pw) {

        meshModel.printMeshData(pw);
    }

    @Override
    public void itemStateChanged(ItemEvent arg0) {

        if (skipItemChanged) {
            return;
        }

        Object obj = arg0.getSource();

        if (obj == chooseObject) {

            ValuePair vp = (ValuePair) chooseObject.getSelectedItem();

            int val = Integer.parseInt(vp.getId());
            if (val < 0) {
                val = CAR0;
            }

            if (CAR0 == val) {
                setRightData(48, 128, 34, // dx,dy,dz
                        0, 0, 0, // dxFront, dyFront, dzFront
                        0, 0, 0, // dxRear, dyRear, dzRear
                        0, 0, 0, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        0, 0, 0// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (BEETLE == val) {
                setRightData(40, 106, 33, // dx,dy,dz
                        0, 0, 0, // dxFront, dyFront, dzFront
                        0, 0, 0, // dxRear, dyRear, dzRear
                        0, 0, 0, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        0, 0, 0// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (TRUCK0 == val) {
                setRightData(111, 319, 116, // dx,dy,dz
                        94, 61, 118, // dxFront, dyFront, dzFront
                        74, 319, 22, // dxRear, dyRear, dzRear
                        0, 0, 0, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        19, 12, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (TANKTRUCK0 == val) {
                setRightData(111, 319, 116, // dx, dy, dz
                        94, 61, 118, // dxFront, dyFront, dzFront
                        74, 319, 22, // dxRear, dyRear, dzRear
                        0, 0, 0, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        19, 12, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (TRUNKSTRUCK == val) {
                setRightData(111, 319, 116, // dx, dy, dz
                        94, 61, 118, // dxFront, dyFront, dzFront
                        74, 319, 22, // dxRear, dyRear, dzRear
                        0, 0, 0, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        19, 12, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (CARTRANSPORTER == val) {
                setRightData(111, 319, 116, // dx,dy,dz
                        94, 61, 118, // dxFront, dyFront, dzFront
                        74, 319, 22, // dxRear, dyRear, dzRear
                        0, 0, 0, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        19, 12, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (CAMPER0 == val) {
                setRightData(59, 141, 30, // dx, dy, dz
                        59, 55, 29, // dxFront, dyFront, dzFront
                        59, 111, 15, // dxRear, dyRear, dzRear
                        59, 38, 17, // dxRoof, dyRoof, dzRoof
                        51, 23, // rearOverhang, frontOverhang
                        0, 34, // rearOverhang1,frontOverhang1,
                        9, 6, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (AUTOBUS == val) {
                setRightData(111, 319, 116, // dx, dy, dz
                        94, 61, 118, // dxFront, dyFront, dzFront
                        74, 319, 22, // dxRear, dyRear, dzRear
                        43, 30, 13, // dxRoof, dyRoof, dzRoof
                        28, 20, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        19, 12, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (ATEAMVAN == val) {
                setRightData(111, 319, 116, // dx, dy, dz
                        111, 61, 118, // dxFront, dyFront, dzFront
                        111, 319, 22, // dxRear, dyRear, dzRear
                        43, 30, 13, // dxRoof, dyRoof, dzRoof
                        28, 20, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        19, 12, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (PICKUP == val) {
                setRightData(43, 55, 7, // dx, dy, dz
                        43, 64, 19, // dxFront, dyFront, dzFront
                        43, 55, 10, // dxRear, dyRear, dzRear
                        43, 30, 13, // dxRoof, dyRoof, dzRoof
                        28, 20, // rearOverhang, frontOverhang
                        0, 42, // rearOverhang1,frontOverhang1,
                        10, 7, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (JEEP == val) {
                setRightData(43, 55, 20, // dx,dy,dz
                        43, 44, 19, // dxFront, dyFront, dzFront
                        43, 20, 10, // dxRear, dyRear, dzRear
                        43, 10, 13, // dxRoof, dyRoof, dzRoof
                        28, 20, // rearOverhang, frontOverhang
                        0, 42, // rearOverhang1,frontOverhang1,
                        10, 7, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (STEAMLOCOMOTIVE0 == val) {
                setRightData(74, 319, 22, // dx,dy,dz
                        94, 61, 118, // dxFront, dyFront, dzFront
                        0, 0, 0, // dxRear, dyRear, dzRear
                        111, 319, 116, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        19, 12, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (SHIP0 == val) {
                setRightData(718, 4550, 378, // dx, dy, dz
                        718, 806, 80, // dxFront, dyFront, dzFront
                        718, 527, 80, // dxRear, dyRear, dzRear
                        718, 366, 595, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        0, 0, 0// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (OCEANLINER0 == val) {
                setRightData(733, 6996, 502, // dx, dy, dz
                        733, 988, 218, // dxFront, dyFront, dzFront
                        733, 720, 203, // dxRear, dyRear, dzRear
                        733, 4446, 335, // dxRoof, dyRoof, dzRoof
                        1183, 1394, // rearOverhang, frontOverhang
                        1881, 0, // rearOverhang1,frontOverhang1,
                        95, 494, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (YACHT0 == val) {
                setRightData(364, 2340, 255, // dx, dy, dz
                        0, 481, 0, // dxFront, dyFront, dzFront
                        0, 0, 0, // dxRear, dyRear, dzRear
                        364, 1882, 234, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        117, 710, // rearOverhang1,frontOverhang1,
                        0, 0, 0// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (SAILER == val) {
                setRightData(403, 2142, 304, // dx, dy, dz
                        403, 957, 86, // dxFront, dyFront, dzFront
                        624, 720, 34, // dxRear, dyRear, dzRear
                        0, 0, 0, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        17, 1310, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (STARSHIP0 == val) {
                setRightData(50, 200, 50, // dx, dy, dz
                        150, 150, 50, // dxFront, dyFront, dzFront
                        50, 250, 50, // dxRear, dyRear, dzRear
                        0, 0, 0, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        0, 0, 0// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (AIRPLANE0 == val) {
                setRightData(87, 455, 82, // dx, dy, dz
                        46, 104, 46, // dxFront, dyFront, dzFront
                        38, 260, 24, // dxRear, dyRear, dzRear
                        330, 217, 53, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        0, 0, 0// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (FIGHTER_AIRCRAFT0 == val) {
                setRightData(87, 455, 82, // dx, dy, dz
                        46, 104, 46, // dxFront, dyFront, dzFront
                        38, 260, 24, // dxRear, dyRear, dzRear
                        330, 217, 53, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        0, 0, 0// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (HELICOPTER == val) {
                setRightData(300, 400, 100, // dx, dy, dz
                        0, 0, 0, // dxFront, dyFront, dzFront
                        300, 100, 50, // dxRear, dyRear, dzRear
                        200, 150, 50, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        0, 0, 0// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (BYKE0 == val) {
                setRightData(12, 22, 12, // dx, dy, dz
                        19, 13, 14, // dxFront, dyFront, dzFront
                        12, 12, 13, // dxRear, dyRear, dzRear
                        12, 19, 7, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        8, 4, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (F10 == val) {
                setRightData(38, 38, 14, // dx, dy, dz
                        7, 55, 14, // dxFront, dyFront, dzFront
                        25, 19, 14, // dxRear, dyRear, dzRear
                        7, 24, 13, // dxRoof, dyRoof, dzRoof
                        15, 25, // rearOverhang, frontOverhang
                        6, 0, // rearOverhang1,frontOverhang1,
                        8, 10, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (TANK0 == val) {
                setRightData(300, 400, 100, // dx, dy, dz
                        100, 150, 150, // dxFront, dyFront, dzFront
                        300, 100, 50, // dxRear, dyRear, dzRear
                        200, 150, 50, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        0, 0, 0// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (TRACTOR0 == val) {
                setRightData(300, 400, 100, // dx, dy, dz
                        0, 0, 0, // dxFront, dyFront, dzFront
                        300, 100, 50, // dxRear, dyRear, dzRear
                        200, 150, 50, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        19, 12, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (RAILWAGON0 == val) {
                setRightData(69, 400, 7, // dx, dy, dz
                        41, 51, 14, // dxFront, dyFront, dzFront
                        41, 51, 14, // dxRear, dyRear, dzRear
                        70, 348, 70, // dxRoof, dyRoof, dzRoof
                        29, 29, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        13, 4, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (RAILWAGON0 == val) {
                setRightData(69, 400, 7, // dx, dy, dz
                        41, 51, 14, // dxFront, dyFront, dzFront
                        41, 51, 14, // dxRear, dyRear, dzRear
                        70, 348, 70, // dxRoof, dyRoof, dzRoof
                        29, 29, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        13, 4, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (RAILWAGON_TANK == val) {
                setRightData(65, 407, 7, // dx, dy, dz
                        41, 51, 14, // dxFront, dyFront, dzFront
                        41, 51, 14, // dxRear, dyRear, dzRear
                        83, 374, 83, // dxRoof, dyRoof, dzRoof
                        29, 29, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        13, 4, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (RAILWAGON_BULK == val) {
                setRightData(78, 364, 6, // dx, dy, dz
                        41, 57, 16, // dxFront, dyFront, dzFront
                        41, 57, 16, // dxRear, dyRear, dzRear
                        78, 364, 56, // dxRoof, dyRoof, dzRoof
                        32, 32, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        13, 4, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (RAILWAGON_COAL == val) {
                setRightData(63, 263, 13, // dx, dy, dz
                        41, 51, 16, // dxFront, dyFront, dzFront
                        41, 51, 16, // dxRear, dyRear, dzRear
                        100, 263, 61, // dxRoof, dyRoof, dzRoof
                        0, 0, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        13, 4, 10// wheel_radius, wheel_width, wheel_rays
                        );
            } else if (RAILWAGON_COACH == val) {
                setRightData(80, 746, 70, // dx, dy, dz
                        41, 71, 18, // dxFront, dyFront, dzFront
                        41, 71, 18, // dxRear, dyRear, dzRear
                        80, 746, 14, // dxRoof, dyRoof, dzRoof
                        64, 64, // rearOverhang, frontOverhang
                        0, 0, // rearOverhang1,frontOverhang1,
                        13, 4, 10// wheel_radius, wheel_width, wheel_rays
                        );
            }
        }

    }

    private void setRightData(int dx, int dy, int dz, int dxFront, int dyFront, int dzFront, int dxRear, int dyRear,
            int dzRear, int dxRoof, int dyRoof, int dzRoof, int rearOverhang, int frontOverhang, int rearOverhang1,
            int frontOverhang1, double wheel_radius, double wheel_width, int wheel_rays) {

        dx_text.setText(dx);
        dy_text.setText(dy);
        dz_text.setText(dz);

        dx_front_text.setText(dxFront);
        dy_front_text.setText(dyFront);
        dz_front_text.setText(dzFront);

        dx_rear_text.setText(dxRear);
        dy_rear_text.setText(dyRear);
        dz_rear_text.setText(dzRear);

        dx_roof_text.setText(dxRoof);
        dy_roof_text.setText(dyRoof);
        dz_roof_text.setText(dzRoof);

        rear_overhang_text.setText(rearOverhang);
        front_overhang_text.setText(frontOverhang);

        rear_overhang1_text.setText(rearOverhang1);
        front_overhang1_text.setText(frontOverhang1);

        wheel_radius_text.setText(wheel_radius);
        wheel_width_text.setText(wheel_width);
        wheel_rays_text.setText(wheel_rays);
    }

    @Override
    public void keyPressed(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub

    }

}
