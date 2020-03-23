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
public class CodigoHexa {
    
    private String memory;
    private int numberParams;
    private String command;
    private String param1;
    private String param2;

    public CodigoHexa() {
    }

    public CodigoHexa(int numberParams, String command) {
        this.numberParams = numberParams;
        this.command = command;
    }

    public CodigoHexa(int numberParams, String command, String param1) {
        this.numberParams = numberParams;
        this.command = command;
        this.param1 = param1;
    }
    
    

    public CodigoHexa(int numberParams, String command, String param1, String param2) {
        this.numberParams = numberParams;
        this.command = command;
        this.param1 = param1;
        this.param2 = param2;
    }
    
    

    public CodigoHexa(String memory, int numberParams, String command, String param1, String param2) {
        this.memory = memory;
        this.numberParams = numberParams;
        this.command = command;
        this.param1 = param1;
        this.param2 = param2;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public int getNumberParams() {
        return numberParams;
    }

    public void setNumberParams(int numberParams) {
        this.numberParams = numberParams;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }     
    
}
