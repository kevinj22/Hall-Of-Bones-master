package Statuses;

import java.awt.Image;
import java.util.concurrent.TimeUnit;

import BattleMVC.BattleController;
import BattleMVC.BattleModel;
import Heros.Hero;

/**
 * OffensiveStatusPerTurn extends Status. OffensiveStatusPerTurn DOES tick per turn.
 * 
 * @author Kevin
 *
 */
public class OffensiveStatusPerTurn extends Status{

	
	/**
	 * OffensiveStatusNotPerTurn argument constructor.
	 * @param name status name
	 * @param statAffected stat affected
	 * @param effectStrength effect strength
	 * @param duration duration of status
	 * @param defaultDuration default duration of status
	 */
	public OffensiveStatusPerTurn(String name, String statAffected, int effectStrength, int duration, int defaultDuration, Image animationImage)
	{
		super(name,statAffected,effectStrength,duration,defaultDuration, animationImage);
	}
	
	/**
	 * Clone method, since it's parent Status is a abstract class we can't create instances of it. 
	 * This is used in updating a hero's statuses to create a copy of the proper run type
	 * so we can still apply polymorphism. 
	 * 
	 * Why not a static factory method? 
	 * Polymorphism doesn't apply to static methods.
	 */
	public OffensiveStatusPerTurn(Status other)
	{
		super(other);
	}
	
	/**
	 * UpdateStatus written as a PER TURN method. 
	 * If duration is 0 remove the status ailment.
	 * Else tick the status effect.
	 * @param hero hero whose statuses are being updated 
	 */
	@Override
	public boolean updateStatus(Hero hero)
	{
		int duration = this.getDuration();
		
		if(duration == 0)
		{
			this.removeStatus(hero);
		}
		else 
		{
			this.applyStatusEffect(hero);
		}
		return false; 
	}

	/**
	 * ApplyStatusEffect adjusts the appropriate statistic on the hero depending on the status effect. 
	 * Since this is a per turn status the status effect is applied each tick.
	 * Since this is a OffenseStatus the amount is SUBTRACTED to the hero's statistic VS a BuffStatus in which it is ADDED.
	 * Reduces the duration of the status effect, if the duration is 0 it will be removed in updateStatus.
	 * @param hero who to apply the status effect to
	 */
	@Override
	public void applyStatusEffect(Hero hero)
	{
		int duration = this.getDuration();
		int effectStrength = this.getEffectStrength();
		String statAffected = this.getStatAffected();
		int statToChange = hero.getStat(statAffected);
		int changedStat;
		if(effectStrength > 0)
		{
			changedStat = statToChange - effectStrength;
		}
		else
		{
			changedStat = statToChange + effectStrength;
		}
		hero.setStat(statAffected, changedStat);
		System.out.println(this.toString() + " being updated. Stat affected: " + this.getStatAffected() + " before: " + statToChange + " after: " + changedStat);
		duration--;
		this.setDuration(duration);
		// If duration is 0 remove the status
		if(duration == 0)
		{
			this.removeStatus(hero);
		}
	}
	
	/**
	 * ApplyStatusEffect with the controller parameter adjusts the appropriate statistic on the hero depending on the status effect and animates
	 * on status tick. 
	 * Since this is a per turn status the status effect is applied each tick.
	 * Since this is a OffenseStatus the amount is SUBTRACTED to the hero's statistic VS a BuffStatus in which it is ADDED.
	 * Reduces the duration of the status effect, if the duration is 0 it will be removed in updateStatus.
	 * @param hero who to apply the status effect to
	 * @param controller the controller is needed to signal the status animation
	 * @return boolean crowd controlled or not, if crowd controlled must skip that heros turn.
	 */
	public boolean updateStatus(Hero hero, BattleController controller)
	{
		int duration = this.getDuration();
		if(duration == 0)
		{
			this.removeStatus(hero);
		}
		else 
		{
			this.applyStatusEffect(hero);
			controller.animateBattleCommand(hero,this.getAnimationImage(),true);
			try {
				TimeUnit.MILLISECONDS.sleep(BattleModel.DISPLAY_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return false; 
	}
	
	/**
	 * Clone method, since this is a abstract class we can't create instances of it. 
	 * This is used in updating a hero's statuses to create a copy of the proper run type
	 * so we can still apply polymorphism. 
	 * 
	 * Why not a static factory method? 
	 * Polymorphism doesn't apply to static methods.
	 * 
	 * This method also displays an implementation of co-variant return type: the class type returned in an overloaded method has been changed
	 * @return OffensiveStatusPerTurn deep copy with new time stamp to allow stacking 
	 * 
	 */
	@Override
	public OffensiveStatusPerTurn clone() {
		return new OffensiveStatusPerTurn(this);
	}

}
