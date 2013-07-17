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
        
        if (modelClass.getParentClass() != null) {
        	String parentClassName = null;
            	
        	ModelClass parentClass = modelClass.getParentClass();
        	if (modelClass.getPackageName().equals(parentClass.getPackageName())) {
        		parentClassName = parentClass.getName();
        	} else {
        		parentClassName = parentClass.getFullyQualifiedName();
        	}
        	
          printClassOpening(modelClass.getName(), parentClassName, modelClass.getInterfaces(), fileWriter);
        } else {
          printClassOpening(modelClass.getName(), modelClass.getInterfaces(), fileWriter);
        }
        
        for (ModelMethod method : modelClass.getMethods()) {
          printLn(fileWriter);
          printMethod(method, fileWriter);
        }

        for (ModelProperty property : modelClass.getProperties()) {
          printLn(fileWriter);
          printField(property, fileWriter);
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

  private void printField(ModelProperty property, PrintWriter fileWriter) {
    for (String annotation : property.getAnnotations()) {
      fileWriter
        .append("  ")
        .append(annotation)
        .append('\n');
    }

    fileWriter.append("  ")
      .append(property.getModifiers())
      .append(" ")
      .append(property.getType())
      .append(" ")
      .append(property.getName());
    
    String defaultValue = property.getDefaultValue();
    if (defaultValue  != null) {
      fileWriter.append(" = ");
      fileWriter.append(defaultValue);
    }

    fileWriter.append(";\n");
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
