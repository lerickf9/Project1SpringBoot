package erickcode.screenmatch;

import erickcode.screenmatch.model.DatosEpisodios;
import erickcode.screenmatch.model.DatosSerie;
import erickcode.screenmatch.model.DatosTemporada;
import erickcode.screenmatch.principal.EjemploStreams;
import erickcode.screenmatch.principal.Principal;
import erickcode.screenmatch.service.ConsumoAPI;
import erickcode.screenmatch.service.ConvierteDatos;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.mostrarMenu();
//		EjemploStreams ejemploStreams = new EjemploStreams();
//		ejemploStreams.muestraEjemplo();

	}
}
