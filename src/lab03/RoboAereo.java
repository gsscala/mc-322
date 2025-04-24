// A classe RoboAereo herda da classe Robo e adiciona funcionalidades específicas para robôs que podem voar
public class RoboAereo extends Robo {
    // Declaração da variável privada altitude, que armazena a altura atual do robô aéreo
    private int altitude = 0;
    
    // Declaração da variável privada altitudeMaxima, que armazena a altura máxima permitida para o robô
    private int altitudeMaxima;

    // Método getter para a altitude do robô aéreo
    public int getAltitude() {
        return altitude;  // Retorna a altitude atual do robô
    }

    // Método setter para a altitude do robô aéreo
    public void setAltitude(int altitude) {
        this.altitude = altitude;  // Define a altitude do robô
    }

    // Método getter para a altitude máxima do robô aéreo
    public int getAltitudeMaxima() {
        return altitudeMaxima;  // Retorna a altitude máxima do robô
    }

    // Método setter para a altitude máxima do robô aéreo
    public void setAltitudeMaxima(int altitudeMaxima) {
        this.altitudeMaxima = altitudeMaxima;  // Define a altitude máxima do robô
    }

    // Construtor da classe RoboAereo, que inicializa o robô com nome, posição (X e Y), direção e altitude máxima
    public RoboAereo(String nome, int xIni, int yIni, String direcao, int altitudeMaxima) {
        super(nome, xIni, yIni, direcao);  // Chama o construtor da classe pai (Robo) para inicializar nome, posição e direção
        setAltitudeMaxima(altitudeMaxima);  // Define a altitude máxima para o robô aéreo
    }

    // Método para o robô aéreo subir, aumentando sua altitude
    public void subir(int deltaZ) {
        if (deltaZ >= 0)  
            setAltitude(getAltitude() + deltaZ);  // Aumenta a altitude do robô
        else
            System.out.println("O número não pode ser negativo!");  
    }

    // Método para o robô aéreo descer, diminuindo sua altitude
    public void descer(int deltaZ) {
        if (deltaZ >= 0)  
            setAltitude(getAltitude() - deltaZ);  // Diminui a altitude do robô
        else
            System.out.println("O número não pode ser negativo!");  
    }
}
