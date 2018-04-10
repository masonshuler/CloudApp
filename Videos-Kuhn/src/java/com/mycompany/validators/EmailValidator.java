package com.mycompany.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
















@FacesValidator("emailValidator")
public class EmailValidator
  implements Validator
{
  public EmailValidator() {}
  
  public void validate(FacesContext context, UIComponent component, Object value)
    throws ValidatorException
  {
    String email = (String)value;
    
    if ((email == null) || (email.isEmpty()))
    {

      return;
    }
    


















    String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    

    if (!email.matches(regex)) {
      throw new ValidatorException(new FacesMessage("Please Enter a Valid Email Address!"));
    }
  }
}