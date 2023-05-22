package projects3.service;



import projects3.dao.Project3Dao;
import projects3.entity.Project;




public class Project3Service {
	private static Project3Dao project3Dao = new Project3Dao();

	public static Project addProject(Project project) {
		return project3Dao.insertProject(project);
		//Calls the method "insertProject" on the "projectDao" method
		
	}
}	
	
