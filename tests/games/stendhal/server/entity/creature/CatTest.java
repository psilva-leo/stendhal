/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.creature;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import games.stendhal.server.maps.MockStendlRPWorld;

import java.util.Arrays;
import java.util.List;


import org.junit.BeforeClass;
import org.junit.Test;


import utilities.RPClass.CatTestHelper;

public class CatTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		CatTestHelper.generateRPClasses();
		MockStendlRPWorld.get();
	}

	List<String> foods = Arrays.asList("chicken", "trout", "cod", "mackerel", "char",
			"perch", "roach", "surgeonfish", "clownfish", "milk");

	/**
	 * Tests for Cat.
	 */
	@Test
	public void testCat() {
		final Cat kitty = new Cat();
		assertThat(kitty.getFoodNames(), is(foods));
	}

}
