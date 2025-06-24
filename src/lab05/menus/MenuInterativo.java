// Declaração do pacote ao qual esta classe pertence
package menus;

// Importações necessárias para o funcionamento da classe
import java.util.Scanner; // Para entrada de dados do usuário
import java.util.ArrayList; // Para trabalhar com listas dinâmicas
import java.util.Arrays; // Para manipulação de arrays
import java.util.List; // Interface para listas
import java.util.regex.*; // Para trabalhar com expressões regulares

import robos.AgenteInteligente;
// Importações de classes específicas do projeto
import robos.EstadoRobo; // Enum que define os estados do robô
import robos.Robo; // Classe base para todos os robôs
import robos.RoboAereo; // Subclasse de robô aéreo
import robos.RoboAleatorio; // Subclasse de robô com comportamento aleatório
import robos.RoboAtirador; // Subclasse de robô atirador
import robos.RoboDesligadoException; // Exceção para operações em robô desligado
import robos.RoboNotFoundException; // Exceção para robô não encontrado
import robos.RoboTerrestre; // Subclasse de robô terrestre
import robos.TaskNotFoundException; // Exceção para tarefa não encontrada
import ambiente.Ambiente; // Classe que representa o ambiente de simulação
import comunicacao.Comunicavel; // Interface para comunicação entre robôs
import comunicacao.ErroComunicacaoException; // Exceção para erros de comunicação
import entity.Entidade; // Classe base para entidades do sistema
import missao.MissaoCentroide;
import missao.MissaoExploraçãoSegura;
import missao.MissaoMatador;
import sensores.*; // Pacote contendo sensores e interfaces relacionadas

// Classe principal que implementa o menu interativo
public class MenuInterativo {
    // Flag para controlar o término da execução do programa
    private boolean terminated = false;
    
    // Ambiente atual da simulação
    private Ambiente ambienteAtual;

    // Construtor que inicializa o menu com um ambiente específico
    public MenuInterativo(Ambiente ambiente) {
        this.ambienteAtual = ambiente;
    }

    // Método getter para acessar o ambiente atual
    public Ambiente getAmbienteAtual() {
        return ambienteAtual;
    }

    // Método principal que inicia a execução do menu
    public void run() {
        // Cria um scanner para ler entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Mensagem inicial do sistema
        System.out.println("Bem-vindo ao Simulador de Robôs!");
        System.out.println("Digite 'help' para ver a lista de comandos disponíveis.");
        System.out.println("Ambiente: " + ambienteAtual.getNome());

        // Loop principal de execução
        while (!terminated) {
            // Exibe o prompt de comando
            System.out.print("> ");
            // Lê a entrada do usuário e remove espaços extras
            String input = scanner.nextLine().trim();
            // Processa o comando digitado
            handleInput(input);
        }

        // Mensagem de encerramento
        System.out.println("Simulação finalizada");
        // Fecha o scanner para liberar recursos
        scanner.close();
    }

    // Método para processar os comandos do usuário
    private void handleInput(String input) {
        // Verifica se o usuário digitou algo
        if (input.isEmpty()) {
            System.out.println("Digite um comando. Use 'help' para ver opções.");
            return;
        }

        // Lista para armazenar os argumentos do comando
        List<String> parsedArgs = new ArrayList<>();
        // Regex para capturar argumentos entre aspas ou palavras individuais
        Matcher m = Pattern.compile("\"([^\"]*)\"|(\\S+)").matcher(input);
        // Processa todos os matches encontrados
        while (m.find()) {
            if (m.group(1) != null) {
                // Adiciona texto entre aspas
                parsedArgs.add(m.group(1));
            } else {
                // Adiciona palavra individual
                parsedArgs.add(m.group(2));
            }
        }

        // Converte a lista para array
        String[] args = parsedArgs.toArray(new String[0]);

        // Processa o comando principal
        switch (args[0].toLowerCase()) {
            case "end":
                // Encerra o programa
                terminated = true;
                break;

            case "help":
                // Exibe ajuda
                showHelp();
                break;

            case "move":
                // Comando de movimento
                move(args);
                break;

            case "missao":
                missao(args);
                break;

            case "executartarefa":
                // Executa tarefa especial
                executarTarefa(args);
                break;

            case "tarefas":
                // Lista tarefas de um robô
                tarefas(args);
                break;

            case "status":
                // Mostra status do ambiente e robôs
                showStatus();
                break;

            case "monitorar":
                // Ativa sensores de um robô
                monitorar(args);
                break;

            case "comunicar":
                // Comunicação entre robôs
                comunicar(args);
                break;

            case "showmapa":
                // Exibe o mapa do ambiente
                showMapa(args);
                break;
            
            case "mudarestado":
                // Altera estado de um robô
                mudarestado(args);
                break;

            case "listmensagens":
                // Lista todas as mensagens trocadas
                getAmbienteAtual().getCentralComunicacao().exibirMensagens();
                break;

            default:
                // Comando não reconhecido
                System.out.println("Comando inválido. Digite 'help' para ver os comandos disponíveis.");
                break;
        }
    }

    // Método para exibir a ajuda detalhada
    private void showHelp() {
        System.out.println("Comandos disponíveis:");
        System.out.println("- end                             : Encerra a simulação imediatamente. Nenhum parâmetro adicional.");
        System.out.println("- showRobos                       : Exibe lista de todos os robôs registrados no ambiente. Sem parâmetros.");
        System.out.println("- status <nome_robo>              : Mostra posição (x,y,z), nível de bateria e estado (ligado/desligado/morto) do robô especificado.");
        System.out.println("- move <nome_robo> <deltaX> <deltaY>       : Desloca o robô em deltaX (eixo X) e deltaY (eixo Y). Valores inteiros." );
        System.out.println("- executarTarefa <nome_robo> <tarefa> [args] : Executa tarefa especial. 'tarefa' deve ser o nome exato (roubar, subir, explodir, etc.). 'args' são parâmetros da tarefa, quando aplicável (ex: deltaZ ou raio).");
        System.out.println("- tarefas <nome_robo>             : Lista todas as tarefas que o tipo de robô suporta. Sem parâmetros.");
        System.out.println("- monitorar <nome_robo>           : Aciona sensores de proximidade e umidade do robô, exibindo leituras atuais.");
        System.out.println("- comunicar <nome1> <nome2> <msg> : Envia a string <msg> do robô <nome1> para o robô <nome2>. Mensagem entre aspas não é obrigatória.");
        System.out.println("- showMapa                        : Desenha no console a representação textual do mapa e posição de todos os robôs.");
        System.out.println("- mudarEstado <nome_robo>         : Alterna entre ligado e desligado. Se desligado, não aceita comandos até re-ligado.");
        System.out.println("- listMensagens                   : Exibe todas as mensagens trocadas no ambiente desde o início da simulação.");
        System.out.println("- help                            : Exibe esta mensagem de ajuda com sintaxes e descrições detalhadas.");
    
        System.out.println("\nDetalhamento de executarTarefa por classe de robô:");
        
        System.out.println("-- Robo (classe base):");
        System.out.println("   - Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô alvo e R é a distância euclidiana entre os dois.");
        
        System.out.println("-- RoboAereo (subclasse de Robo):");
        System.out.println("   - Roubar : mesma lógica de Robo");
        System.out.println("   - Subir <deltaZ> : aumenta altitude até o máximo permitido pelo robô");
        System.out.println("   - Descer <deltaZ> : diminui a altitude (não pode ficar abaixo de zero)");
        
        System.out.println("-- RoboTerrestre (subclasse de Robo):");
        System.out.println("   - Roubar : mesma lógica de Robo");
        
        System.out.println("-- RoboAleatorio (subclasse de Robo):");
        System.out.println("   - Roubar : mesma lógica de Robo");
        System.out.println("   - Explodir <raio> : causa dano letal a todos os robôs dentro do raio especificado (inteiro)");
                
        System.out.println("-- RoboAtirador (subclasse de RoboAereo):"); 
        System.out.println("   - Roubar : mesma lógica de Robo");
        System.out.println("   - Subir <deltaZ> : herdado de RoboAereo");
        System.out.println("   - Descer <deltaZ> : herdado de RoboAereo");
        System.out.println("   - Atirar : elimina todos os robôs alinhados no mesmo X ou Y");
        System.out.println("   - EncherOSaco <n> : envia n mensagens aleatórias para um robô-alvo, gerando spam");
    }

    // Método auxiliar para encontrar robô pelo nome
    private Robo findRobo(String nome) throws RoboNotFoundException {
        // Percorre todas as entidades do ambiente
        for (Entidade e : ambienteAtual.getEntidades()) {
            // Filtra apenas robôs
            if (!(e instanceof Robo)) {
                continue;
            }
            Robo robo = (Robo) e;
            // Comparação case-insensitive
            if (robo.getNome().equalsIgnoreCase(nome)) {
                return robo;
            }
        }
        // Lança exceção se não encontrar
        throw new RoboNotFoundException("Robô não encontrado");
    }
    
    // Método para alterar o estado de um robô
    private void mudarestado(String[] args){
        // Verificação básica de argumentos
        if (args.length < 2) {
            System.out.println("Uso: mudarestado <nome_robo>");
            return;
        }

        Robo robo;
        try{
            // Busca o robô pelo nome
            robo = findRobo(args[1]);
        }
        catch (RoboNotFoundException e){
            // Trata caso robô não seja encontrado
            System.out.println(e.getMessage());
            return;
        }
        
        // Verifica se o robô está morto (estado imutável)
        if (robo.getEstado() == EstadoRobo.MORTO){
            System.out.printf("Robô %s está morto, isto é inalterável!", robo.getNome());
            return;
        }
        
        // Alterna entre ligado e desligado
        EstadoRobo novoEstado = robo.getEstado() == EstadoRobo.DESLIGADO ? 
                                EstadoRobo.LIGADO : EstadoRobo.DESLIGADO;
                                
        // Aplica o novo estado e exibe resultado
        System.out.println(robo.setEstado(novoEstado));
    }

    // Método para listar tarefas de um robô específico
    private void tarefas(String [] args){
        // Verificação básica de argumentos
        if (args.length < 2) {
            System.out.println("Uso: tarefas <nome_robo>");
            return;
        }

        Robo robo;
        try {
            // Busca o robô pelo nome
            robo = findRobo(args[1]);
        } catch (RoboNotFoundException e){
            // Trata caso robô não seja encontrado
            System.out.println(e.getMessage());
            return;
        }
        
        // Exibe cabeçalho com nome do robô
        System.out.printf("Funcionalidades do robô %s:%n", robo.getNome());

        // Lista tarefas específicas para cada tipo de robô
        if (robo.getClass() == Robo.class) {
            System.out.println("- Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô roubado e R é a distância euclideana entre os dois.");
        } 
        else if (robo.getClass() == RoboAereo.class) {
            System.out.println("- Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô roubado e R é a distância euclideana entre os dois.");
            System.out.println("- Subir : aumenta altitude do robô");
            System.out.println("- Descer : diminui a altitude do robô");
        } 
        else if (robo.getClass() == RoboAleatorio.class) {
            System.out.println("- Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô roubado e R é a distância euclideana entre os dois.");
            System.out.println("- Explodir : mata todos em os robôs em um raio R");
        } 
        else if (robo.getClass() == RoboAtirador.class) {
            System.out.println("- Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô roubado e R é a distância euclideana entre os dois.");
            System.out.println("- Subir : aumenta altitude do robô");
            System.out.println("- Descer : diminui a altitude do robô");
            System.out.println("- Atirar : mata todos os robôs com a mesma coordenada x o y do robô");
            System.out.println("- EncherOSaco : Manda um número de mensagens aleatórias (spam) para o robô escolhido");
        } 
        else if (robo.getClass() == RoboTerrestre.class) {
            System.out.println("- Roubar : rouba bateria de todos os robôs do ambiente. A quantidade roubada é o piso de B / R, em que B é a bateria do robô roubado e R é a distância euclideana entre os dois.");
        } 
    }

    // Método para exibir status completo do ambiente
    private void showStatus() {
        // Mostra informações gerais do ambiente
        System.out.printf("Ambiente: %s | Dimensões: %dx%dx%d | Umidade: %d%%%n",
            ambienteAtual.getNome(),
            ambienteAtual.getLargura(),
            ambienteAtual.getProfundidade(),
            ambienteAtual.getAltura(),
            ambienteAtual.getUmidade()
        );
        System.out.println();

        // Mostra status individual de cada robô
        System.out.println("Status dos robôs:");
        for (Entidade e : ambienteAtual.getEntidades()) {
            if (e instanceof Robo) {
                Robo robo = (Robo) e;
                String missaoName = "[---]";

                if (robo instanceof AgenteInteligente) {
                    AgenteInteligente agente = (AgenteInteligente) robo;
                    if (agente.hasMissao()) {
                        missaoName = agente.getMissao().getClass().getSimpleName();
                    }
                }

                // Formata e exibe informações detalhadas do robô
                System.out.printf("%s (%s) - Posição: (%d, %d, %d) - Bateria: %d - Estado: %s%n - %s%n - Missão: %s%n",
                    robo.getNome(),
                    robo.getClass().getSimpleName(), // Tipo do robô
                    robo.getPosicaoX(), // Coordenada X
                    robo.getPosicaoY(), // Coordenada Y
                    robo.getAltitude(),  // Coordenada Z (altura)
                    robo.getBateria(),   // Nível de bateria
                    // Estado formatado
                    robo.getEstado() == EstadoRobo.LIGADO ? "ligado" : 
                    robo.getEstado() == EstadoRobo.DESLIGADO ? "desligado" : "morto",
                    robo.getDescricao(),  // Descrição adicional
                    missaoName
                );
            }
        }
    }

    // Método para mover um robô no ambiente
    private void move(String[] args) {
        // Verifica número mínimo de argumentos
        if (args.length < 4) {
            System.out.println("Uso: move <nome_robo> <deltaX> <deltaY>");
            return;
        }

        Robo robo;
        try{
            // Busca o robô pelo nome
            robo = findRobo(args[1]);
        }catch (RoboNotFoundException e){
            // Trata robô não encontrado
            System.out.println(e.getMessage());
            return;
        }

        try {
            // Converte argumentos para inteiros
            int deltaX = Integer.parseInt(args[2]);
            int deltaY = Integer.parseInt(args[3]);
            
            // Executa o movimento
            robo.mover(deltaX, deltaY);
            
            // Exibe nova posição
            System.out.println(robo.getNome() + " movido para (" + 
                               robo.getPosicaoX() + ", " + 
                               robo.getPosicaoY() + ")");
        } catch (NumberFormatException e) {
            // Trata erros de conversão numérica
            System.err.println("Coordenadas inválidas. Use números inteiros para deltaX e deltaY.");
        }
    }

    private void missao(String [] args) {   
        if (args.length < 3) {
            System.out.println("Uso: missao <nome_robo> <adicionar/executar> [nome_missao]");
            return;
        }

        Robo robo;
        try{
            // Busca o robô pelo nome
            robo = findRobo(args[1]);
        } catch (RoboNotFoundException e){
            // Trata robô não encontrado    
            System.out.println(e.getMessage());
            return;
        }
        
        if (!(robo instanceof AgenteInteligente)) {
            System.err.println("Robô não é um agente inteligente, não pode executar missões.");
            return;
        }
        
        AgenteInteligente agente = (AgenteInteligente) robo;
        

        if (args[2].equals("adicionar")) {

            if (args.length < 4) {
                System.out.println("Adicionar <nome_missao>");
                return;
            }
            
            if (agente.hasMissao()) {
                System.out.println("O robô substitui a missão atual.");
            }

            try {
                switch (args[3].toLowerCase()) {
                    case "centroide":
                        agente.setMissao(new MissaoCentroide(agente, agente.getAmbiente()));
                        break;

                    case "exploracao":
                        agente.setMissao(new MissaoExploraçãoSegura(agente, agente.getAmbiente()));
                        break;

                    case "matador":
                        if (!(agente instanceof RoboAtirador)) {
                            System.err.println("Missão 'matador' só pode ser atribuída a robôs atiradores.");
                            return;
                        }
                        agente.setMissao(new MissaoMatador(agente, agente.getAmbiente()));
                        break;
                
                    default:
                        throw new TaskNotFoundException("Missão não encontrada: " + args[3]);
                }
            } catch (TaskNotFoundException e) {
                System.out.println(e.getMessage());
                return;
            }


            System.out.println("Missão " + args[2] + " adicionada ao robô " + robo.getNome() + ".");
        }


        else if (args[2].equals("executar")) {

            if (!agente.hasMissao()) {
                System.out.println("O robô não possui uma missão definida.");
                return;
            }

            System.out.println("Executando missão " + args[2] + " do robô " + agente.getNome() + ".");
      
            agente.executarMissao();
            
            System.out.println("Fim da missão");
        }

    }  

    // Método para executar tarefas especiais
    private void executarTarefa(String[] args) {
        // Verifica número mínimo de argumentos
        if (args.length < 3) {
            System.out.println("Uso: executartarefa <nome_robo> <ação> [argumentos]");
            return;
        }

        Robo robo = null;
        try {
            // Busca o robô pelo nome
            robo = findRobo(args[1]);
        } catch (RoboNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        // Obtém o nome da tarefa
        String comando = args[2];

        // Prepara argumentos adicionais se existirem
        String[] args_tarefa = new String[0];
        if (args.length > 3) {
            args_tarefa = Arrays.copyOfRange(args, 3, args.length);
        }

        try {
            // Executa a tarefa no robô
            robo.executarTarefa(comando, args_tarefa);
        } catch (RoboDesligadoException e) {
            // Trata robô desligado
            System.out.println(e.getMessage());
        } catch (ErroComunicacaoException e) {
            // Trata erros de comunicação
            System.out.println(e.getMessage());
        } catch (TaskNotFoundException e) {
            // Trata tarefa inexistente
            System.out.println(e.getMessage());
        }
    }

    // Método para ativar sensores de um robô
    private void monitorar(String[] args) {
        // Verifica número mínimo de argumentos
        if (args.length < 2) {
            System.out.println("Uso: monitorar <nome_robo>");
            return;
        }

        Robo robo;
        try{
            // Busca o robô pelo nome
            robo = findRobo(args[1]);
        }
        catch (RoboNotFoundException e){
            System.out.println(e.getMessage());
            return;
        }

        try {
            // Verifica se o robô possui sensores
            if (!(robo instanceof Sensoreavel))
                throw new NaoSensoriavelException("Robô não sensoriável");
            
            // Ativa os sensores
            ((Sensoreavel)robo).acionarSensores();
        } 
        catch (RoboDesligadoException e) {
            // Trata robô desligado
            System.out.println(e.getMessage()); 
        }
        catch (NaoSensoriavelException e){
            // Trata robô sem sensores
            System.out.println(e.getMessage());
        }
    }

    // Método para comunicação entre robôs
    private void comunicar(String[] args) {
        // Verifica número mínimo de argumentos
        if (args.length < 4) {
            System.out.println("Uso: comunicar <nome1> <nome2> <mensagem>");
            return;
        }

        Robo remetente;
        try{
            // Busca robô remetente
            remetente = findRobo(args[1]);
        }
        catch (RoboNotFoundException e){
            System.out.println(e.getMessage());
            return;
        }

        Robo destinatario;
        try{
            // Busca robô destinatário
            destinatario = findRobo(args[2]);
        }catch (RoboNotFoundException e){
            System.out.println(e.getMessage());
            return;
        }

        // Obtém a mensagem
        String mensagem = args[3];

        try {
            // Verifica se ambos podem se comunicar
            if (remetente instanceof Comunicavel && destinatario instanceof Comunicavel) {
                // Envia a mensagem
                ((Comunicavel) remetente).enviarMensagem((Comunicavel) destinatario, mensagem);
                System.out.println("Mensagem enviada de " + remetente.getNome() + " para " + destinatario.getNome());
            } else {
                throw new ErroComunicacaoException("Robôs não são comunicaveis");
            }
        } catch (RoboDesligadoException e) {
            // Trata robô desligado
            System.out.println("Erro ao enviar mensagem: " + e.getMessage());
        } catch (ErroComunicacaoException e) {
            // Trata outros erros de comunicação
            System.out.println("Erro ao enviar mensagem: " +  e.getMessage());
        }
    }

    // Método para exibir o mapa do ambiente
    private void showMapa(String[] args) {
        getAmbienteAtual().visualizarAmbiente();
    }   
}