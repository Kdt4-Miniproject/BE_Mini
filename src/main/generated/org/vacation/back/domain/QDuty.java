package org.vacation.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDuty is a Querydsl query type for Duty
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDuty extends EntityPathBase<Duty> {

    private static final long serialVersionUID = 1304184768L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDuty duty = new QDuty("duty");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath day = createString("day");

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final EnumPath<org.vacation.back.common.VacationStatus> status = createEnum("status", org.vacation.back.common.VacationStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QDuty(String variable) {
        this(Duty.class, forVariable(variable), INITS);
    }

    public QDuty(Path<? extends Duty> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDuty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDuty(PathMetadata metadata, PathInits inits) {
        this(Duty.class, metadata, inits);
    }

    public QDuty(Class<? extends Duty> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

