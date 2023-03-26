package App;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.util.EventObject;

public class App extends JFrame {
    // Данные для таблиц !-- в массив
    FM test = new FM();
    private Object[][] array = test.getContent("C://");
    // Заголовки столбцов !-- в массив
    private Object[] columnsHeader = new String[] {"Имя", "Тип"};
    App(){

    // Главная панель
        JPanel manePanel = new JPanel();
        manePanel.setLayout(new BorderLayout());

    // Панель ЗАПАД (LINE_START) WEST
        JPanel westPanel = new JPanel();
        westPanel.setBackground(Color.PINK);
        westPanel.setPreferredSize(new Dimension(150, 100));

        manePanel.add(westPanel, BorderLayout.LINE_START);

        // Панель истории и ссылок
        JPanel panelHAL = new JPanel();
        panelHAL.setLayout(new GridLayout());
        panelHAL.setBackground(Color.GRAY);
        panelHAL.setPreferredSize(new Dimension(150,50));
        panelHAL.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        westPanel.add(panelHAL);

        // Test
        JButton button1 = new JButton();

        panelHAL.add(button1);

    // Панель СЕВЕР (PAGE_START) NORTH
        JPanel northPanel = new JPanel();
        BorderLayout borderLayoutNorthPanel = new BorderLayout();
        northPanel.setLayout(borderLayoutNorthPanel);
        //northPanel.setBackground(Color.GREEN);
        northPanel.setPreferredSize(new Dimension(300, 30));

        manePanel.add(northPanel, BorderLayout.PAGE_START);

        // Панель кнопок
        JPanel navigationButtonPanel = new JPanel();
        //GridLayout gl = new GridLayout(1,4);
        navigationButtonPanel.setLayout(new GridLayout(1,4));
        navigationButtonPanel.setBackground(Color.CYAN);
        navigationButtonPanel.setPreferredSize(new Dimension(150, 30));
        navigationButtonPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        northPanel.add(navigationButtonPanel, BorderLayout.LINE_START);

        // Кнопка Назад
        JButton backButton = new JButton("Back");
        //backButton.setPreferredSize(new Dimension(40,30));

        navigationButtonPanel.add(backButton);

        // Кнопка Вперёд
        JButton nextButton = new JButton("Next");
        //nextButton.setPreferredSize(new Dimension(40,30));

        navigationButtonPanel.add(nextButton);

        // История расположений (движения по каталогам/папкам)
        JButton historyButton = new JButton("List");
        //historyButton.setPreferredSize(new Dimension(40,30));

        navigationButtonPanel.add(historyButton);

        // Кнопка Вверх до
        JButton upButton = new JButton("Up");
        //upButton.setPreferredSize(new Dimension(40,30));

        navigationButtonPanel.add(upButton);

        // Панель пути
        JPanel pathPanel = new JPanel();
        //GridLayout gl1 = new GridLayout();
        pathPanel.setLayout(new GridLayout());
        pathPanel.setBackground(Color.BLUE);
        pathPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        northPanel.add(pathPanel, BorderLayout.CENTER);

        // Поле пути
        JTextField pathField = new JTextField();
        //textField1.setPreferredSize(new Dimension(200,30));
        /*Font font = textField1.getFont().deriveFont(20f);
        textField1.setFont(font);*/

        pathPanel.add(pathField);

        // Панель поиска
        JPanel searchPanel = new JPanel();
        //GridLayout gl2 = new GridLayout();
        searchPanel.setLayout(new GridLayout());
        searchPanel.setBackground(Color.BLACK);
        searchPanel.setPreferredSize(new Dimension(150,30));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        northPanel.add(searchPanel, BorderLayout.LINE_END);

        // Поле поиска
        JTextField searchField = new JTextField();
        //searchField.setPreferredSize(new Dimension(100,30));

        searchPanel.add(searchField);

    // Панель ЦЕНТР (CENTER)
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.YELLOW);
        centerPanel.setLayout(new GridLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        manePanel.add(centerPanel, BorderLayout.CENTER);

        // Таблица файлов/каталогов
        /*JTable table1 = new JTable(array, columnsHeader);
        centerPanel.add(new JScrollPane(table1));*/
        JTable table1 = new JTable(array,columnsHeader){
          @Override
          public boolean isCellEditable(int row, int column){
              System.out.println("Сработало");
              return getColumnName(column).equals("Имя");
          }
        };

        centerPanel.add(new JScrollPane(table1));

        table1.addMouseListener(new MouseListener() {
            int count = 0;
            public void mouseClicked(MouseEvent event) {
                count++;

                if (count == 4) {
                    pathField.setText(table1.getValueAt(table1.getSelectedRow(), table1.getSelectedColumn()).toString());
                    table1.editCellAt(table1.getSelectedRow(), table1.getSelectedColumn());
                    count = 0;
                }
            }

            public void mouseEntered(MouseEvent event) {}

            public void mouseExited(MouseEvent event) {}

            public void mousePressed(MouseEvent event) {}

            public void mouseReleased(MouseEvent event) {}

        });

    // Настройки окна
        setContentPane(manePanel);
        setSize(800,600);
        setLocationRelativeTo(null);
    }
}
