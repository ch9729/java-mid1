### String 클래스 학습
> 1. String 클래스 - 기본
> 2. String 클래스 - 비교
> 3. String 클래스 - 불변 객체
> 4. String 클래스 - 주요 메서드
> 5. StringBuilder - 가변 String
> 6. String 최적화
> 7. 메서드 체인닝 - Method Chaining

---
### 1. String 클래스 - 기본
> Java 에서 문자를 다루는 대표적인 타입은 `char` , `String` 2가지가 있다.
- `char` 타입을 사용하게 되면 문자 1개만 담을 수 있다.
- 문자를 여러개 담을려면 `char[]` 타입을 사용하면 된다.
- 하지만 문자열은 보통 `String` 을 이용하여 많이 한다.
    ```java
    public class CharArrayMain {
        public static void main(String[] args) {
            char a = 'a'; // 반드시 문자 1개만 담을 수 있다.
            System.out.println("a = " + a);
    
            // 여러개를 담기 위해 배열이 피룡
            char[] charArr = new char[]{'h', 'e', 'l', 'l', 'o'};
            System.out.println(charArr);
    
            // 문자열은 String 을 사용하는게 편하다.
            String str = "hello";
            System.out.println("str = " + str);
        }
    }
    ```
- 하지만 `String` 은 `참조타입(Reference Type)`이다. `char` 와 같은 기본타입은 `=` 연산자를 통해 값을 대입하지만 참조타입은 `new` 키워드를 사용해야 한다.
    ```java
    public class StringBasicMain {
        public static void main(String[] args) {
            String str1 = "hello"; // String 은 레퍼런스 타입
            String str2 = new String("hello"); // new 키워드로 생성 가능
    
            System.out.println("str1 = " + str1);
            System.out.println("str2 = " + str2);
        }
    }
    ```
- 위 코드와 같이 `=` 연산자로 값을 대입하나 `new` 키워드로 값을 메모리에 할당하나 문제가 없는것이 보인다.
- 그 이유는 `Java` 에서 문자열을 자주사용하기 떄문에 **편의상 = 연사자로 값을 대입해도 자동으로 new String("값") 으로 바꿔주기 때문**이다.

#### String 클래스 구조
- String 클래스 파일을 들여다 보면 아래와 같다.
    ```java
    public final class String{
        // 문자열 보관
        private final char[] value; // 자바 9 이전
        private final byte[] value; // 자바 9 이후
        
        // 여러 메서드
        public String concat(String str) { ... }
        public int length() { ... }
        ...
    }
    ```
- 필드를 보면 `char[]` 타입의 `value` 변수가 있는데, `String` 은 다루기 불편한 ***char[] 를 내부에 숨기고 개발자가 편리하게 문자열을 사용할 수 있도록 제공***해준다.
    - Java 9 이후로는 char[] 대신 byte[] 를 사용한다.
    - 왜냐하면 `Java` 에서 `char` 타입은 `2byte`를 차지한다. 그런데 **영어, 숫자는 보통 1byte로 표현**이 가능하다.
    - 그래서 영어나 숫자는 `1byte` 나머지는 `2byte`로 사용한다. -> 즉, _**메모리를 효율적으로 사용할 수 있다.**_
- 메서드 종류
    - `length()` : 문자열의 길이를 반환한다.
    - `charAt(int index)` : 특정 인덱스의 문자를 반환한다.
    - `substring(int beginIndex, int endIndex)` : 문자열의 부분 문자열을 반환한다.
    - `indexOf(String str)` : 특정 문자열이 시작되는 인덱스를 반환한다.
    - `toLowerCase()` , `toUpperCase()` : 문자열을 소문자 , 대문자로 변환한다.
    - `trim()` : 문자열 양 끝의 공백을 제거한다.
    - `concat(String str)` : 문자열을 더한다.
#### String 문자열 더하기
- 기본적으로 참조타입인 `String` 을 `+` 연산하기 위해서는 `+` 연산자가 아닌 특정 메서드를 통해서 사용해야 한다.
- 하지만 문자열은 자주 사용하기 때문에 `Java` 에서 편의상 `+` 연산을 제공한다.
  ```java
  public class StringConcatMain {
      public static void main(String[] args) {
          String a = "hello";
          String b = " java";
  
          String result1 = a.concat(b);
          String result2 = a + b;
  
          System.out.println("result1 = " + result1);
          System.out.println("result2 = " + result2);
      }
  }
  ```
  
---
### 2. String 클래스 - 비교
> `String` 클래스를 비교할 때는 `==` 비교가 아닌 `equals()` 비교를 해야한다.
>> `동일성(Identity)` : `==` 연산자를 통해 두 객체의 참조가 동일한 객체를 가리키고 있는지 확인</br>
>> `동등성(Equality)` : `equals()` 메서드를 사용하여 두 객체가 논리적으로 같은지 확인

#### new String() 객체 동일성,동등성 비교
- `String` 에서 제공하는 `equals()` 는 `Object` 클래스의 `equals()` 를 오버라이딩 해놨기 때문에 문제없이 잘 작동한다!!
  ```java
  public class StringEqualsMain1 {
      public static void main(String[] args) {
          String str1 = new String("hello");
          String str2 = new String("hello");
          System.out.println("new String() == 비교: " + (str1 == str2));
          System.out.println("new String() equals 비교: " + str1.equals(str2));
      }
  }
  ```
- 메모리 구조를 보게 되면 `str1` 과 `str2` 는 `heap` 메모리공간에서 **다른 메모리 주소를 가리키고 있다.**

- 즉, "hello" 라는 값을 갖고 있기 떄문에 동등성(`equals()`)는 `true` 지만 동일성(`==`)은 `false` 이다.
#### = 연산자로 만든 String 객체 동일성,동등성 비교
- 하지만 `String` 을 `=` 연산자로 값을 대입해서 동등성과 동일성을 비교하면 같은 값이 나오게 된다.
  ```java
  public class StringEqualsMain1 {
      public static void main(String[] args) {
          String str3 = "hello";
          String str4 = "hello";
          System.out.println("리터럴 == 비교: " + (str3 == str4));
          System.out.println("리터럴 equals 비교: " + str3.equals(str4));
      }
  }
  ```
- `=` 연산자로 생성한 `String` 객체들은 `문자열 풀(String Pool)` 이라는 공간에 들어가 있다.
- 위 코드의 `String str3 = "hello";` 와 같이 ***문자열 리터럴를 사용하는 경우 자바는 메모리 효율성과 성능 최적화***를 위해 `String Pool` 을 사용한다.
- `String Pool` 은 `Java` 가 **실행되는 시점에 클래스에 문자열 리터럴이 있으면 String Pool 에 String 인스턴스를 미리 만들어둔다.** 이때 같은 문자열이 또 있다면 만들지 않는다.
  > **<참고**></br>
  > 프로그래밍에서 `풀(Pool)`은 **공용 자원을 모아둔 곳**을 뜻한다.</br>
  > 문자열 풀에 필요한 `String` _**인트턴스를 미리 만들어두고 여러곳에서 재사용할 수 있다면 성능과 메모리를 더 최적화**_ 할 수 있다.
- _**String 클래스 객체의 비교를 할 때는 무조건 equals() 메서드를 통해 비교해야 한다 !!!**_

---
### 3. String 클래스 - 불변 객체
> String 클래스는 대표적인 불변(Immutable)객체이다.

- `hello` 값을 가지는 `String` 변수에 `concat()` 메서드를 이용하여 ` java` 문자열을 더해보자
  ```java
  public class StringImmutable1 {
      public static void main(String[] args) {
          String str = "hello";
          str.concat(" java");
          System.out.println("str = " + str);
      }
  }
  ```
    - 값이 제대로 안나온다!! 왜냐하면 불변객체의 특성 때문이다.
- `concat()` 메서드는 ***불변객체의 특성 떄문에 기존의 값을 변경하지 않고 변경값을 가지는 새로운 String 객체를 반환***한다 !!
  ```java
  public class StringImmutable2 {
      public static void main(String[] args) {
          String str = "hello";
          String str2 = str.concat(" java"); 
          System.out.println("str2 = " + str2);
      }
  }
  ```
- 실행과정은 아래와 같다
    - `concat()` 메서드를 호출하면 ***기존의 값과 추가할값을 더한 새로운 결과값을 new String() 에 담아 반환***하게 된다.

#### String 클래스가 불변으로 설계된 이유
- `String` 이 불변으로 설계된 이유는 `String Pool` 때문이다 !!
- String Pool 에 있는 String 인스턴스 값이 바뀌게 되면 문자열을 참조하는 다른 변수의 값도 함께 변경되기 때문이다 !!

---
### 4. String 클래스
> `String` 클래스는 문자열을 편리하게 다루기 위한 다양한 메서드를 제공한다.</br>
> 너무 많기 때문에 필요할 때 _검색하거나 API 문서를 통해서 원하는 기능을 찾는 것이 좋다._

#### 문자열 정보 조회
|함수명|설명|
|---|---|
|`length()`|문자열의 길이를 반환|
|`isEmpty()`|문자열이 비어 있는지 확인|
|`isBlank()`|문자열이 비어 있는지 확인(길이가 0 이거나 공백만 있는경우, Java 11)|
|`charAt(int index)`|지정된 인덱스에 있는 문자를 반환한다.|

#### 문자열 비교
|함수명|설명|
|---|---|
|`equals(Object object)`|두 문자열이 동일한지 비교|
|`equalsIgnoreCase(String anotherString)`|두 문자열을 대소문자 구분 없이 비교한다|
|`compareTo(String anotherString)`|두 문자열을 사전 순으로 비교한다|
|`compareToIgnoreCase(String str)`|두 문자열을 대소문자 구분 없이 사전적으로 비교한다|
|`startsWith(String prefix)`|문자열이 특정 접두사로 시작하는지 확인한다|
|`endsWith(String suffix)`|문자열이 특정 접미사로 끝나는지 확인한다|

#### 문자열 검색
|함수명|설명|
|---|---|
|`contains(CharSequence s)`|문자열이 특정 문자열을 포함하고 있는지 확인한다|
|`indexOf(String ch)` / `indexOf(String ch, int fromIndex)`|문자열이 처음 등장하는 위치를 반환한다|
|`lastIndexOf(String ch)`|문자열이 마지막으로 등장하는 위치를 반환한다|

#### 문자열 조작 및 변환
|함수명|설명|
|---|---|
|`substring(int beginIndex)` / `subString(int beginIndex, int endIndex)`|문자열의 부분 문자열을 반환한다|
|`concat(String str)`|문자열의 끝에 다른 문자열을 붙인다.|
|`replace(CharSequence target, CharSequence replacement)`|특정 문자열을 새 문자열로 대체한다.|
|`replaceAll(String regex, String replacement)`|문자열에서 정규 표현식과 일치하는 부분을 새 문자열로 대체한다|
|`replaceFirst(String regex, String replacement)`|문자열에서 정규 표현식과 일치하는 첫 번째 부분을 새 문자열로 대체한다|
|`toLowerCase()` / `toUpperCase()`|문자열을 소문자/대문자로 변환한다|
|`strip()`|공백과 유니코드 공백을 포함해서 제거한다(Java 11)|

#### 문자열 분할 및 조합
|함수명|설명|
|---|---|
|`split(String regex)`|문자열을 정규 표현식을 기준으로 분할한다|
|`join(CharSequence delimiter, CharSequence ... elements)`|주어진 구분자로 여러 문자열을 결합한다.|

#### 기타 유틸리티
|함수명|설명|
|---|---|
|`valueOf(Object obj)`|다양한 타입을 문자열로 반환한다|
|`toCharArray()`|문자열을 문자 배열로 변환한다|
|`format(String format, Object... args)`|형식 문자열과 인자를 사용하여 새로운 문자열을 생성한다|
|`matches(String regex)`|문자열이 주어진 정규 표현식과 일치하는지 확인한다|

> 참고 : `CharSequence` 는 `String`, `StringBuilder` 의 상위타입이다. 문자열을 처리하는 다양한 객체를 받을 수 있다.

---
### 5. StringBuilder - 가변 String
> String 클래스가 불변객체기 때문에 단점이 있다. 아래는 예시이다.
- `A` 와 `B` , `C`, `D` 를 더하는 예제이다.
    ```java
    String str = "A" + "B" + "C" + "D";
    String str = new String("A") + new String("B") + new String("C") + new String("D");
    String str = new String("AB") + new String("C") + new String("D");
    String str = new String("ABC")+ new String("D");
    String str = new String("ABCD");
    ```
- 수많은 객체가 사용되지 않고 버려지고 결국 `new String("ABCD")` 만 사용된다.
- ***불변인 String 클래스의 단점은 문자를 더하거나 변경할 떄 마다 계속해서 새로운 객체를 생성***해야 한다는 점이다.
- 즉, **컴퓨터의 CPU, 메모리를 더 많이 소모**하게 된다.

#### StringBuilder
- 불변 `String` 의 단점을 해결하기 위해서는 가변 `String` 이 존재하면 된다.
- `StringBuilder` 는 가변 `String` 을 제공하기 때문에 `String` 의 단점을 해결할 수 있다.
- `StringBuilder` 내부에는 `final` 이 아닌 변경가능한 `byte[]` 를 가지고 있다.
  ```java
  public final class StringBuilder {
      char[] value; // Java 9 이전
      byte[] value; // Java 9 이후
  
      // 여러 메서드
      public StringBuilder append(String str) {...}
  
      public int length() {...}
  }
  ```
- StringBuilder 는 다양한 메서드를 지원한다.
    - `append()` : 여러 문자열 추가
    - `insert()` : 특정 위치에 문자열 삽임
    - `delete()` : 특정 범위의 문자열 삭제
    - `reverse()` : 문자열 뒤집기
    - `toString()` : `StringBuilder` 를 `String` 으로 생성하여 반환
  ```java
  public class StringBuilderMain1_1 {
      public static void main(String[] args) {
          StringBuilder sb = new StringBuilder();
          sb.append("A");
          sb.append("B");
          sb.append("C");
          sb.append("D");
  
          System.out.println("sb = " + sb);
  
          sb.insert(4, "Java");
          System.out.println("insert = " + sb);
  
          sb.delete(4, 8);
          System.out.println("delete = " + sb);
  
          sb.reverse();
          System.out.println("reverse() = " + sb);
  
          String str = sb.toString();
          System.out.println("str = " + str);
      }
  }
  ```

> 즉, `StringBuilder` 로 문자열을 변경하고 ***문자열 변경이 끝나면 안전한(불변) String 으로 변환***하는 것이 좋다

---
### 6. String 최적화
> Java 컴파일러는 아래와 같이 문자열 리터럴을 더하는 부분을 자동으로 합쳐준다.

#### 문자열 리터럴 최적화
- 자바는 컨타임에 별도의 문자열 결합 연산을 수행하지 않고 **컴파일러가 직접 결합 연산을 해주기 때문에 성능이 향상**된다.
    ```java
    String helloWorld = "Hello, " + "World!"; // 컴파일 전
    String helloWorld = "Hello, World!"; // 컴파일 후
    ```
#### String 변수 최적화
- 문자열 리터럴의 경우 단순히 합치면 되지만 변수의 경우에는 그 값을 알지 못하기 때문에 합칠 수 없다.
- 이런 경우 컴파일러는 `StringBuilder` 를 사용하여 최적화를 한다(Java 버전별로 다르다)
    ```java
    String result = str1 + str2; // 단순 결합 불가
    String result = new StringBuilder().append(str1).append(str2).toString(); // 최적화
    ```
  > `Java 9` 부터는 `StringConcatFactory` 를 사용해서 최적화를 수행한다.

#### String 최적화가 어려운 경우
- 문자열을 루프안에서 문자열을 더하는 경우에는 최적화가 이루어지지 않는다.
- 아래 코드는 문자열 결합을 10만번 하는동안 걸린시간을 알아보는 코드다.
  ```java
  public class LoopStringMain {
      public static void main(String[] args) {
          long startTime = System.currentTimeMillis(); // 현재 ms 시간을 얻는다.
          String result = "";
          for (int i = 0; i < 100000; i++) {
              result += "Hello Java ";
          }
  
          long endTime = System.currentTimeMillis();
          System.out.println("time" + (endTime - startTime) + "ms");
      }
  }
  ```
- 고작 10만번 연산하는데 2초나 걸리는건 문자열 결합이 루프안에서 최적화가 안된다는것을 뜻한다.
- 반복문에서 n번동안 n개의 객체를 생성한다.
  ```java
  String result = "";
  for(int i=0;i<100000;i++){
      result = new StringBuilder().append(result).append("Hello Java ").toString();
  }
  ```
- 저렇게 `StringBuilder` 객체를 10만번을 만들어서 오래 걸린것이다.
- 루프문안에서 문자열 결합을 한다면 `String` 이 아닌 `StringBuilder` 를 써야한다.
  ```java
  public class LoopStringBuilderMain {
      public static void main(String[] args) {
          long startTime = System.currentTimeMillis(); // 현재 ms 시간을 얻는다.
          StringBuilder sb = new StringBuilder();
          for (int i = 0; i < 100000; i++) {
              sb.append("Hello Java ");
          }
  
          long endTime = System.currentTimeMillis();
          String result = sb.toString();
          System.out.println(result);
          System.out.println("time" + (endTime - startTime) + "ms");
      }
  }
  ```

#### StringBuilder 사용하는게 좋은 경우
1. 반복문에서 반복해서 문자를 연결할 때
2. 조건문을 통해 동적으로 문자열을 조합할 때
3. 복잡한 문자열의 특정 부분을 변경해야 할 때
4. 매우 긴 대용량 문자열을 다룰 때

> 참고 : `StringBuilder` VS `StringBuffer` </br>
> `StringBuffer` : `StringBuilder` 와 똑같은 기능을 수행하지만 내부에 동기화가 되어 있어서, **멀티 쓰레드 상황에 안전하지만 동기화 오버헤드로 인해 성능이 느리다.**</br>
> `StringBuilder` : **멀티 쓰레드 상황에 안전하지 않지만 동기화 오버헤드가 없으므로 속도가 빠르다.**

---
### 7. 메서드 체인닝 - Method Chaining
- 단순히 값을 누적해서 더하는 기능을 제공하는 클래스이다.
    - `add()` 메서드는 호출할 때 마다 value 에 값을 누적하고 자기 자신(`this`)의 참조값을 반환한다.
  ```java
  public class ValueAdder {
  private int value;
  
      public ValueAdder add(int addValue) {
          value += addValue;
          return this;
      }
  
      public int getValue() {
          return value;
      }
  }
  ```
- main() 메서드에서 add() 가 ValueAdder 를 반환하는 것을 이용하여 누적값과 참조값을 보자
  ```java
  public class MethodChainingMain2 {
      public static void main(String[] args) {
          ValueAdder adder = new ValueAdder();
          ValueAdder adder1 = adder.add(1);
          ValueAdder adder2 = adder1.add(2);
          ValueAdder adder3 = adder2.add(3);
  
          System.out.println("adder3.getValue() = " + adder3.getValue());
  
          System.out.println("adder 참조값 : " +adder);
          System.out.println("adder1 참조값 : " +adder1);
          System.out.println("adder2 참조값 : " +adder2);
          System.out.println("adder3 참조값 : " +adder3);
      }
  }
  ```
- 모든 객체가 같은 참조값을 가리킨다. 그림으로 보면 아래와 같다.

- 하지만 이런 방식은 코드가독성도 좋지않고 불편한 코드이다.
- 그러나 위 방식을 아래와 같이 바꾸면 왜 저렇게 자기자신을 반환하고 사용했는지 알게 된다.
  ```java
  public class MethodChainingMain3 {
    public static void main(String[] args) {
      ValueAdder adder = new ValueAdder();
      int result = adder.add(1).add(2).add(3).getValue();
      System.out.println("result = " + result);
    }
  }
  ```
- `adder` 라는 참조변수의 참조값에 `.` 을 찍어 체인처럼 계속 연결되어 값을 가져온다 -> 이를 `메서드 체이닝` 이라 한다!!
- _**메서드 체이닝 기법은 코드를 간결하고 읽기 쉽게 만들어준다.**_
- `StringBuilder` 클래스는 **메서드 체이닝 기법을 제공**한다.

---