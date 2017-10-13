/**
 * 
 */
package ${packageName}.dao.impl;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import ${packageName}.dto.${className};




/**
 * @author liuquan
 *
 */
@Repository("${className?uncap_first}Dao")
public class ${className}DaoImpl implements ${className}Dao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public ${className} get(Object id) {
		String sql = "select * from ${tableName} where id = ?";
		BeanPropertyRowMapper<${className}> rowMapper = new BeanPropertyRowMapper<${className}>(${className}.class);
		rowMapper.setPrimitivesDefaultedForNullValue(true);
		List<${className}> list = jdbcTemplate.query(sql.toString(), new Object[] { id }, rowMapper);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public int delete(Object id) {
		String sql = "delete from ${tableName} where id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	@Override
	public int update(${className} entity) {
		int rowsAffected;
		StringBuilder sql = new StringBuilder();
		sql.append("update ${tableName} set ");

		List<Object> params = new ArrayList<Object>();
		
		<#list attrs as attr>
		<#if attr_index!=0>
		<#if attr.type='String'>
		if (isNotBlank(entity.get${attr.field?cap_first}())) {
			sql.append("${attr.dbField} = ?,");
			params.add(entity.get${attr.field?cap_first}());
		}
		<#else>
		if (entity.get${attr.field?cap_first}() != null) {
			sql.append("${attr.dbField} = ?,");
			params.add(entity.get${attr.field?cap_first}());
		}
		</#if>
		</#if>
		</#list>

		sql.deleteCharAt(sql.length() - 1);
		sql.append(" where id =? ");
		params.add(entity.getId());

		rowsAffected = jdbcTemplate.update(sql.toString(), params.toArray());

		return rowsAffected;
	}
	
	@Override
	public void insert(${className} entity) {
		final String sql = "insert into ${tableName}(<#list attrs as attr><#if attr_index!=0><#if attr_has_next>${attr.dbField},<#else>${attr.dbField}</#if></#if></#list>) values(<#list attrs as attr><#if attr_index!=0><#if attr_has_next>?,<#else>?</#if></#if></#list>)";
		jdbcTemplate.update(sql,<#list attrs as attr><#if attr_index!=0><#if attr_has_next>entity.get${attr.field?cap_first}(),<#else>entity.get${attr.field?cap_first}()</#if></#if></#list>);
		return ;
	}
	
	@Override
	public Number save(${className} entity) {
		final String sql = "insert into ${tableName}(<#list attrs as attr><#if attr_index!=0><#if attr_has_next>${attr.dbField},<#else>${attr.dbField}</#if></#if></#list>) values(<#list attrs as attr><#if attr_index!=0><#if attr_has_next>?,<#else>?</#if></#if></#list>)";
		KeyHolder keyHolder = new GeneratedKeyHolder();

		jdbcTemplate.update(new PreparedStatementCreator() {
			public java.sql.PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
				int i = 0;
				java.sql.PreparedStatement ps = conn.prepareStatement(sql);
				ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				<#list attrs as attr>
				<#if attr_index!=0>
				<#if attr.type='Integer'>
				ps.setInt(++i, entity.get${attr.field?cap_first}());
				<#elseif attr.type='Date'>
				if(entity.get${attr.field?cap_first}() != null)
					ps.setTimestamp(++i, new Timestamp(entity.get${attr.field?cap_first}().getTime()));
				else
					ps.setNull(++i, Types.TIMESTAMP);
				<#elseif attr.type='Double'>
				ps.setDouble(++i, entity.get${attr.field?cap_first}());
				<#elseif attr.type='Float'>
				ps.setFloat(++i, entity.get${attr.field?cap_first}());
				<#else>
				ps.setString(++i, entity.get${attr.field?cap_first}());
				</#if>
				</#if>
				</#list>
				return ps;
			}
		}, keyHolder);
		
		return keyHolder.getKey();
	}
	

}
