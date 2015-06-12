import org.parabot.environment.api.utils.Time;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.Skill;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;

public class Eat implements Strategy{

	@Override
	public boolean activate() {
		//1. if player is in edgeville and his hp is not 99 and has food in his inventory
		//2. if player's hp is less than the food to eat hp and has food in inventory
		//3. if player is not in combat and there is a dragon bone on the floor, then eat to make room
		boolean atEdge = Frost.InEdge.contains(Players.getMyPlayer().getLocation());
		return ((Skill.HITPOINTS.getLevel() != 99) && (Inventory.getCount(Frost.foodId) > 0) 
				&& atEdge) || Inventory.getCount(Frost.foodId) > 0 && (Skill.HITPOINTS.getLevel() <= Frost.hp) || 
				(!Players.getMyPlayer().isInCombat() && Frost.getGroundBones() != null && Inventory.isFull() && Frost.tries < 5);
	}

	@Override
	public void execute() {
		Frost.status = "Eating";
		Menu.sendAction(74, 385, Inventory.getItem(Frost.foodId).getSlot(), 3214, 2213, 4);
		Time.sleep(new SleepCondition(){
			@Override
			public boolean isValid() {
				return Players.getMyPlayer().getAnimation() == 829;
			}			
		}, 3000);
		Time.sleep(new SleepCondition(){
			@Override
			public boolean isValid() {
				return Players.getMyPlayer().getAnimation() == -1;
			}			
		}, 3000);
		
	}

}
