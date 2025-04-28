package menus;

import java.util.Scanner;
import robos.Robo;
import robos.RoboAleatorio;
import robos.RoboAereo;
import robos.RoboAtirador;
import robos.RoboTerrestre;

import ambiente.Ambiente;

public class MenuInterativo {
    private boolean terminated = false;
    private Ambiente ambienteAtual;

    public MenuInterativo(Ambiente ambiente) {
        this.ambienteAtual = ambiente;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bem vindo ao simulador de robos! Leia a readme pra entender os comandos básicos.");
        System.out.println("Ambiente: " + ambienteAtual.getNome());

        while (!terminated) {
            System.out.print("> ");
            String input = scanner.nextLine();
            handleInput(input);
        }
        System.out.println("Simulação finalizada");
        scanner.close();
    }

    private void handleInput(String input) {
        String[] args = input.split("\\s+");
    
        switch (args[0]) {
            case "end":
                terminated = true;
                break;
    
            case "showRobos":
                showRobos();
                break;
    
            case "move":
                move(args);
                break;

            case "special":
                skill(args);
                break;

            case "status":
                showStatus();
                break;
    
            default:
                System.out.println("Comando inválido");
                break;
        }
    }
    
    private void showRobos() {
        for (Robo robo : ambienteAtual.getRobos()) {
            String className = robo.getClass().getSimpleName();
            System.out.println(robo.getNome() + " (" + className + ")");
        }
    }
    
    private void move(String[] args) {
        if (args.length < 4) {
            System.out.println("Uso: move <nome_robo> <x> <y>");
            return;
        }

        String roboNome = args[1];
        Robo robo = findRobo(roboNome);
        
        if (robo == null) {
            System.out.println("Robô não encontrado: " + roboNome);
            return;
        }

        try {
            int deltaX = Integer.parseInt(args[2]);
            int deltaY = Integer.parseInt(args[3]);
            robo.mover(deltaX, deltaY);
            System.out.println(roboNome + " movido para (" + robo.getPosicaoX() + ", " + robo.getPosicaoY() + ")");
        } catch (NumberFormatException e) {
            System.out.println("Coordenadas inválidas");
        }
    }
    
    private Robo findRobo(String nome) {
        for (Robo robo : ambienteAtual.getRobos()) {
            if (nome.equals(robo.getNome())) {
                return robo;
            }
        }
        return null;
    }

    private void skill(String[] args) {
        // Validação básica dos argumentos
        if (args.length < 3) {
            System.out.println("Uso correto: skill <nome_robo> <comando> [argumentos]");
            System.out.println("Comandos disponíveis:");
            System.out.println("- Aéreo: 'subir' <deltaZ>, 'descer' <deltaZ>");
            System.out.println("- Atirador: 'atirar'");
            System.out.println("- Terrestre: 'turbo' <deltaX> <deltaY> <velocidade>");
            System.out.println("- Aleatório: 'aleatorio'");
            return;
        }
    
        Robo robo = findRobo(args[1]);
        if (robo == null) {
            System.out.println("Robô não encontrado: " + args[1]);
            return;
        }
    
        String comando = args[2];
        
        if (robo instanceof RoboAtirador && "atirar".equals(comando)) {
            RoboAtirador atirador = (RoboAtirador) robo;
            atirador.atirar();
        }

        else if (robo instanceof RoboTerrestre && "turbo".equals(comando)) {
            if (args.length < 6) {
                System.out.println("Uso correto: skill " + robo.getNome() + " turbo <deltaX> <deltaY> <velocidade>");
                return;
            }
            RoboTerrestre terrestre = (RoboTerrestre) robo;
            int deltaX = Integer.parseInt(args[3]);
            int deltaY = Integer.parseInt(args[4]);
            int velocidade = Integer.parseInt(args[5]);
            
            terrestre.mover(deltaX, deltaY, velocidade);
        }

        else if (robo instanceof RoboAleatorio && "aleatorio".equals(comando)) {
            RoboAleatorio aleatorio = (RoboAleatorio) robo;
            aleatorio.mover();

        }

        else if (robo instanceof RoboAereo) {
            RoboAereo aereo = (RoboAereo) robo;

            if ("subir".equals(comando)) {
                if (args.length < 4) {
                    System.out.println("Uso correto: skill " + robo.getNome() + " subir <deltaZ>");
                    return;
                }
                int deltaZ = Integer.parseInt(args[3]);

                aereo.subir(deltaZ);
            }

            else if ("descer".equals(comando)) {
                if (args.length < 4) {
                    System.out.println("Uso correto: skill " + robo.getNome() + " descer <deltaZ>");
                    return;
                }
                int deltaZ = Integer.parseInt(args[3]);

                aereo.descer(deltaZ);;

            }

            else {
                System.out.println("Comando inválido para Robô Aéreo. Use 'subir' ou 'descer'");
            }
        }
        else {
            System.out.println(robo.getNome() + " é um robô básico sem habilidades especiais");
        }
    }
    

    
    private void showStatus() {
        System.out.println("Robôs disponíveis:");
        
        for (Robo robo : ambienteAtual.getRobos()) {
            String className = robo.getClass().getSimpleName();
            System.out.printf("%s (%s) - Posição: (%d, %d)%n",
                robo.getNome(),
                className,
                robo.getPosicaoX(),
                robo.getPosicaoY());
        }
    }
}