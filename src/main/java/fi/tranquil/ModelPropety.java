package fi.tranquil;

/**
 * Property of {@link ModelClass}
 */
public class ModelPropety {

  public ModelPropety(String type, String name) {
    this.type = type;
    this.name = name;
  }
  
  public String getName() {
    return name;
  }
  
  public String getType() {
    return type;
  }

  private String type;
  private String name;
}