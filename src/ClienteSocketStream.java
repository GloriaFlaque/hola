import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteSocketStream {
	private Scanner sc = new Scanner(System.in);
	private Socket clientSocket;
	private InetSocketAddress addr;
	private InputStream is;
	private OutputStream os;
	private boolean live = true;
	String hjjhv;
	public void liveCicle() {
		try {
			conection();
			while(live) {
				write();
				if(live)
					read();
			}	
			close();	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void read() throws IOException {
		is = clientSocket.getInputStream();
		byte[] mensajeServidor = new byte[256];
		String mensajeBien = new String(mensajeServidor);
		mensajeBien = mensajeBien.trim();
		is.read(mensajeServidor);
		System.out.println("Mensaje recibido: " + mensajeBien);
	}
	
	public void write() throws IOException {
		os = clientSocket.getOutputStream();
		String mensaje = sc.nextLine();
		os.write(mensaje.getBytes());
		if(mensaje.equals("OF")) {
			live =false;
		}
		
		System.out.println("Mensaje enviado");
	}
	
	public void conection() throws IOException {
		System.out.println("Creando socket cliente");
		clientSocket = new Socket();
		System.out.println("Estableciendo la conexión");
		addr = new InetSocketAddress("localhost", 5555);
		clientSocket.connect(addr);
	}
	
	public void close() throws IOException {
		System.out.println("Cerrando el socket cliente");
		clientSocket.close();
		System.out.println("Terminado");
	}
	
	public static void main(String[] args) {
		ClienteSocketStream css = new ClienteSocketStream();
		css.liveCicle();
	}
}
