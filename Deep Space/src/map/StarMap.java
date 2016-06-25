package map;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import util.Images;

public class StarMap extends BasicGameState{

	private List<StarSystem> stars = new ArrayList<StarSystem>();
	private StarSystem shipLocation;
	
	private int deathDiameter = 1;
	
	private Image background;
	
	private int deathExpand = 0;
	private int deathExpandMax = 200;
	
	public boolean systemOpen = false;
	private StarSystem selectedSystem;
	
	private GameContainer contain;

	@Override
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		background = Images.loadImage("starMapBack.jpg");
		contain = container;
		
		shipLocation = new StarSystem(new Point(50, 50), this, 100, 100, container.getWidth() - 200, container.getHeight() - 200);
		stars.add(shipLocation);
		System.out.println(shipLocation.mapLocation.getX() + ", " + shipLocation.mapLocation.getY());
		
		Random r = new Random();
		for(int i = 0 ; i <= 10 ; i++){
			Point p;
			boolean canPlace = true;
			do{
				canPlace = true;
				p = new Point(75 + r.nextInt(400), 75 + r.nextInt(400));
				for(StarSystem s : stars){
					if(p.x >= s.mapLocation.x - 30 && p.x <= s.mapLocation.x + 30){
						if(p.y >= s.mapLocation.y - 30 && p.y <= s.mapLocation.y + 30){
							canPlace = false;
							break;
						}
					}
				}
			}while(!canPlace);
			stars.add(new StarSystem(p, this, 100, 100, container.getWidth() - 200, container.getHeight() - 200));
		
		}
		
	}

	@Override
	public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		for(int x = 0 ; x <= container.getWidth() ; x+=background.getWidth()){
			for(int y = 0; y<= container.getHeight() ; y += background.getHeight()){
				g.drawImage(background, x, y);
			}
		}
		
		for(StarSystem s : stars){
			g.drawImage(s.mapStar, s.mapLocation.x, s.mapLocation.y);
		}
		g.setColor(Color.red);
		g.setLineWidth(3);
		g.drawOval(shipLocation.mapLocation.x - 8, shipLocation.mapLocation.y - 8, 32, 32);
		
		g.setColor(Color.black);
		g.fillOval(0-deathDiameter/2, 0-deathDiameter/2, deathDiameter, deathDiameter);
		if(systemOpen){
			//renderSystem
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, container.getWidth(), container.getHeight());
			selectedSystem.render(g);
		}
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {
		if(systemOpen){
			selectedSystem.tick();
		}else{
			for(StarSystem s : stars){
				s.getMapStar().rotate(0.50f);
			}
		}
		
		List<StarSystem> remove = new ArrayList<>();
		for(StarSystem s : stars){
			if(s.distanceFromCorner <= deathDiameter / 2){
				remove.add(s);
				s.close();
			}
		}
		
		for(StarSystem s : remove){
			stars.remove(s);
		}
		
		if(deathExpand >= deathExpandMax){
			deathDiameter += 50;
			deathExpand = 0;
		}else{
		//	deathExpand++;
		}
		
	}
	
	@Override
	public void mousePressed(int button, int x, int y) {
		Input input = contain.getInput();
		x = input.getMouseX();
		y = input.getMouseY();
		
		System.out.println(x + ", " + y);
		if(systemOpen){
			selectedSystem.mouseEvent(button, x, y);
		}else{
			for(StarSystem s : stars){
				if(s.inBounds(new Point(x, y))){
					selectedSystem = s;
					systemOpen = true;
					s.open();
					return;
				}
			}
		}
		
		if(selectedSystem !=null){
			
		}
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
