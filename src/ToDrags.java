import org.parabot.environment.scripts.framework.Strategy;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.api.utils.Time;
import org.rev317.min.api.methods.Game;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.SceneObjects;
import org.rev317.min.api.wrappers.SceneObject;
import org.rev317.min.api.wrappers.Tile;


public class ToDrags implements Strategy{
    int cave_id = 2;
    
	@Override
	public boolean activate() {
		// TODO Auto-generated method stub
		boolean atFrostLair = Frost.frostlair.contains(Players.getMyPlayer().getLocation());
		boolean atSouthDrags = Frost.southDrags.contains(Players.getMyPlayer().getLocation());
		boolean atSouthPath = Frost.southDragsPath.contains(Players.getMyPlayer().getLocation());
		boolean atFrostTeleport = Frost.frostTeleport.contains(Players.getMyPlayer().getLocation());
		
		return (Inventory.getCount(Frost.foodId) > 0 && (!atFrostLair && !atFrostTeleport && !atSouthPath && !atSouthDrags) && (Game.getOpenInterfaceId() != 5292)
				|| Inventory.getCount(Frost.foodId) > 0 && atFrostTeleport);		
	}

	@Override
	public void execute() {
		final boolean atFrostLair = Frost.frostlair.contains(Players.getMyPlayer().getLocation());
		final boolean atSouthDrags = Frost.southDrags.contains(Players.getMyPlayer().getLocation());
		final boolean atFrostTeleport = Frost.frostTeleport.contains(Players.getMyPlayer().getLocation());
		
		/**
		 * If the player is not in the frost dragon teleport area and he's not in the frost dragon liar either, then teleport
		 */
		if (Inventory.getCount(Frost.foodId) > 0 && (!atFrostLair && !atFrostTeleport) 
				&& (Game.getOpenInterfaceId() != 5292)){
			  Frost.status = "Going to drags";
			  Menu.sendAction(1107, 1073781425, 49, 53, 2, 0);  //open spellbook
			  Time.sleep(500,700);
			  Menu.sendAction(315, 174, 512, 1174, 2213, 1);  //slayer teleport
			  Time.sleep(500,700);
			  Menu.sendAction(315, 174, 512, 2496, 2213, 1);   //frost dragons
			  Time.sleep(1000,1200);
			  Menu.sendAction(315, 9682944, 188, 2494, 591, 1); //Frost Dragons (Ice Path)
			  Time.sleep(3500,4000);			 	
		}
		
		//if you have food and you're in the frostteleport area, then enter the cave or go to south drags depending on what you selected.
		if (atFrostTeleport && Inventory.getCount(Frost.foodId) > 0){
			if (Frost.use100Dung){
			Frost.status = "Entering cave";
			SceneObject[] Cave = SceneObjects.getNearest(2);
			Cave[0].interact(0); 
			Time.sleep(new SleepCondition(){
				@Override
				public boolean isValid() {
					return atFrostLair;
				}				
			}, 3000);	
			}
			else {
				Frost.status = "Walking to South Drags";
				new Tile(2865, 9923).walkTo();
				Time.sleep(new SleepCondition(){
					@Override
					public boolean isValid() {
						return atSouthDrags;
					}				
				}, 3000);	
			}
		}		
	}	
}
