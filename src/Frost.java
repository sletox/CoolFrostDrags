import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.parabot.environment.api.interfaces.Paintable;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.Category;
import org.parabot.environment.scripts.Script;
import org.parabot.environment.scripts.ScriptManifest;
import org.rev317.min.api.events.MessageEvent;
import org.rev317.min.api.events.listeners.MessageListener;
import org.rev317.min.api.methods.GroundItems;
import org.rev317.min.api.wrappers.GroundItem;
import org.rev317.min.api.wrappers.Npc;
import org.rev317.min.api.wrappers.Tile;
import org.rev317.min.api.methods.Npcs;
import org.parabot.environment.scripts.framework.Strategy;


// http://www.thetechgame.com/Archives/t=771090/rsps-317-server-item-lists.html  **ikov item db **
@ScriptManifest(author = "Coolpopo", category = Category.COMBAT, description = "", name = "CoolFrostDrags", servers = { "Ikov" }, version = 1.0)
public class Frost extends Script implements Paintable, MessageListener{
	
	//array list will hold strategies
	private final ArrayList<Strategy> strategies = new ArrayList<Strategy>();
	private long startTime;
	public static String status;
	
	//Variables to increment
	public static int numBones; 
	public static int numVisage;
	
	
	public static int foodId = 386;
   
	public static int numAtkPots = 1;      //change later
	public static int numStrPots = 1;
	public static int numFood = 25;
	public static int numPrayer;
	public static int hp;
	
	public static int interfaceBoneId = 18831;
	public static int boneId = 18830;
	public static int interfaceVisageId = 11287;
	public static int visageId = 11286;
	
	public static boolean taken = false;
	public static int tries;  //attempts at picking up bones
	
	
	public static boolean quickPrayerStatus = false;
	
	//GUI SETTINGS
	public static boolean use100Dung = false;
	//public static boolean useQuickPrayer = false;
	//public static boolean useYak;

	//Areas
	public static Area InBank = new Area(new Tile(3091, 3487), new Tile(3091, 3500), new Tile(3099, 3500), new Tile(3099, 3487));
	public static Area InEdge = new Area(new Tile(3084, 3486 ), new Tile(3083, 3505), new Tile(3101, 3505), new Tile(3101, 3086));
	public static Area frostlair = new Area(new Tile(2823, 3745), new Tile(2823, 3767), new Tile(2870, 3767), new Tile(2871, 3745));
    public static Area frostTeleport = new Area(new Tile(2864, 9954 ), new Tile(2864, 9959), new Tile(2871, 9959), new Tile(2871, 9954));
    public static Area southDrags = new Area(new Tile(2860,9920), new Tile(2860,9930), new Tile(2873, 9930), new Tile (2868, 9920));
    public static Area southDragsPath = new Area(new Tile(2859,9925), new Tile(2859,9959), new Tile(2871, 9959), new Tile(2871, 9925));
    
    private final Color color1 = new Color(0, 0, 0);
    private final Color color2 = new Color(255, 255, 255);
    private final BasicStroke stroke1 = new BasicStroke(1);
    private final Font font1 = new Font("Calibri", 1, 15);
    private final Font font2 = new Font("Calibri", 0, 12);
    
	public static ArrayList<GroundItem> glitchedBones = new ArrayList<GroundItem>();
    
    public boolean onExecute() {
    	  
    	  GUI window = new GUI();
    	  window.setVisible(true);
    	  while(window.isVisible()){
    		  sleep(20);
    		 
    	  }
    	  
    	  startTime = System.currentTimeMillis();
		  Frost.status = "Initializing Script";         
		  strategies.add(new ReLog());
		  strategies.add(new Banking());
		  strategies.add(new ToDrags());
		  strategies.add(new Pot());
		  strategies.add(new Attack());
		  strategies.add(new Eat());
		  strategies.add(new Loot());

	      provide(strategies);
		  return true;
		}
		
		public void onFinish() {
			System.out.println("bones hustled: " + Frost.numBones);
		}
		
	public void paint(Graphics g1)
	    {
	        // Sets the color for the text
	       // g.setColor(Color.WHITE);
	        // Sets the font to a 14-point Helvetica
	        //g.setFont(new Font("Helvetica", Font.PLAIN, 14));
	 
	        // g.drawString(String message, int x, int y);
	        // The above is the format for how a drawString generally works
	        // X and y are the coordinates on Parabot where the String will appear
	        // (0, 0) is in the top-left
		
		  Graphics2D g = (Graphics2D)g1;
          g.setColor(color1);
          g.fillRect(-6, 249, 520, 89);
          g.setStroke(stroke1);
          g.drawRect(-6, 249, 520, 89);
          g.setFont(font1);
          g.setColor(color2);
          g.drawString("Coolpopo's Frost Dragon Killer", 9, 264);
          g.setFont(font2);
	      g.drawString("Timer: " + runTime(startTime), 8, 286);
	      g.drawString("Status : " + Frost.status, 8, 325);
	      g.drawString("Bones collected: "+ Frost.numBones + "(" + getPerHour(numBones) + "/Hr)", 8, 305);
	      g.drawString("Visages collected: " + Frost.numVisage, 285, 305);
	      g.drawString("Coolpopo", 462, 333);
	    }

    public static Npc getDrag(){
    	
    	Npc[] Drags = Npcs.getNearest(10775);
    	if (Drags != null && Drags.length > 0){
    	      for (Npc d : Drags){
    	           if (!d.isInCombat() && (frostlair.contains(d.getLocation()) && d.distanceTo() < 10 || southDrags.contains(d.getLocation()))){
    	                 return d;
    	        }
    	   } 
    	}
    	return null;
		  
     }
    
    public static GroundItem getGroundBones(){
    	GroundItem[] gb = GroundItems.getNearest(boneId, 11286);
		if(gb != null && gb.length > 0){
			for(GroundItem groundBones : gb){
				if(frostlair.contains(groundBones.getLocation()) && glitchedBones.contains(groundBones)){
					return null;
				}else{
					return groundBones;
				}
			}
		}
		return null;
    }

    @Override
	public void messageReceived(MessageEvent m) {
		if((m.getMessage().contains("These entities are already under attack."))){
			taken = true;
			Time.sleep(50, 100);
		}		
	}
    public static String runTime(long i) {
        DecimalFormat nf = new DecimalFormat("00");
        long millis = System.currentTimeMillis() - i;
        long hours = millis / (1000 * 60 * 60);
        millis -= hours * (1000 * 60 * 60);
        long minutes = millis / (1000 * 60);
        millis -= minutes * (1000 * 60);
        long seconds = millis / 1000;
        return nf.format(hours) + ":" + nf.format(minutes) + ":"
                        + nf.format(seconds);
	 }
	public int getPerHour(int variable) {
        return (int) (((double) (variable - 0) * 3600000D) / (double) (System
                        .currentTimeMillis() - startTime));
	}
    
}
	
	

