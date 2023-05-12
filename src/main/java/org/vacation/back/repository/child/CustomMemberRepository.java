package org.vacation.back.repository.child;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vacation.back.common.Search;
import org.vacation.back.domain.Member;
import org.vacation.back.dto.common.MemberDTO;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface CustomMemberRepository {
    /**
     * @apiNote 존재하면 true/ 없는 데이터면 false
     * */
    public boolean exist(String username);

    public Optional<Member> findByUsername(String username);

    public Optional<Member> findByUserWithAll(String username);

    public boolean existsByEmployNumber(String number);
    public String maxEmployeeNumber();

    public Page<Member> pageMember(Search text, String keyword , Pageable pageable);


    public Optional<Member> findByDetail(String username);

    public Optional<Member> findMemberAndJoinBy(String username);

    public Optional<List<Member>> findDepartmentName(String departmentName);


    public Page<Member> findByDeactivated(Search text,String keyword ,Pageable pageable);

    public Optional<Member> findByDeactiveMember(String username);


    public Optional<Member> removeByusername(String username);
}
