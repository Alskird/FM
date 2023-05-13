package App;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogWindow extends JDialog {

    DialogWindow(FM fm) {

        // Главная панель
        JPanel manePanel = new JPanel();
        manePanel.setLayout(new BorderLayout());

        // Верхняя (над кнопочной) панель
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(400,50));
//        topPanel.setBackground(Color.RED);

        manePanel.add(topPanel, BorderLayout.PAGE_START);

        // Строка сообщения
        JLabel messageLabel = new JLabel();
        fm.initializingElementsWindowConfirmation(messageLabel);

        topPanel.add(messageLabel);

        // Кнопочная панель
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1,3,10,0));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
//        buttonsPanel.setBackground(Color.YELLOW);

        manePanel.add(buttonsPanel, BorderLayout.CENTER);

        // Кнопка Удаления
        JButton deleteButton = new JButton("Delete");

        buttonsPanel.add(deleteButton);

        // Кнопка Удалить безвозвратно
        JButton permanentlyDeleteButton = new JButton("Permanently delete");

        buttonsPanel.add(permanentlyDeleteButton);

        // Кнопка отмены
        JButton cancelButton = new JButton("Cancel");

        buttonsPanel.add(cancelButton);

        // Нижняя (под кнопочкой) панель
        JPanel downPanel = new JPanel();
        downPanel.setLayout(new BorderLayout());
        downPanel.setPreferredSize(new Dimension(400,50));
//        downPanel.setBackground(Color.GREEN);

        manePanel.add(downPanel, BorderLayout.PAGE_END);

        // Обработчик кнопок

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fm.setDeletionParameters(true,false);
            }
        });

        permanentlyDeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fm.setDeletionParameters(true,true);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fm.setDeletionParameters(false,false);
            }
        });

        setContentPane(manePanel);
        setSize(400,200);
        setLocationRelativeTo(null);
    }

}
