
//----------------------------------------------------
// The following code was generated by CUP v0.10f
// Mon Jan 26 16:59:57 CST 1998
//----------------------------------------------------

package java_cup.simple_calc;

import java_cup.runtime.*;

public class parser extends java_cup.runtime.lr_parser {

  /** constructor */
  public parser() {super();}

  /** production table */
  protected static final short _production_table[][] = {
        {1, 2},     {0, 2},     {1, 1},     {4, 0},     {2, 3}, 
        {3, 3},     {3, 3},     {3, 3},     {3, 3},     {3, 3}, 
        {3, 1},     {3, 2},     {3, 3}  };

  /** access to production table */
  public short[][] production_table() {return _production_table;}

  /** parse action table */
  protected static final short[][] _action_table = {
    /*0*/{4,2,9,7,11,3,-1,0},
    /*1*/{4,2,9,7,11,3,-1,0},
    /*2*/{2,-11,3,-11,4,-11,5,-11,6,-11,7,-11,10,-11,-1,0},
    /*3*/{0,-3,4,-3,9,-3,11,-3,-1,0},
    /*4*/{2,-4,3,12,4,10,5,14,6,13,7,9,-1,0},
    /*5*/{0,21,4,2,9,7,11,3,-1,0},
    /*6*/{4,2,9,7,11,3,-1,0},
    /*7*/{3,12,4,10,5,14,6,13,7,9,10,11,-1,0},
    /*8*/{4,2,9,7,11,3,-1,0},
    /*9*/{4,2,9,7,11,3,-1,0},
    /*10*/{2,-13,3,-13,4,-13,5,-13,6,-13,7,-13,10,-13,-1,0},
    /*11*/{4,2,9,7,11,3,-1,0},
    /*12*/{4,2,9,7,11,3,-1,0},
    /*13*/{4,2,9,7,11,3,-1,0},
    /*14*/{2,-8,3,-8,4,-8,5,-8,6,-8,7,-8,10,-8,-1,0},
    /*15*/{2,-9,3,-9,4,-9,5,-9,6,-9,7,-9,10,-9,-1,0},
    /*16*/{2,-6,3,-6,4,-6,5,14,6,13,7,9,10,-6,-1,0},
    /*17*/{2,-7,3,-7,4,-7,5,14,6,13,7,9,10,-7,-1,0},
    /*18*/{2,-10,3,-10,4,-10,5,-10,6,-10,7,-10,10,-10,-1,0},
    /*19*/{0,-1,4,-1,9,-1,11,-1,-1,0},
    /*20*/{0,-2,-1,0},
    /*21*/{2,23,-1,0},
    /*22*/{0,-5,4,-5,9,-5,11,-5,-1,0},
    /*23*/{2,-12,3,-12,4,-12,5,-12,6,-12,7,-12,10,-12,-1,0},
  };

  /** access to parse action table */
  public short[][] action_table() {return _action_table;}

  /** reduce_goto table */
  protected static final short[][] _reduce_table = {
    /*0*/{1,5,2,3,3,4,-1,-1},
    /*1*/{3,23,-1,-1},
    /*2*/{-1,-1},
    /*3*/{-1,-1},
    /*4*/{4,21,-1,-1},
    /*5*/{2,19,3,4,-1,-1},
    /*6*/{3,7,-1,-1},
    /*7*/{-1,-1},
    /*8*/{3,18,-1,-1},
    /*9*/{3,17,-1,-1},
    /*10*/{-1,-1},
    /*11*/{3,16,-1,-1},
    /*12*/{3,15,-1,-1},
    /*13*/{3,14,-1,-1},
    /*14*/{-1,-1},
    /*15*/{-1,-1},
    /*16*/{-1,-1},
    /*17*/{-1,-1},
    /*18*/{-1,-1},
    /*19*/{-1,-1},
    /*20*/{-1,-1},
    /*21*/{-1,-1},
    /*22*/{-1,-1},
    /*23*/{-1,-1},
  };

  /** access to reduce_goto table */
  public short[][] reduce_table() {return _reduce_table;}

  /** instance of action encapsulation class */
  protected CUP$actions action_obj;

  /** action encapsulation object initializer */
  protected void init_actions()
    {
      action_obj = new CUP$actions();
    }

  /** invoke a user supplied parse action */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$do_action(act_num, parser, stack, top);
  }

  /** start state */
  public int start_state() {return 0;}
  /** start production */
  public int start_production() {return 1;}

  /** EOF Symbol index */
  public int EOF_sym() {return 0;}

  /** error Symbol index */
  public int error_sym() {return 1;}


  /** user initialization */
  public void user_init() throws java.lang.Exception
    {
 scanner.init();              
    }

  /** scan to get the next Symbol */
  public java_cup.runtime.Symbol scan()
    throws java.lang.Exception
    {
 return scanner.next_token(); 
    }
}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$actions {

  /** Constructor */
  CUP$actions() { }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$do_action(
    int                        CUP$act_num,
    java_cup.runtime.lr_parser CUP$parser,
    java.util.Stack            CUP$stack,
    int                        CUP$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$result;

      /* select the action based on the action number */
      switch (CUP$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // expr ::= LPAREN expr RPAREN 
            {
              Integer RESULT = null;
		int eleft = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-1)).right;
		Integer e = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-1)).value;
		 RESULT = e; 
              CUP$result = new java_cup.runtime.Symbol(3/*expr*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // expr ::= MINUS expr 
            {
              Integer RESULT = null;
		int eleft = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right;
		Integer e = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-0)).value;
		 RESULT = new Integer(0 - e.intValue()); 
              CUP$result = new java_cup.runtime.Symbol(3/*expr*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-1)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // expr ::= NUMBER 
            {
              Integer RESULT = null;
		int nleft = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).left;
		int nright = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right;
		Integer n = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-0)).value;
		 RESULT = n; 
              CUP$result = new java_cup.runtime.Symbol(3/*expr*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // expr ::= expr MOD expr 
            {
              Integer RESULT = null;
		int e1left = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).right;
		Integer e1 = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right;
		Integer e2 = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-0)).value;
		 RESULT = new Integer(e1.intValue() % e2.intValue()); 
              CUP$result = new java_cup.runtime.Symbol(3/*expr*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // expr ::= expr DIVIDE expr 
            {
              Integer RESULT = null;
		int e1left = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).right;
		Integer e1 = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right;
		Integer e2 = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-0)).value;
		 RESULT = new Integer(e1.intValue() / e2.intValue()); 
              CUP$result = new java_cup.runtime.Symbol(3/*expr*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // expr ::= expr TIMES expr 
            {
              Integer RESULT = null;
		int e1left = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).right;
		Integer e1 = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right;
		Integer e2 = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-0)).value;
		 RESULT = new Integer(e1.intValue() * e2.intValue()); 
              CUP$result = new java_cup.runtime.Symbol(3/*expr*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // expr ::= expr MINUS expr 
            {
              Integer RESULT = null;
		int e1left = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).right;
		Integer e1 = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right;
		Integer e2 = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-0)).value;
		 RESULT = new Integer(e1.intValue() - e2.intValue()); 
              CUP$result = new java_cup.runtime.Symbol(3/*expr*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // expr ::= expr PLUS expr 
            {
              Integer RESULT = null;
		int e1left = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).right;
		Integer e1 = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-2)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right;
		Integer e2 = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-0)).value;
		 RESULT = new Integer(e1.intValue() + e2.intValue()); 
              CUP$result = new java_cup.runtime.Symbol(3/*expr*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // expr_part ::= expr NT$0 SEMI 
            {
              Object RESULT = null;
		int eleft = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).right;
		Integer e = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-2)).value;

              CUP$result = new java_cup.runtime.Symbol(2/*expr_part*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-2)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // NT$0 ::= 
            {
              Object RESULT = null;
		int eleft = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right;
		Integer e = (Integer)((java_cup.runtime.Symbol) CUP$stack.elementAt(CUP$top-0)).value;
 System.out.println("= " + e); 
              CUP$result = new java_cup.runtime.Symbol(4/*NT$0*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // expr_list ::= expr_part 
            {
              Object RESULT = null;

              CUP$result = new java_cup.runtime.Symbol(1/*expr_list*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= expr_list EOF 
            {
              Object RESULT = null;

              CUP$result = new java_cup.runtime.Symbol(0/*$START*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-1)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          /* ACCEPT */
          CUP$parser.done_parsing();
          return CUP$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // expr_list ::= expr_list expr_part 
            {
              Object RESULT = null;

              CUP$result = new java_cup.runtime.Symbol(1/*expr_list*/, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-1)).left, ((java_cup.runtime.Symbol)CUP$stack.elementAt(CUP$top-0)).right, RESULT);
            }
          return CUP$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

