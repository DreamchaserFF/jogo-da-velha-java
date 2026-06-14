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
///                  CONFIGURAÇÃO DA TELA                 ///
///                                                       ///
/////////////////////////////////////////////////////////////

public class TelaJogo extends JFrame {
    // Atributos da classe
    Image imgVazia       = new ImageIcon("botao_vazio.png").getImage(); // Carrega a imagem original para manter a qualidade
    Image imgX           = new ImageIcon("botao_x.png").getImage();
    Image imgO           = new ImageIcon("botao_o.png").getImage();
    ImageIcon icone      = new ImageIcon("velha.png"); // Cria um icone de imagem
    Border border        = BorderFactory.createLineBorder(Color.BLACK,3); // Cria a borda - Não está fazendo nada
    char simboloAtual    = ' '; //Variavel que vai pegar o simbolo do jogador atual
    Random jogadaMaquina = new Random();
    boolean jogando      = true;

    private char[] estado = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}; // Array para marcar estado

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

    // Metodo que verifica a cada turno se alguem venceu
    public boolean verificarVencedor(){
        char[][] padraoVitoria = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Linhas Horizontais
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Colunas verticais
            {0, 4, 8}, {2, 4, 6}             // Diagonais
        };

        // Percorre o dicionario testando cada linha
        for(int i = 0; i < padraoVitoria.length; i++){
            int pos1 = padraoVitoria[i][0];
            int pos2 = padraoVitoria[i][1];
            int pos3 = padraoVitoria[i][2];

            // Se a primeira posição não estiver vazia E for igual à segunda E igual à terceira...
            if (estado[pos1] != ' ' && estado[pos1] == estado[pos2] && estado[pos2] == estado[pos3]) {
                jogando = false;
                
                finalizarPartida("Fim de Jogo", "O vencedor é: " + estado[pos1] + "!", JOptionPane.INFORMATION_MESSAGE);

                return true;
            }
        }
        return false;
    }

    // Metodo de verificar se existe espaço vazio
    private boolean temEspacoVazio() {
        for (int i = 0; i < estado.length; i++) {
            if(estado[i] == ' ') {
                return true; // Achou espaço, o jogo pode continuar
            }
        }
        return false; // Não tem espaço, o tabuleiro lotou
    }

    // Metodo de limpar a memoria e a interface
    private void reiniciarJogo() {
        for (int i = 0; i < estado.length; i++){
            estado[i] = ' ';
            inserirImagem(botoes[i], imgVazia);
        }
        jogando = true;
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
                    char simboloAtual = estado[indiceAtual];

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
        
        if(estado[indice] != ' ' || !jogando){
            return;
        }
        // Turno do humano.
        estado[indice] = 'o'; // Salva o dado na memoria (model)
        inserirImagem(botao, imgO); // Atualiza a tela (view)
        if (verificarVencedor()) {
            return;
        };

        // Verifica o empate
        if(!temEspacoVazio()) {
            jogando = false;
            finalizarPartida("Empate", "Deu velha! O jogo empatou.", JOptionPane.WARNING_MESSAGE);
            return; // Aborta o metodo
        }

        // Turno da maquina
            if(jogando){
                Timer timer = new Timer(800, e -> {
                    jogadaMaquina();
                    verificarVencedor();
                });
                
                timer.setRepeats(false);
                timer.start();
            }
        }

    // Lógica da jogada da maquina
    private void jogadaMaquina(){
        int indiceAleatorio;

        do {
            indiceAleatorio = jogadaMaquina.nextInt(estado.length);
        } while (estado[indiceAleatorio] != ' ');

        estado[indiceAleatorio] = 'x';
        inserirImagem(botoes[indiceAleatorio], imgX);
    }
}
