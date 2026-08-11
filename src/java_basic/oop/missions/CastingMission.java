package java_basic.oop.missions;

/**
 * OOP 미션: 업캐스팅과 다운캐스팅
 *
 * 개념:
 * - 업캐스팅: 자식 객체를 부모 타입 변수에 담는 것. 자동 변환됩니다.
 * - 다운캐스팅: 부모 타입 변수가 가리키는 실제 자식 타입으로 다시 변환하는 것.
 * - 잘못 다운캐스팅하면 ClassCastException이 발생합니다.
 *
 * 목표:
 * 1. PreferredStock 객체를 Asset 타입 변수에 업캐스팅하세요.
 * 2. 업캐스팅된 변수로 printInfo()를 호출해 오버라이딩을 확인하세요.
 * 3. instanceof로 실제 타입을 확인한 후 PreferredStock으로 다운캐스팅하세요.
 * 4. 다운캐스팅한 변수로 printDividend()를 호출하세요.
 * 5. Asset 배열에 Stock과 PreferredStock을 함께 담고 반복 출력하세요.
 *
 * 예상 출력:
 * [우선주] 삼성전자우, 70000.0원
 * 예상 배당금: 3500.0원
 * [일반주] SKALA, 15000.0원
 * [우선주] 삼성전자우, 70000.0원
 *
 * 힌트:
 * - 업캐스팅: Asset asset = preferredStock;
 * - 타입 검사: asset instanceof PreferredStock
 * - 안전한 패턴 매칭: asset instanceof PreferredStock preferred
 * - 전통적인 다운캐스팅: PreferredStock preferred = (PreferredStock) asset;
 */
public class CastingMission {
    public static void main(String[] args) {
        PreferredStock preferredStock = new PreferredStock("삼성전자우", 70_000, 5.0);

        // COMPLETED 1: preferredStock을 Asset 타입 변수 asset에 업캐스팅하세요.
        Asset asset = preferredStock;
        // COMPLETED 2: asset의 printInfo()를 호출하세요.
        // 변수 타입은 Asset이지만 실제 객체의 PreferredStock.printInfo()가 실행되어야 합니다.
        asset.printInfo();
        // TODO 3: asset이 PreferredStock인지 instanceof로 안전하게 검사하세요.
        // TODO 4: 검사에 성공하면 다운캐스팅 후 printDividend()를 호출하세요.

        Asset[] assets = {
                new Stock("SKALA", 15_000),
                new PreferredStock("삼성전자우", 70_000, 5.0)
        };

        // TODO 5: for-each문으로 모든 asset의 printInfo()를 호출하세요.

        /*
         * 도전 문제:
         * 아래 객체는 실제로 Stock입니다.
         * Asset normal = new Stock("일반주", 10_000);
         *
         * 다음 캐스팅을 실행하면 왜 ClassCastException이 발생할까요?
         * PreferredStock wrong = (PreferredStock) normal;
         *
         * 답을 실행 전에 먼저 예상하고, instanceof로 안전하게 고쳐보세요.
         */
    }
}

abstract class Asset {
    private final String name;
    private final double price;

    protected Asset(String name, double price) {
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

class Stock extends Asset {
    Stock(String name, double price) {
        super(name, price);
    }

    @Override
    public void printInfo() {
        System.out.println("[일반주] " + getName() + ", " + getPrice() + "원");
    }
}

class PreferredStock extends Asset {
    private final double dividendRate;

    PreferredStock(String name, double price, double dividendRate) {
        super(name, price);
        this.dividendRate = dividendRate;
    }

    @Override
    public void printInfo() {
        System.out.println("[우선주] " + getName() + ", " + getPrice() + "원");
    }

    public void printDividend() {
        double dividend = getPrice() * dividendRate / 100;
        System.out.println("예상 배당금: " + dividend + "원");
    }
}
