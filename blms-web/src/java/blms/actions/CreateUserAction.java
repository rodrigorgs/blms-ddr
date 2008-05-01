/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blms.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForward;
import blms.actionforms.CreateUserForm;
import blms.facade.BlmsFacade;
import blms.struts.BlmsConfig;

/**
 *
 * @author drsantos
 */
public class CreateUserAction extends org.apache.struts.action.Action {
    
    /* forward name="success" path="" */
    private final static String SUCCESS = "success";
    private final static String ERROR = "error";
    private final static String OPERATION = "user creation";
    
    /**
     * This is the action called from the Struts framework.
     * @param mapping The ActionMapping used to select this instance.
     * @param form The optional ActionForm bean for this request.
     * @param request The HTTP Request we are processing.
     * @param response The HTTP Response we are processing.
     * @throws java.lang.Exception
     * @return
     */
    public ActionForward execute(ActionMapping mapping, ActionForm  form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        CreateUserForm userForm = (CreateUserForm)form;
        
        BlmsFacade facade = new BlmsFacade();
        try {
            facade.useDatabase(BlmsConfig.DBNAME);

            facade.createUser(userForm.getFirstName(), userForm.getLastName(), 
                    userForm.getHomePhone(), userForm.getWorkPhone(), userForm.getCellPhone(),
                    userForm.getEmail(), "");
        } catch (Throwable e) {
            request.setAttribute("exception", e);
            return mapping.findForward(ERROR);
        }
        finally {
            facade.closeDatabase();
        }
        request.setAttribute("operation", OPERATION);
        return mapping.findForward(SUCCESS);
    }
}