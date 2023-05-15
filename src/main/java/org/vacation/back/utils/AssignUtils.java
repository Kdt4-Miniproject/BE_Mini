package org.vacation.back.utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.vacation.back.common.DutyStatus;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.domain.Duty;
import org.vacation.back.domain.Member;
import org.vacation.back.repository.DutyRepository;
import org.vacation.back.repository.MemberRepository;
import java.time.LocalDate;
import java.util.*;


@Component
@Slf4j
@RequiredArgsConstructor
public class AssignUtils {

    private final DutyRepository dutyRepository;

    private final MemberRepository memberRepository;

    /**
     * 매달 1일에 해당 메소드 실행된다.
     */
    public void assign() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue(); // 현재 몇월인지
        int lastDayOfMonth = now.lengthOfMonth(); // 마지막 날

        List<Integer> excludeList = new ArrayList<>(); // 제외할 사람들 넣을 배열
        List<Duty> duties = dutyRepository.findDutiesByStatusAndMonth(DutyStatus.OK, currentMonth); // 상태 OK인 현재달 사람들 조회
        for (int i = 0; i < duties.size(); i++) {
            Duty duty = duties.get(i);
            if (duty != null) {
                LocalDate day = duty.getDay();
                if (day != null) {
                    Integer dayOfMonth = day.getDayOfMonth();
                    excludeList.add(dayOfMonth);
                }
            }
        }

        Map<Integer, Member> map = new HashMap<>(); // 제외할 날짜 빼고 넣음
        for (int i = 1; i <= lastDayOfMonth; i++) {
            if (!excludeList.contains(i)) {
                map.put(i, null);
            }
        }

        List<Member> memberList = memberRepository.findAllActivation(MemberStatus.ACTIVATION); // 맴버 전체 조회
        List<Integer> availableKeys = getAvailableKeys(excludeList, lastDayOfMonth);
        Random random = new Random();

        for (int i = 0; i < availableKeys.size(); i++) { //제외 날짜 뺴고 값을 넣음
            Integer key = availableKeys.get(i);
            Member member = memberList.get(random.nextInt(memberList.size()));
            map.put(key, member);
        }

        for (var entry : map.entrySet()) {
            LocalDate temp = now.withDayOfMonth(entry.getKey());
            Duty duty = Duty.builder()
                    .day(temp)
                    .member(entry.getValue())
                    .status(DutyStatus.OK)
                    .build();

            dutyRepository.save(duty);
        }
    }


    private static List<Integer> getAvailableKeys(List<Integer> excludeList, int lastDayOfMonth) { //제외날짜 없애는 메서드
        List<Integer> availableKeys = new ArrayList<>();
        for (int i = 1; i <= lastDayOfMonth; i++) {
                    if (!excludeList.contains(i)) {
                        availableKeys.add(i);
                    }
                }
                return availableKeys;
            }
        }