public class Obstaculo {
    private int posicaoX1;
    private int posicaoY1;
    private int posicaoX2;
    private int posicaoY2;
    private int altura;
    private TipoObstaculo tipo;

    // Constructor
    public Obstaculo(int posicaoX1, int posicaoY1, int altura, int posicaoX2, int posicaoY2, TipoObstaculo tipo) {
        this.posicaoX1 = posicaoX1;
        this.posicaoY1 = posicaoY1;
        this.posicaoX2 = posicaoX2;
        this.posicaoY2 = posicaoY2;
        this.altura = altura;
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

    public int getAltura() {
        return altura;
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

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setTipo(TipoObstaculo tipo) {
        this.tipo = tipo;
    }
}
