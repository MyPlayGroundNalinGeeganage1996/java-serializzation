package lk.ijse.dep8;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Deserialization {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Path filePath = Paths.get(System.getProperty("user.home"),
                "Desktop", "Serialization Demo", "Customer.dep8");
        if (!Files.exists(filePath)){
            System.err.println("No such file to read");
            return;
        }

        InputStream fis = Files.newInputStream(filePath);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Customer c = (Customer) ois.readObject();
        ois.close();

        c.printDetails();

    }
}
