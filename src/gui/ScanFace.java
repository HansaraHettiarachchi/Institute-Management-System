/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package gui;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import org.opencv.videoio.VideoCapture;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import model.Qube;
import model.RightAlignedComboBoxRenderer;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

/**
 *
 * @author ASUS
 */
public class ScanFace extends javax.swing.JDialog {

    VideoCapture cap;
    Thread t;
    Logger l = new Qube().setLogger("scanFace.log");
    boolean canDo = true;
    private CascadeClassifier faceCascade;
    String type;
    String id;
    private HashMap<String, Integer> camData = new HashMap<>();
    private Integer camType = 0;
    private boolean autoClose = false;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public ScanFace(java.awt.Frame parent, boolean modal, String type, String id, boolean autoClose) {
        super(parent, modal);
        initComponents();
        jLabel4.setVisible(false);
        jLabel5.setVisible(false);
        jProgressBar1.setVisible(false);
        this.type = type;
        this.id = id;
        this.autoClose = autoClose;
        jComboBox1.setRenderer(new RightAlignedComboBoxRenderer());
        try {
            loadData();
        } catch (Exception ex) {
            l.log(Level.WARNING, "While Setting cam Data", ex);
        }
    }

    private void loadData() throws Exception {
        ProcessBuilder pb1 = new ProcessBuilder("python", "pyScript/findDetectedCamS.py");

        pb1.redirectErrorStream(true);
        Process p = pb1.start();
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String results;
        Vector<String> v = new Vector<>();

        int i = 0;
        while ((results = in.readLine()) != null) {
            v.add(results);
            camData.put(results, i);
            i++;
        }
        jComboBox1.setModel(new DefaultComboBoxModel<>(v));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel1.setText("Scan Face");

        jLabel2.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/faceRec.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 0, 51));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Scan Successfull");

        jButton1.setFont(new java.awt.Font("Poppins", 1, 15)); // NOI18N
        jButton1.setText("START SCANING");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jProgressBar1.setFont(new java.awt.Font("Poppins", 1, 11)); // NOI18N
        jProgressBar1.setStringPainted(true);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/loader-ezgif.com-crop.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 255), 2, true));

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Start Scaning");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 735, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jComboBox1.setFont(new java.awt.Font("Poppins", 1, 10)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private BufferedImage matToBufferedImage(Mat mat) throws Exception {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] buffer = new byte[bufferSize];
        mat.get(0, 0, buffer);
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(buffer, 0, targetPixels, 0, buffer.length);
        return image;
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        String text = jButton1.getText();

        if (t == null) {
            t = new Thread(() -> {
                startCamera();
            });
        }

        if ("START SCANING".equals(text)) {

            canDo = true;
            camType = camData.get(String.valueOf(jComboBox1.getSelectedItem()));
            t.start();
            jLabel2.setIcon(new ImageIcon("src/gui/icons/faceRec.gif"));
            jButton1.setText("STOP SCANING");
            jProgressBar1.setValue(0);
            jProgressBar1.setVisible(false);
            jLabel4.setVisible(false);
            jLabel5.setVisible(false);
            jLabel4.setForeground(new Color(255, 0, 51));

        } else if (text.equals("STOP SCANING")) {

            canDo = false;
            t.interrupt();
            cap.release();
            jLabel2.setIcon(new ImageIcon("src/gui/icons/faceRec.png"));
            t = null;
            jButton1.setText("START SCANING");

        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void startCamera() {
        jLabel3.setText("");

        try {
            cap = new VideoCapture(camType);
            cap.set(Videoio.CAP_PROP_FRAME_WIDTH, 2560);
            cap.set(Videoio.CAP_PROP_FRAME_HEIGHT, 1440);

            Mat frame = new Mat();
            Mat displayFrame = new Mat();
            BlockingQueue<Mat> frameQueue = new LinkedBlockingQueue<>();

            if (cap.isOpened()) {
                int fourcc = VideoWriter.fourcc('a', 'v', 'c', '1');
                int fps = 30;
                Size frameSize = new Size(cap.get(Videoio.CAP_PROP_FRAME_WIDTH), cap.get(Videoio.CAP_PROP_FRAME_HEIGHT));
                Size displaySize = new Size(640, 480);  // Lower resolution for display

                String outputPath = "pyScript/images/" + type + "/" + id + ".mp4";
                new File(outputPath).getParentFile().mkdirs();

                VideoWriter videoWriter = new VideoWriter(outputPath, fourcc, fps, frameSize, true);

                if (!videoWriter.isOpened()) {
                    jLabel4.setText("Scan unsuccessfull.");
                    return;
                }

                Thread captureThread = new Thread(() -> {
                    long startTime = System.currentTimeMillis();
                    long durationMillis = 4000; // 6 seconds

                    while (canDo && (System.currentTimeMillis() - startTime) < durationMillis) {
                        if (!cap.read(frame)) {
                            break;
                        } else {
                            if (frame.empty()) {
                                jLabel4.setText("Scan unsuccessfull.");
                                continue;
                            }

                            try {
                                frameQueue.put(frame.clone());
                                Imgproc.resize(frame, displayFrame, displaySize); // Downscale frame for display
                                BufferedImage image = matToBufferedImage(displayFrame);
                                SwingUtilities.invokeLater(() -> jLabel3.setIcon(new ImageIcon(image.getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), image.SCALE_SMOOTH))));
                            } catch (Exception e) {
                                jLabel4.setText("Scan unsuccessfull.");
                            }
                        }
                    }
                    canDo = false;
                });

                Thread writerThread = new Thread(() -> {
                    try {
                        while (canDo || !frameQueue.isEmpty()) {
                            Mat queuedFrame = frameQueue.take(); // Get frame from the queue
                            if (!queuedFrame.empty()) {
                                videoWriter.write(queuedFrame);
                            } else {
                                jLabel4.setText("Scan unsuccessfull.");
                            }
                        }
                    } catch (Exception e) {
                        jLabel4.setText("Scan unsuccessfull.");
                    } finally {
                        videoWriter.release();
                        cap.release();
                        jLabel4.setVisible(true);
                        jLabel4.setText("VERIFYING");
                        jLabel5.setVisible(true);
                        jLabel4.setForeground(new Color(0, 204, 0));
                        encodeData(outputPath);
                    }
                });

                captureThread.start();
                writerThread.start();

                captureThread.join();
                writerThread.join();

            } else {
                jLabel4.setText("Error: Camera not opened");
            }
        } catch (Exception e) {
            jLabel4.setText("Scan unsuccessfull.");
        }
        jLabel4.setVisible(true);
    }

    private void encodeData(String outputParth) {
        ProcessBuilder pb1 = new ProcessBuilder("python", "pyScript/saveImageData1_1.py", outputParth, "pyScript/pickles/main/" + id + ".pkl");

        pb1.redirectErrorStream(true);
        Process p;
        try {
            p = pb1.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));

            new Thread(() -> {
                String results;
                jProgressBar1.setVisible(true);

                try {
                    while ((results = in.readLine()) != null) {

                        if (results.startsWith("Progress:")) {
                            String progressStr = results.split(":")[1].trim().replace("%", "");
                            int progress = (int) Float.parseFloat(progressStr);
                            SwingUtilities.invokeLater(() -> {
                                jProgressBar1.setValue(progress);
                            });
                        } else if (results.equals("success")) {
                            SwingUtilities.invokeLater(() -> {
                                jProgressBar1.setVisible(false);
                                jButton1.doClick();
                                jLabel4.setText("Scan Successful.");
                                jLabel4.setForeground(new Color(0, 204, 0));
                                jLabel5.setVisible(false);
                            });
                            new File(outputParth).delete();

                            if (autoClose) {
                                this.dispose();
                            }
                        } else if (results.equals("unsuccessful")) {
                            SwingUtilities.invokeLater(() -> {
                                jProgressBar1.setVisible(false);
                                jButton1.doClick();
                                jLabel4.setText("Scan Unsuccessful.");
                                jLabel4.setForeground(new Color(255, 0, 51));
                                jLabel5.setVisible(false);
                            });
                        }
                    }
                } catch (IOException e) {
                    jLabel4.setText("Scan unsuccessful.");
                }
            }).start();

            // Wait for the process to complete
            p.waitFor();

        } catch (IOException | InterruptedException ex) {
            jLabel4.setText("Scan unsuccessful.");
        }
    }

    private String createFolder(String folderName) {

        String folderPath = "pyScript/images/" + folderName;

        File directory = new File(folderPath);
        directory.mkdirs();

        return folderPath;
    }

    public static void main(String args[]) {
        try {
            FlatLaf.setup(new FlatMacDarkLaf());
        } catch (Exception e) {
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ScanFace dialog = new ScanFace(new javax.swing.JFrame(), true, "Users", "7", false);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
