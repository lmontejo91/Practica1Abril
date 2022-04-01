/*
 * Clase principal (con función main) que se encargará de interactuar con el
 * usuario e interpretar los comandos (qué comando se pide, argumentos, etc.). Utilizará la segunda
 * clase para realizar las operaciones de gestión de archivos. Manejará todas las posibles excepciones.
 */
package com.mycompany.practica1abril;

import java.io.File;
import java.util.Scanner;

public class MiniTerminal {
    static Scanner teclado=new Scanner(System.in);
    static File carpeta_actual=new File("C:\\Users\\DAW\\PruebasMiniTerminal");
    //static File carpeta_actual=new File("C:\\Users\\LUCÍA\\Downloads\\PRUEBAS");
    static MiniFileManager ruta_actual=new MiniFileManager(carpeta_actual);
    
    public static void Menu(){
        String comando[], opcion;
        System.out.println("\nlopedevega@daw:~$ ");
        
        opcion=teclado.nextLine();
        
        comando=opcion.split(" ");
        
        realizarComando(comando);      
    }
    
    public static void realizarComando(String comando[]){
        switch(comando[0]){
            case "pwd":
                System.out.println(ruta_actual.getPWD());
                Menu();
            break;
            case "cd":
                if(comando[1].equals("..")){
                    try{
                        ruta_actual.changeDir(ruta_actual.getRuta().getParentFile());
                        System.out.println("Cambio de directorio exitoso. Ruta de trabajo actual: "+ruta_actual.getPWD());
                    }catch (FicheroNoEncontrado e){
                        System.err.println(e.getMessage());
                        e.printStackTrace();
                    }   
                }else{
                    try{
                        ruta_actual.changeDir(new File(comando[1]));
                        System.out.println("Cambio de directorio exitoso. Ruta de trabajo actual: "+ruta_actual.getPWD());
                    }catch (FicheroNoEncontrado e){
                        System.err.println(e.getMessage());
                        e.printStackTrace();
                    }     
                }    
                Menu();
            break;
            case "ls":
                ruta_actual.printList(false);
                Menu();
            break;
            case "ll":
                ruta_actual.printList(true);
                Menu();
            break;
            case "mkdir":
                try{
                    ruta_actual.makeDir(comando[1]);
                    System.out.println("Directorio creado exitosamente.");
                }catch (FicheroNoEncontrado e){
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
                
                Menu();
            break;
            case "rm":
                try{
                    if(!(ruta_actual.deleteDir(new File(comando[1])))){
                        System.out.println("No fue posible borrar el directorio ya que contiene subcarpetas en su interior.");
                    }else{
                        System.out.println("Directorio borrado éxitosamente.");
                    }
                }catch (FicheroNoEncontrado e){
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
                
                Menu();
            break;
            case "mv":
                try{
                    ruta_actual.moveFile(new File(comando[1]), new File(comando[2]));
                    System.out.println("Directorio movido o renombrado éxitosamente.");
                }catch (FicheroNoEncontrado e){
                    System.err.println(e.getMessage());
                    e.printStackTrace();
                }
                
                Menu();
            break;
            case "help":
                System.out.println(
                           "● pwd : Muestra cual es la carpeta actual.\n" +
                           "● cd <DIR> : Cambia la carpeta actual a ‘DIR’. Con .. cambia a la carpeta superior.\n" +
                           "● ls : Muestra la lista de directorios y archivos de la carpeta actual (primero directorios, luego archivos, ambos ordenados alfabéticamente).\n" +
                           "● ll : Como ls pero muestra también el tamaño y la fecha de última modificación.\n" +
                           "● mkdir <DIR> : Crea el directorio ‘DIR’ en la carpeta actual.\n" +
                           "● rm <FILE> : Borra ‘FILE’. Si es una carpeta, borrará primero sus archivos y luego la carpeta. Si tiene subcarpetas, las dejará intactas y mostrará un aviso al usuario.\n" +
                           "● mv <FILE1> <FILE2> : Mueve o renombra ‘FILE1’ a ‘FILE2’.\n" +
                           "● help : Muestra una breve ayuda con los comandos disponibles.\n" +
                           "● exit : Termina el programa.");   
                Menu();
            break;
            case "exit":
                              
            break;
            default:
                System.out.println("Comando introducido incorrecto. Inténtelo de nuevo.");
                Menu();
            break;
        }//FIN Switch;  
    }

    
    public static void main(String[] args) {
        System.out.println("--- Mini Terminal LINUX ---");
        Menu();
    }
}
