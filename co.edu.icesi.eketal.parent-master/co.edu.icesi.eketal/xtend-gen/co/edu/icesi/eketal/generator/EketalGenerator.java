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
import co.edu.icesi.eketal.eketal.KindAttribute;
import co.edu.icesi.eketal.eketal.Ltl;
import co.edu.icesi.eketal.eketal.Model;
import co.edu.icesi.eketal.eketal.OrEvent;
import co.edu.icesi.eketal.eketal.Step;
import co.edu.icesi.eketal.eketal.TPrefix;
import co.edu.icesi.eketal.eketal.TransDef;
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
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import org.eclipse.emf.common.util.EList;
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
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.InputOutput;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.StringExtensions;
import org.eclipse.xtext.xtype.XImportDeclaration;

@SuppressWarnings("all")
public class EketalGenerator implements IGenerator {
  public String aspectClass = "";
  
  @Override
  public void doGenerate(final Resource resource, final IFileSystemAccess fsa) {
    InputOutput.<String>println("IGenerator line 35");
    final Iterator<Model> listModel = Iterators.<Model>filter(resource.getAllContents(), Model.class);
    final Set<String> importedLibraries = new TreeSet<String>();
    boolean _hasNext = listModel.hasNext();
    if (_hasNext) {
      final Model modelo = listModel.next();
      String _name = modelo.getName();
      boolean _tripleNotEquals = (_name != null);
      if (_tripleNotEquals) {
        String _name_1 = modelo.getName();
        String _plus = (_name_1 + ".*");
        importedLibraries.add(_plus);
      }
      if (((modelo.getImportSection() != null) && (!modelo.getImportSection().getImportDeclarations().isEmpty()))) {
        final Consumer<XImportDeclaration> _function = (XImportDeclaration it) -> {
          String _importedNamespace = it.getImportedNamespace();
          boolean _tripleNotEquals_1 = (_importedNamespace != null);
          if (_tripleNotEquals_1) {
            String _importedNamespace_1 = it.getImportedNamespace();
            importedLibraries.add(_importedNamespace_1);
          } else {
            String _importedTypeName = it.getImportedTypeName();
            importedLibraries.add(_importedTypeName);
          }
        };
        modelo.getImportSection().getImportDeclarations().forEach(_function);
      }
    }
    final Procedure1<EventClass> _function_1 = (EventClass it) -> {
      this.generateAspect(it, fsa, importedLibraries);
    };
    IteratorExtensions.<EventClass>forEach(Iterators.<EventClass>filter(resource.getAllContents(), EventClass.class), _function_1);
  }
  
  public void generateAspect(final EventClass modelo, final IFileSystemAccess fsa, final Set<String> importedLibraries) {
    String packageName = "co.edu.icesi.eketal.aspects";
    fsa.generateFile(this.prepareFileName(("./" + packageName), StringExtensions.toFirstUpper(modelo.getName())), IFileSystemAccess.DEFAULT_OUTPUT, this.generate(modelo, packageName, importedLibraries));
  }
  
  public String prepareFileName(final String packageName, final String fileName) {
    String _replace = ((packageName + ".") + fileName).replace(".", File.separator);
    return (_replace + ".aj");
  }
  
  public CharSequence generate(final EventClass modelo, final String packageName, final Set<String> libraries) {
    this.aspectClass = modelo.getName();
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    _builder.append(packageName);
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    String packageDefinition = _builder.toString();
    Iterable<Automaton> automatons = Iterables.<Automaton>filter(modelo.getDeclarations(), Automaton.class);
    Iterable<Ltl> buchis = Iterables.<Ltl>filter(modelo.getDeclarations(), Ltl.class);
    final Function1<Automaton, Set<String>> _function = (Automaton a) -> {
      final Set<String> events = new TreeSet<String>();
      final Consumer<Step> _function_1 = (Step s) -> {
        final Function1<TransDef, Boolean> _function_2 = (TransDef t) -> {
          return Boolean.valueOf(events.add(t.getEvent().getName()));
        };
        IterableExtensions.<TransDef>forall(s.getTransitions(), _function_2);
      };
      a.getSteps().forEach(_function_1);
      return events;
    };
    final Map<Automaton, Set<String>> eventsOfAutomaton = IterableExtensions.<Automaton, Set<String>>toInvertedMap(automatons, _function);
    Set<String> importedLibraries = new TreeSet<String>();
    Iterables.<String>addAll(importedLibraries, libraries);
    TreeSet<String> pointcuts = new TreeSet<String>();
    HashMap<String, String> after = new HashMap<String, String>();
    HashMap<String, String> before = new HashMap<String, String>();
    boolean _containsAutomaton = this.containsAutomaton(modelo.getDeclarations());
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
    importedLibraries.add("org.apache.commons.logging.Log");
    importedLibraries.add("org.apache.commons.logging.LogFactory");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("public aspect ");
    String _firstUpper = StringExtensions.toFirstUpper(this.aspectClass);
    _builder_1.append(_firstUpper);
    _builder_1.append("{");
    _builder_1.newLineIfNotEmpty();
    _builder_1.append("\t");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("final static Log logger = LogFactory.getLog(");
    String _firstUpper_1 = StringExtensions.toFirstUpper(this.aspectClass);
    _builder_1.append(_firstUpper_1, "\t");
    _builder_1.append(".class);");
    _builder_1.newLineIfNotEmpty();
    {
      EList<Decl> _declarations = modelo.getDeclarations();
      for(final Decl event : _declarations) {
        {
          if ((event instanceof EvDecl)) {
            _builder_1.append("\t");
            _builder_1.append("//--------Evento: ");
            String _string = ((EvDecl)event).getName().toString();
            _builder_1.append(_string, "\t");
            _builder_1.append("-------------");
            _builder_1.newLineIfNotEmpty();
            _builder_1.append("\t");
            _builder_1.append("pointcut ");
            String _firstLower = StringExtensions.toFirstLower(((EvDecl)event).getName());
            _builder_1.append(_firstLower, "\t");
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
            String _firstLower_1 = StringExtensions.toFirstLower(((EvDecl)event).getName());
            _builder_1.append(_firstLower_1, "\t");
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
            String _firstLower_2 = StringExtensions.toFirstLower(((EvDecl)event).getName());
            _builder_1.append(_firstLower_2, "\t");
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
            String _firstLower_3 = StringExtensions.toFirstLower(((EvDecl)event).getName());
            _builder_1.append(_firstLower_3, "\t");
            _builder_1.append("(){");
            _builder_1.newLineIfNotEmpty();
            {
              boolean _isEmpty = IterableExtensions.isEmpty(automatons);
              boolean _not = (!_isEmpty);
              if (_not) {
                {
                  for(final Automaton automatonName : automatons) {
                    {
                      boolean _contains = eventsOfAutomaton.get(automatonName).contains(((EvDecl)event).getName());
                      if (_contains) {
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("Automaton ");
                        String _firstLower_4 = StringExtensions.toFirstLower(automatonName.getName());
                        _builder_1.append(_firstLower_4, "\t\t");
                        _builder_1.append(" = ");
                        String _firstUpper_2 = StringExtensions.toFirstUpper(automatonName.getName());
                        _builder_1.append(_firstUpper_2, "\t\t");
                        _builder_1.append(".getInstance();");
                        _builder_1.newLineIfNotEmpty();
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append(EketalJvmModelInferrer.reaction, "\t\t");
                        _builder_1.append(".verifyAfter(");
                        String _firstLower_5 = StringExtensions.toFirstLower(automatonName.getName());
                        _builder_1.append(_firstLower_5, "\t\t");
                        _builder_1.append(");");
                        _builder_1.newLineIfNotEmpty();
                      }
                    }
                    _builder_1.append("\t");
                    _builder_1.append("\t");
                    _builder_1.append("//System.out.println(\"[Aspectj] After: Recognized event in ");
                    String _name = automatonName.getName();
                    _builder_1.append(_name, "\t\t");
                    _builder_1.append("\");");
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.append("\t");
                    _builder_1.append("\t");
                    _builder_1.append("logger.info(\"[Aspectj] After: Recognized event in ");
                    String _firstUpper_3 = StringExtensions.toFirstUpper(automatonName.getName());
                    _builder_1.append(_firstUpper_3, "\t\t");
                    _builder_1.append("\");");
                    _builder_1.newLineIfNotEmpty();
                  }
                }
              }
            }
            _builder_1.append("\t");
            _builder_1.append("}");
            _builder_1.newLine();
            _builder_1.append("\t");
            _builder_1.append("before(): ");
            String _firstLower_6 = StringExtensions.toFirstLower(((EvDecl)event).getName());
            _builder_1.append(_firstLower_6, "\t");
            _builder_1.append("(){");
            _builder_1.newLineIfNotEmpty();
            {
              if (((!IterableExtensions.isEmpty(automatons)) || (!IterableExtensions.isEmpty(buchis)))) {
                _builder_1.append("\t");
                _builder_1.append("\t");
                _builder_1.append("Event event = new NamedEvent(\"");
                String _name_1 = ((EvDecl)event).getName();
                _builder_1.append(_name_1, "\t\t");
                _builder_1.append("\");");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("\t");
                _builder_1.append("\t");
                _builder_1.append(EketalJvmModelInferrer.handlerClassName, "\t\t");
                _builder_1.append(" distribuidor = ");
                _builder_1.append(EketalJvmModelInferrer.handlerClassName, "\t\t");
                _builder_1.append(".getInstance();");
                _builder_1.newLineIfNotEmpty();
                _builder_1.append("\t");
                _builder_1.append("\t");
                _builder_1.append("event.setLocalization(distribuidor.getAsyncAddress());");
                _builder_1.newLine();
                _builder_1.append("\t");
                _builder_1.append("\t");
                _builder_1.append("Map map = new HashMap<String, Object>();");
                _builder_1.newLine();
                _builder_1.append("\t");
                _builder_1.append("\t");
                _builder_1.append("distribuidor.multicast(event, map);");
                _builder_1.newLine();
                {
                  for(final Ltl buchi : buchis) {
                    _builder_1.append("\t");
                    _builder_1.append("\t");
                    _builder_1.append("Automaton automaton");
                    String _firstUpper_4 = StringExtensions.toFirstUpper(buchi.getName());
                    _builder_1.append(_firstUpper_4, "\t\t");
                    _builder_1.append(" = co.edu.icesi.eketal.buchiautomaton.");
                    String _firstUpper_5 = StringExtensions.toFirstUpper(buchi.getName());
                    _builder_1.append(_firstUpper_5, "\t\t");
                    _builder_1.append(".getInstance();");
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.append("\t");
                    _builder_1.append("\t");
                    _builder_1.append("if(!automaton");
                    String _firstUpper_6 = StringExtensions.toFirstUpper(buchi.getName());
                    _builder_1.append(_firstUpper_6, "\t\t");
                    _builder_1.append(".evaluate(event)){");
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.append("\t");
                    _builder_1.append("\t");
                    _builder_1.append("\t");
                    _builder_1.append(EketalJvmModelInferrer.reaction, "\t\t\t");
                    _builder_1.append(".onViolation();");
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.append("\t");
                    _builder_1.append("\t");
                    _builder_1.append("}else{");
                    _builder_1.newLine();
                    _builder_1.append("\t");
                    _builder_1.append("\t");
                    _builder_1.append("\t");
                    _builder_1.append("logger.info(\"[Aspectj] Event respects the property ");
                    String _name_2 = buchi.getName();
                    _builder_1.append(_name_2, "\t\t\t");
                    _builder_1.append("\");");
                    _builder_1.newLineIfNotEmpty();
                    _builder_1.append("\t");
                    _builder_1.append("\t");
                    _builder_1.append("}");
                    _builder_1.newLine();
                  }
                }
                {
                  for(final Automaton automatonName_1 : automatons) {
                    {
                      boolean _contains_1 = eventsOfAutomaton.get(automatonName_1).contains(((EvDecl)event).getName());
                      if (_contains_1) {
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("Automaton ");
                        String _firstLower_7 = StringExtensions.toFirstLower(automatonName_1.getName());
                        _builder_1.append(_firstLower_7, "\t\t");
                        _builder_1.append(" = ");
                        String _firstUpper_7 = StringExtensions.toFirstUpper(automatonName_1.getName());
                        _builder_1.append(_firstUpper_7, "\t\t");
                        _builder_1.append(".getInstance();");
                        _builder_1.newLineIfNotEmpty();
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("if(!");
                        String _firstLower_8 = StringExtensions.toFirstLower(automatonName_1.getName());
                        _builder_1.append(_firstLower_8, "\t\t");
                        _builder_1.append(".evaluate(event)){");
                        _builder_1.newLineIfNotEmpty();
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("//System.out.println(\"[Aspectj] Before: Event not recognized by the automaton\");");
                        _builder_1.newLine();
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("logger.info(\"[Aspectj] Before: Event not recognized by the automaton: ");
                        String _firstUpper_8 = StringExtensions.toFirstUpper(automatonName_1.getName());
                        _builder_1.append(_firstUpper_8, "\t\t\t");
                        _builder_1.append("\");");
                        _builder_1.newLineIfNotEmpty();
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("//Debería parar");
                        _builder_1.newLine();
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("}else{");
                        _builder_1.newLine();
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append(EketalJvmModelInferrer.reaction, "\t\t\t");
                        _builder_1.append(".verifyBefore(");
                        String _firstLower_9 = StringExtensions.toFirstLower(automatonName_1.getName());
                        _builder_1.append(_firstLower_9, "\t\t\t");
                        _builder_1.append(");");
                        _builder_1.newLineIfNotEmpty();
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("//System.out.println(\"[Aspectj] Before: Recognized event \"+event+\" in ");
                        String _name_3 = automatonName_1.getName();
                        _builder_1.append(_name_3, "\t\t\t");
                        _builder_1.append("\");");
                        _builder_1.newLineIfNotEmpty();
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("logger.info(\"[Aspectj] Before: Recognized event \"+event+\" in ");
                        String _firstUpper_9 = StringExtensions.toFirstUpper(automatonName_1.getName());
                        _builder_1.append(_firstUpper_9, "\t\t\t");
                        _builder_1.append("\");");
                        _builder_1.newLineIfNotEmpty();
                        _builder_1.append("\t");
                        _builder_1.append("\t");
                        _builder_1.append("}");
                        _builder_1.newLine();
                      }
                    }
                  }
                }
              }
            }
            _builder_1.append("\t");
            _builder_1.append("\t");
            _builder_1.append("//while(!");
            _builder_1.append("\t\t\t\t\t\t//\twait(100);");
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
        _builder_2.append(tipo);
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
        importList.add(string.replaceAll(">", ""));
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
    int _length = eventsDefinition.toString().length();
    int _minus = (_length - 1);
    final String pointCutString = _string.substring(1, _minus);
    return pointCutString;
  }
  
  /**
   * El warnning es omitible, dado que no va a fallar bajo ninguna situación
   */
  public String eventExpression(final EventExpression event, final TreeSet<String> pointcuts) {
    EventPredicate _tipoEvento = event.getTipoEvento();
    boolean _tripleNotEquals = (_tipoEvento != null);
    if (_tripleNotEquals) {
      EventPredicate eventKind = event.getTipoEvento();
      boolean _matched = false;
      if (eventKind instanceof Trigger) {
        _matched=true;
        CharSequence[] pointcutTemp = this.returnCall(((Trigger) eventKind));
        String _string = pointcutTemp[1].toString();
        pointcuts.add(_string);
        return pointcutTemp[0].toString();
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
        _builder.append(_eventExpression);
        _builder.append(" && ");
        EventExpression _right = andEvent.getRight();
        Object _eventExpression_1 = this.eventExpression(((EventExpression) _right), pointcuts);
        _builder.append(_eventExpression_1);
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
          _builder.append(_eventExpression);
          _builder.append(" || ");
          EventExpression _right = orEvent.getRight();
          Object _eventExpression_1 = this.eventExpression(((EventExpression) _right), pointcuts);
          _builder.append(_eventExpression_1);
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
          _builder.append(_eventExpression);
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
      int _size = attribute.getCondition().eContents().size();
      String _plus = (Integer.valueOf(_size) + "doGenerate línea 169");
      InputOutput.<String>println(_plus);
      String body = "";
      int _size_1 = attribute.getCondition().eContents().size();
      boolean _equals = (_size_1 == 1);
      if (_equals) {
        EObject _get = attribute.getCondition().eContents().get(0);
        XStringLiteralImpl valone = ((XStringLiteralImpl) _get);
        StringConcatenation _builder = new StringConcatenation();
        String _value = valone.getValue();
        _builder.append(_value);
        body = _builder.toString();
      } else {
        if (((attribute.getCondition().eContents().get(0) instanceof XStringLiteralImpl) && (attribute.getCondition().eContents().get(1) instanceof XStringLiteralImpl))) {
          EObject _get_1 = attribute.getCondition().eContents().get(0);
          XStringLiteralImpl valone_1 = ((XStringLiteralImpl) _get_1);
          EObject _get_2 = attribute.getCondition().eContents().get(1);
          XStringLiteralImpl valtwo = ((XStringLiteralImpl) _get_2);
          StringConcatenation _builder_1 = new StringConcatenation();
          _builder_1.append("\"");
          String _value_1 = valone_1.getValue();
          _builder_1.append(_value_1);
          _builder_1.append("\".equals(\"");
          String _value_2 = valtwo.getValue();
          _builder_1.append(_value_2);
          _builder_1.append("\")");
          body = _builder_1.toString();
        }
      }
      StringConcatenation _builder_2 = new StringConcatenation();
      _builder_2.append("if(");
      _builder_2.append(body);
      _builder_2.append(")");
      return _builder_2.toString();
    } else {
      Group _hostgroup = attribute.getHostgroup();
      boolean _tripleNotEquals = (_hostgroup != null);
      if (_tripleNotEquals) {
        StringConcatenation _builder_3 = new StringConcatenation();
        _builder_3.append("if(");
        _builder_3.append(EketalJvmModelInferrer.groupClassName);
        _builder_3.append(".host(\"");
        String _name = attribute.getHostgroup().getName();
        _builder_3.append(_name);
        _builder_3.append("\"))");
        return _builder_3.toString();
      } else {
        Group _ongroup = attribute.getOngroup();
        boolean _tripleNotEquals_1 = (_ongroup != null);
        if (_tripleNotEquals_1) {
          StringConcatenation _builder_4 = new StringConcatenation();
          _builder_4.append("if(");
          _builder_4.append(EketalJvmModelInferrer.groupClassName);
          _builder_4.append(".on(\"");
          String _name_1 = attribute.getOngroup().getName();
          _builder_4.append(_name_1);
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
      boolean _contains = p.getQualifiedName().contains("$");
      boolean _not = (!_contains);
      if (_not) {
        String _simpleName = p.getSimpleName();
        parameters.add(_simpleName);
      } else {
        String _replace = IterableExtensions.<String>last(((Iterable<String>)Conversions.doWrapArray(p.getQualifiedName().split("\\.")))).replace("$", ".");
        parameters.add(_replace);
      }
    }
    String typeReturn = null;
    JVMTYPE _returndef = trigger.getReturndef();
    boolean _tripleEquals = (_returndef == null);
    if (_tripleEquals) {
      typeReturn = "";
    } else {
      String _astk = trigger.getReturndef().getAstk();
      boolean _tripleEquals_1 = (_astk == null);
      if (_tripleEquals_1) {
        typeReturn = trigger.getReturndef().getJvmRef().getSimpleName();
      } else {
        typeReturn = "*";
      }
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
    String _firstUpper = StringExtensions.toFirstUpper(trigger.getEsig().toString().replaceAll("\\.", ""));
    _builder.append(_firstUpper);
    _builder.append("()");
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("pointcut point");
    String _firstUpper_1 = StringExtensions.toFirstUpper(trigger.getEsig().toString().replaceAll("\\.", ""));
    _builder_1.append(_firstUpper_1);
    _builder_1.append("(): ");
    _builder_1.append(triggerType);
    _builder_1.append("(");
    _builder_1.append(typeReturn);
    _builder_1.append(" ");
    String _esig = trigger.getEsig();
    _builder_1.append(_esig);
    _builder_1.append("(");
    String _join = IterableExtensions.join(parameters, ",");
    _builder_1.append(_join);
    _builder_1.append("))");
    CharSequence[] returnCall = ((CharSequence[])Conversions.unwrapArray(CollectionLiterals.<CharSequence>newArrayList(_builder.toString(), _builder_1.toString()), CharSequence.class));
    return returnCall;
  }
}
