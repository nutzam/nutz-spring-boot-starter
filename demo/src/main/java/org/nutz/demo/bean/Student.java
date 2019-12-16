package org.nutz.demo.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.json.JsonField;
import org.nutz.spring.boot.service.entity.IdEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author kerbores(kerbores@gmail.com)
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("t_student")
@Comment("学生表")
@EqualsAndHashCode(callSuper = true)
public class Student extends IdEntity {

    private static final long serialVersionUID = 1L;

    @Name
    @Column("s_no")
    @Comment("学生学号")
    private String no;

    @Column("s_name")
    @Comment("学生年龄")
    private String name;

    @Column("s_score")
    @Comment("学分")
    @JsonField(dataFormat = "###,###,###,###,##0.00")
    private double score;
}
