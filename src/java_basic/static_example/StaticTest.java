package java_basic.static_example;

/**
 * static 필드와 인스턴스 필드의 차이를 확인하는 예제입니다.
 */
public class StaticTest {
    // static 중첩 클래스는 StaticTest 객체 없이 생성할 수 있습니다.
    static class Example {
        // 상수는 모든 객체가 같은 값 하나를 공유하며 변경할 수 없습니다.
        public static final int INITIAL_COUNT = 10;

        // static 필드는 Example 객체 전체가 하나의 값을 공유합니다.
        private static int count = 0;

        // 인스턴스 필드는 생성된 객체마다 별도의 값을 가집니다.
        private int instanceId;

        Example() {
            // 객체가 생성될 때마다 공유 변수 count가 1씩 증가합니다.
            count++;
            instanceId = count + INITIAL_COUNT;
        }

        // static 메서드는 객체 없이 Example.getLastInstanceId()로 호출합니다.
        public static int getLastInstanceId() {
            return count + INITIAL_COUNT;
        }

        public static int getCount() {
            return count;
        }

        // 일반 메서드는 e1.getInstanceId()처럼 특정 객체로 호출합니다.
        public int getInstanceId() {
            return instanceId;
        }
    }

    public static void main(String[] args) {
        // Example 객체를 3개 만들면 공유 변수 count는 총 3번 증가합니다.
        Example e1 = new Example();
        Example e2 = new Example();
        Example e3 = new Example();

        System.out.println("총 생성된 객체 수(count): " + Example.getCount());
        System.out.println("마지막 생성된 instanceId: " + Example.getLastInstanceId());

        // 각 객체의 instanceId는 11, 12, 13으로 서로 다릅니다.
        System.out.println("e1의 instanceId: " + e1.getInstanceId());
        System.out.println("e2의 instanceId: " + e2.getInstanceId());
        System.out.println("e3의 instanceId: " + e3.getInstanceId());
    }
}
