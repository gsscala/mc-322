package obstaculos;

public enum TipoObstaculo {
    AREIA_MOVEDICA(0, false, false), 
    PEDRA(5, true, false),
    PREDIO(15, true, false),
    CACTO(3, true, true);

    private final int altura;                
    private final boolean bloqueiaPassagem;  
    // private final double coeficienteGrudento;
    private final boolean letal;

    TipoObstaculo(int altura, boolean bloqueiaPassagem, boolean letal) {
        this.altura = altura;
        this.bloqueiaPassagem = bloqueiaPassagem;
        // this.coeficienteGrudento = coeficienteGrudento;
        this.letal = letal;
    }

    // Getters for the attributes
    public int getAltura() {
        return altura;
    }

    public boolean isBloqueiaPassagem() {
        return bloqueiaPassagem;
    }

    // public double getCoeficienteGrudento() {
    //     return coeficienteGrudento;
    // }

    public boolean isLetal() {
        return letal;
    }
}
