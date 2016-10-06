package org.yourorghere;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;



/**
 * Wiacek.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Wiacek implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("Simple JOGL Application");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Wiacek());
        frame.add(canvas);
        frame.setSize(640, 480);
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
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));

        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
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

    public void display(GLAutoDrawable drawable) {
        //Tworzenie obiektu
        GL gl = drawable.getGL();
        GL g2 = drawable.getGL();
        GL g3 = drawable.getGL();
        //Czyszczenie przestrzeni 3D przed utworzeniem kolejnej klatki
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        //Resetowanie macierzy transformacji
        gl.glLoadIdentity();
 
    gl.glColor3f(0.0f,0.0f,3.0f);
   
         //Wykonanie wszystkich operacji znajduj�cych si� w buforze
        gl.glFlush();
        gl.glBegin(GL.GL_TRIANGLES);

        gl.glVertex3f(-1.0f, 1.2f, -10.0f);
        gl.glVertex3f(-2.0f,-1.2f, -10.0f);
        gl.glVertex3f( 0.0f,-1.2f, -10.0f);
        gl.glEnd();
        
              g2.glFlush();
        g2.glBegin(GL.GL_QUADS);
        g2.glColor3f( 5.0f,2.0f,1.0f);
        g2.glVertex3f( -2.0f, -1.2f, -10.0f);
        g2.glVertex3f(-0.0f, -1.2f, -10.0f);
        g2.glVertex3f( -0f, -3.0f, -10.0f);
        g2.glVertex3f( -2.0f, -3.0f, -10.0f);
        g2.glEnd();
        
                   g3.glFlush();
        g3.glBegin(GL.GL_QUADS);
          g3.glColor3f(0.0f,0.0f,3.0f);
        g3.glVertex3f( -1.0f, -2.5f, -10.0f);
        g3.glVertex3f(-1.0f, -3.0f, -10.0f);
        g3.glVertex3f( -0.5f, -3.0f, -10.0f);
        g3.glVertex3f( -0.5f, -2.5f, -10.0f);
        g3.glEnd();

    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

