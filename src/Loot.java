import org.parabot.environment.api.utils.Time;
import org.rev317.min.api.methods.GroundItems;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.wrappers.GroundItem;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;

public class Loot implements Strategy{
	
	@Override
	public boolean activate() {

      GroundItem[] dragonBones = GroundItems.getNearest(Frost.boneId, Frost.visageId);
      
      if (dragonBones != null){
        if (dragonBones.length > 0 && dragonBones[0].distanceTo() < 10){
            return true;
          }
        }
        return false;
	}

	@Override
	public void execute() {
		GroundItem bone = Frost.getGroundBones();
		if(Frost.tries <= 3){
			Frost.status = "Picking up bones";
			final int b = Inventory.getCount(Frost.interfaceBoneId);
			final int v = Inventory.getCount(Frost.interfaceVisageId);
			bone.take();
			Time.sleep(new SleepCondition(){
				@Override
				public boolean isValid() {
					if(Inventory.getCount(Frost.interfaceBoneId) == (b + 1)){
						Frost.status = "Bones succesfully taken";
						Frost.tries--;
						Frost.numBones++;
						return true;
					}
					else if (Inventory.getCount(Frost.interfaceVisageId) == (v + 1)){
						 Frost.status = "Visage succesfully taken";
						 Frost.numVisage++;
						 return true;
					 }
					
					return false;
				}			
			}, 3000);
			Time.sleep(1000, 1200);
			Frost.tries++;
		}else{
			Frost.status = "Bone is glitched. Adding to blacklist.";
			Frost.tries = 0;
			Frost.glitchedBones.add(bone);
		}
		      
     }	

}