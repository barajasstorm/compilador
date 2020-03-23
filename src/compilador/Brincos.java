/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author juanba
 */
public class Brincos {
    
    private String brincoComando;
    private String etiquetaBrinco;
    private int numeroLinea;

    public Brincos() {
    }

    public Brincos(String brincoComando, String etiquetaBrinco, int numeroLinea) {
        this.brincoComando = brincoComando;
        this.etiquetaBrinco = etiquetaBrinco;
        this.numeroLinea = numeroLinea;
    }

    public String getBrincoComando() {
        return brincoComando;
    }

    public void setBrincoComando(String brincoComando) {
        this.brincoComando = brincoComando;
    }

    public String getEtiquetaBrinco() {
        return etiquetaBrinco;
    }

    public void setEtiquetaBrinco(String etiquetaBrinco) {
        this.etiquetaBrinco = etiquetaBrinco;
    }

    public int getNumeroLinea() {
        return numeroLinea;
    }

    public void setNumeroLinea(int numeroLinea) {
        this.numeroLinea = numeroLinea;
    }

    
    
    
}
