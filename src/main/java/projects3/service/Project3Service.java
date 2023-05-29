package projects3.service;



import java.util.List;
import java.util.NoSuchElementException;
import projects3.dao.Project3Dao;
import projects3.entity.Project;
import projects3.exception.DbException;



public class Project3Service {
	private static Project3Dao project3Dao = new Project3Dao();

	public static Project addProject(Project project) {
		return project3Dao.insertProject(project);
		
		
	}

	public static List<Project> fetchAllProjects() {
		return project3Dao.fetchAllProjects();
	}
	/*It returns a Project object and takes an Integer projectId as a parameter*/
	public static Project fetchProjectById(Integer projectId) {
		return project3Dao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException(
				"Project with project ID=" + projectId + " does not exist"));			
	}

	public static void modifyProjectDetails(Project project) {
		if(!project3Dao.modifyProjectDetails(project)) {
			throw new DbException("Project with ID=" + project.getProjectId() + " does not exist.");
		}
		
	}

	public void deleteProject(Integer projectId) {
		if(!project3Dao.deleteProject(projectId)) {
			throw new DbException("Project with ID=" + projectId + " does not exist.");
		}
		
	}
}	
	
