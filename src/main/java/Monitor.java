import java.util.concurrent.Semaphore;

public class Monitor {
    Semaphore nolleno, novacio;

    public Monitor(int n) {
        nolleno = new Semaphore(n);
        novacio = new Semaphore(0);
    }

    public void permiteinsertar() {
        try {
            nolleno.acquire(); //entra a nolleno, decrementa
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void permiteextraer() {
        //int unidad;
        try {

            novacio.acquire(); //entra al novacio decrementa contador
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void recursoinsertado() {
        try {
            novacio.release();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void recursoextraido() {
        //int unidad;
        try {
            nolleno.release(); //entra al novacio decrementa contador
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}


