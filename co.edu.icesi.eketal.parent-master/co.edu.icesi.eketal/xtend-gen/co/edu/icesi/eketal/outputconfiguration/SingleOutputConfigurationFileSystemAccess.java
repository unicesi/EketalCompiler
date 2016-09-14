package co.edu.icesi.eketal.outputconfiguration;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IFileSystemAccessExtension;
import org.eclipse.xtext.generator.IFileSystemAccessExtension2;

/**
 * @author Miguel Jiménez - Initial contribution and API
 */
@SuppressWarnings("all")
public class SingleOutputConfigurationFileSystemAccess implements IFileSystemAccess, IFileSystemAccessExtension, IFileSystemAccessExtension2 {
  protected IFileSystemAccess fsa;
  
  protected String outputConfigurationName;
  
  public SingleOutputConfigurationFileSystemAccess(final IFileSystemAccess fsa, final String outputConfigurationName) {
    this.fsa = fsa;
    this.outputConfigurationName = outputConfigurationName;
  }
  
  @Override
  public void generateFile(final String fileName, final CharSequence contents) {
    this.fsa.generateFile(fileName, this.outputConfigurationName, contents);
  }
  
  @Override
  public void generateFile(final String fileName, final String outputConfiguration, final CharSequence contents) {
    this.fsa.generateFile(fileName, this.outputConfigurationName, contents);
  }
  
  @Override
  public void deleteFile(final String fileName) {
    this.deleteFile(fileName, this.outputConfigurationName);
  }
  
  @Override
  public void deleteFile(final String fileName, final String ignoredOutputConfigurationName) {
    ((IFileSystemAccessExtension) this.fsa).deleteFile(fileName, this.outputConfigurationName);
  }
  
  @Override
  public URI getURI(final String fileName, final String outputConfiguration) {
    return ((IFileSystemAccessExtension2) this.fsa).getURI(fileName, this.outputConfigurationName);
  }
  
  @Override
  public URI getURI(final String fileName) {
    return ((IFileSystemAccessExtension2) this.fsa).getURI(fileName, this.outputConfigurationName);
  }
}
