package org.vacation.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -566229756L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath birthdate = createString("birthdate");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QDepartment department;

    public final ListPath<Duty, QDuty> duties = this.<Duty, QDuty>createList("duties", Duty.class, QDuty.class, PathInits.DIRECT2);

    public final StringPath email = createString("email");

    public final StringPath employeeNumber = createString("employeeNumber");

    public final StringPath fileName = createString("fileName");

    public final DatePath<java.time.LocalDate> joiningDay = createDate("joiningDay", java.time.LocalDate.class);

    public final EnumPath<org.vacation.back.common.MemberStatus> memberStatus = createEnum("memberStatus", org.vacation.back.common.MemberStatus.class);

    public final StringPath name = createString("name");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final QPosition position;

    public final EnumPath<Role> role = createEnum("role", Role.class);

    public final NumberPath<Integer> totalYears = createNumber("totalYears", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final StringPath username = createString("username");

    public final StringPath vacationLimit = createString("vacationLimit");

    public final ListPath<Vacation, QVacation> vacationTemps = this.<Vacation, QVacation>createList("vacationTemps", Vacation.class, QVacation.class, PathInits.DIRECT2);

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.department = inits.isInitialized("department") ? new QDepartment(forProperty("department")) : null;
        this.position = inits.isInitialized("position") ? new QPosition(forProperty("position")) : null;
    }

}

