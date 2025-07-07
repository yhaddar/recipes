package com.recipes.recipe;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@SpringBootTest
class RecipeApplicationTests {

	private CalculeClass calculeClass = new CalculeClass();

	@Test void addition(){
		assertEquals(3, this.calculeClass.add(1, 2));
	}

	public static class CalculeClass {
		int add(int a, int b) {
			return a + b;
		}
	}


}
