// Implementación de un servidor HTTP
/** Alumnos: Joel Fuentes - Lenin Quiñones **/

import java.io.*;
import java.net.*;
import java.util.*;

//Hilo servido que atiende a un cliente 
//La función de este hilo es retornar el archivo solicitado
public class ServerThread extends Thread { 

  
    //socket enviado por el servidor para realizar la comunicacion
    private Socket socket; 

    // constructor
    public ServerThread(Socket socket) {
        super();
        this.socket= socket;
   
    }

    
    public void run() {
        DataInputStream in= null;
        String message;
        int size;
        byte[] w= new byte[100240];
        try {
            //obtener objeto getInputStream para realizar lecturas del socket
            in =new DataInputStream(socket.getInputStream());
            String linea;
            
                try {
                   
                    System.out.println(linea=in.readLine());
                    gestionarRespuesta(linea);
                   
                } catch (IOException e) {
				    //cerrar socket
					socket.close();
					 return;
                }
          //  }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    //Gestiona la respuesta GET del archivo solicitado
    public void gestionarRespuesta(String message) {
    	
    	if(message.contains("GET")){
    		StringTokenizer stk= new StringTokenizer(message, " ");
    		stk.nextToken();
    		String nombreArchivo=stk.nextToken();
      		//System.out.println(nombreArchivo.substring(1));
      		
      		try{
      		
      		OutputStream paraCliente = socket.getOutputStream();
      		InputStream file = new FileInputStream(nombreArchivo.substring(1));
			byte[] buffer = new byte[10240];
      		int bytes_read;
      		//enviar el archivo solicitado
      		while((bytes_read = file.read(buffer)) != -1)
        		paraCliente.write(buffer, 0, bytes_read);
    		
    		paraCliente.flush();
    		file.close();
    		paraCliente.close();
    		socket.close();
      		
      		}catch(java.io.IOException ioex) {
  System.out.println("se presento el error: "+ioex.toString());
}
		
    	}
        
        
    }

    
}
