package modules;
import java.io.*;
import java.util.Objects;

// singleton class
public class FileHandler {
    private FileOutputStream fileOut;
    private FileInputStream fileIn;
    private static FileHandler instance;

    public static FileHandler getInstance() {
        if(Objects.isNull(instance)) {
            instance = new FileHandler();
        }
        return instance;
    }

    private FileHandler() {
        try{
            fileIn = new FileInputStream("data.dat");
            fileOut = new FileOutputStream("data.dat", true);
            //randomAccessFile = new RandomAccessFile("data.dat", "rw");
        }catch (FileNotFoundException e){
            try{
                new File("data.dat").createNewFile();
                fileIn = new FileInputStream("data.dat");
                fileOut = new FileOutputStream("data.dat", true);
                //randomAccessFile = new RandomAccessFile("data.dat", "rw");
            }catch (IOException ex){
                ex.printStackTrace();
                throw new RuntimeException("Error creating file");
            }
        }
    }

    public void writeObjectToFile(Object obj) {
        try {
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
            //objectOut.close();  // also closes the fileOut stream (don't use)
            //System.out.println("The Object was successfully written to a file");
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public Object readNext() {
        try  {
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            return objectIn.readObject();
        } catch (EOFException e) {
            return null; // End of file reached
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;  // Error in reading file
        }
    }

    public void close() {
        try {
            fileIn.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllObjects(){
        try {
            fileOut.close();
            fileOut = new FileOutputStream("data.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
