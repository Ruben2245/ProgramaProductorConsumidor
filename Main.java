public class Main {
    public static volatile boolean productorFinished = false;

    public static void main(String[] args) throws Exception {
        String inputPath = "entrada.txt";
        String outputPath = "salida.txt";

        System.out.println("Iniciando programa de procesamiento de palabras...");
        
        Thread productor = new Thread(new Productor(inputPath));
        Thread consumer = new Thread(new Consumer(outputPath));
        Thread stats = new Thread(new Stats());

        stats.setDaemon(true);

        productor.start();
        consumer.start();
        stats.start();

        System.out.println("Todos los hilos iniciados correctamente.");

        productor.join();
        productorFinished = true;
        System.out.println("Productor ha terminado de leer el archivo de entrada.");

        synchronized (sharedMemory.monitor) {
            sharedMemory.monitor.notifyAll();
        }

        consumer.join();
        System.out.println("Consumidor ha terminado de procesar todas las palabras.");
        
        System.out.println("\n----- RESUMEN FINAL -----");
        System.out.println("Total de líneas leídas: " + sharedMemory.linesRead.get());
        System.out.println("Total de palabras procesadas y escritas: " + sharedMemory.linesWriten.get());
        System.out.println("Programa finalizado con éxito.");
    }
}