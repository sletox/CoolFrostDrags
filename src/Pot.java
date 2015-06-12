import org.parabot.environment.api.utils.Time;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.Item;

public class Pot implements Strategy{

	 private final int[] superStrengthPotIds = new int[]{2441, 158, 160, 162, 164};
	 private final int[] superAttackPotIds = new int[]{2437, 146, 148, 150, 152};
	@Override
	public boolean activate() {
		
		 Item[] strPot = Inventory.getItems(superStrengthPotIds);
		 Item[] atkPot = Inventory.getItems(superAttackPotIds);
		 boolean atFrostLair = Frost.frostlair.contains(Players.getMyPlayer().getLocation());
	     boolean atSouthDrags = Frost.southDrags.contains(Players.getMyPlayer().getLocation());
	     
		 if (atFrostLair || atSouthDrags){ 
		  if (Skill.ATTACK.getLevel() < 107 && atkPot != null && atkPot.length > 0)
              return true;

          if (Skill.STRENGTH.getLevel() < 107 && strPot != null && strPot.length > 0)
              return true;

          if (strPot == null && atkPot != null || atkPot == null && strPot != null)
        	  return false;

		 }
		 return false;
	}

	@Override
	public void execute() {
		Item[] strPot = Inventory.getItems(superStrengthPotIds);
		Item[] atkPot = Inventory.getItems(superAttackPotIds);
		
		if (Skill.ATTACK.getLevel() < 107){
			Menu.sendAction(74, atkPot[0].getId() - 1, atkPot[0].getSlot(), 3214, 9398, 4);
            Frost.status = "Potting";
            Time.sleep(1400, 1700);
		}
		if (Skill.STRENGTH.getLevel() < 107){
			Menu.sendAction(74, strPot[0].getId() - 1, strPot[0].getSlot(), 3214, 9398, 4);
            Frost.status = "Potting";
            Time.sleep(1400, 1700);
		}
	}

}
