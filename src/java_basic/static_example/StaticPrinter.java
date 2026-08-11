package java_basic.static_example;

/**
 * 객체를 만들지 않고 사용할 수 있는 static 메서드 예제입니다.
 */
public class StaticPrinter {
    /**
     * static 메서드이므로 {@code new StaticPrinter()} 없이
     * {@code StaticPrinter.print("메시지")} 형태로 호출할 수 있습니다.
     */
    public static void print(String message) {
        System.out.println(message);
    }
}
