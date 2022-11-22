public class Productor implements Runnable {
    static int elemento;
    Monitor m;
    Recursos recursos;

    public Productor(Monitor m, Recursos recursos) {
        elemento = 1;
        this.m = m;
        this.recursos = recursos;
    }

    @Override
    public void run() {
        while (true) {
            m.permiteinsertar();
            recursos.insertar(elemento);
            elemento++;
            m.recursoinsertado();
        }
    }
}
