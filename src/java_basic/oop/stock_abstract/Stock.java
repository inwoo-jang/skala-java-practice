package java_basic.oop.stock_abstract;

/** 직접 객체를 만들 수 없으며 자식에게 printInfo 구현을 강제하는 추상 클래스입니다. */
public abstract class Stock {
    private String name;
    private double price;

    protected Stock(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public abstract void printInfo();
}
