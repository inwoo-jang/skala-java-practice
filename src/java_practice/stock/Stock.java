package java_practice.stock;

/**
 * 주식 한 종목의 이름과 현재 가격을 표현하는 클래스입니다.
 */
public class Stock {
    // 객체마다 서로 다른 종목명과 가격을 가집니다.
    protected String name;
    protected double price;

    /**
     * 전달받은 종목명과 가격으로 Stock 객체를 초기화합니다.
     */
    public Stock(String name, double price) {
        // this.name은 필드, name은 생성자의 매개변수입니다.
        this.name = name;
        this.price = price;
    }

    /**
     * 현재 주식 가격을 새로운 가격으로 변경합니다.
     */
    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }

    /**
     * 현재 객체가 가진 종목명과 가격을 출력합니다.
     */
    public void printInfo() {
        System.out.println("[일반주] 종목: " + name + ", 가격: " + price + "원");
    }
}
