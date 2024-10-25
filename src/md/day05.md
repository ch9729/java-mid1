### 열거형 - ENUM
> 1. 문자열과 타입 안정성1
> 2. 문자열과 타입 안정성2
> 3. 타입 안전 열거형 패턴(Type-Safe Enum Pattern)
> 4. 열거형 - Enum Type
> 5. 열거형 - 주요 메서드
> 6. 열거형 - 리팩토링 1
> 7. 열거형 - 리팩토링 2
> 8. 열거형 - 리팩토링

---
### 1. 문자열과 타입 안정성1
> Java 가 제공하는 열거형(Enum Type)을 이해하려면 왜 열거형이 생겨났는지 알아야한다. 예제를 통해 알아보자.
#### 예제 요구사항
- 고객은 `BASIC` , `GOLE` , `DIAMOND` 등급으로 나뉘고 각 등급마다 `10%`, `20%`, `30%` 할인이 들어간다.
- 등급마다 할인해주는 코드는 아래와 같다.
    ```java
    public class DiscountService {
    
        public int discount(String grade, int price){
            int discountPercent = 0;
    
            if(grade.equals("BASIC")) discountPercent = 10;
            else if(grade.equals("GOLD")) discountPercent = 20;
            else if(grade.equals("DIAMOND")) discountPercent = 30;
            else System.out.println(grade + "는 할인을 안합니다 !");
    
            return price * discountPercent / 100;
        }
    }
    ```
- 직접 실행하는 main() 은 아래와 같다.
    ```java
    public class StringGradeEx0_1 {
        public static void main(String[] args) {
            int price = 10000;
    
            DiscountService discountService = new DiscountService();
            int basic = discountService.discount("BASIC", price);
            int gold = discountService.discount("GOLD", price);
            int diamond = discountService.discount("DIAMOND", price);
    
            System.out.println("BASIC 등급의 할인 금액: " + basic);
            System.out.println("GOLD 등급의 할인 금액: " + gold);
            System.out.println("DIAMOND 등급의 할인 금액: " + diamond);
        }
    }
    ```
- 위와 같은 비즈니에서 등급을 문자열로 사용하면 다음과 같은 문제가 있다.
    - **타입 안정성 부족** : 문자열은 ***오타가 자주 발생***하고, ***유효하지 않은 값이 입력***될 수 있다.
    - **데이터 일관성** : `GOLD`, `Gold`, `gold` 등 ***다양한 형식으로 문자열을 입력할 수 있어 일관성***이 떨어진다.
- `String` 사용시 타입 안정성이 부족하다.
    - **값의 제한 부족**
    - **컴파일 시 오류 감지 불가**

---
### 2. 문자열과 타입 안정성2
- 위 예제에서 등급을 문자열로 받았는데 고정된 문자열을 상수로 정하면 더 안전해진다.
    ```java
    public class StringGrade {
        public static final String BASIC = "BASIC";
        public static final String GOLD = "GOLD";
        public static final String DIAMOND = "DIAMOND";
    }
    ```
- 그리고 `DiscountService` 에서 문자열에 오타를 입력해도 상수기 때문에 컴파일 에러가 뜬다.
    ```java
    public class DiscountService {
    
        public int discount(String grade, int price){
            int discountPercent = 0;
    
            if(grade.equals(StringGrade.BASIC)) discountPercent = 10;
            else if(grade.equals(StringGrade.GOLD)) discountPercent = 20;
            else if(grade.equals(StringGrade.DIAMOND)) discountPercent = 30;
            else System.out.println(grade + "는 할인을 안합니다 !");
    
            return price * discountPercent / 100;
        }
    }
    ```
- `main()` 에서도 오타를 낼 일이 없다.
  ```java
  public class StringGradeEx1_1 {
      public static void main(String[] args) {
          int price = 10000;
  
          DiscountService discountService = new DiscountService();
          int basic = discountService.discount(StringGrade.BASIC, price);
          int gold = discountService.discount(StringGrade.GOLD, price);
          int diamond = discountService.discount(StringGrade.DIAMOND, price);
  
          System.out.println("BASIC 등급의 할인 금액: " + basic);
          System.out.println("GOLD 등급의 할인 금액: " + gold);
          System.out.println("DIAMOND 등급의 할인 금액: " + diamond);
      }
  }
  ```
- 이렇게 기존의 등급을 문자열이 아닌 문자열상수로 받기 때문에 `StringGrade` 클래스에 속하는 문자열만 사용할 수 있게 된다.
- **오타가 나도 컴파일 시점에 오류가 발생하기 때문에 개발자가 바로 찾을 수 있다.**
- 하지만 문자열상수도 완전히 안전한 방법은 아니다. 왜냐하면 _문자열상수가 아닌 문자열을 입력해도 컴파일이 되기 때문이다._

---
### 3. 타입 안전 열거형 패턴(Type-Safe Enum Pattern)
- `enum` 은 `enumeration` 의 줄임말인데, 번역하면 열거라는 뜻이고 **어떤 항목을 나열하는 것**을 뜻한다.
- 위 예제의 경우 `BASIC`, `GOLD`, `DIAMOND` 를 나열하는 것이다.
- 이렇게 되면 사용자는 열거형인 `BASIC` , `GOLD` , `DIAMOND` 만 사용할 수 있고 오타나 유효하지 않은 값을 입력할 수 없다.

- 타입 안전 열거형 패턴은 아래와 같이 구현된다.
  ```java
  public class ClassGrade {
      public static final ClassGrade BASIC = new ClassGrade();
      public static final ClassGrade GOLD = new ClassGrade();
      public static final ClassGrade DIAMOND = new ClassGrade();
  }
  ```
- 기존의 `DiscountService` 의 코드도 변경된다.
  ```java
  public class DiscountService {
  
      public int discount(ClassGrade classGrade, int price) {
          int discountPercent = 0;
  
          if (classGrade == ClassGrade.BASIC) discountPercent = 10;
          else if (classGrade == ClassGrade.GOLD) discountPercent = 20;
          else if (classGrade == ClassGrade.DIAMOND) discountPercent = 30;
          else System.out.println("할인X");
  
          return price * discountPercent / 100;
      }
  }
  ```
    - 기존의 `discount()` 에서 첫번째 매개변수의 타입이 `String` -> `ClassGrade` 로 변경되었다.
- main() 에서 잘 동작하는지 확인해보자
  ```java
  public class ClassGradeEx2_1 {
      public static void main(String[] args) {
          int price = 10000;
  
          DiscountService discountService = new DiscountService();
          int basic = discountService.discount(ClassGrade.BASIC, price);
          int gold = discountService.discount(ClassGrade.GOLD, price);
          int diamond = discountService.discount(ClassGrade.DIAMOND, price);
  
          System.out.println("BASIC 등급의 할인 금액: " + basic);
          System.out.println("GOLD 등급의 할인 금액: " + gold);
          System.out.println("DIAMOND 등급의 할인 금액: " + diamond);
      }
  }
  ```
- 오타, 유효하지 않은 값을 입력해도 `ClassGrade` 에 있는 상수필드만 사용할 수 있기 때문에 컴파일 이전에 오류를 잡을 수 있다.
- 하지만 이 방법도 완벽하지 않다. 아래 예제를 통해 보자.
    - 새로운 `ClassGrade` 객체를 만들어 넣을 수 있다. 파라미터 타입이 같기 때문에 에러도 뜨지 않는다.
  ```java
  public class ClassGradeEx2_2 {
      public static void main(String[] args) {
  
          int price = 10000;
          DiscountService discountService = new DiscountService();
          ClassGrade classGrade = new ClassGrade();
          discountService.discount(classGrade, price);
      }
  }
  ```
- 위 방법을 막을려면 생성자를 `private` 로 막아버리면 컴파일에러가 뜨기때문에 잡을 수 있다.
  ```java
  public class ClassGrade {
      public static final ClassGrade BASIC = new ClassGrade();
      public static final ClassGrade GOLD = new ClassGrade();
      public static final ClassGrade DIAMOND = new ClassGrade();
  
      // private 생성자
      private ClassGrade() {}
  }
  ```

#### 타입 안전 열거형 패턴(Type-Safe Enum Pattern)의 장점
- `타입 안정성 향상` : 정해지 객체만 사용하기 때문에 ***잘못된 값을 입력하는 문제를 근본적으로 방지***할 수 있다.
- `데이터 일관성` : 정해진 객체만 사용하므로 ***데이터의 일관성이 보장***된다.
- `제한된 인스턴스 생성` : 클래스는 사전에 정의된 몇 개의 인스턴스만 생성하고, ***외부에서 이 인스턴스들만 사용***할 수 있도록 한다.
- `타입 안정성` : 잘못된 값이 할당되거나 사용되는 것을 ***컴파일 시점에 방지***할 수 있다.

---
### 4. 열거형 - Enum Type
> `Java` 에서 **타입 안전 열거형 패턴(Type-Safe Enum Pattern)**을 편리하게 이용할 수 있도록 `Enum Type`을 제공한다.
- 타입 안전 열거형 패턴을 `Enum Type`으로 바꾸면 아래와 같이 간편해진다.
    ```java
    public enum Grade {
        BASIC, GOLD, DIAMOND
    }
    ```
#### ENUM 특징
- 열거형도 사실 `class` 이다. `class`를 표기하는 곳에 `enum` 으로 바꿨을 뿐이지 `class`이다.
- 열거형은 자동으로 `java.lang.Enum` 을 상속받는다.
- 외부에서 임의로 생성할 수 없다.

- 위 그림이 맞는지 확인해보자.
    - `Enum` 은 `toString()`을 오버라이딩 되어 있어서 참조값을 확인할려면 별도의 메서드를 만들어서 확인해야 한다.
    - `System.identityHashCode(object)` : `Java` 가 관리하는 객체의 참조값을 숫자로 반환한다.
    - `Integer.toHexString()` : 숫자를 16진수로 변환
  ```java
  public class EnumRefMain {
      public static void main(String[] args) {
          System.out.println("class BASIC = " + Grade.BASIC.getClass());
          System.out.println("class GOLD = " + Grade.GOLD.getClass());
          System.out.println("class DIAMOND = " + Grade.DIAMOND.getClass());
  
          // ENUM 은 자동으로 toString()이 오버라이딩 되어있어서 참조값을 확인할 수 없다.
          System.out.println("ref BASIC = " + refValue(Grade.BASIC));
          System.out.println("ref GOLD = " + refValue(Grade.GOLD));
          System.out.println("ref DIAMOND = " + refValue(Grade.DIAMOND));
      }
  
      private static String refValue(Object obj) {
          return Integer.toHexString(System.identityHashCode(obj));
      }
  }
  ```
#### 예제에 ENUM 적용
- 기존의 `DiscountService` 코드에서 `Enum` 으로 수정하였다.
  ```java
  public class DiscountService {
  
      public int discount(Grade classGrade, int price) {
          int discountPercent = 0;
  
          if (classGrade == Grade.BASIC) discountPercent = 10;
          else if (classGrade == Grade.GOLD) discountPercent = 20;
          else if (classGrade == Grade.DIAMOND) discountPercent = 30;
          else System.out.println("할인X");
  
          return price * discountPercent / 100;
      }
  }
  ```
- `main()` 에서도 잘 작동한다.
  ```java
  public class ClassGradeEx3_1 {
      public static void main(String[] args) {
          int price = 10000;
  
          DiscountService discountService = new DiscountService();
          int basic = discountService.discount(Grade.BASIC, price);
          int gold = discountService.discount(Grade.GOLD, price);
          int diamond = discountService.discount(Grade.DIAMOND, price);
  
          System.out.println("BASIC 등급의 할인 금액: " + basic);
          System.out.println("GOLD 등급의 할인 금액: " + gold);
          System.out.println("DIAMOND 등급의 할인 금액: " + diamond);
      }
  }
  ```

#### ENUM 의 장점
1. `타입 안정성 향상` : 열거형에 정의된 상수들만 사용할 수 있기 때문에, ***유효하지 않은 값이 입력될 가능성이 없다.***
2. `간결성 및 일관성` : 열거형을 사용하면 코드가 더 간결하고 명확해지며, ***데이터의 일관성이 보장***된다.
3. `확장성` : 위 예제에서 새로운 등급을 추가해야하면, `ENUM` 에 상수만 추가하면 된다.

---
### 5. 열거형 - 주요 메서드
- 코드로 알아보자
    - `Arrays.toString(object[])` : 배열의 참조값이 아니라 배열의 내부의 값을 출력
    - `values()` : 모든 ENUM 상수를 반환
    - `valueOf(String name)` : name 과 일치하는 ENUM 상수를 반환
    - `name()` : ENUM 상수의 이름을 문자열로 반환
    - `ordinal()` : ENUM 상수의 선언 순서(0부터)를 반환
  ```java
  public class EnumMethodMain {
      public static void main(String[] args) {
          Grade[] values = Grade.values();
          System.out.println(Arrays.toString(values));
  
          for (Grade value : values) {
              System.out.println("name = " + value.name() + ", ordinal = " + value.ordinal());
          }
  
          // String -> ENUM 변환, 잘못된 문자를 넣으면 IlleagalArgumentException 발생
          String input = "GOLD";
          Grade grade = Grade.valueOf(input);
          System.out.println("grade = " + grade);
      }
  }
  ```

#### `ordinal()` 가급적 사용 X
- _**ENUM 상수 중간에 새로운 상수가 추가되면 기존의 상수의 순번이 모두 변경된다 !!**_
- 예를 들어, `BASIC` 다음에 `SIVER` 가 추가되면 `GOLD`, `DIAMOND` 의 값이 변경되어 **다른 코드에서 사이드 이펙트가 발생 !!*
#### ENUM(열거형) 정리
- 열거형은 `java.lang.Enum` 을 자동으로 상속 받는다.
- 열거형은 `java.lang.Enum` 을 상속받기 때문에 **다른 클래스를 상속받을 수 없다.**
- 열거형은 **인터페이스를 구현**할 수 있다.
- 열거형에 **추상 메서드를 선언하고, 구현**할 수 있다.

---
### 6. 열거형 - 리팩토링
- 기존 `ex2` 패키지에 있는 파일을 리팩토링 해보자
  ```java
  public class DiscountService {
  
      public int discount(ClassGrade classGrade, int price) {
          int discountPercent = 0;
  
          if (classGrade == ClassGrade.BASIC) discountPercent = 10;
          else if (classGrade == ClassGrade.GOLD) discountPercent = 20;
          else if (classGrade == ClassGrade.DIAMOND) discountPercent = 30;
          else System.out.println("할인X");
  
          return price * discountPercent / 100;
      }
  }
  ```
    - 위 코드는 불필요한 if문이 너무 많다.
    - 그리고 회원 등급 클래스가 `할인율(discountPercent)`를 가지고 관리하도록 변경하는게 좋다.
- 우선 기존의 **타입안전 열거형 패턴** 코드에서 `할인율(discountPercnet)`를 관리하도록 코드를 추가한다.
  ```java
  public class ClassGrade {
      public static final ClassGrade BASIC = new ClassGrade(10);
      public static final ClassGrade GOLD = new ClassGrade(20);
      public static final ClassGrade DIAMOND = new ClassGrade(30);
  
      private final int discountPercent;
  
      private ClassGrade(int discountPercent) {
          this.discountPercent = discountPercent;
      }
  
      public int getDiscountPercent() {
          return discountPercent;
      }
  }
  ```
    - 생성자를 `private` 로 설정하여 상수가 각각 할인율을 갖도록 설정하였다.
    - 즉, _**상수를 정의할 때 각각의 등급에 따른 할인율이 정해진다.**_
- 기존의 `DiscountService`  클래스의 `discount()` 메서드도 아래와 같이 리팩토링 된다.
  ```java
  public class DiscountService {
      public int discount(ClassGrade classGrade, int price) {
          return price * classGrade.getDiscountPercent() / 100;
      }
  }
  ```
    - 기존의 불필요한 if문이 제거되고, 단순한 할인율 계산 로직만 남았다.
    - 즉, `discount()`가 호출될때 파라미터로 넘어오는 `ClassGrade` 는 이미 ***등급마다 할인율이 정해졌기 때문에 계산결과만 반환***하게 된다.
- `main()` 메서드로 실행하면 아래와 같다.
  ```java
  public class ClassGradeRefMain1 {
      public static void main(String[] args) {
          int price = 10000;
  
          DiscountService discountService = new DiscountService();
          int basic = discountService.discount(ClassGrade.BASIC, price);
          int gold = discountService.discount(ClassGrade.GOLD, price);
          int diamond = discountService.discount(ClassGrade.DIAMOND, price);
  
          System.out.println("BASIC 등급의 할인 금액: " + basic);
          System.out.println("GOLD 등급의 할인 금액: " + gold);
          System.out.println("DIAMOND 등급의 할인 금액: " + diamond);
      }
  }
  ```

---
### 7. 열거형 - 리팩토링 2
- 기존의 타입안전 열거형패턴으로 짠 ClassGrade 를 보면 Grade(등급)과 discountPercnet(할인율)이 1대1로 관련이 있다.
- 그래서 열거형으로 Grade 를 생성할때 아래와 같이 만들 수 있다.
  ```java
  public enum Grade {
      BASIC(10), GOLD(20), DIAMOND(30);
  
      private final int discountPercent;
  
      Grade(int discountPercent) {
          this.discountPercent = discountPercent;
      }
  
      public int getDiscountPercent() {
          return discountPercent;
      }
  }
  ```
    - `discountPercent` 를 상수로 넣고 생성자와 getter를 만들어준다.
    - 그리고 기존 new 키워드를 통해 등급마다 할인율을 지정해줬지만 열거형안에서 상수에 괄호를 이용하여 바로 `discountPercent` 를 지정할 수 있다.
- 기존의 `DiscountService` 클래스와 `main()`을 구현하여 실행해보자.
  ```java
  public class DiscountService {
      public int discount(Grade grade, int price) {
          return price * grade.getDiscountPercent() / 100;
      }
  }
  
  public class EnumRefMain2 {
    public static void main(String[] args) {
      int price = 10000;
  
      DiscountService discountService = new DiscountService();
      int basic = discountService.discount(Grade.BASIC, price);
      int gold = discountService.discount(Grade.GOLD, price);
      int diamond = discountService.discount(Grade.DIAMOND, price);
  
      System.out.println("BASIC 등급의 할인 금액: " + basic);
      System.out.println("GOLD 등급의 할인 금액: " + gold);
      System.out.println("DIAMOND 등급의 할인 금액: " + diamond);
    }
  }
  ```

---
### 8. 열거형 - 리팩토링 3
> 리펙토링 마지막으로는 할인율 계산을 하는 DiscountService 코드만 남았다.

- 현재 할인율 계산을 할 때 `Grade` 가 가지고 있는 데이터인 `discountPercent`의 값을 가져와 사용한다.
- 객체 지향 관점에서 볼 때 `Grade` 자신의 데이터를 외부에 노출하는 것 보다는, `Grade` 클래스가 ***자신의 할인율을 어떻게 계산하는지 스스로 관리하는 것이 캡슐화 원칙***에 더 맞다.
    ```java
    public class DiscountService {
        public int discount(Grade grade, int price) {
            return price * grade.getDiscountPercent() / 100;
        }
    }
    ```
- 즉, 위 로직이 `Grade` 안에 있는게 훨씬 좋다.
  ```java
  public enum Grade {
      BASIC(10), GOLD(20), DIAMOND(30);
  
      private final int discountPercent;
  
      Grade(int discountPercent) {
          this.discountPercent = discountPercent;
      }
  
      public int getDiscountPercent() {
          return discountPercent;
      }
  
      // 비즈니스 로직 추가
      public int dicount(int price) {
          return price * discountPercent / 100;
      }
  }
  ```
    - 기존의 `DiscountService` 에 있던 `dicount()`메서드가 `Grade` 내부로 들어왔다.
- 그럼 `DiscountService` 에 `discount()`도 아래와 같이 바뀐다.
  ```java
  public class DiscountService {
      public int discount(Grade grade, int price) {
          return grade.dicount(price);
      }
  }
  ```
- `main()` 도 문제 없이 잘 돌아간다.
  ```java
  public class EnumRefMain3_1 {
      public static void main(String[] args) {
          int price = 10000;
  
          DiscountService discountService = new DiscountService();
          int basic = discountService.discount(Grade.BASIC, price);
          int gold = discountService.discount(Grade.GOLD, price);
          int diamond = discountService.discount(Grade.DIAMOND, price);
  
          System.out.println("BASIC 등급의 할인 금액: " + basic);
          System.out.println("GOLD 등급의 할인 금액: " + gold);
          System.out.println("DIAMOND 등급의 할인 금액: " + diamond);
      }
  }
  ```

#### DiscountService 제거
- 이제 할인율 계산을 `Grade` 에서 하는데 굳이 `DiscountService가` 없어도 문제가 없다.
  ```java
  public class EnumRefMain3_2 {
      public static void main(String[] args) {
          int price = 10000;
  
          System.out.println("BASIC 등급의 할인 금액: " + Grade.BASIC.dicount(price));
          System.out.println("GOLD 등급의 할인 금액: " + Grade.GOLD.dicount(price));
          System.out.println("DIAMOND 등급의 할인 금액: " + Grade.DIAMOND.dicount(price));
      }
  }
  ```
    - 더이상 `main()` 에서도 `DiscountService` 없이 `Grade` 만 사용해도 된다.

#### 출력 중복 제거
- main() 메서드에서는 비슷한 출력을 계속하기 때문에 이를 메서드로 빼서 리팩토링한다.
  ```java
  public class EnumRefMain3_3 {
      public static void main(String[] args) {
          int price = 10000;
  
          printDiscount(Grade.BASIC, price);
          printDiscount(Grade.GOLD, price);
          printDiscount(Grade.DIAMOND, price);
      }
  
      private static void printDiscount(Grade grade, int price) {
          System.out.println(grade.name() + " 등급의 할인 금액: " + grade.dicount(price));
      }
  }
  ```

#### ENUM 목록
- `Grade` 에 새로운 등급이 추가되더라도 기
- 존 `main()` 코드는 변경 없이 모든 등급의 할인을 출력해보자.
  ```java
  public class EnumRefMain3_4 {
      public static void main(String[] args) {
          int price = 10000;
          Grade[] grades = Grade.values();
          for (Grade grade : grades) {
              printDiscount(grade, price);
          }
      }
  
      private static void printDiscount(Grade grade, int price) {
          System.out.println(grade.name() + " 등급의 할인 금액: " + grade.dicount(price));
      }
  }
  ```
    - `values()` 는 모든 ENUM 상수들을 출력해주기 때문에 새로운 등급이 추가되도 기존 `main()`은 안건들여도 된다.

---