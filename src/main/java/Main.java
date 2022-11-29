import utils.LectorPipe;
import utils.Matriz;

public class Main {
    // Recursos rec;
    public static void main(String[] args) {
      /*  int hilos = 20;
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
         */
        try {
            LectorPipe lectorPipe = new LectorPipe();

            System.out.println("Matriz Posterior (I+) = ---------------------------------------------------------------");
            Matriz matrizPost= new Matriz(lectorPipe.getIncidenciaPosterior());
            matrizPost.imprimir();
            System.out.println("Matriz Previa (I-) = ---------------------------------------------------------------");
            Matriz matrizPrev= new Matriz(lectorPipe.getIncidenciaPrevia());
            matrizPrev.imprimir();
            System.out.println("Matriz Incidencia (I) = ---------------------------------------------------------------");
            Matriz matrizComb= new Matriz(lectorPipe.getIncidenciaCombinada());
            matrizComb.imprimir();
            System.out.println("Matriz Marcados = ---------------------------------------------------------------");
            Matriz matrizMarcados= new Matriz(lectorPipe.getMarcados());
            matrizMarcados.imprimir();

            System.out.println("\nMatriz T Invariantes = ---------------------------------------------------------------");
            Matriz tInvariantes= new Matriz(lectorPipe.getTInvariantes());
            tInvariantes.imprimir();
            System.out.println("Matriz P Invariantes = ---------------------------------------------------------------");
            Matriz pInvariantes= new Matriz(lectorPipe.getPInvariantes());
            pInvariantes.imprimir();

            System.out.println("Cantidad de T Invariantes = "+ lectorPipe.getTInvariantes().length);
            System.out.println("Cantidad de transiciones = " + lectorPipe.getTInvariantes()[0].length);

            System.out.println("Resultado de los  P Invariantes = ---------------------------------------------------------------");
            Matriz resultadoInvariantes= new Matriz(lectorPipe.getResultadoPInvariantes());
            resultadoInvariantes.imprimir();
        } catch (Exception e) {
            System.err.println("Error al crear el lector");
        }





    }
}
