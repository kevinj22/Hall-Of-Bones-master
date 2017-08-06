package Statuses;

import Heros.Hero;
/**
 * Interface to make a HashMap of anonymous functions that refer to a Hero classes setters within the Hero class.
 * This allows me to set a string in each status effect of the stat it will effect.
 * Rather than specifically define each stauts affected i.e Hero.getStat("health") vs Hero.getHealth()
 * So many status effects can use the general form of hero.getStat(String stat) 
 * Saves me from rewriting the update and apply status functions numerous times. 
 * @author Kevin
 *
 */
public interface StatSet {
	
	/**
	 * Will be used to create anonymous functions within a HashMap of the Hero class that will set a hero's fields.
	 * @param hero current hero who stats you wish to change 
	 * @param val value to set to
	 * @return A getter function from the hero
	 */
	void statSet(Hero hero, int val);
}
