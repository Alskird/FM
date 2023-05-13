package App;

import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.datatransfer.*;
import java.lang.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;

public class FM {

    private String pastePath, tempPastePath, path, readingPath, pasteFolder, historyPath, activePath, copyNameFileOrFolder;
    private String startReading, endReading, tempPath2, message, nameDelete;
    private String[][] catalogContent;
    private String[] history, tempPath1;
    private String[] columnsHeader = new String[] {"Имя", "Тип"};
    private final String homePath = "C:/HomeFM/";
    private final int homePathInt = homePath.split("/").length - 1;
    private int flagReading = 0, flagHistory, numberFiles, numberFolders, tempNumberFiles = 0, tempNumberFolders = 0;
    private boolean flag = true, rootFlag = true, delete = false, irrevocably = true;
    private File dir, readingDir, pasteDir, copyPath;
    private DefaultTableModel dtm;
    private JTextField pathField;
    private Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    private DataFlavor flavor = DataFlavor.javaFileListFlavor;
    private ArrayList<String> pathsDelete = new ArrayList<>();
    private JDialog windowDelete = new DialogWindow(this);
    private JFrame infoWindow = new InfoWindow(this);
    private JLabel messageLabel, infoLabel, timeLabel;

    public FM () {
        createWindowInfo();
        TimerWorking timer = new TimerWorking(timeLabel);
    }

    public void initializingElements(JTextField pathField) {
        this.pathField = pathField;
    }
    public void initializingElementsWindowConfirmation(JLabel messageLabel) {
        this.messageLabel = messageLabel;
    }
    public void initializingElementsWindowInfo(JLabel messageLabel, JLabel timeLabel) {
        this.infoLabel = messageLabel;
        this.timeLabel = timeLabel;
    }

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
            historyManagement(homePath);
        }
        return catalogContent;
    }

    public String[][] getContent() {
        int counter = 0;
        path = activePath;
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
        path = activePath + selectPath + "/";
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
        historyManagement(path);
        return catalogContent;
    }

    public boolean editingPermission(boolean status) {
        return status;
    }

    public String getActivePath() {
        return activePath;
    }

    public void readHistoryPath() {
        for (String hPath: history) {
            System.out.println(hPath);
        }
    }

    public void copyFileOrFolder(String nameFileOrFolder){
        File file = new File(activePath,nameFileOrFolder);
        List listOfFiles = new ArrayList();
        listOfFiles.add(file);
        System.out.println(nameFileOrFolder);
        FileTransferable ft = new FileTransferable(listOfFiles);

        clipboard.setContents(ft, new ClipboardOwner() {
            @Override
            public void lostOwnership(Clipboard clipboard, Transferable contents) {
                System.out.println("Lost ownership");
            }
        });

/*        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ft, new ClipboardOwner() {
            @Override
            public void lostOwnership(Clipboard clipboard, Transferable contents) {
                System.out.println("Lost ownership");
            }
        });*/
    }

    public Clipboard getClipboard() {
        return clipboard;
    }

    public void pasteFileOrFolder(String pastePath){
        this.pastePath = getActivePath() + pastePath + "/";
        if (clipboard.isDataFlavorAvailable(flavor)) {
            try {
                List list = (List) clipboard.getData(flavor);
                Iterator countriesIterator = list.iterator();
                /*for (int i = 0; i < list.size(); i++) {
                    System.out.println("Данные буфера");
                    System.out.println(list.get(i));
                    System.out.println("-------------------------------");
                }*/
                while (countriesIterator.hasNext()) {
                    copyToPaste(countriesIterator.next().toString());
                    rootFlag = true;
                }
            } catch (UnsupportedFlavorException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public String splitPathToCopy(String currentPath){
        tempPath1 = currentPath.split("/");
        tempPath2 = pastePath;
        //tempPath2 = pastePath.split("/");
        for (int i = 1; i < tempPath1.length; i++){
            tempPath2 += tempPath1[i] + "/";
        }
        return tempPath2;
    }

    public void copyToPaste(String namePath){
        flagReading ++;
        File ttt = new File(namePath);
        if (ttt.isDirectory()) {
            if (rootFlag) {
                rootFlag = false;
                try {
                    pastePath += ttt.getName() + "/";
                    Files.createDirectory(Path.of(pastePath));
                } catch (IOException e) {
                    System.out.println("Каталог уже существует");
                }
            }
            for (File item : ttt.listFiles()) {
                if (item.isDirectory()) {
                    try {
                        Files.createDirectory(Path.of(splitPathToCopy(namePath) + item.getName()));
                    } catch (IOException e) {
                        System.out.println("Каталог уже существует");
                    }
                    copyToPaste(namePath + "/" + item.getName());
                } else {
                    try {
                        Files.createFile(Path.of(splitPathToCopy(namePath) + item.getName()));
                        Files.copy(Path.of(namePath + "/" + item.getName()), Path.of(splitPathToCopy(namePath) + item.getName()), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.out.println("Файл уже существует");
                    }
                }
            }
        } else {
            try {
                splitPathToCopy(namePath);
                Files.createFile(Path.of(splitPathToCopy(namePath) + ttt.getName()));
                Files.copy(Path.of(namePath), Path.of(splitPathToCopy(namePath) + ttt.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                System.out.println("Файл уже существует");
            }
        }
        flagReading--;
    }

    public void readingСatalog(String nameFolder){
        flagReading ++;
        readingPath = getActivePath() + nameFolder + "/";
        readingDir = new File(readingPath);
        if (tempNumberFiles == 0 && tempNumberFolders == 0){
            startReading = nameFolder;
            System.out.println(readingDir.getAbsolutePath());
        }
        if(readingDir.isDirectory())
        {
            for(File item : readingDir.listFiles()){

                if(item.isDirectory()){
 /*                   System.out.printf("Уровень - [%d] ",flagReading);
                    System.out.println(item.getName() + "\t folder");*/
                    tempNumberFolders++;
                    System.out.println(item.getPath());
                    readingСatalog(nameFolder + "/" + item.getName());
                }
                else{
/*                    System.out.printf("Уровень - [%d] ",flagReading);
                    System.out.println(item.getName() + "\t file");*/
                    tempNumberFiles++;
                    System.out.println(item.getPath());
                }
                if (tempNumberFolders > numberFolders){
                    numberFolders = tempNumberFolders;
                    endReading = item.getName();
                }
                if (tempNumberFiles > numberFiles){
                    numberFiles = tempNumberFiles;
                    endReading = item.getName();
                }
            }
            if (tempNumberFiles == 0 && tempNumberFolders == 0){
                endReading = readingDir.getName();
            }
        } else {
            endReading = readingDir.getName();
        }
        flagReading--;
        if (flagReading == 0){
            System.out.printf("Стартовая точка - %s\n",startReading);
            System.out.printf("Количество папок - %d\n",numberFolders);
            System.out.printf("Количество файлов - %d\n",numberFiles);
            System.out.printf("Конечная точка - %s\n",endReading);
            numberFolders = 0;
            numberFiles = 0;
            tempNumberFolders = 0;
            tempNumberFiles = 0;
            startReading = null;
            endReading = null;
        }
    }

    public void creatingDeletionPaths(String nameFolder){
        flagReading ++;
        readingPath = getActivePath() + nameFolder + "/";
        readingDir = new File(readingPath);
        if (tempNumberFiles == 0 && tempNumberFolders == 0){
            startReading = nameFolder;
            pathsDelete.add(readingDir.getAbsolutePath());
            System.out.println(readingDir.getAbsolutePath());
        }
        if(readingDir.isDirectory())
        {
            for(File item : readingDir.listFiles()){

                if(item.isDirectory()){
 /*                   System.out.printf("Уровень - [%d] ",flagReading);
                    System.out.println(item.getName() + "\t folder");*/
                    tempNumberFolders++;
                    pathsDelete.add(item.getAbsolutePath());
                    //System.out.println(item.getPath());
                    creatingDeletionPaths(nameFolder + "/" + item.getName());
                }
                else{
/*                    System.out.printf("Уровень - [%d] ",flagReading);
                    System.out.println(item.getName() + "\t file");*/
                    tempNumberFiles++;
                    pathsDelete.add(item.getAbsolutePath());
                    //System.out.println(item.getPath());
                }
                if (tempNumberFolders > numberFolders){
                    numberFolders = tempNumberFolders;
                    endReading = item.getName();
                }
                if (tempNumberFiles > numberFiles){
                    numberFiles = tempNumberFiles;
                    endReading = item.getName();
                }
            }
            if (tempNumberFiles == 0 && tempNumberFolders == 0){
                endReading = readingDir.getName();
            }
        } else {
            pathsDelete.add(readingDir.getAbsolutePath());
            endReading = readingDir.getName();
        }
        flagReading--;
        if (flagReading == 0){
            System.out.printf("Стартовая точка - %s\n",startReading);
            System.out.printf("Количество папок - %d\n",numberFolders);
            System.out.printf("Количество файлов - %d\n",numberFiles);
            System.out.printf("Конечная точка - %s\n",endReading);
            numberFolders = 0;
            numberFiles = 0;
            tempNumberFolders = 0;
            tempNumberFiles = 0;
            startReading = null;
            endReading = null;
        }
    }

    public void deleteFileOrFolder(String nameFolderOrFile){
        creatingDeletionPaths(nameFolderOrFile);
        /*for (String path: pathsDelete) {
            System.out.println(path);
        }*/
        String test, temp;
        File recycle;
        ListIterator<String> listIter = pathsDelete.listIterator();
        if (delete){
            while(listIter.hasNext()){
                test = listIter.next();
                if (delete && !irrevocably) {
                    temp = test;
                    test = test.replace("C:\\HomeFM\\","C:/HomeFM/$RECYCLE.BIN/");
                    recycle = new File(temp);
                    System.out.println(test);
                    if (recycle.isDirectory()){
                        try {
                            Files.createDirectory(Path.of(test));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        try {
                            Files.createFile(Path.of(test));
                            Files.copy(Path.of(test), Path.of(temp), StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(test);
                }
            }
//        System.out.println("Обратный проход");
            while (listIter.hasPrevious()){
                try {
                    Files.delete(Path.of(listIter.previous()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
//            System.out.println(listIter.previous());
            }
        }
        pathsDelete.clear();
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
        if(readingDir.isDirectory())
        {
            for(File item : readingDir.listFiles()){

                if(item.isDirectory()){
                    /*System.out.printf("Уровень - [%d] ",flagReading);
                    System.out.println(item.getName() + "\t folder");*/
                    pasteDir.mkdir();
                    pastePath = getActivePath() + pasteFolder + "/" + nameFolder + "/" + item.getName();
                    pasteDir = new File(pastePath);
                    pasteDir.mkdir();
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
        this.dtm = dtm;
        this.columnsHeader = columnsHeader;
    }

    public void updateTable(String[] columnsHeader, String valueActiveField, DefaultTableModel dtm) {
        dtm.setDataVector(getContent(valueActiveField),columnsHeader);
        dtm.fireTableStructureChanged();
    }

    public void updatePathField() {
        pathField.setText(getActivePath());
    }

    public void historyManagement(String path) {
        historyPath = path;
        activePath = path;
        history = path.split("/");
        flagHistory = history.length;
        /*for (String h : history) {
            System.out.println(h);
        }*/
    }

    public void back(){
        if (flagHistory - 1 > homePathInt){
            flagHistory--;
            activePath = "";
            for (int i = 0; i < flagHistory; i++){
                activePath += history[i] + "/";
            }
            getContent();
        }
    }

    public void next(){
        if (flagHistory + 1 <= history.length){
            flagHistory++;
            activePath = "";
            for (int i = 0; i < flagHistory; i++){
                activePath += history[i] + "/";
            }
            getContent();
        }
    }

    public void windowConfirmationDeletion(String nameFolderOrFile){
        message = "Вы уверены что хотите удалить: " + nameFolderOrFile + " ?";
        nameDelete = nameFolderOrFile;
        messageLabel.setText(message);
        windowDelete.setVisible(true);
    }

    public void setDeletionParameters(boolean delete, boolean irrevocably) {
        this.delete = delete;
        this.irrevocably = irrevocably;
        windowDelete.setVisible(false);
        deleteFileOrFolder(nameDelete);
        updateTable(columnsHeader,dtm);
    }

    public void createWindowInfo(){
        infoWindow.setVisible(true);
    }
}
