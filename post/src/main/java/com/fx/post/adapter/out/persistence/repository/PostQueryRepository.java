package com.fx.post.adapter.out.persistence.repository;

import com.fx.post.application.out.persistence.dto.PostInfo;
import com.fx.post.adapter.out.persistence.entity.PostEntity;
import com.fx.post.domain.LikeQuery;
import com.fx.post.domain.PostQuery;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.fx.post.adapter.out.persistence.entity.QCommentEntity.commentEntity;
import static com.fx.post.adapter.out.persistence.entity.QLikeEntity.likeEntity;
import static com.fx.post.adapter.out.persistence.entity.QPostEntity.postEntity;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<PostInfo> findPosts(PostQuery postQuery) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        PostInfo.class,
                        postEntity.id,
                        postEntity.userId,
                        postEntity.content,
                        postEntity.createdAt,
                        likeEntity.id.countDistinct(),
                        commentEntity.id.countDistinct()
                ))
                .from(postEntity)
                .leftJoin(likeEntity).on(postEntity.id.eq(likeEntity.postId))
                .leftJoin(commentEntity).on(postEntity.id.eq(commentEntity.postId))
                .where(
                        userIdCondition(postQuery.getUserId(), postQuery.getUserIds()),
                        cursorCondition(postQuery.getPostId(), postQuery.getPageable().getSort()),
                        postEntity.isDeleted.eq(postQuery.isDeleted())
                )
                .groupBy(postEntity.id, postEntity.userId, postEntity.content, postEntity.createdAt)
                .orderBy(getOrderSpecifier(postQuery.getPageable().getSort()).toArray(new OrderSpecifier[0]))
                .limit(postQuery.getPageable().getPageSize())
                .fetch();

    }

    public List<PostInfo> findLikedPosts(LikeQuery likeQuery) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        PostInfo.class,
                        postEntity.id,
                        postEntity.userId,
                        postEntity.content,
                        postEntity.createdAt,
                        likeEntity.id.countDistinct(),
                        commentEntity.id.countDistinct()
                ))
                .from(postEntity)
                .innerJoin(likeEntity).on(
                        postEntity.id.eq(likeEntity.postId)
                                .and(likeEntity.userId.eq(likeQuery.getUserId()))
                )
                .leftJoin(commentEntity).on(postEntity.id.eq(commentEntity.postId))
                .where(
                        cursorCondition(likeQuery.getPostId(), likeQuery.getPageable().getSort()),
                        postEntity.isDeleted.eq(likeQuery.isDeleted())
                )
                .groupBy(postEntity.id, postEntity.userId, postEntity.content, postEntity.createdAt)
                .orderBy(getOrderSpecifier(likeQuery.getPageable().getSort()).toArray(new OrderSpecifier[0]))
                .limit(likeQuery.getPageable().getPageSize())
                .fetch();
    }

    private BooleanExpression userIdCondition(Long userId, List<Long> userIds) {
        if (userId != null) {
            return postEntity.userId.eq(userId);
        } else if (userIds != null) {
            if (userIds.isEmpty())
                return postEntity.id.isNull();
            return postEntity.userId.in(userIds);
        } else {
            return null;
        }
    }

    private BooleanExpression cursorCondition(Long postId, Sort sort) {
        if (postId == null) return null;

        Sort.Order order = sort.stream().findFirst().orElse(Sort.Order.desc("id"));

        if (order.isDescending()) {
            return postEntity.id.lt(postId);
        } else {
            return postEntity.id.gt(postId);
        }
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        sort.stream().forEach(order -> {
            PathBuilder orderByExpression = new PathBuilder(PostEntity.class, "postEntity");
            orders.add(new OrderSpecifier<>(order.isDescending() ? Order.DESC : Order.ASC,
                    orderByExpression.get(order.getProperty())));
        });
        return orders;
    }

}
