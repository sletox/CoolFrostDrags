import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.rev317.min.api.methods.Bank;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;



public class Banking implements Strategy{
	    
	   int attackPot = 2441;
	   int strPot = 2437;
	   int prayerPot;
	   int sumScrolls;
	   int pakyak;
	   

		@Override
		public boolean activate() {
           // boolean atEdge = Frost.InEdge.contains(Players.getMyPlayer().getLocation());
			return Inventory.getCount(Frost.foodId) == 0;
		}

		@Override
		public void execute() {
			boolean atEdge = Frost.InEdge.contains(Players.getMyPlayer().getLocation());
			boolean atEdgeBank = Frost.InBank.contains(Players.getMyPlayer().getLocation());
			// TODO Auto-generated method stub
			//System.out.println(Players.getMyPlayer().getHealth());
			/**
			 * 1. Bank interface NOT open
			 * 2. player has less than 2 food
			 * 3. the player is currently not in the bank
			 * 4. Not in Edgeville
			 */
			if (Game.getOpenInterfaceId() != 5292 && Inventory.getCount(Frost.foodId) <= 2 && (!Frost.InEdge.contains(Players.getMyPlayer().getLocation()))) 
			{
				
				Frost.status = "Banking";
				Keyboard.getInstance().sendKeys("::home");
		        Time.sleep(2000);
			}
			if (atEdge) {
		        new Tile(3093, 3494).walkTo();
		        Time.sleep(2000);
		        }
			/**
			 * 1. if the player is inside the bank area
			 * 2. Bank interface NOT open
			 * 3. inventory less than 2 food
			 */
			if (atEdgeBank && Game.getOpenInterfaceId() != 5292 && (Inventory.getCount(Frost.foodId) <= 2)) {
	           
				 SceneObject[] booth = SceneObjects.getNearest(2213);
			        if (Game.getOpenInterfaceId() != 5292) {
			            if (booth.length > 0) {
			                while (Game.getOpenInterfaceId() != 5292 && booth[0] != null) {
			                    booth[0].interact(1);
			                    Time.sleep(new SleepCondition() {
			                        @Override
			                        public boolean isValid() {
			                            return Game.getOpenInterfaceId() == 5292;
			                        }
			                    }, 3000);
			                }
			            }
			        }
							
	            Time.sleep(1000);
	        }
			
			if (Game.getOpenInterfaceId() == 5292 && Inventory.getCount(Frost.foodId) <= 2) {
				Frost.status = "Grabbin Deez Nuts";
	            Time.sleep(1000);
	          //  Frost.setNumBones(Frost.getNumBones() + Inventory.getCount(18831));
	           // Frost.setNumVisage(Frost.getNumVisage() + Inventory.getCount(11287));
	            Menu.sendAction(646, 385, 0, 21012, 2213, 1); //deposit all
	            Time.sleep(1000);
	            
	            
	 
	            Bank.withdraw(Frost.foodId, Frost.numFood, 700);
	            Time.sleep(1000);
	            Bank.withdraw(attackPot, Frost.numAtkPots, 400);
	            Time.sleep(1000);
	            Bank.withdraw(strPot, Frost.numStrPots, 400);
	            Time.sleep(1000);
	            
	            Bank.close();
	
	        }
			
		}
		
		
		
		
		

	}