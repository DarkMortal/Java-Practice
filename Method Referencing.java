// Method Referencing using Functional interface

// functional interface: It is an interface that contains only one abstract method.
// It can have any number of default or static methods.

import java.util.function.BiFunction;

@FunctionalInterface
interface MyInterface {
    void display();
    default void print(Object x){
        System.out.println("Object: " + x);
    }
}

public class Main {
    @FunctionalInterface
    interface MaxNum {
        int MAX(int a, int b);
    }

    @FunctionalInterface
    interface AddNum {
        int ADD(int a, int b);
    }

    @FunctionalInterface
    interface isGreater {
        boolean greater(int a, int b);
    }

    public static void main(String[] args) {
        MyInterface myInterface = Main::welcome;
        myInterface.display();

        int a = 10, b = 20;
        MaxNum max = Math::max;
        AddNum add = Integer::sum;

        isGreater greater = (x, y) -> x > y;
        System.out.println("Sum: " + add.ADD(a, b));
        System.out.println("Max: " + max.MAX(a, b));
        System.out.printf("Is greater 1: %b" , greater.greater(a, b));

        BiFunction<Integer, Integer, Boolean> methodObject = (num1, num2) -> num1 < num2;
        isGreater newGreater = methodObject::apply;
        System.out.printf("\nIs greater 2: %b", newGreater.greater(a, b));
    }

    public static void welcome() {
        System.out.println("Hello World!");
    }
}

/*Output:
Hello World!
Sum: 30
Max: 20
Is greater 1: false
Is greater 2: true

 In the above example, we have created a functional interface  MyInterface  with a single abstract method  display() . We have implemented the  display()  method in the  Main  class.
 In the  main()  method, we have created an object of  MyInterface  and assigned the method reference of  display()  method to it.
 When we call the  display()  method using the object of  MyInterface , it will call the  display()  method of the  Main  class.
 Method Reference to an Instance Method of an Arbitrary Object of a Particular Type
 We can also refer to an instance method of an arbitrary object of a particular type using the  ClassName::instanceMethodName  syntax.
 */