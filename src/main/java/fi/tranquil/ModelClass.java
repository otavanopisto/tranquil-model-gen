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
    propeties.add(new ModelPropety(type, name));
  }
  
  public List<ModelPropety> getPropeties() {
    return propeties;
  }

  public List<String> getOriginalPropetyNames() {
    return originalPropetyNames;
  }
  
  public void addOriginalPropertyName(String originalPropertyName) {
    this.originalPropetyNames.add(originalPropertyName);
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

  private List<String> originalPropetyNames = new ArrayList<String>();
}
