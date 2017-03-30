package group19.view;

import java.awt.*;
import javax.swing.*;

import group19.model.*;

public class GamePanel extends JPanel {
	private BallModel ball;
	private BrickModel brick;
	private PaddleModel paddle;
	private WarlordModel warlord;
	
	public GamePanel(BallModel ballModel, BrickModel brickModel, PaddleModel paddleModel, WarlordModel warlordModel) {
		ball = ballModel;
		brick = brickModel;
		paddle = paddleModel;
		warlord = warlordModel;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLUE);
		int x = (int) paddle.getXPos();
		int y = (int) paddle.getYPos();
		g.fillRect(x, y, paddle.getWidth(), paddle.getHeight());
		g.setColor(Color.RED);
		
		x = (int) ball.getXPos();
		y = (int) ball.getYPos();
		g.fillOval(x, y, ball.getDiameter(), ball.getDiameter());
		
		// TODO: paint warlord, bricks(arraylist?) 
	}
}
