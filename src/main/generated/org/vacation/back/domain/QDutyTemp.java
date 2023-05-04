package org.vacation.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDutyTemp is a Querydsl query type for DutyTemp
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDutyTemp extends EntityPathBase<DutyTemp> {

    private static final long serialVersionUID = 49946548L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDutyTemp dutyTemp = new QDutyTemp("dutyTemp");

    public final StringPath day = createString("day");

    public final BooleanPath deleted = createBoolean("deleted");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final EnumPath<org.vacation.back.common.DutyStatus> status = createEnum("status", org.vacation.back.common.DutyStatus.class);

    public QDutyTemp(String variable) {
        this(DutyTemp.class, forVariable(variable), INITS);
    }

    public QDutyTemp(Path<? extends DutyTemp> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDutyTemp(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDutyTemp(PathMetadata metadata, PathInits inits) {
        this(DutyTemp.class, metadata, inits);
    }

    public QDutyTemp(Class<? extends DutyTemp> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

