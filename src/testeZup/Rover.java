package testeZup;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Rover {
	
	// Limites da plataforma (é igual para todos rovers)
	private static int max_x,max_y;
	/* A matriz de direções indica o alcance que um rover possui baseado na sua direção 
	 *  pos na matriz | direção - alcance em x | alcance em y
	 *  0|N -  0|1
	 *  1|E -  1|0
	 *  2|S -  0|-1
	 *  3|W - -1|0
	 * Esta tabela dita a movimentação dos rovers dada uma direção,
	 * e é a mesma para todos os rovers 
	 */
	private static int[][] directions = new int[][]{ {0,1},{1,0},{0,-1},{-1,0} };
	
	// Coordenadas x e y do rover, e para onde está direcionado
	private int x,y,direction;
	
	public Rover(int x, int y, char direction){
		
		this.x = x;
		this.y = y;
		
		switch(direction) {
			case 'N': this.direction = 0; break;
			case 'E': this.direction = 1; break;
			case 'S': this.direction = 2; break;
			case 'W': this.direction = 3; break;
			default : System.out.print("parametro 'direcao' invalido na entrada para este caso de teste; ");
		}
		
	}
	
	public void executeCommand(char command) {
		switch(command){
			case 'L': this.turnLeft(); break;
			case 'R': this.turnRight(); break;
			case 'M': this.move(); break;
			default :
		}
		
	}
	
	/* move - 
	 * Atualiza a posição do rover de acordo com seu direcionamento incrementando
	 * as coordenadas x e y do rover com os valores da tabela de direções (directions)
	 */
	public void move() {
		int n_x = this.x + Rover.directions[direction][0];
		int n_y = this.y + Rover.directions[direction][1];
		
		//Caso o limite da plataforma seja ultrapassado, a movimentação não será efetuada
		if(this.x < 0 || this.x > Rover.max_x || this.y < 0 || this.y > Rover.max_y )
			return;
		
		this.x = n_x;
		this.y = n_y;
	}
	
	/* turnLeft - 
	 * Executa um giro à direita com o complemento da curva à esqueda, assim, tiramos 
	 * proveito do efeito de circularidade que o operador '%' nos oferece (turnRight)
	 */
	public void turnLeft() {
		this.turnRight(Rover.directions.length-1);
	}
	 
	/* turnLeft - 
	 * Executa um giro à direita com comprimento 1 (90º)
	 */
	
	public void turnRight() {
		this.turnRight(1);
	}
	
	/* turnRight -  
	 * Método genérico para virar à direita (também usada pela de virar à esquerda)
	 */
	public void turnRight(int t) {
		this.direction = (this.direction+t)%Rover.directions.length;
	}

	/* getPosition - 
	 * Retorna uma string que indica o posicionamento do rover na plataforma
	 * Ex.: 1 5 N
	 */
	public String getPosition() {
		char d = 'D';
		switch(this.direction) {
			case 0: d = 'N'; break;
			case 1: d = 'E'; break;
			case 2: d = 'S'; break;
			case 3: d = 'W'; break;
		}
		
		return this.x+" "+this.y+" "+d;
	}
	
	public static void setLimits(int max_x,int max_y) { 
		Rover.max_x = max_x;
		Rover.max_y = max_y;
	}
	
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println(" Nome do arquivo de teste? (main args)");
		}
		File input = new File("examples/"+args[0]);
	
		Scanner s = null;
		
		try {
			s = new Scanner(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		// Variáveis auxiliares para leitura de inteiros e caracteres 
		int x,y;
		char direction;
		String commands;
		
		// Leitura dos limites da plataforma
		if(s.hasNextInt()) x = s.nextInt();
		else {
			System.out.println("Esperava uma coordenada x para o limite superior");
			return;
		}
		
		if(s.hasNextInt()) y = s.nextInt();
		else {
			System.out.println("Esperava uma coordenada y para o limite superior");
			return;
		}		
		
		Rover.setLimits(x, y);

		Rover rover;
		
		// Executa enquanto existirem rovers a serem comandados na entrada
		while(s.hasNext()) {
			
			// Leitura das coordenadas de um rover
			if(s.hasNextInt()) x = s.nextInt();
			else {
				System.out.println("Esperava uma coordenada x para o rover");
				return;
			}
			
			if(s.hasNextInt()) y = s.nextInt();
			else {
				System.out.println("Esperava uma coordenada y para o rover");
				return;
			}		
			
			// Leitura da direção do rover
			if(s.hasNext() ) direction = s.next().trim().charAt(0);
			else {
				System.out.println("Esperava uma direcao ()");
				return;
			}		
			
			// Leitura da lista de instruções para o rover
			if(s.hasNext() ) commands = s.next().trim();
			else {
				System.out.println("Esperava uma sequencia de comandos");
				return;
			}
			
			
			// Cria o rover que receberá comandos
			rover = new Rover(x,y,direction);
			for(char c : commands.toCharArray() ){
				rover.executeCommand(c);
			}
			
			System.out.println(rover.getPosition());
			
			
		}
		
	}

	
}
