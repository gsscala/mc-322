package obstaculos;

import robos.Robo;

public class Obstaculo {
    private int posicaoX1;
    private int posicaoY1;
    private int posicaoX2;
    private int posicaoY2;
    private TipoObstaculo tipo;

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

    public boolean isBloqueiaPassagem() {
        return tipo.isBloqueiaPassagem();
    }

    public int getAltura() {
        return tipo.getAltura();
    }

    public boolean isLetal() {
        return tipo.isLetal();
    }

    public int getNivelUmidade() {
        return tipo.getNivelUmidade();
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

        if (getNivelUmidade() > 0) {
            robo.setBateria(robo.getBateria() - 2*getNivelUmidade());

            System.out.println("Você pisou em uma poça e se molhou!");

            if (robo.getBateria() < 0) {
                robo.setIsVivo(false);
                return true;
            }
        }

        return false;
    }
}
