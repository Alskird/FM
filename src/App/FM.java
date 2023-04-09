package App;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class FM {

    private String copyPath, pastePath, path, lastPath, readingPath, copyNameFileOrFolder, pasteFolder;
    private final String homePath = "C://HomeFM/";
    private boolean flag = true;
    private int flagReading = 0;
    private String[][] catalogContent;
    private ArrayList<String> historyPath = new ArrayList<>();
    private int activePath;
    private File dir, dirNew, dirCopy, readingDir, copyDir, newDir, pasteDir;
    private String[] columnsHeader = new String[] {"Имя", "Тип"};
    private DefaultTableModel dtm;

    public String[][] getHomeContent() {
        int counter = 0;
        dir = new File(homePath);
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

    public String[][] getContent() {
        int counter = 0;
        path = historyPath.get(activePath);
        System.out.println(path);
        dir = new File(path);
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
        }
        return catalogContent;
    }
    public String[][] getContent(String selectPath) {
        int counter = 0;
        path = historyPath.get(activePath) + selectPath + "/";
        System.out.println(path);
        dir = new File(path);
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
        historyPath.add(path);
        movingHistoryPath(1);
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

    public void deleteFileOrFolder(String nameFileOrFolder) {
        dir = new File(getActivePath(), nameFileOrFolder);
        dir.delete();
    }

    public void copyFileOrFolder(String nameFileOrFolder){
        /*System.out.println("Copy!");
        copyPath = getActivePath();
        dirCopy = new File(copyPath, nameFileOrFolder);
        System.out.println(dirCopy);*/
        copyPath = getActivePath();
        copyNameFileOrFolder = nameFileOrFolder;
        System.out.println(copyPath);
        System.out.println(copyNameFileOrFolder);
    }

    public void pasteFileOrFolder(String nameFileOrFolder) throws IOException {
        System.out.println("Paste!");
        pastePath = getActivePath() + nameFileOrFolder + "/" + dirCopy.getName();
        dirNew = new File(pastePath);
        /*File fileToCopy = new File("C://HomeFM/test/1231245/hi.txt");
        FileInputStream inputStream = new FileInputStream(fileToCopy);
        FileChannel inChannel = inputStream.getChannel();

        File newFile = new File("C://HomeFM/test/выа/test.txt");
        FileOutputStream outputStream = new FileOutputStream(newFile);
        FileChannel outChannel = outputStream.getChannel();

        inChannel.transferTo(0, fileToCopy.length(), outChannel);

        inputStream.close();
        outputStream.close();*/
        File dirTemp;
        String tempPastePath;
        if(dirCopy.isDirectory())
        {

            dirNew.mkdir();
            for(File item : dirCopy.listFiles()){

                if(item.isDirectory()){
                    tempPastePath = pastePath + "/" + item.getName();
                    dirTemp = new File(tempPastePath);
                    dirTemp.mkdir();
                    /*dirTemp = new File(dirCopy + "/" + item.getName());
                    dirTemp.mkdir();*/
                    //System.out.println(item.getName() + "  \t folder");
                }
                else{
                    //System.out.println(item.getName() + "\t file");
                }
            }
        }
       /* try
        {
            boolean created = dirNew.createNewFile();
            if(created)
                System.out.println("File has been created");
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }*/
    }

    public void readingСatalog(String nameFolder){
        flagReading ++;
        readingPath = getActivePath() + nameFolder + "/";
        readingDir = new File(readingPath);
        if(readingDir.isDirectory())
        {
            for(File item : readingDir.listFiles()){

                if(item.isDirectory()){
                    System.out.printf("Уровень - [%d] ",flagReading);
                    System.out.println(item.getName() + "\t folder");
                    readingСatalog(nameFolder + "/" + item.getName());
                }
                else{
                    System.out.printf("Уровень - [%d] ",flagReading);
                    System.out.println(item.getName() + "\t file");
                }
            }
        }
        flagReading--;
    }

    public void copyСatalog(String nameFolder) throws IOException {
        flagReading ++;
        if (flag){
            pasteFolder = nameFolder;
            nameFolder = copyNameFileOrFolder;
            flag = false;
        }
        readingPath = copyPath + nameFolder + "/";
        readingDir = new File(readingPath);
        pastePath = getActivePath() + pasteFolder + "/" + nameFolder + "/";
        pasteDir = new File(pastePath);
/*        System.out.println(readingDir);
        System.out.println(pasteDir);*/
        if(readingDir.isDirectory())
        {
            //pasteDir.mkdirs();
            for(File item : readingDir.listFiles()){

                if(item.isDirectory()){
                    System.out.printf("Уровень - [%d] ",flagReading);
                    System.out.println(item.getName() + "\t folder");
                    pasteDir.mkdir();
                    pastePath = getActivePath() + pasteFolder + "/" + nameFolder + "/" + item.getName();
                    pasteDir = new File(pastePath);
                    pasteDir.mkdir();
/*                    System.out.println("------------------------");
                    System.out.println(item.getPath());
                    System.out.println("////////////////////////");
                    System.out.println(pasteDir);
                    System.out.println("------------------------");*/
                    copyСatalog(nameFolder + "/" + item.getName());
                }
                else{
                    System.out.println(item.getPath());
                    File fileToCopy = new File(item.getPath());
                    FileInputStream inputStream = new FileInputStream(fileToCopy);
                    FileChannel inChannel = inputStream.getChannel();

                    File newFile = new File(getActivePath() + pasteFolder + "/" + nameFolder + "/" + item.getName());
                    FileOutputStream outputStream = new FileOutputStream(newFile);
                    FileChannel outChannel = outputStream.getChannel();

                    inChannel.transferTo(0, fileToCopy.length(), outChannel);

                    inputStream.close();
                    outputStream.close();
                    /*System.out.printf("Уровень - [%d] ",flagReading);
                    System.out.println(item.getName() + "\t file");*/
                }
            }
        }
        flagReading--;
    }

    public void updateTable(String[] columnsHeader, DefaultTableModel dtm) {
        dtm.setDataVector(getContent(),columnsHeader);
        dtm.fireTableStructureChanged();
    }

    public void updateTable(String[] columnsHeader, String valueActiveField, DefaultTableModel dtm) {
        dtm.setDataVector(getContent(valueActiveField),columnsHeader);
        dtm.fireTableStructureChanged();
    }
}
