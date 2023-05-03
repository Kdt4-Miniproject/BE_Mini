package org.vacation.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPositionAndDepartment is a Querydsl query type for PositionAndDepartment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPositionAndDepartment extends EntityPathBase<PositionAndDepartment> {

    private static final long serialVersionUID = 487071286L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPositionAndDepartment positionAndDepartment = new QPositionAndDepartment("positionAndDepartment");

    public final QDepartment departmentName;

    public final QPosition positionName;

    public QPositionAndDepartment(String variable) {
        this(PositionAndDepartment.class, forVariable(variable), INITS);
    }

    public QPositionAndDepartment(Path<? extends PositionAndDepartment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPositionAndDepartment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPositionAndDepartment(PathMetadata metadata, PathInits inits) {
        this(PositionAndDepartment.class, metadata, inits);
    }

    public QPositionAndDepartment(Class<? extends PositionAndDepartment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.departmentName = inits.isInitialized("departmentName") ? new QDepartment(forProperty("departmentName")) : null;
        this.positionName = inits.isInitialized("positionName") ? new QPosition(forProperty("positionName")) : null;
    }

}

