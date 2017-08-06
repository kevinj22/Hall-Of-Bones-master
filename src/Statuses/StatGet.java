package Statuses;

import Heros.Hero;

/**
 * Interface to make a HashMap of anonymous functions that refer to a Hero classes getters within the Hero class.
 * This allows me to set a string in each status effect of the stat it will effect.
 * Rather than specifically define each stauts affected i.e Hero.getStat("health") vs Hero.getHealth()
 * So many status effects can use the general form of hero.getStat(String stat) 
 * Saves me from rewriting the update and apply status functions numerous times. 
 * @author Kevin
 *
 */
public interface StatGet {
	/**
	 * Will be used to create anonymous functions within a HashMap of the Hero class that represent the Hero's getter functions.
	 * @param hero current hero we wish to get stats from
	 * @return A getter function from the hero
	 */
	int statGet(Hero hero);
}
