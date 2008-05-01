/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blms.actionforms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

/**
 *
 * @author drsantos
 */
public class CreateUserForm extends org.apache.struts.action.ActionForm {
    
   private String firstName;
   private String lastName;
   private String homePhone;
   private String workPhone;
   private String cellPhone;
   private FormFile picture;

    public FormFile getPicture() {
        return picture;
    }

    public void setPicture(FormFile picture) {
        this.picture = picture;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }
   private String email;

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

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
