package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InfoWindow extends JFrame {

    InfoWindow(FM fm) {

        // Главная панель
        JPanel manePanel = new JPanel();
        manePanel.setLayout(new BorderLayout());

        // Панель времени
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BorderLayout());
        timePanel.setPreferredSize(new Dimension(400,20));
        timePanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        timePanel.setBackground(Color.red);

        manePanel.add(timePanel, BorderLayout.PAGE_END);

        // Строка времени
        JLabel timeLabel = new JLabel();

        timePanel.add(timeLabel, BorderLayout.LINE_END);

        // Строка сообщения
        JLabel messageLabel = new JLabel();

        manePanel.add(messageLabel);

        fm.initializingElementsWindowInfo(messageLabel, timeLabel);

        setContentPane(manePanel);
        setSize(400,600);
        setLocation(0,0);
    }
}
