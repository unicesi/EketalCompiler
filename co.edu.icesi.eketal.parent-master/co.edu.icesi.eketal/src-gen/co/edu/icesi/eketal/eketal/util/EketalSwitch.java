/**
 * generated by Xtext 2.12.0
 */
package co.edu.icesi.eketal.eketal.util;

import co.edu.icesi.eketal.eketal.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see co.edu.icesi.eketal.eketal.EketalPackage
 * @generated
 */
public class EketalSwitch<T> extends Switch<T>
{
  /**
   * The cached model package
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static EketalPackage modelPackage;

  /**
   * Creates an instance of the switch.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EketalSwitch()
  {
    if (modelPackage == null)
    {
      modelPackage = EketalPackage.eINSTANCE;
    }
  }

  /**
   * Checks whether this is a switch for the given package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param ePackage the package in question.
   * @return whether this is a switch for the given package.
   * @generated
   */
  @Override
  protected boolean isSwitchFor(EPackage ePackage)
  {
    return ePackage == modelPackage;
  }

  /**
   * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the first non-null result returned by a <code>caseXXX</code> call.
   * @generated
   */
  @Override
  protected T doSwitch(int classifierID, EObject theEObject)
  {
    switch (classifierID)
    {
      case EketalPackage.MODEL:
      {
        Model model = (Model)theEObject;
        T result = caseModel(model);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.EVENT_CLASS:
      {
        EventClass eventClass = (EventClass)theEObject;
        T result = caseEventClass(eventClass);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.DECL:
      {
        Decl decl = (Decl)theEObject;
        T result = caseDecl(decl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.JVAR_D:
      {
        JVarD jVarD = (JVarD)theEObject;
        T result = caseJVarD(jVarD);
        if (result == null) result = caseDecl(jVarD);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.MSIG:
      {
        MSig mSig = (MSig)theEObject;
        T result = caseMSig(mSig);
        if (result == null) result = caseDecl(mSig);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.EV_DECL:
      {
        EvDecl evDecl = (EvDecl)theEObject;
        T result = caseEvDecl(evDecl);
        if (result == null) result = caseDecl(evDecl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.EVENT_EXPRESSION:
      {
        EventExpression eventExpression = (EventExpression)theEObject;
        T result = caseEventExpression(eventExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.EVENT_PREDICATE:
      {
        EventPredicate eventPredicate = (EventPredicate)theEObject;
        T result = caseEventPredicate(eventPredicate);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.KIND_ATTRIBUTE:
      {
        KindAttribute kindAttribute = (KindAttribute)theEObject;
        T result = caseKindAttribute(kindAttribute);
        if (result == null) result = caseEventPredicate(kindAttribute);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.TRIGGER:
      {
        Trigger trigger = (Trigger)theEObject;
        T result = caseTrigger(trigger);
        if (result == null) result = caseEventPredicate(trigger);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.JVMTYPE:
      {
        JVMTYPE jvmtype = (JVMTYPE)theEObject;
        T result = caseJVMTYPE(jvmtype);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.GROUP:
      {
        Group group = (Group)theEObject;
        T result = caseGroup(group);
        if (result == null) result = caseDecl(group);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.HOST:
      {
        Host host = (Host)theEObject;
        T result = caseHost(host);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.INTERVAL_IP:
      {
        Interval_Ip interval_Ip = (Interval_Ip)theEObject;
        T result = caseInterval_Ip(interval_Ip);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.AUTOMATON:
      {
        Automaton automaton = (Automaton)theEObject;
        T result = caseAutomaton(automaton);
        if (result == null) result = caseDecl(automaton);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.STEP:
      {
        Step step = (Step)theEObject;
        T result = caseStep(step);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.TRANS_DEF:
      {
        TransDef transDef = (TransDef)theEObject;
        T result = caseTransDef(transDef);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.LTL:
      {
        Ltl ltl = (Ltl)theEObject;
        T result = caseLtl(ltl);
        if (result == null) result = caseDecl(ltl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.LTL_EXPRESSION:
      {
        LtlExpression ltlExpression = (LtlExpression)theEObject;
        T result = caseLtlExpression(ltlExpression);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.RC:
      {
        Rc rc = (Rc)theEObject;
        T result = caseRc(rc);
        if (result == null) result = caseDecl(rc);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.BODY:
      {
        Body body = (Body)theEObject;
        T result = caseBody(body);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.OR_EVENT:
      {
        OrEvent orEvent = (OrEvent)theEObject;
        T result = caseOrEvent(orEvent);
        if (result == null) result = caseEventExpression(orEvent);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.AND_EVENT:
      {
        AndEvent andEvent = (AndEvent)theEObject;
        T result = caseAndEvent(andEvent);
        if (result == null) result = caseEventExpression(andEvent);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.UNARY_EVENT:
      {
        UnaryEvent unaryEvent = (UnaryEvent)theEObject;
        T result = caseUnaryEvent(unaryEvent);
        if (result == null) result = caseEventExpression(unaryEvent);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.LTL_THEN:
      {
        LtlThen ltlThen = (LtlThen)theEObject;
        T result = caseLtlThen(ltlThen);
        if (result == null) result = caseLtlExpression(ltlThen);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.LTL_OR:
      {
        LtlOr ltlOr = (LtlOr)theEObject;
        T result = caseLtlOr(ltlOr);
        if (result == null) result = caseLtlExpression(ltlOr);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.LTL_AND:
      {
        LtlAnd ltlAnd = (LtlAnd)theEObject;
        T result = caseLtlAnd(ltlAnd);
        if (result == null) result = caseLtlExpression(ltlAnd);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.LTL_UNTIL:
      {
        LtlUntil ltlUntil = (LtlUntil)theEObject;
        T result = caseLtlUntil(ltlUntil);
        if (result == null) result = caseLtlExpression(ltlUntil);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      case EketalPackage.UNARY_LTL:
      {
        UnaryLtl unaryLtl = (UnaryLtl)theEObject;
        T result = caseUnaryLtl(unaryLtl);
        if (result == null) result = caseLtlExpression(unaryLtl);
        if (result == null) result = defaultCase(theEObject);
        return result;
      }
      default: return defaultCase(theEObject);
    }
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Model</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Model</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseModel(Model object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Event Class</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Event Class</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEventClass(EventClass object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseDecl(Decl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>JVar D</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>JVar D</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJVarD(JVarD object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>MSig</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>MSig</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseMSig(MSig object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ev Decl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ev Decl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEvDecl(EvDecl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Event Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Event Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEventExpression(EventExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Event Predicate</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Event Predicate</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseEventPredicate(EventPredicate object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Kind Attribute</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Kind Attribute</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseKindAttribute(KindAttribute object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Trigger</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Trigger</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTrigger(Trigger object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>JVMTYPE</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>JVMTYPE</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseJVMTYPE(JVMTYPE object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Group</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Group</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseGroup(Group object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Host</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Host</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseHost(Host object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Interval Ip</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Interval Ip</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseInterval_Ip(Interval_Ip object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Automaton</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Automaton</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAutomaton(Automaton object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Step</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Step</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseStep(Step object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Trans Def</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Trans Def</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseTransDef(TransDef object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ltl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ltl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLtl(Ltl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ltl Expression</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ltl Expression</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLtlExpression(LtlExpression object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Rc</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Rc</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseRc(Rc object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Body</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Body</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseBody(Body object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Or Event</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Or Event</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseOrEvent(OrEvent object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>And Event</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>And Event</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseAndEvent(AndEvent object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Unary Event</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Unary Event</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUnaryEvent(UnaryEvent object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ltl Then</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ltl Then</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLtlThen(LtlThen object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ltl Or</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ltl Or</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLtlOr(LtlOr object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ltl And</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ltl And</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLtlAnd(LtlAnd object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Ltl Until</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Ltl Until</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseLtlUntil(LtlUntil object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>Unary Ltl</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>Unary Ltl</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
   * @generated
   */
  public T caseUnaryLtl(UnaryLtl object)
  {
    return null;
  }

  /**
   * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
   * <!-- begin-user-doc -->
   * This implementation returns null;
   * returning a non-null result will terminate the switch, but this is the last case anyway.
   * <!-- end-user-doc -->
   * @param object the target of the switch.
   * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
   * @see #doSwitch(org.eclipse.emf.ecore.EObject)
   * @generated
   */
  @Override
  public T defaultCase(EObject object)
  {
    return null;
  }

} //EketalSwitch
