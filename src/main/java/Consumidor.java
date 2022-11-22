public class Consumidor implements Runnable {
    Monitor m;
    Recursos recursos;

    public Consumidor(Monitor m, Recursos recursos) {
        this.m = m;
        this.recursos = recursos;
    }

    @Override
    public void run() {
        while (true) {
            int extraido;
            m.permiteextraer();
            extraido = recursos.extraer();
            m.recursoextraido();
        }
    }
}
