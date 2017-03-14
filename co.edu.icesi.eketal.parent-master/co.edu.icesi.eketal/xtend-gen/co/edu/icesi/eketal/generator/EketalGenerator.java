package co.edu.icesi.eketal.generator;

import co.edu.icesi.eketal.eketal.AndEvent;
import co.edu.icesi.eketal.eketal.Automaton;
import co.edu.icesi.eketal.eketal.Decl;
import co.edu.icesi.eketal.eketal.EvDecl;
import co.edu.icesi.eketal.eketal.EventClass;
import co.edu.icesi.eketal.eketal.EventExpression;
import co.edu.icesi.eketal.eketal.EventPredicate;
import co.edu.icesi.eketal.eketal.Group;
import co.edu.icesi.eketal.eketal.JVMTYPE;
import co.edu.icesi.eketal.eketal.JVarD;
import co.edu.icesi.eketal.eketal.KindAttribute;
import co.edu.icesi.eketal.eketal.Model;
import co.edu.icesi.eketal.eketal.OrEvent;
import co.edu.icesi.eketal.eketal.TPrefix;
import co.edu.icesi.eketal.eketal.Trigger;
import co.edu.icesi.eketal.eketal.UnaryEvent;
import co.edu.icesi.eketal.jvmmodel.EketalJvmModelInferrer;
import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtend2.lib.StringConcatenation;
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
import org.eclipse.xtext.xtype.XImportDeclaration;
import org.eclipse.xtext.xtype.XImportSection;

@SuppressWarnings("all")
public class EketalGenerator implements IGenerator {
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    InputOutput.<String>println("IGenerator line 35");
    TreeIterator<EObject> _allContents = resource.getAllContents();
    final Iterator<Model> listModel = Iterators.<Model>filter(_allContents, Model.class);
    final Set<String> importedLibraries = new TreeSet<String>();
    boolean _hasNext = listModel.hasNext();
    if (_hasNext) {
      final Model modelo = listModel.next();
      String _name = modelo.getName();
      boolean _notEquals = (!Objects.equal(_name, null));
      if (_notEquals) {
        String _name_1 = modelo.getName();
        String _plus = (_name_1 + ".*");
        importedLibraries.add(_plus);
      }
      if (((!Objects.equal(modelo.getImportSection(), null)) && (!modelo.getImportSection().getImportDeclarations().isEmpty()))) {
        XImportSection _importSection = modelo.getImportSection();
        EList<XImportDeclaration> _importDeclarations = _importSection.getImportDeclarations();
        final Consumer<XImportDeclaration> _function = (XImportDeclaration it) -> {
          String _importedNamespace = it.getImportedNamespace();
          boolean _notEquals_1 = (!Objects.equal(_importedNamespace, null));
          if (_notEquals_1) {
            String _importedNamespace_1 = it.getImportedNamespace();
            importedLibraries.add(_importedNamespace_1);
          } else {
            String _importedTypeName = it.getImportedTypeName();
            importedLibraries.add(_importedTypeName);
          }
        };
        _importDeclarations.forEach(_function);
      }
    }
    TreeIterator<EObject> _allContents_1 = resource.getAllContents();
    Iterator<EventClass> _filter = Iterators.<EventClass>filter(_allContents_1, EventClass.class);
    final Procedure1<EventClass> _function_1 = (EventClass it) -> {
      this.generateAspect(it, fsa, importedLibraries);
    };
    IteratorExtensions.<EventClass>forEach(_filter, _function_1);
  }
  
  public void generateAspect(final EventClass modelo, final IFileSystemAccess fsa, final Set<String> importedLibraries) {
    String packageName = "co.edu.icesi.eketal.aspects";
    String _name = modelo.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    String _prepareFileName = this.prepareFileName(("./" + packageName), _firstUpper);
    CharSequence _generate = this.generate(modelo, packageName, importedLibraries);
    fsa.generateFile(_prepareFileName, IFileSystemAccess.DEFAULT_OUTPUT, _generate);
  }
  
  public String prepareFileName(final String packageName, final String fileName) {
    String _replace = ((packageName + ".") + fileName).replace(".", File.separator);
    return (_replace + ".aj");
  }
  
  public CharSequence generate(final EventClass modelo, final String packageName, final Set<String> libraries) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    _builder.append(packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    String packageDefinition = _builder.toString();
    String automatonName = null;
    Set<String> importedLibraries = new TreeSet<String>();
    Iterables.<String>addAll(importedLibraries, libraries);
    TreeSet<String> pointcuts = new TreeSet<String>();
    HashMap<String, String> after = new HashMap<String, String>();
    HashMap<String, String> before = new HashMap<String, String>();
    EList<Decl> _declarations = modelo.getDeclarations();
    boolean _containsAutomaton = this.containsAutomaton(_declarations);
    if (_containsAutomaton) {
      importedLibraries.add("co.edu.icesi.eketal.automaton.*");
    }
    importedLibraries.add("co.edu.icesi.eketal.groupsimpl.*");
    importedLibraries.add("co.edu.icesi.eketal.handlercontrol.*");
    importedLibraries.add("co.edu.icesi.eketal.reaction.*");
    importedLibraries.add("co.edu.icesi.ketal.core.Automaton");
    importedLibraries.add("co.edu.icesi.ketal.core.NamedEvent");
    importedLibraries.add("co.edu.icesi.ketal.core.Event");
    importedLibraries.add("java.util.Map");
    importedLibraries.add("java.util.HashMap");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("public aspect ");
    String _name = modelo.getName();
    String _firstUpper = StringExtensions.toFirstUpper(_name);
    _builder_1.append(_firstUpper, "");
    _builder_1.append("{");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    _builder_1.newLine();
    {
      EList<Decl> _declarations_1 = modelo.getDeclarations();
      for(final Decl event : _declarations_1) {
        {
          if ((event instanceof JVarD)) {
            _builder_1.append("\t");
            _builder_1.append("//");
            JvmTypeReference _type = ((JVarD) event).getType();
            String _qualifiedName = _type.getQualifiedName();
            ArrayList<String> _agregarImports = this.agregarImports(_qualifiedName);
            boolean _add = Iterables.<String>addAll(importedLibraries, _agregarImports);
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
          if ((event instanceof Automaton)) {
            _builder_1.append("\t");
            _builder_1.append("//");
            String _name_3 = ((Automaton)event).getName();
            String _automatonName = automatonName = _name_3;
            _builder_1.append(_automatonName, "\t");
            _builder_1.newLineIfNotEmpty();
          }
        }
        {
          if ((event instanceof EvDecl)) {
            _builder_1.append("\t");
            _builder_1.append("//--------Evento: ");
            String _name_4 = ((EvDecl)event).getName();
            String _string_1 = _name_4.toString();
            _builder_1.append(_string_1, "\t");
            _builder_1.append("-------------");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("pointcut ");
            String _name_5 = ((EvDecl)event).getName();
            String _firstLower_1 = StringExtensions.toFirstLower(_name_5);
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
            _builder_1.append("//after() returning (Object o): ");
            String _name_6 = ((EvDecl)event).getName();
            String _firstLower_2 = StringExtensions.toFirstLower(_name_6);
            _builder_1.append(_firstLower_2, "\t");
            _builder_1.append("() {");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("//\tSystem.out.println(\"[Aspectj] Returned normally with \" + o);");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("//}");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("//after() throwing (Exception e): ");
            String _name_7 = ((EvDecl)event).getName();
            String _firstLower_3 = StringExtensions.toFirstLower(_name_7);
            _builder_1.append(_firstLower_3, "\t");
            _builder_1.append("() {");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("//\tSystem.out.println(\"[Aspectj] Threw an exception: \" + e);");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("//}");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("after(): ");
            String _name_8 = ((EvDecl)event).getName();
            String _firstLower_4 = StringExtensions.toFirstLower(_name_8);
            _builder_1.append(_firstLower_4, "\t");
            _builder_1.append("(){");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("Automaton automata = ");
            String _firstUpper_1 = StringExtensions.toFirstUpper(automatonName);
            _builder_1.append(_firstUpper_1, "\t\t");
            _builder_1.append(".getInstance();");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("Reaction.verifyAfter(automata);");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("System.out.println(\"[Aspectj] After: Returned or threw an Exception\");");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("}");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("before(): ");
            String _name_9 = ((EvDecl)event).getName();
            String _firstLower_5 = StringExtensions.toFirstLower(_name_9);
            _builder_1.append(_firstLower_5, "\t");
            _builder_1.append("(){");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("EventHandler distribuidor = ");
            _builder_1.append(EketalJvmModelInferrer.handlerClassName, "\t\t");
            _builder_1.append(".getInstance();");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("Automaton automata = ");
            String _firstUpper_2 = StringExtensions.toFirstUpper(automatonName);
            _builder_1.append(_firstUpper_2, "\t\t");
            _builder_1.append(".getInstance();");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("Map map = new HashMap<String, Object>();");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("//map.put(\"Automata\", automata);");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("Event event = new NamedEvent(\"");
            String _name_10 = ((EvDecl)event).getName();
            _builder_1.append(_name_10, "\t\t");
            _builder_1.append("\");");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("event.setLocalization(distribuidor.getAsyncAddress());");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("distribuidor.multicast(event, map);");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("if(!automata.evaluate(event)){");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t\t");
            _builder_1.append("System.out.println(\"[Aspectj] Before: Event not recognized by the automaton\");");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t\t");
            _builder_1.append("//Debería parar");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("}else{");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t\t");
            _builder_1.append("Reaction.verifyBefore(automata);");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t\t");
            _builder_1.append("System.out.println(\"[Aspectj] Before: Returned or threw an Exception\");\t\t\t\t\t\t\t");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("}");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("//while(!automata.evaluate(event)){");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("//\twait(100);");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("//\t");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("//}");
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
    _builder_1.append("}");
    _builder_1.newLine();
    String aspect = _builder_1.toString();
    StringConcatenation _builder_2 = new StringConcatenation();
    {
      for(final String tipo : importedLibraries) {
        _builder_2.append("import ");
        _builder_2.append(tipo, "");
        _builder_2.append(";");
        _builder_2.newLineIfNotEmpty();
      }
    }
    _builder_2.newLine();
    String imports = _builder_2.toString();
    return ((packageDefinition + imports) + aspect);
  }
  
  public boolean containsAutomaton(final EList<Decl> list) {
    for (final Decl decl : list) {
      if ((decl instanceof Automaton)) {
        return true;
      }
    }
    return false;
  }
  
  public ArrayList<String> agregarImports(final String name) {
    ArrayList<String> importList = new ArrayList<String>();
    boolean _contains = name.contains("<");
    boolean _not = (!_contains);
    if (_not) {
      importList.add(name);
    } else {
      String[] strings = name.split("<");
      for (final String string : strings) {
        String _replaceAll = string.replaceAll(">", "");
        importList.add(_replaceAll);
      }
    }
    return importList;
  }
  
  public String createPointCut(final EvDecl decl, final TreeSet<String> pointcuts) {
    ArrayList<String> eventsDefinition = new ArrayList<String>();
    EList<EventExpression> _eventos = decl.getEventos();
    for (final EventExpression event : _eventos) {
      String _eventExpression = this.eventExpression(((EventExpression) event), pointcuts);
      eventsDefinition.add(_eventExpression);
    }
    String _string = eventsDefinition.toString();
    String _string_1 = eventsDefinition.toString();
    int _length = _string_1.length();
    int _minus = (_length - 1);
    final String pointCutString = _string.substring(1, _minus);
    return pointCutString;
  }
  
  /**
   * El warnning es omitible, dado que no va a fallar bajo ninguna situación
   */
  public String eventExpression(final EventExpression event, final TreeSet<String> pointcuts) {
    EventPredicate _tipoEvento = event.getTipoEvento();
    boolean _notEquals = (!Objects.equal(_tipoEvento, null));
    if (_notEquals) {
      EventPredicate eventKind = event.getTipoEvento();
      boolean _matched = false;
      if (eventKind instanceof Trigger) {
        _matched=true;
        CharSequence[] pointcutTemp = this.returnCall(((Trigger) eventKind));
        CharSequence _get = pointcutTemp[1];
        String _string = _get.toString();
        pointcuts.add(_string);
        CharSequence _get_1 = pointcutTemp[0];
        return _get_1.toString();
      }
      if (!_matched) {
        if (eventKind instanceof KindAttribute) {
          _matched=true;
          return this.returnAttribute(((KindAttribute) eventKind));
        }
      }
    } else {
      boolean _matched_1 = false;
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
      String _plus = (Integer.valueOf(_size) + "doGenerate línea 169");
      InputOutput.<String>println(_plus);
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
        if (((attribute.getCondition().eContents().get(0) instanceof XStringLiteralImpl) && (attribute.getCondition().eContents().get(1) instanceof XStringLiteralImpl))) {
          XExpression _condition_4 = attribute.getCondition();
          EList<EObject> _eContents_3 = _condition_4.eContents();
          EObject _get_1 = _eContents_3.get(0);
          XStringLiteralImpl valone_1 = ((XStringLiteralImpl) _get_1);
          XExpression _condition_5 = attribute.getCondition();
          EList<EObject> _eContents_4 = _condition_5.eContents();
          EObject _get_2 = _eContents_4.get(1);
          XStringLiteralImpl valtwo = ((XStringLiteralImpl) _get_2);
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
        _builder_3.append("if(");
        _builder_3.append(EketalJvmModelInferrer.groupClassName, "");
        _builder_3.append(".host(\"");
        Group _hostgroup_1 = attribute.getHostgroup();
        String _name = _hostgroup_1.getName();
        _builder_3.append(_name, "");
        _builder_3.append("\"))");
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
    ArrayList<String> parameters = CollectionLiterals.<String>newArrayList();
    EList<JvmTypeReference> _params = trigger.getParams();
    for (final JvmTypeReference p : _params) {
      String _simpleName = p.getSimpleName();
      parameters.add(_simpleName);
    }
    String typeReturn = null;
    JVMTYPE _returndef = trigger.getReturndef();
    String _astk = _returndef.getAstk();
    boolean _equals = Objects.equal(_astk, null);
    if (_equals) {
      JVMTYPE _returndef_1 = trigger.getReturndef();
      JvmTypeReference _jvmRef = _returndef_1.getJvmRef();
      String _simpleName_1 = _jvmRef.getSimpleName();
      typeReturn = _simpleName_1;
    } else {
      typeReturn = "*";
    }
    String triggerType = "";
    TPrefix _triggerType = trigger.getTriggerType();
    if (_triggerType != null) {
      switch (_triggerType) {
        case CALL:
          triggerType = "call";
          break;
        case EXECUTION:
          triggerType = "execution";
          break;
        default:
          break;
      }
    }
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
    _builder_1.append("(): ");
    _builder_1.append(triggerType, "");
    _builder_1.append("(");
    _builder_1.append(typeReturn, "");
    _builder_1.append(" ");
    String _esig_2 = trigger.getEsig();
    _builder_1.append(_esig_2, "");
    _builder_1.append("(");
    String _join = IterableExtensions.join(parameters, ",");
    _builder_1.append(_join, "");
    _builder_1.append("))");
    CharSequence[] returnCall = ((CharSequence[])Conversions.unwrapArray(CollectionLiterals.<CharSequence>newArrayList(_builder.toString(), _builder_1.toString()), CharSequence.class));
    return returnCall;
  }
}
