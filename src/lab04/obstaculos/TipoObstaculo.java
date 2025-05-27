package obstaculos;

public enum TipoObstaculo {
    POCA(0, false, false, 80),    
    PEDRA(5, true, false, 5),      
    PREDIO(15, true, false, 1),    
    CACTO(3, true, true, 10);      

    private final int altura;                
    private final boolean bloqueiaPassagem;  
    private final boolean letal;
    private final int nivelUmidade;           

    TipoObstaculo(int altura, boolean bloqueiaPassagem, boolean letal, int nivelUmidade) {
        this.altura = altura;
        this.bloqueiaPassagem = bloqueiaPassagem;
        this.letal = letal;
        this.nivelUmidade = nivelUmidade;
    }

    // Getters
    public int getAltura() {
        return altura;
    }

    public boolean isBloqueiaPassagem() {
        return bloqueiaPassagem;
    }

    public boolean isLetal() {
        return letal;
    }

    public int getNivelUmidade() {
        return nivelUmidade;
    }
}