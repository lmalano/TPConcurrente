package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LectorPipe {
    private File incYmarc;
    private Element tablaPosterior;
    private Element tablaPrevia;
    private Element tablaCombinada;
    private Element tablaMarcado;

    private File analisisInvariante;
    private Element tablaTInvariantes;
    private Element tablaPInvariantes;

    /*
        private int [][] incidenciaPosterior;
        private int [][] IncidenciaPrevia;
        private int [][] incidenciaCombinada;
        private int [][] marcados;
    */
    public LectorPipe() {
        try {
            String direccion = (new File(".")).getCanonicalPath() + "//PetriNet//incidencias.html"; //
            this.incYmarc = new File(direccion);
            Document doc = this.parsear(this.incYmarc);
            Elements tables;
            tables = doc.select("table");
            this.tablaPosterior = tables.first(); //I+
            this.tablaPrevia = tables.first().nextElementSibling(); //I-
            this.tablaCombinada = tables.first().nextElementSibling().nextElementSibling(); //I
            this.tablaMarcado = tables.first().nextElementSibling().nextElementSibling().nextElementSibling().nextElementSibling(); //M0

            String direccion2 = (new File(".")).getCanonicalPath() + "//PetriNet//analisisInvariante.html";
            this.analisisInvariante = new File(direccion2);
            Document doc2 = this.parsear(this.analisisInvariante);
            Elements tables2;
            tables2 = doc2.select("table");
            this.tablaTInvariantes = tables2.first();
            this.tablaPInvariantes = tables2.get(1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Element getSubTabla(Element tabla) {
        Elements tablasHijas = tabla.select("tr");
        Element tablas = tablasHijas.first().nextElementSibling();
        Element subtabla = tablas.select("tbody").first();
        return subtabla;
    }

    public int[][] getArreglo(Element subTabla, int filaInicial, int columnaInicial) {
        try {
            Elements filas = subTabla.select("tr");
            Elements columnas = filas.first().select("td");
            //System.out.println("Cantidad de filas = "+ filas.size());
            //System.out.println("Cantidad de columnas = "+ columnas.size());
            int[][] arreglo = new int[filas.size() - filaInicial][columnas.size() - columnaInicial];
            for (int i = filaInicial; i < filas.size(); i++) {
                columnas = filas.get(i).select("td");
                for (int j = columnaInicial; j < columnas.size(); j++) {
                    arreglo[i - filaInicial][j - columnaInicial] = Integer.parseInt(columnas.get(j).text());
                }
            }
            return arreglo;
        } catch (Exception e) {
            return null;
        }
    }

    public int[][] getIncidenciaPosterior() {
        Element subtabla = this.getSubTabla(this.tablaPosterior);
        return this.getArreglo(subtabla, 1, 1);
    }

    public int[][] getIncidenciaPrevia() {
        Element subtabla = this.getSubTabla(this.tablaPrevia);
        return this.getArreglo(subtabla, 1, 1);
    }

    public int[][] getIncidenciaCombinada() {
        Element subtabla = this.getSubTabla(this.tablaCombinada);
        return this.getArreglo(subtabla, 1, 1);
    }

    public int[][] getMarcados() {
        Element subtabla = this.getSubTabla(this.tablaMarcado);
        return this.getArreglo(subtabla, 1, 1);
    }

    public int[][] getTInvariantes() {
        return this.getArreglo(this.tablaTInvariantes, 1, 0);
    }

    public int[][] getPInvariantes() {
        return this.getArreglo(this.tablaPInvariantes, 1, 0);
    }

    public Document parsear(File file) {
        try {
            Document doc = Jsoup.parse(file, "UTF-8", "http://example.com/");
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> nombreTransiciones() {
        List<String> nombresTransiciones = new ArrayList<String>();
        Element fila = this.tablaPosterior.select("table tr").first().nextElementSibling().select("table tr").first();
        Elements nombres = fila.select("td");
        for (Element nombre :
                nombres) {
            nombresTransiciones.add(nombre.text().toString());
        }
        nombresTransiciones.remove(0);
        return nombresTransiciones;
    }

    public List<String> nombrePlazas() {
        List<String> nombrePlazas = new ArrayList<String>();
        Element fila = this.tablaMarcado.select("table tr").first().nextElementSibling().select("table tr").first();
        Elements nombres = fila.select("td");
        for (Element nombre :
                nombres) {
            nombrePlazas.add(nombre.text().toString());
        }
        nombrePlazas.remove(0);
        return nombrePlazas;
    }

    public int[][] getResultadoPInvariantes(){
        Document doc = this.parsear(this.analisisInvariante);
        String [] cast = doc.toString().split("<h3> P-Invariant equations </h3>");
        cast = cast[1].split("Analysis time:");
        cast = cast[0].split("<br>");
        List<String> ecuaciones= new ArrayList<String>();

        for (String cadena :
                cast) {
            if(cadena.trim().length()!=0){
                cast=cadena.trim().split("=");
                ecuaciones.add(cast[1].trim().toString());
            }
        }
        int [][] resultado = new int[ecuaciones.size()][1];
        for (int i = 0; i < ecuaciones.size(); i++) {
            resultado[i][0] = Integer.parseInt(ecuaciones.get(i));
        }
        return resultado;

    }

    /*
    public static void main(String[] args) {
        try {
            LectorPipe lectorPipe = new LectorPipe();

            System.out.println("Matriz Posterior = ---------------------------------------------------------------");
            Matriz matrizPost= new Matriz(lectorPipe.getIncidenciaPosterior());
            matrizPost.imprimir();
            System.out.println("Matriz Previa = ---------------------------------------------------------------");
            Matriz matrizPrev= new Matriz(lectorPipe.getIncidenciaPrevia());
            matrizPrev.imprimir();
            System.out.println("Matriz Posterior = ---------------------------------------------------------------");
            Matriz matrizComb= new Matriz(lectorPipe.getIncidenciaCombinada());
            matrizComb.imprimir();
            System.out.println("Matriz Marcados = ---------------------------------------------------------------");
            Matriz matrizMarcados= new Matriz(lectorPipe.getMarcados());
            matrizMarcados.imprimir();

            System.out.println("Matriz T Invariantes = ---------------------------------------------------------------");
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
     */

}
