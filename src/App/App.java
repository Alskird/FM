package App;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class App extends JFrame {
    FM test = new FM();
    private String[][] contentArray = test.getHomeContent();
    private String[] columnsHeader = new String[] {"Имя", "Тип"};
    private int[] selectTableSlot = new int[2];
    private boolean status = false;
    private String valueActiveField;
    private String clickRMBNameFileOrFolder;
    private DefaultTableModel dtm = new DefaultTableModel(contentArray, columnsHeader);
    private Point clickRMB;

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

        // Контекстное меню #1 (для таблицы)
        JPopupMenu popupMenuTable = new JPopupMenu();

        // Создание меню элементов для контекстного меню
        JMenuItem mCopy = new JMenuItem("Копировать");
        JMenuItem mDelete = new JMenuItem("Удалить");
        JMenuItem mRename = new JMenuItem("Переименовать");
        JMenuItem mPaste = new JMenuItem("Вставить");

        // Добавление меню элементов в контекстное меню
        popupMenuTable.add(mCopy);
        popupMenuTable.add(mDelete);
        popupMenuTable.add(mRename);
        popupMenuTable.add(mPaste);

        // Контекстное меню #2 (для панели таблицы)
        JPopupMenu popupMenuPanelTable = new JPopupMenu();

        // Создание меню элементов для контекстного меню
        JMenuItem mPCopy = new JMenuItem("Копировать");
        JMenuItem mPDelete = new JMenuItem("Удалить");
        JMenuItem mPRename = new JMenuItem("Переименовать");
        JMenuItem mPPaste = new JMenuItem("Вставить");

        // Добавление меню элементов в контекстное меню
        popupMenuPanelTable.add(mPCopy);
        popupMenuPanelTable.add(mPDelete);
        popupMenuPanelTable.add(mPRename);
        popupMenuPanelTable.add(mPPaste);


        // Таблица файлов/каталогов
        /*JTable table1 = new JTable(array, columnsHeader);
        centerPanel.add(new JScrollPane(table1));*/

        test.initializingElements(pathField);
        JTable table1 = new JTable(contentArray,columnsHeader){
          @Override
          public boolean isCellEditable(int row, int column){
              return test.editingPermission(status);
          }
        };
        table1.setModel(dtm);

/*        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = table1.getColumnModel().getColumn(i);
            if (i == 2) {
                column.setPreferredWidth(100); //third column is bigger
            } else {
                column.setPreferredWidth(50);
            }
        }*/

        JScrollPane scrollPane = new JScrollPane(table1);
        table1.setFillsViewportHeight(true);

        centerPanel.add(new JScrollPane(table1));

        table1.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent event) {
                if (event.getButton() == MouseEvent.BUTTON1) {
                    if (status) {
                        status = false;
                    }
                    if (event.getClickCount() == 2) {
                        valueActiveField = (table1.getValueAt(table1.getSelectedRow(), table1.getSelectedColumn())).toString();
                        test.updateTable(columnsHeader, valueActiveField, dtm);
                        pathField.setText(test.getActivePath());
                    } else {
                        if (event.getClickCount() == 1) {
                            if (selectTableSlot[0] == table1.getSelectedRow() & selectTableSlot[1] == table1.getSelectedColumn()) {
                                if (table1.getColumnName(table1.getSelectedColumn()).equals("Имя")) {
                                    status = true;
                                    table1.editCellAt(table1.getSelectedRow(), table1.getSelectedColumn());
                                }
                            } else {
                                selectTableSlot[0] = table1.getSelectedRow();
                                selectTableSlot[1] = table1.getSelectedColumn();
                            }
                        }
                    }
                } else {
                    if (event.getButton() == MouseEvent.BUTTON3) {
                        //popupMenuTable.show(manePanel, event.getX(), event.getY());
                        //System.out.println(popupMenuTable.getComponentIndex(mDelete));
                        clickRMBNameFileOrFolder = null;
                        int x = event.getX();
                        int y = event.getY();
                        clickRMB = new Point(x,y);
                        int row = table1.rowAtPoint(clickRMB);
                        int column = table1.columnAtPoint(clickRMB);
                        if (row != -1 && column != -1){
                            clickRMBNameFileOrFolder = table1.getValueAt(row,column).toString();
                            //System.out.println("Success");
                            popupMenuTable.show(event.getComponent(), event.getX(), event.getY());
                        } else {
                            //System.out.println("Fail");
                            popupMenuPanelTable.show(event.getComponent(), event.getX(), event.getY());
                        }
                    }
                }
            }

            public void mouseEntered(MouseEvent event) {}

            public void mouseExited(MouseEvent event) {}

            public void mousePressed(MouseEvent event) {}

            public void mouseReleased(MouseEvent event) {}

        });

        pathField.setText(test.getActivePath());

    // Обработчики элементов контекстного меню #1
        mCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                test.copyFileOrFolder(clickRMBNameFileOrFolder);
            }
        });

        mDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                test.deleteFileOrFolder(clickRMBNameFileOrFolder);
                test.updateTable(columnsHeader, dtm);
            }
        });

        mRename.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                test.readingСatalog(clickRMBNameFileOrFolder);
                System.out.println(clickRMBNameFileOrFolder);
                System.out.println("Item 3 clicked.");
            }
        });

        mPaste.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                test.pasteFileOrFolder(clickRMBNameFileOrFolder);
            }
        });

    // Обработчики элементов контекстного меню #2
        mPCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Item 1 clicked.");
            }
        });

        mPDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Item 2 clicked.");
            }
        });

        mPRename.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                System.out.println("Item 3 clicked.");
            }
        });

        mPPaste.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

    // Обработчики кнопок
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //test.updateTable(searchField);
                test.back();
                contentArray = test.getContent();
                dtm.setDataVector(contentArray,columnsHeader);
                dtm.fireTableStructureChanged();
                pathField.setText(test.getActivePath());
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //test.updateTable(searchField);
                test.next();
                contentArray = test.getContent();
                dtm.setDataVector(contentArray,columnsHeader);
                dtm.fireTableStructureChanged();
                pathField.setText(test.getActivePath());
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //test.updateTable(searchField);
                test.readHistoryPath();
            }
        });
    // Настройки окна
        setContentPane(manePanel);
        setSize(800,600);
        setLocationRelativeTo(null);
    }
}
