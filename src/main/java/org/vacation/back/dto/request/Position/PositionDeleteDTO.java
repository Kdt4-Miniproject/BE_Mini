package org.vacation.back.dto.request.Position;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionDeleteDTO {

    private Long id;

    private boolean deleted;
}
