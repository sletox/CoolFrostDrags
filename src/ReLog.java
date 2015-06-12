import java.awt.Point;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Mouse;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.scripts.framework.Strategy;
import org.rev317.min.api.methods.SceneObjects;


public class ReLog implements Strategy{

	@Override
	public boolean activate() {
	   if (isLoggedIn()) {
          return false;
      }
      System.out.println("Logging In....");
      Frost.status = "Logging In....";
      return true;

     }
	

	@Override
	public void execute() {
	       
		   Point login = new Point(378,316);
	       
           Mouse.getInstance().click(login);
           Time.sleep(new SleepCondition() {
                   @Override
                   public boolean isValid() {
                           return isLoggedIn();
                   }
           }, 6000);
		
	}
	public boolean isLoggedIn() {
        try {
        return SceneObjects.getNearest().length > 0;
        } catch(IllegalArgumentException e) {
                // catch
        }
        return false;
       
}
}
