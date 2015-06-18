package coolfrostdrags;

import org.parabot.environment.scripts.framework.Strategy;
import org.parabot.environment.scripts.framework.SleepCondition;
import org.parabot.environment.api.utils.Time;
import org.parabot.environment.input.Keyboard;
import org.rev317.min.api.methods.Inventory;
import org.rev317.min.api.methods.Menu;
import org.rev317.min.api.methods.Players;
import org.rev317.min.api.methods.Skill;
import org.rev317.min.api.wrappers.Npc;

public class Attack implements Strategy{
	//boolean quickPrayerStatus = false;
	@Override
	public boolean activate() {
		//0. player has at least 1 food
		//1. if player is in the frostlair 
		//2. Player is not in combat
		//3. there are no bones on the ground
		//4. hp is higher than the hp to eat at
		 //Npc[] dragon = Npcs.getNearest(10775);
		
		
		//System.out.println("Attack.java");
			     //DEBUG STATEMENTS
		
		//Npc drag = Frost.getDrag();
		boolean atFrostLair = Frost.frostlair.contains(Players.getMyPlayer().getLocation());
		boolean atSouthDrags = Frost.southDrags.contains(Players.getMyPlayer().getLocation());
		boolean tooFar = false;
		if (Frost.getGroundBones() != null ){
			if (Frost.getGroundBones().distanceTo() > 10){
			tooFar = true;
			}
		}
		return (Inventory.getCount(Frost.foodId) > 0  && (atFrostLair || atSouthDrags) &&				                    
						 !Players.getMyPlayer().isInCombat() && (Frost.getGroundBones() == null || tooFar)
				&& Skill.HITPOINTS.getLevel() >= Frost.hp);				
	}

	@Override
	public void execute() {	
		 if(Frost.getDrag() != null){
				if(Players.getMyPlayer().getAnimation() <= 0){
					final Npc drag = Frost.getDrag();
					drag.interact(1);
					Frost.status = "Attempting to Attack";
					Time.sleep(new SleepCondition(){
						@Override
						public boolean isValid() {
							if(drag.isInCombat() && !Frost.taken){
								Frost.status = "In combat";
								Time.sleep(new SleepCondition(){
									@Override
									public boolean isValid() {
										return Players.getMyPlayer().isInCombat();
									}								
								}, 5000);
								return true;
							}
							/*if(drag.isInCombat() && Frost.taken){
								Frost.status = "some fuckboi already took it. gonna look for anotha";
								return true;
							}*/
						
							return false; 
						}					
					}, 8000);
					Time.sleep(new SleepCondition(){
						@Override
						public boolean isValid() {
							if(Players.getMyPlayer().isInCombat()){
								Frost.status = "In combat";
								if (Frost.quickPrayerStatus == false && Frost.useQuickPrayer){      //////////////////////
									 Frost.status = "Activating Quick Prayer";
									 Menu.sendAction(1500, 1025, 0, 0, 2);
									 Frost.quickPrayerStatus = true;
									 Time.sleep(1000, 1200);
								}
								
								if(Inventory.getCount(Frost.foodId) > 0 && Skill.HITPOINTS.getLevel() < Frost.hp){
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
									
									return false;								
									}							
									if(Players.getMyPlayer().getAnimation() == 1156){
										Frost.status = "Re-attacking dragon";
										drag.interact(1);
										Time.sleep(1000, 1200);
										Frost.status = "In combat";
										return false;
									}
									if(Skill.HITPOINTS.getLevel() < 30 && Inventory.getCount(Frost.foodId) == 0){
									
										Frost.status = "Emergency teleport activated";
										Keyboard.getInstance().sendKeys("::home");
										Time.sleep(6000, 6200);
										return true;
									}
								return false;
								}
							if(!Players.getMyPlayer().isInCombat() && Frost.taken){
								return true;
							}
							if(!Players.getMyPlayer().isInCombat() && !Frost.taken){
								Frost.status = "Waiting to loot bones";
								Time.sleep(new SleepCondition(){
									@Override
									public boolean isValid() {
										if (Frost.quickPrayerStatus == true && Frost.useQuickPrayer){/////////////////////
											Frost.status = "Turning off Prayer";
											 Menu.sendAction(1500, 1025, 0, 0, 2);
											 Frost.quickPrayerStatus = false;
											 Time.sleep(1000, 1200);
										}
										return Frost.getGroundBones() != null;
									}									
								}, 3000);
								return true;
								}
							return  false;	
						}				
					}, 90000);
					Frost.taken = false;
				}
				
			}else{
				Frost.status = "No avaliable dragons. Waiting";
				Time.sleep(new SleepCondition(){
					@Override
					public boolean isValid() {
						return Frost.getDrag() != null;
					}							
				}, 5000);
			}
	    }	
	}
		
		
	
	
	
	




