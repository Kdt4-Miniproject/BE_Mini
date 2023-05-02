package org.vacation.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVacationTemp is a Querydsl query type for VacationTemp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVacationTemp extends EntityPathBase<VacationTemp> {

    private static final long serialVersionUID = 1363388219L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVacationTemp vacationTemp = new QVacationTemp("vacationTemp");

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath end = createString("end");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath start = createString("start");

    public final EnumPath<org.vacation.back.common.VacationStatus> status = createEnum("status", org.vacation.back.common.VacationStatus.class);

    public final QVacation vacation;

    public QVacationTemp(String variable) {
        this(VacationTemp.class, forVariable(variable), INITS);
    }

    public QVacationTemp(Path<? extends VacationTemp> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVacationTemp(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVacationTemp(PathMetadata metadata, PathInits inits) {
        this(VacationTemp.class, metadata, inits);
    }

    public QVacationTemp(Class<? extends VacationTemp> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.vacation = inits.isInitialized("vacation") ? new QVacation(forProperty("vacation"), inits.get("vacation")) : null;
    }

}

