package com.fx.post.adapter.out.persistence.repository;

import com.fx.post.adapter.out.persistence.entity.CommentEntity;
import com.fx.post.adapter.out.persistence.entity.QCommentEntity;
import com.fx.post.domain.CommentInfo;
import com.fx.post.domain.CommentQuery;
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

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<CommentInfo> findComments(CommentQuery commentQuery) {

        if (commentQuery.getUserId() == null && commentQuery.getParentId() == null) {
            QCommentEntity child = new QCommentEntity("child");

            return jpaQueryFactory
                    .select(Projections.constructor(
                            CommentInfo.class,
                            commentEntity.id,
                            commentEntity.userId,
                            commentEntity.postId,
                            commentEntity.content,
                            commentEntity.createdAt,
                            commentEntity.parentId,
                            child.id.count().as("replyCount")
                    ))
                    .from(commentEntity)
                    .leftJoin(child).on(child.parentId.eq(commentEntity.id).and(child.isDeleted.eq(commentQuery.isDeleted())))
                    .where(
                            commentEntity.postId.eq(commentQuery.getPostId()),
                            commentEntity.parentId.isNull(),
                            cursorCondition(commentQuery.getCommentId(), commentQuery.getPageable().getSort()),
                            commentEntity.isDeleted.eq(commentQuery.isDeleted())
                    )
                    .groupBy(commentEntity.id, commentEntity.userId, commentEntity.content, commentEntity.createdAt)
                    .orderBy(getOrderSpecifier(commentQuery.getPageable().getSort()).toArray(new OrderSpecifier[0]))
                    .limit(commentQuery.getPageable().getPageSize())
                    .fetch();
        }
        else {
            return jpaQueryFactory
                    .select(Projections.constructor(
                            CommentInfo.class,
                            commentEntity.id,
                            commentEntity.userId,
                            commentEntity.postId,
                            commentEntity.content,
                            commentEntity.createdAt,
                            commentEntity.parentId
                    ))
                    .from(commentEntity)
                    .where(
                            userIdCondition(commentQuery.getUserId()),
                            postCondition(commentQuery.getPostId()),
                            parentCondition(commentQuery.getParentId()),
                            cursorCondition(commentQuery.getCommentId(), commentQuery.getPageable().getSort()),
                            commentEntity.isDeleted.eq(commentQuery.isDeleted())
                    )
                    .orderBy(getOrderSpecifier(commentQuery.getPageable().getSort()).toArray(new OrderSpecifier[0]))
                    .limit(commentQuery.getPageable().getPageSize())
                    .fetch();
        }
    }

    private BooleanExpression postCondition(Long postId) {
        return postId != null ? commentEntity.postId.eq(postId) : null;
    }

    private BooleanExpression parentCondition(Long parentId) {
        return parentId != null ? commentEntity.parentId.eq(parentId) : null;
    }

    private BooleanExpression userIdCondition(Long userId) {
        return userId != null ? commentEntity.userId.eq(userId) : null;
    }

    private BooleanExpression cursorCondition(Long commentId, Sort sort) {
        if (commentId == null) return null;

        Sort.Order order = sort.stream().findFirst().orElse(Sort.Order.asc("id"));

        if (order.isAscending()) {
            return commentEntity.id.gt(commentId);
        } else {
            return commentEntity.id.lt(commentId);
        }
    }

    private List<OrderSpecifier> getOrderSpecifier(Sort sort) {
        List<OrderSpecifier> orders = new ArrayList<>();
        sort.stream().forEach(order -> {
            PathBuilder orderByExpression = new PathBuilder(CommentEntity.class, "commentEntity");
            orders.add(new OrderSpecifier<>(order.isDescending() ? Order.DESC : Order.ASC,
                    orderByExpression.get(order.getProperty())));
        });
        return orders;
    }

}
