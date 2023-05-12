package org.vacation.back.service.position;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vacation.back.common.PositionStatus;
import org.vacation.back.domain.Position;
import org.vacation.back.dto.common.PositionDTO;
import org.vacation.back.dto.request.Position.PositionModifyDTO;
import org.vacation.back.dto.request.Position.PositionSaveDTO;
import org.vacation.back.exception.CommonException;
import org.vacation.back.exception.ErrorCode;
import org.vacation.back.repository.PositionRepository;
import org.vacation.back.service.PositionService;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Transactional
    public boolean positionSave(PositionSaveDTO dto) {
        try {
            positionRepository.save(dto.toEntity());
        } catch (Exception e) {
            throw new CommonException(ErrorCode.DUPLICATED_MEMBER_NAME, "입력값을 확인해 주세요");
        }
        return true;
    }


    public PositionDTO positionDetail(String id) {
        Position dtoPS = positionRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.DUPLICATED_MEMBER_NAME, "없는 부서입니다.(조회)"));
        return new PositionDTO(dtoPS);
    }


    public List<PositionDTO> positionList() {
        List<Position> positionList = positionRepository.findAll();
        List<PositionDTO> positionDTOList = new ArrayList<>();
        for (Position position : positionList) {
            positionDTOList.add(new PositionDTO(position));
        }
        return positionDTOList;
    }

    @Transactional
    public PositionDTO positionModify(String id, PositionModifyDTO dto) {
        Position positionPS = positionRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.DUPLICATED_MEMBER_NAME, "없는 부서입니다(수정)"));
        positionPS.modify(dto.getVacation());
        return new PositionDTO(positionPS);
    }

    @Transactional
    public boolean positionDelete(String id) {
        Position positionPS = positionRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.DUPLICATED_MEMBER_NAME, "없는 부서입니다(삭제)"));
        positionPS.setStatus(PositionStatus.DEACTIVATION);
        return true;
    }
}
