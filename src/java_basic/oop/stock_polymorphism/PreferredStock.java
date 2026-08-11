package java_basic.oop.stock_polymorphism;

public class PreferredStock extends Stock {
    private double dividendRate;

    public PreferredStock(String name, double price, double dividendRate) {
        super(name, price);
        this.dividendRate = dividendRate;
    }

    // 부모와 같은 선언을 가진 오버라이딩 메서드입니다.
    @Override
    public void printInfo() {
        System.out.println("[우선주] 종목: " + getName()
                + ", 가격: " + getPrice() + "원, 배당률: " + dividendRate + "%");
    }

    // 이름은 같고 매개변수가 다른 오버로딩 메서드입니다.
    public void printInfo(String prefix) {
        System.out.println(prefix + "[우선주] 종목: " + getName()
                + ", 가격: " + getPrice() + "원, 배당률: " + dividendRate + "%");
    }
}
