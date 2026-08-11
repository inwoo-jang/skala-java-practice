package java_basic.generic_example;

/**
 * 같은 Box 클래스 하나로 Integer용 상자, String용 상자를 각각 만들어 쓴다.
 * 타입마다 IntegerBox, StringBox를 따로 만들 필요가 없다.
 */
public class GenericsExample {
    public static void main(String[] args) {
        // Integer 타입의 Box
        Box<Integer> integerBox = new Box<>();
        integerBox.setItem(10);
        System.out.println("Integer Value: " + integerBox.getItem());
        // integerBox.setItem("열");  // 컴파일 에러 → 실행 전에 실수를 잡아준다.

        // String 타입의 Box
        // 오른쪽 <>는 다이아몬드 연산자. 왼쪽에 Box<String>이라 적었으니
        // 컴파일러가 타입을 추론해 준다. new Box<String>("...")와 같은 뜻이다.
        Box<String> stringBox = new Box<>("Hello, Generics!");
        System.out.println("String Value: " + stringBox.getItem());

        // 꺼낼 때 캐스팅이 필요 없다. getItem()이 이미 String으로 확정되어 있다.
        System.out.println("길이: " + stringBox.getItem().length());
    }
}
