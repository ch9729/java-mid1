### 불변 객체 학습
> 1. 기본형과 참조형의 공유
> 2. 공유 참조와 사이드 이펙트
> 3. 불변 객체 - 도입
> 4. 불변 객체 - 예제
> 5. 불변 객체 - 값 변경
> 6. 정리

---
### 1. 기본형과 참조형의 공유
> Java 에서 데이터 타입을 2가지로 나누면 `기본형(Primitive Type)`과 `참조형(Reference Type)`으로 나눌 수 있다.
> - **기본형** : _하나의 값을 여러 변수에서 절대로 공유하지 않는다._
> - **참조형** : _하나의 객체를 참조값을 통해 여러 변수에서 공유할 수 있다._

#### 기본형 예제
- a의 값을 b에 복사하고 b의 값을 변경해도 기본형이기 때문에 a 값에 영향이 없다.
    ```java
    public class PrimitiveMain {
        public static void main(String[] args) {
            // 기본형 : 절대로 같은 값을 공유하지 않는다.
            int a = 10;
            int b = a;
    
            System.out.println("a = " + a);
            System.out.println("b = " + b);
    
            b=20; // b 의 값을 변경해도 a의 영향이 없다.
            System.out.println("a = " + a);
            System.out.println("b = " + b);
        }
    }
    ```

- `b=a` 라고 하면 `Java`에서 ***a의 값을 복사해서 b에 대입***하기 때문에 b의 값이 변경해도 a의 값이 변경되지 않는다.


#### 참조형 예제
- 참조형은 인스턴스를 공유하기 때문에 b 값을 변경하면 a의 값도 변경된다.
  ```java
  public class RefMain1_1 {
      public static void main(String[] args) {
          // 참조형 : 하나의 인스턴스를 공유한다.
          Address a = new Address("Manchester");
          Address b = a;
  
          System.out.println("a = " + a);
          System.out.println("b = " + b);
  
          b.setValue("부산");
          System.out.println("a = " + a);
          System.out.println("b = " + b);
      }
  }
  ```

- 참조형변수 b에 참조형변수 a를 대입하면 **참조값을 복사**하여 b에 전달된다.(_**자바는 항상 값을 복사해서 대입**_)
- 즉, _**참조값을 복사해서 넣었기 때문에 a와 b는 같은 인스턴스를 가리키게 된다.**_


---
### 2. 공유 참조와 사이드 이펙트
> `사이드 이펙트(Side Effect)`는 프로그래밍에서 어떤 계산이 주된 작업 외에 추가적인 부수 효과를 일으키는 것을 말한다

- 위에서 했던 코드를 예시로 들면, b 의 값만 부산 으로 바꿀려고 했지만 a 의 값도 같이 바뀌게되었다. 이것이 사이드 이펙트이다.
  ```java
  public class RefMain1_1 {
      public static void main(String[] args) {
          // 생략 ....
          b.setValue("London");
          System.out.println("a = " + a); // 사이드 이펙트 발생
          System.out.println("b = " + b);
      }
  }
  ```
- 이렇게 개발자에 의도는 b 의 값만 바꿀려고 했으나 참조형 변수의 특징으로 인해 a의 값도 바뀌게 되었다.
- 이런 *개발자에 의도와 다르게 변경이 의도치 않게 다른 부분에 영향을 미치는 경우는 디버깅이 어려워지고 코드의 안정성이 저하*될 수 있다.


#### 사이드 이펙트 해결 방안
> 각각의 객체를 ***서로 다른 인스턴스를 참조***하게 하면 된다.
- 위 코드를 수정해보면 a 와 b 를 다른 인스턴스를 참조하게 초기화하면 된다.
  ```java
  public class RefMain1_2 {
      public static void main(String[] args) {
          // 참조형 : 하나의 인스턴스를 공유한다.
          Address a = new Address("서울"); // x001
          Address b = new Address("서울"); // x002
  
          System.out.println("a = " + a);
          System.out.println("b = " + b);
  
          b.setValue("부산");
          System.out.println("a = " + a); // 사이드 이펙트가 발생하지 않는다 !
          System.out.println("b = " + b);
      }
  }
  ```

- 즉, `서울` 이라는 같은 값을 가지지만 _참조값은 전혀 다르기 때문에 값을 수정해도 서로 영향을 끼치지 않는다._


- 하지만 참조값을 다르게 한다고 해도 _**하나의 객체를 여러 변수가 공유하지 않도록 강제로 막을 수 있는 방법은 없다.**_
- 아까 처럼 `b = a` 라고 코드를 짜도 어떤 오류가 발생하지 않는다. **즉, 자바 문법상 오류가 없기에 강제로 막을 수 있는 방법은 없다.**
  ```java
  public class RefMain1_1 {
      public static void main(String[] args) {
          Address a = new Address("Manchester");
          Address b = a; // 문법상 오류가 없다 !!
          // 생략...
      }
  }
  ```
- 참조형 변수의 객체 공유는 꼭 필요할 때도 있지만, 때로는 공유하는 것이 사이드 이펙트를 만드는 경우가 있다.

---
### 3. 불변 객체 - 도입
> 객체의 공유 자체는 자바에서 컴파일 에러를 내지 않는다! 왜냐하면 **공유 자체에는 문제가 없기 때문이다.**
> 문제의 ***직접적인 원인은 공유된 객체의 값을 변경***하는것이 원인이다.

#### 불변 객체의 도입
- **객체의 상태(객체 내부의 값, 필드 멤버 변수)가 변하지 않는 객체**를 `불변 객체(Immutable Object)`라 한다.
- 이전의 만든 [Address 클래스](https://github.com/LegdayDev/Java-Middle-1/blob/master/src/lang/immutable/address/Address.java) 를 상태가 변하지 않는 불변 클래스로 만든다.
    - `value` 필드를 수정못하게 `final` 로 바꿨다.(필드 수정이 안되기 때문에 `setValue()` 메서드도 삭제)
    - 꼭 final 키워드가 아니더라도 필드를 수정못하게 바꾸기만 하면된다 !!
        ```java
        public class ImmutableAddress {
      
            final private String value; // value 필드를 변하지못하게 final 로 막는다.
      
            public ImmutableAddress(String value) {
                this.value = value;
            }
      
            public String getValue() {
                return value;
            }
      
            @Override
            public String toString() {
                return "Address{" +
                        "value='" + value + '\'' +
                        '}';
            }
        }
        ```
- 만약 다른개발자 객체를 공유하여 필드를 변경할려고 하면 에러가 뜰 것이다.
    ```java
    public class RefMain2 {
        public static void main(String[] args) {
            ImmutableAddress a = new ImmutableAddress("Manchester");
            ImmutableAddress b = a; // 참조값 대입을 막을 수 있는 방법은 없다!!(Java 문법상 문제없음)
    
            System.out.println("a = " + a);
            System.out.println("b = " + b);
    
    //        b.setValue("London"); // 컴파일에러 !! setValue() 메서드가 없기 떄문에 수정불가 !!
            System.out.println("a = " + a);
            System.out.println("b = " + b);
        }
    }
    ```
- 그렇게 되면 개발자는 새로운 객체를 만들어 사용하게 된다

- _**불변이라는 단순한 제약을 사용하여 사이드 이펙트를 막을 수 있다 !**_
> `가변(Mutable)객체` 와 `불변(Immutable)객체` </br>가변은 이름 그대로 처음 만든 이후 상태가 변할 수 있고, 불변은 처음 만든 이후 상태가 변하지 않는 뜻이다.
---
### 4. 불변 객체 - 예제
- 우선 가변(Mutable)객체인 Address 를 공유하는 예제 코드이다.
  ```java
  public class MemberV1 {
      private String name;
      private Address address;
  
      public MemberV1(String name, Address address) {
          this.name = name;
          this.address = address;
      }
  
      public Address getAddress() {
          return address;
      }
  
      public void setAddress(Address address) {
          this.address = address;
      }
  
      @Override
      public String toString() {
          return "MemberV1{" +
                  "name='" + name + '\'' +
                  ", address=" + address +
                  '}';
      }
  }
  
  public class MemberMainV1 {
    public static void main(String[] args) {
      Address address = new Address("서울");
  
      MemberV1 memberA = new MemberV1("회원A", address);
      MemberV1 memberB = new MemberV1("회원B", address);
  
      // 회원A , 회원B 의 처음 주소는 서울
      System.out.println("memberA = " + memberA);
      System.out.println("memberB = " + memberB);
  
      // 요구사항 변경 !! : 회원B의 주소를 부산으로 변경
      memberB.getAddress().setValue("부산");
  
      // 다시 출력
      System.out.println("memberB.address -> 부산으로 변경");
      System.out.println("memberA = " + memberA);
      System.out.println("memberB = " + memberB);
    }
  }
  ```

- **가변객체를 사용할 때 공유객체의 필드를 변경할 때 사이드 이펙트가 발생**한다.
- 아래 코드는 불변(Immutable)객체인 ImmutableAddress 를 공유하는 예제이다.
  ```java
  public class MemberV2 {
      private String name;
      private ImmutableAddress address;
  
      public MemberV2(String name, ImmutableAddress address) {
          this.name = name;
          this.address = address;
      }
  
      public ImmutableAddress getAddress() {
          return address;
      }
  
      public void setAddress(ImmutableAddress address) {
          this.address = address;
      }
  
      @Override
      public String toString() {
          return "MemberV2{" +
                  "name='" + name + '\'' +
                  ", address=" + address +
                  '}';
      }
  }
  
  public class MemberMainV2 {
    public static void main(String[] args) {
      ImmutableAddress address = new ImmutableAddress("서울");
  
      MemberV2 memberA = new MemberV2("회원A", address);
      MemberV2 memberB = new MemberV2("회원B", address);
  
      // 회원A , 회원B 의 처음 주소는 서울
      System.out.println("memberA = " + memberA);
      System.out.println("memberB = " + memberB);
  
      // 요구사항 변경 !! : 회원B의 주소를 부산으로 변경
      // memberB.getAddress().setValue("부산"); 컴파일오류 !!
      memberB.setAddress(new ImmutableAddress("부산")); // 새로운 객체를 만들어 설정해야 한다 !!
  
      // 다시 출력
      System.out.println("memberB.address -> 부산으로 변경");
      System.out.println("memberA = " + memberA);
      System.out.println("memberB = " + memberB);
    }
  }
  ```
  
- 불변객체의 값을 변경할 수 없어서 새로운 객체를 넣어서 변경했다. 즉, 사이드 이펙트를 막았다 !!
---
### 5. 불변 객체 - 값 변경
> 이전까지 공유객체의 값 변경에 따르는 사이드이펙트를 막기위해 불변객체를 사용했다. 만약 _불변객체를 사용하지만 꼭 값을 변경해야 하는 경우가 있다!!_-

- 아래 코드는 불변객체의 값을 변경할 수 있는 add() 라는 메서드를 구현하고 기존의 값을 유지해서 불변객체의 특성을 지키는 클래스이다.
  ```java
  public class ImmutableObj {
      private final int value;
  
      public ImmutableObj(int value) {
          this.value = value;
      }
  
      public ImmutableObj add(int addValue){
          // 기존의 있던 value 값은 유지된다 !! 새로운 객체를 반환
          return new ImmutableObj(value + addValue);
      }
  
      public int getValue() {
          return value;
      }
  }
  ```
    - `add()` 메서드를 통해 `value` 값을 증가시키게 되면 ***기존의 value 를 더하는게 아닌 value 를 더한값을 가지는 객체를 만들어 반환***시켜준다.
    - 즉, **원래 있던 value 필드는 변경하지 않기 떄문에 불변을 유지가능**하게 된다.
- 테스트를 해보면 기존의 값을 더해서 새로운 객체를 받으면 기존값과 신규값 둘 다 확인가능하다.
  ```java
  public class ImmutableMain {
      public static void main(String[] args) {
          ImmutableObj obj1 = new ImmutableObj(10);
          ImmutableObj obj2 = obj1.add(20);
  
          // 계산 이후에도 기존 값과 신규값 확인가능
          System.out.println("obj1.getValue() = " + obj1.getValue());
          System.out.println("obj.getValue() = " + obj2.getValue());
      }
  }
  ```
- 실행 순서를 메모리 구조로 확인해보면 아래와 같다.

---
### 6. 정리
> 이전까지 불변객체의 내용을 중요하게 얘기하고 많이 했던 이유는 `String` 클래스 떄문이다.</br>
> 자바에서 가장 많이 사용되는 `String` 클래스가 불변객체이다.

- 클래스를 불변으로 설계하는 이유는 더 많다.
    - 캐시 안정성
    - 멀티 쓰레드 안정성
    - Entity 값 타입(JPA 에서 사용)