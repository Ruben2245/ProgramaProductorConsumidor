public class Stats implements Runnable {
    public void run() {
        try {
            while (true) {
                Thread.sleep(10000); 

                synchronized (sharedMemory.monitor) {
                    System.out.println("----- Estadísticas -----");
                    System.out.println("Líneas leídas: " + sharedMemory.linesRead.get());
                    System.out.println("Palabras en memoria: " + sharedMemory.words.size());
                    System.out.println("Palabras escritas: " + sharedMemory.linesWriten.get());
                    
                    if (sharedMemory.lastWordWritten != null) {
                        System.out.println("Última palabra: " + sharedMemory.lastWordWritten);
                    }
                    
                    System.out.println("------------------------");
                }
            }
            
        } catch (InterruptedException e) {
            System.out.println("Hilo de estadísticas interrumpido.");
        }
    }
}