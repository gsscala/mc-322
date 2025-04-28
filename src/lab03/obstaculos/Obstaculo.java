package obstaculos;

import robos.Robo;

public class Obstaculo {
    private int posicaoX1;
    private int posicaoY1;
    private int posicaoX2;
    private int posicaoY2;
    private TipoObstaculo tipo;

    // Constructor
    public Obstaculo(int posicaoX1, int posicaoY1, int posicaoX2, int posicaoY2, TipoObstaculo tipo) {
        this.posicaoX1 = posicaoX1;
        this.posicaoY1 = posicaoY1;
        this.posicaoX2 = posicaoX2;
        this.posicaoY2 = posicaoY2;
        this.tipo = tipo;
    }

    // Getters
    public int getPosicaoX1() {
        return posicaoX1;
    }

    public int getPosicaoY1() {
        return posicaoY1;
    }

    public int getPosicaoX2() {
        return posicaoX2;
    }

    public int getPosicaoY2() {
        return posicaoY2;
    }

    public TipoObstaculo getTipo() {
        return tipo;
    }

    // Setters
    public void setPosicaoX1(int posicaoX1) {
        this.posicaoX1 = posicaoX1;
    }

    public void setPosicaoY1(int posicaoY1) {
        this.posicaoY1 = posicaoY1;
    }

    public void setPosicaoX2(int posicaoX2) {
        this.posicaoX2 = posicaoX2;
    }

    public void setPosicaoY2(int posicaoY2) {
        this.posicaoY2 = posicaoY2;
    }

    public void setTipo(TipoObstaculo tipo) {
        this.tipo = tipo;
    }

    // Methods to access attributes from TipoObstaculo

    // Check if the obstacle blocks passage
    public boolean isBloqueiaPassagem() {
        return tipo.isBloqueiaPassagem();
    }

    // Get the height of the obstacle
    public int getAltura() {
        return tipo.getAltura();
    }

    // Get the coefficient of friction for the obstacle
    // public double getCoeficienteGrudento() {
    //     return tipo.getCoeficienteGrudento();
    // }

    // Check if the obstacle is lethal
    public boolean isLetal() {
        return tipo.isLetal();
    }

    public boolean dentroDosLimites(int x, int y, int altura) {
        return x >= posicaoX1 && x <= posicaoX2 && y >= posicaoY1 && y <= posicaoY2 && altura <= getAltura();
    }   

    public boolean handleColisao(Robo robo) {
        if (isBloqueiaPassagem()) {
           return true;
        }

        if (isLetal()) {
            robo.setIsVivo(false);
            return true;
        }


        return false;
    }   


    // Method to describe the obstacle
    // public String describe() {
    //     String descricao = "Obstáculo do tipo " + tipo.name() +
    //                        " [Altura: " + getAltura() + 
    //                        ", Bloqueia passagem: " + isBloqueiaPassagem() + 
    //                        ", Coeficiente Grudento: " + getCoeficienteGrudento() + 
    //                        ", Letal: " + isLetal() + "]";
    //     return descricao;
    // }

    // // Override toString for easier object representation
    // @Override
    // public String toString() {
    //     return describe();
    // }
}