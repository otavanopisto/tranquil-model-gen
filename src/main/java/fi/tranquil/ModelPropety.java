package fi.tranquil;

/**
 * Property of {@link ModelClass}
 */
public class ModelPropety {

  public ModelPropety(String modifiers, String type, String name, String defaultValue, boolean addGetter, boolean addSetter) {
    this.modifiers = modifiers;
    this.type = type;
    this.name = name;
    this.addGetter = addGetter;
    this.addSetter = addSetter;
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
  
  public boolean getAddGetter() {
    return addGetter;
  }
  
  public boolean getAddSetter() {
    return addSetter;
  }

  private String modifiers;
  private boolean addGetter;
  private boolean addSetter;
  private String type;
  private String name;
  private String defaultValue;
}