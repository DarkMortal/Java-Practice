We can both declare and define methods in Abstract classes
But we can only declare methods in interfaces (this was the case until Java-1.7)
From Java-1.8, we can also define methods in interfaces using 'default' keyword

Another difference between interface and abstract class is that, 
abstract class is also a class after all,that's why only one abstract class can be extended (inherited at a time)
whereas multiple interfaces can be implemented at a time

Some things about 'final' keyword in Java:-
If a variable is declared as final, it can't be changed (similar to const in C++)
If a class is declared as final, it can't be extended
If a method inside a class is declared final, it can't be overridden 

Note:- If a class implements one or more Interfaces and doesn't define the unimplemented methods, it has to be declared abstract