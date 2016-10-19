package com.editors.models;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Vector;

import javax.imageio.ImageIO;

import com.BPoint;
import com.Point3D;
import com.Prism;
import com.Segments;
import com.main.Renderer3D;

public abstract class MeshModel {

    Vector<Point3D> texturePoints = null;
    Vector<Point3D> points = null;
    private String description = null;

    protected int[][][] faces;

    private Color backgroundColor = Color.green;

    int IMG_WIDTH = 100;
    int IMG_HEIGHT = 100;

    static int FACE_TYPE_ORIENTATION = 0;
    static int FACE_TYPE_BODY_INDEXES = 1;
    static int FACE_TYPE_TEXTURE_INDEXES = 2;

    // sqrt(1-x*x), 0.125,0.1 decimal fraction steps
    double[] el_125 = { 1.0, 0.9922, 0.9682, 0.9270, 0.8660, 0.7806, 0.6614, 0.4841, 0.0 };
    double[] el_1 = { 1.0, 0.9950, 0.9798, 0.9539, 0.9165, 0.8660, 0.8, 0.7141, 0.6, 0.4359, 0.0 };

    public MeshModel() {

    }

    public void printMeshData(PrintWriter pw) {

        print(pw, "DESCRIPTION=" + description);

        for (int i = 0; i < points.size(); i++) {

            Point3D p = points.elementAt(i);
            print(pw, "v=" + p.x + " " + p.y + " " + p.z);

        }

        for (int i = 0; i < texturePoints.size(); i++) {
            Point3D p = texturePoints.elementAt(i);
            print(pw, "vt=" + p.x + " " + p.y);
        }

    }

    /***
     * Format:
     *
     * faces[nf][type][values]
     *
     * nf=face number type= o =orientation, 1=body polygon indexes 2=texture
     * polygon indexes values=orientation number or indexes
     *
     * @param pw
     * @param faces
     */
    void printFaces(PrintWriter pw, int[][][] faces) {

        if (faces == null) {
            return;
        }

        for (int i = 0; i < faces.length; i++) {

            int[][] face = faces[i];

            int[] fts = face[0];
            int[] pts = face[1];
            int[] tts = face[2];

            String line = "f=[" + fts[0] + "]";

            int len = pts.length;

            for (int j = 0; j < len; j++) {

                if (j > 0) {
                    line += " ";
                }
                line += (pts[j] + "/" + tts[j]);
            }

            print(pw, line);

        }

    }

    public abstract void initMesh();

    public abstract void printTexture(Graphics2D bufGraphics);

    void print(PrintWriter pw, String string) {

        pw.println(string);

    }

    void printTextureLine(Graphics2D graphics, int... indexes) {

        for (int i = 0; i < indexes.length - 1; i++) {

            Point3D p0 = texturePoints.elementAt(indexes[i]);
            Point3D p1 = texturePoints.elementAt(indexes[i + 1]);

            graphics.drawLine(cX(p0.x), cY(p0.y), cX(p1.x), cY(p1.y));
        }
    }

    void printTexturePolygon(Graphics2D graphics, int... indexes) {

        for (int i = 0; i < indexes.length; i++) {

            Point3D p0 = texturePoints.elementAt(indexes[i]);
            Point3D p1 = texturePoints.elementAt(indexes[(i + 1) % indexes.length]);

            graphics.drawLine(cX(p0.x), cY(p0.y), cX(p1.x), cY(p1.y));
        }
    }

    private int cX(double x) {
        return (int) x;
    }

    private int cY(double y) {

        return (int) (IMG_HEIGHT - y);
    }

    public void printTexture(File file) {

        BufferedImage buf = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_BYTE_INDEXED);

        try {

            Graphics2D bufGraphics = (Graphics2D) buf.getGraphics();

            bufGraphics.setColor(backgroundColor);
            bufGraphics.fillRect(0, 0, IMG_WIDTH, IMG_HEIGHT);

            printTexture(bufGraphics);

            ImageIO.write(buf, "gif", file);

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    void printTextureFaces(Graphics2D bg, int[][][] faces) {

        printTextureFaces(bg, faces, 0, faces.length);
    }

    void printTextureFaces(Graphics2D bg, int[][][] faces, int minimum, int maximum) {

        for (int i = minimum; i < maximum; i++) {

            int[][] face = faces[i];

            int[] fts = face[0];
            int[] pts = face[1];
            int[] tts = face[2];

            if (tts.length == 4) {

                int idx0 = tts[0];
                int idx1 = tts[1];
                int idx2 = tts[2];
                int idx3 = tts[3];

                printTexturePolygon(bg, idx0, idx1, idx2, idx3);

            } else if (tts.length == 3) {

                int idx0 = tts[0];
                int idx1 = tts[1];
                int idx2 = tts[2];

                printTexturePolygon(bg, idx0, idx1, idx2);

            }
        }

    }

    void addPoint(double x, double y, double z) {

        points.add(new Point3D(x, y, z));

    }

    /**
     * Add a texture point
     *
     * @param x
     * @param y
     * @param z
     */
    void addTPoint(double x, double y, double z) {

        texturePoints.add(new Point3D(x, y, z));

    }

    /**
     * Add a Texture rectangle starting from the x,y point, moving dx ,dy
     *
     * @param x
     * @param y
     * @param dx
     * @param dy
     */
    void addTRect(double x, double y, double dx, double dy) {

        addTPoint(x, y, 0);
        addTPoint(x + dx, y, 0);
        addTPoint(x + dx, y + dy, 0);
        addTPoint(x, y + dy, 0);

    }

    /**
     * Add a new texture points moving from the last one It's more similar to
     * the real action of drawing from point to point
     *
     * @param x
     * @param y
     * @param dx
     * @param dy
     */
    void seqTPoint(double dx, double dy) {

        if (texturePoints == null || texturePoints.isEmpty()) {
            return;
        }

        Point3D p = texturePoints.lastElement();
        addTPoint(p.x + dx, p.y + dy, 0);
    }

    BPoint addBPoint(double x, double y, double z) {

        int index = points.size();
        BPoint p = new BPoint(x, y, z, index);
        points.add(p);

        return p;
    }

    protected BPoint addBPoint(double d, double e, double f, Segments s0) {
        return addBPoint(s0.x(d), s0.y(e), s0.z(f));
    }

    /**
     * Connected loops for the lateral surface of a block
     *
     * @param nBasePoints
     * @param numSections
     * @param pOffset
     * @param tOffset
     * @return
     */
    int[][][] buildSingleBlockFaces(int nBasePoints, int numSections, int pOffset, int tOffset) {

        int NUM_FACES = nBasePoints * (numSections - 1);
        int[][][] faces = new int[NUM_FACES][3][nBasePoints];

        int counter = 0;
        for (int k = 0; k < numSections - 1; k++) {

            int numLevelPoints = nBasePoints * (k + 1);

            for (int p0 = 0; p0 < nBasePoints; p0++) {

                int p = p0 + k * nBasePoints;
                int t = p0 + k * (nBasePoints + 1);

                faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION] = Renderer3D.CAR_BACK;

                int[] pts = new int[4];
                faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES] = pts;
                pts[0] = p + pOffset;
                int pl = (p + 1) % numLevelPoints;
                if (pl == 0) {
                    pl = k * nBasePoints;
                }
                pts[1] = pl + pOffset;
                pts[2] = pl + nBasePoints + pOffset;
                pts[3] = p + nBasePoints + pOffset;

                int[] tts = new int[4];
                faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES] = tts;
                tts[0] = t + tOffset;
                tts[1] = t + 1 + tOffset;
                tts[2] = t + 1 + nBasePoints + 1 + tOffset;
                tts[3] = t + nBasePoints + 1 + tOffset;

                counter++;

            }

        }

        return faces;

    }

    /**
     * Disconnected loops for the lateral surface of a block
     *
     * @param nBasePoints
     * @param numSections
     * @param pOffset
     * @param tOffset
     * @return
     */
    int[][][] buildRedundantSingleBlockFaces(int nBasePoints, int numSections, int pOffset, int tOffset) {

        int NUM_FACES = nBasePoints * (numSections - 1);
        int[][][] faces = new int[NUM_FACES][3][nBasePoints];

        int counter = 0;
        for (int k = 0; k < numSections - 1; k++) {

            int numLevelPoints = nBasePoints * (k + 1);

            for (int p0 = 0; p0 < nBasePoints; p0++) {

                int p = p0 + k * nBasePoints;
                int t = p0 * 4 + k * 4 * (nBasePoints);

                faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION] = Renderer3D.CAR_BACK;

                int[] pts = new int[4];
                faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES] = pts;
                pts[0] = p + pOffset;
                int pl = (p + 1) % numLevelPoints;
                if (pl == 0) {
                    pl = k * nBasePoints;
                }
                pts[1] = pl + pOffset;
                pts[2] = pl + nBasePoints + pOffset;
                pts[3] = p + nBasePoints + pOffset;

                int[] tts = new int[4];
                faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES] = tts;
                tts[0] = t + tOffset;
                tts[1] = t + 1 + tOffset;
                tts[2] = t + 2 + tOffset;
                tts[3] = t + 3 + tOffset;

                counter++;

            }

        }

        return faces;

    }

    int[][][] buildDoubleBlockFaces(int nBasePoints, int numSections, int pOffset, int tOffset) {

        int NUM_FACES = nBasePoints * (numSections - 1);
        int[][][] faces = new int[NUM_FACES][3][nBasePoints];

        int counter = 0;
        for (int k = 0; k < numSections - 1; k++) {

            int numLevelPoints = nBasePoints * (k + 1);
            int texLevelPoints = nBasePoints / 2 + 1;
            int sigma = texLevelPoints * numSections;

            for (int p0 = 0; p0 < nBasePoints; p0++) {

                int p = p0 + k * nBasePoints;
                int t = 0;
                if (p0 < nBasePoints / 2) {
                    t = p0 + k * texLevelPoints;
                } else {
                    t = p0 + k * texLevelPoints - nBasePoints / 2 + sigma;
                }

                faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION] = Renderer3D.CAR_BACK;

                int[] pts = new int[4];
                faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES] = pts;
                pts[0] = p + pOffset;
                int pl = (p + 1) % numLevelPoints;
                if (pl == 0) {
                    pl = k * nBasePoints;
                }
                pts[1] = pl + pOffset;
                pts[2] = pl + nBasePoints + pOffset;
                pts[3] = p + nBasePoints + pOffset;

                int[] tts = new int[4];
                faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES] = tts;
                tts[0] = t + tOffset;
                tts[1] = t + 1 + tOffset;
                tts[2] = t + 1 + texLevelPoints + tOffset;
                tts[3] = t + texLevelPoints + tOffset;

                counter++;

            }

        }

        return faces;

    }

    int[][][] buildSinglePlaneFaces(int nBasePoints, int numSections, int pOffset, int tOffset) {

        int NUM_FACES = (nBasePoints - 1) * (numSections - 1);
        int[][][] faces = new int[NUM_FACES][3][nBasePoints];

        Vector<int[][]> finalFaces = new Vector<int[][]>();

        int counter = 0;
        for (int k = 0; k < numSections - 1; k++) {

            // int numLevelPoints=nBasePoints*(k+1);

            for (int p0 = 0; p0 < nBasePoints - 1; p0++) {

                if (isFilter(k, p0)) {
                    continue;
                }

                finalFaces.add(faces[counter]);

                int p = p0 + k * nBasePoints;
                int t = p0 + k * nBasePoints;

                faces[counter][0][MeshModel.FACE_TYPE_ORIENTATION] = Renderer3D.CAR_BACK;

                int[] pts = new int[4];
                faces[counter][MeshModel.FACE_TYPE_BODY_INDEXES] = pts;
                pts[0] = p + pOffset;
                int pl = (p + 1);
                pts[1] = pl + pOffset;
                pts[2] = pl + nBasePoints + pOffset;
                pts[3] = p + nBasePoints + pOffset;

                int[] tts = new int[4];
                faces[counter][MeshModel.FACE_TYPE_TEXTURE_INDEXES] = tts;
                tts[0] = t + tOffset;
                tts[1] = t + 1 + tOffset;
                tts[2] = t + 1 + nBasePoints + tOffset;
                tts[3] = t + nBasePoints + tOffset;

                counter++;

            }

        }

        int[][][] fFaces = new int[finalFaces.size()][3][nBasePoints];

        for (int i = 0; i < finalFaces.size(); i++) {

            fFaces[i] = finalFaces.elementAt(i);
        }

        return fFaces;

    }

    public boolean isFilter(int k, int p0) {
        // TODO Auto-generated method stub
        return false;
    }

    /***
     *
     * Simplify the mesh discarding not used and repeated points
     *
     * @param data
     * @return
     */
    void postProcessor(Vector<int[][][]> vFaces) {

        Hashtable fp = new Hashtable();

        Vector<Point3D> newPoints = new Vector<Point3D>();

        int counter = 0;

        Hashtable usedPoints = buildUsedPointsHashtable(vFaces);
        Hashtable readPoints = new Hashtable();

        for (int i = 0; i < points.size(); i++) {

            if (usedPoints.get(i) == null) {
                continue;
            }

            Point3D p = points.elementAt(i);

            String key = p.toString();

            Integer oldIndex = (Integer) readPoints.get(key);

            if (oldIndex != null) {

                fp.put(i, oldIndex);
                continue;
            }

            readPoints.put(p.toString(), counter);
            fp.put(i, counter);
            newPoints.add(p);

            counter++;
        }

        points = newPoints;

        for (int i = 0; i < vFaces.size(); i++) {

            int[][][] faces = vFaces.elementAt(i);

            for (int j = 0; j < faces.length; j++) {
                int[][] face = faces[j];

                int[] fts = face[0];
                int[] pts = face[1];
                int[] tts = face[2];

                for (int k = 0; k < pts.length; k++) {
                    int idx0 = pts[k];
                    pts[k] = (Integer) fp.get(idx0);
                }
            }
        }

    }

    private static Hashtable buildUsedPointsHashtable(Vector<int[][][]> vFaces) {

        Hashtable usedPoints = new Hashtable();

        for (int i = 0; i < vFaces.size(); i++) {

            int[][][] faces = vFaces.elementAt(i);

            for (int j = 0; j < faces.length; j++) {
                int[][] face = faces[j];

                int[] fts = face[0];
                int[] pts = face[1];
                int[] tts = face[2];

                for (int k = 0; k < pts.length; k++) {
                    int idx0 = pts[k];
                    usedPoints.put(idx0, "");
                }

            }
        }

        return usedPoints;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected int[][] buildFace(

            int faceIndex, int b0, int b1, int b2, int b3, int c0, int c1, int c2, int c3) {

        int[][] face = new int[3][4];

        face[0][0] = faceIndex;

        face[1][0] = b0;
        face[1][1] = b1;
        face[1][2] = b2;
        face[1][3] = b3;

        face[2][0] = c0;
        face[2][1] = c1;
        face[2][2] = c2;
        face[2][3] = c3;

        return face;

    }

    protected int[][] buildFace(int carTop, BPoint p0, BPoint p1, BPoint p2, BPoint p3, int[] c) {
        return buildFace(carTop, p0.getIndex(), p1.getIndex(), p2.getIndex(), p3.getIndex(), c[0], c[1], c[2], c[3]);
    }

    protected int[][] buildFace(int carTop, BPoint p0, BPoint p1, BPoint p2, BPoint p3, int c0, int c1, int c2,
            int c3) {
        return buildFace(carTop, p0.getIndex(), p1.getIndex(), p2.getIndex(), p3.getIndex(), c0, c1, c2, c3);
    }

    protected int[][] buildFace(int carTop, BPoint p0, BPoint p1, BPoint p2, int[] c) {
        return buildFace(carTop, p0.getIndex(), p1.getIndex(), p2.getIndex(), c[0], c[1], c[2]);
    }

    protected int[][] buildFace(int carTop, BPoint p0, BPoint p1, BPoint p2, int c0, int c1, int c2) {
        return buildFace(carTop, p0.getIndex(), p1.getIndex(), p2.getIndex(), c0, c1, c2);
    }

    private int[][] buildFace(

            int faceIndex, int b0, int b1, int b2, int c0, int c1, int c2) {

        int[][] face = new int[3][3];

        face[0][0] = faceIndex;

        face[1][0] = b0;
        face[1][1] = b1;
        face[1][2] = b2;

        face[2][0] = c0;
        face[2][1] = c1;
        face[2][2] = c2;

        return face;

    }

    protected BPoint[][] buildWheel(double rxc, double ryc, double rzc, double r, double wheel_width, int raysNumber) {

        BPoint[][] wheelPoints = new BPoint[raysNumber][2];

        for (int i = 0; i < raysNumber; i++) {

            double teta = i * 2 * Math.PI / (raysNumber);

            double x = rxc;
            double y = ryc + r * Math.sin(teta);
            double z = rzc + r * Math.cos(teta);

            wheelPoints[i][0] = addBPoint(x, y, z);
            wheelPoints[i][1] = addBPoint(x + wheel_width, y, z);
        }

        return wheelPoints;

    }

    protected int[][][] buildWheelFaces(BPoint[][] wheelPoints, int texture_index) {

        int raysNumber = wheelPoints.length;
        int totWheelPolygon = raysNumber + 2 * (raysNumber - 2);

        int[][][] bFaces = new int[totWheelPolygon][][];

        // wheel track
        int counter = 0;

        for (int i = 0; i < raysNumber; i++) {

            BPoint p0 = wheelPoints[i][0];
            BPoint p1 = wheelPoints[i][1];
            BPoint p2 = wheelPoints[(i + 1) % raysNumber][1];
            BPoint p3 = wheelPoints[(i + 1) % raysNumber][0];

            bFaces[counter++] = buildFace(0, p0, p1, p2, p3, texture_index, texture_index + 1, texture_index + 2,
                    texture_index + 3);
        }
        // wheel sides as triangles
        for (int i = 1; i < raysNumber - 1; i++) {

            BPoint p0 = wheelPoints[0][0];
            BPoint p1 = wheelPoints[i][0];
            BPoint p2 = wheelPoints[(i + 1) % raysNumber][0];

            bFaces[counter++] = buildFace(0, p0, p1, p2, texture_index, texture_index + 1, texture_index + 2);
        }

        for (int i = 1; i < raysNumber - 1; i++) {

            BPoint p0 = wheelPoints[0][1];
            BPoint p1 = wheelPoints[(i + 1) % raysNumber][1];
            BPoint p2 = wheelPoints[i][1];

            bFaces[counter++] = buildFace(0, p0, p1, p2, texture_index, texture_index + 1, texture_index + 2);
        }

        return bFaces;

    }

    protected int[][][] buildWheelFaces(BPoint[][] wheelPoints, int[] texture_indexes) {

        int raysNumber = wheelPoints.length;
        int totWheelPolygon = raysNumber + 2 * (raysNumber - 2);

        int[][][] bFaces = new int[totWheelPolygon][][];

        // wheel track
        int counter = 0;

        for (int i = 0; i < raysNumber; i++) {

            BPoint p0 = wheelPoints[i][0];
            BPoint p1 = wheelPoints[i][1];
            BPoint p2 = wheelPoints[(i + 1) % raysNumber][1];
            BPoint p3 = wheelPoints[(i + 1) % raysNumber][0];

            bFaces[counter++] = buildFace(0, p0, p1, p2, p3, texture_indexes);
        }
        // wheel sides as triangles
        for (int i = 1; i < raysNumber - 1; i++) {

            BPoint p0 = wheelPoints[0][0];
            BPoint p1 = wheelPoints[i][0];
            BPoint p2 = wheelPoints[(i + 1) % raysNumber][0];

            bFaces[counter++] = buildFace(0, p0, p1, p2, texture_indexes);
        }

        for (int i = 1; i < raysNumber - 1; i++) {

            BPoint p0 = wheelPoints[0][1];
            BPoint p1 = wheelPoints[(i + 1) % raysNumber][1];
            BPoint p2 = wheelPoints[i][1];

            bFaces[counter++] = buildFace(0, p0, p1, p2, texture_indexes);
        }

        return bFaces;

    }

    BPoint[][] addYCylinder(double cyx0, double cyy0, double cyz0, double cylinder_radius, double cylinder_lenght,
            int barrel_meridians) {

        BPoint[][] trunkpoints = new BPoint[barrel_meridians][2];

        for (int i = 0; i < barrel_meridians; i++) {

            double x = cyx0 + cylinder_radius * Math.cos(2 * Math.PI / barrel_meridians * i);
            double z = cyz0 + cylinder_radius * Math.sin(2 * Math.PI / barrel_meridians * i);

            trunkpoints[i][1] = addBPoint(x, cyy0 + cylinder_lenght, z);

        }

        for (int i = 0; i < barrel_meridians; i++) {

            double x = cyx0 + cylinder_radius * Math.cos(2 * Math.PI / barrel_meridians * i);
            double z = cyz0 + cylinder_radius * Math.sin(2 * Math.PI / barrel_meridians * i);

            trunkpoints[i][0] = addBPoint(x, cyy0, z);

        }

        return trunkpoints;

    }

    BPoint[][] addZCylinder(double cyx0, double cyy0, double cyz0, double cylinder_radius, double cylinder_lenght,
            int barrel_meridians) {

        BPoint[][] trunkpoints = new BPoint[barrel_meridians][2];

        for (int i = 0; i < barrel_meridians; i++) {

            double x = cyx0 + cylinder_radius * Math.cos(2 * Math.PI / barrel_meridians * i);
            double yy = cyy0 + cylinder_radius * Math.sin(2 * Math.PI / barrel_meridians * i);

            trunkpoints[i][1] = addBPoint(x, yy, cyz0 + cylinder_lenght);

        }

        for (int i = 0; i < barrel_meridians; i++) {

            double x = cyx0 + cylinder_radius * Math.cos(2 * Math.PI / barrel_meridians * i);
            double yy = cyy0 + cylinder_radius * Math.sin(2 * Math.PI / barrel_meridians * i);

            trunkpoints[i][0] = addBPoint(x, yy, cyz0);

        }

        return trunkpoints;

    }

    protected int addPrism(Prism prism, int counter, int[] bo) {

        faces[counter++] = buildFace(Renderer3D.CAR_TOP, (BPoint) prism.upperBase[0], (BPoint) prism.upperBase[1],
                (BPoint) prism.upperBase[2], (BPoint) prism.upperBase[3], bo);
        faces[counter++] = buildFace(Renderer3D.CAR_LEFT, (BPoint) prism.lowerBase[3], (BPoint) prism.lowerBase[0],
                (BPoint) prism.upperBase[0], (BPoint) prism.upperBase[3], bo);
        faces[counter++] = buildFace(Renderer3D.CAR_RIGHT, (BPoint) prism.lowerBase[1], (BPoint) prism.lowerBase[2],
                (BPoint) prism.upperBase[2], (BPoint) prism.upperBase[1], bo);
        faces[counter++] = buildFace(Renderer3D.CAR_FRONT, (BPoint) prism.lowerBase[2], (BPoint) prism.lowerBase[3],
                (BPoint) prism.upperBase[3], (BPoint) prism.upperBase[2], bo);
        faces[counter++] = buildFace(Renderer3D.CAR_BACK, (BPoint) prism.lowerBase[0], (BPoint) prism.lowerBase[1],
                (BPoint) prism.upperBase[1], (BPoint) prism.upperBase[0], bo);
        faces[counter++] = buildFace(Renderer3D.CAR_BOTTOM, (BPoint) prism.lowerBase[0], (BPoint) prism.lowerBase[3],
                (BPoint) prism.lowerBase[2], (BPoint) prism.lowerBase[1], bo);

        return counter;
    }


    protected static void incrementArrayValues(int[][] bigArray, int inc) {

        for (int j = 0; j < bigArray.length; j++) {

            int[] arr = bigArray[j];

            for (int k = 0; k < arr.length; k++) {
                arr[k]+=inc;
            }

        }

    }


}
