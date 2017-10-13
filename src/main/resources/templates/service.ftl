package ${packageName}.service;

import java.util.List;

import ${packageName}.dto.${className};
import ${packageName}.dto.PageInfo;

/**
 * @author code gen
 *
 */
public interface ${className}Service {
	
	${className} get(Object id);

	int delete(Object id);

	int update(${className} entity);

	void insert(${className} entity);

	Number save(${className} entity);

	PageInfo<${className}> getPageInfo(${className} entity, int start, int pageSize);

}
