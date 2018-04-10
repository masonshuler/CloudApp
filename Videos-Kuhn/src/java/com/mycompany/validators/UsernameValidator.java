package com.mycompany.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
















@FacesValidator("usernameValidator")
public class UsernameValidator
  implements Validator
{
  public UsernameValidator() {}
  
  public void validate(FacesContext context, UIComponent component, Object value)
    throws ValidatorException
  {
    String username = (String)value;
    
    if ((username == null) || (username.isEmpty()))
    {

      return;
    }
    











    String regex = "^[_A-Za-z0-9.@-]{6,32}$";
    
    if (!username.matches(regex)) {
      throw new ValidatorException(new FacesMessage("The username must be minimum 6 and maximum 32 characters long and can contain underscore, uppercase letters, lowercase letters, digits 0 to 9, period, at sign @, and hyphen."));
    }
  }
}