package com.fx.user.adapter.out.persistence.repository;

import com.fx.user.adapter.out.persistence.entity.FollowEntity;
import com.fx.user.adapter.out.persistence.entity.ProfileEntity;
import com.fx.user.adapter.out.persistence.entity.QFollowEntity;
import com.fx.user.adapter.out.persistence.entity.QProfileEntity;
import com.fx.user.domain.FollowQuery;
import com.fx.user.domain.FollowUserInfo;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import static com.fx.user.adapter.out.persistence.entity.QFollowEntity.followEntity;
import static com.fx.user.adapter.out.persistence.entity.QProfileEntity.profileEntity;

@Repository
@RequiredArgsConstructor
public class FollowQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;


    /**
     * 내가 팔로우한 사용자 조회 (내가 followingId 를 참조)
     */
    public List<FollowUserInfo> findByFollowings(FollowQuery followQuery) {
        return jpaQueryFactory
            .select(Projections.constructor(
                FollowUserInfo.class,
                followEntity.id,
                profileEntity.userId,
                profileEntity.nickname,
                profileEntity.mediaId,
                followEntity.status,
                followEntity.createdAt
            ))
            .from(followEntity)
            .join(profileEntity).on(followEntity.followingId.eq(profileEntity.userId))
            .where(
                followEntity.followerId.eq(followQuery.getTargetUserId()),
                ltCreatedAt(followQuery.getCreatedAt()),
                likeNickname(followQuery.getNickname()),
                followEntity.status.eq(followQuery.getStatus())
            )
            .orderBy(getOrderSpecifier(followQuery.getPageable().getSort()).toArray(new OrderSpecifier[0]))
            .limit(followQuery.getPageable().getPageSize())
            .fetch();
    }

    /**
     * 나를 팔로우한 사용자 조회 (내가 followerId 를 참조)
     */
    public List<FollowUserInfo> findByFollowers(FollowQuery followQuery) {
        return jpaQueryFactory
            .select(Projections.constructor(
                FollowUserInfo.class,
                followEntity.id,
                profileEntity.userId,
                profileEntity.nickname,
                profileEntity.mediaId.stringValue(),
                followEntity.status,
                followEntity.createdAt
            ))
            .from(followEntity)
            .join(profileEntity).on(followEntity.followerId.eq(profileEntity.userId))
            .where(
                followEntity.followingId.eq(followQuery.getTargetUserId()),
                ltCreatedAt(followQuery.getCreatedAt()),
                likeNickname(followQuery.getNickname())
            )
            .orderBy(getOrderSpecifier(followQuery.getPageable().getSort()).toArray(new OrderSpecifier[0]))
            .limit(followQuery.getPageable().getPageSize())
            .fetch();
    }

    private BooleanExpression ltCreatedAt(LocalDateTime createdAt) {
        return createdAt != null ? followEntity.createdAt.lt(createdAt) : null;
    }

    private BooleanExpression likeNickname(String nickname) {
        return nickname != null && !nickname.isBlank()
            ? profileEntity.nickname.containsIgnoreCase(nickname)
            : null;
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        sort.stream().forEach(order -> {
            PathBuilder orderByExpression = new PathBuilder(FollowEntity.class, "followEntity");
            orders.add(new OrderSpecifier<>(order.isDescending() ? Order.DESC : Order.ASC,
                orderByExpression.get(order.getProperty())));
        });
        return orders;
    }

}