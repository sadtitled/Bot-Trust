import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.Thread;

public class Main implements Runnable{
	public static List<InstructionPair> Instructions;
	public static Bot Orange;
	public static Bot Blue;
	public static int turn; // 0 == Orange, 1 == Blue
	
	public static Frame frame;
	
	public Main() {
		
	}
	
	public void run() {
		Orange = new Bot(100, Frame.HEIGHT / 2, Color.ORANGE);
			Blue = new Bot(100, Frame.HEIGHT / 2 - 20, Color.BLUE);
			Instructions = new ArrayList<InstructionPair>();
			
			Main main = new Main();
			frame = new Frame();
			frame.setVisible(true);
			try {
				Scanner input = new Scanner(System.in);
				int cases = input.nextInt();
				String bot;
				int button;
				int steps;
				for(int i = 0; i < cases; i++) {
					System.out.println("==================================");
					System.out.println("Problem No: " + (i+1));
					steps = input.nextInt();
					for(int j = 0; j < steps; j++) {
						bot = input.next();
						button = input.nextInt();
						Instructions.add(new InstructionPair(bot, button));
						if( bot.equals("O")) {
							Orange.Instructions.add(new InstructionPair(bot, button));
						} else if (bot.equals("B")) {
							Blue.Instructions.add(new InstructionPair(bot, button));
						}
					}
					main.simulate();
					Instructions.clear();
					Orange.reset(100, Frame.HEIGHT / 2);
					Blue.reset(100, Frame.HEIGHT / 2 - 20);
					System.out.println("==================================");
				}
				
				input.close();
			} finally {
				
			}
	}
	
	public static void main(String[] args) throws FileNotFoundException {
		Main m = new Main();
		m.run();
    }
	
	public static void drawBots(Graphics g) {
		Orange.drawBot(g);
		Blue.drawBot(g);
	}
	
	public void simulate()  {
		int instruction = 0;
		InstructionPair ip = Instructions.get(instruction);
		if(ip.getBot().equals("O")) {
			Orange.setTurn(true);
			Blue.setTurn(false);
		} else {
			Orange.setTurn(false);
			Blue.setTurn(true);
		}
		
		int step = 1;
		while(true) {
			System.out.println("Step No: " + step);
			// IF the bot clicked a button, it will 
			boolean oClicked = Orange.doNext();
			boolean bClicked = Blue.doNext();
			
			if(oClicked || bClicked) {
				instruction++;
				if(instruction >= Instructions.size())
					break;
				ip = Instructions.get(instruction);
				if(ip.getBot().equals("O")) {
					Orange.setTurn(true);
					Blue.setTurn(false);
				} else {
					Orange.setTurn(false);
					Blue.setTurn(true);
				}
			}
			step++;
			try {
    			Thread.sleep(500);                 
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			
			if(Orange.getDone() && Blue.getDone()) {
				break;
			}	
		}
		
	}
}