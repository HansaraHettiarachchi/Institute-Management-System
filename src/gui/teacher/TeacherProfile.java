/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package gui.teacher;

import gui.classes.ClassProfile;
import gui.student.Addresses;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.MySql;
import model.Qube;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author nuwan
 */
public class TeacherProfile extends javax.swing.JDialog {

    private HashMap<Integer, Object> GenderMap = new HashMap<>();
    private HashMap<Integer, Object> paymentTypeMap = new HashMap<>();

    static int teacherId;

    String TNic;
    String filepath;
    String TName;
    String Cid;
    String mob;
    String gender;
    String paymentType;
    String bankName;
    Integer AccountNo;
    
    private String insert_id;

    public TeacherProfile(java.awt.Frame parent, boolean modal, String Tnic) {
        super(parent, modal);
        initComponents();
        this.TNic = Tnic;

        onLoad();
        jTextField4.setEnabled(false);
        jTextField6.setEnabled(false);
        jTextField5.setEnabled(false);
        loadData();

        Font tableFont = new Font("Poppins", Font.PLAIN, 12);
        jTable1.setFont(tableFont);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = jTable1.getTableHeader();
        Font headerFont = new Font("Poppins", Font.BOLD, 14);
        header.setFont(headerFont);

    }

    @SuppressWarnings({"unchecked", "unchecked"})
    private void onLoad() {

        HashMap gM = new Qube().getComboData("gender", "", "name");
        jComboBox1.setModel((ComboBoxModel<String>) gM.get(1));
        GenderMap = (HashMap<Integer, Object>) gM.get(2);

        HashMap pM = new Qube().getComboData("paymenttype", "", "name");
        jComboBox2.setModel((ComboBoxModel<String>) pM.get(1));
        paymentTypeMap = (HashMap<Integer, Object>) pM.get(2);

    }

    private void loadData() {
        try {
            ResultSet resultSet = MySql.select("SELECT \n"
                    + "    t.id AS teacher_id,\n"
                    + "    t.name AS teacher_name,\n"
                    + "    t.nic AS teacher_nic,\n"
                    + "    t.mobile AS teacher_mob,\n"
                    + "    g.name AS gender_name,\n"
                    + "	 pt.name AS paymentType_name,\n"
                    + "    s.name AS status_name\n"
                    + "FROM \n"
                    + "    teachers t\n"
                    + "INNER JOIN \n"
                    + "    status s ON t.status_id = s.id\n"
                    + "INNER JOIN \n"
                    + "    gender g ON t.gender_id = g.id\n"
                    + "INNER JOIN \n"
                    + "    paymenttype pt ON t.paymentType_id = pt.id\n"
                    + "WHERE \n"
                    + "    t.nic = '" + this.TNic + "';");

            if (resultSet.next()) {
                teacherId = resultSet.getInt("teacher_id");

                jTextField2.setText(resultSet.getString("teacher_name"));
                jTextField4.setText(resultSet.getString("teacher_nic"));
                jTextField3.setText(resultSet.getString("teacher_mob"));
                jComboBox1.setSelectedItem(resultSet.getString("gender_name"));
                jComboBox2.setSelectedItem(resultSet.getString("paymentType_name"));

                loadProfileImage(this.TNic);
                getBankDetails(this.TNic);
                loadTeacher(this.TNic);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBankDetails(String TNic) {
        if ("Bank Transfer".equals(jComboBox2.getSelectedItem())) {
            jTextField6.setEnabled(true);
            jTextField5.setEnabled(true);

            try {
                ResultSet resultSet = MySql.select("SELECT \n"
                        + "    t.id AS teacher_id,\n"
                        + "    t.name AS teacher_name,\n"
                        + "    t.nic AS teacher_nic,\n"
                        + "    a.bank AS bank_name,\n"
                        + "    a.accNumber AS account_number\n"
                        + "FROM \n"
                        + "    teachers t\n"
                        + "INNER JOIN \n"
                        + "    adetails a ON t.aDetails_id = a.id\n"
                        + "WHERE \n"
                        + "    t.nic = '" + TNic + "';");

                if (resultSet.next()) {
                    jTextField6.setText(resultSet.getString("bank_name"));
                    jTextField5.setText(resultSet.getString("account_number"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void loadTeacher(String TNic) {
        try {

            ResultSet resultSet = MySql.select("SELECT \n"
                    + "    c.id AS class_id,\n"
                    + "    c.name AS class_name,\n"
                    + "    c.fee AS class_fee,\n"
                    + "    c.tFee AS teacher_fee,\n"
                    + "    c.tPeriod AS duration,\n"
                    + "    d.name AS day_name,\n"
                    + "    st.sTime AS start_time,\n"
                    + "    cr.name AS venue\n"
                    + "FROM \n"
                    + "    classes c\n"
                    + "INNER JOIN \n"
                    + "    teachers t ON c.teachers_id = t.id\n"
                    + "INNER JOIN \n"
                    + "    day d ON c.day_id = d.id\n"
                    + "INNER JOIN \n"
                    + "    starttime st ON c.startTime_id = st.id\n"
                    + "INNER JOIN \n"
                    + "    classrooms cr ON c.classRooms_id = cr.id\n"
                    + "WHERE \n"
                    + "    t.nic = '" + TNic + "';");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("class_name"));
                vector.add(resultSet.getString("start_time"));
                vector.add(resultSet.getString("day_name"));
                vector.add(resultSet.getString("venue"));

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setTeacherStatus(TNic);
    }

    private void loadProfileImage(String NIC) {
        try {
            String query = "SELECT t.nic, p.location FROM teachers t JOIN teacherpimg p ON t.id = p.teachers_id WHERE t.nic = '" + NIC + "';";

            ResultSet resultSet = MySql.select(query);

            if (resultSet.next()) {
                filepath = resultSet.getString("location");
                String imagePath = resultSet.getString("location");

                ImageIcon icon = new ImageIcon(imagePath);

                Image img = icon.getImage();
                Image resizedImg = img.getScaledInstance(jLabel3.getWidth(), jLabel3.getHeight(), Image.SCALE_SMOOTH);
                icon = new ImageIcon(resizedImg);

                jLabel3.setText("");
                jLabel3.setIcon(icon);
            } else {
                System.out.println("No profile image found for Teacher ID: " + NIC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTeacherStatus(String TNic) {
        try {
            String query = "SELECT s.name FROM teachers t JOIN status s ON t.status_id = s.id WHERE t.nic = ?";
            Object[] params = {TNic};

            ResultSet rs = MySql.select(query, params);

            if (rs.next()) {
                String status = rs.getString("name");

                if (status.equalsIgnoreCase("Active")) {
                    jLabel16.setText("ACTIVE");
                    jLabel16.setForeground(new Color(73, 192, 99));
                } else if (status.equalsIgnoreCase("Deactive")) {
                    jLabel16.setText("DEACTIVE");
                    jLabel16.setForeground(Color.red);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Teacher not found with the provided NIC.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An error occurred while fetching the teacher's status: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validation() {

        TName = jTextField2.getText();
        TNic = jTextField4.getText();
        mob = jTextField3.getText();
        bankName = jTextField6.getText();

        gender = String.valueOf(jComboBox1.getSelectedItem());

        if (TName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter Name", "Warning", JOptionPane.WARNING_MESSAGE);

        } else if (!TName.matches("^[a-zA-Z\\s]*$")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid Name", "Warning", JOptionPane.WARNING_MESSAGE);

        } else if (TNic.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please Enter NIC Number", "Warning", JOptionPane.WARNING_MESSAGE);

        } else if (!TNic.matches("^(\\d{12}|\\d{9}[Vv])$")) {
            JOptionPane.showMessageDialog(this, "Please Enter Valid NIC Number", "Warning", JOptionPane.WARNING_MESSAGE);

        } else if (gender.equals("SELECT")) {
            JOptionPane.showMessageDialog(this, "Please Select Gender", "Warning", JOptionPane.WARNING_MESSAGE);

        } else if (mob.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter Student mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);

        } else if (!mob.matches("^07[01245678]{1}[0-9]{7}$")) {
            JOptionPane.showMessageDialog(this, "Please enter valid mobile Number", "Warning", JOptionPane.WARNING_MESSAGE);

        } else if (jLabel3.getIcon() == null) {
            JOptionPane.showMessageDialog(this, "Please upload a profile image.", "Warning", JOptionPane.WARNING_MESSAGE);

        } else if ("Bank Transfer".equals(jComboBox2.getSelectedItem())) {
            jTextField6.setEnabled(true);
            jTextField5.setEnabled(true);
            if (bankName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Bank Name", "Warning", JOptionPane.WARNING_MESSAGE);
                return false;
            } else if (!bankName.matches("^[a-zA-Z ]{3,50}$")) {
                JOptionPane.showMessageDialog(this, "Invalid Bank Name. Please enter only alphabets with a valid length (3-50).", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            try {
                String accountNoText = jTextField5.getText();

                if (!accountNoText.isEmpty()) {
                    AccountNo = Integer.parseInt(accountNoText);
                } else {
                    JOptionPane.showMessageDialog(this, "Account Number is empty", "Warning", JOptionPane.WARNING_MESSAGE);
                    return false;
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Account Number. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Poppins", 1, 24)); // NOI18N
        jLabel1.setText("Teacher Profile");

        jPanel4.setLayout(new java.awt.GridLayout(1, 0, 0, 10));

        jLabel4.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel4.setText("First Name");

        jTextField2.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel6.setText("NIC");

        jTextField4.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel10.setText("Mobile");

        jTextField3.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel11.setText("Gender");

        jComboBox1.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel9.setText("Payment Type");

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));

        jTextField5.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });

        jTextField6.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel12.setText("Bank Name");

        jLabel17.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel17.setText("Account No.");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(15, 15, 15))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );

        jButton3.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jButton3.setText("Address View");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jButton4.setText("Class Profile");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jButton5.setText("Free Cards");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel10)
                        .addGap(177, 177, 177))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(jComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(0, 16, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton3)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4))
                                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(10, 10, 10))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addGap(6, 6, 6)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.add(jPanel3);

        jLabel15.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel15.setText("Class Schedule");

        jTable1.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Class", "Time", "Date", "Vanue"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 432, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel2);

        jButton1.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/teacher/icons/icons8-send-30.png"))); // NOI18N
        jButton1.setText("UPDATE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jButton2.setText("RESET");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPanel5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(102, 102, 102), 3, true));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel3.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Profile Image");
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Poppins", 1, 16)); // NOI18N
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("20321542");

        jLabel16.setFont(new java.awt.Font("Poppins", 1, 15)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(73, 192, 99));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("ACTIVE");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(40, 40, 40));

        jPanel7.setBackground(new java.awt.Color(40, 40, 40));
        jPanel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel7MouseExited(evt);
            }
        });

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gui/student/icons/icons8-payment-history-60.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Poppins", 1, 14)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("History");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(0, 0, 0))
        );

        jPanel9.setBackground(new java.awt.Color(40, 40, 40));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 82, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel13.setBackground(new java.awt.Color(40, 40, 40));

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 82, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(35, 35, 35))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 407, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        try {
            if (validation()) {
                ResultSet resultSet = MySql.select("SELECT id FROM teachers WHERE nic = ?", new Object[]{TNic});
                boolean canUpdate = false;
                int teacherId = -1;

                if (resultSet.next()) {
                    teacherId = resultSet.getInt("id");
                    canUpdate = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Teacher not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (canUpdate) {
                    MySql.iud("UPDATE teachers SET name = ?, mobile = ?, gender_id = ? WHERE id = ?",
                            new Object[]{TName, mob, GenderMap.get(gender), teacherId});

                    String selectedPaymentType = String.valueOf(jComboBox2.getSelectedItem());

                    String paymentTypeIdStr = String.valueOf(paymentTypeMap.get(selectedPaymentType));
                    Integer paymentTypeId = Integer.parseInt(paymentTypeIdStr);

                    if (paymentTypeId == null) {
                        JOptionPane.showMessageDialog(this, "Invalid Payment Type Selected!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ResultSet rs = MySql.select("SELECT paymentType_id, aDetails_id FROM teachers WHERE id = ?", new Object[]{teacherId});
                    Integer currentPaymentTypeId = null;
                    Integer aDetailsId = null;

                    if (rs.next()) {
                        currentPaymentTypeId = rs.getInt("paymentType_id");
                        aDetailsId = rs.getInt("aDetails_id");
                    }

                    if ("Cash".equals(selectedPaymentType)) {
                        MySql.iud("UPDATE teachers SET paymentType_id = ?, aDetails_id = NULL WHERE id = ?",
                                new Object[]{paymentTypeId, teacherId});

                        jTextField6.setEnabled(false);
                        jTextField5.setEnabled(false);
                        jTextField6.setText("");
                        jTextField5.setText("");

                    } else if ("Bank Transfer".equals(selectedPaymentType)) {
                        String bankName = jTextField6.getText().trim();
                        String accNumber = jTextField5.getText().trim();

                        if (bankName.isEmpty() || accNumber.isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please enter both Bank Name and Account Number.", "Warning", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        if (currentPaymentTypeId == null || !currentPaymentTypeId.equals(paymentTypeId)) {
                            MySql.iud("INSERT INTO adetails (bank, accNumber) VALUES (?, ?)", new Object[]{bankName, accNumber});

                            ResultSet newIdResult = MySql.select("SELECT LAST_INSERT_ID() AS newId");
                            if (newIdResult.next()) {
                                int newAdetailsId = newIdResult.getInt("newId");
                                MySql.iud("UPDATE teachers SET paymentType_id = ?, aDetails_id = ? WHERE id = ?",
                                        new Object[]{paymentTypeId, newAdetailsId, teacherId});
                            } else {
                                JOptionPane.showMessageDialog(this, "Failed to insert bank details.", "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        } else {
                            if (aDetailsId != null && aDetailsId > 0) {
                                MySql.iud("UPDATE adetails SET bank = ?, accNumber = ? WHERE id = ?",
                                        new Object[]{bankName, accNumber, aDetailsId});
                            } else {
                                JOptionPane.showMessageDialog(this, "No existing bank details found. Please reselect the payment type.",
                                        "Error", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                        }

                        jTextField6.setEnabled(true);
                        jTextField5.setEnabled(true);
                    }

                    String extention = FilenameUtils.getExtension(filepath);
                    String newImagePath = "src/profileImages/" + Qube.randomString() + "_" + teacherId + "." + extention;

                    try {
                        BufferedImage originalImage = ImageIO.read(new File(filepath));
                        ImageIO.write(originalImage, extention, new File(newImagePath));

//                        MySql.iud("INSERT INTO `studentpimg` (`location`,`student_id`) VALUES ('" + newImagePath + "','" + sid + "');");
                        int response = JOptionPane.showConfirmDialog(
                                this,
                                "Do you want to update the profile image?",
                                "Confirm Update",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (response == JOptionPane.YES_OPTION) {
                            MySql.iud("UPDATE teacherpimg AS tpi\n"
                                    + "JOIN teachers AS t ON tpi.teachers_id = t.id\n"
                                    + "SET tpi.location = '" + newImagePath + "'\n"
                                    + "WHERE t.id = '" + teacherId + "';");

                        } else {
                            JOptionPane.showMessageDialog(this, "Update canceled.", "Information", JOptionPane.INFORMATION_MESSAGE);
                        }

                    } catch (Exception e) {
//                    l.log(Level.WARNING, "While moving image path.", e);
                        e.printStackTrace();
                    }

                    JOptionPane.showMessageDialog(this, "Successfully updated.", "Update Successful", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating teacher details: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked

        if (evt.getClickCount() == 2) {
            try {
                String currentStatus = jLabel16.getText();
                String newStatus;
                int newStatusId;

                if (currentStatus.equals("ACTIVE")) {
                    newStatus = "DEACTIVE";
                    newStatusId = 2;
                    jLabel16.setText("DEACTIVE");
                    jLabel16.setForeground(Color.red);
                } else {
                    newStatus = "ACTIVE";
                    newStatusId = 1;
                    jLabel16.setText("ACTIVE");
                    jLabel16.setForeground(new Color(73, 192, 99));
                }

                String query = "UPDATE teachers SET status_id = ? WHERE nic = ?";
                Object[] params = {newStatusId, TNic};

                MySql.iud(query, params);

            } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_jLabel16MouseClicked

    private void jPanel7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseEntered

    }//GEN-LAST:event_jPanel7MouseEntered

    private void jPanel7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseExited

    }//GEN-LAST:event_jPanel7MouseExited

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        if ("Bank Transfer".equals(jComboBox2.getSelectedItem())) {
            jTextField6.setEnabled(true);
            jTextField5.setEnabled(true);
        } else {
            jTextField6.setEnabled(false);
            jTextField5.setEnabled(false);
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
        JTextField textField = (JTextField) evt.getSource();
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) || textField.getText().length() >= 50) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextField5KeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        long insert_id = 0;
        TName = jTextField2.getText();
        try {

            ResultSet rs = MySql.select("SELECT id FROM teachers WHERE nic = '" + TNic + "';");
            if (rs.next()) {
                insert_id = rs.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Addresses(new Frame(), "teachers", (int) insert_id, TName, "Set Teacher Address", true).setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        int row1 = jTable1.getSelectedRow();
        if (row1 == -1) {
            JOptionPane.showMessageDialog(this, "Please select a row from the table.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String Cname = String.valueOf(jTable1.getValueAt(row1, 0));
        String time = String.valueOf(jTable1.getValueAt(row1, 1));
        String day = String.valueOf(jTable1.getValueAt(row1, 2));
        String place = String.valueOf(jTable1.getValueAt(row1, 3));

        try {
            ResultSet rs = MySql.select("SELECT c.id AS class_id FROM classes c INNER JOIN starttime st ON c.startTime_id = st.id INNER JOIN day d ON c.day_id = d.id JOIN classrooms cr ON c.classRooms_id = cr.id WHERE c.name = '" + Cname + "' AND st.sTime = '" + time + "' AND d.name = '" + day + "' AND cr.name = '" + place + "';");

            if (rs.next()) {
                Cid = rs.getString("class_id");
            }

            new ClassProfile(new Frame(), true, Cid).setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        loadData();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        JFileChooser fileChooser = new JFileChooser();
        int opned = fileChooser.showOpenDialog(this);

        if (opned == 0) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();

            String fp = path.replace("\\", "/");
            String fileExtension = fp.substring(fp.lastIndexOf(".") + 1).toLowerCase();

            if (fileExtension.equals("jpg") || fileExtension.equals("jpeg") || fileExtension.equals("png")) {
                filepath = fp;
                jLabel3.setText("");

                SwingUtilities.invokeLater(() -> {
                    try {
                        jLabel3.setIcon(Qube.resizeImage(jLabel3.getWidth(), jLabel3.getHeight(), fp));
                    } catch (IOException e) {
//                        l.log(Level.WARNING, "While setting profile image part.", e);
                        e.printStackTrace();
                    }
                });
            } else {
                JOptionPane.showMessageDialog(this, "Please select jpeg, jpg, png.", "Invalid file type", JOptionPane.WARNING_MESSAGE);
            }

        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jPanel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel7MouseClicked
        new PaymentHistoryTeacher(new Frame(), true, TNic).setVisible(true);
    }//GEN-LAST:event_jPanel7MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
//        TName = jTextField2.getText();
        try {

            ResultSet rs = MySql.select("SELECT id FROM teachers WHERE nic = '" + TNic + "';");
            if (rs.next()) {
                insert_id = rs.getString("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        new FreeCards(new Frame(), true, this.insert_id).setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(TeacherProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(TeacherProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(TeacherProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(TeacherProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the dialog */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                TeacherProfile dialog = new TeacherProfile(new javax.swing.JFrame(), true);
//                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
//                    @Override
//                    public void windowClosing(java.awt.event.WindowEvent e) {
//                        System.exit(0);
//                    }
//                });
//                dialog.setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
