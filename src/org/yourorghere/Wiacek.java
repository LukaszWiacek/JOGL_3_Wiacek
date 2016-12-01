package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Projekt1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel)
 * <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Wiacek implements GLEventListener {
//statyczne pola okre?laj?ce rotacj? wok�? osi X i Y

    private static float xrot = 0.0f, yrot = 0.0f;

    public static float ambientLight[] = {0.3f, 0.3f, 0.3f, 1.0f};//swiat?o otaczaj?ce
    public static float diffuseLight[] = {0.7f, 0.7f, 0.7f, 1.0f};//?wiat?o rozproszone
    public static float specular[] = {1.0f, 1.0f, 1.0f, 1.0f}; //?wiat?o odbite
    public static float lightPos[] = {0.0f, 150.0f, 150.0f, 1.0f};//pozycja ?wiat?a

    public static float ambientLight1[] = {0.3f, 0.3f, 0.3f, 1.0f};//swiat?o otaczaj?ce
    public static float diffuseLight1[] = {0.7f, 0.7f, 0.7f, 1.0f};//?wiat?o rozproszone
    public static float specular1[] = {1.0f, 1.0f, 1.0f, 1.0f}; //?wiat?o odbite
    public static float lightPos1[] = {0.0f, 150.0f, 150.0f, 1.0f};//pozycja ?wiat?a
    public static float lightPos2[] = {0.0f, 150.0f, -150.0f, 1.0f};
    public static float direction[] = {0, 0, 0};
    static BufferedImage image1 = null, image2 = null;
    static Texture t1 = null, t2 = null;

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Wiacek());
        frame.add(canvas);
        frame.setSize(1024, 768);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        //Obs?uga klawiszy strza?ek
        frame.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    xrot -= 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    xrot += 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    yrot += 1.0f;
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    yrot -= 1.0f;
                }

                if (e.getKeyChar() == 'q') {
                    ambientLight = new float[]{ambientLight[0] - 0.1f, ambientLight[1] - 0.1f, ambientLight[2] - 0.1f, ambientLight[3] - 0.01f};
                }
                if (e.getKeyChar() == 'w') {
                    ambientLight = new float[]{ambientLight[0] + 0.1f, ambientLight[1] + 0.1f, ambientLight[2] + 0.1f, ambientLight[3] + 0.01f};
                }
                if (e.getKeyChar() == 'a') {
                    diffuseLight = new float[]{diffuseLight[0] - 0.1f, diffuseLight[1] - 0.1f, diffuseLight[2] - 0.1f, diffuseLight[3] - 0.01f};
                }
                if (e.getKeyChar() == 's') {
                    diffuseLight = new float[]{diffuseLight[0] + 0.1f, diffuseLight[1] + 0.1f, diffuseLight[2] + 0.1f, diffuseLight[3] + 0.01f};
                }
                if (e.getKeyChar() == 'z') {
                    specular = new float[]{specular[0] - 0.1f, specular[1] - 0.1f, specular[2] - 0.1f, specular[3] - 0.01f};
                }
                if (e.getKeyChar() == 'x') {
                    specular = new float[]{specular[0] + 0.1f, specular[1] + 0.1f, specular[2] + 0.1f, specular[3] + 0.01f};
                }
                if (e.getKeyChar() == 'k') {
                    lightPos = new float[]{lightPos[0] - 0.1f, lightPos[1] - 0.1f, lightPos[2] - 0.1f, lightPos[3] - 0.01f};
                }
                if (e.getKeyChar() == 'l') {
                    lightPos = new float[]{lightPos[0] + 0.1f, lightPos[1] + 0.1f, lightPos[2] + 0.1f, lightPos[3] + 0.01f};
                }

            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
            }
        });
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();

    }

    public void init(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());
        gl.setSwapInterval(1);

        //warto?ci sk?adowe o?wietlenia i koordynaty ?r�d?a ?wiat?a
        float ambientLight[] = {0.3f, 0.3f, 0.3f, 1.0f};//swiat?o otaczaj?ce
        float diffuseLight[] = {0.7f, 0.7f, 0.7f, 1.0f};//?wiat?o rozproszone
        float specular[] = {1.0f, 1.0f, 1.0f, 1.0f}; //?wiat?o odbite
        float lightPos[] = {0.0f, 150.0f, 150.0f, 1.0f};//pozycja ?wiat?a
        //(czwarty parametr okre?la odleg?o?? ?r�d?a:
        //0.0f-niesko?czona; 1.0f-okre?lona przez pozosta?e parametry)
        gl.glEnable(GL.GL_LIGHTING); //uaktywnienie o?wietlenia
        //ustawienie parametr�w ?r�d?a ?wiat?a nr. 0
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, ambientLight, 0); //swiat?o otaczaj?ce
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, diffuseLight, 0); //?wiat?o rozproszone
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, specular, 0); //?wiat?o odbite
        gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, lightPos, 0); //pozycja ?wiat?a
        gl.glEnable(GL.GL_LIGHT0); //uaktywnienie ?r�d?a ?wiat?a nr. 0
        gl.glEnable(GL.GL_COLOR_MATERIAL); //uaktywnienie ?ledzenia kolor�w
        //kolory b?d? ustalane za pomoc? glColor
        gl.glColorMaterial(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE);
        gl.glEnable(GL.GL_DEPTH_TEST);
        // Setup the drawing area and shading mode
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
        try {
            image1 = ImageIO.read(getClass().getResourceAsStream("/pokemon.jpg"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/android.jpg"));
        } catch (Exception exc) {
            JOptionPane.showMessageDialog(null, exc.toString());
            return;
        }

        t1 = TextureIO.newTexture(image1, false);
        t2 = TextureIO.newTexture(image2, false);

        gl.glTexEnvi(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_BLEND | GL.GL_MODULATE);
        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);

        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));
        // Enable VSync
        gl.setSwapInterval(1);
        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH);
        //wy??czenie wewn?trzych stron prymityw�w
        gl.glEnable(GL.GL_CULL_FACE);

    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
            int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        if (height <= 0) { // avoid a divide by zero error!

            height = 1;
        }
        final float h = (float) width / (float) height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, h, 1.0, 20.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    private float[] WyznaczNormalna(float[] punkty, int ind1, int ind2, int ind3) {
        float[] norm = new float[3];
        float[] wektor0 = new float[3];
        float[] wektor1 = new float[3];

        for (int i = 0; i < 3; i++) {
            wektor0[i] = punkty[i + ind1] - punkty[i + ind2];
            wektor1[i] = punkty[i + ind2] - punkty[i + ind3];
        }

        norm[0] = wektor0[1] * wektor1[2] - wektor0[2] * wektor1[1];
        norm[1] = wektor0[2] * wektor1[0] - wektor0[0] * wektor1[2];
        norm[2] = wektor0[0] * wektor1[1] - wektor0[1] * wektor1[0];
        float d
                = (float) Math.sqrt((norm[0] * norm[0]) + (norm[1] * norm[1]) + (norm[2] * norm[2]));
        if (d == 0.0f) {
            d = 1.0f;
        }
        norm[0] /= d;
        norm[1] /= d;
        norm[2] /= d;

        return norm;
    }

    public void display(GLAutoDrawable drawable) {

        GL gl = drawable.getGL();
        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -6.0f); //przesuni?cie o 6 jednostek
        gl.glRotatef(xrot, 1.0f, 0.0f, 0.0f); //rotacja wok�? osi X
        gl.glRotatef(yrot, 0.0f, 1.0f, 0.0f); //rotacja wok�? osi Y
        //Tu piszemy kod tworz?cy obiekty 3D
        // Flush all drawing operations to the graphics card

     
        float x, y, kat, kat3;

        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glColor3f(1.0f, 1.0f, 0.0f);
        gl.glVertex3f(0.0f, 0.0f, 0.0f); //?rodek
        for (kat = 0.0f; kat < (2.0f * Math.PI);
                kat += (Math.PI / 32.0f)) {
            x = 1.0f * (float) Math.sin(kat);
            y = 1.0f * (float) Math.cos(kat);
            gl.glNormal3f(x, y, 0.0f);
            gl.glVertex3f(x, y, 0.0f); //kolejne punkty
        }
        gl.glEnd();

        //kolo2
        gl.glBegin(GL.GL_TRIANGLE_FAN);
        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glVertex3f(0.0f, 0.0f, 2.0f); //?rodek
        for (kat = (float) (2.0f * Math.PI); kat > 0.0f;
                kat -= (Math.PI / 32.0f)) {
            x = 1.0f * (float) Math.sin(kat);
            y = 1.0f * (float) Math.cos(kat);
            gl.glNormal3f(x, y, 0.0f);
            gl.glVertex3f(x, y, 2.0f); //kolejne punkty
        }
        gl.glEnd();

        //walec
        gl.glBindTexture(GL.GL_TEXTURE_2D, t2.getTextureObject());
        gl.glBegin(GL.GL_QUAD_STRIP);

        for (kat3 = (float) (2.0f * Math.PI); kat3 > 0.0f;
                kat3 -= (Math.PI / 32.0f)) {
            x = 1.0f * (float) Math.sin(kat3);
            y = 1.0f * (float) Math.cos(kat3);
            gl.glNormal3f(x, y, 0.0f);
            gl.glTexCoord2f(kat3 / 7, 0.0f);
            gl.glVertex3f(x, y, 2.0f);
            gl.glTexCoord2f(kat3 / 7, 1.0f);
            gl.glVertex3f(x, y, 0.0f);
        }
        gl.glEnd();
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
            boolean deviceChanged) {
    }
}
