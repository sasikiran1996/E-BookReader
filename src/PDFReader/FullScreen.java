package PDFReader;

/*
 * ===========================================
 * Java Pdf Extraction Decoding Access Library
 * ===========================================
 *
 * Project Info:  http://www.idrsolutions.com
 * Help section for developers at http://www.idrsolutions.com/support/
 *
 * (C) Copyright 1997-2015 IDRsolutions and Contributors.
 *
 * This file is part of JPedal/JPDF2HTML5
 *
 
 *
 * ---------------
 * FullScreen.java
 * ---------------
 */


import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import org.jpedal.PdfDecoderInt;
import org.jpedal.examples.viewer.Values;
import org.jpedal.examples.viewer.commands.Exit;
import org.jpedal.examples.viewer.gui.generic.GUIThumbnailPanel;
import org.jpedal.examples.viewer.utils.PropertiesFile;
import org.jpedal.gui.GUIFactory;

/**
 *
 */
public class FullScreen {

    /**window for full screen mode*/
    private static Window win;
       /**location to restore after full screen*/
    private static Point screenPosition;
    
    
    public static void execute(final Object[] args, final GUIFactory currentGUI, final GUIThumbnailPanel thumbnails, final Values commonValues, final PdfDecoderInt decode_pdf, final PropertiesFile properties) {
        if (args == null) {
            // Determine if full-screen mode is supported directly
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            final GraphicsDevice gs = ge.getDefaultScreenDevice();
            if (gs.isFullScreenSupported()) {
                // Full-screen mode is supported
            } else {
                // Full-screen mode will be simulated
            }

            // Create a window for full-screen mode; add a button to leave full-screen mode
            if (win == null) {
                final Frame frame = new Frame(gs.getDefaultConfiguration());
                win = new Window(frame);
            } else {
                //added to allow actions from named actions to interact the full screen as it needs to.
                if (gs.getFullScreenWindow() != null
                        && gs.getFullScreenWindow().equals(win)) {
                    exitFullScreen(currentGUI);
                    return;
                }
            }

            if (currentGUI.getFrame() instanceof JFrame) {
                ((JFrame) currentGUI.getFrame()).getContentPane().remove((JSplitPane)currentGUI.getFrame());
                // Java 1.6 has issues with original pane remaining visible so hide when fullscreen selected
                ((Container)currentGUI.getFrame()).setVisible(false);
            } else {
                ((Container)currentGUI.getFrame()).remove((JSplitPane)currentGUI.getFrame());
            }

            win.add((JSplitPane)currentGUI.getFrame(), BorderLayout.CENTER);

            // Create a button that leaves full-screen mode
            final Button btn = new Button("Return");
            win.add(btn, BorderLayout.NORTH);

            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent evt) {
                    exitFullScreen(currentGUI);
                }
            });

            try {
                screenPosition = ((Container)currentGUI.getFrame()).getLocation();
                // Enter full-screen mode
                gs.setFullScreenWindow(win);
                win.validate();
                
            } catch (final Error e) {
                currentGUI.showMessageDialog("Full screen mode not supported on this machine.\n"
                        + "JPedal will now exit "+e);

                Exit.exit(thumbnails, currentGUI, commonValues, decode_pdf, properties);
                // ...
            }// finally {
            // Exit full-screen mode
            //	gs.setFullScreenWindow(null);
            //}
        } else {

        }
    }

    private static void exitFullScreen(final GUIFactory currentGUI) {

        final Runnable doPaintComponent = new Runnable() {
            @Override
            public void run() {

                // Return to normal windowed mode
                final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                final GraphicsDevice gs = ge.getDefaultScreenDevice();
                gs.setFullScreenWindow(null);

                win.remove((JSplitPane)currentGUI.getFrame());

                if (currentGUI.getFrame() instanceof JFrame) {
                    ((JFrame) currentGUI.getFrame()).getContentPane().add((JSplitPane)currentGUI.getFrame(), BorderLayout.CENTER);
                    // Java 1.6 has issues with original pane remaining visible so show when fullscreen turned off
                    ((Container)currentGUI.getFrame()).setVisible(true);

                    //restore to last position which we saved on entering full screen
                    if (screenPosition != null) {
                        ((Container)currentGUI.getFrame()).setLocation(screenPosition);
                    }
                    screenPosition = null;
                } else {
                    ((Container)currentGUI.getFrame()).add((JSplitPane)currentGUI.getFrame(), BorderLayout.CENTER);
                }

                ((JSplitPane)currentGUI.getFrame()).invalidate();
                ((JSplitPane)currentGUI.getFrame()).updateUI();

                if (currentGUI.getFrame() instanceof JFrame) {
                    ((JFrame) currentGUI.getFrame()).getContentPane().validate();
                } else {
                    ((Container)currentGUI.getFrame()).validate();
                }

                win.dispose();
                win = null;

                //currentGUI.scaleAndRotate();
            }
        };

        SwingUtilities.invokeLater(doPaintComponent);

    }
}
