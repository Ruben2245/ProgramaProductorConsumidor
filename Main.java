public class Main {
    public static volatile boolean productorFinished = false;

    public static void main(String[] args) throws Exception {
        String inputPath = "entrada.txt";
        String outputPath = "salida.txt";

        Thread productor = new Thread(new Productor(inputPath));
        Thread consumer = new Thread(new Consumer(outputPath));
        Thread stats = new Thread(new Stats());

        productor.start();
        consumer.start();
        stats.start();

        productor.join();
        productorFinished = true;

        synchronized (sharedMemory.monitor) {
            sharedMemory.monitor.notifyAll();
        }

        consumer.join();
    }
}