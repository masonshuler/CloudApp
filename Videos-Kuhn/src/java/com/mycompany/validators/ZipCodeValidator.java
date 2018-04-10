package com.mycompany.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
















@FacesValidator("zipCodeValidator")
public class ZipCodeValidator
  implements Validator
{
  public ZipCodeValidator() {}
  
  public void validate(FacesContext context, UIComponent component, Object value)
    throws ValidatorException
  {
    String zipcode = (String)value;
    
    if ((zipcode == null) || (zipcode.isEmpty()))
    {

      return;
    }
    













    String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
    
    if (!zipcode.matches(regex)) {
      throw new ValidatorException(new FacesMessage("The U.S. Postal ZIP code can consist of only 5 digits or 5 digits, hyphen, and 4 digits!"));
    }
  }
}