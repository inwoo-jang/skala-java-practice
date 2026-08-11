package java_basic.oop.stock;

public class Main {
    static void printStockInfo(Stock stock) {
        // 실제 객체가 PreferredStock이면 오버라이딩된 메서드가 실행됩니다.
        stock.printInfo();
    }

    public static void main(String[] args) {
        Stock stock1 = new Stock("삼성전자", 80_000);
        Stock stock2 = new PreferredStock("LG전자", 60_000, 5.0);

        stock1.printInfo();
        stock2.printInfo();

        System.out.println("-----");
        printStockInfo(new Stock("SKALA", 80_000));
        printStockInfo(new PreferredStock("Netflix", 60_000, 10.0));
    }
}
