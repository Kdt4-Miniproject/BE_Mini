package org.vacation.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVacation is a Querydsl query type for Vacation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVacation extends EntityPathBase<Vacation> {

    private static final long serialVersionUID = 277654215L;

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

    public final StringPath username = createString("username");

    public QVacation(String variable) {
        super(Vacation.class, forVariable(variable));
    }

    public QVacation(Path<? extends Vacation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVacation(PathMetadata metadata) {
        super(Vacation.class, metadata);
    }

}

