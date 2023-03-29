package App;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;

public class FM {

    private String lastPath;
    private final String homePath = "C://HomeFM/";
    private String path;
    private String[][] catalogContent;
    private ArrayList<String> historyPath = new ArrayList<>();
    private int activePath;

    public String[][] getHomeContent() {
        int counter = 0;
        File dir = new File(homePath);
        if (dir.isDirectory())
        {
            for(File item : dir.listFiles()){
                counter++;
            }
        }
        catalogContent = new String[counter][2];
        counter = 0;
        if (dir.isDirectory())
        {
            for(File item : dir.listFiles()){

                if(item.isDirectory()){
                    catalogContent[counter][0] = item.getName();
                    catalogContent[counter][1] = "folder";
                }
                else{
                    catalogContent[counter][0] = item.getName();
                    catalogContent[counter][1] = "file";
                }
                counter++;
            }
            historyPath.add(homePath);
            movingHistoryPath(0);
        }
        return catalogContent;
    }

    public String[][] getContent(String selectPath, int callCode) {
        /*
        0 - вызов на чтение двойным кликом мыши
        1 - вызов кнопкой back
        2 - вызов кнопкой next
        */
        int counter = 0;
        if (callCode == 0) {
            path = historyPath.get(activePath) + selectPath + "/";
        } else {
            path = historyPath.get(activePath);
        }
        System.out.println(path);
        File dir = new File(path);
        if (dir.isDirectory())
        {
            for(File item : dir.listFiles()){
                counter++;
                /*if(item.isDirectory()){
                    //System.out.println(item.getName() + "  \t folder");
                }
                else{
                    //System.out.println(item.getName() + "\t file");
                }*/
            }
        }
        catalogContent = new String[counter][2];
        counter = 0;
        if (dir.isDirectory())
        {
            for(File item : dir.listFiles()){

                if(item.isDirectory()){
                    catalogContent[counter][0] = item.getName();
                    catalogContent[counter][1] = "folder";
                }
                else{
                    catalogContent[counter][0] = item.getName();
                    catalogContent[counter][1] = "file";
                }
                counter++;
            }
            switch (callCode) {
                case (0):
                    historyPath.add(path);
                    movingHistoryPath(1);
                    break;
                case (1):
                    break;
                case (2):
                    break;
                default:
                    break;
            }

        }
        /*int flag = 0;
        for (String[] z: catalogContent)
        {
            for (String f: z) {
                if (flag == 0){
                    System.out.println(f);
                    flag++;
                } else {
                    System.out.println("\t" + f);
                    flag--;
                }
            }
        }*/
        return catalogContent;
    }

    public boolean editingPermission(boolean status) {
        return status;
    }

    public String getActivePath() {
        return historyPath.get(activePath);
    }

    public void readHistoryPath() {
        for (String hPath: historyPath) {
            System.out.println(hPath);
        }
    }

    public void movingHistoryPath(int offsetCode) {
        /*
        0 - задача первого пути (домашний каталог/первый запуск)
        1 - движение вперёд
        2 - движение назад
        3 - движение в конец
        */
        switch (offsetCode) {
            case (0):
                System.out.println("задача первого пути (домашний каталог/первый запуск)");
                activePath = 0;
                break;
            case (1):
                System.out.println("движение вперёд");
                if (activePath < historyPath.size() - 1) {
                    activePath++;
                }
                break;
            case (2):
                System.out.println("движение назад");
                if (activePath > 0) {
                    activePath--;
                }
                break;
            case (3):
                System.out.println("движение в конец");
                activePath = historyPath.size();
                System.out.println(activePath);
                break;
            default:
                break;
        }
    }

    public void updateTable(JTextField searchField) {
        searchField.setText("test");
    }
}
