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

  public void addProperty(String type, String name) {
    addProperty("private", type, name, true, true);
  }
  
  public void addProperty(String type, String name, String defaultValue) {
    addProperty("private", type, name, defaultValue, true, true);
  }

  public void addProperty(String modifiers, String type, String name, boolean addGetter, boolean addSetter) {
    addProperty(modifiers, type, name, null, addGetter, addSetter);
  }

  public void addProperty(String modifiers, String type, String name, String defaultValue, boolean addGetter, boolean addSetter) {
    propeties.add(new ModelPropety(modifiers, type, name, defaultValue, addGetter, addSetter));
  }
  
  public List<ModelPropety> getPropeties() {
    return propeties;
  }
  
  public void addMethod(String modifiers, String returnType, String name, String parameters, String body) {
    addMethod(new ModelMethod(modifiers, returnType, name, parameters, body));
  }
  
  public void addMethod(ModelMethod method) {
    this.methods.add(method);
  }

  public void addConstructor(String modifiers, String body, String parameters) {
    this.addMethod(modifiers, null, getName(), parameters, body);
  }

  public List<ModelMethod> getMethods() {
    return methods;
  }

  public String getFullyQualifiedName() {
    return getPackageName() + '.' + getName();
  }
  
  private String packageName;
  
  private String name;
  
  private ModelClass parentClass;
  
  private List<String> imports = new ArrayList<String>();

  private List<String> classAnnotations = new ArrayList<String>();
  
  private List<String> interfaces = new ArrayList<String>();
  
  private List<ModelPropety> propeties = new ArrayList<ModelPropety>();
  
  private List<ModelMethod> methods = new ArrayList<ModelMethod>();
}
