package com.mycompany.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
















@FacesValidator("passwordValidator")
public class PasswordValidator
  implements Validator
{
  public PasswordValidator() {}
  
  public void validate(FacesContext context, UIComponent component, Object value)
    throws ValidatorException
  {
    String enteredPassword = (String)value;
    
    if ((enteredPassword == null) || (enteredPassword.isEmpty()))
    {

      return;
    }
    




    int pwdLength = enteredPassword.length();
    for (int i = 0; i < pwdLength; i++) {
      if (Character.isWhitespace(enteredPassword.charAt(i)))
      {
        throw new ValidatorException(new FacesMessage("Password cannot contain a whitespace!"));
      }
    }
    












    String regex = "^(?=.{8,32})(?=.*[!@#$%^&*()])(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).*$";
    
    if (!enteredPassword.matches(regex)) {
      throw new ValidatorException(new FacesMessage("The password must be minimum 8 and maximum 32 characters long, contain at least one special character above the numbers on the keyboard, contain at least one uppercase letter, contain at least one lowercase letter, and contain at least one digit 0 to 9."));
    }
  }
}