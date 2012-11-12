package fi.tranquil;

import java.util.ArrayList;
import java.util.List;

public class ModelMethod {

  public ModelMethod(String modifiers, String returnType, String name, String parameters, String body) {
    this.modifiers = modifiers;
    this.returnType = returnType;
    this.name = name;
    this.parameters = parameters;
    this.body = body;
  }
  
  public String getName() {
    return name;
  }
  
  public String getParameters() {
    return parameters;
  }
  
  public String getModifiers() {
    return modifiers;
  }
  
  public String getReturnType() {
    return returnType;
  }
  
  public String getBody() {
    return body;
  }
  
  public void addAnnotation(String annotation) {
    annotations.add(annotation);
  }
  
  public List<String> getAnnotations() {
    return annotations;
  }
  
  private String modifiers;
  private String returnType;
  private String name;
  private String parameters;
  private String body;
  private List<String> annotations = new ArrayList<String>();
}
