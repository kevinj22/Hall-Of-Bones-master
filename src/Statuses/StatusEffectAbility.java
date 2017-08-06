package Statuses;

import Heros.Hero;

/**
 * This interface provides only one method, getAbilityStatus().  
 * If this interface is implemented properly in the corresponding abilities 
 * useBattleCommand method it should call, clone, and then 
 * apply getAbilityStatus to it's enemy.
 * @author Kevin
 *
 */
public interface StatusEffectAbility {
	/**
	 * Applies and returns a clone of an abilities Status effect.
	 * If this interface is implemented properly in the corresponding abilities 
	 * useBattleCommand method it should applyAbilityStatus to it's enemy.
	 * @param target target to apply status effect to
	 * @return Abilities status effect
	 */
	Status applyAbilityStatus(Hero target);
}
