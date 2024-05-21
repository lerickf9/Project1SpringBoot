package erickcode.screenmatch;

import erickcode.screenmatch.model.DatosEpisodios;
import erickcode.screenmatch.model.DatosSerie;
import erickcode.screenmatch.model.DatosTemporada;
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
		var consumoApi= new ConsumoAPI();
		var json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&apikey=adcb4f89");
//		var json = consumoApi.obtenerDatos("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);

		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.println(datos);

		json = consumoApi.obtenerDatos("https://www.omdbapi.com/?t=Game%20of%20Thrones&Season=1&Episode=1&apikey=adcb4f89");
		DatosEpisodios episodios= conversor.obtenerDatos(json, DatosEpisodios.class);
		System.out.println(episodios);

		List<DatosTemporada> temporadas = new ArrayList<>();
		for (int i = 0; i <= datos.totalTemporadas(); i++) {
			json=consumoApi.obtenerDatos("https://www.omdbapi.com/?t=Game%20of%20Thrones&Season="+i+"&apikey=adcb4f89");
			var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
			temporadas.add(datosTemporadas);
		}
		temporadas.forEach(System.out::println);
	}
}
