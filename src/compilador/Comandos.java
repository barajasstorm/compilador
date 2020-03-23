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
public class Comandos {
    
    private String comando;
    private int numeroParametros;
    private boolean type0;
    private boolean type1;
    private boolean type2;
    private boolean type3;
    private boolean type4;
    private boolean type5;
    private boolean type6;
    private boolean type7;
    private boolean type8;
    private boolean type9;

    public Comandos() {
    }

    public Comandos(String comando, int numeroParametros, boolean type0, boolean type1, boolean type2, boolean type3, boolean type4, boolean type5, boolean type6, boolean type7, boolean type8, boolean type9) {
        this.comando = comando;
        this.numeroParametros = numeroParametros;
        this.type0 = type0;
        this.type1 = type1;
        this.type2 = type2;
        this.type3 = type3;
        this.type4 = type4;
        this.type5 = type5;
        this.type6 = type6;
        this.type7 = type7;
        this.type8 = type8;
        this.type9 = type9;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public int getNumeroParametros() {
        return numeroParametros;
    }

    public void setNumeroParametros(int numeroParametros) {
        this.numeroParametros = numeroParametros;
    }

    public boolean isType0() {
        return type0;
    }

    public void setType0(boolean type0) {
        this.type0 = type0;
    }

    public boolean isType1() {
        return type1;
    }

    public void setType1(boolean type1) {
        this.type1 = type1;
    }

    public boolean isType2() {
        return type2;
    }

    public void setType2(boolean type2) {
        this.type2 = type2;
    }

    public boolean isType3() {
        return type3;
    }

    public void setType3(boolean type3) {
        this.type3 = type3;
    }

    public boolean isType4() {
        return type4;
    }

    public void setType4(boolean type4) {
        this.type4 = type4;
    }

    public boolean isType5() {
        return type5;
    }

    public void setType5(boolean type5) {
        this.type5 = type5;
    }

    public boolean isType6() {
        return type6;
    }

    public void setType6(boolean type6) {
        this.type6 = type6;
    }

    public boolean isType7() {
        return type7;
    }

    public void setType7(boolean type7) {
        this.type7 = type7;
    }

    public boolean isType8() {
        return type8;
    }

    public void setType8(boolean type8) {
        this.type8 = type8;
    }

    public boolean isType9() {
        return type9;
    }

    public void setType9(boolean type9) {
        this.type9 = type9;
    }

    
    
    
    
    
}
