package org.vacation.back.service;

import org.vacation.back.dto.common.PositionDTO;
import org.vacation.back.dto.request.Position.PositionModifyDTO;
import org.vacation.back.dto.request.Position.PositionSaveDTO;

import java.util.List;

public interface PositionService {

    boolean positionSave(PositionSaveDTO dto);

    PositionDTO positionDetail(String id);

    List<PositionDTO> positionList();

    PositionDTO positionModify(String id, PositionModifyDTO dto);

    boolean positionDelete(String id);
}
