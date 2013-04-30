package fi.tranquil;

import java.util.ArrayList;
import java.util.List;

/**
 * Property of {@link ModelClass}
 */
public class ModelProperty {

  public ModelProperty(String modifiers, String type, String name, String defaultValue) {
    this.modifiers = modifiers;
    this.type = type;
    this.name = name;
    this.defaultValue = defaultValue;
  }
  
  public String getName() {
    return name;
  }
  
  public String getType() {
    return type;
  }
  
  public String getDefaultValue() {
    return defaultValue;
  }
  
  public String getModifiers() {
    return modifiers;
  }

  public void addAnnotation(String annotation) {
    annotations.add(annotation);
  }
  
  public List<String> getAnnotations() {
    return annotations;
  }
  
  private List<String> annotations = new ArrayList<String>();
  private String modifiers;
  private String type;
  private String name;
  private String defaultValue;
}