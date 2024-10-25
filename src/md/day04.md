### 래퍼, Class 클래스
> 1. 래퍼 클래스 - 기본형의 한계1
> 2. 래퍼 클래스 - 기본형의 한계2
> 3. 래퍼 클래스 - 자바 래퍼 클래스
> 4. 래퍼 클래스 - 오토 박싱
> 5. 래퍼 클래스 - 주요 메서드와 성능
> 6. Class 클래스
> 7. System 클래스
> 8. Math, Random 클래스

---
### 1. 래퍼 클래스 - 기본형의 한계1
> `Java` 는 객체 지향 언어이다. 하지만 `int` , `double` 같은 `기본형(Primitive Type)`은 한계가 있다.</br>
> 1. **객체가 아님** : 객체는 유용한 메서드를 제공할 수 있는데, 기본형은 객체가 아니므로 메서드를 제공할 수 없다.</br>
> 2. **null 값을 가질 수 없음** : 때로는 데이터가 없음 이라는 상태를 나타내야 할 때 `null` 을 넣어줘야 하는데 기본형 타입은 `null` 을 가질 수 없다.

#### 기본형의 한계
- 아래 예시로 기본형의 한계를 알 수 있다.
    ```java
    public class MyIntegerMethodMain0 {
        public static void main(String[] args) {
            int value = 10;
    
            int i1 = compareTo(value, 5);
            int i2 = compareTo(value, 10);
            int i3 = compareTo(value, 20);
    
            System.out.println("i1 = " + i1);
            System.out.println("i2 = " + i2);
            System.out.println("i3 = " + i3);
        }
    
        public static int compareTo(int value, int target) {
            if (value < target) return -1;
            else if(value > target) return 1;
            else return 0;
        }
    }
    ```
- 위 코드와 같이 `value` 라는 **변수를 계속 새로운 값과 비교하는 메서드를 실행**한다.
- 만약 `value` 가 객체라면 `value` **객체 스스로 자기 자신의 값과 다른 값을 비교하는 메서드를 만드는것이 유용**하다.

#### 직접 만든 래퍼 클래스
- 위 코드에서 ***int 타입인 value 를 클래스로 감싸서(Wrap) 만드는 것***을 `래퍼 클래스(Wrapper class)`라 한다.
  ```java
  public class MyInteger {
      private final int value;
  
      public MyInteger(int value) {
          this.value = value;
      }
  
      public int getValue() {
          return value;
      }
  
      public int compareTo(int target) {
          if (value < target) return -1;
          else if (value > target) return 1;
          else return 0;
      }
  
      @Override
      public String toString() {
          return String.valueOf(value);
      }
  }
  ```
    - `value` 라는 int 타입 필드를 **불변형**으로 갖고 있다.
    - 이전에 했던 예제에 `compareTo()` 메서드를 **MyInteger 클래스의 멤버함수**로 만들었다.
- `main()` 메서드로 테스트를 해보면 `MyInteger` 는 객체이므로 자기 자신의 메서드를 편리하게 호출할 수 있다.
- 그리고 ***불변이기 때문에 자신의값이 바뀔 일이 없다.***
  ```java
  public class MyIntegerMethodMain1 {
      public static void main(String[] args) {
          MyInteger myInteger = new MyInteger(10);
  
          int i1 = myInteger.compareTo(5);
          int i2 = myInteger.compareTo(10);
          int i3 = myInteger.compareTo(30);
  
          System.out.println("i1 = " + i1);
          System.out.println("i2 = " + i2);
          System.out.println("i3 = " + i3);
      }
  }
  ```
---
### 2. 래퍼 클래스 - 기본형의 한계2
#### 기본형과 null
- 기본형은 항상 값이 들어가 있어야 출력이든 뭐든 할 수 있다.
- 하지만 서비스에 따라 기본형도 `값이 없음(null)` 상태를 가져야 할 때가 있다.
  ```java
  public class MyIntegerNullMain0 {
      public static void main(String[] args) {
          int[] intArr = {-1, 0, 1, 2, 3};
  
          System.out.println(findValue(intArr, -1)); // -1 이 있으니까 반환
          System.out.println(findValue(intArr, 0));
          System.out.println(findValue(intArr, 1));
          System.out.println(findValue(intArr, 100)); // 100은 없으므로 -1 반환 
      }
  
      private static int findValue(int[] intArr, int target) {
          for (int value : intArr) {
              if (value == target) return value;
          }
          return -1; // 뭐라도 반환해야 한다.
      }
  }
  ```
    - 위 코드와 같이 보통 찾는 값이 없으면 -1 을 반환한다.
    - 하지만 만약 찾는값이 -1 이라면 -1을 반환하고 찾는값이 없어도 -1을 반환한다.
    - _**클라이언트 입장에서는 -1을 찾아서 반환한건지 못찾아서 반환한건지 알수가 없다.**_
- 이러한 문제 때문에 **`null` 이라는 명확한 값이 필요**하다
  ```java
  public class MyIntegerNullMain1 {
      public static void main(String[] args) {
          MyInteger[] intArr = {new MyInteger(-1), new MyInteger(0), new MyInteger(1)};
  
          System.out.println(findValue(intArr, -1)); // -1 이 있으니까 반환
          System.out.println(findValue(intArr, 0));
          System.out.println(findValue(intArr, 1));
          System.out.println(findValue(intArr, 100)); // 100은 없으므로 -1 반환
      }
  
      private static MyInteger findValue(MyInteger[] intArr, int target) {
          for (MyInteger myInteger : intArr) {
              if (myInteger.getValue() == target) return myInteger;
          }
          return null; // 뭐라도 반환해야 한다.
      }
  }
  ```
- 위와 같이 수정하면 `null` 이 반환되면 못찾았다는 것을 알 수 있다.

---
### 3. 래퍼 클래스 - 자바 래퍼 클래스
> Java에서는 기본적으로 기본형에 대응하는 래퍼클래스를 제공한다.
- `byte` -> `Byte`
- `short` -> `Short`
- `int` -> `Integer`
- `long` -> `Long`
- `float` -> `Float`
- `double` -> `Double`
- `char` -> `Character`
- `boolean` -> `Boolean`
- 이러한 래퍼 클래스는 2가지 특징이 있다.
    1. 불변이다.
    2. `equals` 로 비교해야 한다.
- 예제로 보자
    - 래퍼 클래스는 객체기 때문에 `new` 키워드를 통해 `heap` 메모리에 띄우는게 맞다.
    - 하지만 **성능 최적화**를 위해 ***자주 쓰는 값들을 캐싱***하여 가져오는 `valueOf()` 메서드를 사용하면 된다. -> `박싱(Boxing)`
    - 캐싱해서 가져온 값들끼리 비교하면 `==` 비교에도 `true` 가 나온다. -> `String Pool` 과 유사
    - 래퍼클래스에서 기본형으로 값을 가져올려면 `intValue()` , `longValue()` 와 같은 메서드를 사용하면 된다.  -> `언박싱(Unboxing)`
  ```java
  public class WrapperClassMain {
      public static void main(String[] args) {
          Integer newInteger = new Integer(10); // 미래에 삭제 예정
          Integer integerObj = Integer.valueOf(10); // -128~127 자주 사용하는 숫자 재사용(캐싱하여 가져온다)
          Long longObj = Long.valueOf(10L);
          Double doubleObj = Double.valueOf(10.5);
  
          System.out.println("newInteger = " + newInteger);
          System.out.println("integerObj = " + integerObj);
          System.out.println("longObj = " + longObj);
          System.out.println("doubleObj = " + doubleObj);
  
          // 레퍼런스에서 기본형으로 값을 꺼낼 수 있다.
          int intValue = integerObj.intValue();
          System.out.println("intValue = " + intValue);
  
          long longValue = longObj.longValue();
          System.out.println("longValue = " + longValue);
  
          System.out.println("비교");
          System.out.println("==: " + (newInteger == integerObj));
          System.out.println("equals: " + (newInteger.equals(integerObj)));
      }
  }
  ```
---
### 4. 래퍼 클래스 - 오토 박싱
> `오토박싱(Auto Boxing)` 은 말 그대로 자동으로 Boxing 을 해준다는 의미이다.

- 박싱과 언박싱의 간단한 예제이다.
  ```java
  public class AutoboxingMain1 {
      public static void main(String[] args) {
          // Primitive -> Wrapper
          int value = 7;
          Integer boxedValue = Integer.valueOf(value);
  
          // Wrapper -> Primitive
          int unboxedValue = boxedValue.intValue();
  
          System.out.println("boxedValue = " + boxedValue);
          System.out.println("unboxedValue = " + unboxedValue);
      }
  }
  ```
- 개발을 하다보면 박싱,언박싱 사용이 정말 많고 자주 발생한다.
- `Java` 는 이러한 문제를 해결하기 위해 `Java 5(JDK 1.5)` 부터 `Auto-boxing` , `Auto-Unboxing` 을 지원한다.
- 위 코드를 수정하여 오토 박싱과 오토 언박싱을 사용해보자.
  ```java
  public class AutoboxingMain2 {
      public static void main(String[] args) {
          // Primitive -> Wrapper
          int value = 7;
          Integer boxedValue = value; // Auto-boxing
  
          // Wrapper -> Primitive
          int unboxedValue = boxedValue; // Auto-Unboxing
  
          System.out.println("boxedValue = " + boxedValue);
          System.out.println("unboxedValue = " + unboxedValue);
      }
  }
  ```
    - 일반 변수 대입하듯 대입하면 _**컴파일러가 자동으로 박싱과 언박싱을 해준다 !**_

---
### 5. 래퍼 클래스 - 주요 메서드와 성능
- `valueOf()` : 기본형값을 넣어 래퍼 타입을 반환한다.(숫자,문자열 지원)
- `parseInt()` : 문자열을 기본형으로 반환
- `compareTo()` : 자기 자신의 값과 인수로 넘어온 값을 비교(크면 1, 같으면 0 , 작으면 -1)
- `Integer.sum()` , `Integer.min()` , `Integer.max()` : Integer 에서 제공하는 static 메서드이다.
    ```java
    public class WrapperUtilsMain {
        public static void main(String[] args) {
            Integer i1 = Integer.valueOf(10); // Primitive -> Wrapper
            Integer i2 = Integer.valueOf("10"); // String -> Wrapper
    
            int i3 = Integer.parseInt("10"); // String -> Primitive
    
            // 비교
            int compareResult = i1.compareTo(20); // 10 과 20을 비교하기 때문에 왼쪽값 기준으로 결과 출력
            System.out.println("compareResult = " + compareResult);
    
            // 산술연산
            System.out.println("sum: " + Integer.sum(10, 20));
            System.out.println("min: " + Integer.min(10, 20));
            System.out.println("max: " + Integer.max(10, 20));
        }
    }
    ```

#### 래퍼 클래스와 성능
- 래퍼 클래스는 기본형보다 다양한 기능을 제공한다.
- 하지만 기본형을 제공하는 이유는 성능차이 때문이다.
- 기본형을 사용할 떄 연산 시간은 아래와 같다.
  ```java
  public class WrapperVsPrimitive {
      public static void main(String[] args) {
          int iterations = 1_000_000_000;
          long startTime, endTime;
  
          // 기본형 long 사용
          long sumPrimitive = 0;
          startTime = System.currentTimeMillis();
          for (int i = 0; i < iterations; i++) {
              sumPrimitive += i;
          }
          endTime = System.currentTimeMillis();
          System.out.println("sumPrimitive = " + sumPrimitive);
          System.out.println("기본형 long 실행 시간 : " + (endTime - startTime) + "ms");
      }
  }
  ```
- 래퍼 클래스를 사용할 때 연산 시간은 아래와 같다.
  ```java
  public class WrapperVsPrimitive {
      public static void main(String[] args) {
          int iterations = 1_000_000_000;
          long startTime, endTime;
          
          // 래퍼 클래스 Long 사용
          Long sumWrapper = 0L;
          startTime = System.currentTimeMillis();
          for (int i = 0; i < iterations; i++) {
              sumWrapper += i;
          }
          endTime = System.currentTimeMillis();
          System.out.println("sumWrapper = " + sumWrapper);
          System.out.println("래퍼 클래스 Long 실행 시간 : " + (endTime - startTime) + "ms");
      }
  }
  ```
- _**기본형은 래퍼 클래스보다 대략 5배정도의 속도차이가 있다.**_
- 이러한 속도차이가 나는 이유는 **기본형은 메모리에서 단순히 그 크기만큼 공간을 차지**한다. -> `int` 는 `4byte`
- 하지만 래퍼 클래스는 내부에 필드로 가지고 있는 **기본형의 값, 객체 메타 데이터를 포함하므로 더 많은 메모리를 사용.** -> 대략 `8~16byte`(Java 버전과 시스템마다 다름)

#### 유지보수 vs 최적화
> 현재 개발을 할때 유지보수와 최적화를 고려한다면 **유지보수하기 좋은 코드를 먼저 고민**해야 한다.</br>
> 현재 최신 컴퓨터는 성능이 매우 좋기 때문에 연산을 몇 번 줄인다고 실질적이 도움이 되지 않는다.
- 코드 변경 없이 성능 최적화를 하면 가장 좋지만, _**성능 최적화는 대부분 복잡함을 요구하고 더 많은 코드들을 추가로 만들어야 한다.**_
- 웹 어플리케이션 기준으로 _**메모리 안에서 연산 하나 보다 네트워크 호출 한번이 수십만배 더 오래 걸린다**_(연산 < IO(Input,Output))
- 권장하는 방법은 ***유지보수하기 좋은 코드로 짠 후 성능테스트를 해보고 문제가 되는 부분을 찾아서 최적화***를 하는 것이다.

---
### 6. Class 클래스
> `Java` 에서 `Class` 클래스는 **클래스의 메타데이터(정보)를 다루는데 사용**된다.

- `Class` 클래스의 주요 기능은 아래와 같다.
    - **타입 정보 얻기** : 클래스의 이름, 슈퍼클래스, 인터페이스, 접근 제한자 등등
    - **리플렉션** : 클래스에 정의된 메서드, 필드, 생성자 등을 조회하고 이를 통해 객체 인스턴스를 생성하거나 메서드를 호출할 수 있다.
    - **동적 로딩과 생성** : `Class.forName()` 메서드를 사용하여 **클래스를 동적으로 로드**하고, `newInstance()` 메서드를 통해 새로운 인스턴스를 생성할 수 있다.
    - **어노테이션 처리** : 클래스에 적용된 `annotation` 을 조회하고 처리하는 기능을 제공
- 예를 들어, `String.class` 는 `String` 클래스에 대한 메타데이터를 조회하거나 조작할 수 있다.
- 코드를 통해 알아보자
    - 패키지명에 `class`는 사용할 수 없기 때문에 관례상 `clazz` 를 많이 사용한다.
  ```java
  public class ClassMetaMain {
      public static void main(String[] args) throws Exception {
          // Class 조회
          Class clazz = String.class; // 1. 클래스에서 조회
  //        Class clazz = new String().getClass(); // 2. 인스턴스에서 조회
  //        Class clazz = Class.forName("java.lang.String"); // 3. 문자열로 조회
  
          // 모든 필드 출력
          Field[] fields = clazz.getDeclaredFields();
          for (Field field : fields) {
              // field 변수에서도 필드명,필드타입 같이 다양한 정보도 조회 가능
              System.out.println("field : " + field.getName());
          }
  
          // 모든 메서드 출력
          Method[] methods = clazz.getDeclaredMethods();
          for (Method method : methods) {
              System.out.println("method : " + method.getName());
          }
  
          // 상위 클래스 정보 출력
          System.out.println("Superclass : " + clazz.getSuperclass().getName());
  
          // 인터페이스 정보 출력
          Class[] interfaces = clazz.getInterfaces();
          for (Class i : interfaces) {
              System.out.println("Interface : " + i);
          }
      }
  }
  ```
- `Class` 클래스의 주요 기능
    - `getDeclaredFields()` : 클래스의 모든 필드를 조회
    - `getDeclaredMethods()` : 클래스의 모든 메서드를 조회
    - `getSuperclass()` : 클래스의 부모 클래스를 조회
    - `getInterfaces()` : 클래스의 인터페이스들을 조회

#### 클래스 생성하기
- `Class` 클래스의 정보를 기반으로 인스턴스를 생성하는 예제이다.
    - `Hello` 라는 간단한 클래스를 생성한다.
  ```java
  public class Hello {
      public String hello() {
          return "hello";
      }
  }
  ```
    - 그리고 `main` 메서드에서는 `Class` 의 메서드를 사용하여 인스턴스를 생성한다.
  ```java
  public class ClassCreateMain {
      public static void main(String[] args) throws Exception {
          Class helloClass = Hello.class;
  //        Class helloClass = Class.forName("lang.clazz.Hello");
  
          Hello hello = (Hello)helloClass.getDeclaredConstructor().newInstance();
          String result = hello.hello();
          System.out.println("result = " + result);
      }
  }
  ```
    - `getDeclaredConstructor()` : 생성자를 선택한다.
    - `nesInstance()` : 선택된 생성자를 기반으로 인스턴스를 생성한다.

#### 리플렉션 - `reflection`
- `Reflection`이란 `Class` 를 사용하여 **클래스의 메타 정보를 기반으로 클래스에 정의된 메서드, 필드, 생성자를 조회**하고 이를 통해 **객체 인스턴스를 생성하고 메서드를 호출하는 작업**을 말한다.
    - 추가로 `annotation` 정보를 읽어서 특별한 기능을 수행할 수 도 있다.(최신 프레임워크는 이런 기능을 적극 활용)

---
### 7. System 클래스
> `System` 클래스는 말 그대로 **시스템과 관련된 기본 기능들을 제공**한다.

- **표준 입력**, **출력**, **오류 스트림** : `System.in` , `System.out` , `System.err`
- **시간 측정** : `System.currentTimeMillis()` 로 현재시간을 밀리초, `System.nanoTime()` 로 나노초 단위로 측정 가능
- **환경 변수** : `System.getenv()` 는 현재 **OS 에서 설정한 환경 변수의 값**을 얻을 수 있다.
- **시스템 속성** : `System.getProperties()` 는 **Java 에서 사용하는 설정 값**을 얻을 수 있다.
- **시스템 종료** : `Syttem.exit(int status)` 프로그램을 종료하고, OS에 프로그램 종료 `상태코드(status)`를 전달
    - `status 0` : 정상 종료
    - `status 0` 이 아님 : 오류나 예외적인 종료
- **배열 고속 복사** : `System.arraycopy` 는 ***Java가 아닌 OS에게 연산을 넘겨 최적화된 메모리 복사 연산을 사용***한다.
    - 보통 배열 복사는 인덱스 하나하나를 복사하지만 OS 레벨에서 하는 고속복사는 2배 이상 빠르다.
  ```java
  public class SystemMain {
      public static void main(String[] args) {
          // 현재 시간(ms)
          long currentTimeMillis = System.currentTimeMillis();
          System.out.println("currentTimeMillis = " + currentTimeMillis + " ms");
  
          // 현재 시간(nano)
          long currentTimeNano = System.nanoTime();
          System.out.println("currentTimeNano = " + currentTimeNano + " nano");
  
          // 환경 변수를 읽는다.
          System.out.println("\ngetenv = " + System.getenv());
  
          // 시스템 속성을 읽는다(Java 가 사용하는 시스템 속성)
          System.out.println("\nproperties = " + System.getProperties());
          System.out.println("\nJava version: " + System.getProperty("java.version"));
  
          // 배열을 고속으로 복사(기존 복사는 인덱스 하나하나 접근했지만 고속복사는 한꺼번에 복사하기 때문에 2배 이상 빠르다)
          char[] originalArray = {'h', 'e', 'l', 'l', 'o' };
          char[] copiedArray = new char[5];
          System.arraycopy(originalArray, 0, copiedArray, 0, originalArray.length);
  
          // 배열 출력(그냥 출력하면 참조값이 나오기 때문에 Arrays.toString()으로 감싸서 출력)
          System.out.println("\ncopiedArray = " + Arrays.toString(copiedArray));
  
          // 프로그램 종료
          System.exit(0);
      }
  }
  ```

---
### 8. Math, Random 클래스
#### Math 클래스
1. 기본 연산 메서드
    - `abs(x)` : 절대값
    - `max(a, b)` : 최대값
    - `min(a, b)` : 최소값
2. 지수 및 로그 연산 메서드
    - `exp(x)` : e^x 계산
    - `log(x)` : 자연 로그
    - `log10(x)` : 로그10
    - `pow(a, b)` : a의 b제곱
3. 반올림 및 정밀도 메서드
    - `ceil(x)` : 올림
    - `floor(x)` : 내림
    - `rint(x)` : 가장 가까운 정수로 반올림
    - `round(x)` : 반올림
4. 삼각 함수 메서드
    - `sin(x)` : 사인
    - `cos(x)` : 코사인
    - `tan(x)` : 탄젠트
5. 기타 유용한 메서드
    - `sqrt(x)` : 제곱근
    - `cbrt(x)` : 세제곱근
    - `random()` : 0.0 과 1.0 사이의 무작위 값 생성

- 아래는 간단한 예제 코드이다.
    ```java
    public class MathMain {
        public static void main(String[] args) {
            // 기본 연산 메서드
            System.out.println("max(10, 20) : " + Math.max(10, 20)); // 최대값
            System.out.println("min(10, 20) : " + Math.min(10, 20)); // 최소값
            System.out.println("abs(-10) : " + Math.abs(-10)); // 절대값
    
            // 반올림 및 정밀도 메서드
            System.out.println("ceil(2.1) : " + Math.ceil(2.1)); // 올림
            System.out.println("floor(2.1) : " + Math.floor(2.1)); // 내림
            System.out.println("round(2.5): " + Math.round(2.5)); // 반올림
    
            // 기타 유용한 메서드
            System.out.println("sqrt(4) : " + Math.sqrt(4)); // 제곱근
            System.out.println("random(): " + Math.random()); // 0.0 ~ 1.0 사이 double 값 반환
        }
    }
    ```
  > 참고 : 아주 정밀한 숫자의 반올림 계산이 필요하면 `BigDecimal` 을 사용

#### Random 클래스
> `Math.random()` 을 사용해도 되지만 `Random` 클래스를 사용하면 더욱 다양한 랜덤값을 구할 수 있다.

- 간단한 예제이다.
    - `random.nextInt()` : 랜덤 `int` 값을 반환
    - `nextDouble()` : 0.0d ~ 1.0d 사이의 랜덤 `double` 값을 반환
    - `nextBoolean()` : 랜덤 `boolean` 값을 반환
    - `nextInt(int bound)` : 0 ~ bound 미만의 숫자를 반환(bound 가 3이면 0, 1, 2 를 반환)
  ```java
  public class RandomMain {
      public static void main(String[] args) {
          Random random = new Random();
    
          int randomInt = random.nextInt();
          System.out.println("randomInt = " + randomInt);
    
          double randomDouble = random.nextDouble(); // 0.0d ~ 1.0d
          System.out.println("randomDouble = " + randomDouble);
    
          boolean randomBoolean = random.nextBoolean();
          System.out.println("randomBoolean = " + randomBoolean);
    
          // 범위 조회
          int randomRange1 = random.nextInt(10); // 0 ~ 9 까지
          System.out.println("0 ~ 9 사이 랜덤값 = " + randomRange1);
    
          int randomRange2 = random.nextInt(10) + 1; // 1 ~ 10 까지
          System.out.println("1 ~ 10 사이 랜덤값 = " + randomRange2);
      }
  }
  ```
#### 씨드 - Seed
> `Random` 은 내부에서 씨드(Seed)값을 사용하여 랜덤 값을 구한다. 만약 씨드값이 같으면 항상 같은 결과가 출력된다.

---