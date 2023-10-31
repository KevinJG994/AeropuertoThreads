package es.pgv.Aeropuerto;

public class Avion extends Thread {
	
	
	private String Nombre;
	private Aeropuerto objAeropuerto;
	
	public Avion(String nombre, Aeropuerto aeropuerto) {
		this.Nombre = nombre;
		this.objAeropuerto = aeropuerto;
	}
	
	public Avion() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		try {
			    System.out.println("El " + this.Nombre + " está intentando embarcar a la tripulación.");
				objAeropuerto.Embarcar_Tripulantes_Al_Vuelo(this.Nombre);
				objAeropuerto.Embarcar_Pasajeros_Al_Vuelo(this.Nombre);
                Thread.sleep(4000);
                System.out.println("El " + this.Nombre + " ha despegado.");
		} catch (InterruptedException e) {
			System.out.println("El " + this.Nombre + " ha tenido un percanse.");
		}
	}

}