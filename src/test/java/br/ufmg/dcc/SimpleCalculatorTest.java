package br.ufmg.dcc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleCalculatorTest {
  private SimpleCalculator calc;

  @BeforeEach
  public void init(){
    calc = new SimpleCalculator();
  }

  @Test
  public void testAddition1() {
    int expected = 5;
    int result = calc.addition(2,3);
    assertEquals(expected, result);
  }

  @Test
  public void testAddition2() {
    assertEquals(-2, calc.addition(4,-6));
  }

  @Test
  public void testSubtraction(){
    assertEquals(0,calc.subtraction(1,1));
  }

  @Test
  public void testMultiplication(){
    assertEquals(6,calc.multiplication(2,3));
  }

  @Test
  public void testMultiplicationIdentity(){
    assertEquals(11,calc.multiplication(11,1));
  }

  @Test
  public void testMultiplicationNegative(){
    assertEquals(-10,calc.multiplication(5,-2));
  }

  @Test
  public void testMultiplicationTwoNegatives(){
    assertEquals(10,calc.multiplication(-5,-2));
  }

  @Test
  public void testDivision(){
    assertEquals(2, calc.division(8,4));
  }

  @Test
  public void testFactorial(){
    assertEquals(120, calc.factorial(5));
  }
}
