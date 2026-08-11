package java_basic.oop.stock;

/** 일반 주식의 공통 상태와 행동을 표현하는 부모 클래스입니다. */
public class Stock {
    private String name;
    private double price;

    public Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void printInfo() {
        System.out.println("[일반주] 종목: " + name + ", 가격: " + price + "원");
    }
}
