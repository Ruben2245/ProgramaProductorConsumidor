import java.io.*;

public class Consumer implements Runnable {
    private final String filePath;

    public Consumer(String filePath) {
        this.filePath = filePath;
    }

    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            while (true) {
                String word;

                synchronized (sharedMemory.monitor) {
                    while (sharedMemory.words.isEmpty()) {
                        if (Main.productorFinished) return;
                        sharedMemory.monitor.wait();
                    }
                    word = sharedMemory.words.remove(0);
                }

                writer.write(word.toUpperCase());
                writer.newLine();
                sharedMemory.linesWriten.incrementAndGet();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}