package java_practice;

/**
 * StringBuilder 실습 — 문자열을 "조립"하는 도구.
 *
 * [왜 StringBuilder를 쓰나?]
 * String은 불변(immutable)이다. 즉 한 번 만들어진 문자열은 절대 바뀌지 않는다.
 *   String s = "a";
 *   s += "b";   // "ab"라는 "새 객체"를 만들어 s가 그것을 가리키게 바꾼 것.
 *               // 기존 "a" 객체는 그대로 남아 쓰레기가 된다.
 * 반복문에서 이렇게 이어붙이면 객체가 계속 새로 생겨 성능이 나빠진다.
 *
 * StringBuilder는 내부에 문자 배열(버퍼)을 두고 그 안의 내용을 직접 고친다.
 * → 새 객체를 만들지 않으므로 반복적인 문자열 조립에 훨씬 빠르다.
 *
 * [비슷한 클래스]
 * StringBuffer : 기능은 같지만 스레드에 안전(synchronized)한 대신 조금 느리다.
 *                혼자 쓰는 지역 변수라면 StringBuilder가 정답.
 */
public class StringBuilderExample {
    public static void main(String[] args) {
        // 빈 StringBuilder 생성. 안에 아직 아무 문자도 없는 상태.
        StringBuilder builder = new StringBuilder();

        // 1. append(): 문자열 뒤에 내용 추가
        // 가장 많이 쓰는 메서드. 항상 "맨 끝"에 붙는다.
        // int, boolean 등 어떤 자료형을 넣어도 알아서 문자열로 바꿔 붙여준다.
        builder.append("상품명: ");
        builder.append("키보드");
        builder.append("\n");

        builder.append("가격: ");
        builder.append(85000);   // 숫자 → 자동으로 "85000" 으로 변환
        builder.append("원\n");

        builder.append("판매 여부: ");
        builder.append(true);    // boolean → "true"

        printStep("1. append 후", builder);

        // 2. insert(): 원하는 위치에 내용 삽입
        // 첫 번째 인자는 "몇 번째 칸 앞에 끼워 넣을지"(0부터 시작).
        // 0을 주면 맨 앞에 삽입되고, 뒤의 내용은 그만큼 밀린다.
        builder.insert(0, "[상품 정보]\n");

        printStep("2. insert 후", builder);

        // 3. replace(): 특정 범위의 내용 교체
        // indexOf()로 바꿀 단어가 몇 번째 칸에서 시작하는지 찾는다. (없으면 -1을 반환)
        int start = builder.indexOf("키보드");
        int end = start + "키보드".length(); // 끝 위치 = 시작 + 길이

        // replace(시작, 끝, 새 문자열)
        // 범위는 "시작은 포함, 끝은 불포함"(start 이상 end 미만)이다. 자바 전반의 공통 규칙.
        // 길이가 달라도 알아서 늘어나거나 줄어든다.
        builder.replace(start, end, "기계식 키보드");

        printStep("3. replace 후", builder);

        // 4. delete(): 특정 범위 삭제
        // 끝 위치로 builder.length()를 주면 "그 지점부터 끝까지" 전부 지운다.
        start = builder.indexOf("판매 여부:");
        builder.delete(start, builder.length());

        printStep("4. delete 후", builder);

        // 5. 메서드 연결하기 (메서드 체이닝, method chaining)
        // append()는 작업을 끝낸 뒤 "자기 자신(this)"을 돌려준다.
        // 그래서 결과에 바로 또 .append()를 붙여 쭉 이어 쓸 수 있다.
        // builder.append(...); 를 여러 줄 쓰는 것과 결과는 완전히 같다.
        builder.append("수량: ")
               .append(2)
               .append("개\n")
               .append("총 가격: ")
               .append(85000 * 2)
               .append("원");

        printStep("5. 메서드 연결 후", builder);

        // 6. 최종적으로 String으로 변환
        // 조립이 끝나면 toString()으로 일반 String을 얻는다.
        // 이때 비로소 String 객체가 "한 번" 만들어진다.
        String result = builder.toString();

        System.out.println("===== 최종 String =====");
        System.out.println(result);
    }

    /**
     * 각 단계의 중간 결과를 보기 좋게 출력하는 도우미 메서드.
     *
     * main이 static이므로 여기서 호출하는 메서드도 static이어야 한다.
     * private → 이 클래스 안에서만 쓰는 내부용 메서드라는 표시.
     *
     * @param step    단계 이름 (제목으로 출력)
     * @param builder 현재까지 조립된 StringBuilder
     */
    private static void printStep(
            String step,
            StringBuilder builder
    ) {
        System.out.println("===== " + step + " =====");
        // println에 객체를 넣으면 내부적으로 toString()이 자동 호출된다.
        System.out.println(builder);
        System.out.println();
    }
}
