import entities.order.OrderHandler;
import modules.AdminScreen;
import modules.ScreenModule;
import modules.CustomerLoginScreen;

public class Main {
    private static final String password = "admin";

    public static void main(String[] args) {
        ScreenModule.clearScreen();
        System.out.println("Welcome to Online Shopping System\n1.Admin\n2.Customer\n3.Exit");
        int choice = ScreenModule.scanner.nextInt();
        switch (choice){
            case 1: {
                System.out.print("Enter password: ");
                if(!password.equals(ScreenModule.scanner.next())){
                    System.out.println("Invalid password");
                    return;
                } AdminScreen.startModule();
            }break;
            case 2: CustomerLoginScreen.startModule(); break;
            case 3: return;
            default: System.out.println("Invalid choice");
        }
        if(OrderHandler.updateFlag) OrderHandler.saveAllOrders();
    }
}