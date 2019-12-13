package org.nutz.demo.bean;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;
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
@EqualsAndHashCode(callSuper = false, of = "no")
public class Student extends IdEntity {

	@Name
	@Column("s_no")
	@Comment("学生学号")
	String no;

	@Column("s_name")
	@Comment("学生年龄")
	String name;
}
