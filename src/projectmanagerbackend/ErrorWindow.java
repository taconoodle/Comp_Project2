package projectmanagerbackend;

import javax.swing.*;

public class ErrorWindow {

    private String errorMessage;

    public ErrorWindow(String errorMessage) {
        this.errorMessage = errorMessage;

        JFrame errorFrame = new JFrame();
        errorFrame.setLayout(null);
        errorFrame.setSize(200, 200);
        errorFrame.setVisible(true);
        errorFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel errorLabel = new JLabel(errorMessage);
        errorLabel.setBounds(10,10,150, 100);
        errorLabel.setLayout(null);
        errorFrame.add(errorLabel);
    }
}
