/*
list.direct.permissions.by.user.name
*/
SELECT
	p.*,
	up.id IS NOT NULL AS selected 
FROM
	(
	SELECT
		b.*,
		m.m_key,
		m.m_name,
		m.m_description,
		m.m_parent_key 
	FROM
		t_acl_menu m
		RIGHT JOIN t_acl_button b ON m.m_key = b.b_menu_key 
		OR b.b_menu_key = NULL 
	) p
	LEFT JOIN ( SELECT * FROM t_acl_user_permission WHERE up_user_name = @userName ) up ON up.up_menu_key = p.m_key 
	AND up.up_button_key = p.b_key
/*
list.indirect.permissions.by.user.name 
*/
SELECT
	p.*,
	rp.id IS NOT NULL AS selected 
FROM
	(
	SELECT
		b.*,
		m.m_key,
		m.m_name,
		m.m_description,
		m.m_parent_key 
	FROM
		t_acl_menu m
		RIGHT JOIN t_acl_button b ON m.m_key = b.b_menu_key 
		OR b.b_menu_key = NULL 
	) p
	LEFT JOIN ( SELECT * FROM t_acl_role_permission WHERE rp_role_key IN ( SELECT ur_role_key FROM t_acl_user_role WHERE ur_user_name = @userName ) ) rp ON rp.rp_menu_key = p.m_key 
	AND rp.rp_button_key = p.b_key
/*
list.permissions.by.role.key
*/
SELECT
	p.*,
	rp.id IS NOT NULL AS selected 
FROM
	(
	SELECT
		b.*,
		m.m_key,
		m.m_name,
		m.m_description,
		m.m_parent_key 
	FROM
		t_acl_menu m
		RIGHT JOIN t_acl_button b ON m.m_key = b.b_menu_key 
		OR b.b_menu_key = NULL 
	) p
	LEFT JOIN ( SELECT * FROM t_acl_role_permission WHERE rp_role_key = @roleKey ) rp ON rp.rp_menu_key = p.m_key 
	AND rp.rp_button_key = p.b_key