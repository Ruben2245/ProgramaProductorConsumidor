import java.io.*;

public class Productor implements Runnable {
    private final String filePath;

    public Productor(String filePath) {
        this.filePath = filePath;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                MemoriaCompartida.lineasLeidas.incrementAndGet();
                String[] palabras = linea.split("\\s+");
                synchronized (MemoriaCompartida.monitor) {
                    for (String palabra : palabras) {
                        if (!palabra.isEmpty()) {
                            MemoriaCompartida.palabras.add(palabra);
                        }
                    }
                    MemoriaCompartida.monitor.notifyAll();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}