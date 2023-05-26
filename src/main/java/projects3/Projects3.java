/**
 * 
 */
package projects3;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projects3.entity.Project;
import projects3.exception.DbException;
import projects3.service.Project3Service;
import projects3.Projects3;

/**
 * @author alexhoward
 *
 */
public class Projects3 {

	private Scanner scanner = new Scanner(System.in);
	
	
	// @formatter:off
			private List<String> operations = List.of(
					"1) Add a project",
					"2) List projects",
					"3) Select a project"
					
					);
			// @formatter:on


			private Object curProject;
	
	public static void main(String[] args) {
		new Projects3().processUserSelections();
		
	}
			private void processUserSelections() {
				boolean done = false;
				
				while(!done) {
					try {
						int selection = getUserSelection();
						
						switch(selection) {
						
							case -1:
								done = exitMenu();
								break;
								
							case 1:
								createProject();
								break;
								
							case 2:
								listProjects();
								break;
								
							case 3:
								selectProject();
								break;	
								
								
						default:
							System.out.println("\n" + selection + " is not a valid selection. Try again.");
								break;
						}
							
					}
					catch(Exception e) {
						System.out.println("\nError: " + e + " Try again. "); 
					}
				} 
		
			}
			//this method calls the list of projects and allows us to select projects via project ID
			private void selectProject() {
				listProjects();
				Integer projectId = getIntInput("Enter a project ID to select a project");
				//unselects current project
				curProject = null;
				//Will throw an exception if current project ID is invalid
				curProject = Project3Service.fetchProjectById(projectId);
				
			}
			//creates a list of project named "projects" which will fetch all projects and print their project ID and name
			private void listProjects() {
				List<Project> projects = Project3Service.fetchAllProjects();
				
				System.out.println("\nProjects: ");
				
				projects.forEach(project -> System.out.println("   " + project.getProjectId()
				+ ": " + project.getProjectName()));
			}
			//Creates the project and its contents. it will set the parameters and prompts for the user to input.
			private void createProject() {
				String projectName = getStringInput("Enter the project name");
				BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
				BigDecimal actualHours = getDecimalInput("Enter the actual hours");
				Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
				String notes = getStringInput("Enter the project notes");
				
				Project project = new Project();
				
				project.setProjectName(projectName);
				project.setEstimatedHours(estimatedHours);
				project.setActualHours(actualHours);
				project.setDifficulty(difficulty);
				project.setNotes(notes);
				
				Project dbProject = Project3Service.addProject(project);
				System.out.println("You have successfully created project: " + dbProject);

				
			}
			
			
			private BigDecimal getDecimalInput(String prompt) {
				String input = getStringInput(prompt);

				if(Objects.isNull(input)) {
					return null;
				}
				try {
					return new BigDecimal(input).setScale(2);
				}
				catch(NumberFormatException e) {
					throw new DbException(input + " is not a valid decimal number.");
				}
			}
			
			private boolean exitMenu() {
				System.out.println("Exiting the menu.");
				return true;
			}
	
			private int getUserSelection() {
				printOperations();
				
				Integer input = getIntInput("\nEnter a menu selection");
				return Objects.isNull(input) ? -1 : input;
			}
			
			
			private Integer getIntInput(String prompt) {
				String input = getStringInput(prompt);

				if(Objects.isNull(input)) {
					return null;
				}
				
				try {
					return Integer.valueOf(input);
				}
				catch(NumberFormatException e) {
					throw new DbException(input + " is not a valid number.");
				}
			}
			private String getStringInput(String prompt) {
				System.out.print(prompt + ": ");

				String input = scanner.nextLine();
				
				return input.isBlank() ? null : input.trim();
			}
			private void printOperations() {
				System.out.println("\nThese are the available selections. Press the Enter key to quit:");
				
				operations.forEach(line -> System.out.println("   " + line));
			
				if(Objects.isNull(curProject)) {
					System.out.println("\nYou are not working with a project.");
				} else {
					System.out.println("\nYou are working with project: "+ curProject);
				}
				
			}
}