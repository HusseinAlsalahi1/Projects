import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest2 {
	CoffeeFactory coffee;
	@BeforeAll
	void innit() {
		 coffee = new CoffeeFactory();
		
	}
	@Test
	void test1() {
		assertEquals(3.50, coffee.getCoffee("cb"));
	}
	@Test
	void test2() {

		assertEquals(4.00, new ExtraShot(coffee.getCoffee("lr")));
	}
	@Test
	void test3() {

		assertEquals(4.50, new Cream(new ExtraShot(coffee.getCoffee("dr"))));
	}
	@Test
	void test4() {
		assertEquals(5.00, new Sugar(new Cream(new ExtraShot(coffee.getCoffee("cb")))));
	}
	@Test
	void test5() {
		assertEquals(6.00, new Sugar(new Sugar(new Cream(new Cream(new ExtraShot(coffee.getCoffee("lr")))))));
	}
}
