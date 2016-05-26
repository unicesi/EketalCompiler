package co.edu.icesi.eketal.generator;

import co.edu.icesi.eketal.eketal.AndEvent;
import co.edu.icesi.eketal.eketal.Decl;
import co.edu.icesi.eketal.eketal.EvDecl;
import co.edu.icesi.eketal.eketal.EventClass;
import co.edu.icesi.eketal.eketal.EventExpression;
import co.edu.icesi.eketal.eketal.EventPredicate;
import co.edu.icesi.eketal.eketal.Group;
import co.edu.icesi.eketal.eketal.JVarD;
import co.edu.icesi.eketal.eketal.KindAttribute;
import co.edu.icesi.eketal.eketal.OrEvent;
import co.edu.icesi.eketal.eketal.Trigger;
import co.edu.icesi.eketal.eketal.UnaryEvent;
import co.edu.icesi.eketal.jvmmodel.EketalJvmModelInferrer;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.generator.IFileSystemAccess;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.xbase.XExpression;
import org.eclipse.xtext.xbase.impl.XStringLiteralImpl;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class EketalGenerator implements IGenerator {
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    InputOutput.<String>println("doGenerate");
    TreeIterator<EObject> _allContents = resource.getAllContents();
    Iterator<EventClass> _filter = Iterators.<EventClass>filter(_allContents, EventClass.class);
    final Procedure1<EventClass> _function = (EventClass it) -> {
      this.generateAspect(it, fsa);
    };
    IteratorExtensions.<EventClass>forEach(_filter, _function);
  }
  
  public void generateAspect(final EventClass modelo, final IFileSystemAccess fsa) {
    String packageName = "co.edu.icesi.eketal.aspects";
    String _name = modelo.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    String _prepareFileName = this.prepareFileName(("./" + packageName), _firstUpper);
    CharSequence _generate = this.generate(modelo, packageName);
    fsa.generateFile(_prepareFileName, IFileSystemAccess.DEFAULT_OUTPUT, _generate);
  }
  
  public String prepareFileName(final String packageName, final String fileName) {
    String _replaceAll = ((packageName + ".") + fileName).replaceAll("\\.", File.separator);
    return (_replaceAll + ".aj");
  }
  
  public CharSequence generate(final EventClass modelo, final String packageName) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    _builder.append(packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    String paquete = _builder.toString();
    Set<String> importaciones = new TreeSet<String>();
    ArrayList<String> pointcuts = new ArrayList<String>();
    importaciones.add("co.edu.icesi.eketal.automaton.*");
    importaciones.add("co.edu.icesi.eketal.groupsimpl.*");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("public aspect ");
    String _name = modelo.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    _builder_1.append(_firstUpper, "");
    _builder_1.append("{");
    _builder_1.newLineIfNotEmpty();
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("//private Automaton automata = miprieraclase.getAutomaton();");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.newLine();
    {
      EList<Decl> _declarations = modelo.getDeclarations();
      for(final Decl event : _declarations) {
        {
          if ((event instanceof JVarD)) {
            _builder_1.append("\t");
            _builder_1.append("//");
            JvmTypeReference _type = ((JVarD) event).getType();
            String _qualifiedName = _type.getQualifiedName();
            ArrayList<String> _agregarImports = this.agregarImports(_qualifiedName);
            boolean _add = Iterables.<String>addAll(importaciones, _agregarImports);
            _builder_1.append(_add, "\t");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("//--------Evento: ");
            String _name_1 = ((JVarD)event).getName();
            String _string = _name_1.toString();
            _builder_1.append(_string, "\t");
            _builder_1.append("-------------");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("private ");
            JvmTypeReference _type_1 = ((JVarD) event).getType();
            String _simpleName = _type_1.getSimpleName();
            _builder_1.append(_simpleName, "\t");
            _builder_1.append(" ");
            String _name_2 = ((JVarD) event).getName();
            String _firstLower = StringExtensions.toFirstLower(_name_2);
            _builder_1.append(_firstLower, "\t");
            _builder_1.append(";");
            _builder_1.newLineIfNotEmpty();
          }
        }
        {
          if ((event instanceof EvDecl)) {
            _builder_1.append("\t");
            _builder_1.append("//--------Evento: ");
            String _name_3 = ((EvDecl)event).getName();
            String _string_1 = _name_3.toString();
            _builder_1.append(_string_1, "\t");
            _builder_1.append("-------------");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("pointcut ");
            String _name_4 = ((EvDecl)event).getName();
            String _firstLower_1 = StringExtensions.toFirstLower(_name_4);
            _builder_1.append(_firstLower_1, "\t");
            _builder_1.append("():");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            String _createPointCut = this.createPointCut(((EvDecl) event), pointcuts);
            _builder_1.append(_createPointCut, "\t\t");
            _builder_1.append(";");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("after() returning (Object o): ");
            String _name_5 = ((EvDecl)event).getName();
            String _firstLower_2 = StringExtensions.toFirstLower(_name_5);
            _builder_1.append(_firstLower_2, "\t");
            _builder_1.append("() {");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("System.out.println(\"Returned normally with \" + o);");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("}");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("after() throwing (Exception e): ");
            String _name_6 = ((EvDecl)event).getName();
            String _firstLower_3 = StringExtensions.toFirstLower(_name_6);
            _builder_1.append(_firstLower_3, "\t");
            _builder_1.append("() {");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("System.out.println(\"Threw an exception: \" + e);");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("}");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("after(): ");
            String _name_7 = ((EvDecl)event).getName();
            String _firstLower_4 = StringExtensions.toFirstLower(_name_7);
            _builder_1.append(_firstLower_4, "\t");
            _builder_1.append("(){");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("System.out.println(\"Returned or threw an Exception\");");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("}");
            _builder_1.newLine();
          }
        }
      }
    }
    _builder_1.append("\t");
    _builder_1.newLine();
    {
      for(final String pointcut : pointcuts) {
        _builder_1.append("\t");
        _builder_1.append(pointcut, "\t");
        _builder_1.append(";");
        _builder_1.newLineIfNotEmpty();
      }
    }
    _builder_1.append("\t");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    String aspect = _builder_1.toString();
    StringConcatenation _builder_2 = new StringConcatenation();
    {
      for(final String tipo : importaciones) {
        _builder_2.append("import ");
        _builder_2.append(tipo, "");
        _builder_2.append(";");
        _builder_2.newLineIfNotEmpty();
      }
    }
    _builder_2.newLine();
    String imports = _builder_2.toString();
    return ((paquete + imports) + aspect);
  }
  
  public ArrayList<String> agregarImports(final String name) {
    ArrayList<String> lista = new ArrayList<String>();
    boolean _contains = name.contains("<");
    boolean _not = (!_contains);
    if (_not) {
      lista.add(name);
    } else {
      String[] strings = name.split("<");
      for (final String string : strings) {
        String _replaceAll = string.replaceAll(">", "");
        lista.add(_replaceAll);
      }
    }
    return lista;
  }
  
  public String createPointCut(final EvDecl decl, final ArrayList<String> pointcuts) {
    ArrayList<String> eventos = new ArrayList<String>();
    EList<EventExpression> _eventos = decl.getEventos();
    for (final EventExpression event : _eventos) {
      String _eventExpression = this.eventExpression(((EventExpression) event), pointcuts);
      eventos.add(_eventExpression);
    }
    String _string = eventos.toString();
    String _string_1 = eventos.toString();
    int _length = _string_1.length();
    int _minus = (_length - 1);
    final String valor = _string.substring(1, _minus);
    return valor;
  }
  
  public String eventExpression(final EventExpression event, final ArrayList<String> pointcuts) {
    EventPredicate _tipoEvento = event.getTipoEvento();
    boolean _notEquals = (!Objects.equal(_tipoEvento, null));
    if (_notEquals) {
      EventPredicate tipoEvento = event.getTipoEvento();
      boolean _matched = false;
      if (!_matched) {
        if (tipoEvento instanceof Trigger) {
          _matched=true;
          CharSequence[] pointcutTemp = this.returnCall(((Trigger) tipoEvento));
          CharSequence _get = pointcutTemp[1];
          String _string = _get.toString();
          pointcuts.add(_string);
          CharSequence _get_1 = pointcutTemp[0];
          return _get_1.toString();
        }
      }
      if (!_matched) {
        if (tipoEvento instanceof KindAttribute) {
          _matched=true;
          return this.returnAttribute(((KindAttribute) tipoEvento));
        }
      }
    } else {
      boolean _matched_1 = false;
      if (!_matched_1) {
        if (event instanceof AndEvent) {
          _matched_1=true;
          AndEvent andEvent = ((AndEvent) event);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("(");
          EventExpression _left = andEvent.getLeft();
          Object _eventExpression = this.eventExpression(((EventExpression) _left), pointcuts);
          _builder.append(_eventExpression, "");
          _builder.append(" && ");
          EventExpression _right = andEvent.getRight();
          Object _eventExpression_1 = this.eventExpression(((EventExpression) _right), pointcuts);
          _builder.append(_eventExpression_1, "");
          _builder.append(")");
          return _builder.toString();
        }
      }
      if (!_matched_1) {
        if (event instanceof OrEvent) {
          _matched_1=true;
          OrEvent orEvent = ((OrEvent) event);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("(");
          EventExpression _left = orEvent.getLeft();
          Object _eventExpression = this.eventExpression(((EventExpression) _left), pointcuts);
          _builder.append(_eventExpression, "");
          _builder.append(" || ");
          EventExpression _right = orEvent.getRight();
          Object _eventExpression_1 = this.eventExpression(((EventExpression) _right), pointcuts);
          _builder.append(_eventExpression_1, "");
          _builder.append(")");
          return _builder.toString();
        }
      }
      if (!_matched_1) {
        if (event instanceof UnaryEvent) {
          _matched_1=true;
          UnaryEvent unaryEvent = ((UnaryEvent) event);
          StringConcatenation _builder = new StringConcatenation();
          _builder.append("!");
          EventExpression _expr = unaryEvent.getExpr();
          Object _eventExpression = this.eventExpression(((EventExpression) _expr), pointcuts);
          _builder.append(_eventExpression, "");
          return _builder.toString();
        }
      }
    }
    return null;
  }
  
  public String returnAttribute(final KindAttribute attribute) {
    XExpression _condition = attribute.getCondition();
    boolean _notEquals = (!Objects.equal(_condition, null));
    if (_notEquals) {
      XExpression _condition_1 = attribute.getCondition();
      EList<EObject> _eContents = _condition_1.eContents();
      int _size = _eContents.size();
      InputOutput.<Integer>println(Integer.valueOf(_size));
      String body = "";
      XExpression _condition_2 = attribute.getCondition();
      EList<EObject> _eContents_1 = _condition_2.eContents();
      int _size_1 = _eContents_1.size();
      boolean _equals = (_size_1 == 1);
      if (_equals) {
        XExpression _condition_3 = attribute.getCondition();
        EList<EObject> _eContents_2 = _condition_3.eContents();
        EObject _get = _eContents_2.get(0);
        XStringLiteralImpl valone = ((XStringLiteralImpl) _get);
        StringConcatenation _builder = new StringConcatenation();
        String _value = valone.getValue();
        _builder.append(_value, "");
        body = _builder.toString();
      } else {
        boolean _and = false;
        XExpression _condition_4 = attribute.getCondition();
        EList<EObject> _eContents_3 = _condition_4.eContents();
        EObject _get_1 = _eContents_3.get(0);
        if (!(_get_1 instanceof XStringLiteralImpl)) {
          _and = false;
        } else {
          XExpression _condition_5 = attribute.getCondition();
          EList<EObject> _eContents_4 = _condition_5.eContents();
          EObject _get_2 = _eContents_4.get(1);
          _and = (_get_2 instanceof XStringLiteralImpl);
        }
        if (_and) {
          XExpression _condition_6 = attribute.getCondition();
          EList<EObject> _eContents_5 = _condition_6.eContents();
          EObject _get_3 = _eContents_5.get(0);
          XStringLiteralImpl valone_1 = ((XStringLiteralImpl) _get_3);
          XExpression _condition_7 = attribute.getCondition();
          EList<EObject> _eContents_6 = _condition_7.eContents();
          EObject _get_4 = _eContents_6.get(1);
          XStringLiteralImpl valtwo = ((XStringLiteralImpl) _get_4);
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("\"");
          String _value_1 = valone_1.getValue();
          _builder_1.append(_value_1, "");
          _builder_1.append("\".equals(\"");
          String _value_2 = valtwo.getValue();
          _builder_1.append(_value_2, "");
          _builder_1.append("\")");
          body = _builder_1.toString();
        }
      }
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("if(");
      _builder_2.append(body, "");
      _builder_2.append(")");
      return _builder_2.toString();
    } else {
      Group _hostgroup = attribute.getHostgroup();
      boolean _notEquals_1 = (!Objects.equal(_hostgroup, null));
      if (_notEquals_1) {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append(EketalJvmModelInferrer.groupClassName, "");
        _builder_3.append(".host(");
        Group _hostgroup_1 = attribute.getHostgroup();
        String _name = _hostgroup_1.getName();
        _builder_3.append(_name, "");
        _builder_3.append(")");
        return _builder_3.toString();
      } else {
        Group _ongroup = attribute.getOngroup();
        boolean _notEquals_2 = (!Objects.equal(_ongroup, null));
        if (_notEquals_2) {
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("if(");
          _builder_4.append(EketalJvmModelInferrer.groupClassName, "");
          _builder_4.append(".on(\"");
          Group _ongroup_1 = attribute.getOngroup();
          String _name_1 = _ongroup_1.getName();
          _builder_4.append(_name_1, "");
          _builder_4.append("\"))");
          return _builder_4.toString();
        }
      }
    }
    return null;
  }
  
  public CharSequence[] returnCall(final Trigger trigger) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("point");
    String _esig = trigger.getEsig();
    String _string = _esig.toString();
    String _replaceAll = _string.replaceAll("\\.", "");
    String _firstUpper = StringExtensions.toFirstUpper(_replaceAll);
    _builder.append(_firstUpper, "");
    _builder.append("()");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("pointcut point");
    String _esig_1 = trigger.getEsig();
    String _string_1 = _esig_1.toString();
    String _replaceAll_1 = _string_1.replaceAll("\\.", "");
    String _firstUpper_1 = StringExtensions.toFirstUpper(_replaceAll_1);
    _builder_1.append(_firstUpper_1, "");
    _builder_1.append("(): call(* ");
    String _esig_2 = trigger.getEsig();
    _builder_1.append(_esig_2, "");
    _builder_1.append("(");
    EList<JvmFormalParameter> _params = trigger.getParams();
    String _join = IterableExtensions.join(_params, ",");
    _builder_1.append(_join, "");
    _builder_1.append("))");
    CharSequence[] retorno = ((CharSequence[])Conversions.unwrapArray(CollectionLiterals.<CharSequence>newArrayList(_builder.toString(), _builder_1.toString()), CharSequence.class));
    return retorno;
  }
}
