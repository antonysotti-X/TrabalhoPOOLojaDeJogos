package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Transformar {
    public static <T, R> List<R> transformar(
            List<T> elementos,
            Function<T, R> funcao) {

        List<R> resultado = new ArrayList<>();

        for (T elemento : elementos) {
            resultado.add(funcao.apply(elemento));
        }

        return resultado;
    }
}
