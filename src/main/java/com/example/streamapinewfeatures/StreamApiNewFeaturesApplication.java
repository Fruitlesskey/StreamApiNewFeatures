package com.example.streamapinewfeatures;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StreamApiNewFeaturesApplication {

  public static void main(String[] args) {
    SpringApplication.run(StreamApiNewFeaturesApplication.class, args);

    StreamApiNewFeaturesApplication.streams();
  }

  public static void streams() {
    List<String> names = Arrays.asList("John", "Jane", "Joe", null, "Jack", null, "Jill");

    List<String> result = names.stream()
        .filter(name -> name != null)
        .toList();

    System.out.println(result);

    List<String> result2 = names.stream()
        .flatMap(Stream::ofNullable)
        .toList();

    System.out.println(result2);

    Stream.iterate(1, n -> n + 2)
        .limit(10)
        .forEach(System.out::println);

    List<Employee> employees = Arrays.asList(
        new Employee("Alice", 50000),
        new Employee("Bob", 60000),
        new Employee("Charlie", 70000),
        new Employee("David", 80000),
        new Employee("Eve", 90000),
        new Employee("Frank", 100_000),
        new Employee("Grace", 110_000),
        new Employee("Henry", 120_000)
    );

    Long collect = employees.stream()
        .mapToDouble(Employee::getSalary)
        .boxed()
        .collect(Collectors.collectingAndThen(
            Collectors.averagingDouble(Double::doubleValue),
            Math::round
        ));

    Double collect2 = employees.stream()
        .mapToDouble(Employee::getSalary)
        .boxed()
        .collect(Collectors.averagingDouble(Double::doubleValue)
        );

    System.out.println(collect);
    System.out.println(collect2);


    List<Integer> numbers = List.of(10, 8, 6, 5, 7, 9, 4, 3, 2, 1);

    List<Integer> integerList = numbers.stream()
        .takeWhile(num -> num < 5)
        .collect(Collectors.toList());
    System.out.println(integerList + "first");

    List<Integer> integerList2 = numbers.stream()
        .dropWhile(num -> num < 5)
        .collect(Collectors.toList());
    System.out.println(integerList2 + "second");

    List<Integer> integerList3 = numbers.stream()
        .dropWhile(num -> num < 3)
        .takeWhile(num -> num < 7)
        .toList();
    System.out.println(integerList3 + "third");

    Map<String, Integer> minMaxMap = numbers.stream()
        .collect(Collectors.teeing(
            Collectors.maxBy(Integer::compareTo)
            , Collectors.minBy(Integer::compareTo),
            (e1, e2) -> Map.of("max", e1.get(), "min", e2.get())));

    System.out.println(minMaxMap);

  }
  static class Employee {

    private String name;

    private int salary;

    public Employee(String name, int salary) {
      this.name = name;
      this.salary = salary;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public int getSalary() {
      return salary;
    }

    public void setSalary(int salary) {
      this.salary = salary;
    }
  }

}
