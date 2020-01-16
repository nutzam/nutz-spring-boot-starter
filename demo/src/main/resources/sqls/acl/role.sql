/*
list.all.roles.by.user.id 
*/
select
	r.*
from
	t_role r
left join t_user_role ur on
	r.id = ur.ur_role_id
where
	ur.ur_user_id = @userId
/*
get.role.infos.by.user.id 
*/
select
	r.*,
	ur.id is not null as `selected`
from
	t_role r
left join (
	select
		*
	from
		t_user_role
	where
		ur_user_id = @userId ) ur on
	r.id = ur.ur_role_id