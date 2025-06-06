/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gui.attendance;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import java.awt.Font;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import model.Qube;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import java.util.logging.Logger;
import model.MySql;
import org.opencv.videoio.Videoio;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.User;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.objdetect.CascadeClassifier;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;
import static zmq.ZMQ.socket;

public class FaceRecognition extends javax.swing.JFrame {

    VideoCapture cap;
    Logger l = new Qube().setLogger("FaceRec.log");
    boolean canDo = true;
    boolean doIt = true;
    boolean endReading = false;
//    public static String recType = "Students";
    public static String recType = "Employees";
    private Integer camType = 0;
    String tableName;
    String id;
    String teacherId;
    String classId;
    String previouslyMarkedAtt = "";
    int tBRCount = 1;

//    private static String fileLocation = "pyScript/call1_2.py";
    private static String fileLocation = "pyScript/call.py";
    private static String tcpAddress = "tcp://localhost:5555";

    boolean stCam = true;
    boolean stCam1 = false;

    Thread t = new Thread(() -> {
        if (camType != -1) {
            startCamera();
        }
    });

    public FaceRecognition() {
        initComponents();
        developeLater();

    }

    public FaceRecognition(Integer cam) {
        initComponents();
        developeLater();

    }

    private void allowAttMarking() {
        if (!recType.equals("Students")) {
            jLabel10.setText("");
        }
        jComboBox1.setEnabled(false);
        if (recType.equals("Employees")) {
            jLabel8.setText(" ");
            jLabel13.setText(" ");

            if (User.getwTypeID() == 1 || User.getwTypeID() == 2) {
            } else {
                jButton1.setEnabled(false);
                jTextField1.setEnabled(false);
            }
        }

        jLabel12.setText(recType);
    }

    private void developeLater() {
        jLabel14.setVisible(false);
        jComboBox1.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(59, 102, 142), 3, true));

        jLabel5.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel5.setText("Name : ");

        jLabel6.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel6.setText("ID :");

        jLabel4.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel4.setText("Type :  ");

        jLabel2.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/icons/dasdsadsa.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText(" ");

        jLabel8.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel8.setText("Class Fee :");

        jTextField1.setFont(new java.awt.Font("Poppins", 1, 15)); // NOI18N
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jButton1.setText("Mark");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel9.setText("Enter Student Id  : ");

        jPanel5.setLayout(new java.awt.GridLayout(1, 0));

        jButton4.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jButton4.setText("Restart");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.add(jPanel6);

        jButton3.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jButton3.setText("Reset");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(11, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.add(jPanel7);

        jButton2.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jButton2.setText("Get Report");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.add(jPanel8);

        jLabel11.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel12.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLabel13.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 255, 0));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Paid");

        jComboBox1.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel14.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel14.setText("Change Resolutions :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jTextField1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton1))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 14, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(24, 24, 24))
        );

        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(59, 102, 142), 3, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Camara Screen");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6))
        );

        jTable1.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel3.setText("Attendance");

        jLabel10.setFont(new java.awt.Font("Poppins", 1, 15)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText(" ");

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 12)); // NOI18N
        jLabel15.setText(" ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 795, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel10))
                .addGap(22, 22, 22)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addGap(25, 25, 25))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    public void setData(String recType, Integer camType, String classId, String teacherId, String className, String teacherName) {
        this.camType = camType;
        this.recType = recType;
        this.classId = classId;
        this.teacherId = teacherId;

        if (recType.equals("Employees")) {
            tableName = "user";
        } else {
            tableName = "student";
        }

        jLabel10.setText(className + " - " + teacherName);
        jLabel12.setText(recType);
    }

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        if (camType != -1) {
            cap.release();
        }

    }//GEN-LAST:event_formWindowClosed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String persons_id = jTextField1.getText();

        if (persons_id.isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please enter id.", "No id entered.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            ResultSet rs = MySql.select("SELECT * FROM `" + tableName + "` WHERE `id` = '" + persons_id + "'; ");

            if (rs.next()) {
                markAttendance(persons_id);
            } else {
                JOptionPane.showMessageDialog(rootPane, "There is no Recode with this id", "Person not found", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            l.log(Level.WARNING, "While checking person's details id", ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if (recType.equals("Employees")) {
            jLabel6.setText("NIC");
        }
        t.start();

        allowAttMarking();

        try {
            setTableData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_formWindowOpened

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        Qube.typeOnlyDigit(evt);
    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        if (evt.getKeyCode() == 10) {
            jButton1.doClick();
            jTextField1.grabFocus();
        }
    }//GEN-LAST:event_jTextField1KeyPressed

    public static void main(String args[]) {

        try {
            FlatLaf.setup(new FlatMacDarkLaf());
        } catch (Exception e) {
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FaceRecognition().setVisible(true);
            }
        });
    }

    static {
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Path path = Paths.get("x64/opencv_java4100.dll");
        String lib = String.valueOf(path.toAbsolutePath());
        System.load(lib);

    }

    Thread getPyReady = new Thread(() -> {

        Path path = Paths.get(fileLocation);
        fileLocation = String.valueOf(path.toAbsolutePath());

        Process p = null;
        try {
            ProcessBuilder pb1 = new ProcessBuilder("python", fileLocation, tcpAddress);
            pb1.redirectErrorStream(true);
            p = pb1.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String results;
            while ((results = in.readLine()) != null) {
                if (endReading) {
                    System.out.println("Reading ended..");
                    break;
                }

                if (results.contains("Match found")) {
                    String id = results.split(":")[0].split(":")[0].split("-")[1].split(" with distance")[0].replaceAll("\\s", "");
                    if (!previouslyMarkedAtt.equals(id)) {
//                        JOptionPane.showMessageDialog(this, id);
                        setResults("Match Found. Marking attendance.......");
                        markAttendance(id);
                    }
                } else if (results.equals("Listening on port tcp://localhost:5555")) {
                    doIt = false;
                } else {
                    setResults(results);
                }
                System.out.println(results);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
    });

//    @SuppressWarnings("deprecation")
    private void startCamera() {
        jLabel1.setText("");

        try (ZContext context = new ZContext(); @SuppressWarnings("deprecation") ZMQ.Socket socket = context.createSocket(ZMQ.PUSH)) {
            socket.connect(tcpAddress);

            cap = new VideoCapture(camType);
            cap.set(Videoio.CAP_PROP_FRAME_WIDTH, 1920);
            cap.set(Videoio.CAP_PROP_FRAME_HEIGHT, 1080);

            Mat frame = new Mat();
            Mat displayFrame = new Mat();

            MatOfByte matOfByte = new MatOfByte();

            if (cap.isOpened()) {
                int i = 0;
//                killConnection();
                getPyReady.start();
                while (canDo) {
                    i++;

                    if (!cap.read(frame)) {
                        System.out.println("Main Loop ended..");

                        canDo = false;
                        stCam1 = true;
                        endReading = true;
                        break;
                    }

                    Thread t12 = new Thread(() -> {
                        while (doIt) {
                            try {
                                Thread.sleep(400);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                        while (true) {
                            if (stCam1) {
                                System.out.println("MSG sending ended..");
                                killConnection();
                                break;
                            }

                            Imgcodecs.imencode(".png", frame, matOfByte);
                            byte[] byteArray = matOfByte.toArray();

                            if (socket != null && !context.isClosed()) {
                                ZMsg msg = new ZMsg();
                                msg.add(byteArray);
                                msg.send(socket);
                            } else {
                                System.out.println("Socket is not open. Cannot send message.");
                                canDo = false;
                                stCam1 = true;
                                endReading = true;
                                break;
                            }

                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }

                    });

                    if (stCam) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        t12.start();
                        stCam = false;
                    }

                    Imgproc.resize(frame, displayFrame, new Size(960, 540));
                    BufferedImage image = matToBufferedImage(displayFrame);
                    SwingUtilities.invokeLater(() -> jLabel1.setIcon(new ImageIcon(image.getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), image.SCALE_SMOOTH))));

                    if (i == 5) {
                        i = 0;
                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void killConnection() {
//        Path path = Paths.get("pyScript\\killConnection.py");
//        fileLocation = String.valueOf(path.toAbsolutePath());
//
//        Process p = null;
//        try {
//            new ProcessBuilder("python", fileLocation).start();
////            pb1.redirectErrorStream(true);
//            
////            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
////            String results;
////            while ((results = in.readLine()) != null) {
////               
////                System.out.println(results);
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (p != null) {
//                p.destroy();
//            }
//        }
    }

    @SuppressWarnings("unchecked")
    private void markAttendanceStudent(String id) throws Exception {
        Date d = new Date();
        String dat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);

        ResultSet rs = MySql.select("SELECT * FROM `attendance` WHERE `DAT` LIKE '" + new SimpleDateFormat("yyyy-MM-dd").format(d) + "%'"
                + " AND `classes_id` = '" + classId + "' AND `student_id` = '" + id + "';");

        if (!rs.next()) {
            System.out.println(classId);
            MySql.iud("INSERT INTO `attendance` (`DAT`,`student_id`,`classes_id`) VALUES (?, ?, ?);", new Object[]{dat, id, classId});
            previouslyMarkedAtt = id;
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

            Vector v = new Vector();

            v.add(tBRCount);
            v.add(jLabel7.getText());
            v.add(jLabel11.getText());
            v.add(dat);

            dtm.addRow(v);
            tBRCount++;
        }
    }

    @SuppressWarnings("unchecked")
    private void markAttendanceExam(String id) throws Exception {
        Date d = new Date();
        String dat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(d);

        ResultSet rs = MySql.select("SELECT * FROM `exam_attendance` WHERE `arrival` LIKE '" + new SimpleDateFormat("yyyy-MM-dd").format(d) + "%'"
                + " AND `exams_id` = '" + classId + "' AND `student_id` = '" + id + "';");

        if (!rs.next()) {
            MySql.iud("INSERT INTO `exam_attendance` (`arrival`,`departure`,`student_id`,`exams_id`) VALUES (?, ?, ?, ?);", new Object[]{dat, dat, id, classId});
            previouslyMarkedAtt = id;
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

            Vector v = new Vector();
            v.add(tBRCount);
            v.add(jLabel7.getText());
            v.add(jLabel11.getText());
            v.add(dat);
            v.add("Not Leaved");

            dtm.addRow(v);
            tBRCount++;
        } else {
            MySql.iud("UPDATE `exam_attendance` SET `departure` = ? WHERE `id` = ?;", new Object[]{dat, rs.getString("exam_attendance.id")});
            previouslyMarkedAtt = id;
            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();

            Vector v = new Vector();

            v.add(tBRCount);
            v.add(jLabel7.getText());
            v.add(jLabel11.getText());
            v.add(rs.getString("arrival"));
            v.add(dat);

            dtm.addRow(v);
            tBRCount++;
        }
    }

    @SuppressWarnings("unchecked")
    private void markAttendanceUser(String id) throws Exception {
        Date d = new Date();
        String time = new SimpleDateFormat("HH:mm:ss").format(d);

        ResultSet rs = MySql.select("SELECT * FROM `user_attendance` WHERE `date` = '" + new SimpleDateFormat("yyyy-MM-dd").format(d) + "'"
                + " AND `user_id` = '" + id + "';");

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        Vector v = new Vector();

        v.add(tBRCount);
        v.add(jLabel7.getText());
        v.add(jLabel11.getText());

        if (!rs.next()) {
            MySql.iud("INSERT INTO `user_attendance` (`date`,`user_id`,`arrival_at`,`leave_at`) VALUES (?, ?, ?, ?);",
                    new Object[]{new SimpleDateFormat("yyyy-MM-dd").format(d), id, time, time});
            previouslyMarkedAtt = id;
            v.add(time);
            v.add(time);
        } else {
            LocalTime time1 = LocalTime.parse(rs.getString("arrival_at"));
            LocalTime time2 = LocalTime.parse(rs.getString("leave_at"));

            Duration duration = Duration.between(time1, time2);

            if (duration.toHours() <= 1) {
                MySql.iud("UPDATE `user_attendance` SET `leave_at` = '" + time + "' WHERE `id` = '" + rs.getString("id") + "'; ");
                previouslyMarkedAtt = id;
                v.add(rs.getString("arrival_at"));
                v.add(time);
            }
        }

        dtm.addRow(v);
        tBRCount++;
    }

    private String isPaid(String id) throws Exception {

        ResultSet rs = MySql.select("SELECT * FROM `stuPayments` WHERE `student_id` = '" + id + "' AND `DAT` LIKE '" + new SimpleDateFormat("yyyy-MM").format(new Date()) + "%'; ");

        if (rs.next()) {
            return "Paid";
        }
        return "Unpaid";

    }

    private void markAttendance(String id) {

        if (recType.equals("Employees")) {
            try {
                ResultSet rs = MySql.select("SELECT * FROM `user` WHERE `id` = '" + id + "';");

                if (rs.next()) {
                    jLabel11.setText(rs.getString("fname") + " " + rs.getString("lname"));
                    jLabel7.setText(rs.getString("nic"));
//                    jLabel13.setText(isPaid(rs.getString("id")));
                    markAttendanceUser(rs.getString("id"));
                    jTextField1.setText("");
                    jTextField1.grabFocus();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            try {
                ResultSet rs = MySql.select("SELECT * FROM `student` WHERE `id` = '" + id + "';");
                if (rs.next()) {
                    jLabel11.setText(rs.getString("fname") + " " + rs.getString("lname"));
                    jLabel7.setText(rs.getString("id"));

                    if (recType.equals("Examinations")) {
                        markAttendanceExam(rs.getString("id"));
                    } else {
                        jLabel13.setText(isPaid(rs.getString("id")));
                        markAttendanceStudent(rs.getString("id"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setResults(String x) {
        jLabel15.setText(x);
    }

    @SuppressWarnings("unchecked")
    private void setTableData() throws Exception {

        DefaultTableModel dtm = setTableHeaders();
        dtm.setRowCount(0);

        Date d = new Date();

        if (recType.equals("Employees")) {
            ResultSet rs = MySql.select("SELECT * FROM `user_attendance` INNER JOIN `user` ON `user_attendance`.`user_id` = `user`.`id`"
                    + " WHERE `date` = '" + new SimpleDateFormat("yyyy-MM-dd").format(d) + "' ;");

            while (rs.next()) {
                Vector v = new Vector();

                v.add(tBRCount);
                v.add(rs.getString("NIC"));
                v.add(rs.getString("fname") + " " + rs.getString("lname"));
                v.add(rs.getString("arrival_at"));
                v.add(rs.getString("leave_at"));

                dtm.addRow(v);
                tBRCount++;
            }
        } else if (recType.equals("Examinations")) {
            ResultSet rs = MySql.select("SELECT * FROM exam_attendance INNER JOIN student ON student.id = exam_attendance.student_id"
                    + " WHERE `arrival` LIKE '" + new SimpleDateFormat("yyyy-MM-dd").format(d) + "%' AND `exams_id` = '" + classId + "';");

            while (rs.next()) {
                Vector v = new Vector();

                v.add(tBRCount);
                v.add(rs.getString("student.id"));
                v.add(rs.getString("fname") + " " + rs.getString("lname"));
                v.add(rs.getString("arrival"));
                v.add(rs.getString("departure"));

                dtm.addRow(v);
                tBRCount++;
            }
        } else {
            ResultSet rs = MySql.select("SELECT * FROM `attendance` INNER JOIN `student` ON `student`.`id` = `attendance`.`student_id`"
                    + " WHERE `DAT` LIKE '" + new SimpleDateFormat("yyyy-MM-dd").format(d) + "%' AND `classes_id` = '" + classId + "'");

            while (rs.next()) {
                Vector v = new Vector();

                v.add(tBRCount);
                v.add(rs.getString("id"));
                v.add(rs.getString("fname") + " " + rs.getString("lname"));
                v.add(rs.getString("DAT"));

                dtm.addRow(v);
                tBRCount++;
            }
        }

    }

    private DefaultTableModel setTableHeaders() {
        Font popinsFont = new Font("Popins", Font.BOLD, 15);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setFont(popinsFont);
        headerRenderer.setHorizontalAlignment(JLabel.LEFT);

        DefaultTableModel tb = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                int[] nonEditableColumns = {0, 1};
                for (int col : nonEditableColumns) {
                    if (column == col) {
                        return false;
                    }
                }
                return true;
            }
        };

        tb.setColumnCount(0);

        if (recType.equals("Employees")) {
            tb.addColumn("No.");
            tb.addColumn("NIC");
            tb.addColumn("Name");
            tb.addColumn("Arrived Time");
            tb.addColumn("Leaved Time");
        } else if (recType.equals("Examinations")) {
            tb.addColumn("No.");
            tb.addColumn("ID");
            tb.addColumn("Name");
            tb.addColumn("Arrived Time");
            tb.addColumn("Leaved Time");
        } else {
            tb.addColumn("No.");
            tb.addColumn("Student ID");
            tb.addColumn("Student Name");
            tb.addColumn("Date and Time");
        }

        jTable1.setModel(tb);
        jTable1.getTableHeader().setDefaultRenderer(headerRenderer);

        return tb;
    }

    private BufferedImage matToBufferedImage(Mat mat) {
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

    public void setRecData(String name, String type, String id) {
        jLabel12.setText(name);
        jLabel11.setText(type);
        jLabel13.setText(id);

    }
}
