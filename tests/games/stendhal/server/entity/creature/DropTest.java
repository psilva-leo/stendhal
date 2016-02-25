package games.stendhal.server.entity.creature;

import static org.junit.Assert.*;

import java.util.List;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.creature.impl.DropItem;
import games.stendhal.server.maps.MockStendlRPWorld;

import org.junit.Test;

public class DropTest {

	@Test
	public void PoisonPotionTest() {
		boolean hasPotion = false;
		boolean hasPoison = false;
		MockStendlRPWorld.get();
		Creature mageGnome = SingletonRepository.getEntityManager().getCreature("mage gnome");
		List<DropItem> droppableItems = mageGnome.dropsItems;
		droppableItems.get(2).name = "";
		
		for(DropItem i : droppableItems) {
			if(i.name.equals("minor potion")){
				hasPotion = true;
			}
			if(i.name.equals("minor poison")){
				hasPoison = true;
			}
		}
		assertTrue(hasPotion);
		assertFalse(hasPoison);
	}

}
