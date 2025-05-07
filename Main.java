public class Main {
    public static volatile boolean productorTerminado = false;

    public static void main(String[] args) throws Exception {
        String inputPath = "entrada.txt";
        String outputPath = "salida.txt";

        Thread productor = new Thread(new Productor(inputPath));
        Thread consumidor = new Thread(new Consumidor(outputPath));
        Thread estadisticas = new Thread(new Estadisticas());

        productor.start();
        consumidor.start();
        estadisticas.start();

        productor.join();
        productorTerminado = true;

        synchronized (MemoriaCompartida.monitor) {
            MemoriaCompartida.monitor.notifyAll();
        }

        consumidor.join();
    }
}