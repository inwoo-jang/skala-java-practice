/**
 * 자바 프로그램의 가장 기본 형태.
 *
 * 자바는 모든 코드가 반드시 클래스(class) 안에 들어가야 한다.
 * 파일 이름(App.java)과 public 클래스 이름(App)은 항상 같아야 한다.
 */
public class App {

    /**
     * main 메서드 = 프로그램의 시작점(entry point).
     * JVM이 프로그램을 실행할 때 이 메서드를 가장 먼저 찾아서 호출한다.
     *
     * 각 키워드의 의미:
     *   public  → 어디서든 접근 가능 (JVM이 외부에서 호출해야 하므로 필수)
     *   static  → 객체를 new 로 만들지 않아도 실행 가능
     *   void    → 반환값 없음
     *   String[] args → 실행 시 커맨드라인으로 넘겨받는 값들
     *                   예) java App hello 1234  →  args = {"hello", "1234"}
     *
     * throws Exception → 이 메서드 안에서 예외가 발생하면 직접 처리하지 않고
     *                    JVM에게 떠넘기겠다는 선언. (지금 코드엔 예외가 없어 없어도 됨)
     */
    public static void main(String[] args) throws Exception {
        // println = print line, 출력 후 줄바꿈까지 해준다.
        // (줄바꿈 없이 출력하려면 System.out.print 사용)
        System.out.println("Hello, World!");
    }
}
