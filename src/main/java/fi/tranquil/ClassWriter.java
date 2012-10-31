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
        
        printLn(fileWriter);
        printOriginalPropertyNames(fileWriter, modelClass.getOriginalPropetyNames());
        printLn(fileWriter);
        
        for (ModelPropety property : modelClass.getPropeties()) {
          printLn(fileWriter);
          printGetter(fileWriter, property.getName(), property.getType());
          printLn(fileWriter);
          printSetter(fileWriter, property.getName(), property.getType());
        }
    
        for (ModelPropety property : modelClass.getPropeties()) {
          printLn(fileWriter);
          printField(fileWriter, property.getName(), property.getType());
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

  private void printOriginalPropertyNames(PrintWriter fileWriter, List<String> originalPropertyName) {
    fileWriter.append("  public final static String[] properties = {");
    
    for (int i = 0, l = originalPropertyName.size(); i < l; i++) {
      fileWriter
        .append('"')
        .append(originalPropertyName.get(i))
        .append('"');

      if (i < l - 1) {
        fileWriter.append(',');
      }
    }
    
    fileWriter.append("};");
  }

  private void printGetter(PrintWriter fileWriter, String propertyName, String propertyType) {
    fileWriter.append("  public ")
      .append(propertyType)
      .append(" get")
      .append(captitalize(propertyName))
      .append("() {\n    return ")
      .append(propertyName)
      .append(";\n  }\n");
  }

  private void printSetter(PrintWriter fileWriter, String propertyName, String propertyType) {
    fileWriter.append("  public void set")
      .append(captitalize(propertyName))
      .append("(")
      .append(propertyType)
      .append(" ")
      .append(propertyName)
      .append(") {")
      .append("\n    this.")
      .append(propertyName)
      .append(" = ")
      .append(propertyName)
      .append(";\n  }\n");
  }

  private void printField(PrintWriter fileWriter, String propertyName, String propertyType) {
    fileWriter.append("  private ")
      .append(propertyType)
      .append(" ")
      .append(propertyName)
      .append(";\n");
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
