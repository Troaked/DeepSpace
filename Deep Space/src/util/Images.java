package util;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Images {

	
	
	public static Image loadImage(String path){
		Image img = null;
		try {
			img = new Image("res/images/" + path);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return img;
	}
}
