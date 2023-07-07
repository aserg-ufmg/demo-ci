package br.ufmg.dcc;

public class App {
    public static void main (String[] args) {
        System.out.println("Oi");
        Calculadora c = new Calculadora();
        System.out.println(c.soma(5, 9));
    }
}
