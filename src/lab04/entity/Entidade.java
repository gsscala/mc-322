package entity;
import ambiente.Ambiente;

public interface Entidade {
    int getPosicaoX();
    int getPosicaoY();
    TipoEntidade getTipo();
    String getDescricao();
    char getRepresentacao();
    void setAmbiente(Ambiente ambiente);
}