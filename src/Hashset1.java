import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class Hashset1 {
	public static void main(String args[]) {
		Set<String> saiyans = new HashSet<String>();  //HashSet is actually and Implementation of the Set Interface
		saiyans.add("Kakarot");
		saiyans.add("Vegeta");
		saiyans.add("Broly");
		saiyans.add("Jiren");
		saiyans.add("Kakarot");  //HasSet doesn't allow duplicates. So it is not gonna add this to the saiyans set
		
		Set<String> angels = new LinkedHashSet<String>();  //HashSet doesn't guarantee that the elements will be stored in the same order that they inserted. But LinkedHashSet does
		angels.add("Whis");
		angels.add("Vados");
		angels.add("Korn");
		angels.add("Merno");
		
		/*
		 * HashSet uses a hashfunction to get a unique memory location for each and every element in the memory
		 * But LinkedHash set uses LinkedList in its internal working. That's why LinkedHashSet has linear time complexity and HashSet has constant time complexity
		 * HashSet doesn't guarantee that the elements will be stored in the same order that they inserted. But LinkedHashSet does
		 * Both LinkedHashSet and HashSet doesn't allow duplicate elements
		*/
		
		//Looping through a HashSet using a Iterator
		Iterator<String> name = angels.iterator();
		while(name.hasNext()) System.out.println(name.next());
		
		System.out.println("\n");
		
		//Looping using For Loop
		for (String fighters : saiyans) System.out.println(fighters);
		
		HashSet<String> warriors = new HashSet<String>();
		warriors.addAll(saiyans);  //Adds all the elements of saiyans. Similar to union
		warriors.addAll(angels);  //Adds all the elements of angels. Similar to union
		
		System.out.println("\n");
		for (String fighters : warriors) System.out.println(fighters);
		
		warriors.retainAll(saiyans);  //Removes all elements which are not common to warriors and saiyans. Similar to intersection
		
		System.out.println("\n");
		for (String fighters : warriors) System.out.println(fighters);
	}
}

/*Output
Whis
Vados
Korn
Merno


Kakarot
Jiren
Vegeta
Broly


Kakarot
Whis
Korn
Vados
Merno
Jiren
Vegeta
Broly


Kakarot
Jiren
Vegeta
Broly

*/
