/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studyapp.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import studyapp.App;
import studyapp.data.Folders;


/**
 * 
 * @author Rashid Iqbal
 * @email v3rsion9@gmail.com
 * @github https://github.com/rashidcoder
 * 
 */


public class HDir {

    static void create() {
        String uri = "/studyapp/resources/inputData.txt";
        URL url = new HDir().getClass().getResource(uri);
        if (url != null) { //if there is a file in the location
            File f = new File(url.getFile());
            if (f.exists()) {
                try {
                    ObjectOutputStream in = new ObjectOutputStream(new FileOutputStream(f));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                //create file and then writes data.
            }
        } else {
            Path currentRelativePath = Paths.get(new HDir().getClass().getResource("/studyapp").getPath());
            String s = currentRelativePath.toAbsolutePath().toString();
            System.out.println("Current relative path is: " + s);

            File file = new File("/studyapp/abc.txt");

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
            for (Path name : dirs) {
                System.err.println(name + " .................. ");
            }

            try {
                Path path = Files.createDirectories(Paths.get("studyapp/data/test"));
                System.out.println("studyapp.App.start() .... just testing " + path.getRoot());
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }

            URL dir_url = ClassLoader.getSystemResource("/studyapp/resources");

            File dir;

        }

    }

    public static void createTreeDir() {
        boolean dirFlag = false;

        String std = new HDir().getClass().getResource("/studyapp").getPath().replace("file:/", "");

        try {
            for (String[] dirCollection : Folders.STRUCTURE) {
                for (String dir : dirCollection) {
                    File stockDir = new File(Folders.PARENT_DIR + "" + dir);
                    dirFlag = stockDir.mkdir();
                } 
            }
        } catch (SecurityException Se) {
            System.out.println("Error while creating directory in Java:" + Se);
        }

        if (dirFlag) {
            System.out.println("Directory created successfully");
        } else {
            System.out.println("Directory was not created successfully " + std);
        }
    }
}
