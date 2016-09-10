/*
Esta clase contiene los metodos principales de busqueda de archivos. 
Recibe el nombre del archivo a buscar 
y cuando lo encuentre abre el explorador de windows mostrando el archivo.
--------------------------------------------------------------------------
This class includes the main search files procedure.
Get the file name to search and
when it finds it, open the windows explorer showing the file.
*/
package finder;

import java.awt.FileDialog;
import java.io.File;
import javax.swing.JFrame;

public class Buscador extends Thread{
    
    private String argF, argFile;
    private JFrame f;
    String s;
    int aux = 0;

    public Buscador(String argFichero, String argFile, JFrame f) {
        this.argF = argFichero;
        this.argFile = argFile;
        this.f = f;
        this.start();
    }
    
    
    public synchronized void buscar (String argFichero, File argFile, JFrame f) {
        String path;
        File[] lista = argFile.listFiles();
        if (lista != null) {
            for (File elemento : lista) {
                if (elemento.isDirectory() && !elemento.isHidden()) {
                    buscar (argF, elemento, f);
                }else if(elemento.isFile()){ 
                    path = elemento.getAbsolutePath();
                    try{ s = path.substring(path.lastIndexOf('\\') + 1, path.lastIndexOf('.')); }catch(Exception e){}
                    if (s.equalsIgnoreCase(argFichero)){
                        if(aux == 0){
                            FileDialog dialogoArchivo = new FileDialog(f,"", FileDialog.LOAD);
                            dialogoArchivo.setDirectory(elemento.getParentFile().toString());
                            dialogoArchivo.setVisible(true);
                        }
                        aux = 1;
                    this.interrupt();
                    }
                }
            }
        }
    }
    
    public void run(){
        buscar (argF, new File(argFile), f);
    }
}
