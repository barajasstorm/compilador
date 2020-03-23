package compilador;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author juanba
 */
public class Compilador {
    
    //Matriz de memoria
    public static MatrizMemoria[][] memoryMatrix = new MatrizMemoria[16][16];
    
    
    //Lista que guarda codigo hexadecimal para imprimrlo
    public static ArrayList<CodigoHexa> codigoLista = new ArrayList<>();
    
    //Lista que guarda comandos validos para generar archivo de comandos limpio
    public static ArrayList<String> comandosValidos = new ArrayList<>();
    
    //Lista para guardar las etiquetas
    public static ArrayList<String> etiquetasList = new ArrayList<String>();
    
    //Lista para guardar los brincos y sus etiquetas a las cuales se traslada
    public static ArrayList<Brincos> brincosList = new ArrayList<>();
    
    //Lista con los registros validos
    public static ArrayList<String> registroList = new ArrayList<String>() {
        {
            add("AL");
            add("BL");
            add("CL");
            add("DL");
        }
    };
    
    //Lista con los valores validos
    public static ArrayList<String> valorList = new ArrayList<String>() {
        {
            //id from 0 to 255 represents all hexadecimals lowercase
            for (int i = 0; i < 256; i++) {
                String hex = String.format("%02X", i);
                //System.out.println("Hex: " + hex.toUpperCase());
                add(hex.toUpperCase());
            }
        }
    };
    
    //Estos son los valores validos para los comandos IN y OUT
    public static ArrayList<String> inputOutputList = new ArrayList<String>() {
        {
            add("00");
            add("01");
            add("02");
            add("03");
            add("04");
            add("05");
            add("06");
            add("07");
            add("08");
        }
    };

    //Todos los comandos validos y sus parametros que puede aceptar segun la matriz
    //	0	Registro	Registro
    //	1	Registro	Valor
    //	2	NULL	
    //	3	Valor	
    //	4	Registro	
    //	5	[Valor]	Registro
    //	6	Registro	[Valor]
    //	7	Registro	[Registro]
    //	8	[Registro]	Registro
    //	9	Etiqueta	
    public static ArrayList<Comandos> comandosList = new ArrayList<Comandos>() {
        {
            add(new Comandos("ADD", 2, true, true, false, false, false, false, false, false, false, false));
            add(new Comandos("AND", 2, true, false, false, false, false, false, false, false, false, false));
            add(new Comandos("CALL", 1, false, false, false, true, false, false, false, false, false, false));
            add(new Comandos("CLI", 0, false, false, true, false, false, false, false, false, false, false));
            add(new Comandos("CLO", 0, false, false, true, false, false, false, false, false, false, false));
            add(new Comandos("CMP", 2, true, true, false, false, false, false, false, false, false, false));
            add(new Comandos("DB", 1, false, false, false, true, false, false, false, false, false, false));
            add(new Comandos("DEC", 1, false, false, false, false, true, false, false, false, false, false));
            add(new Comandos("DIV", 2, true, true, false, false, false, false, false, false, false, false));
            add(new Comandos("HALT", 0, false, false, true, false, false, false, false, false, false, false));
            add(new Comandos("IN", 1, false, false, false, true, false, false, false, false, false, false));
            add(new Comandos("INC", 1, false, false, false, false, true, false, false, false, false, false));
            add(new Comandos("INT", 1, false, false, false, true, false, false, false, false, false, false));
            add(new Comandos("IRET", 0, false, false, true, false, false, false, false, false, false, false));
            add(new Comandos("JMP", 1, false, false, false, false, false, false, false, false, false, true));
            add(new Comandos("JNO", 1, false, false, false, false, false, false, false, false, false, true));
            add(new Comandos("JNS", 1, false, false, false, false, false, false, false, false, false, true));
            add(new Comandos("JNZ", 1, false, false, false, false, false, false, false, false, false, true));
            add(new Comandos("JO", 1, false, false, false, false, false, false, false, false, false, true));
            add(new Comandos("JS", 1, false, false, false, false, false, false, false, false, false, true));
            add(new Comandos("JZ", 1, false, false, false, false, false, false, false, false, false, true));
            add(new Comandos("MOV", 2, false, true, false, false, false, true, true, true, true, false));
            add(new Comandos("MUL", 2, true, true, false, false, false, false, false, false, false, false));
            add(new Comandos("NOP", 0, false, false, true, false, false, false, false, false, false, false));
            add(new Comandos("NOT", 1, false, false, false, false, true, false, false, false, false, false));
            add(new Comandos("OR", 2, true, false, false, false, false, false, false, false, false, false));
            add(new Comandos("ORG", 1, false, false, false, false, true, false, false, false, false, false));
            add(new Comandos("OUT", 1, false, false, false, true, false, false, false, false, false, false));
            add(new Comandos("POP", 1, false, false, false, false, true, false, false, false, false, false));
            add(new Comandos("POPF", 0, false, false, true, false, false, false, false, false, false, false));
            add(new Comandos("PUSH", 1, false, false, false, false, true, false, false, false, false, false));
            add(new Comandos("PUSHF", 0, false, false, true, false, false, false, false, false, false, false));
            add(new Comandos("RET", 0, false, false, true, false, false, false, false, false, false, false));
            add(new Comandos("ROL", 1, false, false, false, false, true, false, false, false, false, false));
            add(new Comandos("ROR", 1, false, false, false, false, true, false, false, false, false, false));
            add(new Comandos("SHL", 1, false, false, false, false, true, false, false, false, false, false));
            add(new Comandos("SHR", 1, false, false, false, false, true, false, false, false, false, false));
            add(new Comandos("STI", 0, false, false, true, false, false, false, false, false, false, false));
            add(new Comandos("SUB", 2, true, true, false, false, false, false, false, false, false, false));
            add(new Comandos("XOR", 2, true, false, false, false, false, false, false, false, false, false));
            add(new Comandos("END", 0, false, false, true, false, false, false, false, false, false, false));
        }
    };

    public static void main(String args[]) {
        
        //Llenamos la matriz con las memorias correspondientes
        int mem = 0;
        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 16; j++) {
                String hex = String.format("%02X", mem);
                MatrizMemoria temp = new MatrizMemoria(hex, "END");
                memoryMatrix[i][j] = temp;
                mem++;
            }
        }
    
        //Para mostrar la matriz de memorias
        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 16; j++) {
                //System.out.print("mem: " + memoryMatrix[i][j].getMemory() + "\t");
            }
            //System.out.println();
        }
        
        
        int numeroLinea = 0;
        String linea;
        boolean end = false;
        boolean error = false;

        try {
            File archivoFuente = new File("/Users/juanba/Documents/SoftwareSistemas/02TLIGHT2.ASM");
            FileReader fr = new FileReader(archivoFuente);
            BufferedReader br = new BufferedReader(fr);
            FileWriter archivoMaquina = new FileWriter("/Users/juanba/Documents/SoftwareSistemas/02Lightnuevo.ASM");
            PrintWriter pw = new PrintWriter(archivoMaquina);

            int memLocation = 0;
            while ((linea = br.readLine()) != null && end == false) {

                numeroLinea++;
                linea = linea.trim();
                
                //Si la linea esta vacia no entra al generador de codigo
                if (linea.length() > 0) {
                    
                    //Si la linea inicia con ; no entra al generador de codigo
                    if (!linea.startsWith(";")) {
                        
                        //Dividimos la linea en 2, donde el lado izquierdo del punto y coma es lo que necesitamos
                        for (String parts : linea.split(";")) {
                            //Este ciclo solo corre una vez

                            //Creamos variables locales
                            boolean comando = false;

                            //Dividimos cada linea en comando y parametros
                            String[] splitCommand = parts.split("\\s+");

                            //Verificamos si es comando o etiqueta
                            for (Comandos comand : comandosList) {
                                if (splitCommand[0].equals(comand.getComando())) {
                                    comando = true;
                                    break;
                                }
                            }

                            //Si no es un comando validamos la etiqueta
                            boolean comillas = false;
                            if (comando == false) {
                                for (int i = 0; i < parts.length(); i++) {
                                    if (parts.charAt(i) == ':') {
                                        comillas = true;
                                    }
                                }

                                //Validamos no se haya ingresado antes
                                if (comillas == true) {
                                    splitCommand[0] = splitCommand[0].substring(0, splitCommand[0].length() - 1);
                                    boolean dobleEtiqueta = false;
                                    for (String etiquet : etiquetasList) {
                                        etiquet = etiquet.toLowerCase();
                                        String splitCmmd = splitCommand[0].toLowerCase();

                                        if (splitCmmd.toLowerCase().equals(etiquet)) {
                                            System.out.println("La etiqueta " + splitCommand[0] + " en linea " + numeroLinea + " ya se ha ingresado antes.");
                                            dobleEtiqueta = true;
                                            error = true;
                                        }
                                    }

                                    //Si no se ha ingresado antes, agregar a lista
                                    if (dobleEtiqueta == false) {
                                        etiquetasList.add(splitCommand[0]);
                                        codigoLista.add(new CodigoHexa(0, "Etiqueta"));
                                    }

                                } else {
                                    System.out.println("La etiqueta o comando \"" + splitCommand[0] + "\" en linea " + numeroLinea + " no es valida.");
                                    error = true;
                                }
                            }

                            //Cargar todos los atributos del comando que encontramos a un objeto
                            if (comando) {
                                Comandos comandoParams = new Comandos();
                                for (Comandos command : comandosList) {
                                    if (splitCommand[0].equals(command.getComando())) {
                                        comandoParams = command;
                                    }
                                }

                                //Verificamos el tamano de splitCommand para asegurarnos cumpla con o sin parametros  
                                //Con dos parametros o un parametro
                                if (comandoParams.getNumeroParametros() == 2 || comandoParams.getNumeroParametros() == 1) {
                                    
                                    if (splitCommand.length == 2) {
                                        //Se calcula con parametros
                                        String[] params = splitCommand[1].split(",");

                                        //---------------------------------------------2 Parametros----------------------------------------------------//
                                        //Validamos cantidad de parametros
                                        if (comandoParams.getNumeroParametros() == 2) {
                                            if (params.length == 2) {
                                                //Se calcula con dos parametros                                                                                                  
                                                boolean valido = false;
                                                String param1 = params[0];
                                                String param2 = params[1];

                                                //Se agrega a lista generadora de codigo
                                                if (comandoParams.isType0()) {
                                                    if (validateType0(comandoParams.getComando(), param1, param2)) {
                                                        valido = true;
                                                    }
                                                }

                                                if (comandoParams.isType1()) {
                                                    if (validateType1(comandoParams.getComando(), param1, param2)) {
                                                        valido = true;
                                                    }
                                                }

                                                if (comandoParams.isType5()) {
                                                    if (validateType5(comandoParams.getComando(), param1, param2)) {
                                                        valido = true;
                                                    }
                                                }

                                                if (comandoParams.isType6()) {
                                                    if (validateType6(comandoParams.getComando(), param1, param2)) {
                                                        valido = true;
                                                    }
                                                }

                                                if (comandoParams.isType7()) {
                                                    if (validateType7(comandoParams.getComando(), param1, param2)) {
                                                        valido = true;
                                                    }
                                                }

                                                if (comandoParams.isType8()) {
                                                    if (validateType8(comandoParams.getComando(), param1, param2)) {

                                                        valido = true;
                                                    }
                                                }

                                                if (valido == true) {

                                                } else {
                                                    System.out.println("Parametros de comando " + splitCommand[0] + " en linea " + numeroLinea + " no son de acuerdo al formato valido para ese comando.");
                                                    System.out.println("Formatos validos: ");
                                                    if (comandoParams.isType0()) {
                                                        System.out.println("* Registro y Registro");
                                                    }

                                                    if (comandoParams.isType1()) {
                                                        System.out.println("* Registro y Valor");
                                                    }

                                                    if (comandoParams.isType5()) {
                                                        System.out.println("* [Valor] y Registro");
                                                    }

                                                    if (comandoParams.isType6()) {
                                                        System.out.println("* Registro y [Valor]");
                                                    }

                                                    if (comandoParams.isType7()) {
                                                        System.out.println("* Registro y [Registro]");
                                                    }

                                                    if (comandoParams.isType8()) {
                                                        System.out.println("* [Registro] y Registro");
                                                    }

                                                    System.out.println("");

                                                }

                                            } else {
                                                System.out.println(splitCommand[0] + " debe contener dos parametros.");
                                            }
                                        }

                                        //---------------------------------------------1 Parametro----------------------------------------------------//
                                        if (comandoParams.getNumeroParametros() == 1) {
                                            if (params.length == 1) {
                                                //Se calcula con un parametro
                                                boolean valido = false;
                                                String param = params[0];

                                                if (comandoParams.isType3()) {
                                                    if (validateType3(comandoParams.getComando(), param)) {
                                                        valido = true;
                                                    }
                                                }

                                                if (comandoParams.isType4()) {
                                                    if (validateType4(comandoParams.getComando(), param)) {
                                                        valido = true;
                                                    }
                                                }

                                                if (comandoParams.isType9()) {
                                                    if (validateType9(comandoParams.getComando(), param)) {
                                                        brincosList.add(new Brincos(splitCommand[0], param, numeroLinea));
                                                        valido = true;
                                                    }
                                                }

                                                if (valido == true) {

                                                } else {
                                                    System.out.println("Parametro de comando " + splitCommand[0] + " en linea " + numeroLinea + " no es de acuerdo al formato valido para ese comando.");
                                                    System.out.println("Formatos validos: ");

                                                    if (comandoParams.isType3()) {
                                                        System.out.println("* Valor 00 a 08");
                                                    }

                                                    if (comandoParams.isType4()) {
                                                        System.out.println("* Registro");
                                                    }

                                                    System.out.println("");

                                                }

                                            } else {
                                                System.out.println(splitCommand[0] + " solo debe contener un parametro.");
                                            }
                                        }

                                    } else {
                                        System.out.println("Comando " + splitCommand[0] + " en linea " + numeroLinea + " debe contener parametros.");
                                        error = true;
                                    }

                                }

                                //Sin parametros
                                if (comandoParams.getNumeroParametros() == 0) {

                                    if (splitCommand.length == 1) {
                                        if (comandoParams.isType2()) {
                                            if (validateType2(comandoParams.getComando())) {
                                                //Al llamar type2 se agrega a lista de comandos
                                            }
                                        }

                                    } else {
                                        System.out.println("Comando " + splitCommand[0] + " en linea " + numeroLinea + " no debe contener parametros.");
                                        error = true;
                                    }

                                }

                            }

                            //Guardar comandos
                            if (linea.startsWith("END")) {
                                comandosValidos.add(parts);
                                end = true;

                            } else {
                                comandosValidos.add(parts);
                            }

                            break;
                        }
                    }
                }
            }

            //Validar los brincos hagan referencia a una etiqueta valida
            boolean brincoValido = false;
            String etiquetaNoValida;

            for (Brincos brinco : brincosList) {

                //Recorremos todas las etiquetas
                for (String etiqueta : etiquetasList) {

                    //Comparamos la etiqueta del brinco con las etiquetas de nuestra lista
                    if (brinco.getEtiquetaBrinco().toLowerCase().equals(etiqueta.toLowerCase())) {
                        brincoValido = true;
                    }
                }

                if (!brincoValido) {
                    error = true;
                    System.out.println("Brinco a etiqueta " + brinco.getEtiquetaBrinco() + " en linea " + brinco.getNumeroLinea() + " no es valido.");
                }

            }

            if (end == false) {
                System.out.println("Falta mnemónico de finalización");
            }

            if (end == true && error == false) {

                for (String comando : comandosValidos) {
                    //System.out.println(comando);
                    pw.println(comando);
                }
                fr.close();
                archivoMaquina.close();

                archivoMaquina = new FileWriter("/Users/juanba/Documents/SoftwareSistemas/codigoMaquina.ASM");
                pw = new PrintWriter(archivoMaquina);

                //Genera codigo hexadecimal            
                int memoryLocation = 0;
                boolean memoryDislocation = false;

                for (CodigoHexa codigo : codigoLista) {                    
                    
                    if(codigo.getCommand().equals("CB") || codigo.getCommand().equals("00")) {
                        memoryLocation--;
                    }

                    if (codigo.getCommand().equals("Etiqueta")) {
                        memoryLocation++;
                        memoryDislocation = true;

                    } else {
                        //Comandos sin parametros
                        if (codigo.getNumberParams() == 0) {
                            
                            
                            
                            //imprimir en archivo
                            pw.print("[" + String.format("%02X", memoryLocation) + "] ");
                            pw.println(codigo.getCommand());
                            
                            //Agregamos a matriz de memoria
                            generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", codigo.getCommand());
                            memoryLocation++;
                            
                            
                        }

                        //Comandos con 1 parametro
                        if (codigo.getNumberParams() == 1) {
                            
                           
                            //Imprimir archivo
                            if (codigo.getCommand().equals("ORG")) {
                                memoryLocation = Integer.parseInt(codigo.getParam1(), 16);
                                

                            } else {

                                //Imprime memoria
                                pw.print("[" + String.format("%02X", memoryLocation) + "] ");
                                
                                //Agregar a matriz
                                generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", codigo.getCommand());
                                
                                //Incrementamos memoria
                                memoryLocation = memoryLocation + 2;

                                //Imprime comando
                                pw.print(codigo.getCommand() + " ");
                                
                                

                                if (codigo.getParam1().equals("AL") || codigo.getParam1().equals("BL") || codigo.getParam1().equals("CL") || codigo.getParam1().equals("DL")) {
                                    //Imprime param1
                                    if (codigo.getParam1().equals("AL")) {
                                        pw.println("00");
                                        //Agregar a matriz
                                        generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "00");
                                    }

                                    if (codigo.getParam1().equals("BL")) {
                                        pw.println("01");
                                        //Agregar a matriz
                                        generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "01");
                                    }

                                    if (codigo.getParam1().equals("CL")) {
                                        pw.println("02");
                                        //Agregar a matriz
                                        generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "02");
                                    }

                                    if (codigo.getParam1().equals("DL")) {
                                        pw.println("03");
                                        //Agregar a matriz
                                        generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "03");
                                    }
                                } else {
                                    pw.println(codigo.getParam1());
                                    //Agregar a matriz
                                    generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", codigo.getParam1());
                                }
                            }
                        }

                        //Cuando son 2 parametros
                        if (codigo.getNumberParams() == 2) {
                            

                            //Imprime memoria
                            pw.print("[" + String.format("%02X", memoryLocation) + "] ");
                            
                            //Agregar a matriz
                            generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", codigo.getCommand());
                            
                            memoryLocation = memoryLocation + 3;

                            //Imprime comando
                            pw.print(codigo.getCommand() + " ");

                            //Imprime param 1
                            if (codigo.getParam1().equals("AL") || codigo.getParam1().equals("BL") || codigo.getParam1().equals("CL") || codigo.getParam1().equals("DL")) {
                                //Imprime param1
                                if (codigo.getParam1().equals("AL")) {
                                    pw.print("00 ");
                                    //Agregar a matriz
                                    generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "00");
                                }

                                if (codigo.getParam1().equals("BL")) {
                                    pw.print("01 ");
                                    //Agregar a matriz
                                    generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "01");
                                }

                                if (codigo.getParam1().equals("CL")) {
                                    pw.print("02 ");
                                    //Agregar a matriz
                                    generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "02");
                                }

                                if (codigo.getParam1().equals("DL")) {
                                    pw.print("03 ");
                                    //Agregar a matriz
                                    generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "03");
                                }
                            } else {

                                pw.print(codigo.getParam1() + " ");
                                //Agregar a matriz
                                generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", codigo.getParam1());
                            }

                            //Imprime param2
                            if (codigo.getParam2().equals("AL") || codigo.getParam2().equals("BL") || codigo.getParam2().equals("CL") || codigo.getParam2().equals("DL")) {
                                //Imprime param1
                                if (codigo.getParam2().equals("AL")) {
                                    pw.println("00");
                                    //Agregar a matriz
                                    generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "00");
                                }

                                if (codigo.getParam2().equals("BL")) {
                                    pw.println("01");
                                    //Agregar a matriz
                                    generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "01");
                                }

                                if (codigo.getParam2().equals("CL")) {
                                    pw.println("02");
                                    //Agregar a matriz
                                    generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "02");
                                }

                                if (codigo.getParam2().equals("DL")) {
                                    pw.println("03");
                                    //Agregar a matriz
                                    generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", "03");
                                }
                            } else {
                                pw.println(codigo.getParam2());
                                //Agregar a matriz
                                generadorDeMatriz("[" + String.format("%02X", memoryLocation) + "] ", codigo.getParam2());
                            }
                        }

                        if(memoryDislocation) { 
                            memoryLocation--; 
                            memoryDislocation = false;
                        }
                        
                    }
                    

                }
                
                imprimirMatriz();
                fr.close();
                archivoMaquina.close();
                
            }


        } catch (Exception e3) {
            e3.printStackTrace();
        } finally {

        }

    }

    //Registro y Registro
    public static boolean validateType0(String comando, String param1, String param2) {
        boolean valid1 = false;
        boolean valid2 = false;
        boolean valid = false;

        //Validate param1
        for (String reg : registroList) {
            if (param1.equals(reg)) {
                valid1 = true;
                break;
            }
        }

        //Validate param2
        for (String reg : registroList) {
            if (param2.equals(reg)) {
                valid2 = true;
                break;
            }
        }

        if (valid1 && valid2) {

            //Generador de codigo
            if (comando.equals("ADD")) {
                codigoLista.add(new CodigoHexa(2, "A0", param1, param2));
            }

            if (comando.equals("SUB")) {
                codigoLista.add(new CodigoHexa(2, "A1", param1, param2));
            }

            if (comando.equals("MUL")) {
                codigoLista.add(new CodigoHexa(2, "A2", param1, param2));
            }

            if (comando.equals("DIV")) {
                codigoLista.add(new CodigoHexa(2, "A3", param1, param2));
            }

            if (comando.equals("CMP")) {
                codigoLista.add(new CodigoHexa(2, "DA", param1, param2));
            }

            if (comando.equals("AND")) {
                codigoLista.add(new CodigoHexa(2, "AA", param1, param2));
            }

            if (comando.equals("OR")) {
                codigoLista.add(new CodigoHexa(2, "AB", param1, param2));
            }

            if (comando.equals("XOR")) {
                codigoLista.add(new CodigoHexa(2, "AC", param1, param2));
            }

            valid = true;
        }

        return valid;
    }

    //Registro y Valor
    public static boolean validateType1(String comando, String param1, String param2) {
        boolean valid1 = false;
        boolean valid2 = false;
        boolean valid = false;

        //Validate param1
        for (String reg : registroList) {
            if (param1.equals(reg)) {
                valid1 = true;
                break;
            }
        }

        //Validate param2
        for (String val : valorList) {
            if (param2.equals(val)) {
                valid2 = true;
                break;
            }
        }

        if (valid1 && valid2) {

            //Generador de codigo
            if (comando.equals("ADD")) {
                codigoLista.add(new CodigoHexa(2, "B0", param1, param2));
            }

            if (comando.equals("SUB")) {
                codigoLista.add(new CodigoHexa(2, "B1", param1, param2));
            }

            if (comando.equals("MUL")) {
                codigoLista.add(new CodigoHexa(2, "B2", param1, param2));
            }

            if (comando.equals("DIV")) {
                codigoLista.add(new CodigoHexa(2, "B6", param1, param2));
            }

            if (comando.equals("CMP")) {
                codigoLista.add(new CodigoHexa(2, "DB", param1, param2));
            }

            if (comando.equals("MOV")) {
                codigoLista.add(new CodigoHexa(2, "D0", param1, param2));
            }

            valid = true;
        }

        return valid;
    }

    //Null
    public static boolean validateType2(String comando) {
        
        boolean valid = true;
        //Generador de codigo
        if (comando.equals("END")) {
            codigoLista.add(new CodigoHexa(0, "00"));
        }

        if (comando.equals("HALT")) {
            codigoLista.add(new CodigoHexa(0, "00"));
        }

        if (comando.equals("NOP")) {
            codigoLista.add(new CodigoHexa(0, "FF"));
        }

        if (comando.equals("PUSHF")) {
            codigoLista.add(new CodigoHexa(0, "EA"));
        }

        if (comando.equals("POPF")) {
            codigoLista.add(new CodigoHexa(0, "EB"));
        }

        if (comando.equals("RET")) {
            codigoLista.add(new CodigoHexa(0, "CB"));
        }

        if (comando.equals("IRET")) {
            codigoLista.add(new CodigoHexa(0, "CD"));
        }

        if (comando.equals("CLO")) {
            codigoLista.add(new CodigoHexa(0, "FE"));
        }

        if (comando.equals("STI")) {
            codigoLista.add(new CodigoHexa(0, "FC"));
        }

        if (comando.equals("CLI")) {
            codigoLista.add(new CodigoHexa(0, "FD"));
        }

        return valid;
    }

    //Valor 00 a 08
    public static boolean validateType3(String comando, String param) {
        boolean valid = false;

        for (String val : inputOutputList) {
            if (param.equals(val)) {
                valid = true;
                break;
            }
        }

        if (valid) {

            //Generador de codigo
            if (comando.equals("IN")) {
                codigoLista.add(new CodigoHexa(1, "F0", param));
            }

            if (comando.equals("OUT")) {
                codigoLista.add(new CodigoHexa(1, "F1", param));
            }

            if (comando.equals("CALL")) {
                codigoLista.add(new CodigoHexa(1, "CA", param));
            }

            if (comando.equals("INT")) {
                codigoLista.add(new CodigoHexa(1, "CC", param));
            }

            if (comando.equals("DB")) {
                boolean isString = false;
                for (int i = 0; i < param.length(); i++) {
                    if (param.charAt(i) == '"') {
                        isString = true;
                        break;
                    }
                }

                codigoLista.add(new CodigoHexa(1, (isString) ? "00" : param));
            }

        }

        return valid;
    }

    //Registro
    public static boolean validateType4(String comando, String param) {
        boolean valid = false;

        for (String val : registroList) {
            if (param.equals(val)) {
                valid = true;
                break;
            }
        }

        if (comando.equals("ORG")) {
            for (String val : valorList) {
                if (param.equals(val)) {
                    valid = true;
                    break;
                }
            }
        }

        if (valid) {

            //Generador de codigo
            if (comando.equals("INC")) {
                codigoLista.add(new CodigoHexa(1, "A4", param));
            }

            if (comando.equals("DEC")) {
                codigoLista.add(new CodigoHexa(1, "A5", param));
            }

            if (comando.equals("ORG")) {
                codigoLista.add(new CodigoHexa(1, "ORG", param));
            }

            if (comando.equals("SHL")) {
                codigoLista.add(new CodigoHexa(1, "9C", param));
            }

            if (comando.equals("SHR")) {
                codigoLista.add(new CodigoHexa(1, "9D", param));
            }

            if (comando.equals("ROL")) {
                codigoLista.add(new CodigoHexa(1, "9A", param));
            }

            if (comando.equals("ROR")) {
                codigoLista.add(new CodigoHexa(1, "9B", param));
            }

            if (comando.equals("PUSH")) {
                codigoLista.add(new CodigoHexa(1, "E0", param));
            }

            if (comando.equals("POP")) {
                codigoLista.add(new CodigoHexa(1, "E1", param));
            }

            if (comando.equals("NOT")) {
                codigoLista.add(new CodigoHexa(1, "AD", param));
            }

        }

        return valid;
    }

    //[Valor] y Registro
    public static boolean validateType5(String comando, String param1, String param2) {
        boolean valid = false;
        boolean valid1 = false;
        boolean valid2 = false;
        boolean corcheteIzq = false;
        boolean corcheteDer = false;

        for (int i = 0; i < param1.length(); i++) {
            if (param1.charAt(i) == '[') {
                corcheteIzq = true;
            }
            if (param1.charAt(i) == ']') {
                corcheteDer = true;
            }
        }

        if (corcheteIzq && corcheteDer) {
            String paramLimpio = param1.replaceAll("\\p{P}", "");

            for (String val : valorList) {
                if (paramLimpio.equals(val)) {
                    valid1 = true;
                    break;
                }
            }
        }

        for (String reg : registroList) {
            if (param2.equals(reg)) {
                valid2 = true;
                break;
            }
        }

        if (valid1 && valid2) {

            //Generador de codigo
            if (comando.equals("MOV")) {
                codigoLista.add(new CodigoHexa(2, "D2", param1.replaceAll("\\p{P}", ""), param2));
            }

            valid = true;
        }

        return valid;
    }

    //Registro y [Valor]
    public static boolean validateType6(String comando, String param1, String param2) {
        boolean valid = false;
        boolean valid1 = false;
        boolean valid2 = false;
        boolean corcheteIzq = false;
        boolean corcheteDer = false;

        for (String reg : registroList) {
            if (param1.equals(reg)) {
                valid1 = true;
                break;
            }
        }

        for (int i = 0; i < param2.length(); i++) {
            if (param2.charAt(i) == '[') {
                corcheteIzq = true;
            }
            if (param2.charAt(i) == ']') {
                corcheteDer = true;
            }
        }

        if (corcheteIzq && corcheteDer) {
            String paramLimpio = param2.replaceAll("\\p{P}", "");

            for (String val : valorList) {
                if (paramLimpio.equals(val)) {
                    valid2 = true;
                    break;
                }
            }
        }

        if (valid1 && valid2) {

            //Generador de codigo
            if (comando.equals("MOV")) {
                codigoLista.add(new CodigoHexa(2, "D1", param1, param2.replaceAll("\\p{P}", "")));
            }

            if (comando.equals("CMP")) {
                codigoLista.add(new CodigoHexa(2, "DC", param1, param2.replaceAll("\\p{P}", "")));
            }

            valid = true;
        }

        return valid;
    }

    //Registro y [Registro]
    public static boolean validateType7(String comando, String param1, String param2) {
        boolean valid = false;
        boolean valid1 = false;
        boolean valid2 = false;
        boolean corcheteIzq = false;
        boolean corcheteDer = false;

        for (String reg : registroList) {
            if (param1.equals(reg)) {
                valid1 = true;
                break;
            }
        }

        for (int i = 0; i < param2.length(); i++) {
            if (param2.charAt(i) == '[') {
                corcheteIzq = true;
            }
            if (param2.charAt(i) == ']') {
                corcheteDer = true;
            }
        }

        if (corcheteIzq && corcheteDer) {
            String paramLimpio = param2.replaceAll("\\p{P}", "");

            for (String reg : registroList) {
                if (paramLimpio.equals(reg)) {
                    valid2 = true;
                    break;
                }
            }
        }

        if (valid1 && valid2) {

            //Generador de codigo
            if (comando.equals("MOV")) {
                codigoLista.add(new CodigoHexa(2, "D3", param1, param2.replaceAll("\\p{P}", "")));
            }

            valid = true;
        }

        return valid;
    }

    //[Registro] y Registro
    public static boolean validateType8(String comando, String param1, String param2) {
        boolean valid = false;
        boolean valid1 = false;
        boolean valid2 = false;
        boolean corcheteIzq = false;
        boolean corcheteDer = false;

        for (int i = 0; i < param1.length(); i++) {
            if (param1.charAt(i) == '[') {
                corcheteIzq = true;
            }
            if (param1.charAt(i) == ']') {
                corcheteDer = true;
            }
        }

        if (corcheteIzq && corcheteDer) {
            String paramLimpio = param1.replaceAll("\\p{P}", "");

            for (String reg : registroList) {
                if (paramLimpio.equals(reg)) {
                    valid1 = true;
                    break;
                }
            }
        }

        for (String reg : registroList) {
            if (param2.equals(reg)) {
                valid2 = true;
                break;
            }
        }

        if (valid1 && valid2) {

            //Generador de codigo
            if (comando.equals("MOV")) {
                codigoLista.add(new CodigoHexa(2, "D4", param1.replaceAll("\\p{P}", ""), param2));
            }

            valid = true;
        }

        return valid;
    }

    //Etiqueta
    public static boolean validateType9(String comando, String param) {
        boolean valid = true;

        if (comando.equals("JMP")) {
            codigoLista.add(new CodigoHexa(1, "C0", "12"));
        }

        if (comando.equals("JZ")) {
            codigoLista.add(new CodigoHexa(1, "C1", "09"));
        }

        if (comando.equals("JNZ")) {
            codigoLista.add(new CodigoHexa(1, "C2", "04"));
        }

        if (comando.equals("JS")) {
            codigoLista.add(new CodigoHexa(1, "C3", "09"));
        }

        if (comando.equals("JNS")) {
            codigoLista.add(new CodigoHexa(1, "C4", "04"));
        }

        if (comando.equals("JO")) {
            codigoLista.add(new CodigoHexa(1, "C5", "09"));
        }

        if (comando.equals("JNO")) {
            codigoLista.add(new CodigoHexa(1, "C6", "04"));
        }

        return valid;
    }
    
    //Genarador de matriz
    public static void generadorDeMatriz(String memory, String comando){
        String memoryLimpio = memory.replaceAll("\\p{P}", "");
        System.out.println("Validando a : " + memoryLimpio);
        //System.out.println("Comando: " + comando);
        boolean encontrado = false;
        
        
        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 16; j++) {
                String temp = memoryMatrix[i][j].getMemory();
                
                char character1 = memoryMatrix[i][j].getMemory().charAt(0); 
                int ascii1 = (int) character1; 
                
                char character2 = memoryMatrix[i][j].getMemory().charAt(1); 
                int ascii2 = (int) character2; 
                
                System.out.println("ascii1: " + ascii1 + " ascii2: " + ascii2);
                
                char character3 = memoryLimpio.charAt(0); 
                int ascii3 = (int) character3; 
                
                char character4 = memoryLimpio.charAt(1);
                int ascii4 = (int) character4;
                
                System.out.println("ascii3: " + ascii3 + " ascii4: " + ascii4);
             
                System.out.println();
                
                if((ascii1 == ascii3) && (ascii3 == ascii4)) {
                    //System.out.println("True");
                    System.out.println("martrx: " + memoryMatrix[i][j].getMemory() + " temp: " + memoryLimpio);
                    memoryMatrix[i][j].setComando(comando);
                    encontrado = true;
                    break;
                }

            }
            if(encontrado) break;
        }        
    }
    
    public static void imprimirMatriz() {
        int colMem = 0;
        System.out.print("\t");
        for(int i = 0; i < 16; i++) {
            System.out.print(String.format("%X", i).toUpperCase() + "\t");
        }
        System.out.println();
        for(int i = 0; i < 16; i++) {
            for(int j = 0; j < 16; j++) {
                
                if(j == 0) {
                    System.out.print(String.format("%X", colMem).toUpperCase() + "0" + "\t" + memoryMatrix[i][j].getComando() + "\t");
                    colMem = colMem + 1;
                } else {
                    System.out.print(memoryMatrix[i][j].getComando() + "\t");
                }
            }
            System.out.println();
        }  
    }
    
    

}
