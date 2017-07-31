package hadoop.wordcount;

public class SingletonTime {
	
	private static SingletonTime instance = new SingletonTime(); 
	private long tiempoInicial;
	private long tiempoFinal;
	
	private SingletonTime() {
		
	}
	
	public synchronized static SingletonTime getInstance(){
		return instance;
	}

	public long getTiempoInicial() {
		return tiempoInicial;
	}

	public synchronized void setTiempoInicial(long tiempoInicial) {
		this.tiempoInicial = tiempoInicial;
	}

	public long getTiempoFinal() {
		return tiempoFinal;
	}

	public synchronized void setTiempoFinal(long tiempoFinal) {
		this.tiempoFinal = tiempoFinal;
	}
	
	public long calcularTiempo(){
		return tiempoFinal-tiempoInicial;
	}
	
}
