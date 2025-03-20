write a method in java to compare two objects
In Java, comparing two objects can be approached in different ways depending on what aspects of the objects you're interested in 
comparing. Below are several methods for comparing objects:

1. **Using `equals()` Method**: The `equals()` method is used to check if two objects have the same data.

2. **Using `hashCode()` Method**: The `hashCode()` method can be used in conjunction with `equals()` to compare hash codes, which might 
be useful in certain contexts like collections (e.g., `HashMap`, `HashSet`).

3. **Using `compareTo()` Method**: This is applicable if the objects implement the `Comparable` interface. It allows for natural ordering 
of objects.

4. **Custom Comparison Logic**: You can write a custom method to compare specific attributes or properties of the objects.

Here's an example demonstrating these approaches:

```java
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 1. Override equals() method to compare two Person objects based on their data.
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Check for reference equality
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Person person = (Person) obj;
        return age == person.age && name.equals(person.name);
    }

    @Override
    public int hashCode() {
        // Override hashCode when equals is overridden to maintain the contract between them.
        return Objects.hash(name, age);
    }
    
    // 2. Implement Comparable interface for natural ordering based on age.
    @Override
    public int compareTo(Person other) {
        return Integer.compare(this.age, other.age);
    }

    // 3. Custom comparison method (optional)
    public boolean isSamePersonAs(Person other) {
        if (other == null) return false;
        return this.name.equals(other.name) && this.age == other.age;
    }

    public static void main(String[] args) {
        Person person1 = new Person("Alice", 30);
        Person person2 = new Person("Bob", 25);
        Person person3 = new Person("Alice", 30);

        // Using equals()
        System.out.println("person1.equals(person2): " + person1.equals(person2)); // false
        System.out.println("person1.equals(person3): " + person1.equals(person3)); // true

        // Using compareTo()
        System.out.println("person1.compareTo(person2): " + person1.compareTo(person2)); // positive number (30 > 25)
        System.out.println("person2.compareTo(person1): " + person2.compareTo(person1)); // negative number (25 < 30)

        // Using custom method
        System.out.println("Is same as: " + person1.isSamePersonAs(person3)); // true
    }
}
```

### Key Points:
- **`equals()` Method**: Always override `equals()` when you override `hashCode()`. The contract between these methods ensures that equal 
objects must have the same hash code.
- **`compareTo()` Method**: Use this for natural ordering of objects. It returns a negative integer, zero, or a positive integer as the 
first object is less than, equal to, or greater than the second.
- **Custom Comparison Logic**: Useful when you need specific criteria that aren't covered by `equals()`, `hashCode()`, or `compareTo()`.

Choose the method based on your specific requirements for comparison.

