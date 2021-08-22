package com.ulrich.matthiae.java;

import com.ulrich.matthiae.java.streams.model.Animal;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamsTest {

    public static final List<Animal> ANIMALS_1 = Arrays.asList(
            new Animal("Dog", "Woof", true),
            new Animal("Cow", "Moo", false)
    );
    public static final List<Animal> ANIMALS_2 = Arrays.asList(
            new Animal("Dog", "Woof", true),
            new Animal("Cow", "Moo", false),
            new Animal("Cat", "Meow", true),
            new Animal("Duck", "Quack", true)
    );
    public static final List<Animal> ANIMALS_3 = Arrays.asList(
            new Animal("Frog", "Croak", false),
            new Animal("Karen", "Manager!", false)
    );

    @Test
    public void basicFilterAndSort() {

        /* Here we inspect the stream by using peek as well as printing out the result*/
        ANIMALS_2.stream()
                .peek(System.out::println)
                .filter(animal -> animal.getSound().startsWith("M"))
                .peek(System.out::println)
                .sorted(Comparator.comparing(Animal::getType))
                .peek(System.out::println)
                .map(animal -> animal.getSound().equals("Moo"))
                .forEach(System.out::println);

        /* In a real world scenario you would probably do something closer to this */
        List<Animal> filteredAndSortedAnimals = ANIMALS_2.stream()
                .filter(animal -> animal.getSound().startsWith("M"))
                .sorted(Comparator.comparing(Animal::getType))
                .collect(Collectors.toList());
    }

    @Test
    public void flatMap() {

        Stream<List<Animal>> animalsStream = Stream.of(ANIMALS_1, ANIMALS_2, ANIMALS_3);

        /* Using distinct to get a unique list of animals */
        List<Animal> flatList = animalsStream
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(Animal::getType))
                .distinct()
                .collect(Collectors.toList());

        System.out.println(flatList);



        /* Using a set rather than distinct() to do the same, but now it isn't sorted anymore*/
        animalsStream = Stream.of(ANIMALS_1, ANIMALS_2, ANIMALS_3);

        Set<Animal> flatSet = animalsStream
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        System.out.println(flatSet);



        /* Specifying a TreeSet and custom comparator to get an ordered set */
        animalsStream = Stream.of(ANIMALS_1, ANIMALS_2, ANIMALS_3);

        Set<Animal> orderedFlatSet = animalsStream
                .flatMap(Collection::stream)
                .collect(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Animal::getType)))
                );

        System.out.println(orderedFlatSet);
    }

    @Test
    public void minMaxAndCount() {

        /* findAny() allows us to turn this into a parallel stream easily since findFirst will have a performance impact */
        Stream<Integer> intStream = Stream.iterate(0, i -> i + 9);

        Optional<Integer> found = intStream
                .limit(10000000)
                .filter(i -> i / 81 == 195644)
                .findAny();

        System.out.println(found.get());


        /* Count example */
        intStream = Stream.iterate(0, i -> i + 9);

        long count = intStream
                .limit(1000000)
                .filter(i -> i % 81 == 0)
                .count();

        System.out.println(count);


        /* Min example */
        intStream = Stream.iterate(0, i -> i + 9);

        long min = intStream
                .limit(1000000)
                .filter(i -> i % 81 == 0)
                .min(Comparator.comparingInt(i -> i))
                .get();

        System.out.println(min);

        /* Max example */
        intStream = Stream.iterate(0, i -> i + 9);

        long max = intStream
                .limit(1000000)
                .filter(i -> i % 81 == 0)
                .max(Comparator.comparingInt(i -> i))
                .get();

        System.out.println(max);
    }

    @Test
    public void customReduction() {

        /* Using a custom reduction to construct a brand new animal out of the stream of animals */
        Optional<Animal> newAnimal = ANIMALS_2.stream()
                .reduce((animal1, animal2) -> new Animal(
                        animal1.getType().substring(0, animal1.getType().length() - 1) + animal2.getType().substring(1, animal2.getType().length() - 1),
                        animal1.getSound().substring(1, animal1.getSound().length() - 1) + animal2.getSound().substring(0, animal2.getSound().length() - 1),
                        animal1.getFluffy() && animal2.getFluffy()
                ));

        System.out.println(newAnimal);
    }
}
