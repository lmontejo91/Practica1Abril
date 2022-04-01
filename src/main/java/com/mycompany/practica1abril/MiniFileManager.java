/*
* Clase MiniFileManager : Tendrá los atributos y métodos que necesites para realizar las distintas
* operaciones relacionadas con la gestión de archivos. Necesitarás al menos un método por cada
* operación. Se anzará una excepción si se produce un error o la operación solicitada no es posible.
* Algunos ejemplos que podrías implementar:
*   ● String getPWD() : Devuelve una cadena de texto con la carpeta actual.
*   ● boolean changeDir(String dir) : Cambia la carpeta actual a dir . Devuelve ‘true’ si fué posible.
*   ● void printList(boolean info) : Muestra una lista con los directorios y archivos de la carpeta
*     actual. Si info es ‘true’ mostrará también su tamaño y fecha de modificación.
*   ● etc.
*/
package com.mycompany.practica1abril;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

public class MiniFileManager {
    File ruta;
    
    public MiniFileManager(File ruta){
        this.ruta=ruta;
    }

    public File getRuta() {
        return ruta;
    }

    public String getPWD() {
        return ruta.getAbsolutePath();
    }
    
    public boolean changeDir(File dir) throws FicheroNoEncontrado{
        if(!dir.exists()){
          throw new FicheroNoEncontrado("Ruta al fichero especificado no existe. No se pudo cambiar el directorio activo.");
        }
        ruta=dir;
        return ruta.exists();
    }
    
    public void printList(boolean info){
        File arbolDir[]=ruta.listFiles();
        if(arbolDir.length==0){
            System.out.println("--- Directorio vacío ---");
        }
        if(info==false){
            Arrays.sort(arbolDir);
            for(File s : arbolDir){
                if(s.isDirectory()){
                    System.out.println("[*] "+s.getName());
                }   
            }//Fin FOR Directories
            for(File s : arbolDir){
                if(s.isFile()){
                    System.out.println("[A] "+s.getName());
                }    
            }//Fin FOR Files
        }else{
            for(File s : arbolDir){
                if(s.isDirectory()){
                    System.out.println("[*] "+s.getName()+" --- Tamaño: "+s.length()+" --- Última modificación: "+new Date(s.lastModified()));
                }   
            }//Fin FOR Directories
            for(File s : arbolDir){
                if(s.isFile()){
                    System.out.println("[A] "+s.getName()+" --- Tamaño: "+s.length()+" --- Última modificación: "+new Date(s.lastModified()));
                }    
            }//Fin FOR Files
        }
    }
    
    public boolean makeDir(String dir) throws FicheroNoEncontrado{
        if(dir.contains("\\")){
            if(!((new File(dir)).getParentFile().exists())){
                throw new FicheroNoEncontrado("Ruta al fichero especificado no existe. Creación de directorio fallida.");
            }
            return (new File(dir)).mkdir(); //Para crear nuevo directorio dentro la ruta absoluta dada.
        }else{
            return new File(ruta.getAbsolutePath()+"\\"+dir).mkdir(); //Para crear nuevo directorio dentro del actual cuando la orden es dada mediante direccionamiento relativo.
        }
    }
    
    public boolean deleteDir(File dir) throws FicheroNoEncontrado{
        if(!(dir.toString().contains("\\"))){
           dir=new File(ruta.getAbsolutePath()+"\\"+dir);
        }
        if(!dir.exists()){
          throw new FicheroNoEncontrado("Ruta al fichero especificado no existe. No se pudo realizar la operación de borrar.");
        }
        File contenido[]=dir.listFiles();
        if(dir.listFiles().length!=0){
            for(File f : contenido){
                if(f.isDirectory()){
                    System.out.println("No es posible borrar el directorio seleccionado ya que contiene otros directorios o subcarpetas en su interior.");
                    return false;
                }
            }
            for(File f : contenido){
                f.delete();
            }
        }        
        return dir.delete();   
    }
    
    public boolean moveFile(File origen, File destino) throws FicheroNoEncontrado{
        if(!(origen.toString().contains("\\"))){
            origen=new File(ruta.getAbsolutePath()+"\\"+origen);
        }
        if(!(destino.toString().contains("\\"))){
            destino=new File(ruta.getAbsolutePath()+"\\"+destino);
        }
        
        if(!origen.exists() || !destino.getParentFile().exists()){
          throw new FicheroNoEncontrado("Ruta al fichero especificado no existe. No se pudo mover o renombrar el directorio.");
        }
        
        return origen.renameTo(destino);
    }
    
}//FIN Class
