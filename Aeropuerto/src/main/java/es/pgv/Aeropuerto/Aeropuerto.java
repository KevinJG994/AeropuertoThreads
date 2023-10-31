package es.pgv.Aeropuerto;

import java.util.concurrent.Semaphore;

public class Aeropuerto {

	private int SalonDePasajeros;
	private int ChekingDePasajeros;
	private int ControlDePasajeros;
	private int EmbarqueDePasajeros;
	private int ControlDeTripulacion;
	private int EmbarqueDeTripulacion;

	private final Semaphore semSalonPasajeros;
	private final Semaphore semChekingPasajeros;
	private final Semaphore semControlPasajeros;
	private final Semaphore semEmbarquePasajeros;
	private final Semaphore semControlTripulacion;
	private final Semaphore semEmbarqueTripulacion;
	private final Semaphore semEmbarqueP;
	private final Semaphore semEmbarqueT;
	
	private final Semaphore mutexSalon;
	private final Semaphore mutexCheking;
	private final Semaphore mutexControl;
	private final Semaphore mutexEmbarque;
	private final Semaphore mutexPasajeros;
	private final Semaphore mutexControlTrip;
	private final Semaphore mutexEmbarqueTrip;
	private final Semaphore mutexTripulacion;
	
	
	public Aeropuerto() {

		semSalonPasajeros = new Semaphore(100);
		semChekingPasajeros = new Semaphore(6, true);
		semControlPasajeros = new Semaphore(3, true);
		semEmbarquePasajeros = new Semaphore(100, true);
		semControlTripulacion = new Semaphore(2, true);
		semEmbarqueTripulacion = new Semaphore(4, true);
		semEmbarqueP = new Semaphore(0, true);
		semEmbarqueT = new Semaphore(0, true);
	
		mutexSalon = new Semaphore(1, true);
		mutexCheking = new Semaphore(1, true);
		mutexControl = new Semaphore(1, true);
		mutexEmbarque = new Semaphore(1, true);
		mutexPasajeros = new Semaphore(1, true);
		mutexControlTrip = new Semaphore(1, true);
		mutexEmbarqueTrip = new Semaphore(1, true);
		mutexTripulacion = new Semaphore(1, true);
	}

	
	public void Entrar_al_Salon_de_Pasajeros(String nombrePasajero) {
		try {
			semSalonPasajeros.acquire();
			mutexSalon.acquire();
			
			SalonDePasajeros++;
			System.out.println(nombrePasajero + " ha entrado al salón de pasajeros. - Pasajeros en el Salón: " + this.SalonDePasajeros);

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutexSalon.release();
			
		}
	}

	
	public void Acceder_al_Cheking_de_Pasajeros(String nombrePasajero) {
		try {
			semChekingPasajeros.acquire();
			mutexCheking.acquire();

				SalonDePasajeros--;
				ChekingDePasajeros++;
				System.out.println(nombrePasajero + " ha pasado al área de cheking de pasajeros. -- Pasajeros en el Cheking: " + this.ChekingDePasajeros);
				System.out.println(nombrePasajero + " ha entrado al salón de pasajeros. - Pasajeros en el Salón: " + this.SalonDePasajeros);
				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			semSalonPasajeros.release();
			mutexCheking.release();
			
		}
	}

	
	public void Acceder_al_Control_de_Pasajeros(String nombrePasajero) {
		try {
			semControlPasajeros.acquire();
			mutexControl.acquire();

				ChekingDePasajeros--;
				ControlDePasajeros++;
				System.out.println(nombrePasajero + " ha pasado al área de control de pasajeros. --- Pasajeros en el Control: " + this.ControlDePasajeros);
				mutexControl.release();
				
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mutexControl.release();
			semChekingPasajeros.release();
		}
	}

	
	public void Acceder_al_Embarque_de_Pasajeros(String nombrePasajero) {
		try {
			semEmbarquePasajeros.acquire();
			mutexEmbarque.acquire();
		
				ControlDePasajeros--;
				EmbarqueDePasajeros++;
				System.out.println(nombrePasajero + " ha pasado al área de embarque de pasajeros. ---- Pasajeros en el Embarque: " + this.EmbarqueDePasajeros);
						
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			mutexEmbarque.release();
			semControlPasajeros.release();
			semEmbarqueP.release();
		}
	}

	
	public void Acceder_al_Control_de_Tripulacion(String NombreTripulante) {
		try {
			semControlTripulacion.acquire();
			mutexControlTrip.acquire();
		
				ControlDeTripulacion++;
				System.out.println("El " + NombreTripulante + " ha entrado al control de tripulación." + " === Tripulantes en Control " + (this.ControlDeTripulacion));
						
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutexControlTrip.release();
		}
	}

	
	public void Acceder_al_Embarque_de_Tripulacion(String NombreTripulante) {
		try {
			semEmbarqueTripulacion.acquire();
			mutexEmbarqueTrip.acquire();
			
				ControlDeTripulacion--;
				EmbarqueDeTripulacion++;
				System.out.println("El " + NombreTripulante + " ha entrado a la zona de embarque de la tripulación." + " === Tripulantes en Embarque " + this.EmbarqueDeTripulacion);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semControlTripulacion.release();
			mutexEmbarqueTrip.release();
			semEmbarqueT.release();
		}
	}


	
	public void Embarcar_Tripulantes_Al_Vuelo(String NombreVuelo) {
		try {
			semEmbarqueT.acquire(4);
			mutexTripulacion.acquire();
			
			if (EmbarqueDeTripulacion == 4) {
				EmbarqueDeTripulacion = EmbarqueDeTripulacion - 4;
				System.out.println("El " + NombreVuelo + " ha embarcado a 4 tripulantes.");
			} 

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutexTripulacion.release();
			semEmbarqueTripulacion.release(4);			
		}
	}

	
	public void Embarcar_Pasajeros_Al_Vuelo(String NombreVuelo) {
		try {
			semEmbarqueP.acquire(70);
			mutexPasajeros.acquire();
						
			if (EmbarqueDePasajeros == 70) {
				EmbarqueDePasajeros = EmbarqueDePasajeros - 70;
				System.out.println("El " + NombreVuelo + " ha embarcado a 70 pasajeros.");
			} 

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			mutexPasajeros.release();
			semEmbarquePasajeros.release(70);			
		}
	}
}