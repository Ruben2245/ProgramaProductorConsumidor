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
        System.out.println("Programa ejecutándose. Agrega palabras a 'entrada.txt'");
        
        // Mantener el programa corriendo indefinidamente
        // Solo los hilos trabajarán automáticamente
        productor.join();
        consumer.join();
    }
}