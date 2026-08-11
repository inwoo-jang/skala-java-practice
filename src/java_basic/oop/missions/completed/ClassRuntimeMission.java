package java_basic.oop.missions.completed;

/**
 * 미션 3: java.lang.Class와 java.lang.Runtime 사용하기
 *
 * Class:
 * 실행 중인 Java 클래스의 이름, 패키지, 메서드 같은 정보를 표현합니다.
 * 모든 객체는 getClass()로 자신의 Class 정보를 가져올 수 있습니다.
 *
 * Runtime:
 * 현재 Java 프로그램이 실행되는 환경 정보를 제공합니다.
 * CPU 개수와 JVM 메모리 정보 등을 확인할 수 있습니다.
 *
 * java.lang 패키지의 클래스이므로 별도의 import는 필요하지 않습니다.
 *
 * 목표:
 * 1. StockTool의 전체 클래스 이름과 간단한 이름을 출력하세요.
 * 2. 클래스가 속한 패키지 이름을 출력하세요.
 * 3. 현재 JVM에서 사용할 수 있는 CPU 개수를 출력하세요.
 * 4. JVM의 최대 메모리를 MB 단위로 출력하세요.
 *
 * 예상 출력 형태(숫자는 컴퓨터마다 다름):
 * 전체 이름: java_basic.oop.missions.completed.ClassRuntimeMission$StockTool
 * 간단한 이름: StockTool
 * 패키지: java_basic.oop.missions.completed
 * 사용 가능한 CPU: 10개
 * JVM 최대 메모리: 8192MB
 *
 * 힌트:
 * - 객체에서 Class 얻기: tool.getClass()
 * - 전체 이름: getName()
 * - 간단한 이름: getSimpleName()
 * - 패키지 이름: getPackageName()
 * - Runtime 객체: Runtime.getRuntime()
 * - CPU 개수: availableProcessors()
 * - 최대 메모리: maxMemory()
 * - byte를 MB로 바꾸려면 1024 * 1024로 나눕니다.
 */
public class ClassRuntimeMission {
    public static void main(String[] args) {
        StockTool tool = new StockTool();

        // 실행 중인 tool 객체가 어떤 클래스에서 생성됐는지를 나타냅니다.
        Class<?> toolClass = tool.getClass();

        // DONE 1: toolClass로 전체 이름, 간단한 이름, 패키지 이름을 출력하세요.
        System.out.println("전체 이름: " + toolClass.getName());
        System.out.println("간단한 이름: " + toolClass.getSimpleName());
        System.out.println("패키지: " + toolClass.getPackageName());

        // 현재 실행 중인 JVM 환경을 나타내는 Runtime 객체는 하나를 공유합니다.
        Runtime runtime = Runtime.getRuntime();

        // DONE 2: runtime으로 사용 가능한 CPU 개수를 출력하세요.
        System.out.println("사용 가능한 CPU: " + runtime.availableProcessors() + "개");

        // DONE 3: maxMemory() 결과를 MB 단위로 변환해 출력하세요.
        System.out.println("JVM 최대 메모리: " + (runtime.maxMemory() / (1024 * 1024)) + "MB");
    }

    static class StockTool {
        // Class 정보를 확인하기 위한 연습용 중첩 클래스입니다.
    }
}
