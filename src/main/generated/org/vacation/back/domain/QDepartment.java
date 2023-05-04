package org.vacation.back.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDepartment is a Querydsl query type for Department
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDepartment extends EntityPathBase<Department> {

    private static final long serialVersionUID = -2020312900L;

    public static final QDepartment department = new QDepartment("department");

    public final StringPath departmentName = createString("departmentName");

    public final NumberPath<Integer> departmentPersonal = createNumber("departmentPersonal", Integer.class);

    public final ListPath<Member, QMember> memberList = this.<Member, QMember>createList("memberList", Member.class, QMember.class, PathInits.DIRECT2);

    public final ListPath<PositionAndDepartment, QPositionAndDepartment> positionAndDepartments = this.<PositionAndDepartment, QPositionAndDepartment>createList("positionAndDepartments", PositionAndDepartment.class, QPositionAndDepartment.class, PathInits.DIRECT2);

    public final EnumPath<org.vacation.back.common.DepartmentStatus> status = createEnum("status", org.vacation.back.common.DepartmentStatus.class);

    public final NumberPath<Integer> vacationLimit = createNumber("vacationLimit", Integer.class);

    public QDepartment(String variable) {
        super(Department.class, forVariable(variable));
    }

    public QDepartment(Path<? extends Department> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDepartment(PathMetadata metadata) {
        super(Department.class, metadata);
    }

}

