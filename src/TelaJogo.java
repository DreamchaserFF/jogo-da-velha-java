import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.Timer;

    /////////////////////////////////////////////////////////////
   ///                                                       ///
  ///                CONFIGURAÇÃO DA TELA                   ///
 ///                                                       ///
/////////////////////////////////////////////////////////////


public class TelaJogo extends JFrame {
    // Atributos da classe
    java.net.URL urlImgVazia          = getClass().getResource("/assets/botao_vazio.png");
    Image imgVazia                    = new ImageIcon(urlImgVazia).getImage(); // Carrega a imagem original para manter a qualidade
    java.net.URL urlImgX              = getClass().getResource("/assets/botao_x.png");
    Image imgX                        = new ImageIcon(urlImgX).getImage();
    java.net.URL urlImgO              = getClass().getResource("/assets/botao_o.png");
    Image imgO                        = new ImageIcon(urlImgO).getImage();
    java.net.URL urlIcone             = getClass().getResource("/assets/velha.png");
    ImageIcon icone                   = new ImageIcon(urlIcone); // Cria um icone de imagem
    Border border                     = BorderFactory.createLineBorder(Color.BLACK,3); // Cria a borda - Não está fazendo nada
    char simboloAtual                 = ' '; //Variavel que vai pegar o simbolo do jogador atual
    Random jogadaMaquina              = new Random();
    private boolean bloqueioInterface = false;
    private TabuleiroLogico cerebro;
    
    JLabel label = new JLabel(); // Cria o label
    private JButton[] botoes = new JButton[9]; // Cria os botoes

    // METODOS
    // Redimenciona a imagem para que ela fique do tamanho certo quando mudar. Não funciona direito
    public void inserirImagem(JButton botao, Image imagem){
        int largura = botao.getWidth();
        int altura  = botao.getHeight();
        Image imgRedim = imagem.getScaledInstance(largura, altura, Image.SCALE_FAST); // Redimensiona a imagem
        botao.setIcon(new ImageIcon(imgRedim));
    };

    // Metodo de limpar a memoria e a interface
    private void reiniciarJogo() {
        this.bloqueioInterface = false;
        for (int i = 0; i < cerebro.tamanhoTabuleiro; i++){
            cerebro.limparMemoria();
            inserirImagem(botoes[i], imgVazia);
        }
        cerebro.jogando = true;
    }

    // Metodo para finalizar a partida em si
    private void finalizarPartida(String titulo, String mensagem, int icone) {
        int resposta = JOptionPane.showConfirmDialog(this, 
            mensagem + "\n\nDeseja jogar novamente?",
            titulo,
            JOptionPane.YES_NO_OPTION,
            icone);

        if (resposta == JOptionPane.YES_OPTION) {
            reiniciarJogo();
        } else {
            System.exit(0);
        }
    }

    // TELA DO JOGO
    public TelaJogo(){ // Se passar argumentos, ele precisa desses argumentos quando chamado no main
    // Construtor da classe
        this.cerebro = new TabuleiroLogico();
        this.setTitle("Jogo da Velha"); // Titulo da janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Sai do programa quando frame é fechado
        this.setSize(800,800); // Determina as dimensões x e y da janela
        this.getContentPane().setBackground(new Color(0,0,0)); // Colore background
        this.setLayout(new GridLayout(3, 3)); // Cria a grid que vai ser o tabuleiro 
        

        // Preenche os espaços com os botões vazios     
        for(int i = 0; i < botoes.length; i++) {
            // Precisamos criar constantes com "final" porque se usarmos variaveis, elas podem mudar ou não existir mais antes do jogo carregar.
            botoes[i] = new JButton();
            botoes[i].setBorder(border); // Passa a border criada para os botões.
            final JButton btnAtual = botoes[i]; // captura uma "imagem" da variavel do botao nesse momento
            final int indiceAtual  = i; // Captura uma imagem do indice

            // Lógica do click
            // Em java, o metodo pode ser chamado antes de ser criado.
            btnAtual.addActionListener(e -> processarJogada(indiceAtual, btnAtual));

            // Listener de redimensionamento para deixar a imgem responsiva.
            btnAtual.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e){
                    // O redimencionador vai atualizar a imagem para a que foi passada como parametro sempre que ele for chamado (quando a janela muda de tamanho.)
                    char simboloAtual = cerebro.obterSimbolo(indiceAtual);

                    // Pega cada imagem baseada no turno e redimenciona
                    if(simboloAtual == 'x'){
                        inserirImagem(btnAtual, imgX);
                    }
                    else if(simboloAtual == 'o'){
                        inserirImagem(btnAtual, imgO);
                    }
                    else {
                        inserirImagem(btnAtual, imgVazia);
                    }
                }
            });

        add(botoes[indiceAtual]);
        }

        setIconImage(icone.getImage()); // Coloca o icone na janela
        setVisible(true); // Deixa ele visível. Deve sempre ir por ultimo para garantir que tudo carregue
    }

    private void processarJogada(int indice, JButton botao){

        if(bloqueioInterface || cerebro.obterSimbolo(indice) != ' ' || !cerebro.jogando){
            return;
        }
        // Turno do humano.
        cerebro.registrarJogada(indice, 'o');; // Salva o dado na memoria (model)
        inserirImagem(botao, imgO); // Atualiza a tela (view)
        // Pergunta quem venceu e guarda a resposta
        char vencedor = this.cerebro.verificarVencedor();
        if (vencedor != ' ') {
            finalizarPartida("Fim de Jogo", "O vencedor é: " + vencedor + "!", JOptionPane.INFORMATION_MESSAGE);
            return;
        };

        // Verifica o empate
        if(!this.cerebro.temEspacoVazio()) {
            cerebro.jogando = false;
            finalizarPartida("Empate", "Deu velha! O jogo empatou.", JOptionPane.WARNING_MESSAGE);
            return; // Aborta o metodo
        }

        // Turno da maquina
            if(cerebro.jogando){
                bloqueioInterface = true;
                Timer timer = new Timer(800, e -> {
                    jogadaMaquina();
                    
                    char vencedorMaquina = this.cerebro.verificarVencedor();

                    if(vencedorMaquina != ' '){
                        finalizarPartida("Fim de Jogo", "O vencedor é: " + vencedorMaquina + "!", JOptionPane.INFORMATION_MESSAGE);
                    };

                    bloqueioInterface = false;
                });
                
                timer.setRepeats(false);
                timer.start();
            }
        }

    // Lógica da jogada da maquina
    private void jogadaMaquina(){
        int indiceAleatorio;

        do {
            indiceAleatorio = jogadaMaquina.nextInt(cerebro.tamanhoTabuleiro);
        } while (cerebro.obterSimbolo(indiceAleatorio) != ' ');

        cerebro.registrarJogada(indiceAleatorio, 'x');;
        inserirImagem(botoes[indiceAleatorio], imgX);
    }
}
