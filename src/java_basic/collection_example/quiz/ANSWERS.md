# OrderQuiz 정답지

> 출제자용. 풀기 전에 열지 말 것.

실제 실행 결과 기준으로 검증했습니다.

---

## PART 1 — 죽은 코드 (워밍업) · 위반: R3

기대 결과(8줄 출력)는 정확히 나옵니다. 어긴 건 공통 요구사항 R3 하나뿐입니다.
"결과가 맞으면 된 거 아니냐"는 반응이 나오면, 이 문제의 목적이 바로 그겁니다.


```java
Iterator<String> orderIterator = orderList.iterator();   // ← 만들고 안 씀

for (String order : orderList) { ... }                   // 실제로는 for-each로 순회
```

**결과**: 정상 출력됩니다. 아무 문제 없이 8건이 다 찍힙니다.

**문제점**: `orderIterator`를 만들어놓고 한 번도 쓰지 않습니다. 실행에는 지장이 없지만, 읽는 사람에게 "여기서 이터레이터를 쓰나 보다"라는 잘못된 신호를 줍니다.

**토론거리**: 사실 for-each는 컴파일되면 내부적으로 이터레이터를 만들어 씁니다. 즉 이 코드에는 이터레이터가 두 개 생기고 하나만 쓰이는 셈입니다.

---

## PART 2 — HashMap 순회 순서를 믿은 코드 · 위반: PART 2 요구사항 + R2

```java
String firstProduct = totalMap.keySet().iterator().next();
System.out.println("apple이 맞는가? " + firstProduct.equals("apple"));
```

**실행 결과**:
```
가장 먼저 등록된 상품: banana
apple이 맞는가? false
```

**문제점**: `HashMap`은 **입력 순서를 보장하지 않습니다.** 키의 해시값으로 자리를 정하기 때문에 `apple`을 먼저 넣어도 `banana`가 먼저 나올 수 있습니다.

**이게 왜 무서운가**: 예외가 안 터집니다. 그냥 조용히 틀린 값이 나옵니다. 게다가 우연히 통과하는 경우도 있어서 "내 컴퓨터에선 됐는데?"의 전형적인 원인이 됩니다.

**해결**:
- 입력 순서를 유지하려면 → `LinkedHashMap`
- 키를 정렬해서 쓰려면 → `TreeMap`
- 애초에 "첫 번째"라는 개념이 필요한지부터 다시 생각하기

선언 한 줄만 바꾸면 됩니다. `Map` 인터페이스로 받았으니 나머지 코드는 손댈 필요가 없죠 — 인터페이스로 선언하는 습관의 이점입니다.

---

## PART 3 — for-each 안에서 remove · 위반: R1

```java
for (String order : orderList) {
    if (quantity < 3) {
        orderList.remove(order);      // ← 여기
    }
}
```

**실행 결과**: `java.util.ConcurrentModificationException`

**문제점**: for-each는 내부적으로 이터레이터를 씁니다. 그 이터레이터는 리스트가 몇 번 수정됐는지를 기록해두고(`modCount`), `next()`를 부를 때마다 "내가 알던 횟수와 지금이 같은가"를 확인합니다. `list.remove()`는 이터레이터 몰래 리스트를 고치므로 다음 `next()`에서 걸립니다.

**해결**: 이터레이터를 직접 만들고 `iterator.remove()`를 쓰면 이터레이터가 자기 상태도 같이 갱신합니다.

```java
for (Iterator<String> it = orderList.iterator(); it.hasNext();) {
    String order = it.next();
    if (Integer.parseInt(order.split(",")[1]) < 3) {
        it.remove();
    }
}
```

자바 8 이상이면 `orderList.removeIf(o -> ...)` 한 줄로도 됩니다.

**추가 함정 (여유 있으면)**: 이 예외는 **항상** 터지는 게 아닙니다. 끝에서 두 번째 요소를 지우면 반복이 조용히 끝나버려 예외 없이 통과합니다. "가끔 되는" 버그가 제일 위험하다는 이야기로 이어가면 좋습니다.

---

## PART 4 — Arrays.asList의 결과에 remove · 위반: R1

```java
List<String> orderList = Arrays.asList(ORDERS);   // ← new ArrayList<>(...) 로 감싸지 않음
orderList.remove(0);
```

**실행 결과**: `취소 전: 8건`까지 출력된 뒤 `java.lang.UnsupportedOperationException`

**문제점**: `Arrays.asList()`가 돌려주는 건 `java.util.ArrayList`가 **아닙니다.** `Arrays` 안에 있는 별개의 클래스 `java.util.Arrays$ArrayList`입니다. 이름만 같습니다. 배열을 들여다보는 창(뷰)이라서 크기가 배열에 묶여 있고, 그래서 `add`/`remove`를 지원하지 않습니다.

`getClass().getName()`을 찍어보게 하면 확실히 와닿습니다.

**보너스**: `set()`은 됩니다. 그런데 값을 바꾸면 **원본 배열까지 바뀝니다.** 이걸 보여주면 "뷰"라는 개념이 한 번에 이해됩니다.

```java
List<String> view = Arrays.asList(ORDERS);
view.set(0, "durian,9");
System.out.println(ORDERS[0]);   // durian,9  ← 배열이 바뀜
```

**해결**: `new ArrayList<>(Arrays.asList(ORDERS))` — 요소를 복사해 진짜 내 리스트를 만듭니다.

---

## 정리

| PART | 증상 | 위반한 요구사항 | 핵심 개념 |
| --- | --- | --- | --- |
| 1 | 조용함 (기대 결과 일치) | R3 | 죽은 코드 |
| 2 | 조용히 틀림 (`banana` 출력) | PART 2 요구사항 + R2 | HashMap은 순서를 보장하지 않음 |
| 3 | ConcurrentModificationException | R1 | 순회 중 컬렉션 수정 |
| 4 | UnsupportedOperationException | R1 | 고정 크기 뷰 vs 진짜 리스트 |

R1 예외 없이 실행 / R2 항상 같은 결과 / R3 죽은 코드 없음

**출제 포인트**: 위험한 순서는 2 → 3 → 4 → 1입니다. 예외가 터지는 3, 4는 최소한 즉시 발견되지만, 2번은 아무도 모르게 틀린 답을 내놓습니다. "터지는 버그가 차라리 낫다"는 결론으로 마무리하면 좋습니다.
