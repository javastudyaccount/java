package main;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class LexicographicComparator implements Comparator<Person> {
	@Override
	public int compare(Person a, Person b) {
		return a.name.compareToIgnoreCase(b.name);
	}
}

public class ComparatorDemo {
	public void comparatorDemo(List<Person> people) {
		Collections.sort(people, new LexicographicComparator());
		System.out.println(people);
	}
}
