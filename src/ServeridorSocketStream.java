import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ServeridorSocketStream {
	private Socket newSocket;
	private ServerSocket serverSocket;
	private InputStream is;
	private OutputStream os;
	private Scanner sc = new Scanner(System.in);
	private boolean live = true;
	
	public void liveCicle() {
	try {
			conection();
			while(live) {
				read();
				if(live)
					write();
			}
			close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void read() throws IOException {
		byte[] mensaje = new byte[256];
		is.read(mensaje);
		String mensajeBien = new String(mensaje);
		mensajeBien = mensajeBien.trim();
		System.out.println("Mensaje recibido: " + mensajeBien);
		if(mensajeBien.equals("OF")) {
			live = false;
		}
	}
	
	public void write() throws IOException {
		String mensajeCliente = sc.nextLine();
		os.write(mensajeCliente.getBytes());
	}
	
	public void conection() throws IOException {
		System.out.println("Creando el socket servidor");
		serverSocket = new ServerSocket();
		System.out.println("Realizando el bind");
		InetSocketAddress addr = new InetSocketAddress("localhost", 5555);
		serverSocket.bind(addr);
		System.out.println("Aceptando conexiones");
		newSocket = serverSocket.accept();
		System.out.println("Conexión recibida");
		is = newSocket.getInputStream();
		os = newSocket.getOutputStream();
	}
	public void close() throws IOException {
		System.out.println("Cerrando el nuevo socket");
		newSocket.close();
		System.out.println("Cerrando el socket servidor");
		serverSocket.close();
	}
	public static void main(String[] args) {
		ServeridorSocketStream sss = new ServeridorSocketStream();
		sss.liveCicle();
	}
}
