
import javax.swing.JFrame;

public class Frame extends JFrame{

	Frame(){
			
		this.add(new Painel());
		this.setTitle("Cobra");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
	}
}