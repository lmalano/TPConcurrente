import java.util.concurrent.TimeUnit;

public class Recursos {
    int nrecursos, tam, frente, cola;
    int[] recurso;

    //nrecursos --> cant de rec tam--> cant de rec producidos
    public Recursos(int nrecursos) {
        this.nrecursos = nrecursos;
        this.tam = 0;
        this.frente = 0;
        this.cola = 0;
        this.recurso = new int[nrecursos];
    }

    public void insertar(int elem) {
        System.out.println("por producir, soy: " + Thread.currentThread().getName() + "; Recursos disponibles: " + tam);
        recurso[cola] = elem;
        cola = (cola + 1) % nrecursos;
        tam++;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // System.out.println("valor de cola : "+ cola +"\n valor de n :"+n);
        //  System.out.println("info del semaforo nolleno: "+ nolleno.toString());
        System.out.println("recurso producido por: " + Thread.currentThread().getName() + " Recursos disponibles: " + tam);
    }

    public int extraer() {
        int unidad;
        System.out.println("por consumir, soy: " + Thread.currentThread().getName() + "; Recursos disponibles: " + tam);
        // System.out.println("valor frente antes de extraer: "+ frente+"\n");
        unidad = recurso[frente];
        frente = (frente + 1) % nrecursos;
        tam--;
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //  System.out.println("valor de frente : "+ frente +"\nvalor de unidad :"+unidad);
        //System.out.println("info del semaforo novacio: "+ novacio.toString());
        System.out.println("recurso consumido por: " + Thread.currentThread().getName() + " Recursos disponibles: " + tam);
        return unidad;
    }
}
