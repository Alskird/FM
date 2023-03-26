package App;

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
            activePath = 0;
            System.out.println(historyPath.get(activePath));
        }
        return catalogContent;
    }

    public String[][] getContent(String selectPath) {
        int counter = 0;
        path = historyPath.get(activePath) + selectPath + "/";
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
            historyPath.add(path);
            activePath++;
            System.out.println(historyPath.get(activePath));
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
}
