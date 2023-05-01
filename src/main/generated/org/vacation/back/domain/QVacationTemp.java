package org.vacation.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVacationTemp is a Querydsl query type for VacationTemp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVacationTemp extends EntityPathBase<VacationTemp> {

    private static final long serialVersionUID = 1363388219L;

    public static final QVacationTemp vacationTemp = new QVacationTemp("vacationTemp");

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath end = createString("end");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath start = createString("start");

    public final EnumPath<org.vacation.back.common.VacationStatus> status = createEnum("status", org.vacation.back.common.VacationStatus.class);

    public final StringPath username = createString("username");

    public QVacationTemp(String variable) {
        super(VacationTemp.class, forVariable(variable));
    }

    public QVacationTemp(Path<? extends VacationTemp> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVacationTemp(PathMetadata metadata) {
        super(VacationTemp.class, metadata);
    }

}

