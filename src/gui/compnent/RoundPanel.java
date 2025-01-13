package gui.compnent;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPanel;

public class RoundPanel extends JPanel {

    public RoundPanel() {
        init();
    }

    private void init() {
        this.putClientProperty(FlatClientProperties.STYLE, "arc:20");
    }
}
