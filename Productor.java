import java.io.*;

public class Productor implements Runnable {
    private final String filePath;
    private long lastPosition = 0;

    public Productor(String filePath) {
        this.filePath = filePath;
    }

    public void run() {
        try {
            // Crear archivo si no existe
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            
            while (true) {
                // Si el archivo es más pequeño que la última posición, se reinició
                if (file.length() < lastPosition) {
                    lastPosition = 0;
                }
                
                // Si hay nuevo contenido
                if (file.length() > lastPosition) {
                    try (RandomAccessFile randomFile = new RandomAccessFile(filePath, "r")) {
                        randomFile.seek(lastPosition);
                        String line;
                        
                        while ((line = randomFile.readLine()) != null) {
                            if (!line.trim().isEmpty()) {
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
                        }
                        
                        lastPosition = randomFile.getFilePointer();
                    }
                }
                
                // Esperar antes de la siguiente verificación
                Thread.sleep(500);
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}