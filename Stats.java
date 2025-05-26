public class Stats implements Runnable {
    public void run() {
        try {
            while (!Main.productorFinished || !sharedMemory.words.isEmpty()) {
                Thread.sleep(10000); 

                synchronized (sharedMemory.monitor) {
                    System.out.println("----- Estadísticas -----");
                    System.out.println("Líneas leídas: " + sharedMemory.linesRead.get());
                    System.out.println("Palabras en memoria: " + sharedMemory.words.size());
                    System.out.println("Palabras escritas: " + sharedMemory.linesWriten.get());
                    
                    System.out.println("------------------------");
                }
            }
            
            System.out.println("----- Estadísticas Finales -----");
            System.out.println("Total líneas leídas: " + sharedMemory.linesRead.get());
            System.out.println("Total palabras escritas: " + sharedMemory.linesWriten.get());
            System.out.println("------------------------------");
            
        } catch (InterruptedException e) {
            System.out.println("Hilo de estadísticas interrumpido.");
        }
    }
}