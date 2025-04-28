import ambiente.Ambiente;
import robos.Robo;
import robos.RoboAereo;
import robos.RoboAleatorio;
import robos.RoboAtirador;
import robos.RoboTerrestre;

import menus.MenuInterativo;

public class Main {
    // Método principal (entry point) da aplicação
    public static void main(String[] args) {
        Ambiente ilhas_dog = new Ambiente(200, 100, "ilhas dog");
        
        RoboAleatorio roboaleatorio = new RoboAleatorio("random", 0, 0, "Norte", 100);  
        RoboAtirador roboatirador = new RoboAtirador("praprapra", 0, 0, "Leste", 100, "Ak-47");
        RoboTerrestre roboterrestre = new RoboTerrestre("cebolinha", 0, 0, "Sul", 100);        
        RoboAereo roboaereo = new RoboAereo("dog drone", 0, 0, "Norte", 100);  
        Robo robo = new Robo("npc", 0, 0, "Norte");

        // Adiciona os robôs criados ao ambiente
        ilhas_dog.adicionarRobo(roboaleatorio);
        ilhas_dog.adicionarRobo(roboatirador);
        ilhas_dog.adicionarRobo(roboterrestre);
        ilhas_dog.adicionarRobo(roboaereo);
        ilhas_dog.adicionarRobo(robo);

        MenuInterativo menu = new MenuInterativo(ilhas_dog);

        menu.run();
    }
 
}

// javac -d bin $(find src/lab03/ -name "*.java")
// java -cp bin Main

