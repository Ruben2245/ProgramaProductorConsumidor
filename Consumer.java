import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Consumer implements Runnable {
    private final String filePath;

    public Consumer(String filePath) {
        this.filePath = filePath;
    }

    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            while (!Main.productorFinished || !sharedMemory.words.isEmpty()) {
                String word = null;

                synchronized (sharedMemory.monitor) {
                    while (sharedMemory.words.isEmpty() && !Main.productorFinished) {
                        sharedMemory.monitor.wait();
                    }

                    if (!sharedMemory.words.isEmpty()) {
                        word = sharedMemory.words.remove(0);
                    }
                }

                if (word != null) {
                    writer.write(word.toUpperCase());
                    writer.newLine();
                    writer.flush();
                    sharedMemory.linesWriten.incrementAndGet();
                    Thread.sleep(10000); // Esperar 10 segundos por cada palabra
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}