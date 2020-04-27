# Spring Boot JPA Online shop example 


## 연관관계 주인? 
- 양방향 연관관계에서, 객체가 서로 참조하고 있는데 도대체 주인?은 누구로 할거야? 에서 나온 개념 
- 보통 DB에서는 외래키는 1:N에서 N쪽에 존재한다. 생각해보면 당연한거
- Member(1) : Order(N )

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
- 외래키는 Order 테이블에 존재하고, join하는 컬럼은 `member_id` 이다. 
- mappedBy는 member라는 필드에 의해서 매핑된 정보일 뿐, 연관관계 주인은 Order에 있고, Member가 fk를 변경할 수 없다. 오직 Member는 읽기 전용이다.
