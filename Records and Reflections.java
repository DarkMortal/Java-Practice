import java.lang.reflect.*;

// record is a new feature in Java 14. It is a class that is defined with a single line of code.
// it gives us all the features of a class without writing all the boilerplate code. like getters, setters, equals, hashcode, etc.
// attributes of a record are  immutable by default (private final) and cannot be changed even using reflection.
record Person(String name, int age) {

    // some input validation (optional)
    public Person {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }

    private boolean isAdult() {
        return this.age >= 18;
    }
}

public class ReflectionsExample {
    public static void main(String[] args) {
        try {
            // get the class object by name <?> denotes that it can be any type of class
            Class<?> personClass = Class.forName("Person");

            // isInstance: Determines if the specified Object is assignment-compatible with the object represented by this Class.
            // This method is the dynamic equivalent of the Java language instanceof operator.
            System.out.println(personClass.isInstance(new Person("Alice", 15)) ? "Person" : "Not a Person");

            // get the constructor of the class
            Constructor<?> constructor = personClass.getConstructor(String.class, int.class);
            // create an object of the class using reflection from the constructor
            Person person = (Person) constructor.newInstance("Alice", 15);
            System.out.println(person);
            System.out.println(person.getClass());

            Method isAdultMethod = personClass.getDeclaredMethod("isAdult");
            isAdultMethod.setAccessible(true);  // mandatory if method is private

            Field ageField = personClass.getDeclaredField("age");
            ageField.setAccessible(true);  // mandatory if field is private
            System.out.println(person.age() == (int) ageField.get(person));  // true

            // the invoke method is used to call the method on the object.
            // by default, it returns an object so we need to type cast it.
            System.out.println((boolean) isAdultMethod.invoke(person) ? "Adult" : "Not Adult");  // Not Adult

        } catch (ClassNotFoundException | NoSuchFieldException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}

/*Output
Person
Person[name=Alice, age=15]
class Person
true
Not Adult
*/

// reflection is not limited to records. it can be used with any class.