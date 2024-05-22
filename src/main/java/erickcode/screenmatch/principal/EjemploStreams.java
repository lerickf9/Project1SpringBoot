package erickcode.screenmatch.principal;

import java.util.Arrays;
import java.util.List;

public class EjemploStreams {
    public void muestraEjemplo(){
        List<String> nombre = Arrays.asList("Erick", "Joudy", "Lia", "Gigi", "Blacky");
        nombre.stream()
                .sorted()
                .limit(4)
                .filter(n -> n.startsWith("E"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }

}
