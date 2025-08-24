package com.fx.user.adapter.out.persistence.repository;

import static com.fx.user.adapter.out.persistence.entity.QFollowEntity.followEntity;
import static com.fx.user.adapter.out.persistence.entity.QProfileEntity.profileEntity;

import com.fx.user.adapter.out.persistence.entity.FollowEntity;
import com.fx.user.domain.FollowQuery;
import com.fx.user.application.out.persistence.dto.FollowUserInfo;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

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
                followEntity.status,
                followEntity.createdAt
            ))
            .from(followEntity)
            .join(profileEntity).on(followEntity.followingId.eq(profileEntity.userId))
            .where(
                followEntity.followerId.eq(followQuery.getTargetUserId()),
                cursorCondition(followQuery.getFollowId(), followQuery.getPageable().getSort()),
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
                followEntity.status,
                followEntity.createdAt
            ))
            .from(followEntity)
            .join(profileEntity).on(followEntity.followerId.eq(profileEntity.userId))
            .where(
                followEntity.followingId.eq(followQuery.getTargetUserId()),
                cursorCondition(followQuery.getFollowId(), followQuery.getPageable().getSort()),
                likeNickname(followQuery.getNickname()),
                followEntity.status.eq(followQuery.getStatus())
            )
            .orderBy(getOrderSpecifier(followQuery.getPageable().getSort()).toArray(new OrderSpecifier[0]))
            .limit(followQuery.getPageable().getPageSize())
            .fetch();
    }

    private BooleanExpression cursorCondition(Long followId, Sort sort) {
        if (followId == null) return null;

        Sort.Order order = sort.stream().findFirst().orElse(Sort.Order.desc("id"));

        if (order.isDescending()) {
            return followEntity.id.lt(followId);
        } else {
            return followEntity.id.gt(followId);
        }
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