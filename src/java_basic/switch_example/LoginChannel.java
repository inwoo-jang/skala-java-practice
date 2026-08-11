package java_basic.switch_example;

/**
 * 프로그램에서 허용하는 로그인 채널을 한정된 상수로 정의한 enum입니다.
 * 임의의 문자열 대신 enum을 사용하면 오타와 잘못된 값을 줄일 수 있습니다.
 */
public enum LoginChannel {
    FACEBOOK,
    KAKAO,
    APPLE,
    NAVER,
    DEFAULT
}
