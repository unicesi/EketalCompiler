package co.edu.icesi.eketal.outputconfiguration;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;

@SuppressWarnings("all")
public class OutputConfigurationAdapter implements Adapter {
  private String outputConfigurationName;
  
  public OutputConfigurationAdapter(final String outputConfigurationName) {
    this.outputConfigurationName = outputConfigurationName;
  }
  
  public String getOutputConfigurationName() {
    return this.outputConfigurationName;
  }
  
  @Override
  public void notifyChanged(final Notification notification) {
  }
  
  @Override
  public Notifier getTarget() {
    return null;
  }
  
  @Override
  public void setTarget(final Notifier newTarget) {
  }
  
  @Override
  public boolean isAdapterForType(final Object type) {
    return false;
  }
}
