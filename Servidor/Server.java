// Implementación de un servidor HTTP
/** Alumnos: Joel Fuentes - Lenin Quiñones **/

import java.io.*;
import java.net.*;
import java.util.*;


public class Server {
	boolean interrupcion=false;
    ServerSocket     server;//socket para aceptar conexiones
        Socket           socket;//socket para atender a un cliente
    public void start(int port) {
        
        ServerThread thread;//thread que atiende a un cliente especifico
		
        try {
            server=new ServerSocket(port);
            
            while(true && !interrupcion) {
                try {
                    //aceptar cliente
                    socket= server.accept();
                    System.err.println("conectado");
 
                    
                    //crear un nuevo hilo que atiende a este cliente especifico
                    thread=new ServerThread(socket);
                    thread.start();
                } catch (IOException e) {
                	
                }
            }
        } catch (IOException ioe) {
            System.err.println(ioe);
        }
    }
    //funcion que maneja la interrupción
    public void terminarServer(){
    	try{
    	interrupcion=true;
    	System.out.println("Adios");
    	if(socket!=null)socket.close();
    	if(server!=null)server.close();
    	} catch (IOException e) {
    		System.err.println(e);
    		}
    	System.exit(0);
    }


    public static void main(String[] args) {
        Server server=new Server();
        int port=Integer.parseInt(args[0]);
        System.err.println("Servidor Ok en puerto:"+port);
        Interrupcion inter= new Interrupcion(server);
        inter.start();
        server.start(port);
       
              
    }
}
