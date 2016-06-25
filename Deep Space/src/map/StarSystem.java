package map;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import javafx.scene.shape.Circle;
import util.Images;
import util.Names;

public class StarSystem {

	public Point mapLocation;
	private StarMap starMap;
	public int distanceFromCorner;
	
	private String name;
	
	private Point sun;
	private Point planet;
	
	private Image starImg;
	private Image planetImg;
	public Image mapStar;
	
	private int starAnimation = 0;
	private int starAnimationMax = 100;
	private int starImgIndex = 0;
	
	//private double planetRotation = 0;
	
	private int boxX, boxY, boxWidth, boxHeight;
	
	public StarSystem(Point p, StarMap map, int boxX, int boxY, int boxWidth, int boxHeight) {
		starImg = Images.loadImage("star/starFull.png");
		planetImg = Images.loadImage("star/star.png");
		mapStar = Images.loadImage("star/star.png");
		
		this.boxHeight = boxHeight;
		this.boxWidth = boxWidth;
		this.boxX = boxX;
		this.boxY = boxY;
		
		sun = new Point(boxX + boxWidth/2, boxY + boxHeight/2);
		planet = new Point(sun.x - 150, sun.y);
		
		Random r = new Random();
		mapLocation = new Point(p.x, p.y);
		distanceFromCorner = (int) Math.sqrt((p.x*p.x) + (p.y*p.y));
		mapStar.rotate(r.nextInt(50)); 
		this.starMap = map;
		name = Names.starNames[r.nextInt(Names.starNames.length)];
	}
	
	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(boxX, boxY, boxWidth, boxHeight);
		
		g.drawImage(starImg, sun.x - 32, sun.y - 32, sun.x + 32, sun.y + 32, starImgIndex*64, 0, starImgIndex*64+64, 64);
		
		g.setColor(new Color(255, 255, 255, 25));
		g.setLineWidth(1);
		int i = sun.x - planet.x;
		g.drawOval(planet.x, sun.y - i, i*2, i*2);
		
		g.drawImage(planetImg, planet.x - 8, planet.y - 8,  null);
		
		
		g.setLineWidth(10);
		g.setColor(Color.white);
		g.drawRect(boxX, boxY, boxWidth, boxHeight);
	}

	public void tick() {
		if(starAnimation >= starAnimationMax){
			starAnimation = 0;
			if(starImgIndex < 4){
				starImgIndex++;
			}else{
				starImgIndex = 0;
			}
		}else{
			starAnimation++;
		}
	}

	public void mouseEvent(int button, int x, int y) {
		close();
	}
	
	public boolean inBounds(Point p){
		if(p.x >= mapLocation.x && p.x <= (mapLocation.x + 16)){
			if(p.y >= mapLocation.y && p.y <= (mapLocation.y + 16)){
				return true;
			}
		}
		return false;
	}
	
	public void open(){
		
	}
	
	public void close(){
		starMap.systemOpen = false;
	}
	
	public Image getMapStar() {
		return mapStar;
	}

}
