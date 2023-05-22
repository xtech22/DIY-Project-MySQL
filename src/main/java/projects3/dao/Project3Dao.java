package projects3.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import projects3.entity.Project;
import projects3.exception.DbException;
import provided.util.DaoBase;



@SuppressWarnings("unused")
public class Project3Dao extends DaoBase {

	
	private final String CATEGORY_TABLE = "category";
	private final String MATERIAL_TABLE = "material";
	private final String PROJECT_TABLE = "project";
	private final String PROJECT_CATEGORY_TABLE = "project_category";
	private final String STEP_TABLE = "step";
	
	
	
	
	public Project insertProject(Project project) {
		//Saves project details. SQL statement is used to insert values in the "Project" object. "?'s" are placeholder values.
		String sql = ""
				+ "INSERT INTO " + PROJECT_TABLE + " "
				+ "(project_name, estimated_hours, actual_hours, difficulty, notes) "
				+ "VALUES "
				+ "(?, ?, ?, ?, ?)";

		//Obtains connection and starts transaction
		try(Connection conn = DbConnection.getConnection()) {
			startTransaction(conn);

			//Obtains a "PreparedStatement" object from the "Connection" object.
			try(PreparedStatement stmt = conn.prepareStatement(sql)) {

				//"setParameter" handles null values correctly and sets the project details as parameters.
				setParameter(stmt, 1, project.getProjectName(), String.class);
				setParameter(stmt, 2, project.getEstimatedHours(), BigDecimal.class);
				setParameter(stmt, 3, project.getActualHours(), BigDecimal.class);
				setParameter(stmt, 4, project.getDifficulty(), Integer.class);
				setParameter(stmt, 5, project.getNotes(), String.class);

				//Saves project details by performing insert by calling "executeUpdate" on the "PreparedStatement" object.
				stmt.executeUpdate();
				Integer projectId = getLastInsertId(conn, PROJECT_TABLE);

				//Commits transaction
				commitTransaction(conn);
				  
				//Obtains primary key "projectId" and returns the project.
				project.setProjectId(projectId);
				return project;
			}
			catch (Exception e) {
				rollbackTransaction(conn);
				throw new DbException(e);
			}
		} catch (SQLException e) {
			throw new DbException(e);
		}
		
	}
	}


