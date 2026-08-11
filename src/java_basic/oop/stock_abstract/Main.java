package java_basic.oop.stock_abstract;

public class Main {
    static void printStockInfo(Stock stock) {
        stock.printInfo();
    }

    public static void main(String[] args) {
        // Stock은 추상 클래스이므로 new Stock(...)은 불가능하지만 변수 타입으로는 사용할 수 있습니다.
        Stock stock1 = new PreferredStock("삼성전자", 80_000, 0.0);
        Stock stock2 = new PreferredStock("LG전자", 60_000, 5.0);

        stock1.printInfo();
        stock2.printInfo();

        System.out.println("-----");
        printStockInfo(new PreferredStock("SKALA", 80_000, 0.0));
        printStockInfo(new PreferredStock("Netflix", 60_000, 10.0));
    }
}
