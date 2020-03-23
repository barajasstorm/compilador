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
public class MatrizMemoria {
    
    private String memory;
    private String comando;

    public MatrizMemoria() {
    }

    public MatrizMemoria(String memory, String comando) {
        this.memory = memory;
        this.comando = comando;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }
    
}
