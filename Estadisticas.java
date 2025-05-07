public class Estadisticas implements Runnable {
    public void run() {
        try {
            // Se sigue ejecutando cada 10 segundos
            while (!Main.productorTerminado || !MemoriaCompartida.palabras.isEmpty()) {
                Thread.sleep(10000);  // Espera 10 segundos antes de mostrar las estadísticas

                // Sincronización para asegurar que no haya conflictos con otros hilos
                synchronized (MemoriaCompartida.monitor) {
                    System.out.println("----- Estadísticas -----");
                    System.out.println("Líneas leídas: " + MemoriaCompartida.lineasLeidas.get());
                    System.out.println("Palabras en memoria: " + MemoriaCompartida.palabras.size());
                    System.out.println("Palabras escritas: " + MemoriaCompartida.palabrasEscritas.get());
                    System.out.println("------------------------");
                }
            }
        } catch (InterruptedException e) {
            // Si el hilo es interrumpido, se finaliza de manera controlada
            System.out.println("Hilo de estadísticas interrumpido.");
        }
    }
}