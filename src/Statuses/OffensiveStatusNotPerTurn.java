package Statuses;

import java.awt.Image;
import java.util.concurrent.TimeUnit;

import BattleMVC.BattleController;
import BattleMVC.BattleModel;
import Heros.Hero;

/**
 * OffensiveStatusNotPerTurn extends Status. OffensiveStatusNotPerTurn do NOT tick per turn, they are a one time application
 * that is completely removed after it's duration.
 * 
 * @author Kevin
 *
 */
public class OffensiveStatusNotPerTurn extends Status{

	/**
	 * OffensiveStatusNotPerTurn argument constructor.
	 * @param name status name
	 * @param statAffected stat affected
	 * @param effectStrength effect strength
	 * @param duration duration of status
	 * @param defaultDuration default duration of status
	 */
	public OffensiveStatusNotPerTurn(String name, String statAffected, int effectStrength, int duration, int defaultDuration, Image animationImage)
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
	public OffensiveStatusNotPerTurn(Status other)
	{
		super(other);
	}
	
	/**
	 * UpdateStatus written as a NON per turn method. 
	 * If duration is equal to the initial duration since this is a once time apply status effect will apply once.
	 * If duration is not 0 or the initial duration update the duration of the status effect by removing 1.
	 * If duration is 0 remove the status ailment and restore the heros stats to what they originally were. 
	 * @param hero: hero whose statuses are being updated 
	 */
	@Override
	public boolean updateStatus(Hero hero)
	{
		boolean crowdControlled = false;
		int duration = this.getDuration();
		int effectStrength = this.getEffectStrength();
		String statAffected = this.getStatAffected();
		
		if(duration == 0)
		{
			int statToChange = hero.getStat(statAffected);
			int changedStat = statToChange - effectStrength;
			hero.setStat(statAffected, changedStat);
			System.out.println(this.toString() + " being removed. Stat affected: " + this.getStatAffected() + " before: " + statToChange + " after: " + changedStat);
			this.removeStatus(hero);
		}
		
		if(duration > 0)
		{
			if(this.getDuration() == this.getDefaultDuration())
			{
				this.applyStatusEffect(hero);
			}
			else // Not a per turn status and not initial time so need to update 
			{
				System.out.println(this.toString() + " being updated. Stat affected: " + this.getStatAffected() + " Current value: " + hero.getStat(statAffected));
				duration--;
				this.setDuration(duration);
			}
		}
		return crowdControlled; 
	}

	/**
	 * UpdateStatus written as a NON per turn method. Animates on controller and sends signal to update view after all status ticks.
	 * If duration is equal to the initial duration since this is a once time apply status effect will apply once.
	 * If duration is not 0 or the initial duration update the duration of the status effect by removing 1.
	 * If duration is 0 remove the status ailment and restore the heros stats to what they originally were. 
	 * @param hero hero whose statuses are being updated 
	 * @param controller the controller is needed to signal the status animation
	 * @return boolean crowd controlled or not, if crowd controlled must skip that heros turn.
	 */
	public boolean updateStatus(Hero hero, BattleController controller)
	{
		boolean crowdControlled = false;
		int duration = this.getDuration();
		int effectStrength = this.getEffectStrength();
		String statAffected = this.getStatAffected();
		
		if(duration == 0)
		{
			int statToChange = hero.getStat(statAffected);
			int changedStat = statToChange - effectStrength;
			hero.setStat(statAffected, changedStat);
			System.out.println(this.toString() + " being removed. Stat affected: " + this.getStatAffected() + " before: " + statToChange + " after: " + changedStat);
			this.removeStatus(hero);
		}
		
		if(duration > 0)
		{
			if(this.getDuration() == this.getDefaultDuration())
			{
				this.applyStatusEffect(hero);
			}
			else // Not a per turn status and not initial time so need to update 
			{
				System.out.println(this.toString() + " being updated. Stat affected: " + this.getStatAffected() + " Current value: " + hero.getStat(statAffected));
				duration--;
				this.setDuration(duration);
			}
			controller.animateBattleCommand(hero,this.getAnimationImage(),true);
			try {
				TimeUnit.MILLISECONDS.sleep(BattleModel.DISPLAY_SLEEP_TIME);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return crowdControlled; 
	}
	
	/**
	 * ApplyStatusEffect adjusts the appropriate statistic on the hero depending on the status effect. 
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
	 * 
	 * @return OffensiveStatusNotPerTurn deep copy with new time stamp to allow stacking 
	 */
	@Override
	public OffensiveStatusNotPerTurn clone() {
		return new OffensiveStatusNotPerTurn(this);
	}
	
}
