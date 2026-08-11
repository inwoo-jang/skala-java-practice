package java_basic.oop.stock;

/** Stock을 상속받고 배당률 상태와 출력 동작을 확장한 우선주입니다. */
public class PreferredStock extends Stock {
    private double dividendRate;

    public PreferredStock(String name, double price, double dividendRate) {
        // 부모 Stock의 생성자를 먼저 호출합니다.
        super(name, price);
        this.dividendRate = dividendRate;
    }

    @Override
    public void printInfo() {
        System.out.println("[우선주] 종목: " + getName()
                + ", 가격: " + getPrice() + "원, 배당률: " + dividendRate + "%");
    }
}
