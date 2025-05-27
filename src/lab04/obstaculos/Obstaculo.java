package obstaculos;

import robos.EstadoRobo;
import robos.Robo;
import entity.*;

public class Obstaculo implements Entidade {
    private int posicaoX1;
    private int posicaoY1;
    private int posicaoX2;
    private int posicaoY2;
    private TipoObstaculo tipoObstaculo;
    private TipoEntidade tipoEntidade = TipoEntidade.OBSTACULO;
    private char representacao = 'X';

    public Obstaculo(int posicaoX1, int posicaoY1, int posicaoX2, int posicaoY2, TipoObstaculo tipoObstaculo) {
        this.posicaoX1 = posicaoX1;
        this.posicaoY1 = posicaoY1;
        this.posicaoX2 = posicaoX2;
        this.posicaoY2 = posicaoY2;
        this.tipoObstaculo = tipoObstaculo;
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

    // pega o ponto central do obstáculo
    @Override
    public int getPosicaoX() {
        return (posicaoX1 + posicaoX2) / 2;
    }

    // pega o ponto central do obstáculo
    @Override
    public int getPosicaoY() {
        return (posicaoY1 + posicaoY2) / 2;
    }
    
    public TipoObstaculo getTipoObstaculo() {
        return tipoObstaculo;
    }

    @Override
    public TipoEntidade getTipo() {
        return tipoEntidade;
    }

    @Override
    public String getDescricao() {
        return tipoObstaculo.toString();
    }

    @Override
    public char getRepresentacao() {
        return representacao;
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

    public void setTipoObstaculo(TipoObstaculo tipo) {
        this.tipoObstaculo = tipo;
    }

    public boolean isBloqueiaPassagem() {
        return tipoObstaculo.isBloqueiaPassagem();
    }

    public int getAltura() {
        return tipoObstaculo.getAltura();
    }

    public boolean isLetal() {
        return tipoObstaculo.isLetal();
    }

    public int getNivelUmidade() {
        return tipoObstaculo.getNivelUmidade();
    }

    public boolean dentroDosLimites(int x, int y, int altura) {
        return x >= posicaoX1 && x <= posicaoX2 && y >= posicaoY1 && y <= posicaoY2 && altura <= getAltura();
    }   

    public void handleColisao(Robo robo, int x, int y, int altura) throws ColisaoException {

        if (!dentroDosLimites(x, y, altura)) {
            return;
        } 
        
        if (isBloqueiaPassagem()) {
           throw new ColisaoException("Colisão com obstáculo: " + getDescricao());
        }

        if (isLetal()) {
            robo.setEstado(EstadoRobo.MORTO);
            throw new ColisaoException("Colisão letal com obstáculo: " + getDescricao());
        }

        if (getNivelUmidade() > 0) {
            robo.setBateria(robo.getBateria() - 2*getNivelUmidade());

            System.out.println("Você pisou em uma poça e se molhou!");

            if (robo.getBateria() < 0) {
                robo.setEstado(EstadoRobo.MORTO);
                throw new ColisaoException("Robô morreu por falta de bateria após pisar em uma poça: " + getDescricao());
            }
        }
    }
}
