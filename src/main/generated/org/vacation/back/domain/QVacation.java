package org.vacation.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVacation is a Querydsl query type for Vacation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVacation extends EntityPathBase<Vacation> {

    private static final long serialVersionUID = 277654215L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QVacation vacation = new QVacation("vacation");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath end = createString("end");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath start = createString("start");

    public final EnumPath<org.vacation.back.common.VacationStatus> status = createEnum("status", org.vacation.back.common.VacationStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final QVacationTemp vacationTemp;

    public QVacation(String variable) {
        this(Vacation.class, forVariable(variable), INITS);
    }

    public QVacation(Path<? extends Vacation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QVacation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QVacation(PathMetadata metadata, PathInits inits) {
        this(Vacation.class, metadata, inits);
    }

    public QVacation(Class<? extends Vacation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.vacationTemp = inits.isInitialized("vacationTemp") ? new QVacationTemp(forProperty("vacationTemp"), inits.get("vacationTemp")) : null;
    }

}

