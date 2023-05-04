package org.vacation.back.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.vacation.back.domain.Member;
import org.vacation.back.domain.QDepartment;
import org.vacation.back.domain.QDuty;
import org.vacation.back.domain.QMember;
import org.vacation.back.repository.child.CustomMemberRepository;

import javax.annotation.PostConstruct;
import java.util.List;


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

        public boolean existsByEmployNumber(String number){
            Integer fetch = queryFactory
                    .selectOne()
                    .from(member)
                    .where(member.employeeNumber.eq(number))
                    .fetchFirst();

            return fetch != null;
        }

        public Integer maxEmployeeNumber(){
           String number = queryFactory
                  .select(member.employeeNumber.max())
                  .from(member)
                  .fetchOne();

           return Integer.parseInt(number);

        }

        public Page<Member> memberPage(Pageable pageable,String username){
            List<Member> content = queryFactory
                    .select(member)
                    .from(member)
                    .innerJoin(QDuty.duty)
                    .fetchJoin()
                    .innerJoin(QDepartment.department)
                    .fetchJoin()
                    .fetchJoin()
                    .fetch();

            return null;

        }
}
