
class WrapperClass2{    //No access modifier can be specified here
   public int value;
   WrapperClass2(int a){ value=a; }
}

public interface Interface2
{
    void LoopDISP(WrapperClass2 wpcs);  //By default (and it can't be changed), all methods of an interface are public 
    //C++ const = Java final. All variables of an Interface are final by default
    int value=11;
    default double method2(int x,int y){
       return (double)(x/(y+x));
    }
}
