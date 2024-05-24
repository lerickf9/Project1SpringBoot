package erickcode.screenmatch.principal;

import erickcode.screenmatch.model.DatosEpisodios;
import erickcode.screenmatch.model.DatosSerie;
import erickcode.screenmatch.model.DatosTemporada;
import erickcode.screenmatch.model.Episodio;
import erickcode.screenmatch.service.ConsumoAPI;
import erickcode.screenmatch.service.ConvierteDatos;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner teclado = new Scanner(System.in);

    private ConsumoAPI consumoApi = new ConsumoAPI();

    private final String URL_BASE = "https://www.omdbapi.com/?t=";

    private final String API_KEY = "&apikey=adcb4f89";

    private ConvierteDatos conversor = new ConvierteDatos();

    public void mostrarMenu() {

        //Busca los datos generales de las series
        System.out.println("Por favor escribe el nombre de la serie que desea buscar");
        var nombreSerie = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + API_KEY);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        //Busca los datos de todas las temporadas
        List<DatosTemporada> temporadas = new ArrayList<>();
        for (int i = 1; i <= datos.totalTemporadas(); i++) {
            json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+") + "&Season=" + i + API_KEY);
            var datosTemporadas = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporadas);
        }
//        temporadas.forEach(System.out::println);

        //Mostrar el titulo de los episodios para la temporadas
//        for (int i = 0; i < datos.totalTemporadas(); i++) {
//            List<DatosEpisodios> episodiosTemporada = temporadas.get(i).episodios();
//            for (int j = 0; j < episodiosTemporada.size(); j++) {
//                System.out.println(episodiosTemporada.get(j).titulo());
//            }
//        }

        //Funcion lambda
//        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));

        //Convertir toda la informacion en una lista del tipo datosEpisodio
        List<DatosEpisodios> datosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .collect(Collectors.toList());

        //Top 5 episodios
//        System.out.println("TOP 5 episodios");
//        datosEpisodios.stream()
//                .filter(e -> !e.evaluacion().equalsIgnoreCase("N/A"))
//                .peek(e -> System.out.println("Primer filtro (N/A)" + e))
//                .sorted(Comparator.comparing(DatosEpisodios::evaluacion).reversed())
//                .peek(e -> System.out.println("Segundo Ordenacion > a <" + e))
//                .map(e -> e.titulo().toUpperCase())
//                .peek(e -> System.out.println("Tercer Filtro mayuscula" + e))
//                .limit(5)
//                .forEach(System.out::println);

        //Convirtiendo los Datos  a una lista de tipo Episodio
        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d)))
                .collect(Collectors.toList());

//        episodios.forEach(System.out::println);

        //Busqueda de episodios a partir de  un año especifico
//        System.out.println("Indica el año del cual deseas ver los episodios");
//        var fecha = teclado.nextInt();
//        teclado.nextLine();
//
//        LocalDate fechaBusqueda = LocalDate.of(fecha, 1, 1);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-YYYY");
//        episodios.stream()
//                .filter(e -> e.getFechaLanzamiento() != null && e.getFechaLanzamiento().isAfter(fechaBusqueda))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                                "Episodio: " + e.getTitulo() +
//                                "Fecha de lanzamiento: " + e.getFechaLanzamiento().format(dtf)
//                ));

        //Busqueda de episodio por un pedazo de titulo
//        System.out.println("Ingrese el titulo del episodio que desea ver");
//        var pedazoTitulo = teclado.nextLine();
//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(pedazoTitulo.toUpperCase()))
//                .findFirst();
//        if(episodioBuscado.isPresent()){
//            System.out.println(" Episodio encontrado ");
//            System.out.println("Los datos son: "+ episodioBuscado.get());
//        }else{
//            System.out.println("¡Episodio no encontrado!");
//        }

        Map<Integer, Double> evaluacionesPorTemporada = episodios.stream()
                .filter(e -> e.getEvaluacion()> 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada,
                        Collectors.averagingDouble(Episodio::getEvaluacion)));
        System.out.println(evaluacionesPorTemporada);

        //Tengo error con DoubleSummaryStatistics cuando coloco Episodio::getEvaluacion()
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getEvaluacion() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getEvaluacion));
        System.out.println("Media de las evaluaciones: "+ est.getAverage());
        System.out.println("Epísodio mejor evaluado: "+ est.getMax());
        System.out.println("Epísodio Peor evaluado: "+ est.getMin());


//        DoubleSummaryStatistics est = episodios.doubleStream()
//                .filter(e -> e.getEvaluacion()> 0.0)
//                .collect(Collectors.summingDouble(Episodio::getEvaluacion));
//        System.out.println("Media de evaluacion " + est.getAverage());
//        System.out.println("Episodio Mejor evaluacion " + est.getAverage());
//        System.out.println("Episodio Peor evaluado" + est.getMin());
    }
}
