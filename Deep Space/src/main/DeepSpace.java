package main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import map.StarMap;

public class DeepSpace extends StateBasedGame{
	
	public static Menu menu;

	public static void main(String[] args) {
		try {
			AppGameContainer game = new AppGameContainer(new DeepSpace());
			game.setDisplayMode(1280, 720, false);
			game.setVSync(true);
			game.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public DeepSpace() {
		super("Deep Space");
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		//menu = new Menu();
		//addState(menu);
		addState(new StarMap());
	}

}
