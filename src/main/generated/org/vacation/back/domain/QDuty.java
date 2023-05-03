package org.vacation.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDuty is a Querydsl query type for Duty
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDuty extends EntityPathBase<Duty> {

    private static final long serialVersionUID = 1304184768L;

    public static final QDuty duty = new QDuty("duty");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath day = createString("day");

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final EnumPath<org.vacation.back.common.VacationStatus> status = createEnum("status", org.vacation.back.common.VacationStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QDuty(String variable) {
        super(Duty.class, forVariable(variable));
    }

    public QDuty(Path<? extends Duty> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDuty(PathMetadata metadata) {
        super(Duty.class, metadata);
    }

}

