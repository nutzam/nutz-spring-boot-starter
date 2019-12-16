/*
test.sql 
 */
SELECT 12 ;
/**
 * 测试一下sql模版
 */
/*
test.tpl
*/
SELECT  
<% if(vars.a > 5){ %>
	$a
<% }else{ %>
	@b
<% } %>