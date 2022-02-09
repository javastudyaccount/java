package test;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import main.ComparatorDemo;
import main.Person;

public class TestComparatorDemo {

	List<Person> people = Arrays.asList(new Person("Joe", 24), new Person("Peter", 18), new Person("Chris", 21),
			new Person("Deli", 12));

	@Test
	public void testComparatorDemo() {
		ComparatorDemo cpd = new ComparatorDemo();
		cpd.comparatorDemo(people);
	}

}
