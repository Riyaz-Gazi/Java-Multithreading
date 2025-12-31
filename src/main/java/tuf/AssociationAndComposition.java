package tuf;

import java.util.ArrayList;
import java.util.List;


// Association & Aggregation
class Employee {
    String name;

    public Employee(String name) {
        this.name = name;
    }
}

class Department {
    List<Employee> employees;

    public Department(List<Employee> employees) {
        this.employees = employees;
    }
}

// Composition
class Room {
    String name;

    public Room(String name) {
        this.name = name;
    }
}

class House {
    List<Room> rooms;

    public House() {
        rooms = new ArrayList<>();
        rooms.add(new Room("Living Room"));
        rooms.add(new Room("Bed Room"));
    }
}


public class AssociationAndComposition {
    public static void main(String[] args) {

    }
}
