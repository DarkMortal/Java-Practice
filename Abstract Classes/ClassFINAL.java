import java.util.Scanner;
//Note:- If a class implements one or more Interfaces and doesn't define the unimplemented methods, it has to be declared abstract
public class ClassFINAL implements Interface1, Interface2
{
    private static Scanner obj=new Scanner(System.in); 
    
    public void LoopDISP(WrapperClass2 x){
       while(x.value>0){
          System.out.print(x.value+" ");
          x.value--;
       }
    }
    public int RecursiveADD(int x){
       if(x==1) return 1;
       else return (x+RecursiveADD(x-1));
    }
    
    public static void main(String args[]){
        try{
           int x,y;
           System.out.print("Enter the initalising value=");
           x=y=obj.nextInt();
           ClassFINAL THIS=new ClassFINAL();
           int z=THIS.RecursiveADD(x+y);
           System.out.println("SUM="+z);
           WrapperClass2 app=new WrapperClass2(value);
           THIS.LoopDISP(app);
           System.out.println("\nAPP VALUE="+app.value);
        }catch(Exception err){
          System.out.println(err);
        }
    }
}
