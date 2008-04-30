/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blms.actionforms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

/**
 *
 * @author drsantos
 */
public class CreateUserForm extends org.apache.struts.action.ActionForm {
    
   private String firstName;

   private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

   public String getFirstName() {
       return firstName;
   }
   
   public void setFirstName(String firstName) {
       this.firstName = firstName;
   }
  
   /**
    *
    */
   public CreateUserForm() {
       super();
       // TODO Auto-generated constructor stub
   }

}
