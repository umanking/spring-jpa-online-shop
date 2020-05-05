# Spring Boot JPA Online shop example 

## 모델링 
![](https://user-images.githubusercontent.com/28615416/80606188-f9311300-8a6e-11ea-8bd0-5978c60d9128.png)
![](https://user-images.githubusercontent.com/28615416/80606200-fd5d3080-8a6e-11ea-938c-db9ca1084f3f.png)


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





## 엔티티 설계시 주의사항

1.  setter를 사용하지 않는다. 
    
1.  객체에 대한 변경사항을 파악하기 어렵고, 유지보수하기 매우 힘들다.
    
2.  연관관계 설정은 lazy로 설정한다.

    1.  EAGER는 예측이 어렵고, 어떤 SQL이 실행되는지 파악하기 힘들다, 특히 JPQL 을 실행할 때 N + 1 문제가 자주 발생한다. 

        >   order N: member 1 관계에서
        >   select o from order; 이 결과 100개의 order 결과가 있으면, 해당 결과마다 member를 조회하는 쿼리가 100번 발생한다. 즉, order조회 쿼리 1 번에 따른 100번(N)번의 결과 값에 대한 쿼리가 추가로 발생한다. 해서 N+1 문제!!!

    2.  연관된 엔티티를 가져오고 싶으면 fetch join, 엔티티 그래프로 해결한다.

3.  컬렉션 초기화는 필드에서 초기화 한다. 
    1.  하이버네이트는 엔티티를 영속화 할때, 컬렉션을 감싸서 하이버네이트가 제공하는 컬렉션으로 제공한다. 그렇기 때문에 컬렉션을 변경해서 사용하게 되면, 하이버네이트가 원하는 동작되로 되지 않을 가능성이 있다.

4.  테이블,컬럼명 생성전략 
    1.  스프링 부트를 사용하게 되면 SpringPhsicalNamingStrategy로 camelcase -> uderscore 로 변경한다.