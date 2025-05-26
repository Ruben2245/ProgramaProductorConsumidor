import java.io.*;

public class Productor implements Runnable {
    private final String filePath;

    public Productor(String filePath) {
        this.filePath = filePath;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sharedMemory.linesRead.incrementAndGet();
                String words[] = line.split("\\s+");
                synchronized (sharedMemory.monitor) {
                    for (String word : words) {
                        if (!word.isEmpty()) {
                            sharedMemory.words.add(word);
                        }
                    }
                    sharedMemory.monitor.notifyAll();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }                       
}
