package br.ufmg.dcc;

public class SimpleCalculator {

  public int addition(int x, int y) {
    return x + y;
  }

  public int subtraction(int x, int y) {
    return x - y;
  }
  
  public int multiplication(int x, int y){
    return x * y;
  }
  
  public int division(int x, int y){
    return x / y;
  }
  
  public int factorial(int x){
    int result = 1;
    for(int i=2; i<=x; i++){
       result *= i;
    }
    return result;
  }

}
