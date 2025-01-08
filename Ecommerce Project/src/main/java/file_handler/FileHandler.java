package file_handler;
import java.io.*;
import java.util.Objects;

// singleton class
public class FileHandler {
    private FileOutputStream fileOutInventory, fileOutCustomer, fileOutOrder;
    private FileInputStream fileInInventory, fileInCustomer, fileInOrder;
    private static FileHandler instance;

    public static enum ObjectType {
        CUSTOMER, INVENTORY, ORDER
    }

    public static FileHandler getInstance() {
        if(Objects.isNull(instance)) {
            instance = new FileHandler();
        }
        return instance;
    }

    private FileHandler() {
        try{
            new File("customer.dat").createNewFile();
            new File("inventory.dat").createNewFile();
            new File("order.dat").createNewFile();

            fileInCustomer = new FileInputStream("customer.dat");
            fileOutCustomer = new FileOutputStream("customer.dat", true);

            fileInInventory = new FileInputStream("inventory.dat");
            fileOutInventory = new FileOutputStream("inventory.dat", true);

            fileInOrder = new FileInputStream("order.dat");
            fileOutOrder = new FileOutputStream("order.dat", true);
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public void writeObjectToFile(Object obj, ObjectType type) {
        try {
            ObjectOutputStream objectOut = switch (type) {
                case CUSTOMER -> new ObjectOutputStream(fileOutCustomer);
                case INVENTORY -> new ObjectOutputStream(fileOutInventory);
                case ORDER -> new ObjectOutputStream(fileOutOrder);
                default -> throw new IllegalArgumentException("Invalid object type");
            };

            objectOut.writeObject(obj);
            //objectOut.close();  // also closes the fileOut stream (don't use)
            //System.out.println("The Object was successfully written to a file");
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public Object readNext(ObjectType type) {
        try  {
            ObjectInputStream objectIn = switch (type) {
                case CUSTOMER -> new ObjectInputStream(fileInCustomer);
                case INVENTORY -> new ObjectInputStream(fileInInventory);
                case ORDER -> new ObjectInputStream(fileInOrder);
                default -> throw new IllegalArgumentException("Invalid object type");
            };
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
            fileInCustomer.close();
            fileOutCustomer.close();

            fileInInventory.close();
            fileOutInventory.close();

            fileInOrder.close();
            fileOutOrder.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteAllObjects(ObjectType type){
        try {
            switch (type){
                case ObjectType.ORDER: {
                    fileOutOrder.close();
                    fileOutOrder = new FileOutputStream("order.dat");
                } break;
                case ObjectType.INVENTORY: {
                    fileOutInventory.close();
                    fileOutInventory = new FileOutputStream("inventory.dat");
                } break;
                case ObjectType.CUSTOMER: {
                    fileOutCustomer.close();
                    fileOutCustomer = new FileOutputStream("customer.dat");
                } break;
                default: break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}