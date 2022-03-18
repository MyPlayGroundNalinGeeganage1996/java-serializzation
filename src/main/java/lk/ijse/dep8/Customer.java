package lk.ijse.dep8;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

public class Customer implements Serializable {
    private String id;
    private String name;
    private String address;
    private byte[] picture;
    private String picPath;



    public Customer() {
    }

    public Customer(String id, String name, String address,byte[] picture,String picPath) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.picture = picture;
        this.picPath = picPath;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", picture=" + Arrays.toString(picture) +
                '}';
    }

    public void printDetails(){
        System.out.printf("id = %s, name = %s, address = %s",id, name, address);
    }
//    private void writeObject(ObjectOutputStream oos){
//        System.out.println("Writing the customer");
//        try {
//            oos.writeObject(id);
//            oos.writeObject(name);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    private void readObject(ObjectInputStream ois){
//        System.out.println("Reading the customer");
//
//        try {
//            this.id = (String) ois.readObject();
//            this.name = (String) ois.readObject();
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }

//    }

}
