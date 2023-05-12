package org.vacation.back.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.Search;
import org.vacation.back.domain.*;
import org.vacation.back.dto.common.MemberDTO;
import org.vacation.back.repository.child.CustomMemberRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements CustomMemberRepository {

        private final JPAQueryFactory queryFactory;




        public boolean exist(String username){
            QMember member = QMember.member;

            Integer fetch = queryFactory
                    .selectOne()
                    .from(member)
                    .where(member.username.eq(username))
                    .fetchFirst();

            return fetch != null;
        }

    @Override
    public Optional<Member> findByUsername(String username) {

           Member member = queryFactory.select(QMember.member)
                    .from(QMember.member)
                    .where(QMember.member.username.eq(username)
                    .and(QMember.member.memberStatus.eq(MemberStatus.ACTIVATION)))
                    .fetchOne();

          if(member != null) return Optional.of(member);
          else return Optional.empty();
    }

    @Override
    public Optional<Member> findByUserWithAll(String username) {

            QMember member = QMember.member;
            QPosition position = QPosition.position;
            QDepartment department = QDepartment.department;


            Member memberEntity = queryFactory.select(member)
                    .from(member)
                    .innerJoin(member.position,position).fetchJoin()
                    .innerJoin(member.department,department).fetchJoin()
                    .where(member.memberStatus.eq(MemberStatus.ACTIVATION),member.username.eq(username))
                    .fetchOne();

            if(memberEntity != null) return Optional.of(memberEntity);
            else return Optional.empty();
        }

    public boolean existsByEmployNumber(String number){
            QMember member = QMember.member;
            Integer fetch = queryFactory
                    .selectOne()
                    .from(member)
                    .where(member.employeeNumber.eq(number))
                    .fetchFirst();

            return fetch != null;
        }

        public String maxEmployeeNumber(){
            QMember member = QMember.member;

           String number = queryFactory
                  .select(member.employeeNumber.max())
                  .from(member)
                  .fetchOne();


           return  number;

        }


        /**
         * @apiNote ALL로 보내면 키워드 무시함
         *
         * */
    @Override
    public Page<Member> pageMember(Search text,String keyword ,Pageable pageable) {

            JPAQuery<Member> query = queryFactory.selectDistinct(QMember.member)
                    .from(QMember.member)
                    .innerJoin(QMember.member.position,QPosition.position).fetchJoin()
                    .innerJoin(QMember.member.department,QDepartment.department).fetchJoin()
                    .where(typeSearch(text,keyword),QMember.member.memberStatus.eq(MemberStatus.ACTIVATION))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize());

            List<Member> content = query.fetch();


        return PageableExecutionUtils.getPage(content,pageable,() ->  queryFactory
                .select(Wildcard.count)
                .from(QMember.member)
                .innerJoin(QMember.member.position,QPosition.position)
                .innerJoin(QMember.member.department,QDepartment.department)
                .where(typeSearch(text,keyword))
                .fetch().get(0));
    }

    @Override
    public Optional<Member> findByDetail(String username) {
        QMember member = QMember.member;
        QDepartment department = QDepartment.department;
        QPosition position = QPosition.position;

        Member memberEntity = queryFactory.select(member)
                .from(member)
                .innerJoin(member.position,position).fetchJoin()
                .innerJoin(member.department,department).fetchJoin()
                .where(member.username.eq(username).and(member.memberStatus.eq(MemberStatus.ACTIVATION)))
                .fetchOne();


        if(memberEntity != null) return Optional.of(memberEntity);
        else return Optional.empty();
    }

    @Override
    public Optional<Member> findMemberAndJoinBy(String username) {

        Member member = queryFactory.select(QMember.member)
                .from(QMember.member)
                .innerJoin(QMember.member.department,QDepartment.department).fetchJoin()
                .where(QMember.member.username.eq(username)).fetchOne();

        return Optional.of(member);
    }

    @Override
    public Optional<List<Member>> findDepartmentName(String departmentName) {

       List<Member> list =  queryFactory.select(QMember.member)
                .from(QMember.member)
                .where(QMember.member.department.departmentName.eq(departmentName))
                .fetch();

        return Optional.of(list);
    }

    @Override
    public Page<Member> findByDeactivated(Search text,String keyword ,Pageable pageable) {

        QMember member = QMember.member;
        JPAQuery<Member> query = queryFactory.selectDistinct(QMember.member)
                .from(QMember.member)
                .innerJoin(QMember.member.position,QPosition.position).fetchJoin()
                .innerJoin(QMember.member.department,QDepartment.department).fetchJoin()
                .where(typeSearch(text,keyword),QMember.member.memberStatus.eq(MemberStatus.WAITING))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());


        List<Member> content = query.fetch();


        return PageableExecutionUtils.getPage(content,pageable,() ->  queryFactory
                .select(Wildcard.count)
                .from(QMember.member)
                .innerJoin(QMember.member.position,QPosition.position)
                .innerJoin(QMember.member.department,QDepartment.department)
                .where(typeSearch(text,keyword),QMember.member.memberStatus.eq(MemberStatus.WAITING))
                .fetch().get(0));
    }

    @Override
    public Optional<Member> findByDeactiveMember(String username) {


        Member member = queryFactory.select(QMember.member)
                .from(QMember.member)
                .innerJoin(QMember.member.department,QDepartment.department).fetchJoin()
                .where(QMember.member.username.eq(username))
                .fetchOne();

        if(member != null) return Optional.of(member);
        return Optional.empty();
    }

    @Override
    public Optional<Member> removeByusername(String username) {
        QMember member = QMember.member;
        QDepartment department = QDepartment.department;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.or(member.memberStatus.eq(MemberStatus.WAITING));
        booleanBuilder.or(member.memberStatus.eq(MemberStatus.ACTIVATION));


        Member memberEntity = queryFactory.select(member)
                .from(member)
                .where(booleanBuilder,member.username.eq(username))
                .innerJoin(member.department,department).fetchJoin()
                .fetchOne();

        if(memberEntity != null) return Optional.of(memberEntity);
        return Optional.empty();
    }

    public Optional<List<String>> memberBydepartmentName(String departmentName){
        QMember member = QMember.member;

        List<String> list = queryFactory.select(member.username)
                .from(member)
                .where(member.department.departmentName.eq(departmentName)
                        .and(member.memberStatus.eq(MemberStatus.ACTIVATION)))
                .fetch();

        if(list != null) return Optional.of(list);
        else return Optional.empty();
    }


    private BooleanExpression typeSearch(Search text,String keyword){
        if(text == null && keyword == null) return null;

        QMember member = QMember.member;
        QPosition position = QPosition.position;
        QDepartment department = QDepartment.department;


        if(text.equals(Search.NAME)) {
            return member.name.contains(keyword);
        } else if (text.equals(Search.EMAIL)) {
            return member.email.contains(keyword);
        } else if (text.equals(Search.POSITION)) {
            return position.positionName.contains(keyword);
        } else if(text.equals(Search.DEPARTMENT)){
            return department.departmentName.contains(keyword);
        }

        return null;
    }
}
