package java_basic.switch_example;

/**
 * enum 값에 따라 서로 다른 로그인 메서드를 호출하는 switch 예제입니다.
 */
public class EnumSwitch {
    public static void main(String[] args) {
        // FACEBOOK이라는 enum 값을 전달하여 페이스북 로그인 흐름을 선택합니다.
        executeLogin(LoginChannel.FACEBOOK);
    }

    /**
     * 전달받은 로그인 채널에 맞는 메서드 하나를 실행합니다.
     *
     * @param channel 실행할 로그인 채널
     */
    public static void executeLogin(LoginChannel channel) {
        // 화살표(->) switch 문법은 선택된 case의 코드만 실행하므로 break가 필요 없습니다.
        switch (channel) {
            case FACEBOOK -> facebookLogin();
            case KAKAO -> kakaoLogin();
            case APPLE -> appleLogin();
            case NAVER -> naverLogin();
            case DEFAULT -> defaultLogin();
        }
    }

    // 아래 메서드는 EnumSwitch 내부에서만 사용하므로 private으로 선언합니다.
    // 특정 객체의 상태를 사용하지 않으므로 객체 생성 없이 호출할 수 있는 static 메서드입니다.
    private static void facebookLogin() {
        System.out.println("FACEBOOK Login");
    }

    private static void kakaoLogin() {
        System.out.println("KAKAO Login");
    }

    private static void appleLogin() {
        System.out.println("Apple Login");
    }

    private static void naverLogin() {
        System.out.println("Naver Login");
    }

    private static void defaultLogin() {
        System.out.println("Default Login");
    }
}
