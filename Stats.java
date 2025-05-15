public class Stats implements Runnable {
    public void run() {
        try {
            // Se sigue ejecutando cada 10 segundos
            while (!Main.productorFinished || !sharedMemory.words.isEmpty()) {
                Thread.sleep(10000);  // Espera 10 segundos antes de mostrar las estadísticas

                // Sincronización para asegurar que no haya conflictos con otros hilos
                synchronized (sharedMemory.monitor) {
                    System.out.println("----- Estadísticas -----");
                    System.out.println("Líneas leídas: " + sharedMemory.linesRead.get());
                    System.out.println("Palabras en memoria: " + sharedMemory.words.size());
                    System.out.println("Palabras escritas: " + sharedMemory.linesWriten.get());
                    System.out.println("------------------------");
                }
            }
        } catch (InterruptedException e) {
            // Si el hilo es interrumpido, se finaliza de manera controlada
            System.out.println("Hilo de estadísticas interrumpido.");
        }
    }
}