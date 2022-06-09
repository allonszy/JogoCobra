
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class Painel extends JPanel implements ActionListener{

	static final int LARGURA = 1300;
	static final int ALTURA = 750;
	static final int UNITARIO = 50;
	static final int UNITARIOJOGO = (LARGURA*ALTURA)/(UNITARIO*UNITARIO);
	static final int DELAY = 85;
	final int x[] = new int[UNITARIOJOGO];
	final int y[] = new int[UNITARIOJOGO];
	int CorpoCobra = 6;
	int FrutaComida;
	int FrutaX;
	int FrutaY;
	char direcao = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	Painel(){
		random = new Random();
		this.setPreferredSize(new Dimension(LARGURA,ALTURA));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		InicioJogo();
	}
	public void InicioJogo() {
		NovaFruta();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		
		if(running) {
			/* PARA QUADRICULADO
			for(int i=0;i<ALTURA/UNITARIO;i++) {
				g.drawLine(i*UNITARIO, 0, i*UNITARIO, ALTURA);
				g.drawLine(0, i*UNITARIO, LARGURA, i*UNITARIO);
			}
			*/
			g.setColor(Color.green);
			g.fillOval(FrutaX, FrutaY, UNITARIO, UNITARIO);
		
			for(int i = 0; i< CorpoCobra;i++) {
				if(i == 0) {
					g.setColor(Color.magenta);
					g.fillRect(x[i], y[i], UNITARIO, UNITARIO);
				} else {
					g.setColor(Color.magenta);
					g.fillRect(x[i], y[i], UNITARIO, UNITARIO);
				}			
			}
			g.setColor(Color.white);
			g.setFont( new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrica = getFontMetrics(g.getFont());
			g.drawString("Pontuação: "+FrutaComida, (LARGURA - metrica.stringWidth("Pontuação "+FrutaComida))/2, g.getFont().getSize());
		}
		else {
			gameOver(g);
		}
		
	}
	public void NovaFruta(){
		FrutaX = random.nextInt((int)(LARGURA/UNITARIO))*UNITARIO;
		FrutaY = random.nextInt((int)(ALTURA/UNITARIO))*UNITARIO;
	}
	public void Mover(){
		for(int i = CorpoCobra;i>0;i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		switch(direcao) {
		case 'U': //up
			y[0] = y[0] - UNITARIO;
			break;
		case 'D': //down
			y[0] = y[0] + UNITARIO;
			break;
		case 'L': //left
			x[0] = x[0] - UNITARIO;
			break;
		case 'R': //right
			x[0] = x[0] + UNITARIO;
			break;
		}
		
	}
	public void ChecarFruta() {
		if((x[0] == FrutaX) && (y[0] == FrutaY)) {
			CorpoCobra++;
			FrutaComida++;
			NovaFruta();
		}
	}
	public void ChecarColisao() {
		//checar se a cabeça da cobra colide com o corpo
		for(int i = CorpoCobra;i>0;i--) {
			if((x[0] == x[i])&& (y[0] == y[i])) {
				running = false;
			}
		}
		//checa se a cabeça toca a esquerda
		if(x[0] < 0) {
			running = false;
		}
		//checa se a cabeça toca a direita
		if(x[0] > LARGURA) {
			running = false;
		}
		//checa se a cabeça toca o topo
		if(y[0] < 0) {
			running = false;
		}
		//checa se a cabeça toca a base
		if(y[0] > ALTURA) {
			running = false;
		}
		
		if(!running) {
			timer.stop();
		}
	}
	public void gameOver(Graphics g) {
		//Pontuação
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 40));
		FontMetrics metrica1 = getFontMetrics(g.getFont());
		g.drawString("Pontuação Final: "+FrutaComida, (LARGURA - metrica1.stringWidth("Pontuação Final: "+FrutaComida))/2, g.getFont().getSize());
		//texto de Fim de Jogo
		g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrica2 = getFontMetrics(g.getFont());
		g.drawString("Fim de Jogo", (LARGURA - metrica2.stringWidth("Game Over"))/2, ALTURA/2);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			Mover();
			ChecarFruta();
			ChecarColisao();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direcao != 'R') {
					direcao = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direcao != 'L') {
					direcao = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direcao != 'D') {
					direcao = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direcao != 'U') {
					direcao = 'D';
				}
				break;
			}
		}
	}
}