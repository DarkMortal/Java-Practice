import java.util.Scanner;

/*We can only call variables by reference with the help of Wrapper classes. There's simply not other way in Java (atleast not now)*/

class WrapperClass{    //No access modifier can be specified here
   public int value;
   WrapperClass(int a){ value=a; }
}
public class CallByReference   //A single java file can't have more than 1 public class
{
    static void GO(WrapperClass x,int n){ x.value+=n;}
    private static Scanner obj=new Scanner(System.in); 
    public static void main(String args[]){
        System.out.print("Values of var and initalizer=");
        int a=obj.nextInt(); 
        int b=obj.nextInt();
        WrapperClass wp=new WrapperClass(a);
        GO(wp,b);
        System.out.println("Value of A="+a);
        System.out.println("Value of WrapperClass A="+wp.value);
    }
}


