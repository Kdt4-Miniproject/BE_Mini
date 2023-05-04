package org.vacation.back.repository.child;

public interface CustomMemberRepository {
    /**
     * @apiNote 존재하면 true/ 없는 데이터면 false
     * */
    public boolean exist(String username);

    public boolean existsByEmployNumber(String number);
    public Integer maxEmployeeNumber();
}
