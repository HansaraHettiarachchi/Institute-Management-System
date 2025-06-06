package model;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import gui.teacher.PaySheet;
import gui.users.UserPaysheet;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.Vector;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class Qube {

     public Qube() {
        Thread t = new Thread(() -> {
            User.setbDone(false);
            try {
                SimpleDateFormat fullDateFormatter = new SimpleDateFormat("yyyy-MM");

                Date d = new Date();
                Date d1 = new Date();

                Calendar c = Calendar.getInstance();
                c.setTime(d);
                c.add(Calendar.MONTH, -1);
                d = c.getTime();

                String date = fullDateFormatter.format(d1) + "-01";

                ResultSet rs1 = MySql.select("SELECT * FROM `teachers`; ");

                while (rs1.next()) {
                    ResultSet rs = MySql.select("SELECT * FROM `teapayments` WHERE `teachers_id` = '" + rs1.getInt("id") + "' AND  `DAT` LIKE '%" + date + "%' AND `desc` = 'Opening Balance';");

                    if (!rs.next()) {
                        PaySheet p = new PaySheet(new Frame(), true, rs1.getString("id"), fullDateFormatter.format(d), rs1.getString("name"));
                        double tot = p.getTotBalance();
                        MySql.iud("INSERT INTO `teapayments` (`DAT`, `price`, `teachers_id`, `paymentType_id`, `desc`) VALUES"
                                + " ('" + fullDateFormatter.format(d1) + "-01" + "', '" + tot + "', '" + rs1.getString("id") + "', '2', 'Opening Balance');");
                        p.dispose();
                    }

                }
                
                ResultSet rs2 = MySql.select("SELECT * FROM `user`; ");

                while (rs2.next()) {
                    ResultSet rs = MySql.select("SELECT * FROM `user_payments` WHERE `user_id` = '" + rs2.getInt("id") + 
                            "' AND  `DAT` LIKE '%" + date + "%' AND `desc` = 'Opening Balance';");

                    if (!rs.next()) {
                        UserPaysheet p = new UserPaysheet(new Frame(), true, rs2.getString("id"), 
                                fullDateFormatter.format(d), rs2.getString("fname"),rs2.getDouble("salary"));
                        double tot = p.getSSal();
                        MySql.iud("INSERT INTO `user_payments` (`DAT`, `price`, `user_id`, `desc`) VALUES"
                                + " ('" + fullDateFormatter.format(d1) + "-01" + "', '" + tot + "', '" + rs2.getString("id") + "', 'Opening Balance');");
                        p.dispose();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                User.setbDone(false);
            }
        });

        if (User.isbDone()) {
            t.start();

        }
    }

    private static Logger logger = Logger.getLogger("Pharmacy");

//    public static DecimalFormat df = new DecimalFormat("0.00");
    public static DecimalFormat df;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator(' ');  // Use space as the grouping separator

        df = new DecimalFormat("#,##0.00", symbols);  // Pattern for grouping and two decimal places
    }

//    public static boolean isInternetAvailable() {
//        try {
//            URL url = new URL("http://www.google.com");
//            HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection();
//            urlConnect.setConnectTimeout(5000); // 5 seconds timeout
//            urlConnect.connect();
//            return urlConnect.getResponseCode() == 200;
//        } catch (Exception e) {
//            return false;
//        }
//    }
    public Logger setLogger(String fileName) {

        try {
            FileHandler fileHandler = new FileHandler("log/" + fileName, true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logger;
    }

    public static Result readBarCode(String barCode) {
        try {
            InputStream barInput = new FileInputStream(barCode);
            BufferedImage bI = ImageIO.read(barInput);
            LuminanceSource sourece = new BufferedImageLuminanceSource(bI);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(sourece));
            Reader reader = new MultiFormatReader();
            Result result = reader.decode(bitmap);

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;

    }

    @SuppressWarnings("unchecked")
    public HashMap<Integer, Object> getComboData(String tableName, String rest, String columnName) {

        Vector<String> v = new Vector<>();
        HashMap<Integer, Object> mp = new HashMap<>();
        HashMap<String, Object> comData = new HashMap<>();

        try {
            ResultSet rs = MySql.select("SELECT * FROM `" + tableName + "` " + rest + " ;");
            v.add("SELECT");
            while (rs.next()) {
                v.add(rs.getString(columnName));
                comData.put(rs.getString(columnName), rs.getString("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mp.put(1, new DefaultComboBoxModel(v));
        mp.put(2, comData);
        return mp;
    }

    @SuppressWarnings("unchecked")
    public HashMap<Integer, Object> getComboData(String tableName, String rest, String columnName, Boolean withSelect) {

        Vector<String> v = new Vector<>();
        HashMap<Integer, Object> mp = new HashMap<>();
        HashMap<String, Object> comData = new HashMap<>();

        try {
            ResultSet rs = MySql.select("SELECT * FROM `" + tableName + "` " + rest + " ;");

            if (withSelect) {
                v.add("SELECT");
            }
            while (rs.next()) {
                v.add(rs.getString(columnName));
                comData.put(rs.getString(columnName), rs.getString("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mp.put(1, new DefaultComboBoxModel(v));
        mp.put(2, comData);
        return mp;
    }

    @SuppressWarnings("unchecked")
    public HashMap<Integer, Object> getComboData(String tableName, String rest, String columnName1, String columNamae2) {

        Vector<String> v = new Vector<>();
        HashMap<Integer, Object> mp = new HashMap<>();
        HashMap<String, Object> comData = new HashMap<>();

        try {
            ResultSet rs = MySql.select("SELECT * FROM `" + tableName + "` " + rest + " ;");
            v.add("SELECT");
            while (rs.next()) {
                v.add(rs.getString(columnName1) + " : " + rs.getString(columNamae2));
                comData.put(rs.getString(columnName1) + " : " + rs.getString(columNamae2), rs.getString("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mp.put(1, new DefaultComboBoxModel(v));
        mp.put(2, comData);
        return mp;
    }

    @SuppressWarnings("unchecked")
    public HashMap<Integer, Object> getComboData(String tableName, String rest, String columnName1, String columNamae2, Boolean withSelect) {

        Vector<String> v = new Vector<>();
        HashMap<Integer, Object> mp = new HashMap<>();
        HashMap<String, Object> comData = new HashMap<>();

        try {
            ResultSet rs = MySql.select("SELECT * FROM `" + tableName + "` " + rest + " ;");
            
            if (withSelect) {
                v.add("SELECT");
            }
            while (rs.next()) {
                v.add(rs.getString(columnName1) + " : " + rs.getString(columNamae2));
                comData.put(rs.getString(columnName1) + " : " + rs.getString(columNamae2), rs.getString("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        mp.put(1, new DefaultComboBoxModel(v));
        mp.put(2, comData);
        return mp;
    }

    public static ImageIcon resizeImage(int w, int h, String path) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(path));
//            BufferedImage resizedImage = new BufferedImage(l.getWidth(), l.getWidth(), BufferedImage.TYPE_INT_ARGB);

        return new ImageIcon(originalImage.getScaledInstance(w, h, originalImage.SCALE_SMOOTH));
    }

    public static String randomString() {
        int leftLimit = 97; // 'a'
        int rightLimit = 122; // 'z'
        int targetStringLength = 20;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    public static String randomDigits(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Appends a random digit (0-9)
        }
        return sb.toString();
    }

    public static void typeOnlyDigit(java.awt.event.KeyEvent evt) {
        JTextField textField = (JTextField) evt.getSource();
        char c = evt.getKeyChar();
        if (!Character.isDigit(c) || textField.getText().length() >= 50) {
            evt.consume();
        }

    }

    public void loadProfileImage(String imagePath, JLabel j) {

        ImageIcon icon = new ImageIcon(imagePath);

        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(j.getWidth(), j.getHeight(), Image.SCALE_SMOOTH);
        icon = new ImageIcon(resizedImg);

        j.setText("");
        j.setIcon(icon);
    }

}
