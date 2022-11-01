/*
list.role.infos.by.user.name
*/
SELECT
	r.*,
	ur.id IS NOT NULL AS selected
FROM
	t_acl_role r
LEFT JOIN (
		SELECT
			*
		FROM
			t_acl_user_role
		WHERE
			ur_user_name = @userName
	) ur ON
	r.r_key = ur.ur_role_key