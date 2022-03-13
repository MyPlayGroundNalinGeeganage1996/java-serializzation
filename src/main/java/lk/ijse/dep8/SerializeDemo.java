package lk.ijse.dep8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SerializeDemo {




    public static void main(String[] args) throws IOException {
        Customer customer = new Customer("C001","kasun","Kolonna",new byte[10]);

        String homeDirPath = System.getProperty("user.home");
        Path dirPath = Paths.get(homeDirPath, "Desktop", "Serializable Demos");

        if (Files.isDirectory(dirPath)){
            Files.createDirectories(dirPath);
        }

        customer.printDetails();




    }
}
