public enum TipoObstaculo {
    BOMBA(9, 50, 2),
    POCO(6, 20, 1),
    BURACO_NEGRO(10, 100, 3),
    PEDRA(3, 0, 0);

    private final int perigo;
    private final int dano;
    private final int alcanceZ;

    TipoObstaculo(int perigo, int dano, int alcanceZ) {
        this.perigo = perigo;
        this.dano = dano;
        this.alcanceZ = alcanceZ;
    }

    public int getPerigo() {
        return perigo;
    }

    public int getDano() {
        return dano;
    }

    public int getAlcanceZ() {
        return alcanceZ;
    }
}
