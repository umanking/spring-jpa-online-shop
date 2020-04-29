# Spring Boot JPA Online shop example 

## 모델링 



## 연관관계 1:N 
- 양방향 연관관계에서, 객체가 서로 참조하고 있는데 도대체 주인?은 누구로 할거야? 에서 나온 개념 
- 보통 DB에서는 외래키는 1:N에서 N쪽에 존재한다. 생각해보면 당연한거
- Member(1) : Order(N)

```java
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList();
} 

public class Order {
    
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
```
- 외래키는 Order 테이블에 존재하고(N쪽에 존재함), join 컬럼은 `member_id` 이다. 
- mappedBy는 member라는 필드에 의해서 매핑된 정보일 뿐, 연관관계 주인은 Order에 있고, Member가 fk를 변경할 수 없다. 오직 Member는 읽기 전용이다.


## 연관관계 계층구조
```java
public class Category {
    
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList();

}
```
- 자기자신을 계층구조로 가질 경우, 부모는 ManyToOne으로 자식은 OneToMany로 매핑한다.


## 외래키는 꼭 걸어야 하나? 
- 데이터의 정합성이 중요하면(돈,...) `참조 무결성` 제약조건을 만족하기 위해서 반드시 걸어야 하고
- 단순 서비스가 잘 돌아가기만을 위한 거라면 -> index만 잘 걸어줘도 된다.


## 값 객체 ?
Address 같은 값 객체는 값이기 때문에 변경되면 안되고, Immutable하게 설계되야 한다. 생성자에서만 만들고, Setter는 제거한다. 

## Getter, Setter
데이터를 조회할 일이 많음. Getter는 열어두고, Setter는 비즈니스 로직쪽으로 빼야 한다.

## JPA spec
- jpa는 reflection이나 proxy를 이용해서 객체를 생성하기 때문에, 기본생성자가 없다면 빨간줄이 뜬다.