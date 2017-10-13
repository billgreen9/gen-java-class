/**
 * 
 */
package ${packageName}.dao;

import ${packageName}.dto.${className};

/**
 * @author 
 *
 */
public interface ${className}Dao {

	${className} get(Object id);

	int delete(Object id);

	int update(${className} entity);

	/**
	 * save data and not return the data id
	 */
	void insert(${className} entity);

	/**
	 * save data and return the data id
	 */
	Number save(${className} entity);
	
}