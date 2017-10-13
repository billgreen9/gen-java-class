package ${packageName}.service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${packageName}.dao.${className}Dao;
import ${packageName}.dto.${className};
import ${packageName}.dto.PageInfo;

/**
 * @author code gen
 *
 */
@Service("${className?uncap_first}Service")
public class ${className}ServiceImpl implements ${className}Service {

	@Autowired
	private ${className}Dao ${className?uncap_first}Dao;

	@Override
	public ${className} get(Object id) {
		return ${className?uncap_first}Dao.get(id);
	}

	@Override
	public int delete(Object id) {
		return ${className?uncap_first}Dao.delete(id);
	}

	@Override
	public int update(${className} entity) {
		return ${className?uncap_first}Dao.update(entity);
	}

	@Override
	public void insert(${className} entity) {
		${className?uncap_first}Dao.insert(entity);
	}

	@Override
	public Number save(${className} entity) {
		return ${className?uncap_first}Dao.save(entity);
	}

	@Override
	public PageInfo<${className}> getPageInfo(${className} entity, int pageNo, int pageSize) {
		PageInfo<${className}> pageInfo = new PageInfo<>(pageNo, pageSize);
		int start = (pageNo - 1) * pageSize;
		StringBuilder sqlTemp = new StringBuilder(" from ${tableName} where 1=1  ");
		List<Object> args = new ArrayList<Object>();

		<#list attrs as attr>
		<#if attr_index!=0>
		<#if attr.type='String'>
		if (isNotBlank(entity.get${attr.field?cap_first}())) {
			sql.append(" and ${attr.dbField} like ? ");
			args.add("%" + entity.get${attr.field?cap_first}() + "%");
		}
		<#else>
		if (entity.get${attr.field?cap_first}() != null) {
			sql.append(" and ${attr.dbField} = ? ");
			args.add(entity.get${attr.field?cap_first}());
		}
		</#if>
		</#if>
		</#list>
		
		int count = ${className?uncap_first}Dao.getCount("select count(1) " + sqlTemp.toString(), args.toArray());
		pageInfo.setCount(count);
		
		sqlTemp.append(" limit ?,?");
		args.add(start);
		args.add(pageSize);
		List<${className}> list = ${className?uncap_first}Dao.getList("select * " + sqlTemp.toString(), args.toArray());
		pageInfo.setData(list);
		return pageInfo;
	}

	

}
