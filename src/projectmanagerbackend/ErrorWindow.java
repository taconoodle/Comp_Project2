package projectmanagerbackend;

import javax.swing.*;

public class ErrorWindow {

    private String errorMessage;

    public ErrorWindow(String errorMessage) {
        this.errorMessage = errorMessage;

        JFrame errorFrame = new JFrame();
        errorFrame.setLayout(null);
        errorFrame.setSize(300, 300);
        errorFrame.setVisible(true);
        errorFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JLabel errorLabel = new JLabel(errorMessage);
        errorLabel.setBounds(10,10,250, 100);
        errorLabel.setLayout(null);
        errorFrame.add(errorLabel);
    }
}
