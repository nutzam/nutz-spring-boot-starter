/*
list.direct.permissions.by.user.id 
*/
select
	up.up_action_key as `actionKey`,
	a.a_name as `actionName`,
	a.id as `actionId`,
	m.m_name as `moduleName`,
	up.up_module_key as `moduleKey`,
	m.m_description as `moduleDescription`,
	m.id as `moduleId`
from
	t_user_permission up
left join t_action a on
	a.a_key = up.up_action_key
left join t_module m on
	m.id = up.up_module_id
where
	up_user_id = @userId
/*
list.indirect.permissions.by.user.id 
*/
select
	rp.rp_action_key as `actionKey` ,
	a.a_name as `actionName`,
	a.id as `actionId`,
	m.m_name as `moduleName`,
	rp.rp_module_key as `moduleKey`,
	m.m_description as `moduleDescription`,
	m.id as `moduleId`
from
	t_role_permission rp
left join t_user_role ur on
	rp.rp_role_id = ur.ur_role_id
left join t_action a on
	a.a_key = rp.rp_action_key
left join t_module m on
	m.id = rp.rp_module_id
where
	ur.ur_user_id = @userId
/*
list.permissions.by.role.key
*/
SELECT
	p.*,
	rp.id IS NOT NULL AS `selected` 
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
/*
list.permissions.info.by.role.id.and.module.ids
*/
select
	r.*,
	rp.id is not null as `actionSelected`,
	rp.rp_module_id in ($moduleIds) as `moduleSelected`
from
	(
	select
		a.a_key as `actionKey`,
		a.a_name as `actionName`,
		a.id as `actionId`,
		m.m_name as `moduleName`,
		m.m_key as `moduleKey`,
		m.m_description as `moduleDescription`,
		m.id as `moduleId`
	from
		t_action a
	left join (
		select
			*
		from
			t_module ) m on
		a.a_module_id = m.id
		or a.a_module_id = 0 ) r
left join (
	select
		*
	from
		t_role_permission
	where
		rp_role_id = @roleId ) rp on
	rp.rp_module_id = r.moduleId
	and rp.rp_action_key = r.actionKey
/*
list.permissions.info.by.user.id.and.module.ids
*/
select
	r.*,
	up.id is not null as `actionSelected`,
	up.up_module_id in ($moduleIds) as `moduleSelected`
from
	(
	select
		a.a_key as `actionKey`,
		a.a_name as `actionName`,
		a.id as `actionId`,
		m.m_name as `moduleName`,
		m.m_key as `moduleKey`,
		m.m_description as `moduleDescription`,
		m.id as `moduleId`
	from
		t_action a
	left join (
		select
			*
		from
			t_module ) m on
		a.a_module_id = m.id
		or a.a_module_id = 0 ) r
left join (
	select
		*
	from
		t_user_permission
	where
		up_user_id = @userId ) up on
	up.up_module_id = r.moduleId
	and up.up_action_key = r.actionKey

	
	
	