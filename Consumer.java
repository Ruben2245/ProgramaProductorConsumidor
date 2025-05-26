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
            while (true) {
                String word = null; 
                synchronized (sharedMemory.monitor) {
                    while (sharedMemory.words.isEmpty()) {
                        sharedMemory.monitor.wait();
                    }

                    if (!sharedMemory.words.isEmpty()) {
                        word = sharedMemory.words.remove(0);
                        sharedMemory.lastWordWritten = word;
                    }
                }

                if (word != null) {
                    writer.write(word.toUpperCase());
                    writer.newLine();
                    writer.flush();
                    sharedMemory.linesWriten.incrementAndGet();
                    
                    synchronized (sharedMemory.monitor) {
                        sharedMemory.monitor.notifyAll();
                    }
                    
                    Thread.sleep(10000);
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}