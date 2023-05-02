package org.vacation.back.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.vacation.back.domain.QMember;
import org.vacation.back.repository.child.CustomMemberRepository;

import javax.annotation.PostConstruct;


@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements CustomMemberRepository {

        private final JPAQueryFactory queryFactory;

        private  QMember member;
        @PostConstruct
        public void init(){
            member = QMember.member;
        }



        public boolean exist(String username){
            Integer fetch = queryFactory
                    .selectOne()
                    .from(member)
                    .where(member.username.eq(username))
                    .fetchFirst();

            return fetch != null;
        }
}
