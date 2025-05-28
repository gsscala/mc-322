package utils;
import entity.Entidade;

public class DistanceCalculator {
    private int posX1, posX2, posY1, posY2;

    public DistanceCalculator(int x1, int x2, int y1, int y2){
        posX1 = x1;
        posX2 = x2;
        posY1 = y1;
        posY2 = y2;
    }

    public DistanceCalculator(Entidade entidade1, Entidade entidade2){
        posX1 = entidade1.getPosicaoX();
        posX2 = entidade2.getPosicaoX();
        posY1 = entidade1.getPosicaoY();
        posY2 = entidade2.getPosicaoY();
    }

    public double calculateDistance(){
        int dx = posX1 - posX2;
        int dy = posY1 - posY2;
        return Math.sqrt(dx * dx + dy * dy);
    }
}
