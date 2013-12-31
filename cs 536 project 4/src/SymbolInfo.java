/**************************************************
 *  class used to hold information associated w/
 *  Symbs (which are stored in SymbolTables)
 *  Update to handle arrays and methods
 *
 ****************************************************/

import java.util.*;

class SymbolInfo extends Symb {
	public ASTNode.Kinds kind;
	public ASTNode.Types type;
	//length holds length of array
	public int length;
	//method list is list of all methods in program
	public static ArrayList<methodDeclNode> methodList = new ArrayList<methodDeclNode>();
	//argStuffList is list of types and kinds of args for a method
	public ArrayList<ArgStuff> argStuffList = new ArrayList<ArgStuff>();
	public static ArrayList<ArgStuff> argCheckingList = new ArrayList<ArgStuff>();

	public SymbolInfo(String id, ASTNode.Kinds k, ASTNode.Types t){    
		super(id);
		kind = k; type = t; length = 0;
	}
	
	public String toString(){
		return "("+name()+": kind=" + kind+ ", type="+  type+")";
	}
}

//types and kinds for a method
class ArgStuff{
	ASTNode.Types type;
	ASTNode.Kinds kind;
	public ArgStuff(ASTNode.Types t, ASTNode.Kinds k){
		type = t;
		kind = k;
	}
}