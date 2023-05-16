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
import org.vacation.back.exception.NotFoundPositionException;
import org.vacation.back.exception.PositionDuplicateException;
import org.vacation.back.repository.PositionRepository;
import org.vacation.back.service.PositionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Transactional
    public boolean positionSave(PositionSaveDTO dto) {
        Optional<Position> positionOP = positionRepository.findById(dto.getPositionName());
        if (positionOP.isPresent()) {
            throw new PositionDuplicateException("이미 존재하는 직급입니다");
        } else {
            try {
                positionRepository.save(dto.toEntity());
                return true;
            } catch (Exception e) {
                throw new CommonException(ErrorCode.DTO_IS_NULL);
            }
        }
    }

    public PositionDTO positionDetail(String id) {
        try {
            Position dtoPS = positionRepository.findByPositionId(id);
            return new PositionDTO(dtoPS);
        } catch (Exception e) {
            throw new NotFoundPositionException();
        }
    }

    public List<PositionDTO> positionList() {
        List<Position> positionList = positionRepository.findByPositionAll();
        List<PositionDTO> positionDTOList = new ArrayList<>();
        for (Position position : positionList) {
            positionDTOList.add(new PositionDTO(position));
        }
        return positionDTOList;
    }

    @Transactional
    public PositionDTO positionModify(String id, PositionModifyDTO dto) {
        Position positionPS = positionRepository.findById(id).orElseThrow(NotFoundPositionException::new);
        positionPS.modify(dto.getVacation());
        return new PositionDTO(positionPS);
    }

    @Transactional
    public boolean positionDelete(String id) {
        Position positionPS = positionRepository.findById(id).orElseThrow(NotFoundPositionException::new);
        positionPS.setStatus(PositionStatus.DEACTIVATION);
        return true;
    }
}
