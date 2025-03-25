import java.util.ArrayList;

public class Ambiente {
    private int largura;
    private int altura;
    protected ArrayList<Robo> robos = new ArrayList<>();

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getLargura() {
        return largura;
    }

    public void setLargura(int largura) {
        this.largura = largura;
    }

    public Ambiente (int largura, int altura){
        this.largura = largura;
        this.altura = altura;
    }

    public void adicionarRobo(Robo r){
        robos.add(r);
        r.addAmbiente(this);
    }

    public ArrayList<Robo> getRobos(){
        return this.robos;
    }

    public boolean dentroDosLimites(int x, int y){
        return x >= 0 && x < this.largura && y >= 0 && y < this.altura;
    }

    public boolean dentroDosLimites(int x, int y, int z, int altitudeMaxima){
        return x >= 0 && x < this.largura && y >= 0 && y < this.altura && z >= 0 && z < altitudeMaxima;
    }
}
