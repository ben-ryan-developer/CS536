// The following methods type check  AST nodes used in CSX
import java.util.*;

public class TypeChecking extends Visitor { 
	private boolean shouldInsert = true;
	int typeErrors;     // Total number of type errors found 
	SymbolTable st;	
	methodDeclNode currentMethod;

	TypeChecking(){
		typeErrors = 0;
		st = new SymbolTable(); 
	}

	boolean isTypeCorrect(csxLiteNode n) {
		this.visit(n);
		return (typeErrors == 0);
	}

	boolean isTypeCorrect(classNode n) {
		n.className.kind = ASTNode.Kinds.VisibleLabel;
		n.className.type = ASTNode.Types.Void;
		this.visit(n);
		return (typeErrors == 0);
	}

	static void assertCondition(boolean assertion){  
		if (! assertion)
			throw new RuntimeException();
	}
	void typeMustBe(ASTNode.Types testType,ASTNode.Types requiredType,String errorMsg) {
		if ((testType != ASTNode.Types.Error) && (testType != requiredType)) {
			System.out.println(errorMsg);
			typeErrors++;
			testType = ASTNode.Types.Error;
		}
	}

	//checks multiple types
	void typeMustBe(ASTNode.Types testType, ArrayList<ASTNode.Types> requiredTypes, String errorMsg){
		for(int i = 0; i < requiredTypes.size(); i++){
			if((testType == ASTNode.Types.Error) || (testType == requiredTypes.get(i))){
				return;
			}
		}
		System.out.println(errorMsg);
		typeErrors++;
		testType = ASTNode.Types.Error;
	}

	void kindMustBe(ASTNode.Kinds testKind, ASTNode.Kinds requiredKind, String errorMsg) {
		if(testKind != requiredKind){
			System.out.println(errorMsg);
			typeErrors++;
		}
	}

	//check multiple kinds
	void kindMustBe(ASTNode.Kinds testKind, ArrayList<ASTNode.Kinds> requiredKinds, String errorMsg){
		for(int i = 0; i < requiredKinds.size(); i++){
			if(testKind == requiredKinds.get(i)){
				return;
			}
		}
		System.out.println(errorMsg);
		typeErrors++;
	}

	void typesMustBeEqual(ASTNode.Types type1,ASTNode.Types type2,String errorMsg) {
		if ((type1 != ASTNode.Types.Error) && (type2 != ASTNode.Types.Error) &&
				(type1 != type2)) {
			System.out.println(errorMsg);
			typeErrors++;
		}
	}
	String error(ASTNode n) {
		return "Error (line " + n.linenum + "): ";
	}

	static String opToString(int op) {
		switch (op) {
		case sym.PLUS:
			return(" + ");
		case sym.MINUS:
			return(" - ");
		case sym.EQ:
			return(" == ");
		case sym.NOTEQ:
			return(" != ");
		case sym.TIMES:
			return(" * ");
		case sym.SLASH:
			return(" / ");
		case sym.COR:
			return(" || ");
		case sym.CAND:
			return(" && ");
		case sym.INCREMENT:
			return(" ++ ");
		case sym.DECREMENT:
			return(" -- ");
		case sym.NOT:
			return(" ! ");
		case sym.GEQ:
			return(" >= ");
		case sym.GT:
			return(" > ");
		case sym.LEQ:
			return(" <= ");
		case sym.LT:
			return(" < ");
		default:
			assertCondition(false);
			return "";
		}
	}


	static void printOp(int op) {
		switch (op) {
		case sym.PLUS:
			System.out.print(" + ");
			break;
		case sym.MINUS:
			System.out.print(" - ");
			break;
		case sym.EQ:
			System.out.print(" == ");
			break;
		case sym.NOTEQ:
			System.out.print(" != ");
			break;
		case sym.TIMES:
			System.out.print(" * ");
			break;
		case sym.SLASH:
			System.out.print(" / ");
			break;
		case sym.COR:
			System.out.print(" || ");
			break;
		case sym.CAND:
			System.out.print(" && ");
			break;
		case sym.GEQ:
			System.out.print(" >= ");
			break;
		case sym.GT:
			System.out.print(" > ");
			break;
		case sym.LEQ:
			System.out.print(" <= ");
			break;
		case sym.LT:
			System.out.print(" < ");
			break;
		default:
			throw new Error();
		}
	}


	void visit(csxLiteNode n){
		this.visit(n.progDecls);
		this.visit(n.progStmts);
	}

	void visit(fieldDeclsNode n){
		this.visit(n.thisField);
		this.visit(n.moreFields);
	}
	void visit(nullFieldDeclsNode n){}

	void visit(stmtsNode n){
		this.visit(n.thisStmt);
		this.visit(n.moreStmts);

	}
	void visit(nullStmtsNode n){}

	void visit(varDeclNode n){
		SymbolInfo     id = (SymbolInfo) st.localLookup(n.varName.idname);
		if (id != null) {
			System.out.println(error(n) + id.name() + " is already declared.");
			typeErrors++;
			n.varName.type = ASTNode.Types.Error;
		} 
		else {
			this.visit(n.initValue);
			if (n.initValue.isNull()){
				id = new SymbolInfo(n.varName.idname, ASTNode.Kinds.Var, n.varType.type);
				try {
					st.insert(id);
				} catch (DuplicateException d) 
				{ /* can't happen */ }
				catch (EmptySTException e) 
				{ /* can't happen */ }
				n.varName.idinfo=id;
				return;
			}
			exprNode temp = (exprNode)n.initValue;
			typeMustBe(temp.type, n.varType.type, error(n) + "The initializer must be of type " + n.varType.type.name());
			ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
			possibleKinds.add(ASTNode.Kinds.Value);
			possibleKinds.add(ASTNode.Kinds.Var);
			possibleKinds.add(ASTNode.Kinds.ScalarParm);
			kindMustBe(temp.kind, possibleKinds, error(n) + "Initial value's Kind must be scalar");
			id = new SymbolInfo(n.varName.idname, ASTNode.Kinds.Var, n.varType.type);
			try {
				st.insert(id);
			} catch (DuplicateException d) 
			{ /* can't happen */ }
			catch (EmptySTException e) 
			{ /* can't happen */ }
			n.varName.idinfo=id;
		}
	}

	void visit(nullTypeNode n){}

	void visit(intTypeNode n){
		n.type = ASTNode.Types.Integer;
	}
	void visit(boolTypeNode n){
		n.type = ASTNode.Types.Boolean;
	}
	void visit(identNode n){
		SymbolInfo id = (SymbolInfo) st.globalLookup(n.idname);
		if (id == null) {
			System.out.println(error(n) +  n.idname + " is not declared.");
			typeErrors++;
			n.type = ASTNode.Types.Error;
		} 
		else {
			n.type = id.type; 
			n.kind = id.kind;
			n.idinfo = id;
		}
	}

	void visit(nameNode n){
		this.visit(n.varName);
		if(n.subscriptVal.isNull()){
			n.type = n.varName.type;
			n.kind = n.varName.kind;
			return;
		}
		else {
			this.visit(n.subscriptVal);
			if(n.varName.kind != ASTNode.Kinds.Array){
				System.out.println(error(n) + "Only arrays can be subscripted.");
				typeErrors++;
				n.type = ASTNode.Types.Error;
				return;
			}
			ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
			possibleKinds.add(ASTNode.Kinds.Value);
			possibleKinds.add(ASTNode.Kinds.Var);
			possibleKinds.add(ASTNode.Kinds.ScalarParm);
			exprNode temp = (exprNode)n.subscriptVal;
			kindMustBe(temp.kind, possibleKinds, error(n) + "subscript's Kind must be scalar");
			ArrayList<ASTNode.Types> possibleTypes = new ArrayList<ASTNode.Types>();
			possibleTypes.add(ASTNode.Types.Integer);
			possibleTypes.add(ASTNode.Types.Character);
			typeMustBe(temp.type, possibleTypes, error(n) + "Array subscripts must be integer or character expressions.");
			n.type = n.varName.type;
			n.kind = ASTNode.Kinds.Var;
		}
	}

	void visit(asgNode n){
		this.visit(n.target);
		this.visit(n.source);
		if(n.target.kind == ASTNode.Kinds.Var || n.target.kind == ASTNode.Kinds.Array 
				|| n.target.kind == ASTNode.Kinds.ScalarParm || n.target.kind == ASTNode.Kinds.ArrayParm){
			if(n.target.kind == ASTNode.Kinds.ScalarParm || n.target.kind == ASTNode.Kinds.Value || n.target.kind == ASTNode.Kinds.Var){
				ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
				possibleKinds.add(ASTNode.Kinds.Value);
				possibleKinds.add(ASTNode.Kinds.Var);
				possibleKinds.add(ASTNode.Kinds.ScalarParm);
				for(int i = 0; i < possibleKinds.size(); i++){
					if((n.source.kind == possibleKinds.get(i) && n.target.type == n.source.type) || n.source.type == ASTNode.Types.Error 
							|| n.target.type == ASTNode.Types.Error){
						return;
					}
				}
				System.out.println(error(n) + "Right hand side of an assignment is not assignable to left hand side.");
				n.source.type = ASTNode.Types.Error;
				typeErrors++;
				return;
			}
			else if(n.target.kind == ASTNode.Kinds.Array && n.source.kind == ASTNode.Kinds.Array && (n.target.type == n.source.type)){
				if(n.target.varName.idinfo.length != ((nameNode)n.source).varName.idinfo.length){ 
					System.out.println(error(n) + "Source and target of the assignment must have the same length.");
					typeErrors++;
					n.source.type = ASTNode.Types.Error;
				}
				return;
			}
			else if(n.target.kind == ASTNode.Kinds.Array && n.target.type == ASTNode.Types.Character && n.source.kind == ASTNode.Kinds.String){
				if(n.target.varName.idinfo.length != ((strLitNode)n.source).strval.length()){
					System.out.println(error(n) + "Source and target of the assignment must have the same length.");
					typeErrors++;
					n.source.type = ASTNode.Types.Error;
				}
				return;
			}
			System.out.println(error(n) + "Right hand side of an assignment is not assignable to left hand side.");
			typeErrors++;
			n.source.type = ASTNode.Types.Error;
			return;
		}
		System.out.println(error(n) + "Target of assignment can't be changed.");
		typeErrors++;
		n.source.type = ASTNode.Types.Error;
	}

	void visit(ifThenNode n){
		this.visit(n.condition);
		typeMustBe(n.condition.type, ASTNode.Types.Boolean,
				error(n) + "The control expression of an if must be a bool.");
		if(n.condition.type != ASTNode.Types.Boolean){
			System.out.println(error(n) + "The control expression of an if must be a bool.");
			typeErrors++;
		}
		else{
			ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
			possibleKinds.add(ASTNode.Kinds.Value);
			possibleKinds.add(ASTNode.Kinds.Var);
			possibleKinds.add(ASTNode.Kinds.ScalarParm);
			kindMustBe(n.condition.kind, possibleKinds, error(n) + "Condition must be a scalar.");
		}
		this.visit(n.thenPart);
		this.visit(n.elsePart);
	}

	void visit(printNode n){
		this.visit(n.outputValue);
		ArrayList<ASTNode.Types> possibleTypes = new ArrayList<ASTNode.Types>();
		possibleTypes.add(ASTNode.Types.Integer);
		possibleTypes.add(ASTNode.Types.Boolean);
		possibleTypes.add(ASTNode.Types.Character);
		typeMustBe(n.outputValue.type, possibleTypes, error(n) + "Only integers, booleans, strings, characters and character arrays may be written.");

		if(n.outputValue.kind == ASTNode.Kinds.Array || n.outputValue.kind == ASTNode.Kinds.ArrayParm){
			if(n.outputValue.type == ASTNode.Types.Integer || n.outputValue.type == ASTNode.Types.Boolean){
				System.out.println(error(n) + "Only integers, booleans, strings, characters and character arrays may be written.");
				typeErrors++;
				n.outputValue.type = ASTNode.Types.Error;
				return;
			}
		}
		ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
		possibleKinds.add(ASTNode.Kinds.ScalarParm);
		possibleKinds.add(ASTNode.Kinds.String);
		possibleKinds.add(ASTNode.Kinds.Var);
		possibleKinds.add(ASTNode.Kinds.Value);
		kindMustBe(n.outputValue.kind, possibleKinds, error(n) + "Only integers, booleans, strings, characters and character arrays may be written.");
		this.visit(n.morePrints);
	}

	void visit(blockNode n){
		// open a new local scope for the block body
		st.openScope();
		this.visit(n.decls);
		this.visit(n.stmts);
		// close this block's local scope
		try { st.closeScope();
		}  catch (EmptySTException e) 
		{ /* can't happen */ }
	}

	void typesMustBeComparable(ASTNode.Types type1,ASTNode.Types type2,
			String errorMsg) {
		if ((type1 == ASTNode.Types.Error) || (type2 == ASTNode.Types.Error))
			return;
		if ((type1 == ASTNode.Types.Boolean) && (type2 == ASTNode.Types.Boolean))
			return;
		if (((type1 == ASTNode.Types.Integer) || (type1 == ASTNode.Types.Character)) &&
				((type2 == ASTNode.Types.Integer) || (type2 == ASTNode.Types.Character)))
			return;
		System.out.println(errorMsg);
		typeErrors++;
	}

	void visit(binaryOpNode n){
		this.visit(n.leftOperand);
		this.visit(n.rightOperand);
		ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
		possibleKinds.add(ASTNode.Kinds.Value);
		possibleKinds.add(ASTNode.Kinds.Var);
		possibleKinds.add(ASTNode.Kinds.ScalarParm);
		kindMustBe(n.leftOperand.kind, possibleKinds, error(n) + "Left operand of" + opToString(n.operatorCode) + 
				"must be a scalar.");
		kindMustBe(n.rightOperand.kind, possibleKinds, error(n) + "Right operand of" + opToString(n.operatorCode) + 
				"must be a scalar.");
		n.kind = ASTNode.Kinds.Value;
		ASTNode.Types type1 = n.leftOperand.type;
		ASTNode.Types type2 = n.rightOperand.type;
		if(n.operatorCode == sym.TIMES || n.operatorCode == sym.SLASH || n.operatorCode == sym.PLUS || n.operatorCode == sym.MINUS){
			if (!((type1 == ASTNode.Types.Integer) || (type1 == ASTNode.Types.Character)) && type1 != ASTNode.Types.Error) {
				System.out.println(error(n) + "Left operand of" + opToString(n.operatorCode) + "must be arithmetic.");
				typeErrors++;
			}
			if(!((type2 == ASTNode.Types.Integer) || (type2 == ASTNode.Types.Character)) && type2 != ASTNode.Types.Error){
				System.out.println(error(n) + "Right operand of" + opToString(n.operatorCode) + "must be arithmetic.");
				typeErrors++;
			}
			n.type = ASTNode.Types.Integer;
		}
		else if(n.operatorCode == sym.CAND || n.operatorCode == sym.COR){
			if (!(type1 == ASTNode.Types.Boolean) && type1 != ASTNode.Types.Error){
				System.out.println(error(n) + "Left operand of" + opToString(n.operatorCode) + "must be a bool.");
				typeErrors++;
			}
			if(!(type2 == ASTNode.Types.Boolean) && type2!= ASTNode.Types.Error)
			{
				System.out.println(error(n) + "Right operand of" + opToString(n.operatorCode) + "must be a bool.");
				typeErrors++;
			}
			n.type = ASTNode.Types.Boolean;
		}
		else{
			String errorMsg = error(n) + "Operands of" + opToString(n.operatorCode) + "must both be arithmetic or both must be boolean.";
			typesMustBeComparable(n.leftOperand.type, n.rightOperand.type, errorMsg);
			n.type = ASTNode.Types.Boolean;
		}
	}

	void visit(intLitNode n){
		n.type = ASTNode.Types.Integer;
	}

	void visit(classNode n){
		this.visit(n.members);
	}

	void  visit(memberDeclsNode n){
		this.visit(n.fields);
		this.visit(n.methods);
	}

	void  visit(methodDeclsNode n){
		this.visit(n.thisDecl);
		this.visit(n.moreDecls);
	}

	void visit(nullStmtNode n){}

	void visit(nullReadNode n){}

	void visit(nullPrintNode n){}

	void visit(nullExprNode n){}

	void visit(nullMethodDeclsNode n){}

	void visit(methodDeclNode n){ 
		SymbolInfo m = new SymbolInfo(n.name.idname, ASTNode.Kinds.Method, n.returnType.type);
		SymbolInfo temp = (SymbolInfo)st.localLookup(n.name.idname);
		if(temp != null){
			if(temp.kind != ASTNode.Kinds.Method){
				typeErrors++;
				System.out.println(error(n) + n.name.idname + " is already declared.");
				this.visit(n.args); 
				this.visit(n.decls);
				this.visit(n.stmts);
				return;
			}
			for(int i = 0; i < SymbolInfo.methodList.size(); i++){
				if(n.name.idname.equals(SymbolInfo.methodList.get(i).name.idname)){
					shouldInsert = false;
					//set should insert to false so we can check the array args and store them in the static args list to 
					//compare them to other method args
					st.openScope();
					this.visit(n.args);
					try{
						st.closeScope();
					}catch(EmptySTException e){
						/*can't happen*/
					}
					shouldInsert = true;
					ArrayList<ArgStuff> nList = SymbolInfo.argCheckingList;
					//ArrayList<ArgStuff> tempList = ((SymbolInfo)st.globalLookup(SymbolInfo.methodList.get(i).name.idname)).argStuffList;
					ArrayList<ArgStuff> tempList = SymbolInfo.methodList.get(i).name.idinfo.argStuffList;
					if(nList.size() != tempList.size()){
						break;
					}
					boolean overload = false;
					for(int j = 0; j < nList.size(); j++){
						if(nList.get(j).type != tempList.get(j).type || nList.get(j).kind != tempList.get(j).kind){
							overload = true;
							break;
						}
					}
					if(overload == true){
						break;
					}
					typeErrors++;
					currentMethod = n;
					System.out.println(error(n) + n.name.idname + " is already declared.");
					this.visit(n.args); 
					this.visit(n.decls);
					this.visit(n.stmts);
					return;
				}
			}
		}
		try {
			st.insert(m);
		} catch (DuplicateException d) 
		{ /* can't happen */ } 
		catch (EmptySTException e) 
		{ /* can't happen */ }
		SymbolInfo.methodList.add(n);
		st.openScope();
		n.name.idinfo = m;
		n.name.type = m.type;
		n.name.kind = m.kind;
		currentMethod = n;
		this.visit(n.args); 
		this.visit(n.decls);
		this.visit(n.stmts);
		try{
			st.closeScope();
		}catch(EmptySTException e){
			/*can't happen*/
		}
	}
	void visit(incrementNode n){
		this.visit(n.target);
		ArrayList<ASTNode.Types> possibleTypes = new ArrayList<ASTNode.Types>();
		possibleTypes.add(ASTNode.Types.Integer);
		possibleTypes.add(ASTNode.Types.Character);
		typeMustBe(n.target.type, possibleTypes, error(n) + "Operand of ++ must be arithmetic.");
		ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
		possibleKinds.add(ASTNode.Kinds.Value);
		possibleKinds.add(ASTNode.Kinds.Var);
		possibleKinds.add(ASTNode.Kinds.ScalarParm);
		kindMustBe(n.target.kind, possibleKinds, error(n) + "Operand of ++ must be a scalar.");
		possibleKinds = new ArrayList<ASTNode.Kinds>();
		possibleKinds.add(ASTNode.Kinds.Var);
		possibleKinds.add(ASTNode.Kinds.Array);
		possibleKinds.add(ASTNode.Kinds.ScalarParm);
		possibleKinds.add(ASTNode.Kinds.ArrayParm);
		kindMustBe(n.target.kind, possibleKinds, error(n) + "Target of ++ can't be changed.");
		n.target.type = ASTNode.Types.Integer;
	}
	void visit(decrementNode n){
		this.visit(n.target);
		ArrayList<ASTNode.Types> possibleTypes = new ArrayList<ASTNode.Types>();
		possibleTypes.add(ASTNode.Types.Integer);
		possibleTypes.add(ASTNode.Types.Character);
		typeMustBe(n.target.type, possibleTypes, error(n) + "Operand of -- must be arithmetic.");
		ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
		possibleKinds.add(ASTNode.Kinds.Value);
		possibleKinds.add(ASTNode.Kinds.Var);
		possibleKinds.add(ASTNode.Kinds.ScalarParm);
		kindMustBe(n.target.kind, possibleKinds, error(n) + "Operand of -- must be a scalar.");
		possibleKinds = new ArrayList<ASTNode.Kinds>();
		possibleKinds.add(ASTNode.Kinds.Var);
		possibleKinds.add(ASTNode.Kinds.Array);
		possibleKinds.add(ASTNode.Kinds.ScalarParm);
		possibleKinds.add(ASTNode.Kinds.ArrayParm);
		kindMustBe(n.target.kind, possibleKinds, error(n) + "Target of -- can't be changed.");
		n.target.type = ASTNode.Types.Integer;
	}
	void visit(argDeclsNode n){
		this.visit(n.thisDecl);
		this.visit(n.moreDecls);
	}

	void visit(nullArgDeclsNode n){}


	void visit(valArgDeclNode n){
		SymbolInfo id = (SymbolInfo) st.localLookup(n.argName.idname);
		if (id != null) {
			System.out.println(error(n) + id.name() + " is already declared.");
			typeErrors++;
			n.argName.type = ASTNode.Types.Error;
		} 
		else{
			this.visit(n.argType);
			ArrayList<ASTNode.Types> possibleTypes = new ArrayList<ASTNode.Types>();
			possibleTypes.add(ASTNode.Types.Integer);
			possibleTypes.add(ASTNode.Types.Character);
			possibleTypes.add(ASTNode.Types.Boolean);
			typeMustBe(n.argType.type, possibleTypes, error(n) + "Parameters must be of type integer, boolean, or character.");
			id = new SymbolInfo(n.argName.idname, ASTNode.Kinds.ScalarParm, n.argType.type);
			n.argName.idinfo=id;
			n.argName.type = n.argType.type;
			n.argName.kind = ASTNode.Kinds.ScalarParm;
			ArgStuff tempArgStuff = new ArgStuff(n.argType.type, ASTNode.Kinds.ScalarParm);
			//see same thing in arrayargdeclnode
			if(shouldInsert){
				try { 
					st.insert(id);
				} catch (DuplicateException d) 
				{ /* can't happen */ }
				catch (EmptySTException e) 
				{ /* can't happen */ }
				currentMethod.name.idinfo.argStuffList.add(tempArgStuff);
				return;
			}
			//add two list
			SymbolInfo.argCheckingList.add(tempArgStuff);
		}
	}

	void visit(arrayArgDeclNode n){
		SymbolInfo id = (SymbolInfo) st.localLookup(n.argName.idname);
		if (id != null) {
			System.out.println(error(n) + id.name() + " is already declared.");
			typeErrors++;
			n.argName.type = ASTNode.Types.Error;
		} 
		else{
			this.visit(n.elementType);
			ArrayList<ASTNode.Types> possibleTypes = new ArrayList<ASTNode.Types>();
			possibleTypes.add(ASTNode.Types.Integer);
			possibleTypes.add(ASTNode.Types.Character);
			possibleTypes.add(ASTNode.Types.Boolean);
			typeMustBe(n.elementType.type, possibleTypes, error(n) + "Paramter element must be of type integer, boolean, or character.");
			id = new SymbolInfo(n.argName.idname, ASTNode.Kinds.ArrayParm, n.elementType.type);
			n.argName.idinfo=id;
			n.argName.type = n.elementType.type;
			n.argName.kind = ASTNode.Kinds.ArrayParm;
			ArgStuff tempArgStuff = new ArgStuff(n.elementType.type, ASTNode.Kinds.ArrayParm);
			//if a method decl, then put args into method arg list
			if(shouldInsert){
				try { 
					st.insert(id);
				} catch (DuplicateException d) 
				{ /* can't happen */ }
				catch (EmptySTException e) 
				{ /* can't happen */ }
				currentMethod.name.idinfo.argStuffList.add(tempArgStuff);
				return;
			}
			//adds arg to arg list
			SymbolInfo.argCheckingList.add(tempArgStuff);
		}
	}

	void visit(constDeclNode n){
		SymbolInfo id = (SymbolInfo) st.localLookup(n.constName.idname);
		if (id != null) {
			System.out.println(error(n) + id.name() + " is already declared.");
			typeErrors++;
			n.constName.type = ASTNode.Types.Error;
		} 
		else{
			this.visit(n.constValue);
			ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
			possibleKinds.add(ASTNode.Kinds.Value);
			possibleKinds.add(ASTNode.Kinds.Var);
			possibleKinds.add(ASTNode.Kinds.ScalarParm);
			kindMustBe(n.constValue.kind, possibleKinds, error(n) + "Constant value's kind must be scalar");
			id = new SymbolInfo(n.constName.idname, ASTNode.Kinds.Value, n.constValue.type);
			try { 
				st.insert(id);
			} catch (DuplicateException d) 
			{ /* can't happen */ }
			catch (EmptySTException e) 
			{ /* can't happen */ }
			n.constName.idinfo=id;
		}
	}


	void visit(arrayDeclNode n){
		SymbolInfo id = (SymbolInfo) st.localLookup(n.arrayName.idname);
		if(id != null) {
			System.out.println(error(n) + n.arrayName.idname + " is already declared.");
			typeErrors++;
			n.arrayName.type = ASTNode.Types.Error;
		}
		else{
			//array size stuff
			this.visit(n.arraySize);
			if(n.arraySize.intval <= 0){
				System.out.println(error(n) + n.arrayName.idname + " must have more than 0 elements.");
				typeErrors++;
				n.arrayName.type = ASTNode.Types.Error;
				return;
			}
			this.visit(n.elementType);
			n.arrayName.idinfo = new SymbolInfo(n.arrayName.idname, ASTNode.Kinds.Array, n.elementType.type);
			n.arrayName.idinfo.length = n.arraySize.intval;
			n.arrayName.type = n.elementType.type;
			n.arrayName.kind = ASTNode.Kinds.Array;
			try { 
				st.insert(n.arrayName.idinfo);
			} catch (DuplicateException d) 
			{ /* can't happen */ }
			catch (EmptySTException e) 
			{ /* can't happen */ }
		}
	}

	void visit(charTypeNode n){
		n.type = ASTNode.Types.Character;
	}
	void visit(voidTypeNode n){
		n.type = ASTNode.Types.Void;
	}


	void visit(whileNode n){
		this.visit(n.condition);
		typeMustBe(n.condition.type, ASTNode.Types.Boolean, error(n) + "Condition's type must be boolean");
		ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
		possibleKinds.add(ASTNode.Kinds.Value);
		possibleKinds.add(ASTNode.Kinds.Var);
		possibleKinds.add(ASTNode.Kinds.ScalarParm);
		kindMustBe(n.condition.kind, possibleKinds, error(n) + "Condition's kind must be scalar");
		//check if label present
		if(n.label.isNull()){
			this.visit(n.loopBody);
			return;
		}
		else {
			SymbolInfo id = (SymbolInfo) st.localLookup(((identNode)n.label).idname);
			//if has label, check if exists
			if (id != null) {
				System.out.println(error(n) + ((identNode)n.label).idname + " is already declared.");
				typeErrors++;
				((identNode)n.label).type = ASTNode.Types.Error;
				this.visit(n.loopBody);
			} 
			else{
				SymbolInfo m = new SymbolInfo(((identNode)n.label).idname, ASTNode.Kinds.VisibleLabel, ASTNode.Types.Void);
				try {
					st.insert(m);
				} catch (DuplicateException d) 
				{ /* can't happen */ }
				catch (EmptySTException e) 
				{ /* can't happen */ }
				this.visit(n.loopBody);
				m.kind = ASTNode.Kinds.HiddenLabel; 
			}
		}
	}

	//see continue node
	void visit(breakNode n){
		SymbolInfo id =  (SymbolInfo) st.globalLookup(n.label.idname);
		if (id == null) {
			System.out.println(error(n) + n.label.idname + " is not declared.");
			typeErrors++;
			n.label.type = ASTNode.Types.Error;
		} 
		else{
			if(id.kind == ASTNode.Kinds.HiddenLabel){
				System.out.println(error(n) + id.name() + " doesn't label an enclosing while loop.");
				typeErrors++;
				return;
			}
			kindMustBe(id.kind, ASTNode.Kinds.VisibleLabel, error(n) + id.name() + " isn't a label.");
		}
	}

	//see break node
	void visit(continueNode n){
		SymbolInfo id =  (SymbolInfo) st.globalLookup(n.label.idname);
		if (id == null) {
			System.out.println(error(n) + n.label.idname + " is not declared.");
			typeErrors++;
			n.label.type = ASTNode.Types.Error;
		} 
		else{
			if(id.kind == ASTNode.Kinds.HiddenLabel){
				System.out.println(error(n) + id.name() + " doesn't label an enclosing while loop.");
				typeErrors++;
				return;
			}
			kindMustBe(id.kind, ASTNode.Kinds.VisibleLabel, error(n) + id.name() + " isn't a label.");
		}
	}

	//
	void visit(callNode n){
		SymbolInfo id =  (SymbolInfo) st.globalLookup(n.methodName.idname);
		if (id == null) {
			System.out.println(error(n) + n.methodName.idname + " is not declared.");
			typeErrors++;
			return;
		} 
		//has to be a void method
		if(id.kind != ASTNode.Kinds.Method){
			System.out.println(error(n) + n.methodName.idname + " isn't a method.");
			typeErrors++;
			return;
		}
		if(id.type != ASTNode.Types.Void){
			System.out.println(error(n) + n.methodName.idname + " is called as a procedure and must therefore return void.");
			typeErrors++;
			return;
		}
		this.visit(n.args);
		int numParams = -1;
		//number args in stored method
		int argSize = 0;
		ArrayList<Integer> parameterCheck = new ArrayList<Integer>();
		//go through list of all methods until find one with same name
		for(int i = 0; i < SymbolInfo.methodList.size(); i++){
			//parameter ccheck holds list of indeces of incorrect parameters
			parameterCheck = new ArrayList<Integer>();
			methodDeclNode temp = SymbolInfo.methodList.get(i);
			//if same name but different size, can overload bro
			if(temp.name.idname.equals(n.methodName.idname) && 
					temp.name.idinfo.argStuffList.size() == 0 && SymbolInfo.argCheckingList.size() == 0){
				SymbolInfo.argCheckingList = new ArrayList<ArgStuff>();
				argSize = temp.name.idinfo.argStuffList.size();
				return;
			}
			//if same name but same size, check params types for sameness
			if(temp.name.idname.equals(n.methodName.idname)){
				argSize = temp.name.idinfo.argStuffList.size();
				if(temp.name.idinfo.argStuffList.size() == SymbolInfo.argCheckingList.size()){
					for(int j = 0; j < SymbolInfo.argCheckingList.size(); j++){
						if(SymbolInfo.argCheckingList.get(j).type != temp.name.idinfo.argStuffList.get(j).type ||
								!kindCompatible(SymbolInfo.argCheckingList.get(j).kind, temp.name.idinfo.argStuffList.get(j).kind)){
							parameterCheck.add(j);
						}
					}
					if(parameterCheck.size() == 0){
						SymbolInfo.argCheckingList = new ArrayList<ArgStuff>();
						return;
					}
				}
				else{
					numParams = temp.name.idinfo.argStuffList.size();
				}
			}
		}
		//if invalid
		if(numParams >= 0){
			System.out.println(error(n) + n.methodName.idname + " requires " + argSize + " parameters.");
			typeErrors++;
			SymbolInfo.argCheckingList = new ArrayList<ArgStuff>();
			return;
		}
		if(parameterCheck.size() != 0){
			for(int i = 0; i < parameterCheck.size(); i++){
				System.out.println(error(n) + "In the call to " + n.methodName.idname + 
						", parameter " + (parameterCheck.get(i) + 1) + " has incorrect type.");
				typeErrors++;
			}
		}
		SymbolInfo.argCheckingList = new ArrayList<ArgStuff>();
	}

	//checks kinds of argdecls
	boolean kindCompatible(ASTNode.Kinds kind1, ASTNode.Kinds kind2){
		if(kind1 == ASTNode.Kinds.Var || kind1 == ASTNode.Kinds.Value || kind1 == ASTNode.Kinds.ScalarParm){
			if(kind2 == ASTNode.Kinds.ScalarParm){
				return true;
			}
		}
		if(kind2 == ASTNode.Kinds.Var || kind2 == ASTNode.Kinds.Value || kind2 == ASTNode.Kinds.ScalarParm){
			if(kind1 == ASTNode.Kinds.ScalarParm){
				return true;
			}
		}
		if(kind1 == ASTNode.Kinds.Array || kind1 == ASTNode.Kinds.ArrayParm){
			if(kind2 == ASTNode.Kinds.ArrayParm){
				return true;
			}
		}
		if(kind2 == ASTNode.Kinds.Array || kind2 == ASTNode.Kinds.ArrayParm){
			if(kind1 == ASTNode.Kinds.ArrayParm){
				return true;
			}
		}
		if(kind1 == kind2){
			return true;
		}
		return false;
	}

	void visit(readNode n){ 
		ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
		this.visit(n.targetVar);
		ArrayList<ASTNode.Types> possibleTypes = new ArrayList<ASTNode.Types>();
		possibleTypes.add(ASTNode.Types.Integer);
		possibleTypes.add(ASTNode.Types.Character);
		typeMustBe(n.targetVar.type, possibleTypes, error(n) + "Only integers and characters may be read.");
		possibleKinds.add(ASTNode.Kinds.Var);
		kindMustBe(n.targetVar.kind, possibleKinds, error(n) + n.targetVar.varName.idname + " may not be assigned to.");
		this.visit(n.moreReads);
	}


	void visit(returnNode n){
		if(n.returnVal.isNull()){
			typeMustBe(currentMethod.returnType.type, ASTNode.Types.Void, error(n) + "Return type of " + currentMethod.name.idname + " is not void.");
		}
		else{
			this.visit(n.returnVal);
			exprNode temp = (exprNode)n.returnVal;
			ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
			possibleKinds.add(ASTNode.Kinds.Value);
			possibleKinds.add(ASTNode.Kinds.Var);
			possibleKinds.add(ASTNode.Kinds.ScalarParm);
			kindMustBe(temp.kind, possibleKinds, error(n) + "Return value's kind must be scalar");
			typeMustBe(temp.type, currentMethod.returnType.type, error(n) + "Return type of " + currentMethod.name.idname + " is " + currentMethod.returnType.type);
		}
	}


	//does some stuff too
	//type and kind check
	void visit(argsNode n){ 
		this.visit(n.argVal);
		ArrayList<ASTNode.Types> possibleTypes = new ArrayList<ASTNode.Types>();
		possibleTypes.add(ASTNode.Types.Integer);
		possibleTypes.add(ASTNode.Types.Character);
		possibleTypes.add(ASTNode.Types.Boolean);
		typeMustBe(n.argVal.type, possibleTypes, error(n) + "Parameters must be of type integer, boolean, or character.");
		ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
		possibleKinds.add(ASTNode.Kinds.ScalarParm);
		possibleKinds.add(ASTNode.Kinds.String);
		possibleKinds.add(ASTNode.Kinds.Var);
		possibleKinds.add(ASTNode.Kinds.Array);
		possibleKinds.add(ASTNode.Kinds.ArrayParm);
		possibleKinds.add(ASTNode.Kinds.Value);
		kindMustBe(n.argVal.kind, possibleKinds, error(n) + "Parameters must be Scalar, strings, or arrays.");
		//argstuff is used to build list of method arguments
		ArgStuff temp = new ArgStuff(n.argVal.type, n.argVal.kind);
		SymbolInfo.argCheckingList.add(temp);
		this.visit(n.moreArgs);
	}

	void visit(nullArgsNode n){}

	//cast node does stuff
	void visit(castNode n){
		this.visit(n.operand);
		ArrayList<ASTNode.Types> possibleTypes = new ArrayList<ASTNode.Types>();
		possibleTypes.add(ASTNode.Types.Integer);
		possibleTypes.add(ASTNode.Types.Character);
		possibleTypes.add(ASTNode.Types.Boolean);
		//type check n
		if(n.operand.type != ASTNode.Types.Integer && n.operand.type != ASTNode.Types.Boolean 
				&& n.operand.type != ASTNode.Types.Character){
			System.out.println(error(n) + "Operand must be of type integer, character, or boolean.");
			typeErrors++;
			n.type = ASTNode.Types.Error;
			return;
		}
		//kind check n
		ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
		possibleKinds.add(ASTNode.Kinds.Value);
		possibleKinds.add(ASTNode.Kinds.Var);
		possibleKinds.add(ASTNode.Kinds.ScalarParm);
		if(n.operand.kind != ASTNode.Kinds.Value && n.operand.kind != ASTNode.Kinds.Var && n.operand.kind != ASTNode.Kinds.Value){
			System.out.println(error(n) + "Operand of cast must be an  integer, character or boolean.");
			typeErrors++;
			n.type = ASTNode.Types.Error; 
			return;
		}
		this.visit(n.resultType);
		if(n.resultType.type != ASTNode.Types.Integer && n.resultType.type != ASTNode.Types.Boolean 
				&& n.resultType.type != ASTNode.Types.Character){
			System.out.println(error(n) + "Result must be of type integer, character, or boolean.");
			typeErrors++;
			n.type = ASTNode.Types.Error;
			return;
		}
		n.type = n.resultType.type;
		n.kind = n.operand.kind;
		//n.operand.type = n.type;		
	}
	//fct call node is similar to call node.  fctcall also handles more types (int bool char)
	//fct node also tells you how many overloaded functions there are that you do not match
	void visit(fctCallNode n){
		n.kind = n.methodName.kind;
		SymbolInfo id =  (SymbolInfo) st.globalLookup(n.methodName.idname);
		if (id == null) {
			System.out.println(error(n) + n.methodName.idname + " is not declared.");
			typeErrors++;
			n.type = ASTNode.Types.Error;
			return;
		} 
		if(id.kind != ASTNode.Kinds.Method){
			System.out.println(error(n) + n.methodName.idname + " isn't a method.");
			typeErrors++;
			n.type = ASTNode.Types.Error;
			return;
		}
		if(id.type != ASTNode.Types.Integer && id.type != ASTNode.Types.Boolean && id.type != ASTNode.Types.Character){
			System.out.println(error(n) + n.methodName.idname + " Return type must be an integer, character or boolean. ");
			typeErrors++;
			n.type = ASTNode.Types.Error;
			return;
		}
		this.visit(n.methodArgs);
		int numParams = -1;
		int argSize = 0;
		ArrayList<Integer> parameterCheck = new ArrayList<Integer>();
		for(int i = 0; i < SymbolInfo.methodList.size(); i++){
			parameterCheck = new ArrayList<Integer>();
			methodDeclNode temp = SymbolInfo.methodList.get(i);
			if(temp.name.idname.equals(n.methodName.idname) && 
					temp.name.idinfo.argStuffList.size() == 0 && SymbolInfo.argCheckingList.size() == 0){
				argSize++;
				SymbolInfo.argCheckingList = new ArrayList<ArgStuff>();
				return;
			}
			if(temp.name.idname.equals(n.methodName.idname)){
				argSize++;
				if(temp.name.idinfo.argStuffList.size() == SymbolInfo.argCheckingList.size()){
					for(int j = 0; j < SymbolInfo.argCheckingList.size(); j++){
						if(SymbolInfo.argCheckingList.get(j).type != temp.name.idinfo.argStuffList.get(j).type ||
								!kindCompatible(SymbolInfo.argCheckingList.get(j).kind, temp.name.idinfo.argStuffList.get(j).kind)){
							parameterCheck.add(j);
						}
					}
					if(parameterCheck.size() == 0){
						SymbolInfo.argCheckingList = new ArrayList<ArgStuff>();
						n.type = id.type;
						return;
					}
				}
				else{
					numParams = temp.name.idinfo.argStuffList.size();
				}
			}
		}
		if(numParams >= 0){
			if(numParams != SymbolInfo.argCheckingList.size() && argSize <= 1){
				System.out.println(error(n) + n.methodName.idname + " requires " + numParams + " parameters.");
				typeErrors++;
				SymbolInfo.argCheckingList = new ArrayList<ArgStuff>();
				n.type = ASTNode.Types.Error;
				return;
			}
			System.out.println(error(n) + "None of the " + argSize + " definitions of method " + n.methodName.idname+ 
					" match the parameters in this call.");
			typeErrors++;
			SymbolInfo.argCheckingList = new ArrayList<ArgStuff>();
			n.type = ASTNode.Types.Error;
			return;
		}
		if(parameterCheck.size() != 0){
			for(int i = 0; i < parameterCheck.size(); i++){
				System.out.println(error(n) + "In the call to " + n.methodName.idname + 
						", parameter " + (parameterCheck.get(i) + 1) + " has incorrect type.");
				typeErrors++;
				n.methodName.type = ASTNode.Types.Error;
			}
		}
		SymbolInfo.argCheckingList = new ArrayList<ArgStuff>();
	}

	//only has to handle not (!)
	void visit(unaryOpNode n){
		this.visit(n.operand);
		if(n.operatorCode == sym.NOT){
			n.type = ASTNode.Types.Boolean;
			typeMustBe(n.operand.type, ASTNode.Types.Boolean, error(n)+"Operand of"+ opToString(n.operatorCode)+"must be boolean.");
			ArrayList<ASTNode.Kinds> possibleKinds = new ArrayList<ASTNode.Kinds>();
			possibleKinds.add(ASTNode.Kinds.ScalarParm);
			possibleKinds.add(ASTNode.Kinds.Var);
			possibleKinds.add(ASTNode.Kinds.Value);
			kindMustBe(n.operand.kind, possibleKinds, error(n) + "Parameters must be Scalar, strings, or arrays.");
			n.kind = n.operand.kind;
		}
	}

	//charlitnode sets type to character
	void visit(charLitNode n){
		n.type = ASTNode.Types.Character;
	}
	//strlitnode sets type to char and kind to string
	void visit(strLitNode n){
		n.type = ASTNode.Types.Character;
		n.kind = ASTNode.Kinds.String;
	}

	//both bool nodes set type to boolean
	void visit(trueNode n){
		n.type = ASTNode.Types.Boolean;
	}

	void visit(falseNode n){
		n.type = ASTNode.Types.Boolean;
	}
}
