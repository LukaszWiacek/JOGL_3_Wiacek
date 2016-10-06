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
 
     public void DisplayTriangleFan(float r, float xsr, float ysr, GL gl) {
 
         float x, y, kat;
         gl.glBegin(GL.GL_TRIANGLE_FAN);
         gl.glVertex3f(xsr,ysr,-6.0f); //?rodek
         for(kat = 0.0f; kat < (2.0f*Math.PI);
         kat+=(Math.PI/32.0f))
         {
         x = r*(float)Math.sin(kat)+xsr;
         y = r*(float)Math.cos(kat)+ysr;
         gl.glVertex3f(x, y, -6.0f); //kolejne punkty
         gl.glVertex3f(xsr, ysr, -6.0f); //?rodek
         for (kat = 0.0f; kat < (2.0f * Math.PI);
                 kat += (Math.PI / 32.0f)) {
             x = r * (float) Math.sin(kat) + xsr;
             y = r * (float) Math.cos(kat) + ysr;
             gl.glVertex3f(x, y, -6.0f); //kolejne punkty
          }
          gl.glEnd();
         
 
      }
 
      public void display(GLAutoDrawable drawable) {
          GL gl = drawable.getGL();
  
          // Clear the drawing area
          gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
          // Reset the current matrix to the "identity"
          gl.glLoadIdentity();
                 
         DisplayTriangleFan(1.0f , 0.0f , 0.0f, gl);
      
 
         DisplayTriangleFan(1.0f, 1.0f, 1.0f, gl);
 
          // Flush all drawing operations to the graphics card
          gl.glFlush();
      }
  
      public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
      }

    public void init(GLAutoDrawable drawable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  }
