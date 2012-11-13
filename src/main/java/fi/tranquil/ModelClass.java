package fi.tranquil;

import java.util.ArrayList;
import java.util.List;

/**
 * Model representing {@link TranquilModel}
 */
public class ModelClass {

  public ModelClass(String packageName, String name) {
    this(packageName, name, null);
  }
  
  public ModelClass(String packageName, String name, ModelClass parentClass) {
    this.packageName = packageName;
    this.name = name;
    this.parentClass = parentClass;
  }
  
  public String getName() {
    return name;
  }
  
  public String getPackageName() {
    return packageName;
  }

  public ModelClass getParentClass() {
    return parentClass;
  }
  
  public void setParentClass(ModelClass parentClass) {
    this.parentClass = parentClass;
  }
  
  public void addImport(String importString) {
    this.imports.add(importString);
  }
  
  public List<String> getImports() {
    return imports;
  }
  
  public void addClassAnnotation(String classAnnotation) {
    classAnnotations.add(classAnnotation);
  }
  
  public List<String> getClassAnnotations() {
    return classAnnotations;
  }

  public void addInterface(String implementedInterface) {
    interfaces.add(implementedInterface);
  }
  
  public List<String> getInterfaces() {
    return interfaces;
  }

  public ModelProperty addProperty(String type, String name) {
    return addProperty("private", type, name);
  }
  
  public ModelProperty addProperty(String modifiers, String type, String name) {
    return addProperty(modifiers, type, name, null);
  }

  public ModelProperty addProperty(String modifiers, String type, String name, String defaultValue) {
    ModelProperty modelPropety = new ModelProperty(modifiers, type, name, defaultValue);
    properties.add(modelPropety);
    return modelPropety;
  }
  
  public List<ModelProperty> getProperties() {
    return properties;
  }
  
  public ModelMethod addMethod(String modifiers, String returnType, String name, String parameters, String body) {
    return addMethod(new ModelMethod(modifiers, returnType, name, parameters, body));
  }
  
  public ModelMethod addMethod(ModelMethod method) {
    this.methods.add(method);
    return method;
  }

  public ModelMethod addConstructor(String modifiers, String body, String parameters) {
    return this.addMethod(modifiers, null, getName(), parameters, body);
  }
  
  public ModelMethod addGetter(ModelProperty property) {
    return addMethod("public", property.getType(), "get" + captitalize(property.getName()), null, "    return " + property.getName() + ";");
  }
  
  public ModelMethod addSetter(ModelProperty property) {
    StringBuilder bodyBuilder = new StringBuilder();
    bodyBuilder
      .append("    this.")
      .append(property.getName())
      .append(" = ")
      .append(property.getName())
      .append(";");
    
    return addMethod("public", "void", "set" + captitalize(property.getName()), property.getType() + " " + property.getName(), bodyBuilder.toString());
  }
  
  public List<ModelMethod> getMethods() {
    return methods;
  }

  public String getFullyQualifiedName() {
    return getPackageName() + '.' + getName();
  }
  
  private String captitalize(String string) {
    return Character.toUpperCase(string.charAt(0)) + string.substring(1);
  }
  
  private String packageName;
  
  private String name;
  
  private ModelClass parentClass;
  
  private List<String> imports = new ArrayList<String>();

  private List<String> classAnnotations = new ArrayList<String>();
  
  private List<String> interfaces = new ArrayList<String>();
  
  private List<ModelProperty> properties = new ArrayList<ModelProperty>();
  
  private List<ModelMethod> methods = new ArrayList<ModelMethod>();
}
