/**
 * @(#)Interrupcion.java
 *
 *
 * @author 
 * @version 1.00 2010/10/23
 */


import java.util.*;
import java.io.*;

//Hilo servido que atiende a un cliente 
public class Interrupcion extends Thread { 

   
	Server miServer;
    // constructor
    public Interrupcion(Server s) {
        super();
        miServer=s;
    }

    
    public void run() {
    	try{
		BufferedReader lectura = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Para detener el servidor alguna letra");
		lectura.readLine();
		miServer.terminarServer();
    	}catch(IOException ioe){
    		System.out.print(ioe.toString());
    	}
    }
}