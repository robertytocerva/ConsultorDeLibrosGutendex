package com.robertytocerva.literalura;

import com.robertytocerva.literalura.service.LibraryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final LibraryService libraryService;

	public LiteraluraApplication(LibraryService libraryService) {
		this.libraryService = libraryService;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Scanner sc = new Scanner(System.in);
		int opt = -1;
		while (opt != 0) {
			System.out.println("""
                ===== LITERALURA =====
                1 - Buscar libro por título (Gutendex) y guardar
                2 - Listar libros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos en un año
                5 - Listar libros por idioma
                0 - Salir
                Seleccione una opción: """);
			String input = sc.nextLine().trim();
			if (input.isEmpty()) continue;
			try { opt = Integer.parseInt(input); } catch (NumberFormatException e) { continue; }

			switch (opt) {
				case 1 -> {
					System.out.print("Título a buscar: ");
					String title = sc.nextLine().trim();
					libraryService.searchAndSaveByTitle(title);
				}
				case 2 -> libraryService.printAllBooks();
				case 3 -> libraryService.printAllAuthors();
				case 4 -> {
					System.out.print("Año (YYYY): ");
					String y = sc.nextLine().trim();
					try {
						int year = Integer.parseInt(y);
						libraryService.printAuthorsAliveIn(year);
					} catch (NumberFormatException e) {
						System.out.println("Año inválido");
					}
				}
				case 5 -> {
					System.out.print("Código de idioma (ej: en, es, fr, pt): ");
					String lang = sc.nextLine().trim().toLowerCase();
					libraryService.printBooksByLanguage(lang);
				}
				case 0 -> System.out.println("Fin.");
				default -> System.out.println("Opción inválida");
			}
			System.out.println();
		}
	}
}
