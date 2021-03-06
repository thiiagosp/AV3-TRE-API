package controller;

import java.util.List;

import dao.EmployeeDao;
import dao.SessionDao;
import entity.Employee;
import entity.Session;

public class EmployeeController {

	EmployeeDao employeeDao = new EmployeeDao();
	SessionDao sessionDao = new SessionDao();
	
	
	/** Portugues: M�todo respons�vel pelo login
	 *  English: Method responsible for login
	 *  
	 * @param pCodeRegistration
	 * @param pPassword
	 * @return Employee
	 * @author Guilherme gui_dutralves@hotmail.com
	 * 
	 * log altera��o: 
	 * <Name></Name>
	 * <Date></Date>
	 * <Description></Description>	
	 */
	public Employee Login(String pCodeRegistration, String pPassword){
		
		//valida se o usu�rio � v�lido ou n�o, se n�o � v�lido, retorna NULL
		Employee employee = employeeDao.getEmployeeByCodeRegistrationAndPassword(pCodeRegistration, pPassword);
		
		//Se o Employee n�o � nulo, necess�rio verificar a session.
		if(employee != null){
			
			Session session = sessionDao.getActiveSessionByEmployee(employee);
			
			// se a session � null ent�o vai ser realizado o login inserindo uma nova session
			if(session == null){
				session = new Session(employee);
				sessionDao.insertNewSession(session);
				employee.setToken(session.getToken()); // atualiza o token
				employeeDao.updateEmployee(employee); // atualiza o Employee com o token
			}
		}
		return employee;
	}
	
}
