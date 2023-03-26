package App;

import java.io.File;

public class FM {

    private String lastPath;
    private final String homePath = "C://HomeFM/";

    public String[][] getContent(String path) {
        //String homePath = "C://HomeFM/";
        String[][] catalogContent;
        int counter = 0;
        File dir = new File(path);
        /*if (dir.listFiles() == null){
            System.out.println("Был null");
        }*/
        if (dir.isDirectory())
        {
            for(File item : dir.listFiles()){// обработать исключение на null
                counter++;
                if(item.isDirectory()){
                    //System.out.println(item.getName() + "  \t folder");
                }
                else{
                    //System.out.println(item.getName() + "\t file");
                }
            }
        }
        catalogContent = new String[counter][2];
        //System.out.printf("Число файлов %d\n", counter);
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
        return catalogContent;
    }

    public boolean editingPermission(boolean status) {
        return status;
    }
}
