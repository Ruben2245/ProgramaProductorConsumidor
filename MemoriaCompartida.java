import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoriaCompartida {
    public static final List<String> palabras = new ArrayList<>();
    public static final Object monitor = new Object();

    public static final AtomicInteger lineasLeidas = new AtomicInteger(0);
    public static final AtomicInteger palabrasEscritas = new AtomicInteger(0);
}