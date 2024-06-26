package com.cafehub.cafehub.review.entity;

import com.cafehub.cafehub.cafe.entity.Cafe;
import com.cafehub.cafehub.comment.entity.Comment;
import com.cafehub.cafehub.common.dto.Timestamped;
import com.cafehub.cafehub.member.entity.Member;
import com.cafehub.cafehub.reviewPhoto.entity.ReviewPhoto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Review extends Timestamped {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "review_id")
    private Long id;

    private Integer rating;

    @Lob
    private String content;

    private Integer likeCount;

    private Integer commentCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private Cafe cafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    /**
     * 양방향 매핑의 경우 필요에 따라 회의를 거쳐 생성하는 것으로 하자
     * 양방향 매핑의 경우 다음과 같은 단점이 있다.
     * 1. 메모리 사용
     * 양방향 매핑을 사용하면 연관된 엔티티를 로드할 때 메모리 사용량이 증가할수 있다.
     * 특히 지연로딩을 사용할 때, 프록시 객체를 유지하기 위해 추가적인 메모리가 필요하다.
     * 2. 성능 문제
     * 한 엔티티를 조회할 때 연관된 엔티티를 프록시로 가져오기 때문에 추가적인 쿼리가 실행될 수 있다.
     * 3. 순환 참조 문제
     * lombok의 toString / @Responsebody / HttpEntitu / ResponseEntitu에서
     * 엔티티를 그대로 사용할 경우
     * 4. 유지 보수 및 복잡성
     * 두 엔티티 간의 일관성을 계속 유지해야 함으로써 문제가 생길 수 있다.
     * 또한 한 트랜잭션 안에서 영속성 컨텍스트를 이용할 경우 데이터 적합성이 불일치 할 수 있다.
     */

    // One To Many로 데이터를 끌고오는게 아니라 JPQL 로 fetchJoin을 하기 위해서 열었음
    @OneToMany(mappedBy = "review")
    @Builder.Default
    private List<ReviewPhoto> reviewPhotos = new ArrayList<>();

    @OneToMany(mappedBy = "review")
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
//
//    @OneToMany(mappedBy = "review")
//    private List<LikeReview> likeReviews = new ArrayList<>();

    // updateReview 부분 최적화.
    public void updateContent(Integer rating, String content) {
        this.rating = rating;
        this.content = content;
    }

    public void updateCommentCount(Integer commentCount){
        this.commentCount = commentCount;
    }

    public void updateLikeCount(Integer likeCount){
        this.likeCount = likeCount;
    }
}
