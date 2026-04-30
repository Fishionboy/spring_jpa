package com.javier.jpa_app;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import com.javier.jpa_app.entities.Person;
import com.javier.jpa_app.repositories.PersonRepository;

@SpringBootApplication
public class JpaAppApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(JpaAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// findOne();
		update();
	}

	@Transactional
	public void update(){
		Scanner scanner = new Scanner (System.in);
		System.out.println("Ingrese el id de la persona...");
		Long id= scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresent(person -> {
			System.out.println(person);
			System.out.println("Ingrese en lenguaje de programación");
			String programmingLanguage = scanner.next();
			person.setProgrammingLanguage(programmingLanguage);
			Person personDb = repository.save(person);
			System.out.println(personDb);

		});

		scanner.close();

	}

	@Transactional
	public void create() {
		Scanner scanner = new Scanner (System.in);
		System.out.println("Ingrese el nombre de la persona...");
		String name = scanner.next();
		System.out.println("Ingrese el apellido de la persona...");
		String lastname = scanner.next();
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name, lastname, programmingLanguage);

		Person personNew = repository.save(person);

		System.out.println(personNew);

		repository.findById(personNew.getId()).ifPresent(System.out::println);

	}

	@Transactional(readOnly = true)
	public void findOne() {
		// Person person = null;
		// Optional<Person> optionalPerson = repository.findById(1L);
		// if(optionalPerson.isPresent()){
		// person= optionalPerson.get();
		// System.out.println(person);
		// }

		repository.findById(1L).ifPresent(System.out::println);

	}

	public void list() {
		// List<Person> persons = (List<Person>) repository.findAll();
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguage("Java");
		// List<Person> personsBuscar = (List<Person>) repository.byscarByProgrammingLanguage("Java", "Andres");

		persons.stream().forEach(p -> System.out.println(p));

		List<Object[]> personsValues = repository.obtenerPersonData();
		personsValues.stream().forEach(p -> {
			System.out.println(p[0] + " otro texto " + p[1] + " algun texto");
		});
	}

}
