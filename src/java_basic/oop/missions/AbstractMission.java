package java_basic.oop.missions;

import java.lang.reflect.Modifier;

/** 미션 2: 공통 상태는 부모에, 개별 행동은 자식에 맡기기 */
public class AbstractMission {
    public static void main(String[] args) throws Exception {
        // TODO 1: MissionPayment를 abstract class로 바꾸세요. 성공 후 TODO를 COMPLETED로 변경하세요.
        check(Modifier.isAbstract(MissionPayment.class.getModifiers()),
                "MissionPayment 앞에 abstract를 붙이세요.");

        // TODO 2: pay()를 본문 없는 abstract 메서드로 바꾸세요.
        check(Modifier.isAbstract(MissionPayment.class.getDeclaredMethod("pay").getModifiers()),
                "pay()를 public abstract String pay();로 바꾸세요.");

        // TODO 3: CardPayment가 MissionPayment를 상속하게 하고, 생성자에서 super(amount)를 호출하세요.
        boolean inherited = MissionPayment.class.isAssignableFrom(CardPayment.class);
        check(inherited, "CardPayment에 extends MissionPayment를 추가하세요.");

        // TODO 4: pay()를 오버라이딩해 \"카드 결제: 10000원\"을 반환하세요.
        if (inherited) {
            MissionPayment payment = (MissionPayment) (Object) new CardPayment(10_000);
            check("카드 결제: 10000원".equals(payment.pay()), "CardPayment.pay()를 오버라이딩하세요.");
        }
    }

    private static void check(boolean success, String hint) {
        System.out.println(success ? "[SUCCESS]" : "[KEEP GOING] " + hint);
    }
}

// abstract class로 바꾸세요.
class MissionPayment {
    protected final int amount;

    protected MissionPayment(int amount) {
        this.amount = amount;
    }

    // 추상 메서드로 바꾸세요.
    public String pay() {
        return "";
    }
}

// MissionPayment를 상속하고, 중복된 amount 필드는 제거하세요.
class CardPayment {
    private final int amount;

    CardPayment(int amount) {
        // 상속한 뒤에는 아래 대입 대신 super(amount)를 사용하세요.
        this.amount = amount;
    }

    // pay()를 오버라이딩하세요.
}
