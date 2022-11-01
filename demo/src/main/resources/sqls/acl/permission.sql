/*
list.direct.permissions.by.user.name
*/
SELECT
	p.*,
	up.id IS NOT NULL AS selected
FROM
	t_acl_permission p
LEFT JOIN (
		SELECT
			*
		FROM
			t_acl_user_permission
		WHERE
			up_user_name = @userName
	) up ON
	p.p_key_path = up.up_permission_key_path
/*
list.indirect.permissions.by.user.name 
*/
SELECT
	p.*,
	rp.id IS NOT NULL AS selected
FROM
	t_acl_permission p
LEFT JOIN (
		SELECT
			*
		FROM
			t_acl_role_permission
		WHERE
			rp_role_key IN (
				SELECT
					ur_role_key
				FROM
					t_acl_user_role
				WHERE
					ur_user_name = @userName
			)
	) rp ON
	p.p_key_path = rp.rp_permission_key_path
/*
list.permissions.by.role.key
*/
SELECT
	p.*,
	rp.id IS NOT NULL AS selected
FROM
	t_acl_permission p
LEFT JOIN (
		SELECT
			*
		FROM
			t_acl_role_permission
		WHERE
			rp_role_key = @roleKey
	) rp ON
	p.p_key_path = rp.rp_permission_key_path
