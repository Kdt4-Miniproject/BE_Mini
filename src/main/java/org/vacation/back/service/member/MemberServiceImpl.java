package org.vacation.back.service.member;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.MemberStatus;
import org.vacation.back.common.Search;
import org.vacation.back.domain.*;
import org.vacation.back.dto.common.MemberDTO;
import org.vacation.back.dto.common.UameAndPositionDTO;
import org.vacation.back.dto.request.member.*;
import org.vacation.back.dto.response.PageResponseDTO;
import org.vacation.back.dto.response.VacationResponseDTO;
import org.vacation.back.exception.*;
import org.vacation.back.repository.DepartmentRepository;
import org.vacation.back.repository.MemberRepository;
import org.vacation.back.repository.PositionRepository;
import org.vacation.back.service.FileService;
import org.vacation.back.service.MemberService;

import javax.swing.text.html.Option;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final PositionRepository positionRepository;

    private final DepartmentRepository departmentRepository;


    private final FileService fileService;

    @Transactional(readOnly = true)
    @Override
    public boolean exist(String username) {
        return memberRepository.exist(username);
    }

    @Override
    public boolean join(RegisterMemberDTO dto) {


           Integer year = Year.now().getValue() + 1;
           Integer temp  = Integer.parseInt(dto.getJoiningDay().substring(0,4));


           //TODO: Status 아닌 것들 조회해야함
           Position position = positionRepository.findById(dto.getPositionName()).orElseThrow(NotFoundPositionException::new);
           Department department = departmentRepository.findById(dto.getDepartmentName()).orElseThrow(NotFoundDepartmentException::new);



        try{

           int years = year - temp;

           dto.setYears(Integer.toString(years));

           int maxNumber = Integer.parseInt(memberRepository.maxEmployeeNumber());


           Member member = dto.toEntity();


           member.assignEmNumber(Integer.toString(maxNumber+1));
           member.changePassword(passwordEncoder.encode(dto.getPassword()));
           member.changeDepartment(department);
           member.changePosition(position);
           member.changeStatus(MemberStatus.WAITING);

           String fileName =  fileService.S3ToTemp(member.getFileName());
           member.changeFileName(fileName);
           Member data = memberRepository.save(member);
       }catch (Exception e){ //TODO: 커스텀 예외처리필요
           throw new JoinFailException();
       }


            return true;
    }

    @Transactional(readOnly = true)
    @Override
    public PageResponseDTO<?> pageMember(Search text, String keyword, int page, int size) {

        Pageable pageable = PageRequest.of(page,size);

        Page<Member> pageContent = memberRepository.pageMember(text,keyword,pageable);

        List<MemberDTO> memberDTOList = pageContent.getContent().stream().map(MemberDTO::toDTO).toList();

        PageResponseDTO<?> pageResponseDTO = PageResponseDTO.builder()
                .first(pageContent.isFirst())
                .last(pageContent.isLast())
                .content(memberDTOList)
                .total(pageContent.getTotalElements())
                .build();


        return pageResponseDTO;
    }



    @Transactional(readOnly = true)
    @Override
    public MemberDTO findByDetail(String username) {

        Member member = memberRepository.findByDetail(username).orElseThrow(MemberNotFoundException::new);


        MemberDTO memberDTO = MemberDTO.toDTO(member);

        return memberDTO;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean memberModify(MemberModifyDTO dto, String username) {

        Optional<Member> optional = memberRepository.findByUsername(username);
        boolean exist = optional.isPresent();

        if(exist){
            Member member = optional.get();
            if(!passwordEncoder.matches(dto.getOldPassword(),member.getPassword())) throw new PasswordNotMatchException("패스워드가 틀립니다.");
            //암호화된게 뒤로 와야함
            if(dto.getOldPassword() != null) member.changePassword(passwordEncoder.encode(dto.getNewPassword()));
            member.changeEmail(dto.getEmail());
            member.changePhoneNumber(dto.getPhoneNumber());
            member.changeFileName(dto.getFileName());
            member.changeName(dto.getName());

            return true;
        }
        else return false;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean adminModify(AdminMemberModifyRequest memberModifyRequest) {


        Integer year = Year.now().getValue() + 1;
        if(memberModifyRequest.getJoiningDay() != null){
            Integer temp  = Integer.parseInt(memberModifyRequest.getJoiningDay().substring(0,4));

            int years = year - temp;
            memberModifyRequest.setYears(Integer.toString(years));
        }

        Optional<Member> optional = memberRepository.findByUsername(memberModifyRequest.getUsername());

        if (optional.isEmpty()) {
            return false;
        } else {
           Member member =  optional.get();

           member.changeName(memberModifyRequest.getName());
           member.changeEmail(memberModifyRequest.getEmail());
           member.changePhoneNumber(memberModifyRequest.getPhoneNumber());
           member.changeBirthDate(memberModifyRequest.getBirthDate());
           member.changeJoiningDay(memberModifyRequest.getJoiningDay());
           member.changeYears(memberModifyRequest.getYears());
           member.changeFileName(memberModifyRequest.getFileName());
        }

        return true;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean adminRoleModify(RoleChangeRequest request) {

        String username = request.getUsername();

        Optional<Member> optional = memberRepository.findMemberAndJoinBy(username);

        if(optional.isEmpty()){
            return false;
        }else{
            Member member = optional.get();

            Department department = member.getDepartment();

            Optional<List<Member>> list = memberRepository.findDepartmentName(department.getDepartmentName());

            if(list.isPresent()){
                List<Member> changeDatas = list.get();

                if(!changeDatas.isEmpty()){
                    changeDatas.forEach(entity -> entity.changeRole(Role.STAFF));
                }
            }
             member.changeRole(request.getRole());
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponseDTO<?> deactivatedList(Search text, String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        Page<Member> pageContent = memberRepository.findByDeactivated(text,keyword,pageable);
        List<MemberDTO> memberDTOList = pageContent.getContent().stream().map(MemberDTO::toDTO).toList();

        PageResponseDTO<?> pageResponseDTO = PageResponseDTO.builder()
                .first(pageContent.isFirst())
                .last(pageContent.isLast())
                .content(memberDTOList)
                .total(pageContent.getTotalElements())
                .build();

        return pageResponseDTO;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean adminStatusModify(AdminStatusModifyRequest request) {

        String username = request.getUsername();

        Optional<Member> optional = memberRepository.findByDeactiveMember(username);

        if(optional.isPresent()){
           Member member =  optional.get();
           Department department = member.getDepartment();

           if(!member.getMemberStatus().equals(MemberStatus.ACTIVATION)
                   && request.getMemberStatus().equals(MemberStatus.ACTIVATION)) department.plusPersonal();

           if(member.getMemberStatus().equals(MemberStatus.ACTIVATION)
                   && request.getMemberStatus().equals(MemberStatus.WAITING)) department.minusPersonal();

           if(member.getMemberStatus().equals(MemberStatus.ACTIVATION)
                   && request.getMemberStatus().equals(MemberStatus.DEACTIVATION)) department.minusPersonal();

           member.changeStatus(request.getMemberStatus());
        }else{
            return false;
        }
        return true;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean changePwd(String username, PasswordModifyRequest passwordModifyRequest) {
        Optional<Member> optional = memberRepository.findByUsername(username);

        if(optional.isEmpty()){
            return false;
        }else{
          Member member = optional.get();

          if(!(passwordEncoder.matches(passwordModifyRequest.getOldPassword(),member.getPassword()))){
              throw new PasswordNotMatchException("패스워드가 틀렸습니다");
          }else{
              member.changePassword(passwordEncoder.encode(passwordModifyRequest.getNewPassword()));
              return true;
          }
        }
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public boolean memberRemove(String username) {
        Optional<Member> optional = memberRepository.removeByusername(username);

        if(optional.isPresent()){
            Member member = optional.get();
            Department department =member.getDepartment();

            if(member.getMemberStatus().equals(MemberStatus.ACTIVATION))  department.minusPersonal();

            member.changeStatus(MemberStatus.DEACTIVATION);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public PageResponseDTO<?> vacationFindByDepartment(Pageable pageable, String departmentName) {

        List<Member> memberList = memberRepository.memberBydepartmentName(departmentName)
                .orElseThrow(MemberNotFoundException::new);

        Page<Vacation> vacations = memberRepository.vacationByUsername(memberList.stream()
                        .map(Member::getUsername).toList(),pageable);

        List<VacationResponseDTO> content = vacations.getContent()
                .stream().map(vacation ->  VacationResponseDTO.toDTOv(vacation,vacation.getMember(),
                        departmentName, vacation.getMember().getPosition().getPositionName())).toList();

       PageResponseDTO<?> pageResponseDTO = PageResponseDTO.builder()
               .content(content)
               .last(vacations.isLast())
               .first(vacations.isFirst())
               .total(vacations.getTotalElements())
               .build();

        return pageResponseDTO;
    }


}
