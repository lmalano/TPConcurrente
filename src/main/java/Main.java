public class Main {
    // Recursos rec;
    public static void main(String[] args) {
        int hilos = 20;
        int cantrec = 4;
        Recursos rec = new Recursos(cantrec);
        Monitor monitor = new Monitor(cantrec);
        Runnable productor = new Productor(monitor, rec);
        Runnable consumidor = new Consumidor(monitor, rec);
        Thread[] hilosproductores = new Thread[hilos];
        Thread[] hilosconsumidores = new Thread[hilos];


        for (int i = 0; i < hilos; i++) {
            hilosproductores[i] = new Thread(productor);
            hilosconsumidores[i] = new Thread(consumidor);
            hilosproductores[i].setName("Productor " + i);
            hilosconsumidores[i].setName("Consumidor " + i);
        }
        for (int i = 0; i < hilos; i++) {
            hilosproductores[i].start();
            hilosconsumidores[i].start();
        }

    }
}
