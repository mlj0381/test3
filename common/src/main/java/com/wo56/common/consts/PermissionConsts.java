package com.wo56.common.consts;

/**
 * 平台权限枚举类型
 * @author zhouchao
 *
 */
public class PermissionConsts {

	/************************* 权限/角色/实体/菜单 **Start ***********************/
	public static class GrantConstant {
		public static final String STATE = "state";
		public static final int STATE_0 = 0;// 
		public static final int STATE_1 = 1;
		public static final String CODE_TYPE_GRANT_STATE = "GRANT_STATE";

		public static final String OBJECT_TYPE = "objectType";
		public static final String OBJECT_ID = "objectId";
		
		public static final String SYS_OBJECT_GRANT_CACHE_KEY = "allObjectGrant";// sys_object_grant表数据全量缓存KEY
		public static final String SYS_ROLE_CACHE_KEY = "allRole";// sys_role表数据全量缓存KEY
		public static final String SYS_ENTITY_CACHE_KEY = "allRole";// sys_entity表数据全量缓存KEY
		public static final String SYS_ROLE_OPER_REL_CACHE_KEY = "allRoleOperRel";// sys_role_oper_rel表数据全量缓存KEY
		public static final String SYS_MENU = "allMenu"; // sys_menu 表数据全量缓存key
		
		public static final String SYS_TENANT_DEF = "sysTenantDef"; // 租户信息
		
		public static final String SYS_ROLE_GRANT_CACHE_KEY = "roleGrant";// 缓存sys_role_grant表的数据

		public static final String CODE_TYPE_SYS_OBJECT_GRANT_OBJECT_TYPE = "SYS_OBJECT_GRANT@OBJECT_TYPE";
		public static final int OBJECT_TYPE_1 = 1;// 角色
		public static final int OBJECT_TYPE_2 = 2;// 组织
		public static final int OBJECT_TYPE_3 = 3;// 操作员
		
		public static final String CODE_TYPE_SYS_OBJECT_GRANT_IS_PERMIT = "SYS_OBJECT_GRANT@IS_PERMIT";
		public static final int IS_PERMIT_0 = 0;// 禁止
		public static final int IS_PERMIT_1 = 1;// 允许
		
		public static final String CODE_TYPE_SYS_ENTITY_ENTITY_TYPE = "SYS_ENTITY@ENTITY_TYPE";
		public static final int SYS_ENTITY_ENTITY_TYPE_MENU = 1;// 菜单权限
		public static final int SYS_ENTITY_ENTITY_TYPE_OPERATE = 2;// 实体权限
		public static final int SYS_ENTITY_ENTITY_TYPE_WLPT_MENU = 3;// 平台菜单权限
		public static final int SYS_ENTITY_ENTITY_TYPE_WLPT_AJAX = 4;// 平台请求权限
		
		public static final String SYS_URL="SYS_URL";
	}

	public static class EntityIdConstant {
	}

	public static class SysRoleConstant {
	}
	
	/************************* 权限/角色/实体/菜单 **End ***********************/

}
