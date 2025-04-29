package sensores;

import robos.Robo;


public class Sensor {
    
    private double raio;
    
    protected Robo robo; 

    public Sensor(double raio) {
        this.raio = raio;
    }

    // Getter and Setter for raio
    public double getRaio() {
        return raio;
    }

    public void setRaio(double raio) {
        this.raio = raio;
    }

    // Getter and Setter for robo
    public Robo getRobo() {
        return robo;
    }

    public void setRobo(Robo robo) {
        this.robo = robo;
    }
}

