import java.io.*;

public class Consumidor implements Runnable {
    private final String filePath;

    public Consumidor(String filePath) {
        this.filePath = filePath;
    }

    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            while (true) {
                String palabra;

                synchronized (MemoriaCompartida.monitor) {
                    while (MemoriaCompartida.palabras.isEmpty()) {
                        if (Main.productorTerminado) return;
                        MemoriaCompartida.monitor.wait();
                    }
                    palabra = MemoriaCompartida.palabras.remove(0);
                }

                writer.write(palabra.toUpperCase());
                writer.newLine();
                MemoriaCompartida.palabrasEscritas.incrementAndGet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}