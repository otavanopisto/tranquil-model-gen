package fi.tranquil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.tools.JavaFileObject;

/**
 * Class for writing {@link ClassWriter} classes into files
 */
public class ClassWriter {

  public void writeClass(JavaFileObject file, ModelClass modelClass) throws IOException {
    OutputStream fileStream = openFileStream(file);
    try {
      PrintWriter fileWriter = openFileWriter(fileStream);
      try {
    
        printPackage(modelClass.getPackageName(), fileWriter);
        printLn(fileWriter);
        
        if (!modelClass.getImports().isEmpty()) {
          for (String importString : modelClass.getImports()) {
            printImport(fileWriter, importString);
          }
          printLn(fileWriter);
        }

        for (String classAnnotation : modelClass.getClassAnnotations()) {
          printClassAnnotation(fileWriter, classAnnotation);
        }
        
        if (modelClass.getParentClass() != null)
          printClassOpening(modelClass.getName(), modelClass.getParentClass().getFullyQualifiedName(), modelClass.getInterfaces(), fileWriter);
        else 
          printClassOpening(modelClass.getName(), modelClass.getInterfaces(), fileWriter);
        
        for (ModelMethod method : modelClass.getMethods()) {
          printLn(fileWriter);
          printMethod(method, fileWriter);
        }
        
        for (ModelPropety property : modelClass.getPropeties()) {
          if (property.getAddGetter()) {
            printLn(fileWriter);
            printGetter(fileWriter, property.getName(), property.getType());
          }

          if (property.getAddSetter()) {
            printLn(fileWriter);
            printSetter(fileWriter, property.getName(), property.getType());
          }
        }
    
        for (ModelPropety property : modelClass.getPropeties()) {
          printLn(fileWriter);
          printField(fileWriter, property.getModifiers(), property.getName(), property.getType(), property.getDefaultValue());
        }
    
        printClassClosing(fileWriter); 
        
      } finally {
        closeFileWriter(fileWriter);
      }
    } finally {
      closeFileStream(fileStream);
    }
  }

  private void printLn(PrintWriter fileWriter) {
    fileWriter.append('\n');
  }

  private void printPackage(String packageName, PrintWriter fileWriter) {
    fileWriter
      .append("package ")
      .append(packageName)
      .append(";\n");
  }

  private void printImport(PrintWriter fileWriter, String importString) {
    fileWriter
      .append("import ")
      .append(importString)
      .append(";\n");
  }

  private void printClassAnnotation(PrintWriter fileWriter, String classAnnotation) {
    fileWriter
      .append(classAnnotation)
      .append("\n");
  }

  private void printClassOpening(String className, List<String> interfaces, PrintWriter fileWriter) {
    printClassOpening(className, null, interfaces, fileWriter);
  }
  
  private void printClassOpening(String className, String parentClass, List<String> interfaces, PrintWriter fileWriter) {
    fileWriter
      .append("public class ")
      .append(className);
    
    if (parentClass != null) {
      fileWriter.append(" extends ");
      fileWriter.append(parentClass);
    }
    
    if (interfaces.size() > 0) {
      fileWriter.append(" implements ");
      for (int i = 0, l = interfaces.size(); i < l; i++) {
        String implementedInterface = interfaces.get(i);
        fileWriter.append(implementedInterface);
        if (i < (l - 1)) 
          fileWriter.append(" ,"); 
      } 
    }
    
    fileWriter.append(" {\n");
  }

  private void printClassClosing(PrintWriter fileWriter) {
    fileWriter.append("}\n");
  }

  private void printGetter(PrintWriter fileWriter, String propertyName, String propertyType) {
    printMethod(new ModelMethod("public", propertyType, "get" + captitalize(propertyName), null, "    return " + propertyName + ";"), fileWriter);
  }

  private void printSetter(PrintWriter fileWriter, String propertyName, String propertyType) {
    StringBuilder bodyBuilder = new StringBuilder();
    bodyBuilder
      .append("    this.")
      .append(propertyName)
      .append(" = ")
      .append(propertyName)
      .append(";");
    
    printMethod(new ModelMethod("public", "void", "set" + captitalize(propertyName), propertyType + " " + propertyName, bodyBuilder.toString()), fileWriter);
  }

  private void printMethod(ModelMethod method, PrintWriter fileWriter) {
    for (String annotation : method.getAnnotations()) {
      fileWriter
        .append("  ")
        .append(annotation)
        .append('\n');
    }

    fileWriter.append("  ");
    fileWriter.append(method.getModifiers());
    fileWriter.append(' ');
    if (method.getReturnType() != null) {
      fileWriter.append(method.getReturnType());
      fileWriter.append(' ');
    }
    
    fileWriter
      .append(method.getName())
      .append("(");
    
    if (method.getParameters() != null) {
      fileWriter.append(method.getParameters());
    }
    
    fileWriter
      .append(") {\n")
      .append(method.getBody())
      .append("\n  }\n");
  }

  private void printField(PrintWriter fileWriter, String modifiers, String propertyName, String propertyType, String defaultValue) {
    fileWriter.append("  ")
      .append(modifiers)
      .append(" ")
      .append(propertyType)
      .append(" ")
      .append(propertyName);
    
    if (defaultValue != null) {
      fileWriter.append(" = ");
      fileWriter.append(defaultValue);
    }

    fileWriter.append(";\n");
  }
  
  private String captitalize(String string) {
    return Character.toUpperCase(string.charAt(0)) + string.substring(1);
  }
  
  private OutputStream openFileStream(JavaFileObject file) throws IOException {
    return file.openOutputStream();
  }
  
  private PrintWriter openFileWriter(OutputStream fileStream) throws IOException {
    return new PrintWriter(fileStream);
  }
  
  private void closeFileStream(OutputStream fileStream) throws IOException {
    fileStream.flush();
    fileStream.close();
  }
  
  private void closeFileWriter(PrintWriter fileWriter) {
    fileWriter.flush();
    fileWriter.close();
  }

}
