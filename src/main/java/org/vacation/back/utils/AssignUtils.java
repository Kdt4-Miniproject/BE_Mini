package org.vacation.back.utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
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



    @Scheduled(cron = "0 0 0 1 * ?") //
    public void assign() {
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue(); // 현재 몇월인지
        int lastDayOfMonth = now.lengthOfMonth(); // 마지막 날

        List<Integer> excludeList = new ArrayList<>(); // 제외할 사람들 넣을 배열
        List<Duty> duties = dutyRepository.findDutiesByStatusAndMonth(DutyStatus.OK, currentMonth); // 상태 OK인 현재달 사람들 조회
        for (int i = 0; i < duties.size(); i++) { // 승인된 당직 리스트 반복문
            Duty duty = duties.get(i); // 인덱스 별 당직
            if (duty != null) { // 비어있지 않을때
                LocalDate day = duty.getDay(); // 승인된 당직 날짜
                if (day != null) { // 승인된 당직 날짜
                    Integer dayOfMonth = day.getDayOfMonth(); // 승인된 당직 날짜중에 일만 가져오기
                    excludeList.add(dayOfMonth); // 1. excludeList에 신청한 사람들 날짜 넣기
                }
            }
        }

        Map<Integer, Member> map = new HashMap<>(); // 제외할 날짜 빼고 넣음
        for (int i = 1; i <= lastDayOfMonth; i++) { // 1~해당 월의 마지막 날까지 반복문
            if (!excludeList.contains(i)) { // i가 1일때 excludelist에 값이 없으면  ex) 신청 2, 3, 5     ////////    1 null 4 null 6 null
                map.put(i, null);
            }
        }

        List<Member> memberList = memberRepository.findAllActivation(MemberStatus.ACTIVATION); // 맴버 전체 조회
        List<Integer> availableKeys = getAvailableKeys(excludeList, lastDayOfMonth);
        Random random = new Random();

        for (int i = 0; i < availableKeys.size(); i++) { //제외 날짜 뺴고 값을 넣음
            Integer key = availableKeys.get(i);
            Member member = memberList.get(random.nextInt(memberList.size()));
            map.put(key, member); // [{1,randomMember}, {4,randomMember}, {6,randomMember}, {7,randomMember}, {8,randomMember}, .... , {31,randomMember}]
        }

        for (var entry : map.entrySet()) {
            LocalDate temp = now.withDayOfMonth(entry.getKey()); // key값이 일만 Int로 가지고 있어서 이 정보를 가지고 LocalDate로 바꿔 주기 위해서 사용하는 메서드(withDayOfMonth) spring이 가지고 있음
            Duty duty = Duty.builder() // duty 빌더
                    .day(temp)
                    .member(entry.getValue())
                    .status(DutyStatus.OK)
                    .build();

            dutyRepository.save(duty); // 마지막 duty save
        }
    }


    private static List<Integer> getAvailableKeys(List<Integer> excludeList, int lastDayOfMonth) { //제외날짜 없애는 메서드
        List<Integer> availableKeys = new ArrayList<>();
        for (int i = 1; i <= lastDayOfMonth; i++) {
                    if (!excludeList.contains(i)) {
                        availableKeys.add(i); //   [1, 4, 6, 7 ..., 31]
                    }
                }
                return availableKeys;
            }
        }