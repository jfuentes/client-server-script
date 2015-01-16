// Implementación de un cliente HTTP
/** Alumnos: Joel Fuentes - Lenin Quiñones **/

import java.io.*;
import java.net.*;
import  java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Cliente {
  public static void main(String[] args) {
    try {
      // Chequeo de parametros
      if ((args.length != 3))
        throw new IllegalArgumentException("Numero de argumentos incorrecto. \nUse: java Cliente <host> <archivo> <puerto>\n");
      
           
      StringTokenizer stk= new StringTokenizer(args[1],"/");
      String archivo="out";
      while(stk.hasMoreTokens())
      	archivo= stk.nextToken();
      
      FileOutputStream to_file= new FileOutputStream(archivo);
      URL url = new URL(args[0]);
      String protocol = url.getProtocol();
      if (!protocol.equals("http"))
        throw new IllegalArgumentException("La URL debe usar el protocolo http"); //validación del protocolo
      String host = url.getHost(); //obtenemos el puerto de la URL
      int port = url.getPort();
      if (port == -1) port = Integer.parseInt(args[2]);  // si no está en la URL la obtenemos del argumento
      String filename = args[1]; //obtenemos el archivo que recuperaremos del servidor
      System.out.println(filename);
      //abrimos un socket para establecer la comunicación a partir del host y puerto
      Socket socket = new Socket(host, port);
      InputStream from_server = socket.getInputStream();
      PrintWriter to_server =     new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
      
      // Enviamos la petición primitiva de solicitud HTTP GET con el archivo solicitado
      to_server.println("GET " + filename );
      to_server.flush();  // Send it right now!
      
      // Ahora corresponde leer la respuesta
      byte[] buffer = new byte[10240];
      int bytes_read;
      while((bytes_read = from_server.read(buffer)) != -1){
        to_file.write(buffer, 0, bytes_read);
      	System.out.print(new String(buffer));
      }
     
      to_file.close(); //cerramos el archivo
      
      
      //Ahora se analiza el archivo descargado
      //siempre y cuando sea una página html (caso básico porque puede ser php o jsp)
      if(archivo.contains(".html")){
      	FileReader html= new FileReader(archivo);
      	BufferedReader bf = new BufferedReader(html);
      	String sCadena;
      	//analizamos el código en busca de tags <img>
      	while ((sCadena = bf.readLine())!=null) {
	
			Pattern p = Pattern.compile("<img");
        	Matcher m = p.matcher(sCadena);
			if ( m.find() ){
            System.err.println("Las direcciones email no empiezan por punto o @");
            StringTokenizer st= new StringTokenizer(sCadena,".");
            String imagen =st.nextToken();
            StringTokenizer st2= new StringTokenizer(imagen,"\"");
            String fichero1=st2.nextToken();
            String fichero2 =st2.nextToken();
            System.out.println(fichero2);
            String extension =st.nextToken();
            
            StringTokenizer st3= new StringTokenizer(extension,"\"");
            extension =st3.nextToken();
            
            System.out.println(extension);
            
             //pedimos recursivamente las imagenes 
             args[1]="/"+fichero2+"."+extension;
             main(args);
			}
            			
		}
      }
      socket.close();
      to_server.close();
    }
    catch (Exception e) {   
      System.err.println(e);
      System.err.println("Error, Use: java Cliente <host> <archivo> <puerto>\n");
    }
  }
  
  
}